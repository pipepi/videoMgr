package com.aepan.sysmgr.web.controller;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com._21cn.framework.util.PageList;
import com._21cn.framework.web.HttpRequestInfo;
import com.aepan.sysmgr.azure.UploadFileInput;
import com.aepan.sysmgr.model.Store;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.User;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.VideoCheck;
import com.aepan.sysmgr.model._enum.ResponseType;
import com.aepan.sysmgr.model._enum.Validate;
import com.aepan.sysmgr.model.config.FileConfig;
import com.aepan.sysmgr.model.log.OperationLog;
import com.aepan.sysmgr.model.packageinfo.PackageInfo;
import com.aepan.sysmgr.model.packageinfo.PackageStat;
import com.aepan.sysmgr.model.tempinfo.LinkVideoInfo;
import com.aepan.sysmgr.service.ConfigService;
import com.aepan.sysmgr.service.PackageService;
import com.aepan.sysmgr.service.PackageStatService;
import com.aepan.sysmgr.service.PartnerDataService;
import com.aepan.sysmgr.service.StorageService;
import com.aepan.sysmgr.service.StoreService;
import com.aepan.sysmgr.service.UserService;
import com.aepan.sysmgr.service.VideoService;
import com.aepan.sysmgr.util.AjaxResponseUtil;
import com.aepan.sysmgr.util.ConfigManager;
import com.aepan.sysmgr.util.Constants;
import com.aepan.sysmgr.util.FileUtil;
import com.alibaba.fastjson.JSONObject;

/**
 * 视频相关接口
 * @author lanker
 * 2015年7月31日下午4:09:52
 */
@Controller
public class VideoController extends DataTableController {
	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	
	@Autowired
	private VideoService videoService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UserService userService;
	@Autowired
	private PackageService packageService;
	@Autowired 
	private PackageStatService packageStatService;
	@Autowired
	private StoreService storeService;
	@Autowired
	private StorageService storageService;
	@Autowired
	private PartnerDataService partnerDataService;

	//----------------------------视频审核 start---------------------------------------
	@RequestMapping("/video/searchhm")
	public String checkSearch(HttpServletRequest request,HttpServletResponse res,ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, res);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String sellerName = reqInfo.getParameter("seller_name","");
		String checkState = reqInfo.getParameter("check_state");
		String pageNo = reqInfo.getParameter("pageNo");
		String pageSize = reqInfo.getParameter("pageSize");
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?4:Integer.parseInt(pageSize);
		int checkstate = checkState==null?-1:Integer.parseInt(checkState);
		PageList<Video> list = new PageList<Video>();
		if(checkstate>-1){
			model.addAttribute("checkState",checkstate);
		}
		model.addAttribute("sortName", "update_time");
		model.addAttribute("sortOrder", "desc");
		if(sellerName==null||sellerName.trim().length()==0){
			//查询最新编辑过的视频列表
			//model.addAttribute("user", ((User)(request.getSession().getAttribute(Constants.SESSION_USER))).getId());
			list = videoService.getListWithPatnerAccountName(model, pn, ps);
		}else{
			//接口：输入（商家名称）输出（videoMgr系统中对应的user_id）
			
			int[] userIds = userService.getUserIdsByPartnerAccountName(sellerName);
			if(userIds!=null&&userIds.length>0){
				String uIdStr = "";
				for (int i : userIds) {
					uIdStr += i+",";
				}
				uIdStr = uIdStr.substring(0,uIdStr.length()-1);
				model.addAttribute("userIds", uIdStr);
				list = videoService.getListWithPatnerAccountName(model, pn, ps);
			}
		}
		model.clear();
		model.addAttribute("list", list);
		model.addAttribute("turn", list.getPageTurn());
		return "/video/ui/checksub";
	}
	@RequestMapping("/video/checkpage")
	public String checkPage(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, response);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String checkState = reqInfo.getParameter("check_state");
		if(checkState!=null&&!checkState.trim().isEmpty()){
			model.addAttribute("check_state", checkState);
		}
		//校验是否为管理者
		return "/video/ui/check";
	}
	@RequestMapping("/video/check")
	public String check(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, response);
		}
		User user = (User)request.getSession().getAttribute(Constants.SESSION_ADMIN_USER);
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		int videoId = reqInfo.getIntParameter("id");
		int checkState = reqInfo.getIntParameter("state");
		String checkMsg = reqInfo.getParameter("msg","");
		videoService.check(videoId, checkState, checkMsg);
		//记录操作日志
		partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
						user.getPartnerAccountId(),
						user.getPartnerAccountName(),
						"/video/check", 
						"审核视频[id="+videoId+",checkState(1待审核、2上线、3未通过、4下线)="+checkState+"]", 
						request.getRemoteAddr()));
		model.addAttribute("success", true);
		AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("/video/offLineMulti")
	public String offLineMulti(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, response);
		}
		User user = (User)request.getSession().getAttribute(Constants.SESSION_ADMIN_USER);
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String ids = reqInfo.getParameter("ids");
		String checkMsg = reqInfo.getParameter("msg");
		if(ids!=null&&ids.trim().length()>0&&
		   checkMsg!=null&&checkMsg.trim().length()>0
			){
			String[] videoIdArr = ids.split(",");
			for (String vid : videoIdArr) {
				int vidInt = Integer.parseInt(vid);
				videoService.check(vidInt, VideoCheck.state_下线, checkMsg);
			}
			//记录操作日志
			partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
							user.getPartnerAccountId(),
							user.getPartnerAccountName(),
							"/video/offLineMulti", 
							"批量下线视频[ids="+ids+"]", 
							request.getRemoteAddr()));
			model.addAttribute("success", true);
			AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		}
		return null;
	}

	//----------------------------视频审核 end---------------------------------------
	@RequestMapping("/video/linkPlayer")
	@Transactional(rollbackFor=Exception.class)
	public String linkPlayer(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, response);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
        Integer storeId = reqInfo.getIntParameter("storeId", -1);
        if(storeId > 0 ){
        	User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
        	String videoIds = reqInfo.getParameter("videoIds", "");
        	if(videoIds.trim().length()>0){
        		String[] vidArr = videoIds.split(",");
        		List<StoreVideo> batchList = new ArrayList<StoreVideo>();
        		for (String vid : vidArr) {
					StoreVideo sv = new StoreVideo(user.getId(),storeId,Integer.parseInt(vid));
					batchList.add(sv);
				}
        		LinkVideoInfo lvi = videoService.canLink(user.getId(),user.getPackageId(), storeId, batchList);
        		if(lvi.can){
        			videoService.deleteByUserIdANDStoreId(user.getId(), storeId);
        			videoService.batchInsert(batchList.size(), batchList, user.getId());
        			//更新套餐,已使用视频数量
        			PackageStat pstate = packageStatService.getByUserId(user.getId());
        			pstate.setVideoNum(lvi.linkedVideoNum);
        			packageStatService.updateUsedNum(pstate);
        			//更新被关联播放器logo
        			Store store = storeService.getById(storeId);
        			if(store!=null){
        				Video logoVideo = videoService.getStoreLogoVideo(videoIds);
        				if(logoVideo!=null){
        					store.setLogoUrl(logoVideo.imgMin);
        					store.setLogoUrl_301(logoVideo.getImgMin301());
            				store.setMaxLogoUrl(logoVideo.getImgMax());
            				store.setMaxLogoUrl_414(logoVideo.getImgMax414());
        				}else{
        					store.setLogoUrl("");
        					store.setLogoUrl_301("");
            				store.setMaxLogoUrl("");
            				store.setMaxLogoUrl_414("");
        				}
        				model.addAttribute("storeLogo", store.getLogoUrl());
        				//更新播放器状态
        				if(store.getStatus()==Store.STATUS_离线){
        					if(storeService.getLinkedProductNum(storeId)>0){
        						store.setStatus(Store.STATUS_在线);
        					}
        				}
        				storeService.update(configService, store);
        			}
        		}else{
        			model.addAttribute("success", false);
        			
        			model.addAttribute("oc", lvi.onceStoreCanLinkVideoNum);
        			model.addAttribute("ol", lvi.onceStoreVideoNum);
        			
        			model.addAttribute("c", lvi.canlinkVideoNum);
        			model.addAttribute("l", lvi.linkedVideoNum);
                	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
                	return null;
        		}
        		 
        	}else if(videoIds.trim().length()==0){
        		videoService.deleteByUserIdANDStoreId(user.getId(), storeId);
        		//更新被关联播放器logo 状态
        		Store store = storeService.getById(storeId);
        		if(store!=null){
        			store.setLogoUrl("");
        			model.addAttribute("storeLogo", "");
        			if(store.getStatus()==Store.STATUS_在线){
        				store.setStatus(Store.STATUS_离线);
        			}
        			storeService.update(configService, store);
        		}
        	}
        	model.addAttribute("success", true);
        	packageStatService.countLindedVideoNum(user.getId());
        	AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
        	return null;
        }
        model.addAttribute("success", false);
        AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
	/**
	 * 播放器页面关联视频
	 */
	@RequestMapping("video/listhm4player")
	public String listVideoData4Player(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, response);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pageNo");
		String pageSize = reqInfo.getParameter("pageSize");
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?50:Integer.parseInt(pageSize);
		model.addAttribute("user", ((User)(request.getSession().getAttribute(Constants.SESSION_USER))).getId());
		model.addAttribute("checkState",VideoCheck.state_上线);
		model.addAttribute("sortName", "update_time");
		model.addAttribute("sortOrder", "desc");
		PageList<Video> list = videoService.getList(model, pn, ps);
		model.clear();
		model.addAttribute("list", list);
		model.addAttribute("turn", list.getPageTurn());
		return "/store/videolistsub";
	}	
	@RequestMapping("video/listhm")
	public String listVideoPage(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, response);
		}
		User user = getUser(request);
		int userId = user.getId();
		PackageStat packageStat = packageStatService.getByUserId(userId);
		float  totalFlowNum=packageStat.getFlowNum()/1024;
		float usedFlowNum = packageStat.getUsedFlowNum()/1024;
		boolean flowEnough=true;
		if(usedFlowNum>=totalFlowNum){
			flowEnough=false;
		}
		Date now = new Date();
		Date endTime = packageStat.getEndTime();
		boolean isOutDate=now.after(endTime);
		model.addAttribute("isOutDate",isOutDate);
		model.addAttribute("flowEnough",flowEnough);
		PackageInfo packageInfo =  packageService.getById(user.getPackageId());
		int hadVideoNum = videoService.getVideoCountByUserId(userId);
		int canHaveVideoNum = packageInfo==null?0:packageInfo.getCanHaveVideoNum();
		model.addAttribute("hadVideoNum", hadVideoNum);
		model.addAttribute("canHaveVideoNum", canHaveVideoNum);
		return "/video/ui/list";
		
	}
	
	@RequestMapping("video/listhmAjax")
	public String listVideoAjax(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, response);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pageNo");
		String pageSize = reqInfo.getParameter("pageSize");
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?4:Integer.parseInt(pageSize);
		model.addAttribute("user", ((User)(request.getSession().getAttribute(Constants.SESSION_USER))).getId());
		model.addAttribute("sortName", "update_time");
		model.addAttribute("sortOrder", "desc");
		PageList<Video> list = videoService.getList(model, pn, ps);
		model.clear();
		model.addAttribute("list", list);
		model.addAttribute("turn", list.getPageTurn());
		AjaxResponseUtil.returnData(response, JSONObject.toJSONString(model));
		return null;
	}
	@RequestMapping("video/listhmdata")
	public String listVideoSub(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Validate vali = validate(request);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.HTML, model, response);
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(request);
		String pageNo = reqInfo.getParameter("pageNo");
		String pageSize = reqInfo.getParameter("pageSize");
		int pn = pageNo==null?1:Integer.parseInt(pageNo);
		int ps = pageSize==null?4:Integer.parseInt(pageSize);
		model.addAttribute("user", ((User)(request.getSession().getAttribute(Constants.SESSION_USER))).getId());
		model.addAttribute("sortName", "update_time");
		model.addAttribute("sortOrder", "desc");
		PageList<Video> list = videoService.getList(model, pn, ps);
		model.clear();
		model.addAttribute("list", list);
		model.addAttribute("turn", list.getPageTurn());
		return "/video/ui/listsub";
	}
	/**
	 *删除视频(包含名称、描述、视频、图片、关联关系)
	 */
	@RequestMapping("/video/delete")
	@Transactional(rollbackFor=Exception.class)
	public String delete(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		Validate v = validate(req);
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		ResponseType resType = ResponseType.valueOf(reqInfo.getParameter("res_type","HTML"));
		if(!v.equals(Validate.OK)){
			return validateResponse(v, resType, model, res);
		}
		User user = getLoginUser(req);
		String id = reqInfo.getParameter("video_id");
		if(id!=null&&!id.trim().isEmpty()){
			//删除数据库视频记录，关联播放器记录，云视频数据，云图片数据
			videoService.delete(Integer.parseInt(id),user.getId());
			//记录操作日志
			partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
							user.getPartnerAccountId(),
							user.getPartnerAccountName(),
							"/video/deletehmAjax", 
							"删除视频[id="+id+"]", 
							req.getRemoteAddr()));
		}
		switch (resType) {
		case HTML:
			return "redirect:/video/listhm";
		case JSON:
			model.addAttribute("success", true);
			AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
			return null;
		default:return null;
		}
	}
	@RequestMapping("/video/update_ajax")
	public String updateAjax(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		Validate vali = validate(req);
		if(!vali.equals(Validate.OK)){
			return validateResponse(vali, ResponseType.JSON, model, res);
		}
		User user = getLoginUser(req);
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String id = reqInfo.getParameter("video_id");
		String active = reqInfo.getParameter("active");
		if(id!=null&&!id.trim().isEmpty()){
			Video v = videoService.get(Integer.parseInt(id));
			if(v!=null){
				boolean update = false;
				if(active!=null&&!active.trim().isEmpty()){
					v.active=active.equals("true")?true:false;
					update = true;
				}
				if(update){
					videoService.update(v);
					
					/*List<StoreVideo> videoList = videoService.getStoreVideoListByVideoId(v.id);
					if(videoList!=null){
						for (StoreVideo storeVideo : videoList) {
							int storeId=storeVideo.getStoreId();
							int userId=storeVideo.getUserId();
							List<StoreVideo> storeVideoList = storeService.getStoreVideoList(userId, storeId);
							
							if(storeVideoList==null){
								continue;
							}
							StringBuffer idBuff=new StringBuffer();
							for (StoreVideo storeVideo2 : storeVideoList) {
								idBuff.append(storeVideo2.getVideoId()).append(",");
							}
							
							Store store = storeService.getById(storeId);
							Video logoVideo = videoService.getStoreLogoVideo(idBuff.toString());
							if(logoVideo==null){
								store.setLogoUrl("");
								store.setMaxLogoUrl("");
							}else{
								store.setLogoUrl(logoVideo.imgMin);
								store.setMaxLogoUrl(logoVideo.imgMax);
							}
							
							storeService.update(configService, store);
							
						}
					}*/
					
					partnerDataService.addLog(new OperationLog(OperationLog.TYPE_视频, 
									user.getPartnerAccountId(),
									user.getPartnerAccountName(),
									"/video/update_ajax", 
									"修改视频[id="+id+"]", 
									req.getRemoteAddr()));
					model.addAttribute("success", true);
					AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
					return null;
				}
			}
		}
		model.addAttribute("success", false);
		AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	/**
	 * 上传被裁剪源图
	 */
	@RequestMapping("/video/upimgcover")
	public String uploadImgCover(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		//校验图片高【大于1280】宽【大于800】 
		File f = storageService.uploadImg(new UploadFileInput(req,true)).file;
		try {
			BufferedImage bi = ImageIO.read(f);
        int srcWidth = bi.getWidth();
        int srcHeight = bi.getHeight();
        String imageId = f.getName();
        String imageUrl = f.getAbsolutePath();
        if(srcWidth<1280||srcHeight<800){
        	model.addAttribute("width", srcWidth);
            model.addAttribute("height", srcHeight);
        	model.addAttribute("status", 2);
        	model.addAttribute("errMSG", "上传图片最小分辨率1280*800!");
        	storageService.deleteImg(new UploadFileInput(imageId, "", true));
        }else{
        	//清楚上一次上传和裁剪的图片
        	String oldSrcImgId = reqInfo.getParameter("oldSrcImgId");
        	String oldCutImgId = reqInfo.getParameter("oldCutImgId");
        	storageService.deleteImg(new UploadFileInput(oldSrcImgId, oldCutImgId, true));
        	model.addAttribute("imageId", imageId);
        	model.addAttribute("imageUrl", imageUrl);
        	model.addAttribute("width", srcWidth);
        	model.addAttribute("height", srcHeight);
        	model.addAttribute("status", 0);
        }
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("status", 1);
			model.addAttribute("errMSG", "上传图片发生异常!");
		}
		
		
		AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	/**
	 * 裁剪图片
	 */
	@RequestMapping("/video/cutimg")
	public String cutImg(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		if(!isLogin(req)&&!isAdminLogin(req)){
			return "redirect:/login";
		}
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		double scale = new Double(reqInfo.getParameter("scale")).doubleValue();
		int x1 = Integer.parseInt(reqInfo.getParameter("x1"));
		int y1 = Integer.parseInt(reqInfo.getParameter("y1"));
		int x2 = Integer.parseInt(reqInfo.getParameter("x2"));
		int y2 = Integer.parseInt(reqInfo.getParameter("y2"));
		String srcImgId = reqInfo.getParameter("srcImageId");
		logger.info("srcImgId="+srcImgId);
		int w = x2-x1;
		int h = y2-y1;
		//缩放后，选中区域高宽和起始点
		w = (int)Math.round(w/scale);
		h = (int)Math.round(h/scale);
		int x = (int)Math.round(x1/scale);
		int y = (int)Math.round(y1/scale);
		FileConfig fileConfig = ConfigManager.getInstance().getFileConfig(configService);
		File f = new File(storageService.localImgUrl(srcImgId));
		if(f.exists()){
			try {
				BufferedImage srcImage = ImageIO.read(f);
				String imageType = FileUtil.getImageType(f);
				byte[] cropContent = FileUtil.cropImage(srcImage, x, y, w, h, imageType,fileConfig);
				String cutName = "_1"+srcImgId;
				storageService.uploadImg(new UploadFileInput(cutName, cropContent, true));
				model.addAttribute("cutImgId", cutName);
				model.addAttribute("status", 0);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		AjaxResponseUtil.returnData(res, JSONObject.toJSONString(model));
		return null;
	}
	private static final String STATIC_MOD_SINCE="Fri, 02 Sep 2011 16:00:38 GMT";
	@RequestMapping("/video/downimg")
	public String downImg(HttpServletRequest req,HttpServletResponse rsp,ModelMap model){
		HttpRequestInfo reqInfo = new HttpRequestInfo(req);
		String url = reqInfo.getParameter("img_url");
		byte[] buffer = null;
		if(url!=null){
			File target = new File(storageService.localImgUrl(url));
			try {
				FileInputStream fis = new FileInputStream(target);
				ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
				byte[] b = new byte[1000];
				int n;
				while((n = fis.read(b))!=-1){
					bos.write(b,0,n);
				}
				bos.close();
				fis.close();
				buffer = bos.toByteArray();
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		if(buffer!=null){
			rsp.setHeader("Content-Type", "image/jpg");
			rsp.setHeader("Cache-Control","max-age=604800,public");
			rsp.setHeader("Last-Modified",STATIC_MOD_SINCE);
			rsp.setContentLength(buffer.length);
			try {  
				rsp.getOutputStream().write(buffer);
				if(rsp.getOutputStream()!=null){
					rsp.getOutputStream().close();
				}
			} catch (IOException e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}
	@RequestMapping(value ="/video/clickvideo" ,method = RequestMethod.POST)
	public void clickVideo(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		String id = req.getParameter("id");
		Integer idInt = Integer.valueOf(id);
		videoService.addVideoCNum(idInt);
		if(RandomUtils.nextInt(100)>60){
			storeService.reflashSearchIndex(idInt);
		}
	}
	@RequestMapping(value ="/video/h5clickvideo", method = RequestMethod.POST)
	public void h5ClickVideo(HttpServletRequest req,HttpServletResponse res,ModelMap model){
		String id = req.getParameter("id");
		Integer idInt = Integer.valueOf(id);
		videoService.addH5VideoCNum(idInt);
		storeService.reflashSearchIndex(idInt);
		if(RandomUtils.nextInt(100)>60){
			storeService.reflashSearchIndex(idInt);
		}
	}
}
