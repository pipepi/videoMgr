# --------------------------------------------------------
# Host:                         mysqlservices-sha.chinacloudapp.cn
# Server version:               5.5.39
# Server OS:                    Win64
# HeidiSQL version:             6.0.0.3603
# Date/time:                    2015-10-22 17:24:47
# --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
# Dumping data for table video9cooo.t_sysmgr_config: ~4 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_config` DISABLE KEYS */;
INSERT INTO `t_sysmgr_config` (`id`, `name`, `config`) VALUES
	(1, '文件上传配置', '{"IM4JAVA_TOOLPATH":"/usr/local/bin/gm","IMG_AZURE_DIR":"videoimg","IMG_AZURE_PAY_DIR":"payimg","IMG_AZURE_PRE":"https://pic9cooo.blob.core.chinacloudapi.cn","IMG_LOCAL_DIR":"/home/aztsay/imgtmpfile/","MAX_IMGS_SIZE":3145728,"MAX_IMG_WIDTH":400,"MAX_VIDEO_SIZE":209715200,"MIN_IMG_WIDTH":200,"PAY_NOTIFY_URL":"http://app-9cooo-001.chinacloudapp.cn","PARTNER_INDEX_URL":"http://www.kaimai8.com/SellerAdmin","GET_PARTNER_PRODUCT_PAGE_URL":"http://mall-9cooo-01.chinacloudapp.cn/SellerAdmin/product/ListProduct","GET_PARTNER_PRODUCT_DETAIL_URL":"http://mall-9cooo-01.chinacloudapp.cn/VideoInterface/ListProductDetail"}'),
	(2, '短信配置', '{"ac":"1001@501176370003","authkey":"DF007ADB8A1C5B02A9BF27C368FB945A","cgid":"5278","csid":"5550","oth_ac":"1001@501176370004","oth_authkey":"E723ED871F6BFFDABB16F78D8AA1782C","oth_cgid":"5279","oth_csid":"5553","oth_url":"http://smsapi.c123.cn/OpenPlatform/OpenApi","url":"http://smsapi.c123.cn/OpenPlatform/OpenApi"}'),
	(3, '邮件配置', '{"apiKey":"YJLNcPHzXPCwryJ1","apiUser":"9cooo_test_ouOYFL","from":"mail@9cooo.com","fromname":"9库网","url":"http://sendcloud.sohu.com/webapi/"}'),
	(4, '合作方地址配置', '{"ROOT_PATH_KAIMAI8":"http://www.kaimai8.com","PARTNER_INDEX_URL":"http://www.kaimai8.com/SellerAdmin","GET_PARTNER_PRODUCT_PAGE_URL":"http://mall-9cooo-01.chinacloudapp.cn/SellerAdmin/product/ListProduct","GET_PARTNER_PRODUCT_DETAIL_URL":"http://mall-9cooo-01.chinacloudapp.cn/VideoInterface/ListProductDetail","GET_PARTNER_PRODUCT_SKUSINFO":"http://mall-9cooo-01.chinacloudapp.cn/Product/GetSKUsInfo?","UPDATE_PRODUCT_STOKE":"http://mall-9cooo-01.chinacloudapp.cn/Product/updateStock?"}');
/*!40000 ALTER TABLE `t_sysmgr_config` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_package: ~9 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_package` DISABLE KEYS */;
INSERT INTO `t_sysmgr_package` (`id`, `name`, `package_type`, `player_num`, `video_num`, `product_num`, `price`, `duration`, `flow_num`, `month_flow_price`, `create_time`, `update_time`) VALUES
	(1, '免月费套餐1006', 1, 5, 3, 10, 0, 12, 1, 0.01, '2015-08-10 23:59:34', '2015-10-21 03:31:16'),
	(2, '基本套餐', 1, 3, 5, 3, 0.01, 12, 1, 0, '2015-08-12 22:57:19', '2015-10-21 03:31:20'),
	(3, '超级套餐', 1, 50, 50, 50, 0.001, 24, 1, 0.015, '2015-08-14 06:59:07', '2015-10-22 07:47:09'),
	(17, '无名套餐', 2, 1000, 1000, 1000, 0, 12, 100, 0, '2015-09-30 15:11:21', '2015-09-30 15:13:25'),
	(18, '为什么套餐不可以删除', 2, 0, 99, 99, 0, 12, 200, 0.01, '2015-09-30 16:11:21', '2015-10-12 02:47:20'),
	(19, '这的确是个问题', 2, 3000000, 7, 0, 0, 1, 300, 0.01, '2015-09-30 16:13:57', '2015-10-08 09:53:20'),
	(20, '个', 2, 0, 0, 0, 0.01, 0, 400, 0.01, '2015-09-30 17:25:28', '2015-10-10 03:55:40'),
	(21, '测试套餐1003', 2, 5, 5, 10, 0.01, 6, 1000, 2000, '2015-10-03 13:36:50', '2015-10-10 08:19:54'),
	(22, '新建套餐1006', 2, 10, 10, 100, 0, 1, 0, 0, '2015-10-06 09:15:08', '2015-10-10 08:18:17');
/*!40000 ALTER TABLE `t_sysmgr_package` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_package_rule: ~5 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_package_rule` DISABLE KEYS */;
INSERT INTO `t_sysmgr_package_rule` (`Id`, `name`, `package_id`, `type`, `product_type`, `times`, `create_time`) VALUES
	(1, '视频播放次数', 1, 1, 0, 9, '2015-08-10 23:59:49'),
	(2, '商品上传', 1, 0, 0, 0, '2015-08-11 00:00:22'),
	(3, '产品数量', 1, 1, 0, 14, '2015-08-15 02:42:05'),
	(4, '视频电商', 3, 1, 0, 10, '2015-08-26 11:47:10'),
	(5, '浏览次数', 1, 1, 0, 20, '2015-09-04 11:27:56');
/*!40000 ALTER TABLE `t_sysmgr_package_rule` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_privilege: ~47 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_privilege` DISABLE KEYS */;
INSERT INTO `t_sysmgr_privilege` (`id`, `resource_id`, `operation_id`, `role_id`, `create_time`) VALUES
	(1, 1, 1, 1, '2015-04-16 03:44:27'),
	(2, 2, 1, 1, '2015-04-16 03:44:27'),
	(3, 3, 1, 1, '2015-04-16 03:44:27'),
	(4, 4, 1, 1, '2015-04-16 03:44:27'),
	(5, 5, 1, 1, '2015-04-16 03:44:27'),
	(6, 6, 1, 1, '2015-04-16 03:44:27'),
	(7, 7, 1, 1, '2015-07-05 17:32:52'),
	(8, 8, 1, 1, '2015-07-05 17:33:01'),
	(9, 9, 1, 1, '2015-07-05 17:34:40'),
	(10, 13, 1, 1, '2015-07-23 20:04:05'),
	(11, 14, 1, 1, '2015-07-23 20:13:14'),
	(12, 15, 1, 1, '2015-07-23 20:13:27'),
	(13, 16, 1, 1, '2015-07-31 00:06:12'),
	(14, 17, 1, 1, '2015-07-31 00:06:17'),
	(15, 18, 1, 1, '2015-07-31 00:06:21'),
	(16, 19, 1, 1, '2015-07-31 00:06:30'),
	(17, 20, 1, 1, '2015-07-31 00:06:37'),
	(18, 21, 1, 1, '2015-07-31 00:06:54'),
	(19, 22, 1, 1, '2015-07-31 00:06:58'),
	(20, 23, 1, 1, '2015-07-31 00:07:01'),
	(21, 24, 1, 1, '2015-07-31 00:07:05'),
	(22, 25, 1, 1, '2015-07-31 00:07:09'),
	(23, 26, 1, 1, '2015-07-31 00:07:22'),
	(24, 1, 1, 0, '2015-08-07 00:45:46'),
	(25, 2, 1, 0, '2015-08-07 00:45:52'),
	(26, 27, 1, 1, '2015-08-07 23:35:39'),
	(28, 3, 1, 3, '2015-08-07 23:42:34'),
	(29, 3, 2, 3, '2015-08-07 23:42:34'),
	(30, 27, 1, 3, '2015-08-07 23:42:34'),
	(32, 1, 1, 4, '2015-08-09 01:11:02'),
	(33, 1, 2, 4, '2015-08-09 01:11:02'),
	(34, 2, 1, 4, '2015-08-09 01:11:02'),
	(35, 2, 2, 4, '2015-08-09 01:11:02'),
	(36, 3, 1, 4, '2015-08-09 01:11:02'),
	(37, 3, 2, 4, '2015-08-09 01:11:02'),
	(38, 4, 1, 4, '2015-08-09 01:11:02'),
	(39, 4, 2, 4, '2015-08-09 01:11:02'),
	(40, 5, 1, 4, '2015-08-09 01:11:02'),
	(41, 5, 2, 4, '2015-08-09 01:11:02'),
	(42, 1, 1, 4, '2015-08-09 01:12:11'),
	(43, 1, 2, 4, '2015-08-09 01:12:11'),
	(44, 2, 1, 4, '2015-08-09 01:12:11'),
	(45, 2, 2, 4, '2015-08-09 01:12:11'),
	(46, 3, 1, 4, '2015-08-09 01:12:11'),
	(47, 3, 2, 4, '2015-08-09 01:12:11'),
	(48, 4, 1, 4, '2015-08-09 01:12:11'),
	(49, 4, 2, 4, '2015-08-09 01:12:11');
/*!40000 ALTER TABLE `t_sysmgr_privilege` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_resource: ~24 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_resource` DISABLE KEYS */;
INSERT INTO `t_sysmgr_resource` (`id`, `p_id`, `url`, `name`, `status`, `create_time`) VALUES
	(1, 3, '/resource/list', '资源管理', 1, '2015-06-28 19:52:23'),
	(2, 3, '/operation/list', '操作管理', 1, '2015-06-28 19:52:30'),
	(3, 0, '', '系统', 1, '2015-06-28 19:52:07'),
	(4, 3, '/role/list', '角色管理', 1, '2015-06-28 19:52:14'),
	(5, 3, '/privilege/list', '权限管理', 0, '2015-08-07 05:17:22'),
	(6, 3, '/user/list', '用户管理', 1, '2015-06-28 19:52:42'),
	(7, 0, '', '商品管理', 1, '2015-07-30 23:54:55'),
	(8, 7, '/product/list', '商品列表', 1, '2015-07-31 00:35:55'),
	(9, 7, '/product/publish', '商品发布', 1, '2015-07-31 00:36:02'),
	(13, 7, '/video/mgr', '视频管理', 1, '2015-07-31 00:36:12'),
	(14, 0, '', '店铺管理', 1, '2015-07-31 00:00:18'),
	(15, 0, '', '广告管理', 1, '2015-07-31 00:08:20'),
	(16, 3, '/package/list', '套餐管理', 1, '2015-08-14 06:34:25'),
	(17, 7, '/productType/list', '商品归类管理', 1, '2015-08-14 02:32:59'),
	(18, 14, '/store/list', '店铺信息管理', 1, '2015-07-31 00:11:42'),
	(19, 14, '/store/message', '店铺内容管理', 1, '2015-07-31 00:15:25'),
	(20, 0, '', '订单管理', 1, '2015-07-31 00:00:12'),
	(21, 20, '/order/list', '订单列表', 1, '2015-07-30 23:59:16'),
	(22, 0, '', '模版管理', 1, '2015-07-31 00:00:50'),
	(23, 22, '/logistics/demo', '物流模版', 1, '2015-07-31 00:01:42'),
	(24, 22, '/customer/service', '售后服务模版', 1, '2015-07-31 00:02:42'),
	(25, 0, '', '帮助', 1, '2015-07-31 00:08:26'),
	(26, 15, '/adv/list/', '广告列表', 1, '2015-08-07 04:30:53'),
	(27, 14, '/store/videolist', '播放器列表', 1, '2015-08-23 21:28:53');
/*!40000 ALTER TABLE `t_sysmgr_resource` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_role: ~4 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_role` DISABLE KEYS */;
INSERT INTO `t_sysmgr_role` (`id`, `name`, `type`, `create_time`) VALUES
	(1, 'admin', 1, '2015-04-16 03:09:59'),
	(2, 'test', 0, '2015-08-07 05:15:56'),
	(3, 'new merchant', 0, '2015-08-07 23:38:17'),
	(4, '44545454545', 0, '2015-08-09 01:09:16');
/*!40000 ALTER TABLE `t_sysmgr_role` ENABLE KEYS */;

# Dumping data for table video9cooo.t_sysmgr_user: ~72 rows (approximately)
/*!40000 ALTER TABLE `t_sysmgr_user` DISABLE KEYS */;
INSERT INTO `t_sysmgr_user` (`id`, `username`, `partner_account_name`, `partner_mobile`, `password`, `email`, `role_id`, `package_id`, `status`, `create_time`, `partner_account_id`, `partner_id`) VALUES
	(1, 'admin', NULL, NULL, '8NPDHHZLMdkfMfSK+iggpA==', '', 1, 1, 0, '2015-08-12 22:58:01', 0, 0),
	(2, 'luoyh', 'selleradmin', NULL, 'hhNi2UoieA6cGaDzgX25Mw==', 'luoyh@corp.21cn.com', 1, 1, 1, '2015-09-28 06:49:25', 0, 0),
	(3, 'luojc', NULL, NULL, 'wuZuBuzamGKM1JfF4Vb3/g==', 'luojc@qq.com', 3, 2, 1, '2015-08-26 17:35:52', 0, 0),
	(4, 'sunhy', NULL, NULL, 'wuZuBuzamGKM1JfF4Vb3/g==', 'sunhy@qq.com', 1, 1, 1, '2015-08-12 22:58:02', 0, 0),
	(5, 'liuw', NULL, NULL, 'wuZuBuzamGKM1JfF4Vb3/g==', 'liuw@qq.com', 0, 1, 0, '2015-08-12 22:58:03', 0, 0),
	(13, 'azt2015090509464', 'a378', NULL, '', '', 2, 1, 1, '2015-09-07 10:24:16', 0, 0),
	(14, NULL, NULL, NULL, '', '', 2, 1, 0, '2015-09-12 17:27:18', 2, 1),
	(15, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-12 17:45:13', 3, 1),
	(16, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-12 17:53:25', 4, 1),
	(17, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-12 17:56:25', 5, 1),
	(18, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-12 18:02:23', 6, 1),
	(19, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-15 22:54:07', 11, 1),
	(20, NULL, 'SellerAdmin', NULL, '', 'hairry.zhou@azt-china.com', 2, 1, 0, '2015-10-15 03:24:01', 1, 1),
	(21, NULL, 'maple_chen', NULL, '', '', 2, 3, 0, '2015-10-22 02:39:46', 243, 1),
	(22, NULL, 'doris', NULL, '', '', 2, 1, 0, '2015-10-09 15:39:49', 262, 1),
	(23, NULL, 'kilalonger', NULL, '', '', 2, 1, 0, '2015-09-23 16:42:09', 261, 1),
	(24, NULL, NULL, NULL, '', '', 2, 0, 0, '2015-09-15 17:39:30', 22, 1),
	(25, NULL, 'kewell5', NULL, '', '', 2, 1, 0, '2015-09-16 05:11:36', 264, 1),
	(26, NULL, 'maple_test', NULL, '', '', 2, 18, 0, '2015-10-14 03:30:58', 246, 1),
	(27, NULL, 'oyxp72487', NULL, '', '', 2, 1, 0, '2015-09-22 11:01:39', 265, 1),
	(28, NULL, 'rextest0916', NULL, '', '', 2, 1, 0, '2015-09-22 14:43:13', 267, 1),
	(29, NULL, 'oyxpaa', NULL, '', '', 2, 0, 0, '2015-09-17 09:41:28', 268, 1),
	(30, NULL, 'maple_a', NULL, '', '', 2, 0, 0, '2015-09-18 11:00:32', 271, 1),
	(31, NULL, 'yang_hai_100', NULL, '', '', 2, 0, 0, '2015-09-18 14:13:30', 244, 1),
	(32, NULL, 'rextest0922', NULL, '', '', 2, 0, 0, '2015-09-22 15:11:02', 274, 1),
	(33, NULL, 'oyxp11', NULL, '', '', 2, 0, 0, '2015-09-23 09:57:13', 275, 1),
	(34, NULL, 'oyxpaa12', NULL, '', '', 2, 0, 0, '2015-09-23 10:35:28', 279, 1),
	(35, NULL, 'admin', NULL, '', '', 2, 0, 0, '2015-09-24 19:07:41', 0, 2),
	(36, NULL, 'oyxpabcd', NULL, '', '', 2, 1, 0, '2015-09-26 13:58:38', 282, 1),
	(37, NULL, 'cccc', NULL, '', '', 2, 1, 0, '2015-09-26 16:33:45', 280, 1),
	(38, NULL, 'Hairry', NULL, '', '', 2, 1, 0, '2015-09-26 17:58:51', 263, 1),
	(39, NULL, 'oyxpaa11', NULL, '', '111111111@qq.com', 2, 0, 0, '2015-09-29 06:51:04', 287, 1),
	(40, NULL, 'rextest1006', NULL, '', 'rextest1006@azt-china.com', 2, 2, 0, '2015-10-09 12:19:20', 294, 1),
	(41, NULL, 'rextest100602', NULL, '', 'testing1007@azt-china.com', 2, 2, 0, '2015-10-09 13:22:49', 295, 1),
	(42, NULL, 'kilalonger1', NULL, '', '123@qq.com', 2, 0, 0, '2015-10-08 04:00:46', 266, 1),
	(43, NULL, 'cctvcat', NULL, '', '22@ww.sinaa.com', 2, 1, 0, '2015-10-08 04:10:16', 296, 1),
	(44, NULL, 'kilalonger2', NULL, '', 'ceshi2@qq.com', 2, 1, 0, '2015-10-12 06:38:54', 297, 1),
	(45, NULL, 'kilalonger3', NULL, '', 'kilalonger3@qq.com', 2, 2, 0, '2015-10-08 07:32:28', 298, 1),
	(46, NULL, 'test1', NULL, '', 'das@11.com', 2, 2, 0, '2015-10-09 07:36:36', 302, 1),
	(47, NULL, '123456', NULL, '', '1@44.com', 2, 0, 0, '2015-10-09 08:33:20', 304, 1),
	(48, NULL, 'test1010', NULL, '', '3213@3213.com', 2, 1, 0, '2015-10-09 18:59:20', 305, 1),
	(49, NULL, '9库有钱淫', NULL, '', 'null666666@163.com', 2, 1, 0, '2015-10-12 01:01:39', 307, 1),
	(50, NULL, 'kewell02', NULL, '', '3123213@qq.com', 2, 0, 0, '2015-10-12 02:46:27', 256, 1),
	(51, NULL, '9coootest1', NULL, '', 'ljc@9cooo.com', 2, 1, 0, '2015-10-12 02:53:05', 308, 1),
	(52, NULL, 'rt1012', NULL, '', 'rt1012@azt-china.com', 2, 2, 0, '2015-10-12 03:53:20', 309, 1),
	(53, NULL, 'lake0101', NULL, '', 'ce@ce.com', 2, 1, 0, '2015-10-13 02:41:30', 311, 1),
	(54, NULL, 'cary_zeng', NULL, '', '358172437@qq.com', 2, 1, 0, '2015-10-13 03:03:19', 314, 1),
	(55, NULL, 'alexchen', NULL, '', 'alex.chen@azt-china.com', 2, 1, 0, '2015-10-13 03:05:28', 313, 1),
	(56, NULL, 'mona-zhao', NULL, '', 'zhao.mona@azt-china.com', 2, 1, 0, '2015-10-13 03:35:27', 317, 1),
	(57, NULL, 'zoe_zhou', NULL, '', 'zhouyingping@azt-china.com', 2, 20, 0, '2015-10-13 03:36:43', 316, 1),
	(58, NULL, 'azt_test_1013', NULL, '', '1513431905@qq.com', 2, 1, 0, '2015-10-13 04:58:20', 315, 1),
	(59, NULL, 'zhou', NULL, '', 'zhouyingping@azt-china.com', 2, 1, 0, '2015-10-13 06:21:12', 318, 1),
	(60, NULL, 'william-shop', NULL, '', '860953603@qq.com', 2, 2, 0, '2015-10-14 07:59:09', 320, 1),
	(61, NULL, 'ljctest2015', NULL, '', 'luoyw@azt-china.com', 2, 2, 0, '2015-10-14 08:17:51', 321, 1),
	(62, NULL, '9cooo', NULL, '', '9cooo@9cooo.com', 2, 0, 0, '2015-10-19 08:34:07', 322, 1),
	(63, NULL, 'kilalonger4', NULL, '', 'ceshi4@qq.com', 2, 1, 0, '2015-10-21 06:26:12', 326, 1),
	(64, NULL, 'IvyChen', NULL, '', '775309529@qq.com', 2, 1, 0, '2015-10-21 02:08:17', 327, 1),
	(65, NULL, 'langge05', NULL, '', '123@123.com', 2, 1, 0, '2015-10-21 07:19:03', 335, 1),
	(66, NULL, 'langge06', NULL, '', '123@qq.com', 2, 0, 0, '2015-10-21 03:14:57', 329, 1),
	(67, NULL, '四川沱牌舍得酒业股份有限公司', NULL, '', '12345566@sina.com', 2, 1, 0, '2015-10-21 03:39:36', 334, 1),
	(68, NULL, 'langge01', NULL, '', '123@123.com', 2, 1, 0, '2015-10-21 05:40:31', 328, 1),
	(69, NULL, '成都阿当电子科技有限公司', NULL, '', '807435869@qq.com', 2, 1, 0, '2015-10-21 05:51:59', 337, 1),
	(70, NULL, '四川梅鹤酒业有限公司', NULL, '', '736389000@hotmail.com', 2, 1, 0, '2015-10-21 06:09:23', 340, 1),
	(71, NULL, 'langge02', NULL, '', '123@123.com', 2, 1, 0, '2015-10-21 06:39:57', 330, 1),
	(72, NULL, 'langge03', NULL, '', '123@123.com', 2, 1, 0, '2015-10-21 06:49:52', 331, 1),
	(73, NULL, 'langge04', NULL, '', '123@123.COM', 2, 1, 0, '2015-10-21 07:00:58', 333, 1),
	(74, NULL, 'kilalonger5', NULL, '', 'ceshi7@11.com', 2, 1, 0, '2015-10-21 07:25:52', 341, 1),
	(75, NULL, 'kilalonger6', NULL, '', '测试7@qq.com', 2, 1, 0, '2015-10-21 07:39:42', 342, 1),
	(76, NULL, 'miumiu', NULL, '', '305098300@qq.com', 2, 1, 0, '2015-10-21 08:17:19', 343, 1),
	(77, NULL, 'yuanben', NULL, '', '123@123.com', 2, 1, 0, '2015-10-21 08:34:22', 344, 1),
	(78, NULL, 'zoezhou', NULL, '', 'zhouyingping@azt-china.com', 2, 1, 0, '2015-10-21 08:48:23', 245, 1),
	(79, NULL, 'yanghai100', NULL, '', '1925546556@qq.com', 2, 3, 0, '2015-10-22 02:02:27', 345, 1),
	(80, NULL, 'kilalonger7', NULL, '', '测试7@qq.com', 2, 0, 0, '2015-10-22 06:43:56', 347, 1),
	(81, NULL, 'zzzz', NULL, '', 'sdas@sd.com', 2, 3, 0, '2015-10-22 07:48:02', 348, 1);
/*!40000 ALTER TABLE `t_sysmgr_user` ENABLE KEYS */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
