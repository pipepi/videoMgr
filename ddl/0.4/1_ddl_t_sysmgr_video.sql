ALTER TABLE `t_sysmgr_video`
	CHANGE COLUMN `video` `video` VARCHAR(500) NULL DEFAULT '' COMMENT '视频地址' AFTER `h5_video_cnum`,
	CHANGE COLUMN `h5_video` `h5_video` VARCHAR(1000) NULL DEFAULT '' COMMENT '视频h5地址' AFTER `video`,
	CHANGE COLUMN `encode_asset_id` `encode_asset_id` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '视频微软云编码id' AFTER `upload_asset_id`;
