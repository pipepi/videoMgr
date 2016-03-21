#用户帐号信息
select * from t_sysmgr_user where id = 38;
#套餐信息
select * from t_sysmgr_package_stat where user_id = 38;
#流量购买信息
select * from t_sysmgr_flow where user_id = 38;
#流量统计信息
select * from t_sysmgr_flow_log where user_id = 38;
#视频信息
select * from t_sysmgr_video where user_id = 38;
#播放器信息
select * from t_sysmgr_store where user_id = 38;
#播放器关联视频信息
select * from t_sysmgr_store_video where user_id = 38;
#播放器关联商品信息
select * from t_sysmgr_store_product where user_id = 38;
#订单信息
select * from t_sysmgr_product_order where buyers_id = 38;
#商品临时信息
select * from t_sysmgr_product_info where user_id = 38;