SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_company
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `site_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点名称',
  `site_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '站点地址',
  `start_date` date NULL DEFAULT NULL COMMENT '开始日期',
  `end_date` date NULL DEFAULT NULL COMMENT '结束日期',
  `compress_html` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否压缩页面',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `company_id` bigint NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统-公司表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES (-1, '网站名称未设置', NULL, '2020-09-01', '2099-10-29', '0', NULL, 1067246875800000001, '2020-07-09 22:27:30', NULL, NULL, NULL, '0');
INSERT INTO `sys_company` VALUES (0, 'localhost.com', 'localhost.com', '2020-09-01', '2099-10-29', '0', NULL, 1067246875800000001, '2020-07-09 22:27:30', NULL, NULL, NULL, '0');
INSERT INTO `sys_company` VALUES (1000000000000000001, '何方社区', 'hefang.group', '2020-08-01', '2099-10-29', '0', 0, 1067246875800000001, '2020-08-22 13:13:07', NULL, 1, '2021-08-05 22:46:00', '0');
INSERT INTO `sys_company` VALUES (1297039071178330113, '啊哈哈哈哈', 'b.abc.com', '2020-08-01', '2099-10-29', '0', 0, 1067246875800000001, '2020-08-22 13:13:07', NULL, 1, '2021-08-22 12:55:56', '0');
INSERT INTO `sys_company` VALUES (1423289455838920706, 'abc.com', 'abc.com', '2021-08-05', '2021-08-19', '0', 0, 1, '2021-08-05 22:27:04', NULL, 1, '2021-08-07 11:28:01', '0');

SET FOREIGN_KEY_CHECKS = 1;
