ALTER TABLE `t_sysmgr_user` ADD COLUMN `qqid_key` VARCHAR(100) NOT NULL COMMENT '播放器需要调用qq应用' AFTER `partner_account_name`;

ALTER TABLE `t_sysmgr_user` MODIFY COLUMN `email` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '邮箱地址';
ALTER TABLE `t_sysmgr_user` MODIFY COLUMN `qqid_key` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '播放器需要调用qq应用';
