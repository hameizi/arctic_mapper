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

import com.netease.arctic.ams.server.model.Optimizer;
import com.netease.arctic.ams.server.model.OptimizerResourceInfo;
import com.netease.arctic.ams.server.model.TableTaskStatus;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.ams.server.mybatis.Map2StringConverter;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * optimize mapper.
 */
@Mapper
public interface OptimizerMapper {
  String TABLE_NAME = "optimizer";

  @Select(
      "select optimizer_id,group_name,optimizer_start_time,optimizer_touch_time,optimizer_status,thread_count,total_memory" +
          " from " + TABLE_NAME +
          " where group_name = #{groupName} and (optimizer_status = 'RUNNING' or optimizer_status = 'STARTING')")
  @Results({
      @Result(property = "optimizerId", column = "optimizer_id"),
      @Result(column = "groupName", property = "group_name"),
      @Result(column = "optimizerStartTime", property = "optimizer_start_time", typeHandler = Long2TsConvertor.class),
      @Result(column = "optimizerTouchTime", property = "optimizer_touch_time", typeHandler = Long2TsConvertor.class),
      @Result(column = "optimizerStatus", property = "optimizer_status"),
      @Result(column = "threadCount", property = "thread_count"),
      @Result(column = "totalMemory", property = "total_memory")
  })
  List<Optimizer> selectOptimizersByGroupName(String groupName);

  @Select(
      "select optimizer_id,group_name,optimizer_start_time,optimizer_touch_time,optimizer_status,thread_count,total_memory" +
          " from " + TABLE_NAME + " where optimizer_status = 'RUNNING' or optimizer_status = 'STARTING'")
  @Results({
      @Result(property = "optimizerId", column = "optimizer_id"),
      @Result(property = "groupName", column = "group_name"),
      @Result(property = "optimizerStartTime", column = "optimizer_start_time", typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizerTouchTime", column = "optimizer_touch_time", typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizerStatus", column = "optimizer_status"),
      @Result(property = "threadCount", column = "thread_count"),
      @Result(property = "totalMemory", column = "total_memory")
  })
  List<Optimizer> selectOptimizers();

  @Select("select sum(thread_count) as occupationCore," +
      "sum(total_memory) as occupationMemory from " + TABLE_NAME + " where optimizer_status = 'RUNNING' or " +
      "optimizer_status = 'STARTING'")
  OptimizerResourceInfo selectOptimizerGroupResourceInfo();

  @Select("select sum(thread_count) as occupationCore," +
      "sum(total_memory) as occupationMemory from " + TABLE_NAME +
      " where group_name = #{groupName} " +
      "and (optimizer_status = 'RUNNING' or " +
      "optimizer_status = 'STARTING')")
  OptimizerResourceInfo selectOptimizerGroupResourceInfoByGroupName(String groupName);

  @Insert("insert into " + TABLE_NAME +
      "(group_name,optimizer_start_time,optimizer_touch_time,optimizer_status,thread_count,total_memory) values (" +
      "#{optimizer.groupName}, #{optimizer.optimizerStartTime, typeHandler=com.netease.arctic.ams.server.mybatis" +
      ".Long2TsConvertor}, #{optimizer.optimizerTouchTime, typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor}, #{optimizer" +
      ".optimizerStatus}, #{optimizer.threadCount}, #{optimizer.totalMemory})")
  @Options(useGeneratedKeys = true, keyProperty = "optimizer.optimizerId")
  void insertOptimizer(@Param("optimizer") Optimizer optimizer);

  @Select(
      "select optimizer_id,group_name,optimizer_start_time,optimizer_touch_time,optimizer_status,thread_count,total_memory " +
          "from " + TABLE_NAME + " where optimizer_id = #{optimizerId}")
  @Results({
      @Result(property = "optimizerId", column = "optimizer_id"),
      @Result(property = "groupName", column = "group_name"),
      @Result(property = "optimizerStartTime", column = "optimizer_start_time", typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizerTouchTime", column = "optimizer_touch_time", typeHandler = Long2TsConvertor.class),
      @Result(property = "optimizerStatus", column = "optimizer_status"),
      @Result(property = "threadCount", column = "thread_count"),
      @Result(property = "totalMemory", column = "total_memory")
  })
  Optimizer selectOptimizer(@Param("optimizerId") Long optimizerId);

  @Update("update " + TABLE_NAME + " set" +
      " optimizer_state_info = #{state,typeHandler=com.netease.arctic.ams" +
      ".server.mybatis.Map2StringConverter}, optimizer_status = #{status}, optimizer_touch_time = CURRENT_TIMESTAMP" +
      " where optimizer_id = #{optimizerId}")
  void updateOptimizerState(
      @Param("optimizerId") Long optimizerId, @Param("state") Map state, @Param("status") String status);

  @Update("update " + TABLE_NAME + " set optimizer_status = #{status} where optimizer_id = #{optimizerId}")
  void updateOptimizerStatus(@Param("optimizerId") Long optimizerId, @Param("status") String status);

  @Delete("delete from " + TABLE_NAME + " where optimizer_id = #{optimizerId}")
  void deleteOptimizer(@Param("optimizerId") Long optimizerId);
}

