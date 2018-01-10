package com.syfri.generator.repositry;

import com.syfri.generator.model.Column;
import com.syfri.generator.template.FileTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Repository相当与将DAO标识为SpringBean
 * @author li.xue 2017/12/5
 */
@Repository("generatorRepositry")
public class GeneratorRepositry {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 测试连接情况
	 */
	public int doTestConnection(){
		int count = jdbcTemplate.execute(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				return connection.prepareStatement("select 1 from dual");
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException {
				ResultSet rs = preparedStatement.executeQuery();
				rs.next();
				return rs.getInt(1);
			}
		});
		return count;
	}

	/**
	 * 获取数据库中的表名
	 */
	public List<String> doGetTables(String dbName){
		List<String> tables = new ArrayList<String>();
		jdbcTemplate.execute(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(FileTemplate.SQL_TABLE);
				//ps.setString(1, dbName);
				return ps;
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement preparedStatement) throws SQLException, DataAccessException{
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next()){
					tables.add(rs.getString("TABLE_COMMENT")+":"+rs.getString("TABLE_NAME"));
				}
				return 0;
			}
		});
		return tables;
 	}

	/**
	 * 获取列名
	 */
	public List<Column> doGetColumns(String dbName, String tableName, String template) {
		List<Column> fieldList = new ArrayList<Column>();
		jdbcTemplate.execute(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(FileTemplate.SQL_COLUMN);
				ps.setString(1, tableName);
				//ps.setString(2, dbName);
				return ps;
			}
		}, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement pstmt) throws SQLException, DataAccessException {
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					Column filed = new Column();
					filed.setTableCataLog(rs.getString("TABLE_CATALOG"));
					filed.setTableSchema(rs.getString("TABLE_SCHEMA"));
					filed.setTableName(tableName);
					String columnName = rs.getString("COLUMN_NAME").toLowerCase();
					filed.setColumnName(columnName);
					filed.setOrderPosition(rs.getString("ORDINAL_POSITION"));
					filed.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
					filed.setIsNullable(rs.getString("IS_NULLABLE"));
					String dataType = rs.getString("DATA_TYPE").toLowerCase().trim();
					filed.setDataType(dataType);
					int numbericPrecision = rs.getInt("NUMERIC_PRECISION");
					int numbericScale= rs.getInt("NUMERIC_SCALE");
					filed.setNumeric_precision(numbericPrecision+"");
					if("int".equals(dataType)||"decimal".equals(dataType)){
						filed.setMaxLength(numbericPrecision+"");
						filed.setNumeric_scale(numbericScale+"");
						if("int".equals(dataType)) filed.setBeanType("Integer");
						if("decimal".equals(dataType)&&0==numbericScale){
							filed.setBeanType("long");
						}
						if("decimal".equals(dataType)&&numbericScale>0&&numbericPrecision<=10){
							filed.setBeanType("float");
						}
						if("decimal".equals(dataType)&&numbericScale>0&&numbericPrecision>10){
							filed.setBeanType("double");
						}
					}else{
						filed.setMaxLength(rs.getString("CHARACTER_MAXIMUM_LENGTH"));
						filed.setNumeric_scale("");
						filed.setBeanType("String");
					}
					String comment = rs.getString("COLUMN_COMMENT");
					filed.setComment(comment);
					filed.setBeanName(handleColumn(columnName,template));
					filed.setBeanComment(comment);
					fieldList.add(filed);
				}
				return 0;
			}
		});
		return fieldList;
	}

	/**
	 * 将带_的列名转换成驼峰式
	 */
	private String handleColumn(String columnName,String template){
		String[] arr = columnName.split("_");
		StringBuffer buffer = new StringBuffer(arr[0]);
		if("default".equals(template.trim())){
			for(int i=1;i<arr.length;i++){
				buffer.append(arr[i].substring(0, 1).toUpperCase()+arr[i].substring(1).toLowerCase());
			}
		}
		return buffer.toString();
	}
}
