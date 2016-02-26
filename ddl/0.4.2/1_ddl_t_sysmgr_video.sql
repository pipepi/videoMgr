ALTER TABLE `t_sysmgr_video`
	ADD COLUMN `storage` TINYINT(3) UNSIGNED NULL DEFAULT '1' COMMENT '存储平台  0：本地；1：微软云；2：金山云；' AFTER `img_min`;