ALTER TABLE `t_sysmgr_store`
	CHANGE COLUMN `logo_url` `logo_url` VARCHAR(500) NULL DEFAULT NULL COMMENT '店铺logo图' AFTER `user_id`,
	ADD COLUMN `logo_url_301` VARCHAR(500) NULL DEFAULT NULL COMMENT '301px宽度logo' AFTER `logo_url`,
	CHANGE COLUMN `max_logo_url` `max_logo_url` VARCHAR(500) NULL DEFAULT NULL COMMENT '大图片' AFTER `logo_url_301`,
	ADD COLUMN `max_logo_url_414` VARCHAR(500) NULL DEFAULT NULL COMMENT '414px宽度logo' AFTER `max_logo_url`;