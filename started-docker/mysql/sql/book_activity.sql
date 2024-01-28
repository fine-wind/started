SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_activity
-- ----------------------------
DROP TABLE IF EXISTS `book_activity`;
CREATE TABLE `book_activity`
(
    `id`          bigint NOT NULL COMMENT 'id',
    `title`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动标题',
    `type`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动类型',
    `sponsor`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主办方',
    `content`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '活动内容',
    `start_date`  datetime NULL DEFAULT NULL COMMENT '活动开始时间',
    `end_date`    datetime NULL DEFAULT NULL COMMENT '活动结束时间',
    `pic_file_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片所在的id',
    `creator`     bigint NULL DEFAULT NULL COMMENT '创建人',
    `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '读书活动' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of book_activity
-- ----------------------------
INSERT INTO `book_activity`
VALUES (1275225050344505346, '11', '11', '11', '11', '2020-06-23 08:29:42', '2020-06-23 08:29:42', '11',
        1067246875800000001, '2020-06-23 08:31:59');
INSERT INTO `book_activity`
VALUES (1275230075737260033, '测试2', '测试2', '测试2', '<p>测试2测试2测试2测试2测试2测试2测试2测试2测试2测试2</p>',
        '2020-06-23 08:51:48',
        '2020-06-25 00:00:00', '测试2', 1067246875800000001, '2020-06-23 08:51:57');

SET
FOREIGN_KEY_CHECKS = 1;
