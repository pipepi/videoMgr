ALTER TABLE `t_sysmgr_package_stat`
	ADD COLUMN `used_flow_count_time` DATETIME NULL DEFAULT '1978-01-01 08:00:00' COMMENT '流量最后统计时间' AFTER `used_flow_num`;