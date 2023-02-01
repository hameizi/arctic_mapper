package com.netease.arctic.ams.server.mapper;

import com.netease.arctic.ams.server.model.TableIdentifierDTO;
import com.netease.arctic.ams.server.mybatis.Map2StringConverter;
import com.netease.arctic.table.TableIdentifier;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface TableIdentifierMapper {

  String TABLE_NAME = "table_identifier";

  @Insert("insert into " + TABLE_NAME +
      "(catalog_name, db_name, table_name)" +
      " values(" +
      " #{tableIdentifier.catalogName}," +
      " #{tableIdentifier.dbName}," +
      " #{tableIdentifier.tableName}" +
      " )")
  @Options(useGeneratedKeys = true, keyProperty = "tableIdentifier.tableId")
  void createTableMeta(@Param("tableIdentifier") TableIdentifierDTO tableIdentifier);

  @Select("select table_id, table_name, db_name, catalog_name from " + TABLE_NAME + " where " +
      "table_id=#{tableId}")
  @Results({
      @Result(property = "tableId", column = "table_id"),
      @Result(property = "tableName", column = "table_name"),
      @Result(property = "dbName", column = "db_name"),
      @Result(property = "catalogName", column = "catalog_name")
  })
  TableIdentifierDTO getTableIdentifier(@Param("tableId") long tableId);

  @Delete("delete from " + TABLE_NAME + " where catalog_name = #{tableIdentifier.catalog} and " +
      "db_name = #{tableIdentifier.database} and table_name = #{tableIdentifier.tableName}")
  void deleteTableMeta(@Param("tableIdentifier") TableIdentifier tableIdentifier);

  /**
   * Copy from {@link TableMetadataMapper}.
   */
  @Select("select db_name from " + TABLE_NAME + " where catalog_name=#{catalogName} group by db_name")
  List<String> getDatabases(@Param("catalogName") String catalogName);

  /**
   * Copy from {@link TableMetadataMapper}.
   */
  @Select("select count(catalog_name) from " + TABLE_NAME + " where catalog_name=#{catalogName}")
  Integer getTableCountInCatalog(@Param("catalogName") String catalogName);
}
