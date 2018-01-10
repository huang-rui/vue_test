package com.syfri.generator.model;

import java.io.Serializable;
import java.util.List;

/**
 * 产生的Bean的情况
 * @author  li.xue  2017/12/5
 */

public class GeneratorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*项目名称.*/
	private String projectName = "";

	/*模块名称.*/
	private String modelName = "";

	/*表名称.*/
	private String tableName = "";

	/*包.*/
	private String basePath = "";

	/*前缀.*/
	private String prefix = "";

	/*生成路径.*/
	private String genPath = "";

	/*表中的列.*/
	private List<Column> columnList;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
}
