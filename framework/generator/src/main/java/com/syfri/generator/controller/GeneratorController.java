package com.syfri.generator.controller;

import com.alibaba.fastjson.JSON;
import com.syfri.generator.model.GeneratorBean;
import com.syfri.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class GeneratorController {

	@Autowired
	public GeneratorService generatorService;

	@Value("${framework.projectName}")
	private String projectName;

	@Value("${framework.modelName}")
	private String modelName;

	@Value("${framework.genPath}")
	private String genPath;

	@Value("${framework.prefix}")
	private String prefix;

	@RequestMapping("/index")
	public String index(){
		return "index";
	}

	@RequestMapping("/doGetTables")
	public @ResponseBody Map<String,Object> doGetTables(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("projectName", projectName);
		map.put("modelName", modelName);
		map.put("genPath", genPath);
		map.put("prefix", prefix);
		map.put("list", generatorService.getTables());
		return map;
	}

	@RequestMapping(value = "/doGetColumns", produces = "application/json;charset=utf-8")
	public @ResponseBody String doGetColumns(@RequestParam String tableName){
		return JSON.toJSONString(generatorService.getTableColumn(tableName));
	}

	@RequestMapping("/doGenerate")
	public @ResponseBody String doGenerator(GeneratorBean bean){
		generatorService.generate(bean);
		return "";
	}

}
