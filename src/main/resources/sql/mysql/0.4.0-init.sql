CREATE TABLE `catalog_metadata`
(
    `catalog_id`             int(11) NOT NULL AUTO_INCREMENT,
    `catalog_name`           varchar(64) NOT NULL COMMENT 'catalog name',
    `catalog_metastore`      varchar(64) NOT NULL COMMENT 'catalog type like hms/ams/hadoop/custom',
    `storage_configs`        mediumtext COMMENT 'base64 code of storage configs',
    `auth_configs`           mediumtext COMMENT 'base64 code of auth configs',
    `catalog_properties`     mediumtext COMMENT 'catalog properties',
    PRIMARY KEY (`catalog_id`),
    UNIQUE KEY `catalog_name_index` (`catalog_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'catalog metadata';

CREATE TABLE `container_metadata`
(
    `container_name`       varchar(64) NOT NULL COMMENT 'container name',
    `container_type`       varchar(64) NOT NULL COMMENT 'container type like flink/local',
    `properties` mediumtext COMMENT 'container properties',
    PRIMARY KEY (`container_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'container metadata';

CREATE TABLE `optimize_group`
(
    `group_name`       varchar(50) NOT NULL  COMMENT 'Optimize group name',
    `container_name`   varchar(100) DEFAULT NULL  COMMENT 'Container name',
    `properties`       mediumtext  COMMENT 'Properties',
    PRIMARY KEY (`group_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Group to divide optimize resources';

CREATE TABLE `optimizer`
(
    `optimizer_id`               bigint(20) NOT NULL AUTO_INCREMENT,
    `group_name`                 varchar(50) DEFAULT NULL COMMENT 'queue name',
    `optimizer_start_time`       timestamp not null default CURRENT_TIMESTAMP COMMENT 'optimizer start time',
    `optimizer_touch_time`       timestamp not null default CURRENT_TIMESTAMP COMMENT 'update time',
    `optimizer_status`           varchar(16)   DEFAULT NULL COMMENT 'optimizer status',
    `thread_count`               int(11) DEFAULT NULL COMMENT 'total number of all CPU resources',
    `total_memory`               bigint(30) DEFAULT NULL COMMENT 'optimizer use memory size',
    `optimizer_state_info`       mediumtext COMMENT 'optimizer state info, contains like yarn application id and flink job id',
    PRIMARY KEY (`optimizer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Optimizer info';

CREATE TABLE `database_metadata`
(
    `catalog_name` varchar(64) NOT NULL COMMENT 'catalog name',
    `db_name`      varchar(128) NOT NULL COMMENT 'database name',
    PRIMARY KEY (`catalog_name`, `db_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'database metadata';

CREATE TABLE `table_identifier`
(
    `table_id`     bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'Auto increment id',
    `catalog_name` varchar(64)  NOT NULL COMMENT 'Catalog name',
    `db_name`      varchar(128) NOT NULL COMMENT 'Database name',
    `table_name`   varchar(128) NOT NULL COMMENT 'Table name',
    PRIMARY KEY (`table_id`),
    UNIQUE KEY `table_name_index` (`catalog_name`, `db_name`, `table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Table identifier';

CREATE TABLE `table_metadata`
(
    `table_id`        bigint(20) NOT NULL COMMENT 'table id',
    `primary_key`     varchar(256) DEFAULT NULL COMMENT 'Primary key',
    `sort_key`        varchar(256) DEFAULT NULL COMMENT 'Sort key',
    `table_location`  varchar(256) DEFAULT NULL COMMENT 'Table location',
    `base_location`   varchar(256) DEFAULT NULL COMMENT 'Base table location',
    `change_location` varchar(256) DEFAULT NULL COMMENT 'change table location',
    `properties`      mediumtext COMMENT 'Table properties',
    `meta_store_site` mediumtext COMMENT 'base64 code of meta store site',
    `hdfs_site`       mediumtext COMMENT 'base64 code of hdfs site',
    `core_site`       mediumtext COMMENT 'base64 code of core site',
    `auth_method`     varchar(32)  DEFAULT NULL COMMENT 'auth method like KERBEROS/SIMPLE',
    `hadoop_username` varchar(64)  DEFAULT NULL COMMENT 'hadpp username when auth method is SIMPLE',
    `krb_keytab`      text COMMENT 'kerberos keytab when auth method is KERBEROS',
    `krb_conf`        text COMMENT 'kerberos conf when auth method is KERBEROS',
    `krb_principal`   text COMMENT 'kerberos principal when auth method is KERBEROS',
    `current_tx_id`   bigint(20) NOT NULL DEFAULT 0 COMMENT 'current transaction id',
    `current_schema_id`   int(11) NOT NULL DEFAULT 0 COMMENT 'current schema id',
    PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Table metadata';

CREATE TABLE `table_optimizing_runtime`
(
    `table_id`                      bigint(20) NOT NULL,
    `current_snapshot_id`           bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Base table current snapshot id',
    `current_change_snapshotId`     bigint(20) DEFAULT NULL COMMENT 'Change table current snapshot id',
    `last_major_optimizing_time`    timestamp COMMENT 'Latest Major Optimize time for all partitions',
    `last_minor_optimizing_time`    timestamp COMMENT 'Latest Minor Optimize time for all partitions',
    `last_full_optimizing_time`     timestamp COMMENT 'Latest Minor Optimize time for all partitions',
    `optimizing_status`             varchar(20) DEFAULT 'Idle' COMMENT 'Table optimize status: MajorOptimizing, MinorOptimizing, Pending, Idle',
    `optimizing_status_start_time`  timestamp DEFAULT 0 COMMENT 'Table optimize status start time',
    PRIMARY KEY (`table_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Optimize running information of each table';

CREATE TABLE `table_optimizing_procedure`
(
    `procedure_id`                  varchar(40) NOT NULL COMMENT 'History auto increment id',
    `table_id`                      bigint(20) NOT NULL,
    `status`                        varchar(10) NOT NULL COMMENT 'Direct to TableOptimizingStatus',
    `optimizing_type`               varchar(10) NOT NULL COMMENT 'Optimize type: Major, Minor',
    `plan_time`                     datetime(3) DEFAULT NULL COMMENT 'First plan time',
    `commit_time`                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'transaction allocate time',
    `fail_reason`                   varchar(4096) DEFAULT NULL COMMENT 'Error message after task failed',
    `duration`                      bigint(20) DEFAULT NULL COMMENT 'Execute cost time',
    `summary`                       mediumtext COMMENT 'Max change transaction id of these tasks',
    PRIMARY KEY (`procedure_id`),
    KEY  `table_index` (`table_id`, `plan_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'History of optimizing after each commit';

CREATE TABLE `optimizing_task`
(
    `procedure_id`              varchar(40) NOT NULL COMMENT 'Optimize type: Major, Minor, FullMajor',
    `task_id`                   int(11) NOT NULL COMMENT 'Optimize task unique id',
    `retry_num`                 int(11) NOT NULL default 0 COMMENT 'Retry times for the same task_trace_id',
    `task_type`                 varchar(10) NOT NULL COMMENT 'Optimize type: Major, Minor',
    `table_id`                  bigint(20) NOT NULL,
    `partition`                 varchar(128)  DEFAULT NULL COMMENT 'Partition',
    `max_change_transaction_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Max change transaction id',
    `min_change_transaction_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT 'Min change transaction id',
    `create_time`               timestamp DEFAULT 0 COMMENT 'Task create time',
    `start_time`                timestamp DEFAULT 0 COMMENT 'Time when task start waiting to execute',
    `cost_time`                 bigint(20) DEFAULT NULL COMMENT 'Task Execute cost time',
    `status`                    varchar(16)   DEFAULT NULL  COMMENT 'Optimize Status: Init, Pending, Executing, Failed, Prepared, Committed',
    `fail_reason`               varchar(4096) DEFAULT NULL COMMENT 'Error message after task failed',
    `optimizer_group`           int(11) DEFAULT NULL COMMENT 'Queue id which execute task',
    `optimizer_id`              bigint(20) DEFAULT NULL COMMENT 'Job type',
    `thread_id`                 int(11) DEFAULT NULL COMMENT 'Job id',
    `insert_file_count`         int(11) DEFAULT NULL COMMENT 'Insert file cnt',
    `delete_file_count`         int(11) DEFAULT NULL COMMENT 'Delete file cnt',
    `base_file_count`           int(11) DEFAULT NULL COMMENT 'Base file cnt',
    `pos_delete_file_count`     int(11) DEFAULT NULL COMMENT 'Pos-Delete file cnt',
    `insert_file_size`          bigint(20) DEFAULT NULL COMMENT 'Insert file size in bytes',
    `delete_file_size`          bigint(20) DEFAULT NULL COMMENT 'Delete file size in bytes',
    `base_file_size`            bigint(20) DEFAULT NULL COMMENT 'Base file size in bytes',
    `pos_delete_file_size`      bigint(20) DEFAULT NULL COMMENT 'Pos-Delete file size in bytes',
    `source_nodes`              varchar(2048) DEFAULT NULL COMMENT 'Source nodes of task',
    `new_file_size`             bigint(20) DEFAULT NULL COMMENT 'File size generated by task executing',
    `new_file_count`            int(11) DEFAULT NULL COMMENT 'File cnt generated by task executing',
    `properties`                text COMMENT 'Task properties',
    PRIMARY KEY (`procedure_id`, `task_id`, `retry_num`),
    KEY  `table_index` (`table_id`, `procedure_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Optimize task basic information';

CREATE TABLE `optimizing_file`
(
    `file_id`       bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'History auto increment id',
    `procedure_id`   varchar(40) NOT NULL COMMENT 'Optimize type: Major, Minor, FullMajor',
    `task_id`        varchar(40) NOT NULL COMMENT 'Optimize task unique id',
    `content_type`  varchar(32) NOT NULL COMMENt 'File type: BASE_FILE, INSERT_FILE, EQ_DELETE_FILE, POS_DELETE_FILE',
    `is_target`     tinyint(4) DEFAULT '0' COMMENT 'Is file newly generated by optimizing',
    `file_serialization`  MEDIUMBLOB DEFAULT NULL COMMENT 'File bytes after serialization',
    PRIMARY KEY (`file_id`),
    KEY    `task_identifier` (`procedure_id`,`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'Optimize files for Optimize task';

--CREATE TABLE `optimizing_quota_history`
--(
--    `table_id`                  bigint(20) NOT NULL,
--    `procedure_id`              varchar(40) NOT NULL COMMENT 'Optimize type: Major, Minor, FullMajor',
--    `task_id`                   int(11) NOT NULL COMMENT 'Optimize task unique id',
--    `retry_num`                 int(11) NOT NULL default 0 COMMENT 'Retry times for the same task_trace_id',
--    `start_time`                datetime(3) DEFAULT NULL COMMENT 'Task start time',
--    `cost_time`                 bigint(20) DEFAULT NULL COMMENT 'Task cost time',
--    `optimizer_group`      int(11) DEFAULT NULL COMMENT 'Queue id which execute task',
--    PRIMARY KEY (`procedure_id`, `task_id`, `retry_num`),
--    KEY `table_end_time_plan_group_index` (`table_id`, `start_time`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'History of each optimize task execute';

CREATE TABLE `table_transaction_meta`
(
    `table_identifier` varchar(384) NOT NULL COMMENT 'table full name with catalog.db.table',
    `transaction_id`   bigint(20) NOT NULL COMMENT 'allocated transaction id',
    `signature`        varchar(256) NOT NULL COMMENT 'transaction request signature',
    `commit_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'transaction allocate time',
    PRIMARY KEY (`table_identifier`,`transaction_id`),
    UNIQUE KEY `signature_unique` (`table_identifier`,`signature`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT 'transaction meta info of table';

CREATE TABLE `api_tokens`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `apikey`     varchar(256) NOT NULL COMMENT 'openapi client public key',
    `secret`     varchar(256) NOT NULL COMMENT 'The key used by the client to generate the request signature',
    `apply_time` datetime DEFAULT NULL COMMENT 'apply time',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `account_unique` (`apikey`) USING BTREE COMMENT 'account unique'
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='Openapi  secret';

CREATE TABLE `platform_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'file id',
  `file_name` varchar(100) NOT NULL COMMENT 'file name',
  `file_content_b64` mediumtext NOT NULL COMMENT 'file content encoded with base64',
  `file_path` varchar(100) DEFAULT NULL COMMENT 'may be hdfs path , not be used now',
  `add_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'add timestamp',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='store files info saved in the platform';

INSERT INTO catalog_metadata(catalog_name,catalog_metastore,storage_configs,auth_configs, catalog_properties) VALUES ('local_catalog','ams','{"storage.type":"hdfs","hive.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg==","hadoop.core.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg==","hadoop.hdfs.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg=="}','{"auth.type":"simple","auth.simple.hadoop_username":"root"}','{"warehouse":"/tmp/arctic/warehouse","table-formats":"MIXED_ICEBERG"}');