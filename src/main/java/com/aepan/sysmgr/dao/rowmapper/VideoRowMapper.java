/**
 * 
 */
package com.aepan.sysmgr.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.core.RowMapper;

import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.VideoCheck;
import com.aepan.sysmgr.util.JSONUtil;

/**
 * @author Administrator
 * 2015年8月3日上午11:16:52
 */
public class VideoRowMapper  implements RowMapper<Video>{

	@Override
	public Video mapRow(ResultSet rs, int index) throws SQLException {
		Video v = new Video();
		v.id = rs.getInt("id");
		v.userId = rs.getInt("user_id");
		v.guid = rs.getString("guid");
		v.name = rs.getString("title");
		v.desc = rs.getString("descript");
		v.video=rs.getString("video");
		if(rs.getString("h5_video")!=null){
			v.h5Video=rs.getString("h5_video").replaceAll("&amp;", "&");
		}
		v.videoSize=rs.getFloat("video_size");
		v.h5VideoSize=rs.getFloat("h5_video_size");
		v.videoCnum= rs.getLong("video_cnum");
		v.h5VideoCnum=rs.getLong("h5_video_cnum");
		v.uploadAssetId = rs.getString("upload_asset_id");
		v.encodeAssetId = rs.getString("encode_asset_id");
		v.imgMax=rs.getString("img_max");
		v.imgMax414=rs.getString("img_max_414");
		v.imgMin301=rs.getString("img_min_301");
		v.imgMin=rs.getString("img_min");
		v.setStorage(rs.getInt("storage"));
		v.type=rs.getInt("type_name");
		v.sortOrder=rs.getInt("sort_order");
		v.active=rs.getBoolean("active");
		v.checkState = rs.getInt("check_state");
		String msg = rs.getString("check_msg");
		if(msg!=null&&!msg.trim().isEmpty()){
			v.checkMsgs = JSONUtil.fromJsonList(msg, VideoCheck.class);
			if(v.checkMsgs!=null&&!v.checkMsgs.isEmpty()){
				String cms = "";
				for (VideoCheck vc : v.checkMsgs) {
					// 状态 原因 时间
					String stateName = "";
					if(vc.state == 3){
						stateName = "[不通过]";
					}else if(vc.state == 4){
						stateName = "[违规下架]";
					}else{
						stateName = "[其他]";
					} 
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					cms+= stateName+vc.msg+"  "+sdf.format(vc.createTime)+"  "+"\\r\\n";
				}
				v.checkMsgStr = cms;
			}
		}
		v.createTime=rs.getTimestamp("create_time");
		v.modificationTime=rs.getTimestamp("update_time");
		return v;
	}

}
