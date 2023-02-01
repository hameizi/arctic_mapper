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

import com.netease.arctic.ams.server.model.OptimizeHistory;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.ams.server.mybatis.Map2StringConverter;
import com.netease.arctic.table.TableIdentifier;
import java.sql.Timestamp;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface OptimizeHistoryMapper {
  String TABLE_NAME = "table_optimizing_procedure";

  @Select("select procedure_id, table_id,status, optimizing_type, " +
      " plan_time, commit_time, fail_reason, duration, summary from " + TABLE_NAME + " where " +
      "table_id = #{tableId}")
  @Results({
      @Result(property = "procedureId", column = "procedure_id"),
      @Result(column = "table_id", property = "tableId"),
      @Result(column = "status", property = "status"),
      @Result(column = "optimizing_type", property = "optimizingType"),
      @Result(column = "plan_time", property = "planTime", typeHandler = Long2TsConvertor.class),
      @Result(column = "commit_time", property = "commitTime", typeHandler = Long2TsConvertor.class),
      @Result(column = "fail_reason", property = "failReason"),
      @Result(column = "duration", property = "duration"),
      @Result(column = "summary", property = "summary", typeHandler = Map2StringConverter.class)
  })
  List<OptimizeHistory> selectOptimizeHistory(@Param("tableId") long tableId);

  @Select("select max(commit_time) from " + TABLE_NAME + " where " +
      "table_id = #{tableId}")
  Timestamp latestCommitTime(@Param("tableId") long tableId);

  @Delete("delete from " + TABLE_NAME + " where table_id = #{tableId}")
  void deleteOptimizeRecord(@Param("tableId") long tableId);

  @Insert(
      "insert into " + TABLE_NAME + " (procedure_id, table_id,status, optimizing_type," +
          "plan_time, commit_time, fail_reason, duration, summary) values (" +
          "#{optimizeHistory.procedureId}, " +
          "#{optimizeHistory.tableId}, " +
          "#{optimizeHistory.status}, " +
          "#{optimizeHistory.optimizingType}, " +
          "#{optimizeHistory.planTime, " +
          "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
          "#{optimizeHistory.commitTime, " +
          "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
          "#{optimizeHistory.failReason}, " +
          "#{optimizeHistory.duration}, " +
          "#{optimizeHistory.summary," +
          "typeHandler=com.netease.arctic.ams.server.mybatis.Map2StringConverter} " +
          ")")
  void insertOptimizeHistory(@Param("optimizeHistory") OptimizeHistory optimizeHistory);

  @Select("select max(procedure_id) from " + TABLE_NAME)
  Long maxOptimizeHistoryId();

  @Delete("delete from " + TABLE_NAME + " where " +
      "table_id = #{tableId} " +
      "and commit_time < #{expireTime, typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}")
  void expireOptimizeHistory(
      @Param("tableId") long tableId,
      @Param("expireTime") long expireTime);

}
