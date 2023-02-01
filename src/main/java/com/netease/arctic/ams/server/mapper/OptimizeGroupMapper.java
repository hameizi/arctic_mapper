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

import com.netease.arctic.ams.server.model.OptimizeQueueMeta;
import com.netease.arctic.ams.server.mybatis.Map2StringConverter;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

//TODO OptimizeQueueMapper和OptimizerGroupMapper合并为这一个mapper
public interface OptimizeGroupMapper {
  String TABLE_NAME = "optimize_group";

  @Select("select group_name, properties, container_name from " + TABLE_NAME)
  @Results({
      @Result(property = "groupName", column = "group_name"),
      @Result(property = "properties", column = "properties", typeHandler = Map2StringConverter.class),
      @Result(property = "containerName", column = "container_name")
  })
  List<OptimizeQueueMeta> selectOptimizeQueues();

  @Select("select group_name,  properties, container_name from " + TABLE_NAME + " where group_name = " +
      "#{groupName}")
  @Results({
      @Result(property = "groupName", column = "group_name"),
      @Result(property = "properties", column = "properties", typeHandler = Map2StringConverter.class),
      @Result(property = "containerName", column = "container_name")
  })
  OptimizeQueueMeta selectOptimizeQueue(@Param("groupName") String groupName);

  @Insert("insert into " + TABLE_NAME + " (group_name,properties,container_name) values " +
      "(#{optimizeQueue.groupName}, " +
      "#{optimizeQueue.properties, typeHandler=com.netease.arctic" +
      ".ams.server.mybatis.Map2StringConverter}, #{optimizeQueue.containerName})")
  void insertQueue(@Param("optimizeQueue") OptimizeQueueMeta optimizeQueue);

  //TODO 删除条件由groupId改为groupName
  @Delete("delete from " + TABLE_NAME + " where group_name = #{groupName}")
  void deleteQueue(String groupName);


  @Delete("delete from " + TABLE_NAME)
  void deleteAllQueue();

  @Update("update " + TABLE_NAME + " set" +
      " properties = #{optimizeQueue.properties, typeHandler=com.netease.arctic.ams.server.mybatis" +
      ".Map2StringConverter}," +
      " container_name = #{optimizeQueue.containerName}" +
      " where group_name = #{optimizeQueue.groupName}")
  void updateQueue(@Param("optimizeQueue") OptimizeQueueMeta optimizeQueue);
}