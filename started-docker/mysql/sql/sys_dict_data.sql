SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_dict_type_value`(`dict_type_id` ASC, `dict_value` ASC) USING BTREE,
  INDEX `idx_sort`(`sort` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1160061112075464705, 1160061077912858600, '男', '0', '', 0, 1067246875800000001, '2020-06-20 15:25:59', 1067246875800000001, '2020-06-20 15:25:59', '0');
INSERT INTO `sys_dict_data` VALUES (1160061146967879681, 1160061077912858600, '女', '1', '', 1, 1067246875800000001, '2020-06-20 15:25:59', 1067246875800000001, '2020-06-20 15:25:59', '0');
INSERT INTO `sys_dict_data` VALUES (1160061190127267841, 1160061077912858625, '保密', '2', '', 2, 1067246875800000001, '2020-06-20 15:25:59', 1067246875800000001, '2020-06-20 15:25:59', '0');
INSERT INTO `sys_dict_data` VALUES (1225814069634195457, 1225813644059140097, '公告', '0', '', 0, 1067246875800000001, '2020-06-20 15:25:49', 1067246875800000001, '2020-06-20 15:25:49', '0');
INSERT INTO `sys_dict_data` VALUES (1225814107559092225, 1225813644059140097, '会议', '1', '', 1, 1067246875800000001, '2020-06-20 15:25:49', 1067246875800000001, '2020-06-20 15:25:49', '0');
INSERT INTO `sys_dict_data` VALUES (1225814271879340034, 1225813644059140097, '其他', '2', '', 2, 1067246875800000001, '2020-06-20 15:25:49', 1067246875800000001, '2020-06-20 15:25:49', '0');

SET FOREIGN_KEY_CHECKS = 1;
