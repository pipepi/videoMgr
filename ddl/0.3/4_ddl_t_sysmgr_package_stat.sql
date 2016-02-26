ALTER TABLE `t_sysmgr_package_stat` ADD COLUMN `send_notify` INTEGER UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否发送邮件通知' AFTER `duration`;


ALTER TABLE `t_sysmgr_package_stat` MODIFY COLUMN `flow_num` FLOAT NOT NULL DEFAULT 0 COMMENT '开通流量数',
 ADD COLUMN `used_flow_num` FLOAT NOT NULL DEFAULT 0 COMMENT '使用流量数' AFTER `flow_num`;
 
 
 ALTER TABLE `t_sysmgr_package` MODIFY COLUMN `flow_num` float UNSIGNED NOT NULL DEFAULT 0 COMMENT '流量';
