drop table if exists `t_sysmgr_flow_log`; 
CREATE TABLE `t_sysmgr_flow_log` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`user_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '用户id',
	`store_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '关联播放器',
	`video_id` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '视频id',
	`flow` FLOAT UNSIGNED NOT NULL DEFAULT '0.0' COMMENT '流量',
	`create_time` TIMESTAMP NOT NULL DEFAULT '1971-01-01 00:00:00' COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='流量日志明细'
COLLATE='gbk_chinese_ci'
ENGINE=InnoDB;
