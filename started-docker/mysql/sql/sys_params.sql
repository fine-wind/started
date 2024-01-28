SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS `sys_params`;
CREATE TABLE `sys_params`  (
  `id` bigint NOT NULL COMMENT 'id',
  `param_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数编码',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `param_type` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '类型   0：系统参数   1：非系统参数',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_param_code`(`param_code` ASC) USING BTREE,
  INDEX `idx_create_date`(`create_date` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO `sys_params` VALUES (18379423042, 'SCRIPT', '/*  */\r\n(function () {\r\n		var baidus = function () {\r\n				var hm = document.createElement(\"script\");\r\n				hm.src = \"https://hm.baidu.com/hm.js?ae07a0284f4dc2af3baee7c36e35e096\";\r\n				var s = document.getElementsByTagName(\"script\")[0];\r\n				s.parentNode.insertBefore(hm, s);\r\n		};\r\n		/*没有端口号，并且网页结尾地址是html的*/\r\n		if (top.window.location.port.length <= 0 && top.window.location.host.indexOf(\'.\')>1) {\r\n				setTimeout(() => {baidus();}, 100);\r\n				/*todo 添加 debugger 使无法被调试*/\r\n				setInterval(() => {\r\n						\r\n				}, 1000)\r\n		}\r\n})();', '0', NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1067246875800000073, 'CLOUD_STORAGE_CONFIG_KEY', '{   \"type\": 5,   \"domain\": \"http://localhost:8080/admin\",   \"localPath\": \"D:/\",   \"prefix\": \"upload\",   \"qcloudBucketName\": \"\" }', '0', '云存储配置信息', 1067246875800000001, '2020-06-20 15:25:59', 1, '2021-08-06 21:31:41', '0');
INSERT INTO `sys_params` VALUES (1067246875800000075, 'MAIL_CONFIG_KEY', '{\"smtp\":\"smtp.163.com\",\"port\":25,\"username\":\"qjiabook@163.com\",\"password\":\"UBOENRZAQFRXHEZG\"}', '0', '邮件配置信息', 1067246875800000001, '2020-06-20 15:25:59', 1067246875800000001, '2020-06-20 15:25:59', '0');
INSERT INTO `sys_params` VALUES (1460151637441159170, 'NAME', '何方社区', '0', '网站名称', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151637898338305, 'THIS_HOST', 'https://hefang.group', '0', '网站域名', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151637965447170, 'SYNOPSIS', '烟雨山林暮色沉，<br/>\r\n细风吹遍如意林。', '0', '网站简介', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638032556033, 'COMPRESS', 'true', '0', '网站压缩，压缩需键入 true | false', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638099664897, 'PAGE_CACHE_PATH', '', '0', '页面缓存路径', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638166773761, 'STATIC_VERSION', '0.0.1', '0', 'js,css的版本号，用于清理浏览器端缓存', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638305185793, 'JWT_SECRETKEY', 'JWT_SECRETKEY', '0', '用户登录token加密密钥', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638368100354, 'JWT_EXPIRATION', '3600', '0', 'jwt过期时间(秒), 默认60分钟', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1460151638435209217, 'JWT_EXPIRATION_TIME_BEFORE', '1800', '0', '自动在x秒后刷新token, 默认30分钟', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1463073180284825601, 'BEIAN_IPC', '', '0', '网站备案号', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1463073180519706626, 'BEIAN_MIIT', 'https://beian.miit.gov.cn/', '0', '公信部连接', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1486908899291729922, 'TEMPLATE_PATH', '/app/resources', '0', '模板位置', NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_params` VALUES (1590691620964782082, 'JWT_SECRET_KEY', 'JWT_SECRET_KEY', '0', '用户登录token加密密钥', NULL, NULL, NULL, NULL, '0');

SET FOREIGN_KEY_CHECKS = 1;
