package com.netease.arctic.ams.server.model;

import java.util.Map;

public class OptimizeHistory {
  private String procedureId;
  private long tableId;
  private String status;
  private String optimizingType;
  private long planTime;

  private long commitTime;
  private String failReason;
  private long duration;
  private Map<String, String> summary;

  public String getProcedureId() {
    return procedureId;
  }

  public void setProcedureId(String procedureId) {
    this.procedureId = procedureId;
  }

  public long getTableId() {
    return tableId;
  }

  public void setTableId(long tableId) {
    this.tableId = tableId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOptimizingType() {
    return optimizingType;
  }

  public void setOptimizingType(String optimizingType) {
    this.optimizingType = optimizingType;
  }

  public long getPlanTime() {
    return planTime;
  }

  public void setPlanTime(long planTime) {
    this.planTime = planTime;
  }

  public long getCommitTime() {
    return commitTime;
  }

  public void setCommitTime(long commitTime) {
    this.commitTime = commitTime;
  }

  public String getFailReason() {
    return failReason;
  }

  public void setFailReason(String failReason) {
    this.failReason = failReason;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public Map<String, String> getSummary() {
    return summary;
  }

  public void setSummary(Map<String, String> summary) {
    this.summary = summary;
  }
}
