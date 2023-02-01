package com.netease.arctic.ams.server.model;

import java.util.Map;

public class Optimizer {
  private long optimizerId;
  private String groupName;
  private long optimizerStartTime;
  private long optimizerTouchTime;
  private String optimizerStatus;
  private Integer threadCount;
  private Long totalMemory;
  private Map<String, String> optimizerStateInfo;

  public long getOptimizerId() {
    return optimizerId;
  }

  public void setOptimizerId(long optimizerId) {
    this.optimizerId = optimizerId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public long getOptimizerStartTime() {
    return optimizerStartTime;
  }

  public void setOptimizerStartTime(long optimizerStartTime) {
    this.optimizerStartTime = optimizerStartTime;
  }

  public long getOptimizerTouchTime() {
    return optimizerTouchTime;
  }

  public void setOptimizerTouchTime(long optimizerTouchTime) {
    this.optimizerTouchTime = optimizerTouchTime;
  }

  public String getOptimizerStatus() {
    return optimizerStatus;
  }

  public void setOptimizerStatus(String optimizerStatus) {
    this.optimizerStatus = optimizerStatus;
  }

  public Integer getThreadCount() {
    return threadCount;
  }

  public void setThreadCount(Integer threadCount) {
    this.threadCount = threadCount;
  }

  public Long getTotalMemory() {
    return totalMemory;
  }

  public void setTotalMemory(Long totalMemory) {
    this.totalMemory = totalMemory;
  }

  public Map<String, String> getOptimizerStateInfo() {
    return optimizerStateInfo;
  }

  public void setOptimizerStateInfo(Map<String, String> optimizerStateInfo) {
    this.optimizerStateInfo = optimizerStateInfo;
  }
}
