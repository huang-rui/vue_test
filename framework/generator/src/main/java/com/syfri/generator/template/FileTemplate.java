package com.syfri.generator.template;

import com.syfri.generator.model.Column;

import java.util.HashMap;
import java.util.Map;

public class FileTemplate {

	/********************* 公用SQL模板  *********************/
	/*---- MYSQL -----*/
	/*
	*1.库中的表查询
	* public final static String SQL_TABLE =" SELECT TABLE_NAME,TABLE_COMMENT FROM  information_schema.TABLES  WHERE 1=1  AND table_schema = ? ";
	*2.表中的列查询
	* public final static String SQL_COLUMN ="SELECT TABLE_CATALOG,TABLE_SCHEMA,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE, "
			+" CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_COMMENT "
			+" FROM INFORMATION_SCHEMA.COLUMNS  "
			+" WHERE 1=1  AND TABLE_NAME = ? and table_schema =  ?  ORDER BY ordinal_position" ;
	*/

	/*---- ORACLE -----*/
	/*1.库中的表查询.*/
	public final static String SQL_TABLE =" SELECT T.TABLE_NAME,NVL(COMMENTS,T.TABLE_NAME) TABLE_COMMENT FROM USER_TABLES T,USER_TAB_COMMENTS C WHERE T.TABLE_NAME = C.TABLE_NAME ";

	/*2.表中的列查询.*/
	public final static String SQL_COLUMN ="SELECT '' TABLE_CATALOG,'' TABLE_SCHEMA,T.COLUMN_NAME,COLUMN_ID ORDINAL_POSITION,DATA_DEFAULT COLUMN_DEFAULT,NULLABLE IS_NULLABLE,DATA_TYPE, "
			+" DATA_LENGTH CHARACTER_MAXIMUM_LENGTH,DATA_PRECISION NUMERIC_PRECISION,DATA_SCALE NUMERIC_SCALE,C.COMMENTS COLUMN_COMMENT "
			+" FROM USER_TAB_COLUMNS T,USER_COL_COMMENTS C  "
			+" WHERE T.TABLE_NAME = C.TABLE_NAME AND T.COLUMN_NAME = C.COLUMN_NAME AND T.TABLE_NAME = ? ORDER BY COLUMN_ID" ;

	/*3.查询记录数SQL.*/
	public final static String SQL_COUNT = "\t<select id=\"doSearchCount\" parameterType=\"$_voName$\" resultType=\"java.lang.Integer\">\n"
			+"	\tSELECT COUNT(0) AS NUM FROM $TABLE_NAME$ $PREFIX$ WHERE 1=1 AND DELETE_FLAG = 'N'\n"
			+"	\t<include refid=\"SQL_CONDITION\"/>\n"
			+"\t</select>\n";


	/********************* 公用文件模板  *********************/

	/*1.Mapper文件模板.*/
	public static  final String MAPPER_TEMPLATE = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"+
			"<!DOCTYPE mapper  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">"+
			"\n<mapper namespace=\"$packagePath$.dao.$_daoName$\">\n";

	/*2.DAO文件模板.*/
	public static  final String DAO_TEMPLATE = "package $packagePath$.dao;\n\n"+
			"import com.syfri.baseapi.dao.BaseDAO;\n"+
			"import $packagePath$.model.$_voName$;\n\n"+
			"public interface $_daoName$ extends BaseDAO<$_voName$>{\n"+
			"}";

	/*3.Service文件模板.*/
	public static  final String SERVICE_TEMPLATE = "package $packagePath$.service;\n\n"+
			"import com.syfri.baseapi.service.BaseService;\n"+
			"import $packagePath$.model.$_voName$;\n\n"+
			"public interface $_serviceName$  extends BaseService<$_voName$>{\n"+
			"}";
	/*4.ServiceImpl文件模板.*/
	public static  final String SERVICE_IMPL_TEMPLATE = "package $packagePath$.service.impl;\n\n"+
			"import org.springframework.beans.factory.annotation.Autowired;\n"+
			"import org.springframework.stereotype.Service;\n\n"+
			"import com.syfri.baseapi.service.impl.BaseServiceImpl;\n"+
			"import $packagePath$.dao.$_daoName$;\n"+
			"import $packagePath$.model.$_voName$;\n"+
			"import $packagePath$.service.$_serviceName$;\n\n"+
			"@Transactional(rollbackFor = {Exception.class, RuntimeException.class})\n"+
			"@Service(\"$serviceName$\")\n"+
			"public class $_serviceName$Impl extends BaseServiceImpl<$_voName$> implements $_serviceName$ {\n\n"+
			"\t@Autowired\n"+
			"\tprivate $_daoName$ $daoName$;\n\n"+
			"\t@Override\n"+
			"\tpublic $_daoName$ getBaseDAO() {\n"+
			"\t\treturn $daoName$;\n"+
			"\t}\n"+
			"}";

	/*5.controller文件模板.*/
	public static  final String CONTROLLER_TEMPLATE = "package $packagePath$.controller;\n\n"+
			"import org.springframework.beans.factory.annotation.Autowired;\n\n"+
			"import org.springframework.web.bind.annotation.RestController;\n"+
			"import org.springframework.web.bind.annotation.RequestMapping;\n\n"+
			"import $packagePath$.model.$_voName$;\n"+
			"import $packagePath$.service.$_serviceName$;\n"+
			"import com.syfri.baseapi.controller.BaseController;\n\n"+

			"@RestController\n"+
			"@RequestMapping(\"$requestMapping$\")\n"+
			"public class $_controllerName$  extends BaseController<$_voName$>{\n\n"+
			"\t@Autowired\n"+
			"\tprivate $_serviceName$ $serviceName$;\n\n"+
			"\t@Override\n"+
			"\tpublic $_serviceName$ getBaseService() {\n"+
			"\t\treturn this.$serviceName$;\n"+
			"\t}\n\n"+
			"}\n";
	/*6.Bean文件模板.*/
	public static  final String VO_TEMPLATE = "package $packagePath$.model;\n\n"+
			"import java.io.Serializable;\n\n"+
			"import com.syfri.baseapi.model.ValueObject;\n\n"+
			"public class $_voName$ extends ValueObject implements Serializable{\n\n"+
			"\tprivate static final long serialVersionUID = 1L;\n\n"+
			"";

	public static Map<String,String > dataMap = new HashMap<String,String>();

	static {
		dataMap.put("varchar2", "String");
		dataMap.put("int", "int");
		dataMap.put("float", "float");
		dataMap.put("double", "double");
		dataMap.put("decimal", "long");
		dataMap.put("datetime", "String");
	}

	/**
	 * 获取类型
	 * @param dataType 数据类型
	 * @param length 长度
	 * @param scale 精度
	 */
	public static String getType(String dataType, String length, String scale){
		if(scale==null  || scale.equals("0") || scale.equals("null")){
			return dataMap.get(dataType)==null?"String":dataMap.get(dataType).toString();
		}else{
			return "double";
		}
	}

	/**
	 * 特殊字段的处理
	 */
	public static String handerColume(Column columnVO, String prefix, String operation){
		String result = "";
		String cname = columnVO.getColumnName();
		String dataType = columnVO.getDataType();

		String newName = handerVOColumn(prefix+" "+cname);

		/*1.查询.*/
		if("SELECT".equals(operation.toUpperCase())){
			result = prefix +"."+cname +" "+newName;
			if("timestamp".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d %H:%i:%s') " +newName;
			}
			if("data".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d') " +newName;
			}
			if("time".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%H:%i:%s') " +newName;
			}
		}

		/*1.插入.*/
		if("INSERT".equals(operation.toUpperCase())){
			result = prefix +"."+cname +" "+newName;
			if("timestamp".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d %H:%i:%s')" + newName;
			}
			if("data".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%Y-%m-%d')" + newName;
			}
			if("time".contains(dataType.toLowerCase())){
				result = "DATE_FORMAT("+prefix +"."+cname +",'%H:%i:%s')" + newName;
			}
		}
		return result;
	}

	/**
	 * 对字段名称的处理
	 * @author li.xue  2017/12/5
	 */
	public static String handerVOColumn(String cname){
		cname = cname.toLowerCase();
		String[] parts = cname.split("_");
		StringBuffer result = new StringBuffer();
		for(int i=0;i<parts.length;i++){
			String part = parts[i];
			if(i==0){
				result.append(part.toLowerCase());
			}else{
				result.append(part.substring(0,1).toUpperCase().substring(1));
			}
		}
		return result.toString();
	}

	public static String handerPeculiarColumn(String cname, String prefix){
		String newName = handerVOColumn(prefix+"_"+cname);
		String result = "#{"+newName+"}";
		if(cname.endsWith("CREATED_DATE")||cname.endsWith("LAST_UPD_DATE")){
			result = "NOW()";
		}else if(cname.endsWith("MODIFICATION_NUM")||cname.endsWith("DELETE_FLAG")){
			result = "0";
		}else if(cname.equals("ROW_ID")&&prefix.contains("item")){
			result = "DEFAULT_SEQ('DEFAULT')";
			//result = "#{"+prefix+"_"+cname+"}";
		}else{
			result = "#{"+newName+"}";
		}

		return result;
	}
}
