/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netease.arctic.ams.server.model;

import com.netease.arctic.ams.api.OptimizeStatus;
import com.netease.arctic.table.TableIdentifier;

public class TableTaskHistory {
  private long tableId;
  private OptimizeTaskId optimizeTaskId;

  private OptimizeStatus status = OptimizeStatus.Init;
  private int retryNum = 0;
  private long startTime;
  private long costTime;
  private int optimizerGroup;

  public long getTableId() {
    return tableId;
  }

  public void setTableId(long tableId) {
    this.tableId = tableId;
  }

  public OptimizeTaskId getOptimizeTaskId() {
    return optimizeTaskId;
  }

  public void setOptimizeTaskId(OptimizeTaskId optimizeTaskId) {
    this.optimizeTaskId = optimizeTaskId;
  }

  public OptimizeStatus getStatus() {
    return status;
  }

  public void setStatus(OptimizeStatus status) {
    this.status = status;
  }

  public int getRetryNum() {
    return retryNum;
  }

  public void setRetryNum(int retryNum) {
    this.retryNum = retryNum;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getCostTime() {
    return costTime;
  }

  public void setCostTime(long costTime) {
    this.costTime = costTime;
  }

  public int getOptimizerGroup() {
    return optimizerGroup;
  }

  public void setOptimizerGroup(int optimizerGroup) {
    this.optimizerGroup = optimizerGroup;
  }

  @Override
  public String toString() {
    return "TableTaskHistory{" +
        "tableId=" + tableId +
        ", procedureId='" + optimizeTaskId.getProcedureId() + '\'' +
        ", startTime=" + startTime +
        ", costTime=" + costTime +
        ", optimizerGroup=" + optimizerGroup +
        ", taskId=" + optimizeTaskId.getTaskId() +
        ", retryNum=" + retryNum +
        '}';
  }
}
