ALTER TABLE `t_sysmgr_store` ADD INDEX `idx_uid`(`user_id`);
ALTER TABLE `t_sysmgr_flow` ADD INDEX `idx_uid`(`user_id`);
ALTER TABLE `t_sysmgr_package_stat` ADD INDEX `idx_uid`(`user_id`);
ALTER TABLE `t_sysmgr_product_order` ADD INDEX `idx_oid`(`order_id`);
ALTER TABLE `t_sysmgr_store_product` ADD INDEX `idx_sid`(`store_id`);
ALTER TABLE `t_sysmgr_user` ADD INDEX `idx_pid`(`partner_account_id`, `partner_id`);