# --------------------------------------------------------
# Host:                         mysqlservices-sha.chinacloudapp.cn
# Server version:               5.5.39
# Server OS:                    Win64
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2015-10-22 17:16:47
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

# Dumping structure for table video9cooo.t_sysmgr_config
DROP TABLE IF EXISTS `t_sysmgr_config`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_config` (
  `id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '配置id',
  `name` varchar(50) DEFAULT NULL COMMENT '配置名称',
  `config` text COMMENT 'json配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置信息';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_flow
DROP TABLE IF EXISTS `t_sysmgr_flow`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_flow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '流水id',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `flow_num` int(10) unsigned NOT NULL COMMENT '开通流量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '1979-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=gbk COMMENT='流量';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_operation
DROP TABLE IF EXISTS `t_sysmgr_operation`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shortcut` int(11) DEFAULT NULL COMMENT '操作简称',
  `name` varchar(200) DEFAULT NULL COMMENT '操作名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作定义表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_package
DROP TABLE IF EXISTS `t_sysmgr_package`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `name` varchar(255) NOT NULL COMMENT '套餐名字',
  `package_type` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '套餐类型',
  `player_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '播放器数量',
  `video_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '视频数量',
  `product_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '产品数量',
  `price` float unsigned NOT NULL DEFAULT '0' COMMENT '价格',
  `duration` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '周期时长（单位：月）',
  `flow_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '流量',
  `month_flow_price` float unsigned NOT NULL DEFAULT '0' COMMENT '没有流量费用',
  `create_time` timestamp NOT NULL DEFAULT '1978-01-01 08:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '1978-01-01 08:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_package_rule
DROP TABLE IF EXISTS `t_sysmgr_package_rule`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_package_rule` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '规则名',
  `package_id` int(11) DEFAULT NULL COMMENT '关联package_id',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '规则类型 0、无限制；1、次数限制',
  `product_type` int(11) NOT NULL DEFAULT '0' COMMENT '产品规则属性0、智能电商；1、视频、2、产品；3、EXT',
  `times` int(11) NOT NULL DEFAULT '0' COMMENT '当限制次数时，该字段生效',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '规则创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='套餐规则';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_package_stat
DROP TABLE IF EXISTS `t_sysmgr_package_stat`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_package_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `package_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '套餐id',
  `player_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用的播放器数量',
  `video_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用的视频数量',
  `product_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '使用的产品数量',
  `flow_num` float NOT NULL DEFAULT '0' COMMENT '使用流量（单位M）',
  `duration` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '时长',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `create_time` timestamp NOT NULL DEFAULT '1978-01-01 08:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '1978-01-01 08:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户套餐状态';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_privilege
DROP TABLE IF EXISTS `t_sysmgr_privilege`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `resource_id` int(11) DEFAULT NULL COMMENT '资源id',
  `operation_id` int(11) DEFAULT NULL COMMENT '操作id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `operation_id` (`operation_id`),
  KEY `resource_id` (`resource_id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_product_info
DROP TABLE IF EXISTS `t_sysmgr_product_info`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_product_info` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `partner_product_id` int(11) NOT NULL DEFAULT '0' COMMENT '第三方后台产品id',
  `name` varchar(255) DEFAULT NULL COMMENT '产品名',
  `code` varchar(255) DEFAULT NULL COMMENT '产品编码',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '产品拥有者ID',
  `category_id` int(11) NOT NULL DEFAULT '0' COMMENT '分类Id',
  `category_path` varchar(255) DEFAULT NULL COMMENT '分类路径',
  `brand_id` int(11) DEFAULT '0' COMMENT '品牌类型',
  `short_desciption` varchar(255) DEFAULT NULL COMMENT '简短广告词',
  `photo_url1` varchar(255) DEFAULT NULL COMMENT '产品图片1',
  `photo_url2` varchar(255) DEFAULT NULL COMMENT '产品图片2',
  `photo_url3` varchar(255) DEFAULT NULL COMMENT '产品图片3',
  `photo_url4` varchar(255) DEFAULT NULL COMMENT '产品图片4',
  `photo_url5` varchar(255) DEFAULT NULL COMMENT '产品图片5',
  `type` int(11) DEFAULT '0' COMMENT '产品类型',
  `store_id` int(11) NOT NULL DEFAULT '0' COMMENT '关联店铺ID',
  `mark_price` int(11) DEFAULT '0' COMMENT '市场价',
  `price` int(11) DEFAULT '0' COMMENT '产品价格',
  `attr_color` varchar(255) DEFAULT NULL COMMENT '颜色属性',
  `attr_size` varchar(255) DEFAULT NULL COMMENT '大小属性',
  `min_sale_price` int(11) NOT NULL DEFAULT '0' COMMENT '最小销售价',
  `hasSKU` varchar(255) DEFAULT NULL COMMENT '产品SKU',
  `visti_count` int(11) NOT NULL DEFAULT '0',
  `sale_count` int(11) NOT NULL DEFAULT '0' COMMENT '销售量',
  `freight_template_id` int(11) NOT NULL DEFAULT '0' COMMENT '运费模版',
  `weight` int(11) DEFAULT '0' COMMENT '产品重量，单位为KG',
  `volume` float(4,2) NOT NULL DEFAULT '0.00' COMMENT '体积',
  `reserve` int(11) DEFAULT '0' COMMENT '产品库存',
  `status` int(11) DEFAULT '1' COMMENT '产品销售状态，0、注销；1、在线',
  `audit_status` int(11) NOT NULL DEFAULT '0' COMMENT '审核状态；0、未审核，1、已审核',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `ext` varchar(255) DEFAULT NULL COMMENT '产品扩展',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_product_order
DROP TABLE IF EXISTS `t_sysmgr_product_order`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_product_order` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `channel_id` varchar(255) DEFAULT '0' COMMENT '交易流水号',
  `order_id` varchar(255) DEFAULT '0' COMMENT '订单ID',
  `buyers_id` int(20) DEFAULT '0' COMMENT '买家ID',
  `buyers_name` varchar(255) DEFAULT NULL COMMENT '买家名字',
  `seller_id` int(11) DEFAULT '0' COMMENT '卖家ID',
  `seller_name` varchar(255) DEFAULT NULL COMMENT '卖家名',
  `product_id` varchar(1000) DEFAULT NULL COMMENT '（产品SKU）遍历',
  `product_name` varchar(1000) DEFAULT NULL COMMENT '产品名',
  `quantitys` varchar(800) DEFAULT NULL COMMENT '（数量）遍历',
  `price` float(12,2) NOT NULL DEFAULT '0.00' COMMENT '订单金额',
  `amount` int(11) NOT NULL DEFAULT '0' COMMENT '产品数量',
  `to_province` varchar(60) DEFAULT NULL COMMENT '省',
  `to_city` varchar(60) DEFAULT NULL COMMENT '市',
  `to_area` varchar(60) DEFAULT NULL COMMENT '区',
  `to_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `to_mail` varchar(255) DEFAULT NULL COMMENT '收货邮箱',
  `to_zip_code` varchar(40) DEFAULT NULL COMMENT '邮政编码',
  `to_mobile` varchar(50) DEFAULT NULL COMMENT '收货人手机号',
  `back_mobile` varchar(50) DEFAULT NULL COMMENT '退货手机号',
  `back_mail` varchar(50) DEFAULT NULL COMMENT '退货邮箱',
  `back_address` varchar(255) DEFAULT NULL COMMENT '退货地址',
  `attr_color` varchar(255) DEFAULT NULL COMMENT '颜色属性',
  `attr_size` varchar(255) DEFAULT NULL COMMENT '大小属性',
  `order_status` int(10) NOT NULL DEFAULT '0' COMMENT '支付状态 0、未支付；1、支付；2、完成；3、已关闭',
  `buyers_delete` tinyint(4) unsigned DEFAULT '0' COMMENT '买家逻辑删除 0:未删除 1:已删除',
  `deliver_status` int(10) NOT NULL DEFAULT '0' COMMENT '送货状态 0、未送货；1、已送货未到达；2、已收货；3退货；4、延期',
  `pay_type` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '支付类型',
  `pay_for` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '付款原因(区别套餐还是订单)',
  `logistics_num` varchar(255) DEFAULT NULL COMMENT '物流单号',
  `logistics_company` varchar(255) DEFAULT '0' COMMENT '物流公司',
  `attr` varchar(1000) DEFAULT NULL COMMENT '属性，遍历成str插入',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `cannel_time` datetime DEFAULT NULL COMMENT '撤单时间',
  `wx_prepay_id` varchar(100) DEFAULT NULL COMMENT '微信预支付订单号',
  `wxcode_img_url` varchar(100) DEFAULT NULL COMMENT '微信支付图片url',
  `wxcode_update_time` datetime DEFAULT NULL COMMENT '微信支付码更新时间',
  `out_order_id` varchar(100) DEFAULT NULL COMMENT '支付方订单号',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_product_type
DROP TABLE IF EXISTS `t_sysmgr_product_type`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_product_type` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '类型名',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品类型';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_resource
DROP TABLE IF EXISTS `t_sysmgr_resource`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '资源id',
  `p_id` int(11) DEFAULT NULL COMMENT '节点父类id',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '资源状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源管理表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_role
DROP TABLE IF EXISTS `t_sysmgr_role`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `type` int(11) DEFAULT NULL COMMENT '角色类别(2、超级管理员，1、管理员；0、普通用户)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色管理表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_sms
DROP TABLE IF EXISTS `t_sysmgr_sms`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_sms` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '类型(1:验证码)',
  `phone_no` varchar(50) NOT NULL DEFAULT '' COMMENT '短信接收手机号码',
  `content` varchar(1000) NOT NULL DEFAULT '' COMMENT '短信内容',
  `send_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信发出记录表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_stat
DROP TABLE IF EXISTS `t_sysmgr_stat`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_stat` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '事件名',
  `type` int(11) DEFAULT '0' COMMENT '事件类型 1、video点击；2、video分享；3、video流量；4、商品点击；5、商品分享',
  `param_id` varchar(255) DEFAULT NULL COMMENT 'param的id',
  `content` varchar(255) DEFAULT NULL COMMENT '事件内容',
  `ext` varchar(255) DEFAULT NULL COMMENT '扩展字段',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='统计数据';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_store
DROP TABLE IF EXISTS `t_sysmgr_store`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_store` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '店铺名字',
  `description` varchar(500) DEFAULT NULL COMMENT '店铺描述',
  `share_content` varchar(400) DEFAULT NULL COMMENT '分享内容',
  `inner_code` varchar(4000) DEFAULT NULL COMMENT '内嵌代码',
  `type` varchar(255) DEFAULT NULL COMMENT '播放器类别',
  `private_dns` varchar(400) DEFAULT NULL COMMENT '个性化域名',
  `com_address` varchar(255) DEFAULT NULL COMMENT '公司地址',
  `com_tele` varchar(255) DEFAULT '' COMMENT '公司电话',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '商铺拥有者卖家ID',
  `logo_url` varchar(255) DEFAULT NULL COMMENT '店铺logo图',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '店铺状态 0、离线；1、在线；2、审核',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='店铺';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_store_product
DROP TABLE IF EXISTS `t_sysmgr_store_product`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_store_product` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '拥有者Id',
  `store_id` int(11) NOT NULL DEFAULT '0' COMMENT '商铺id',
  `product_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品商铺关联表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_store_video
DROP TABLE IF EXISTS `t_sysmgr_store_video`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_store_video` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '拥有者Id',
  `store_id` int(11) NOT NULL DEFAULT '0' COMMENT '商铺id',
  `video_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频商铺关联表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_user
DROP TABLE IF EXISTS `t_sysmgr_user`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(200) DEFAULT NULL COMMENT '用户名',
  `partner_account_name` varchar(255) DEFAULT NULL COMMENT '合作方账号',
  `partner_mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(200) NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(200) NOT NULL DEFAULT '' COMMENT '邮箱地址',
  `role_id` int(11) DEFAULT '2' COMMENT '角色id',
  `package_id` int(11) NOT NULL DEFAULT '0' COMMENT '套餐类型',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '帐号状态 -1、停用；0审核；1、可用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `partner_account_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '合作方账户Id',
  `partner_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '合作方id',
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理表';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_user_address
DROP TABLE IF EXISTS `t_sysmgr_user_address`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_user_address` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(12) DEFAULT NULL COMMENT '关联买家用户ID',
  `to_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户收货历史地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='买家地址';

# Data exporting was unselected.


# Dumping structure for table video9cooo.t_sysmgr_video
DROP TABLE IF EXISTS `t_sysmgr_video`;
CREATE TABLE IF NOT EXISTS `t_sysmgr_video` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '上传卖家id',
  `guid` varchar(36) NOT NULL DEFAULT '' COMMENT 'guid 32位标识码',
  `title` varchar(100) NOT NULL DEFAULT '' COMMENT '名称',
  `descript` varchar(1000) NOT NULL DEFAULT '' COMMENT '视频简介',
  `video_size` float(20,0) unsigned NOT NULL DEFAULT '0' COMMENT '流文件大小（单位：字节）',
  `h5_video_size` float(20,0) unsigned NOT NULL DEFAULT '0' COMMENT 'h5流文件大小（单位：字节）',
  `video_cnum` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频观看次数',
  `h5_video_cnum` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT 'h5视频观看次数',
  `video` varchar(500) DEFAULT '' COMMENT '视频地址',
  `h5_video` varchar(1000) DEFAULT '' COMMENT '视频h5地址',
  `upload_asset_id` varchar(64) NOT NULL DEFAULT '' COMMENT '视频微软云id',
  `encode_asset_id` varchar(64) NOT NULL DEFAULT '' COMMENT '视频微软云编码id',
  `img_max` varchar(500) NOT NULL DEFAULT '' COMMENT '大图',
  `img_min` varchar(500) NOT NULL DEFAULT '' COMMENT '小图',
  `type_name` tinyint(3) unsigned DEFAULT '0' COMMENT '类型',
  `sort_order` smallint(5) unsigned DEFAULT '0' COMMENT '视频排序值',
  `active` tinyint(3) unsigned DEFAULT '1' COMMENT '是否激活',
  `check_state` tinyint(3) unsigned DEFAULT '0' COMMENT '审核状态 1:待审核;2:上线;3:未通过;4:下线',
  `check_msg` varchar(1000) DEFAULT '' COMMENT '审核消息',
  `create_time` timestamp NULL DEFAULT '1971-01-01 08:00:00' COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT '1971-01-01 08:00:00' COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频';

# Data exporting was unselected.
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
