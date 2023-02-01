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

import com.netease.arctic.ams.server.model.BaseOptimizeTaskRuntime;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface OptimizeTaskRuntimesMapper {
  String TABLE_NAME = "optimizing_task";

  @Select("select procedure_id,task_id, task_type, status, start_time, cost_time, " +
      " thread_id,optimizer_id, retry_num, fail_reason, new_file_size, new_file_count from" +
      " (SELECT s.*," +
      "IF(@pre_procedure_id = s.procedure_id and @pre_task_id = s.task_id, @cur_rank := @cur_rank + 1, @cur_rank := 1) ranking," +
      "@pre_procedure_id := s.procedure_id,@pre_task_id := s.task_id" +
      " FROM " + TABLE_NAME + " s , (SELECT @cur_rank := 0, @pre_procedure_id := NULL,@pre_task_id := NULL) r" +
      " ORDER BY procedure_id,task_id,retry_num desc) aa" +
      " where ranking=1")
  @Results({
      @Result(property = "taskId.procedureId", column = "procedure_id"),
      @Result(property = "taskId.taskId", column = "task_id"),
      @Result(property = "status", column = "status"),
      @Result(property = "startTime", column = "start_time",
          typeHandler = Long2TsConvertor.class),
      @Result(property = "costTime", column = "cost_time"),
      @Result(property = "threadId", column = "thread_id"),
      @Result(property = "optimizerId", column = "optimizer_id"),
      @Result(property = "retryCount", column = "retry_count"),
      @Result(property = "failReason", column = "fail_reason"),
      @Result(property = "newFileSize", column = "new_file_size"),
      @Result(property = "newFileCount", column = "new_file_count")
  })
  List<BaseOptimizeTaskRuntime> selectAllOptimizeTaskRuntimes();

  // @Update("update " + TABLE_NAME + " set" +
  //     " status = #{optimizeTaskRuntime.status}," +
  //     " thread_id = #{optimizeTaskRuntime.threadId}," +
  //     "optimizer_id = #{optimizeTaskRuntime.optimizerId, jdbcType=VARCHAR}," +
  //     " retry_count = #{optimizeTaskRuntime.retryCount}," +
  //     " fail_reason = #{optimizeTaskRuntime.failReason, jdbcType=VARCHAR}," +
  //     " new_file_size = #{optimizeTaskRuntime.newFileSize}," +
  //     " new_file_count = #{optimizeTaskRuntime.newFileCount}," +
  //     " cost_time = #{optimizeTaskRuntime.costTime}" +
  //     " where procedure_id = #{optimizeTaskRuntime.taskId.procedureId} and task_id = #{optimizeTaskRuntime.taskId" +
  //     ".taskId}")
  // void updateOptimizeTaskRuntime(
  //     @Param("optimizeTaskRuntime") BaseOptimizeTaskRuntime optimizeTaskRuntime);
}

