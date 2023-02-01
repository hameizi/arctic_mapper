package com.netease.arctic.ams.server.model;

import com.netease.arctic.ams.api.OptimizeStatus;
import java.util.Map;

public class BaseOptimizeTaskRuntime {
  private OptimizeTaskId taskId;
  private long startTime;
  private Long costTime;
  private OptimizeStatus status = OptimizeStatus.Init;
  private String failReason;
  private Long optimizerId;
  private Integer threadId;
  private Integer retryNum;
  private Long newFileSize;
  private Integer newFileCount;

  public OptimizeTaskId getTaskId() {
    return taskId;
  }

  public void setTaskId(OptimizeTaskId taskId) {
    this.taskId = taskId;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public Long getCostTime() {
    return costTime;
  }

  public void setCostTime(Long costTime) {
    this.costTime = costTime;
  }

  public OptimizeStatus getStatus() {
    return status;
  }

  public void setStatus(OptimizeStatus status) {
    this.status = status;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public Long getOptimizerId() {
    return optimizerId;
  }

  public void setOptimizerId(Long optimizerId) {
    this.optimizerId = optimizerId;
  }

  public Integer getThreadId() {
    return threadId;
  }

  public void setThreadId(Integer threadId) {
    this.threadId = threadId;
  }

  public Integer getRetryNum() {
    return retryNum;
  }

  public void setRetryNum(Integer retryNum) {
    this.retryNum = retryNum;
  }

  public Long getNewFileSize() {
    return newFileSize;
  }

  public void setNewFileSize(Long newFileSize) {
    this.newFileSize = newFileSize;
  }

  public Integer getNewFileCount() {
    return newFileCount;
  }

  public void setNewFileCount(Integer newFileCount) {
    this.newFileCount = newFileCount;
  }
}
