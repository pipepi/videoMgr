/**
 * 
 */
package com.aepan.sysmgr.dao.implement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com._21cn.framework.util.PageList;
import com._21cn.framework.util.PageTurn;
import com.aepan.sysmgr.dao.VideoDao;
import com.aepan.sysmgr.dao.rowmapper.VideoRowMapper;
import com.aepan.sysmgr.model.StoreVideo;
import com.aepan.sysmgr.model.Video;
import com.aepan.sysmgr.model.VideoCheck;
import com.aepan.sysmgr.util.DaoUtil;
import com.aepan.sysmgr.util.JSONUtil;

/**
 * 视频
 * @author lanker
 * 2015年8月3日上午11:36:09
 */
@Repository
public class VideoDaoImpl implements VideoDao {
	final Logger logger = LoggerFactory.getLogger(VideoDaoImpl.class);
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Override
	public Video findVideoById(int id) {
		String sql = "select * from t_sysmgr_video where id = ? ";
		
		List<Video> videoList = jdbcTemplate.query(sql, new Object[] {id},new VideoRowMapper());
		 
		 if(videoList.size()>0){
			 return videoList.get(0);
		 }
		 return null;
	}
	@Override
	public void delete(int id) {
		String sql = "delete from t_sysmgr_video where id = ?";
		jdbcTemplate.update(sql, id);
	}
	
	@Override
	public int getVideoCountByUserId(int userId){
		String sql ="select count(1) from t_sysmgr_video where user_id=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{userId}, Integer.class);
	}
	
	/**
	 * 播放器关联视频，视频列表
	 * 排序： 已关联的视频>已发布的视频；然后发布时间，或播放次数排序（默认时间倒序）；
	 */
	@Override
	public PageList<Video> videos4Link(int storeId,int userId,String sortBy,String sortType,int pageNo,int pageSize){
		StringBuffer sql = new StringBuffer();
		   sql.append(" from  t_sysmgr_video a left join  (select store_id,video_id from t_sysmgr_store_video where user_id= ")
		   .append(userId);
		   if(storeId>0){
			   sql.append(" and store_id= ")
			   .append(storeId);
			   
		   }
		   sql.append(") b on a.id = b.video_id ") 
		   .append(" where ") 
		   .append(" a.user_id = ")
		   .append(userId)
		   .append(" ")
		   .append(" and (b.store_id>0 or a.check_state=")
		   .append(VideoCheck.state_上线)
		   .append(") ")
		   .append(" and a.active = 1 ");
		StringBuffer querySql = new StringBuffer();
		querySql.append("select a.* ")
				.append(sql.toString())
		   		.append(" order by b.store_id desc ");
		if(sortBy.equals("updatetime")){
			querySql.append(", a.update_time ");
		}else if(sortBy.equals("cnum")){
			querySql.append(", a.video_cnum+a.h5_video_cnum ");
		}else{}
		querySql.append(sortType);
		querySql.append(" limit ")
		.append((pageNo-1)*pageSize)
		.append(",")
		.append(pageSize)
		.append(" ");
		PageList<Video> list= toPageList(jdbcTemplate.query(querySql.toString(),new VideoRowMapper()));
		list = list==null?new PageList<Video>():list;
		list.setPageTurn(new PageTurn(pageNo, pageSize,countBySql("select count(0) "+sql.toString())));
		return list;
	}
	private int countBySql(String sql){
		int count = (Integer)jdbcTemplate.queryForObject(sql,Integer.class);
		return count;
	}
	@Override
	public PageList<Video> pageList(Map<String, Object> params, int pageNo,
			int pageSize) {
		StringBuffer sql = new StringBuffer();
		int startIndex = (pageNo-1)*pageSize;
		sql.append("select * from t_sysmgr_video where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		condition(sql, params, args);
		if(params.get("sortName") != null){
			String sortName = (String)params.get("sortName");
			String sortOrder = (String)params.get("sortOrder");
			if(sortName.equals("create_time")){
				sql.append(" order by create_time ");
				if(sortOrder.equals("desc")){
					sql.append(" desc ");
				}
			}else if(sortName.equals("update_time")){
				sql.append(" order by update_time ");
				if(sortOrder.equals("desc")){
					sql.append(" desc ");
				}
			}
		}
		sql.append(" limit ?,? ");
		args.add(startIndex);
		args.add(pageSize);
		PageList<Video> list= toPageList(jdbcTemplate.query(sql.toString(),args.toArray(),new VideoRowMapper()));
		list = list==null?new PageList<Video>():list;
		list.setPageTurn(new PageTurn(pageNo, pageSize,pageCount(params)));
		return list;
	}
	@Override
	public int pageCount(Map<String, Object> params){
		StringBuffer sql = new StringBuffer("select count(0) from t_sysmgr_video where 1=1 ");
		List<Object> args = new ArrayList<Object>();
		condition(sql, params, args);
		int count = (Integer)jdbcTemplate.queryForObject(sql.toString(),args.toArray(),Integer.class);
		return count;
	}
	private void doin(StringBuffer sql,List<Object> args,String eqIds){
		String[] items =eqIds.split(",");
		sql.append(" (");
		for(int i=0;i<items.length;i++){
			sql.append("?");
			sql.append(i<items.length-1?",":")");
			args.add(Integer.parseInt(items[i]));
		}
	}
	private void condition(StringBuffer sql,Map<String, Object> params,List<Object> args){
		if(params.get("eqId")!=null){
			sql.append(" and id = ? ");
			args.add(params.get("eqId"));
		}
		if(params.get("eqIds")!=null){
			sql.append(" and id in ");
			doin(sql, args, (String)params.get("eqIds"));
		}
		if(params.get("user")!=null){
			sql.append(" and user_id = ? ");
			args.add(params.get("user"));
		}
		if(params.get("userIds")!=null){
			sql.append(" and user_id in ");
			doin(sql, args, (String)params.get("userIds"));
		}
		if(params.get("checkState")!=null){
			sql.append(" and check_state = ? ");
			args.add(params.get("checkState"));
		}
		if(params.get("active")!=null){
			sql.append(" and active = ? ");
			args.add(params.get("active"));
		}
	}
	private PageList<Video> toPageList(List<Video> list){
		if(list!=null&&!list.isEmpty()){
			PageList<Video> rs = new PageList<Video>();
			for (Video t : list) {
				rs.add(t.escapeHtml());
			}
			return rs;
		}else{
			return null;
		}
	}
	@Override
	public int addVideoWithGenKey(Video v) throws Exception {
		StringBuffer sql = new StringBuffer();
		 sql.append("insert into t_sysmgr_video (")
			.append("user_id,")
			.append("guid,")
			.append("title,")
			.append("descript,")
			.append("video,")
			.append("h5_video,")
			.append("video_size,")
			.append("h5_video_size,")
			.append("video_cnum,")
			.append("h5_video_cnum,")
			.append("upload_asset_id,")
			.append("encode_asset_id,")
			.append("img_max,")
			.append("img_max_414,")
			.append("img_min_301,")
			.append("img_min,")
			.append("storage,")
			.append("type_name,")
			.append("sort_order,")
			.append("active,")
			.append("check_state,")
			.append("check_msg,")
			.append("create_time,")
			.append("update_time) ")
			.append("values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		 Date now = new Date();
		return DaoUtil.executeWithGenKey(jdbcTemplate, sql.toString(), 
				 v.userId,
				 v.guid,
				 v.name,
				 v.desc,
				 v.video,
				 v.h5Video,
				 v.videoSize,
				 v.h5VideoSize,
				 v.videoCnum,
				 v.h5VideoCnum,
				 v.uploadAssetId,
				 v.encodeAssetId,
				 v.imgMax,
				 v.imgMax414,
				 v.imgMin301,
				 v.imgMin,
				 v.storage,
				 v.type,
				 v.sortOrder,
				 v.active,
				 v.checkState,
				 v.checkMsgs==null?"":JSONUtil.toJson(v.checkMsgs),
			     now,
			     now);
	}

	@Override
	public  void addVideoCNum(int id){
		String sql="update t_sysmgr_video set video_cnum=video_cnum+1 where id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}
	
	@Override
	public  void addH5VideoCNum(int id){
		String sql="update t_sysmgr_video set h5_video_cnum=h5_video_cnum+1 where id=?";
		jdbcTemplate.update(sql,new Object[]{id});
	}
	
	
	@Override
	public float getUsedFlowNumByUserId(int userId){
		
		String sql="select sum(video_size*video_cnum+h5_video_size*h5_video_cnum) from videomgr.t_sysmgr_video where  user_id=?";
		
		Float useFlowNum = jdbcTemplate.queryForObject(sql, new Object[]{userId},Float.class);
		
		return useFlowNum;
	}
	
	
	
	@Override
	public void updateVideo(Video v) {
		v.deleteImgPre();
		StringBuffer sql = new StringBuffer();
		sql.append("update t_sysmgr_video set title = ?,descript = ?,video = ?,h5_video=?, video_size=?,h5_video_size=?,upload_asset_id = ? ,"
				+ "encode_asset_id = ? ,img_max = ?,img_max_414 = ?,img_min_301 = ?,img_min = ? ,storage= ?,type_name = ? ,sort_order = ?,"
				+ "active = ?,check_state = ? ,check_msg = ? ,update_time = ?  where id = ? ");
		logger.info("updateVideo  escapeHtml videoId="+v.id+"  v.name"+v.name+"   v.desc"+v.desc+
				"  name  after is ="+StringEscapeUtils.unescapeHtml(v.name)+"    desc after is ="+StringEscapeUtils.unescapeHtml(v.desc));				
		jdbcTemplate.update(sql.toString(), 
				new Object[]{StringEscapeUtils.unescapeHtml(v.name),
							 StringEscapeUtils.unescapeHtml(v.desc),
							 v.video,
							 v.h5Video,
							 v.videoSize,
							 v.h5VideoSize,
							 v.uploadAssetId,
							 v.encodeAssetId,
							 v.imgMax,
							 v.imgMax414,
							 v.imgMin301,
							 v.imgMin,
							 v.storage,
							 v.type,
							 v.sortOrder,
							 v.active,
							 v.checkState,
							 v.checkMsgs==null?"":JSONUtil.toJson(v.checkMsgs),
							 new Date(),
							 v.id});
	}
	@Override
	public void batchInsert(int batchSize, List<StoreVideo> batchList,
			Integer userId) {
		final List<StoreVideo> rlist = batchList;
		jdbcTemplate.batchUpdate(" insert t_sysmgr_store_video(user_id, store_id, video_id) "
                    + " values(?, ?, ?)", new BatchPreparedStatementSetter(){

						@Override
						public int getBatchSize() {
							return rlist.size();
						}

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							StoreVideo sp = (StoreVideo)rlist.get(i);
							ps.setInt(1, sp.getUserId());
							ps.setInt(2, sp.getStoreId());
							ps.setInt(3, sp.getVideoId());
						}
                    });
		
	}

	@Override
	public boolean deleteByUserIdANDStoreId(int userId, int storeId) {
		return jdbcTemplate.update(" delete from t_sysmgr_store_video where user_id = ? "
				+ " and store_id = ?", 
				new Object[]{userId, storeId}) > 0;
		
	}
	@Override
	public void deleteLinkRelationByVideoId(int userId,int videoId){
		jdbcTemplate.update(" delete from t_sysmgr_store_video where user_id = ? "
				+ " and video_id = ?", 
				new Object[]{userId, videoId});
	}
	@Override
	public List<StoreVideo> getStoreVideoList(int userId,int storeId){
		String sql = "select * from t_sysmgr_store_video where user_id = ? and store_id = ? ";
		return jdbcTemplate.query(sql,STORE_VIDEO_HANDDLE,userId,storeId);
	}
	@Override
	public List<StoreVideo> getStoreVideoListByUserId(int userId){
		String sql = "select * from t_sysmgr_store_video where user_id = ? ";
		return jdbcTemplate.query(sql,STORE_VIDEO_HANDDLE,userId);
	}
	@Override
	public List<StoreVideo> getStoreVideoListByVideoId(int videoId){
		String sql = "select * from t_sysmgr_store_video where video_id = ? ";
		return jdbcTemplate.query(sql,STORE_VIDEO_HANDDLE,videoId);
	}
	
	
	@Override
	public int getStoreVideoCountByUserId(int userId){
		String sql = "select count(id) from t_sysmgr_store_video where user_id = ? ";
		return jdbcTemplate.queryForObject(sql,new Object[]{userId},Integer.class);
	}
	
	private static  RowMapper<StoreVideo> STORE_VIDEO_HANDDLE = new RowMapper<StoreVideo>(){
		@Override
		public StoreVideo mapRow(ResultSet rs, int index) throws SQLException {
			StoreVideo sv = new StoreVideo();
			sv.id = rs.getInt("id");
			sv.userId = rs.getInt("user_id");
			sv.storeId = rs.getInt("store_id");
			sv.videoId = rs.getInt("video_id");
			sv.createTime = rs.getTimestamp("create_time");
			return sv;
		}
		
	};
	
	@Override
	public int getStoreClickNum(int storeId){
		
		String sql ="SELECT sum(v.video_cnum+v.h5_video_cnum) as num FROM  t_sysmgr_store_video r, t_sysmgr_video v where r.store_id=? and r.user_id=v.user_id and r.video_id=v.id";
		Integer clickNum = jdbcTemplate.queryForObject(sql,new Object[]{storeId},Integer.class);
		if(clickNum==null){
			clickNum=0;
		}
		return clickNum;
	}
	
	
	
	@Override
	public List<Video> getListByStoreId(int storeId, int userId){
		StringBuffer sb = new StringBuffer(" select v.id, v.user_id, v.guid, v.title, v.descript, ");
				     sb.append(" v.video,v.h5_video,v.video_size,v.h5_video_size,v.video_cnum,v.h5_video_cnum, v.upload_asset_id, v.encode_asset_id, v.img_max, ");
				     sb.append(" v.img_max_414,v.img_min_301,v.img_min,v.storage, v.type_name, v.sort_order, v.active, v.check_state, ");
				     sb.append(" v.check_msg, v.create_time, v.update_time from ");
				     sb.append(" t_sysmgr_store_video r, t_sysmgr_video v ");
		             sb.append(" where r.user_id = ? and r.store_id = ? and r.video_id = v.id  ");
		             sb.append(" and v.active = 1 and check_state = 2 ");
		return jdbcTemplate.query(sb.toString(), new Object[]{userId, storeId}, new VideoRowMapper());
	}
	
	
	
}
