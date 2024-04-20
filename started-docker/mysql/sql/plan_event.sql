CREATE TABLE `plan_event`
(
    `id`          bigint NOT NULL,
    `dt`          date                                    DEFAULT NULL,
    `text`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `color`       varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `sort`        varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `creator`     varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `create_date` datetime                                DEFAULT NULL,
    `updater`     datetime                                DEFAULT NULL,
    `update_date` datetime                                DEFAULT NULL,
    `del_flag`    varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;