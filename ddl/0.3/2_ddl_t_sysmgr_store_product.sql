ALTER TABLE `t_sysmgr_store_product`
	ADD COLUMN `product_name` VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '商品名称，用于搜索功能' AFTER `product_id`,
	ADD COLUMN `product_desc` VARCHAR(2024) NOT NULL DEFAULT '' COMMENT '商品描述,用于搜索功能' AFTER `product_name`,
	ADD COLUMN `product_type` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '商品类型，用于搜索功能' AFTER `product_desc`,
	ADD INDEX `search` (`product_name`, `product_desc`, `product_type`);
ALTER TABLE `t_sysmgr_store_product`
	CHANGE COLUMN `product_name` `product_name` VARCHAR(1024) NULL DEFAULT '' COMMENT '商品名称，用于搜索功能' AFTER `product_id`,
	CHANGE COLUMN `product_desc` `product_desc` VARCHAR(2024) NULL DEFAULT '' COMMENT '商品描述,用于搜索功能' AFTER `product_name`,
	CHANGE COLUMN `product_type` `product_type` VARCHAR(128) NULL DEFAULT '' COMMENT '商品类型，用于搜索功能' AFTER `product_desc`;
ALTER TABLE `t_sysmgr_store_product`
	ADD COLUMN `product_attrs` VARCHAR(2048) NULL DEFAULT '' COMMENT '商品属性' AFTER `product_type`,
	ADD COLUMN `product_price` FLOAT UNSIGNED NULL DEFAULT '0.0' COMMENT '价格' AFTER `product_attrs`;