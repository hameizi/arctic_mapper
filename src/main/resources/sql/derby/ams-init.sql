CREATE TABLE catalog_metadata
(
    catalog_id            int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    catalog_name           varchar(64) NOT NULL,
    catalog_metastore      varchar(64) NOT NULL,
    storage_configs        clob(64m),
    auth_configs           clob(64m),
    catalog_properties     clob(64m),
    PRIMARY KEY (catalog_id),
    UNIQUE (catalog_name)
);

CREATE TABLE container_metadata
(
    container_name       varchar(64) NOT NULL,
    container_type       varchar(64) NOT NULL,
    properties clob(64m),
    PRIMARY KEY (container_name)
);

CREATE TABLE optimize_group
(
    group_name       varchar(50) NOT NULL  ,
    container_name   varchar(100) DEFAULT NULL  ,
    properties       clob(64m)  ,
    PRIMARY KEY (group_name)
) ;

CREATE TABLE optimizer
(
    optimizer_id               bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    group_name                 varchar(50) DEFAULT NULL ,
    optimizer_start_time       timestamp not null default CURRENT_TIMESTAMP ,
    optimizer_touch_time       timestamp not null default CURRENT_TIMESTAMP ,
    optimizer_status           varchar(16)   DEFAULT NULL ,
    thread_count               int DEFAULT NULL ,
    total_memory               bigint DEFAULT NULL ,
    optimizer_state_info       clob(64m) ,
    PRIMARY KEY (optimizer_id)
);

CREATE TABLE database_metadata
(
    catalog_name varchar(64) NOT NULL ,
    db_name      varchar(128) NOT NULL ,
    PRIMARY KEY (catalog_name, db_name)
);

CREATE TABLE table_identifier
(
    table_id     bigint   NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,
    catalog_name varchar(64)  NOT NULL ,
    db_name      varchar(128) NOT NULL ,
    table_name   varchar(128) NOT NULL ,
    PRIMARY KEY (table_id),
    UNIQUE (catalog_name, db_name, table_name)
);

CREATE TABLE table_metadata
(
    table_id        bigint NOT NULL ,
    primary_key     varchar(256) DEFAULT NULL ,
    sort_key        varchar(256) DEFAULT NULL ,
    table_location  varchar(256) DEFAULT NULL ,
    base_location   varchar(256) DEFAULT NULL ,
    change_location varchar(256) DEFAULT NULL ,
    properties      clob(64m) ,
    meta_store_site clob(64m) ,
    hdfs_site       clob(64m) ,
    core_site       clob(64m) ,
    auth_method     varchar(32)  DEFAULT NULL ,
    hadoop_username varchar(64)  DEFAULT NULL ,
    krb_keytab      clob(64m) ,
    krb_conf        clob(64m) ,
    krb_principal   clob(64m) ,
    current_tx_id   bigint NOT NULL DEFAULT 0 ,
    current_schema_id   int NOT NULL DEFAULT 0 ,
    PRIMARY KEY (table_id)
) ;

CREATE TABLE table_optimizing_runtime
(
    table_id                      bigint NOT NULL,
    current_snapshot_id           bigint NOT NULL DEFAULT -1,
    current_change_snapshotId     bigint DEFAULT NULL ,
    last_major_optimizing_time    timestamp ,
    last_minor_optimizing_time    timestamp ,
    last_full_optimizing_time     timestamp ,
    optimizing_status             varchar(20) DEFAULT 'Idle' ,
    optimizing_status_start_time  timestamp DEFAULT NULL ,
    PRIMARY KEY (table_id)
);

CREATE TABLE table_optimizing_history
(
    history_id                    bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    table_id                      bigint NOT NULL,
    optimizing_type               varchar(10) NOT NULL ,
    plan_time                     timestamp DEFAULT NULL,
    commit_time                   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    fail_reason                   varchar(4096) DEFAULT NULL ,
    duration                      bigint DEFAULT NULL ,
    summary                       clob(64m) ,
    PRIMARY KEY (history_id)
) ;

CREATE TABLE optimizing_task
(
    procedure_id              varchar(40) NOT NULL ,
    task_id                   int NOT NULL ,
    task_type                 varchar(10) NOT NULL ,
    table_id                  bigint NOT NULL,
    partition                 varchar(128)  DEFAULT NULL ,
    max_change_transaction_id bigint NOT NULL DEFAULT -1 ,
    min_change_transaction_id bigint NOT NULL DEFAULT -1 ,
    create_time               timestamp DEFAULT NULL ,
    start_time                timestamp DEFAULT NULL ,
    cost_time                 bigint DEFAULT NULL ,
    status                    varchar(16)   DEFAULT NULL  ,
    fail_reason               varchar(4096) DEFAULT NULL ,
    optimizer_id              bigint DEFAULT NULL ,
    thread_id                 int DEFAULT NULL ,
    insert_file_count         int DEFAULT NULL ,
    delete_file_count         int DEFAULT NULL ,
    base_file_count           int DEFAULT NULL ,
    pos_delete_file_count     int DEFAULT NULL ,
    insert_file_size          bigint DEFAULT NULL ,
    delete_file_size          bigint DEFAULT NULL ,
    base_file_size            bigint DEFAULT NULL ,
    pos_delete_file_size      bigint DEFAULT NULL ,
    source_nodes              varchar(2048) DEFAULT NULL ,
    retry_count               int DEFAULT NULL ,
    new_file_size             bigint DEFAULT NULL ,
    new_file_count            int DEFAULT NULL,
    properties                clob(64m) ,
    PRIMARY KEY (procedure_id, task_id)
) ;

CREATE TABLE optimizing_file
(
    file_id       bigint NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) ,
    procedure_id   varchar(40) NOT NULL ,
    task_id        varchar(40) NOT NULL ,
    content_type  varchar(32) NOT NULL ,
    is_target     int DEFAULT 0 ,
    file_serialization  blob DEFAULT NULL ,
    PRIMARY KEY (file_id)
) ;

CREATE TABLE optimizing_quota_history
(
    table_id                  bigint NOT NULL,
    procedure_id              varchar(40) NOT NULL ,
    task_id                   int NOT NULL ,
    retry_num                 int NOT NULL default 0 ,
    start_time                timestamp DEFAULT NULL ,
    cost_time                 bigint DEFAULT NULL ,
    optimizer_group      int DEFAULT NULL ,
    PRIMARY KEY (procedure_id, task_id, retry_num)
);

CREATE TABLE table_transaction_meta
(
    table_identifier varchar(384) NOT NULL ,
    transaction_id   bigint NOT NULL ,
    signature        varchar(256) NOT NULL ,
    commit_time      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (table_identifier,transaction_id),
    UNIQUE (table_identifier,signature)
);

CREATE TABLE api_tokens
(
    id         int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    apikey     varchar(256) NOT NULL ,
    secret     varchar(256) NOT NULL ,
    apply_time timestamp DEFAULT NULL ,
    PRIMARY KEY (id),
    UNIQUE  (apikey)
) ;

CREATE TABLE platform_file (
  id int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  file_name varchar(100) NOT NULL,
  file_content_b64 clob(64m) NOT NULL,
  file_path varchar(100) DEFAULT NULL,
  add_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

INSERT INTO catalog_metadata(catalog_name,catalog_metastore,storage_configs,auth_configs, catalog_properties) VALUES ('local_catalog','ams','{"storage.type":"hdfs","hive.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg==","hadoop.core.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg==","hadoop.hdfs.site":"PGNvbmZpZ3VyYXRpb24+PC9jb25maWd1cmF0aW9uPg=="}','{"auth.type":"simple","auth.simple.hadoop_username":"root"}','{"warehouse":"/tmp/arctic/warehouse","table-formats":"MIXED_ICEBERG"}');