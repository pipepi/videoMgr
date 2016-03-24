/**
 * 
 */
package com.aepan.sysmgr.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.azure.AzureMediaSDKException;
import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.azure.UploadFileRps;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.VideoCheck;
import com.aepan.sysmgr.model._enum.ResponseType;
import com.aepan.sysmgr.model._enum.Validate;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.StorageService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.FileUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 视频文件存储到本地控制器    
 * 然后异步处理上传的视频
 * @author lanker
 * 2015年11月26日上午10:18:48
 */
@Controller
public class VideoAsyncController extends DataTableController {
	private static final Logger logger = LoggerFactory.getLogger(VideoAsyncController.class);
	@Autowired
	private StorageService storageService;
	@Autowired
	private PartnerDataService partnerDataService;
	@RequestMapping("/video/async/page")
	public String uploadVideoPage(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		if(!isLogin(request)){
			return "redirect:/";
		}
		model.addAttribute("role", User.PARTNER_SELLER);
		model.addAttribute("partner_ctx", ConfigManager.getInstance().getPartnerConfig(configService).rootPath);
		return "/video/async/edit";
	}
	@RequestMapping("/video/async/upload")
	public String uploadVideo(HttpServletRequest request,HttpServletResponse res,ModelMap model) throws InterruptedException{
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, res);
		}
		File videoFile = storageService.uploadVideo(new UploadFileInput(true, request)).file;
		if(videoFile!=null){
			model.addAttribute("url", videoFile.getAbsoluteFile());
			model.addAttribute("name", videoFile.getName());
			model.addAttribute("success", true);
		}else{
			model.addAttribute("success", false);
			model.addAttribute("errMsg", "上传视频不能大于200M");
		}
    	AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("/video/async/save")
	public String save(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws Exception{
		Validate vali = validate(req);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, res);
		}
		//套餐拥有视频数量校验
		User user = (User)req.getSession().getAttribute(Constants.SESSION_USER);
		int userId = user.getId();
		PackageInfo packageInfo =  packageService.getById(user.getPackageId());
		int hadVideoNum = videoService.getVideoCountByUserId(userId);
		
		int canHaveVideoNum = packageInfo==null?0:packageInfo.getVideoNum()*packageInfo.getPlayerNum();
		if(hadVideoNum>=canHaveVideoNum){
			model.addAttribute("error_desc", "套餐视频数量已满["+hadVideoNum+"/"+canHaveVideoNum+"]");
			AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
			return null;
		}		
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String srcImgId = reqInfo.getParameter("src_img_id");
		String cutImgId = reqInfo.getParameter("cut_img_id");
		String videoFileName = reqInfo.getParameter("video_file_name");
		String videoUrl = reqInfo.getParameter("video_url");
		String activeStr = reqInfo.getParameter("active","");
		int active = activeStr.trim().length()>0?Integer.parseInt(activeStr):1;
		String desc = reqInfo.getParameter("desc");
		String title = reqInfo.getParameter("title");
		
		//根据裁剪图和大小图尺寸，生成两张图；删除源图和裁剪图
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		File f = new File(storageService.localImgUrl(cutImgId));
		BufferedImage srcImage = ImageIO.read(f);
		String imageType = FileUtil.getImageType(f);
		byte[] maxImgContent =  FileUtil.file2Byte(f);
		byte[] max_414_ImgContent =  FileUtil.scaleImage(srcImage, 414, imageType,fileConfig);
		byte[] min_301_ImgContent =  FileUtil.scaleImage(srcImage, 301, imageType,fileConfig);
		byte[] minImgContent =  FileUtil.scaleImage(srcImage, 188, imageType,fileConfig);
		logger.debug(" "+maxImgContent.length/1000);
		logger.debug(" "+max_414_ImgContent.length/1000);
		logger.debug(" "+min_301_ImgContent.length/1000);
		logger.debug(" "+minImgContent.length/1000);
		String maxImg = "max_"+srcImgId;
		String max_414_Img = "max_414"+srcImgId;
		String min_301_Img = "min_301"+srcImgId;
		String minImg = "min_"+srcImgId;
		//保存本地替换成保存微软云
		UploadFileRps maxrs = storageService.uploadImg(new UploadFileInput(maxImg, maxImgContent,false));
		UploadFileRps max_414_rs = storageService.uploadImg(new UploadFileInput(max_414_Img, max_414_ImgContent,false));
		UploadFileRps min_301_rs = storageService.uploadImg(new UploadFileInput(min_301_Img, min_301_ImgContent,false));
		UploadFileRps minrs = storageService.uploadImg(new UploadFileInput(minImg, minImgContent,false));
		storageService.deleteImg(new UploadFileInput(srcImgId, cutImgId, true));
		
		Video v = new Video();  
		v.checkState = VideoCheck.state_转码中;
		UUID uuid = UUID.randomUUID();
		v.userId = userId;
		v.guid=uuid.toString();
		v.name=title;
		v.desc=desc;
		v.active= active==1?true:false;
		v.uploadAssetId = videoFileName;
		v.encodeAssetId = videoUrl;
		if(maxrs.success){v.imgMax = maxrs.imgUrl;}
		if(max_414_rs.success){v.imgMax414 = max_414_rs.imgUrl;}
		if(min_301_rs.success){v.imgMin301 = min_301_rs.imgUrl;}
		if(minrs.success){v.imgMin = minrs.imgUrl;}
		v.storage = storageService.storage();
		int id = videoService.insert(v);
		//记录操作日志
		partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
						user.getPartnerAccountId(),
						user.getPartnerAccountName(),
						"/video/save", 
						"添加视频[id="+id+"]", 
						req.getRemoteAddr()));
		model.addAttribute("id", id);
		model.clear();
		model.addAttribute("success",0);
		model.addAttribute("role", User.PARTNER_SELLER);
		AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("/video/async/updatepage")
	public String updatePage(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws AzureMediaSDKException{
		Validate vali = validate(req);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, res);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String id = reqInfo.getParameter("video_id");
		int role = reqInfo.getIntParameter("role",1);
		if(id!=null&&!id.trim().isEmpty()){
			Video v = videoService.get(Integer.parseInt(id));
			v.escapeHtml();
			model.addAttribute("video",v);
		}
		model.addAttribute("role", role);
		return "/video/async/edit";
	}
	@RequestMapping("/video/async/update")
	public String update(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws Exception{
		Validate vali = validate(req);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, res);
		}
		User user = getLoginUser(req);
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String id = reqInfo.getParameter("id");
		int role = reqInfo.getIntParameter("role",1);
		String srcImgId = reqInfo.getParameter("src_img_id");
		String cutImgId = reqInfo.getParameter("cut_img_id");
		String videoFileName = reqInfo.getParameter("video_file_name");
		String videoUrl = reqInfo.getParameter("video_url");
		String activeStr = reqInfo.getParameter("active","");
		int active = activeStr.trim().length()>0?Integer.parseInt(activeStr):1;
		String desc = reqInfo.getParameter("desc");
		String title = reqInfo.getParameter("title");
		
		if(id!=null&&!id.trim().isEmpty()){
			Video v = videoService.get(Integer.parseInt(id));
			if(v!=null){
				boolean update = false;
				boolean activeBool = active==1?true:false; 
				if(activeBool!=v.active){
					v.active = activeBool;
					update = true;
				}
				if(title!=null&&!title.equals(v.name)){
					v.name=title;
					update = true;
				}
				if(desc!=null&&!desc.equals(v.desc)){
					v.desc=desc;
					update = true;
				}
				if(videoFileName!=null&&!videoFileName.endsWith(v.uploadAssetId)){
					v.uploadAssetId = videoFileName;
					v.encodeAssetId = videoUrl;
					if(v.checkState!=VideoCheck.state_转码中){
						v.checkState=VideoCheck.state_转码中;
					}
					update = true;
				}
				
				if(cutImgId!=null&&srcImgId!=null){
					FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
					///根据裁剪图和大小图尺寸，生成两张图；删除源图和裁剪图;删除旧的两张尺寸图
					File f = new File(storageService.localImgUrl(cutImgId));
					BufferedImage srcImage = ImageIO.read(f);
					String imageType = FileUtil.getImageType(f);
					
					byte[] maxImgContent = FileUtil.file2Byte(f);
					byte[] max_414_ImgContent =  FileUtil.scaleImage(srcImage, 414, imageType,fileConfig);
					byte[] min_301_ImgContent =  FileUtil.scaleImage(srcImage, 301, imageType,fileConfig);
					byte[] minImgContent =  FileUtil.scaleImage(srcImage, 188, imageType,fileConfig);
					String maxImg = "max_"+srcImgId;
					String max_414_Img = "max_414"+srcImgId;
					String min_301_Img = "min_301"+srcImgId;
					String minImg = "min_"+srcImgId;
					//保存本地替换成保存微软云
					UploadFileRps maxrs = storageService.uploadImg(new UploadFileInput(maxImg, maxImgContent,false));
					UploadFileRps max_414_rs = storageService.uploadImg(new UploadFileInput(max_414_Img, max_414_ImgContent,false));
					UploadFileRps min_301_rs = storageService.uploadImg(new UploadFileInput(min_301_Img, min_301_ImgContent,false));
					UploadFileRps minrs = storageService.uploadImg(new UploadFileInput(minImg, minImgContent,false));
					
					storageService.deleteImg(new UploadFileInput(srcImgId, cutImgId, true));
					storageService.deleteImg(new UploadFileInput(v.imgMax, v.imgMin, false));
					storageService.deleteImg(new UploadFileInput(v.imgMax414, v.imgMin301, false));
					if(maxrs.success){v.imgMax = maxrs.imgUrl;}
					if(max_414_rs.success){v.imgMax414 = max_414_rs.imgUrl;}
					if(min_301_rs.success){v.imgMin301 = min_301_rs.imgUrl;}
					if(minrs.success){v.imgMin = minrs.imgUrl;}
					update = true;
				}
				if(update){
					if(v.checkState!=VideoCheck.state_转码中){
						v.checkState = VideoCheck.state_待审核;
					}
					videoService.update(v);
					//记录操作日志
					partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
									user.getPartnerAccountId(),
									user.getPartnerAccountName(),
									"/video/update", 
									"修改视频[id="+id+"]", 
									req.getRemoteAddr()));
				}
			}
		}
		model.clear();
		model.addAttribute("success",0);
		model.addAttribute("role", role);
		AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	/**
	 * 删除视频(仅视频)
	 */
	@RequestMapping("/video/async/remove")
	public String removeVideo(HttpServletRequest req,HttpServletResponse res,ModelMap model) throws AzureMediaSDKException{
		Validate v = validate(req);
		if(!v.equals(Validate.OK)){
			return validateResponse(v, ResponseType.JSON, model, res);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		 String upload_asset_id = reqInfo.getParameter("upload_asset_id");
		 String encode_asset_id = reqInfo.getParameter("encode_asset_id");
		 int  flag = reqInfo.getIntParameter("flag",1);//删除标签 1:微软云  2:本地
		 UploadFileInput in = new UploadFileInput(flag==2?true:false,upload_asset_id,encode_asset_id);
		 model.addAttribute("success", storageService.deleteVideo(in));
		 AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
}
