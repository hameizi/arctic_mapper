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

import com.netease.arctic.table.TableIdentifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.collections.MapUtils;

public class TableOptimizeRuntime {
  public static final long INVALID_SNAPSHOT_ID = -1L;
  private long tableId;
  // for unKeyedTable or base table
  private long currentSnapshotId = INVALID_SNAPSHOT_ID;
  // for change table
  private long currentChangeSnapshotId = INVALID_SNAPSHOT_ID;
  private OptimizeStatus optimizeStatus = OptimizeStatus.Idle;
  private long optimizeStatusStartTime = -1;

  private Long lastMajorOptimizeTime;
  private Long lastFullOptimizeTime;
  private Long lastMinorOptimizeTime;

  public TableOptimizeRuntime() {
  }

  public TableOptimizeRuntime(long tableId) {
    this.tableId = tableId;
  }

  public long getTableId() {
    return tableId;
  }

  public void setTableId(long tableId) {
    this.tableId = tableId;
  }

  public long getCurrentSnapshotId() {
    return currentSnapshotId;
  }

  public void setCurrentSnapshotId(long currentSnapshotId) {
    this.currentSnapshotId = currentSnapshotId;
  }


  public OptimizeStatus getOptimizeStatus() {
    return optimizeStatus;
  }

  public void setOptimizeStatus(
      OptimizeStatus optimizeStatus) {
    this.optimizeStatus = optimizeStatus;
  }

  public long getOptimizeStatusStartTime() {
    return optimizeStatusStartTime;
  }

  public void setOptimizeStatusStartTime(long optimizeStatusStartTime) {
    this.optimizeStatusStartTime = optimizeStatusStartTime;
  }

  public Long getLastMajorOptimizeTime() {
    return lastMajorOptimizeTime;
  }

  public void setLastMajorOptimizeTime(Long lastMajorOptimizeTime) {
    this.lastMajorOptimizeTime = lastMajorOptimizeTime;
  }

  public Long getLastFullOptimizeTime() {
    return lastFullOptimizeTime;
  }

  public void setLastFullOptimizeTime(Long lastFullOptimizeTime) {
    this.lastFullOptimizeTime = lastFullOptimizeTime;
  }

  public Long getLastMinorOptimizeTime() {
    return lastMinorOptimizeTime;
  }

  public void setLastMinorOptimizeTime(Long lastMinorOptimizeTime) {
    this.lastMinorOptimizeTime = lastMinorOptimizeTime;
  }

  public long getCurrentChangeSnapshotId() {
    return currentChangeSnapshotId;
  }

  public void setCurrentChangeSnapshotId(long currentChangeSnapshotId) {
    this.currentChangeSnapshotId = currentChangeSnapshotId;
  }


  @Override
  public String toString() {
    return "TableOptimizeRuntime{" +
        "tableId=" + tableId +
        ", currentSnapshotId=" + currentSnapshotId +
        ", currentChangeSnapshotId=" + currentChangeSnapshotId +
        ", optimizeStatus=" + optimizeStatus +
        ", optimizeStatusStartTime=" + optimizeStatusStartTime +
        ", lastMajorOptimizeTime=" + lastMajorOptimizeTime +
        ", lastFullOptimizeTime=" + lastFullOptimizeTime +
        ", lastMinorOptimizeTime=" + lastMinorOptimizeTime +
        '}';
  }

  public enum OptimizeStatus {
    FullOptimizing("full"),
    MajorOptimizing("major"),
    MinorOptimizing("minor"),
    Pending("pending"),
    Idle("idle");

    private String displayValue;

    OptimizeStatus(String displayValue) {
      this.displayValue = displayValue;
    }

    public String displayValue() {
      return displayValue;
    }
  }
}
