package com.netease.arctic.ams.server.model;

import java.util.List;
import java.util.Map;

public class BaseOptimizeTask {
  private OptimizeTaskId taskId;
  private String taskType;
  private long tableId;
  private String partition;
  private long maxChangeTransactionId;
  private long minChangeTransactionId;
  private long createTime;
  private Long optimizerId;

  private Integer optimizerGroup;
  private Integer threadId;
  private Integer insertFileCount;
  private Integer deleteFileCount;
  private Integer baseFileCount;
  private Integer posDeleteFileCount;
  private Long insertFileSize;
  private Long deleteFileSize;
  private Long baseFileSize;
  private Long posDeleteFileSize;
  private List<TreeNode> sourceNodes;
  private Map<String, String> properties;

  public OptimizeTaskId getTaskId() {
    return taskId;
  }

  public void setTaskId(OptimizeTaskId taskId) {
    this.taskId = taskId;
  }

  public String getTaskType() {
    return taskType;
  }

  public void setTaskType(String taskType) {
    this.taskType = taskType;
  }

  public long getTableId() {
    return tableId;
  }

  public void setTableId(long tableId) {
    this.tableId = tableId;
  }

  public String getPartition() {
    return partition;
  }

  public void setPartition(String partition) {
    this.partition = partition;
  }

  public long getMaxChangeTransactionId() {
    return maxChangeTransactionId;
  }

  public void setMaxChangeTransactionId(long maxChangeTransactionId) {
    this.maxChangeTransactionId = maxChangeTransactionId;
  }

  public long getMinChangeTransactionId() {
    return minChangeTransactionId;
  }

  public void setMinChangeTransactionId(long minChangeTransactionId) {
    this.minChangeTransactionId = minChangeTransactionId;
  }

  public long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(long createTime) {
    this.createTime = createTime;
  }

  public Long getOptimizerId() {
    return optimizerId;
  }

  public void setOptimizerId(Long optimizerId) {
    this.optimizerId = optimizerId;
  }

  public Integer getOptimizerGroup() {
    return optimizerGroup;
  }

  public void setOptimizerGroup(Integer optimizerGroup) {
    this.optimizerGroup = optimizerGroup;
  }

  public Integer getThreadId() {
    return threadId;
  }

  public void setThreadId(Integer threadId) {
    this.threadId = threadId;
  }

  public Integer getInsertFileCount() {
    return insertFileCount;
  }

  public void setInsertFileCount(Integer insertFileCount) {
    this.insertFileCount = insertFileCount;
  }

  public Integer getDeleteFileCount() {
    return deleteFileCount;
  }

  public void setDeleteFileCount(Integer deleteFileCount) {
    this.deleteFileCount = deleteFileCount;
  }

  public Integer getBaseFileCount() {
    return baseFileCount;
  }

  public void setBaseFileCount(Integer baseFileCount) {
    this.baseFileCount = baseFileCount;
  }

  public Integer getPosDeleteFileCount() {
    return posDeleteFileCount;
  }

  public void setPosDeleteFileCount(Integer posDeleteFileCount) {
    this.posDeleteFileCount = posDeleteFileCount;
  }

  public Long getInsertFileSize() {
    return insertFileSize;
  }

  public void setInsertFileSize(Long insertFileSize) {
    this.insertFileSize = insertFileSize;
  }

  public Long getDeleteFileSize() {
    return deleteFileSize;
  }

  public void setDeleteFileSize(Long deleteFileSize) {
    this.deleteFileSize = deleteFileSize;
  }

  public Long getBaseFileSize() {
    return baseFileSize;
  }

  public void setBaseFileSize(Long baseFileSize) {
    this.baseFileSize = baseFileSize;
  }

  public Long getPosDeleteFileSize() {
    return posDeleteFileSize;
  }

  public void setPosDeleteFileSize(Long posDeleteFileSize) {
    this.posDeleteFileSize = posDeleteFileSize;
  }

  public List<TreeNode> getSourceNodes() {
    return sourceNodes;
  }

  public void setSourceNodes(List<TreeNode> sourceNodes) {
    this.sourceNodes = sourceNodes;
  }

  public Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<String, String> properties) {
    this.properties = properties;
  }
}
