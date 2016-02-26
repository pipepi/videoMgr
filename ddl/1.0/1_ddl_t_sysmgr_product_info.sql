ALTER TABLE `t_sysmgr_product_info`
	CHANGE COLUMN `partner_product_id` `partner_product_id` INT(11) NOT NULL DEFAULT '0' COMMENT '第三方后台产品id' AFTER `Id`;