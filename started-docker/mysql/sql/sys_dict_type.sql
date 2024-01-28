SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `type` int NULL DEFAULT NULL COMMENT '字典类型，见字典 dict_type',
  `sort` int UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `company_id` bigint NULL DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (0, 'dict_type', '字典类型', NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, '0');
INSERT INTO `sys_dict_type` VALUES (1, 'status', '数据状态', '状态  停用  or 正常', NULL, 3, 1, '2021-08-06 18:30:38', 1, '2021-08-06 18:30:44', -1, '0');
INSERT INTO `sys_dict_type` VALUES (1160061077912858625, 'gender', '性别', '11', NULL, 8, 1067246875800000001, '2020-06-20 15:25:59', 1, '2021-08-06 18:31:07', NULL, '0');
INSERT INTO `sys_dict_type` VALUES (1225813644059140097, 'notice_type', '站内通知-类型', '', NULL, 9, 1067246875800000001, '2020-06-20 15:25:49', 1, '2021-08-06 18:31:00', NULL, '0');
INSERT INTO `sys_dict_type` VALUES (5555555555555555555, 'mourning', '哀悼', '重大情况时页面黑白', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1');

SET FOREIGN_KEY_CHECKS = 1;
