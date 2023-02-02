package com.netease.arctic.ams.server.service;

import com.netease.arctic.ams.api.OptimizeStatus;
import com.netease.arctic.ams.server.mapper.ApiTokensMapper;
import com.netease.arctic.ams.server.mapper.CatalogMetadataMapper;
import com.netease.arctic.ams.server.mapper.ContainerMetadataMapper;
import com.netease.arctic.ams.server.mapper.DatabaseMetadataMapper;
import com.netease.arctic.ams.server.mapper.InternalTableFilesMapper;
import com.netease.arctic.ams.server.mapper.OptimizeGroupMapper;
import com.netease.arctic.ams.server.mapper.OptimizeHistoryMapper;
import com.netease.arctic.ams.server.mapper.OptimizeTaskRuntimesMapper;
import com.netease.arctic.ams.server.mapper.OptimizeTasksMapper;
import com.netease.arctic.ams.server.mapper.OptimizerMapper;
import com.netease.arctic.ams.server.mapper.PlatformFileInfoMapper;
import com.netease.arctic.ams.server.mapper.TableIdentifierMapper;
import com.netease.arctic.ams.server.mapper.TableMetadataMapper;
import com.netease.arctic.ams.server.mapper.TableOptimizeRuntimeMapper;
import com.netease.arctic.ams.server.mapper.TaskHistoryMapper;
import com.netease.arctic.ams.server.model.ApiTokens;
import com.netease.arctic.ams.server.model.BaseOptimizeTask;
import com.netease.arctic.ams.server.model.BaseOptimizeTaskRuntime;
import com.netease.arctic.ams.server.model.CatalogMeta;
import com.netease.arctic.ams.server.model.Container;
import com.netease.arctic.ams.server.model.OptimizeHistory;
import com.netease.arctic.ams.server.model.OptimizeQueueMeta;
import com.netease.arctic.ams.server.model.OptimizeTaskId;
import com.netease.arctic.ams.server.model.Optimizer;
import com.netease.arctic.ams.server.model.TableIdentifierDTO;
import com.netease.arctic.ams.server.model.TableMetadata;
import com.netease.arctic.ams.server.model.TableOptimizeRuntime;
import com.netease.arctic.ams.server.model.TableTaskHistory;
import com.netease.arctic.ams.server.model.TreeNode;
import com.netease.arctic.table.TableIdentifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

public class Test extends IJDBCService {

  // @BeforeClass
  // public static void before() throws Exception {
  //   DerbyTestUtil derbyTestUtil = new DerbyTestUtil();
  //   derbyTestUtil.createTestTable();
  //
  // }

  @org.junit.Test
  public void testApiTokensMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      ApiTokensMapper mapper = getMapper(sqlSession, ApiTokensMapper.class);
      ApiTokens apiTokens = new ApiTokens();
      apiTokens.setId(1);
      apiTokens.setApikey("key");
      apiTokens.setSecret("secret");
      apiTokens.setApplyTime("2023-01-30 00:00:00");
      mapper.insert(apiTokens);
      Assert.assertEquals("secret", mapper.getSecretBykey("key"));
      mapper.delToken(1);
    }
  }

  @org.junit.Test
  public void testCatalogMetadataMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      CatalogMetadataMapper mapper = getMapper(sqlSession, CatalogMetadataMapper.class);
      CatalogMeta catalogMeta = new CatalogMeta();
      catalogMeta.setCatalogMetastore("ams");
      catalogMeta.setCatalogName("test");
      catalogMeta.setCatalogProperties(new HashMap<>());
      catalogMeta.setAuthConfigs(new HashMap<>());
      catalogMeta.setStorageConfigs(new HashMap<>());
      mapper.insertCatalog(catalogMeta);
      mapper.getCatalogs();
      mapper.updateCatalog(catalogMeta);
      mapper.getCatalog("test");
    }
  }

  @org.junit.Test
  public void testContainerMetadataMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      ContainerMetadataMapper mapper = getMapper(sqlSession, ContainerMetadataMapper.class);
      Container container = new Container();
      container.setContainerName("test");
      container.setContainerType("flink");
      container.setProperties(new HashMap<>());
      mapper.insertContainer(container);
      mapper.getContainers();
      mapper.getContainer("test");
      mapper.getType("test");
    }
  }

  @org.junit.Test
  public void testDatabaseMetadataMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      DatabaseMetadataMapper mapper = getMapper(sqlSession, DatabaseMetadataMapper.class);
      mapper.insertDb("testCatalog", "testDb");
      mapper.listDb("testCatalog");
      mapper.dropDb("testCatalog", "testDb");
    }
  }

  @org.junit.Test
  public void testInternalTableFilesMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      InternalTableFilesMapper mapper = getMapper(sqlSession, InternalTableFilesMapper.class);
      OptimizeTaskId optimizeTaskId = new OptimizeTaskId();
      optimizeTaskId.setTaskId(1);
      optimizeTaskId.setProcedureId("1");
      mapper.insertOptimizeTaskFile(
          optimizeTaskId,
          "contentType",
          1,
          "fileSerialization".getBytes(StandardCharsets.UTF_8));
      mapper.insertOptimizeTaskFile(
          optimizeTaskId,
          "contentType",
          0,
          "fileSerialization".getBytes(StandardCharsets.UTF_8));
      mapper.selectOptimizeTaskFiles(
          optimizeTaskId,
          "contentType",
          0);
      mapper.selectOptimizeTaskFiles(
          optimizeTaskId,
          "contentType",
          1);
      mapper.deleteOptimizeTaskTargetFile(optimizeTaskId);
      mapper.selectOptimizeTaskFiles(
          optimizeTaskId,
          "contentType",
          1);
      mapper.deleteOptimizeTaskFile(optimizeTaskId);
      mapper.selectOptimizeTaskFiles(
          optimizeTaskId,
          "contentType",
          0);
    }
  }

  @org.junit.Test
  public void testOptimizeGroupMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      OptimizeGroupMapper mapper = getMapper(sqlSession, OptimizeGroupMapper.class);
      OptimizeQueueMeta meta = new OptimizeQueueMeta();
      meta.setContainerName("testContainer");
      meta.setGroupName("testGroup");
      meta.setProperties(new HashMap<>());
      mapper.insertQueue(meta);
      meta.setContainerName("testContainer1");
      mapper.updateQueue(meta);
      mapper.selectOptimizeQueues();
      mapper.selectOptimizeQueue("testGroup");
      mapper.deleteQueue("testGroup");
      mapper.deleteAllQueue();
    }
  }

  @org.junit.Test
  public void testOptimizeHistoryMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      OptimizeHistoryMapper mapper = getMapper(sqlSession, OptimizeHistoryMapper.class);
      OptimizeHistory optimizeHistory = new OptimizeHistory();
      optimizeHistory.setDuration(100);
      optimizeHistory.setProcedureId("1");
      optimizeHistory.setStatus("status");
      optimizeHistory.setOptimizingType("Minor");
      optimizeHistory.setSummary(new HashMap<>());
      optimizeHistory.setPlanTime(System.currentTimeMillis());
      optimizeHistory.setCommitTime(System.currentTimeMillis());
      optimizeHistory.setFailReason("fail");
      optimizeHistory.setTableId(1);
      mapper.insertOptimizeHistory(optimizeHistory);
      mapper.selectOptimizeHistory(1);
      mapper.latestCommitTime(1);
      mapper.maxOptimizeHistoryId();
      mapper.expireOptimizeHistory(1, 10);
      mapper.deleteOptimizeRecord(1);
    }
  }

  @org.junit.Test
  public void testOptimizerMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      OptimizerMapper mapper = getMapper(sqlSession, OptimizerMapper.class);
      Optimizer optimizer = new Optimizer();
      optimizer.setOptimizerStatus("running");
      optimizer.setOptimizerStartTime(System.currentTimeMillis());
      optimizer.setOptimizerStateInfo(new HashMap<>());
      optimizer.setOptimizerTouchTime(System.currentTimeMillis());
      optimizer.setGroupName("group");
      optimizer.setThreadCount(1);
      optimizer.setTotalMemory(100L);
      mapper.insertOptimizer(optimizer);
      mapper.selectOptimizer(optimizer.getOptimizerId());
      mapper.selectOptimizers();
      mapper.selectOptimizerGroupResourceInfo();
      mapper.selectOptimizersByGroupName("group");
      mapper.selectOptimizerGroupResourceInfoByGroupName("group");
      mapper.updateOptimizerStatus(optimizer.getOptimizerId(), "fail");
      mapper.updateOptimizerState(optimizer.getOptimizerId(), new HashMap(), "failed");
      mapper.deleteOptimizer(optimizer.getOptimizerId());
    }
  }

  @org.junit.Test
  public void testOptimizeTaskRuntimesMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      BaseOptimizeTask baseOptimizeTask = new BaseOptimizeTask();
      OptimizeTaskId optimizeTaskId = new OptimizeTaskId();
      optimizeTaskId.setTaskId(1);
      optimizeTaskId.setProcedureId("5");
      baseOptimizeTask.setTaskId(optimizeTaskId);
      baseOptimizeTask.setTaskType("Major");
      baseOptimizeTask.setTableId(1);
      baseOptimizeTask.setPartition("/aa");
      baseOptimizeTask.setMaxChangeTransactionId(2);
      baseOptimizeTask.setMinChangeTransactionId(3);
      baseOptimizeTask.setCreateTime(System.currentTimeMillis());
      baseOptimizeTask.setOptimizerGroup(5);
      baseOptimizeTask.setOptimizerId(4L);
      baseOptimizeTask.setThreadId(5);
      baseOptimizeTask.setInsertFileCount(6);
      baseOptimizeTask.setBaseFileCount(7);
      baseOptimizeTask.setDeleteFileCount(8);
      baseOptimizeTask.setPosDeleteFileCount(9);
      baseOptimizeTask.setInsertFileSize(10L);
      baseOptimizeTask.setDeleteFileSize(11L);
      baseOptimizeTask.setBaseFileSize(12L);
      baseOptimizeTask.setPosDeleteFileSize(13L);
      TreeNode treeNode = new TreeNode();
      treeNode.setIndex(1);
      treeNode.setMask(2);
      List<TreeNode> treeNodeList = new ArrayList<>();
      treeNodeList.add(treeNode);
      baseOptimizeTask.setSourceNodes(treeNodeList);
      baseOptimizeTask.setProperties(new HashMap<>());
      BaseOptimizeTaskRuntime baseOptimizeTaskRuntime = new BaseOptimizeTaskRuntime();
      baseOptimizeTaskRuntime.setTaskId(optimizeTaskId);
      baseOptimizeTaskRuntime.setOptimizerId(14L);
      baseOptimizeTaskRuntime.setCostTime(15L);
      baseOptimizeTaskRuntime.setStatus(OptimizeStatus.Failed);
      baseOptimizeTaskRuntime.setFailReason("fail reason");
      baseOptimizeTaskRuntime.setNewFileCount(16);
      baseOptimizeTaskRuntime.setRetryNum(17);
      baseOptimizeTaskRuntime.setThreadId(19);
      baseOptimizeTaskRuntime.setStartTime(System.currentTimeMillis());
      baseOptimizeTaskRuntime.setNewFileSize(20L);
      OptimizeTasksMapper optimizeTasksMapper = getMapper(sqlSession, OptimizeTasksMapper.class);
      optimizeTasksMapper.insertOptimizeTask(baseOptimizeTask, baseOptimizeTaskRuntime);
      optimizeTasksMapper.selectAllOptimizeTasks();
      baseOptimizeTaskRuntime.setRetryNum(20);
      optimizeTasksMapper.selectAllOptimizeTaskRuntimes();

      optimizeTasksMapper.deleteOptimizeTask(optimizeTaskId);
    }
  }

  @org.junit.Test
  public void testTableIdentifierMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      TableIdentifierMapper mapper = getMapper(sqlSession, TableIdentifierMapper.class);
      TableIdentifierDTO tableIdentifierDTO = new TableIdentifierDTO();
      tableIdentifierDTO.setTableName("testTable");
      tableIdentifierDTO.setCatalogName("testCatalog");
      tableIdentifierDTO.setDbName("testDb");
      mapper.createTableMeta(tableIdentifierDTO);
      mapper.getTableIdentifier(tableIdentifierDTO.getTableId());
      mapper.getDatabases("testCatalog");
      mapper.getTableCountInCatalog("testCatalog");
      mapper.deleteTableMeta(TableIdentifier.of("testCatalog", "testDb", "testTable"));
    }
  }

  @org.junit.Test
  public void testTableMetadataMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      TableIdentifierMapper tableIdentifierMapper = getMapper(sqlSession, TableIdentifierMapper.class);
      TableIdentifierDTO tableIdentifierDTO = new TableIdentifierDTO();
      tableIdentifierDTO.setTableName("testTable");
      tableIdentifierDTO.setCatalogName("testCatalog");
      tableIdentifierDTO.setDbName("testDb");
      tableIdentifierMapper.createTableMeta(tableIdentifierDTO);

      TableMetadataMapper mapper = getMapper(sqlSession, TableMetadataMapper.class);
      TableMetadata tableMetadata = new TableMetadata();
      tableMetadata.setTableId(tableIdentifierDTO.getTableId());
      tableMetadata.setAuthMethod("simple");
      tableMetadata.setBaseLocation("hdfs://xxx");
      tableMetadata.setChangeLocation("hdfs://xxx");
      tableMetadata.setTableLocation("hdfs://xxx");
      tableMetadata.setTableIdentifier(TableIdentifier.of("testCatalog", "testDb", "testTable"));
      tableMetadata.setPrimaryKey("PrimaryKey");
      // tableMetadata.setMetaStore("ams");
      tableMetadata.setMetaStoreSite("MetaStoreSite");
      tableMetadata.setCoreSite("coresite");
      tableMetadata.setHdfsSite("HdfsSite");
      tableMetadata.setKrbPrincipal("KrbPrincipal");
      tableMetadata.setKrbConf("krbconf");
      tableMetadata.setKrbKeyteb("keytab");
      tableMetadata.setHadoopUsername("sloth");
      tableMetadata.setProperties(new HashMap<>());
      mapper.createTableMeta(tableMetadata);
      mapper.listTableMetas();
      mapper.getTableMetas("testCatalog", "testDb");
      mapper.updateTableProperties(tableIdentifierDTO.getTableId(), new HashMap<>());
      mapper.updateTableTxId(tableIdentifierDTO.getTableId(), 11L);
      mapper.loadTableMeta(tableIdentifierDTO.getTableId());
      mapper.deleteTableMeta(tableIdentifierDTO.getTableId());
    }
  }

  @org.junit.Test
  public void testTableOptimizeRuntimeMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      TableOptimizeRuntimeMapper mapper = getMapper(sqlSession, TableOptimizeRuntimeMapper.class);
      TableOptimizeRuntime tableOptimizeRuntime = new TableOptimizeRuntime();
      tableOptimizeRuntime.setTableId(7);
      tableOptimizeRuntime.setOptimizeStatus(TableOptimizeRuntime.OptimizeStatus.Idle);
      tableOptimizeRuntime.setOptimizeStatusStartTime(System.currentTimeMillis());
      tableOptimizeRuntime.setCurrentChangeSnapshotId(1);
      tableOptimizeRuntime.setCurrentSnapshotId(2);
      tableOptimizeRuntime.setLastMinorOptimizeTime(System.currentTimeMillis());
      tableOptimizeRuntime.setLastMajorOptimizeTime(System.currentTimeMillis());
      tableOptimizeRuntime.setLastFullOptimizeTime(System.currentTimeMillis());
      mapper.insertTableOptimizeRuntime(tableOptimizeRuntime);
      mapper.selectTableOptimizeRuntimes();
      tableOptimizeRuntime.setCurrentSnapshotId(11);
      mapper.updateTableOptimizeRuntime(tableOptimizeRuntime);
      mapper.deleteTableOptimizeRuntime(7);
    }
  }

  @org.junit.Test
  public void testTaskHistoryMapper() {
    try (SqlSession sqlSession = getSqlSession(true)) {
      OptimizeTasksMapper mapper = getMapper(sqlSession, OptimizeTasksMapper.class);
      TableTaskHistory taskHistory = new TableTaskHistory();
      taskHistory.setTableId(7);
      OptimizeTaskId optimizeTaskId = new OptimizeTaskId();
      optimizeTaskId.setProcedureId("3");
      optimizeTaskId.setTaskId(2);
      taskHistory.setOptimizeTaskId(optimizeTaskId);
      taskHistory.setCostTime(111L);
      taskHistory.setStartTime(System.currentTimeMillis());
      taskHistory.setOptimizerGroup(2);
      taskHistory.setRetryNum(4);
      mapper.selectTaskHistory(TableIdentifier.of("testCatalog", "testDb", "testTable"), "2");
      mapper.selectTaskHistoryByTableIdAndTime(TableIdentifier.of("testCatalog", "testDb", "testTable"),
          System.currentTimeMillis()-100000000, System.currentTimeMillis());
      taskHistory.setStatus(OptimizeStatus.Committed);
      mapper.updateTaskHistory(taskHistory);
    }
  }
}
