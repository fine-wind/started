SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_room_p
-- ----------------------------
DROP TABLE IF EXISTS `sys_room_p`;
CREATE TABLE `sys_room_p`  (
  `id` bigint NOT NULL,
  `room_id` bigint NULL DEFAULT NULL COMMENT '房间id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `creator` bigint NULL DEFAULT NULL,
  `create_date` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_date` datetime NULL DEFAULT NULL,
  `del_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '页面菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_room_p
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
