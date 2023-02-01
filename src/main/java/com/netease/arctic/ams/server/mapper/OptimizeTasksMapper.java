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

import com.netease.arctic.ams.server.model.BaseOptimizeTask;
import com.netease.arctic.ams.server.model.BaseOptimizeTaskRuntime;
import com.netease.arctic.ams.server.model.OptimizeTaskId;
import com.netease.arctic.ams.server.mybatis.ListOfTreeNode2StringConverter;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.ams.server.mybatis.Map2StringConverter;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface OptimizeTasksMapper {
  String TABLE_NAME = "optimizing_task";

  @Select("select procedure_id,task_id, task_type, table_id, `partition`," +
      "  max_change_transaction_id, min_change_transaction_id, " +
      " create_time, optimizer_id, thread_id," +
      " insert_file_size, delete_file_size, base_file_size, pos_delete_file_size," +
      " insert_file_count, delete_file_count, base_file_count, pos_delete_file_count, source_nodes, properties" +
      " from (SELECT s.*," +
      "IF(@pre_procedure_id = s.procedure_id and @pre_task_id = s.task_id, @cur_rank := @cur_rank + 1, @cur_rank := 1) ranking," +
      "@pre_procedure_id := s.procedure_id,@pre_task_id := s.task_id" +
      " FROM " + TABLE_NAME + " s , (SELECT @cur_rank := 0, @pre_procedure_id := NULL,@pre_task_id := NULL) r" +
      " ORDER BY procedure_id,task_id,retry_num desc) aa" +
      " where ranking=1")
  @Results({
      @Result(property = "taskId.procedureId", column = "procedure_id"),
      @Result(property = "taskId.taskId", column = "task_id"),
      @Result(property = "taskType", column = "task_type"),
      @Result(property = "tableId", column = "table_id"),
      @Result(property = "partition", column = "partition"),
      @Result(property = "maxChangeTransactionId", column = "max_change_transaction_id"),
      @Result(property = "minChangeTransactionId", column = "min_change_transaction_id"),
      @Result(property = "createTime", column = "create_time",
          typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizerId", column = "optimizer_id"),
      @Result(property = "threadId", column = "thread_id"),
      @Result(property = "insertFileSize", column = "insert_file_size"),
      @Result(property = "deleteFileSize", column = "delete_file_size"),
      @Result(property = "baseFileSize", column = "base_file_size"),
      @Result(property = "posDeleteFileSize", column = "pos_delete_file_size"),
      @Result(property = "insertFileCount", column = "insert_file_count"),
      @Result(property = "deleteFileCount", column = "delete_file_count"),
      @Result(property = "baseFileCount", column = "base_file_count"),
      @Result(property = "posDeleteFileCount", column = "pos_delete_file_count"),
      @Result(property = "sourceNodes", column = "source_nodes",
          typeHandler = ListOfTreeNode2StringConverter.class),
      @Result(property = "properties", column = "properties",
          typeHandler = Map2StringConverter.class)
  })
  List<BaseOptimizeTask> selectAllOptimizeTasks();

  @Insert("insert into " + TABLE_NAME + " (" +
      " procedure_id, task_id, retry_num,task_type, table_id, `partition`," +
      " max_change_transaction_id, min_change_transaction_id," +
      " create_time, start_time, cost_time, status, fail_reason,optimizer_group, optimizer_id, thread_id," +
      " insert_file_size, delete_file_size, base_file_size, pos_delete_file_size," +
      " insert_file_count, delete_file_count, base_file_count, pos_delete_file_count," +
      " source_nodes, new_file_size,new_file_count,properties)" +
      " values(" +
      " #{optimizeTask.taskId.procedureId}," +
      " #{optimizeTask.taskId.taskId}," +
      " #{optimizeTaskRuntime.retryNum}," +
      " #{optimizeTask.taskType}," +
      " #{optimizeTask.tableId}," +
      " #{optimizeTask.partition}," +
      " #{optimizeTask.maxChangeTransactionId}," +
      " #{optimizeTask.minChangeTransactionId}," +
      " #{optimizeTask.createTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
      " #{optimizeTaskRuntime.startTime, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}," +
      " #{optimizeTaskRuntime.costTime}," +
      " #{optimizeTaskRuntime.status}, " +
      " #{optimizeTaskRuntime.failReason}, " +
      " #{optimizeTask.optimizerGroup}," +
      " #{optimizeTask.optimizerId}," +
      " #{optimizeTask.threadId}," +
      " #{optimizeTask.insertFileSize}," +
      " #{optimizeTask.deleteFileSize}," +
      " #{optimizeTask.baseFileSize}," +
      " #{optimizeTask.posDeleteFileSize}," +
      " #{optimizeTask.insertFileCount}," +
      " #{optimizeTask.deleteFileCount}," +
      " #{optimizeTask.baseFileCount}," +
      " #{optimizeTask.posDeleteFileCount}," +
      " #{optimizeTask.sourceNodes, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.ListOfTreeNode2StringConverter}," +
      " #{optimizeTaskRuntime.newFileSize}," +
      " #{optimizeTaskRuntime.newFileCount}," +
      " #{optimizeTask.properties, " +
      "typeHandler=com.netease.arctic.ams.server.mybatis.Map2StringConverter}" +
      " )")
  void insertOptimizeTask(
      @Param("optimizeTask") BaseOptimizeTask optimizeTask,
      @Param("optimizeTaskRuntime") BaseOptimizeTaskRuntime optimizeTaskRuntime);

  @Delete("delete from " + TABLE_NAME + " where procedure_id = #{optimizeTaskId.procedureId} and task_id = " +
      "#{optimizeTaskId.taskId}")
  void deleteOptimizeTask(@Param("optimizeTaskId") OptimizeTaskId optimizeTaskId);
}
