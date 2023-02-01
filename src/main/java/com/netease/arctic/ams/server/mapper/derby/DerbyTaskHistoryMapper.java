package com.netease.arctic.ams.server.mapper.derby;

import com.netease.arctic.ams.server.mapper.TaskHistoryMapper;
import com.netease.arctic.ams.server.model.TableTaskHistory;
import com.netease.arctic.ams.server.mybatis.Long2TsConvertor;
import com.netease.arctic.table.TableIdentifier;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface DerbyTaskHistoryMapper extends TaskHistoryMapper {

  String TABLE_NAME = "optimizing_quota_history";

  @Select("select optimizing_quota_history.table_id, procedure_id, task_id, retry_num, catalog_name, db_name, " +
      "table_name, optimizer_group, " +
      "start_time, cost_time, optimizer_group from " + TABLE_NAME + " inner join table_identifier on " +
      " optimizing_quota_history.table_id=table_identifier.table_id where " +
      "catalog_name = #{tableIdentifier.catalog} and " +
      "db_name = #{tableIdentifier.database} and " +
      "table_name = #{tableIdentifier.tableName} " +
      "and timestampadd(SQL_TSI_DAY,1,start_time) > #{startTime, " +
      "typeHandler=com" +
      ".netease.arctic" +
      ".ams" +
      ".server" +
      ".mybatis.Long2TsConvertor} " +
      "and start_time < #{endTime, typeHandler=com.netease.arctic.ams.server.mybatis.Long2TsConvertor} ")
  @Results({
      @Result(column = "table_id", property = "tableId"),
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
  List<TableTaskHistory> selectTaskHistoryByTableIdAndTime(@Param("tableIdentifier") TableIdentifier tableIdentifier,
      @Param("startTime") long startTime,
      @Param("endTime") long endTime);

  @Delete("delete from " + TABLE_NAME + " where " +
      "table_id = #{tableId} " +
      "and TIMESTAMPADD(SQL_TSI_FRAC_SECOND,cost_time,start_time) < #{expireTime}")
  void expireTaskHistory(@Param("tableId") long tableId,
      @Param("expireTime") long expireTime);
}
