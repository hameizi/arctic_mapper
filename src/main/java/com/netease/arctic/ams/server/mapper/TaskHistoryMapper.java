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

import com.netease.arctic.ams.server.model.TableTaskHistory;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.table.TableIdentifier;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TaskHistoryMapper {
  String TABLE_NAME = "optimizing_task";

  @Select("select optimizing_task.table_id,procedure_id, task_id, retry_num, catalog_name, db_name, " +
      "table_name,start_time, " +
      "cost_time, optimizer_group from " + TABLE_NAME + " inner join table_identifier on " +
      "optimizing_task.table_id=table_identifier.table_id where " +
      "catalog_name = #{tableIdentifier.catalog} and db_name = #{tableIdentifier.database} " +
      "and table_name = #{tableIdentifier.tableName} and procedure_id = #{procedureId}")
  @Results({
      @Result(column = "procedure_id", property = "optimizeTaskId.procedureId"),
      @Result(column = "task_id", property = "optimizeTaskId.taskId"),
      @Result(column = "retry_num", property = "retryNum"),
      @Result(column = "table_id", property = "tableId"),
      @Result(column = "optimizer_group", property = "optimizerGroup"),
      @Result(column = "start_time", property = "startTime",
          typeHandler = Long2TsConvertor.class),
      @Result(column = "cost_time", property = "costTime"),
      @Result(column = "optimizer_group", property = "optimizerGroup")
  })
  List<TableTaskHistory> selectTaskHistory(@Param("tableIdentifier") TableIdentifier tableIdentifier,
                                           @Param("procedureId") String procedureId);

  // @Insert("insert into " + TABLE_NAME + "(procedure_id, task_id, retry_num, table_id," +
  //     "start_time, cost_time, optimizer_group) values ( " +
  //     "#{taskHistory.optimizeTaskId.procedureId}, " +
  //     "#{taskHistory.optimizeTaskId.taskId}, " +
  //     "#{taskHistory.retryNum}, " +
  //     "#{taskHistory.tableId}, " +
  //     "#{taskHistory.startTime, " +
  //     "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
  //     "#{taskHistory.costTime}, " +
  //     "#{taskHistory.optimizerGroup}) ")
  // void insertTaskHistory(@Param("taskHistory") TableTaskHistory taskHistory);

  @Update("update " + TABLE_NAME + " set " +
      "start_time = #{taskHistory.startTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, " +
      "cost_time = #{taskHistory.costTime} " +
      "where " +
      "procedure_id = #{taskHistory.optimizeTaskId.procedureId} and task_id = #{taskHistory.optimizeTaskId.taskId} " +
      "and retry_num = " +
      "#{taskHistory.retryNum}")
  void updateTaskHistory(@Param("taskHistory") TableTaskHistory taskHistory);

  @Select("select optimizing_task.table_id, procedure_id, task_id, retry_num, catalog_name, db_name, " +
      "table_name,  " +
      "start_time, cost_time, optimizer_group from " + TABLE_NAME + " inner join table_identifier on " +
      " optimizing_task.table_id=table_identifier.table_id where " +
      "catalog_name = #{tableIdentifier.catalog} and " +
      "db_name = #{tableIdentifier.database} and " +
      "table_name = #{tableIdentifier.tableName} " +
      "and ((unix_timestamp(start_time)*1000+cost_time) > #{startTime})" +
      " and unix_timestamp(start_time)*1000 < #{endTime}")
  @Results({
      @Result(column = "table_id", property = "tableId"),
      @Result(column = "procedure_id", property = "optimizeTaskId.procedureId"),
      @Result(column = "task_id", property = "optimizeTaskId.taskId"),
      @Result(column = "retry_num", property = "retryNum"),
      @Result(column = "table_id", property = "tableId"),
      @Result(column = "start_time", property = "startTime",
          typeHandler = Long2TsConvertor.class),
      @Result(column = "cost_time", property = "costTime"),
      @Result(column = "optimizer_group", property = "optimizerGroup")
  })
  List<TableTaskHistory> selectTaskHistoryByTableIdAndTime(@Param("tableIdentifier") TableIdentifier tableIdentifier,
                                                           @Param("startTime") long startTime,
                                                           @Param("endTime") long endTime);

  // @Delete("delete from " + TABLE_NAME + " where " +
  //     "table_id = #{tableId}")
  // void deleteTaskHistory(@Param("tableId") long tableId);
  //
  //
  // @Delete("delete from " + TABLE_NAME + " where " +
  //     "table_id = #{tableId} " +
  //     "and unix_timestamp(start_time)-cost_time < #{expireTime}")
  // void expireTaskHistory(@Param("tableId") long tableId,
  //                        @Param("expireTime") long expireTime);
}
