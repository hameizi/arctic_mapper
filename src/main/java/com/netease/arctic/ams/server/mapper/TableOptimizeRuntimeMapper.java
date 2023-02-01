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

package com.netease.arctic.ams.server.mapper;

import com.netease.arctic.ams.server.model.TableOptimizeRuntime;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.table.TableIdentifier;
import java.util.List;
import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TableOptimizeRuntimeMapper {
  String TABLE_NAME = "table_optimizing_runtime";

  @Select("select table_id, current_snapshot_id, current_change_snapshotId," +
      " last_major_optimizing_time, last_full_optimizing_time, last_minor_optimizing_time," +
      " optimizing_status, optimizing_status_start_time " +
      " from " + TABLE_NAME)
  @Results({
      @Result(property = "tableId", column = "table_id"),
      @Result(property = "currentSnapshotId", column = "current_snapshot_id"),
      @Result(property = "currentChangeSnapshotId", column = "current_change_snapshotId"),
      @Result(property = "lastMajorOptimizeTime", column = "last_major_optimizing_time",
          typeHandler = Long2TsConvertor.class),
      @Result(property = "lastFullOptimizeTime", column = "last_full_optimizing_time",
          typeHandler = Long2TsConvertor.class),
      @Result(property = "lastMinorOptimizeTime", column = "last_minor_optimizing_time",
          typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizeStatus", column = "optimizing_status"),
      @Result(property = "optimizeStatusStartTime", column = "optimizing_status_start_time",
          typeHandler = Long2TsConvertor.class)
  })
  List<TableOptimizeRuntime> selectTableOptimizeRuntimes();

  @Update("update " + TABLE_NAME + "  set " +
      "current_snapshot_id = #{runtime.currentSnapshotId}, " +
      "current_change_snapshotId = #{runtime.currentChangeSnapshotId}, " +
      "last_major_optimizing_time = #{runtime.lastMajorOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
      "last_full_optimizing_time = #{runtime.lastFullOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
      "last_minor_optimizing_time = #{runtime.lastMinorOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
      "optimizing_status = #{runtime.optimizeStatus}, " +
      "optimizing_status_start_time = #{runtime.optimizeStatusStartTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor} " +
      "where " +
      "table_id = #{runtime.tableId}")
  void updateTableOptimizeRuntime(@Param("runtime") TableOptimizeRuntime runtime);

  @Delete("delete from " + TABLE_NAME + " where " +
      "table_id = #{tableId}")
  void deleteTableOptimizeRuntime(@Param("tableId") long tableId);

  @Insert("insert into " + TABLE_NAME + " (table_id, " +
      "current_snapshot_id, current_change_snapshotId, last_major_optimizing_time, last_full_optimizing_time, " +
      "last_minor_optimizing_time, optimizing_status, optimizing_status_start_time) values ( " +
      "#{runtime.tableId}, " +
      "#{runtime.currentSnapshotId}, " +
      "#{runtime.currentChangeSnapshotId}, " +
      "#{runtime.lastMajorOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
      "#{runtime.lastFullOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
      "#{runtime.lastMinorOptimizeTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
      "#{runtime.optimizeStatus}," +
      "#{runtime.optimizeStatusStartTime," +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}" +
      ") ")
  void insertTableOptimizeRuntime(@Param("runtime") TableOptimizeRuntime runtime);
}
