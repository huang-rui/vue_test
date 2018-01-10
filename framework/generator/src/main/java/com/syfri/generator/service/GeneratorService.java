package com.syfri.generator.service;

import com.syfri.generator.model.Column;
import com.syfri.generator.model.GeneratorBean;
import com.syfri.generator.repositry.GeneratorRepositry;
import com.syfri.generator.template.FileTemplate;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("generatorService")
public class GeneratorService {

	@Autowired
	private Environment env;

	@Autowired
	private GeneratorRepositry generatorRepositry;

	/**
	 * 检查连接是否正常（=1正常）
	 */
	public int doTestConnection(){
		return generatorRepositry.doTestConnection();
	}

	/**
	 * 获取所有库中所有表
	 */
	public List<String> getTables(){
		String dbName = env.getProperty("spring.datasource.url");
		//dbName = dbName.substring(dbName.lastIndexOf("/")+1, dbName.indexOf("?"));
		dbName = dbName.substring(dbName.lastIndexOf("/")+1);
		return generatorRepositry.doGetTables(dbName);

	}

	/**
	 * 获取表中的字段
	 */
	public List<Column> getTableColumn(String tableName){
		String dbName = env.getProperty("spring.datasource.url");
		//dbName = dbName.substring(dbName.lastIndexOf("/")+1, dbName.indexOf("?"));
		dbName = dbName.substring(dbName.lastIndexOf("/")+1);
		return generatorRepositry.doGetColumns(dbName, tableName, "default");
	}

	//文件生成

	/*项目名称.*/
	String projectName = "";

	/*模块名称（小写）.*/
	String modelName = "";

	/*表名称.*/
	String tableName = "";

	/*前缀.*/
	String prefix = "";

	/*包名.*/
	String basePath = "";

	/*生成路径.*/
	String genPath = "";


	/*表中的列信息.*/
	List<Column> list = new ArrayList<Column>();

	/*临时变量.*/
	StringBuffer buffer = new StringBuffer();

	/*插入时字段.*/
	StringBuffer iColumnBuffer = new StringBuffer();

	/*单次插入时字段值.*/
	StringBuffer iPrefixColumnBuffer = new StringBuffer();

	/*批量插入时字段值.*/
	StringBuffer iBatchPrefixColumnBuffer = new StringBuffer();

	/*更新时时字段.*/
	StringBuffer uColumnBuffer = new StringBuffer();

	/*更新时时字段值.*/
	StringBuffer uPrefixColumnBuffer = new StringBuffer();

	/*basePath+"."+modelName项目包路径.*/
	String packagePath = "";

	/*modelName.substring(0, 1).toUpperCase()+modelName.substring(1)模块名称.*/
	String _modelName = "";

	/*modelName+"Bean";小写开头Bean.*/
	String voName = "";

	/*_modelName+"Bean";大写开头Bean.*/
	String _voName = "";

	/*modelName+"Controller";小写开头Controller.*/
	String controllerName = "";

	/*_modelName+"Controller";大写开头Controller.*/
	String _controllerName = "";

	/*modelName+"Service";小写开头Service.*/
	String serviceName = "";

	/*_modelName+"Service";大写开头Service.*/
	String _serviceName = "";

	/*modelName+"Repository";小写开头Repository.*/
	String daoName = "";

	/*_modelName+"Repository";大写开头Repository.*/
	String _daoName = "";

	int size = 0;

	public String generate(GeneratorBean bean){
		initialize(bean);
		try{
			generatorMapper();
			generatorDao();
			generatorService();
			generatorController();
			generatorBean();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			buffer = null;
			iColumnBuffer = null;
			iPrefixColumnBuffer = null;
			iBatchPrefixColumnBuffer = null;
			uColumnBuffer = null;
			uPrefixColumnBuffer = null;
		}
		return "0";
	}

	/**
	 * 准备数据
	 * 采用的是Oracle，如果是Mysql则将SYSDATE换成NOW()
	 */
	private void initialize(GeneratorBean bean){
		buffer = new StringBuffer();
		iColumnBuffer = new StringBuffer();
		iPrefixColumnBuffer = new StringBuffer();
		iBatchPrefixColumnBuffer = new StringBuffer();
		uColumnBuffer = new StringBuffer();
		uPrefixColumnBuffer = new StringBuffer();

		this.projectName = bean.getProjectName().toLowerCase();
		this.modelName = bean.getModelName().toLowerCase();
		this.tableName = bean.getTableName();
		this.prefix = bean.getPrefix().toLowerCase();
		this.basePath = bean.getBasePath().toLowerCase();
		this.genPath = bean.getGenPath().toLowerCase();

//		this.packagePath = basePath + "." +projectName + "." + modelName;
		this.packagePath = basePath + "." +projectName;
		this._modelName = modelName.substring(0,1).toUpperCase() + modelName.substring(1);
		this.voName = modelName + "VO";
		this._voName = _modelName + "VO";
		this.controllerName = modelName + "Controller";
		this._controllerName = _modelName + "Controller";
		this.serviceName = modelName + "Service";
		this._serviceName = _modelName + "Service";
		this.daoName = modelName + "DAO";
		this._daoName = _modelName + "DAO";

		//表中的列及数量
		list = bean.getColumnList();
		size = list.size();

		//iColumnBuffer
		for(int i=0;i<size;i++){
			if(i==size-1){
				iColumnBuffer.append(list.get(i).getColumnName() + ")\n");
			}else{
				iColumnBuffer.append(list.get(i).getColumnName() + ",");
			}
		}

		//iPrefixColumnBuffer,iBatchPrefixColumnBuffer
		iPrefixColumnBuffer.append("(");
		//iBatchPrefixColumnBuffer.append("(");
		for(int i=0;i<size;i++){
			String beanName = list.get(i).getBeanName();
			String columnName = list.get(i).getColumnName();
			String dataType = list.get(i).getDataType().toUpperCase();

			if(columnName.endsWith("pkid")){
				iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=VARCHAR}");
			} else if(columnName.endsWith("create_time")){
				iPrefixColumnBuffer.append("SYSDATE");
				iBatchPrefixColumnBuffer.append("SYSDATE create_time");
			}else if(columnName.endsWith("alter_time")){
				iPrefixColumnBuffer.append("SYSDATE");
				iBatchPrefixColumnBuffer.append("SYSDATE alter_time");
			}else if(columnName.endsWith("delete_flag")){
				iPrefixColumnBuffer.append("'N'");
				iBatchPrefixColumnBuffer.append("'N' delete_flag");
			}else{
				if("DATE".equals(dataType)){
					iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=DATE}");
					iBatchPrefixColumnBuffer.append("#{item."+beanName+",jdbcType=DATE} " + columnName);
				}else if("VARCHAR2".equals(dataType)){
					iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=VARCHAR}");
					iBatchPrefixColumnBuffer.append("#{item."+beanName+",jdbcType=VARCHAR} " + columnName);
				}else if("NUMBER".equals(dataType)){
					iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=DOUBLE}");
					iBatchPrefixColumnBuffer.append("#{item."+beanName+",jdbcType=DOUBLE} " + columnName);
				}else if("TIMESTAMP(6)".equals(dataType)){
					iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=TIMESTAMP}");
					iBatchPrefixColumnBuffer.append("#{item."+beanName+",jdbcType=TIMESTAMP} " + columnName);
				}else{
					iPrefixColumnBuffer.append("#{"+beanName+",jdbcType=VARCHAR}");
					iBatchPrefixColumnBuffer.append("#{item."+beanName+",jdbcType=VARCHAR} " + columnName);
				}
			}

			if (i==size-1) {
				iPrefixColumnBuffer.append(")\n");
				iBatchPrefixColumnBuffer.append("\n");
			} else {
				iPrefixColumnBuffer.append(",");
				if(columnName.endsWith("pkid")){
				}else{
					iBatchPrefixColumnBuffer.append(",");
				}
			}
		}

		//uColumnBuffer
		for(int i=0;i<size;i++){
			String columnName = list.get(i).getColumnName();
			String columnNameUP = columnName.toUpperCase();
			String beanName = list.get(i).getBeanName();
			String dataType = list.get(i).getDataType().toUpperCase();
			String separator = ",";
			if(i==size-1){
				separator = "";
			}
			if (columnNameUP.endsWith("ALTER_TIME")){
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + columnName + "=TO_DATE(TO_CHAR(SYSDATE,'yyyy-MM-dd HH24:mi:ss'),'yyyy-MM-dd HH24:mi:ss')"+separator+"</if>\n");
			}else if(columnNameUP.endsWith("DELETE_FLAG")){
				uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + columnName + "='N'"+separator+"</if>\n");
			}else{
				if("DATE".equals(dataType)){
					uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + columnName + "=TO_DATE(#{" + beanName + "},'yyyy-MM-dd HH24:mi:ss')"+separator+"</if>\n");
				}else if("TIMESTAMP".equals(dataType)){
					uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + columnName + "=TO_DATE(#{" + beanName + "},'yyyy-MM-dd HH24:mi:ss')"+separator+"</if>\n");
				}else{
					uColumnBuffer.append("\t\t\t<if test=\"" + beanName + " != null\">" + columnName + "=#{" + beanName + "}"+separator+"</if>\n");
				}
			}
		}
	}

	/**
	 * 生成Mapper
	 */
	private void generatorMapper() throws IOException{
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/com/syfri/" + projectName + "/mapper/" + modelName + "-mapper.xml");
		buffer.append(FileTemplate.MAPPER_TEMPLATE.replace("$packagePath$", packagePath).replace("$_daoName$", _daoName)
				.replace("$_voName$", _voName));

		StringBuffer tempbuffer = new StringBuffer();

		System.out.println("************************************1.查询条件******************************************************");
		tempbuffer.append("\t<sql id=\"SQL_CONDITION\">\n");
		for (int i = 0; i < size; i++) {
			Column column = list.get(i);
			String name = column.getColumnName();
			String beanName = column.getBeanName();
			tempbuffer.append("\t\t<if test=\"" + beanName + " != null and " + beanName + " != '' \">\n");
			tempbuffer.append("\t\t and " + prefix + "." + name + " = #{" + beanName + "}\n");
			tempbuffer.append("\t\t</if>\n");
		}
		tempbuffer.append("\t</sql>");

		buffer.append("\t<!--1.查询条件-->\n");
		buffer.append(tempbuffer.toString() + "\n\n");

		System.out.println("************************************2.查询内容******************************************************");
		tempbuffer.delete(0, tempbuffer.length());
		buffer.append("\t<!--2.查询内容-->\n");
		buffer.append("\t<sql id=\"SQL_CONTENT\">\n");
		for (int i = 0; i < size; i++) {
			Column column = list.get(i);
			String dataType = column.getDataType();
			String name = column.getColumnName();
			String beanName = column.getBeanName();
			String result = prefix + "." + name + " " + beanName;


			if ("timestamp(6)".equals(dataType.toLowerCase())) {
				result = "TO_CHAR(" + prefix + "." + name + ",'yyyy-MM-dd HH24:mi:ss') " + beanName;
			}else if ("date".equals(dataType.toLowerCase())) {
				result = "TO_CHAR(" + prefix + "." + name + ",'yyyy-MM-dd HH24:mi:ss') " + beanName;
			}else{
				result = prefix + "." + name + " " + beanName;
			}
			/*MYSQL
			if ("timestamp".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%Y-%m-%d %H:%i:%s')" + beanName;
			}else if ("data".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%Y-%m-%d')" + beanName;
			}else if ("time".equals(dataType.toLowerCase())) {
				result = "DATE_FORMAT(" + prefix + "." + name + ",'%H:%i:%s')" + beanName;
			}else{
				result = prefix + "." + name + " " + beanName;
			}*/
			if (i == size - 1) {
				buffer.append("\t\t" + result + "\n");
			} else {
				buffer.append("\t\t" + result + ",\n");
			}
		}
		buffer.append("\t</sql>\n\n");

		System.out.println("************************************3.查询记录数******************************************************");
		buffer.append("\t<!--3.查询记录数-->\n");
		String sql_count = FileTemplate.SQL_COUNT.replace("$TABLE_NAME$", tableName).replace("$PREFIX$", prefix)
				.replace("$_voName$", _voName);
		buffer.append(sql_count + "\n");

		System.out.println("************************************4.按条件查询记录******************************************************");
		buffer.append("\t<!--4.按条件查询记录-->\n");
		buffer.append("\t<select id=\"doSearchListByVO\" parameterType=\"" + _voName + "\"  resultType=\"" + _voName
				+ "\"> \n");
		buffer.append("\t\tSELECT \n");
		buffer.append("\t\t\t<include refid=\"SQL_CONTENT\"/>\n");
		buffer.append("\t\tFROM " + tableName + " " + prefix + "  WHERE " + prefix + ".DELETE_FLAG = 'N' \n");
		buffer.append("\t\t\t<include refid=\"SQL_CONDITION\"/>\n");
		buffer.append("\t</select>\n\n");

		System.out.println("************************************5.根据ID查询记录******************************************************");
		buffer.append("\t<!--5.根据ID查询记录-->\n");
		buffer.append(
				"\t<select id=\"doFindById\" parameterType=\"" + _voName + "\"  resultType=\"" + _voName + "\">  \n");
		buffer.append("\t\tSELECT \n");
		buffer.append("\t\t<include refid=\"SQL_CONTENT\"/>\n");
		buffer.append("\t\tFROM " + tableName + " " + prefix + " \n");
		buffer.append("\t\tWHERE " + prefix + ".DELETE_FLAG = 'N' AND  " + prefix + ".PKID = #{value} \n");
		buffer.append("\t</select>\n\n");
		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");

		System.out.println("************************************6.按照VO插入******************************************************");
		buffer.append("\t<!--6.按照VO插入-->\n");
		buffer.append("\t<insert  id=\"doInsertByVO\"  parameterType=\"" + _voName + "\"> \n");
		buffer.append("\t\t<selectKey resultType=\"String\" order=\"BEFORE\" keyProperty=\"pkid\">\n");
		buffer.append("\t\t\tSELECT SYS_GUID() AS pkid FROM DUAL\n");
		buffer.append("\t\t</selectKey>\n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		// 加入字段名
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tVALUES");
		// 加入字段别名
		buffer.append(iPrefixColumnBuffer.toString());
		buffer.append("\t </insert>\n\n");
		// System.out.println(buffer.toString());

		System.out.println("************************************7.批量插入******************************************************");
		buffer.append("\t<!--7.批量插入-->\n");
		buffer.append("\t<insert  id=\"doBatchInsertByList\"  parameterType=\"java.util.List\"> \n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		// 加入字段名
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tSELECT SYS_GUID() pkid, A.* \n");
		buffer.append("\t\tFROM ( \n");
		buffer.append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\"UNION ALL\" >\n");
		buffer.append("\t\tSELECT\n");
		buffer.append("\t\t\t");
		// 加入字段别名
		buffer.append(iBatchPrefixColumnBuffer.toString());
		buffer.append("\t\tFROM DUAL\n");
		buffer.append("\t\t</foreach>\n");
		buffer.append("\t\t) A\n");
		buffer.append("\t</insert>\n\n");

		System.out.println("************************************8.按主键逻辑删除******************************************************");
		buffer.append("\t<!--8.按主键逻辑删除-->\n");
		buffer.append("\t<update id=\"doDeleteById\"  parameterType=\"String\"> \n");
		// buffer.append("\t\tDELETE FROM "+tableName+" WHERE 1=1 AND PKID =
		// #{value} \n");
		buffer.append("\t\tUPDATE  " + tableName + " " + prefix + " SET " + prefix
				+ ".DELETE_FLAG = 'Y'  WHERE 1=1   AND " + prefix + ".PKID = #{value} \n");
		buffer.append("\t</update>\n\n");
		// System.out.println(buffer.toString());

		System.out.println("************************************9.修改******************************************************");
		buffer.append("\t<!--9.按复合条件逻辑更新-->\n");
		buffer.append("\t<update  id=\"doUpdateByVO\"  parameterType=\"" + _voName + "\"> \n");
		buffer.append("\t\tUPDATE   " + tableName	 + "  \n");
		buffer.append("\t\t<set>\n");
		buffer.append(uColumnBuffer.toString());
		buffer.append("\t\t</set>\n");
		buffer.append("\t\tWHERE  PKID = #{pkid}\n");
		buffer.append("\t</update>\n\n");
		// System.out.println(buffer.toString());

		System.out.println("************************************10.批量修改******************************************************");
		buffer.append("\t<!--10.批量修改-->\n");
		buffer.append("\t<update id=\"doBatchUpdateByList\"  parameterType=\"java.util.List\">\n");
		buffer.append("\t\tINSERT INTO " + tableName + "(");
		buffer.append(iColumnBuffer.toString());
		buffer.append("\t\tVALUES\n");
		buffer.append("\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\" >\n");
		buffer.append("\t\t\t");
		// 加入字段别名
		buffer.append(iBatchPrefixColumnBuffer.toString());
		buffer.append("\t\t</foreach>\n");
		buffer.append("\t\tON DUPLICATE KEY UPDATE   \n");
		for (int i = 0; i < size; i++) {
			String cname = list.get(i).getColumnName();
			if (i == size - 1) {
				buffer.append("\t\t\t").append(cname).append(" = VALUES(").append(cname).append(")\n");
			} else {
				buffer.append("\t\t\t").append(cname).append(" = VALUES(").append(cname).append("),\n");
			}
		}
		buffer.append("\t</update>\n\n");

		System.out.println("************************************11.根据对象查询记录******************************************************");
		buffer.append("\t<!--11.根据对象查询记录-->\n");
		buffer.append(
				"\t<select id=\"doFindByVO\" parameterType=\"" + _voName + "\"  resultType=\"" + _voName + "\">  \n");
		buffer.append("\t\tSELECT \n");
		buffer.append("\t\t<include refid=\"SQL_CONTENT\"/>\n");
		buffer.append("\t\tFROM " + tableName + " " + prefix + " \n");
		buffer.append("\t\tWHERE " + prefix + ".DELETE_FLAG = 'N' \n");
		buffer.append("\t\t<include refid=\"SQL_CONDITION\"/>\n");
		buffer.append("\t</select>\n\n");

		buffer.append("</mapper>\n");
		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	/**
	 * 生成Dao
	 */
	private void generatorDao() throws Exception{
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/com/syfri/" + projectName + "/dao/" + _daoName + ".java");
		buffer.append(FileTemplate.DAO_TEMPLATE.replace("$packagePath$", packagePath).replace("$basePath$", basePath)
				.replace("$_voName$", _voName).replace("$_daoName$", _daoName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	/**
	 * 生成Service
	 */
	private void generatorService() throws Exception{
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/com/syfri/" + projectName + "/service/" + _serviceName + ".java");

		buffer.append(FileTemplate.SERVICE_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$basePath$", basePath).replace("$_voName$", _voName).replace("$_serviceName$", _serviceName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");

		file = new File(genPath + "/com/syfri/" + projectName + "/service/impl/" + _serviceName + "Impl.java");
		buffer = null;
		buffer = new StringBuffer();
		buffer.append(FileTemplate.SERVICE_IMPL_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$basePath$", basePath).replace("$_daoName$", _daoName).replace("$daoName$", daoName)
				.replace("$_serviceName$", _serviceName).replace("$serviceName$", serviceName)
				.replace("$_voName$", _voName));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	/**
	 * 生成Controller
	 */
	private void generatorController() throws Exception{
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(
				genPath + "/com/syfri/" + projectName + "/controller/" + _controllerName + ".java");

		buffer.append(FileTemplate.CONTROLLER_TEMPLATE.replace("$packagePath$", packagePath)
				.replace("$_voName$", _voName).replace("$_serviceName$", _serviceName)
				.replace("$serviceName$", serviceName).replace("$_controllerName$", _controllerName)
				.replace("$returnPath$", projectName + "/" + modelName + "/" + modelName)
				.replace("$requestMapping$", modelName).replace("$basePath$", basePath)
				.replace("$packagePath$", packagePath));

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}

	/**
	 * 生成Bean
	 */
	private void generatorBean() throws Exception{
		buffer.delete(0, buffer.length());
		buffer = new StringBuffer();
		File file = new File(genPath + "/com/syfri/" + projectName + "/model/" + _voName + ".java");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		buffer.append(FileTemplate.VO_TEMPLATE.replace("$packagePath$", packagePath).replace("$basePath$", basePath)
				.replace("$_voName$", _voName));
		for (Column field : list) {
			String comment = field.getBeanComment();
			Matcher m = CRLF.matcher(comment);
			comment = m.replaceAll("\t");
			buffer.append("\tprivate " + field.getBeanType() + " " + field.getBeanName() + ";\t//" + comment
					+ "\n");

		}
		buffer.append("\n");
		for (Column field : list) {
			String name = field.getBeanName();
			// String fullName = prefix+"_"+cName;
			// String fullName = FileTemplate.handerVOColumn(prefix+"_"+cname);
/*			String type = field.getDataType();
			String length = field.getMaxLength();
			String scale = field.getNumeric_scale();*/
			String temp = name.substring(0, 1).toUpperCase() + name.substring(1); // 模块名称
			buffer.append("\tpublic " + field.getBeanType()  + " get" + temp + "(){\n");
			buffer.append("\t\treturn " + name + ";\n");
			buffer.append("\t}\n");
			buffer.append("\tpublic void set" + temp + "(" + field.getBeanType()  + " " + name
					+ "){\n");
			buffer.append("\t\tthis." + name + " = " + name + ";\n");
			buffer.append("\t}\n");

		}
		buffer.append("}");

		FileUtils.writeStringToFile(file, buffer.toString(), "UTF-8");
	}
}
