package com.syfri.generator.model;

import java.io.Serializable;

/**
 * 表中列属性
 * @author  li.xue  2017/12/5
 */
public class Column implements Serializable {

	private static final long serialVersionUID = 1L;

	/*表容器名.*/
	private String tableCataLog;

	/*表模式名.*/
	private String tableSchema;

	/*表名.*/
	private String tableName;

	/*列名.*/
	private String columnName;

	/*顺序位置.*/
	private String orderPosition;

	/*默认值.*/
	private String columnDefault;

	/*是否为空.*/
	private String isNullable;

	/*数据类型.*/
	private String dataType;

	/*最大长度.*/
	private String maxLength;

	/*小数点位数.*/
	private String numeric_precision;

	/*数据位数.*/
	private String numeric_scale;

	/*描述.*/
	private String comment;

	/*实体名称.*/
	private String beanName;

	/*实体描述.*/
	private String beanComment;

	/*实体类型.*/
	private String beanType;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTableCataLog() {
		return tableCataLog;
	}

	public void setTableCataLog(String tableCataLog) {
		this.tableCataLog = tableCataLog;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getOrderPosition() {
		return orderPosition;
	}

	public void setOrderPosition(String orderPosition) {
		this.orderPosition = orderPosition;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getNumeric_precision() {
		return numeric_precision;
	}

	public void setNumeric_precision(String numeric_precision) {
		this.numeric_precision = numeric_precision;
	}

	public String getNumeric_scale() {
		return numeric_scale;
	}

	public void setNumeric_scale(String numeric_scale) {
		this.numeric_scale = numeric_scale;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getBeanComment() {
		return beanComment;
	}

	public void setBeanComment(String beanComment) {
		this.beanComment = beanComment;
	}

	public String getBeanType() {
		return beanType;
	}

	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
}
