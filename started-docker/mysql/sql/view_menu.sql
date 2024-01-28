SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for view_menu
-- ----------------------------
DROP TABLE IF EXISTS `view_menu`;
CREATE TABLE `view_menu`  (
  `id` bigint NOT NULL,
  `tital` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `pid` bigint NULL DEFAULT NULL COMMENT '上级id',
  `random` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当显示此类型是是否随机',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '页面菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of view_menu
-- ----------------------------
INSERT INTO `view_menu` VALUES (1, '小工具', '#', 0, NULL, 2, 1, '2020-09-20 15:38:43', 1, NULL, '1');
INSERT INTO `view_menu` VALUES (2, '小游戏', '#', 0, NULL, 3, 1, '2020-09-20 15:38:43', 1, '2021-08-24 18:12:53', '1');
INSERT INTO `view_menu` VALUES (3, '2048', '/2048/index.html', 2, NULL, 3, 1, '2020-09-20 15:38:43', 1, NULL, '1');
INSERT INTO `view_menu` VALUES (4, '在线二维码', '/tool/qrcode.html', 1, NULL, 2, 1, '2020-09-20 15:38:43', 1, NULL, '1');
INSERT INTO `view_menu` VALUES (5, 'chat', '/chat.html', 1, NULL, 2, NULL, NULL, NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1066735891990, '知识点', 'learning', 0, NULL, 1, 1, '2021-09-09 14:36:37', NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1066735891990001, 'Docker', 'docker', 1066735891990, NULL, 1, NULL, NULL, NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1066735891990002, 'Nginx', 'nginx', 1066735891990, NULL, 2, NULL, NULL, NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1066735891990003, 'MySql', 'mysql', 1066735891990, NULL, 3, NULL, NULL, NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1430145782742970367, '毒鸡汤', 'dujitang', 0, NULL, 2, 1, '2021-08-30 20:31:40', 1430150288885108737, '2021-08-25 16:17:18', '1');
INSERT INTO `view_menu` VALUES (1430145782742970368, '套路', '/taolu', 0, NULL, 2, 1, '2021-08-30 20:31:40', 1067246875800000001, '2021-10-06 17:07:09', '1');
INSERT INTO `view_menu` VALUES (1431442811419148290, '源码', '#', NULL, NULL, 5, 1430149985762758658, '2021-08-28 10:25:36', NULL, NULL, '1');
INSERT INTO `view_menu` VALUES (1431442998250225665, 'java', '/java', 1431442811419148290, NULL, 1, 1430149985762758658, '2021-08-28 10:26:20', 1430149985762758658, '2021-08-28 14:57:10', '1');
INSERT INTO `view_menu` VALUES (1434096200057139202, 'php', 'php', 1431442811419148290, NULL, NULL, 1430150288885108737, '2021-09-04 18:09:13', NULL, NULL, '1');

SET FOREIGN_KEY_CHECKS = 1;
