package com.syfri.baseapi.controller;


import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.model.ValueObject;
import com.syfri.baseapi.service.BaseService;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 基础Controller
 * @author  li.xue  2017/12/5
 */
public abstract class BaseController<T extends ValueObject> {

	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	protected static final String RESULT = "result";

	private Class clz;
	private T instance;
	private Type type;

	public abstract BaseService<T> getBaseService();

	public BaseController(){
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@PostConstruct
	public void initialize(){
		type = this.getClass().getGenericSuperclass();
		if(!(type instanceof ParameterizedType)){
			clz = Object.class;
		}
		Type param = ((ParameterizedType)type).getActualTypeArguments()[0];
		clz = (Class) param;
		try {
			instance = (T) clz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@ApiOperation(value = "根据ID查询", notes = "查询一条记录")
	@ApiImplicitParam(name = "id", value = "业务ID", dataType = "String", paramType = "path")
	@GetMapping("{id}")
	public @ResponseBody ResultVO find(@PathVariable String id){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(this.getBaseService().doFindById(id));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

	@ApiOperation(value="查询列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("list")
	public @ResponseBody ResultVO list(@RequestBody T vo ) {
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(this.getBaseService().doSearchListByVO(vo));
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

	@ApiOperation(value="分页查询列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("page")
	public @ResponseBody ResultVO page(@RequestBody T vo ) {
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(this.getBaseService().doSearchPage(vo));
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}


	@ApiOperation(value="根据VO保存",notes="注意事项")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PostMapping("")
	public @ResponseBody ResultVO save(@RequestBody T vo) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			this.getBaseService().doInsertByVO(vo);
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}

		return 	resultVO;
	}

	@ApiOperation(value="根据VO更新",notes="注意事项")
	@ApiImplicitParam(name="vo",value = "业务实体")
	@PutMapping("")
	public @ResponseBody ResultVO update(@RequestBody T vo) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			if(validateParam(vo)){
				this.getBaseService().doUpdateByVO(vo);
			}
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return 	resultVO;
	}

	@ApiOperation(value="根据VO删除",notes="注意事项")
	@DeleteMapping("")
	public @ResponseBody ResultVO delete(@RequestBody T vo) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			this.getBaseService().doDeleteByVO(vo);
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return 	resultVO;
	}

	@ApiOperation(value="根据ID删除",notes="注意事项")
	@ApiImplicitParam(name="id",value = "业务ID",dataType="String",paramType="path")
	@DeleteMapping("{id}")
	public @ResponseBody ResultVO delete(@PathVariable String id) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			this.getBaseService().doDeleteById(id);
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return 	resultVO;
	}

	/**
	 * 对VO中的数据进行验证
	 * @param vo
	 * @author  li.xue  2017/12/5
	 */
	public boolean validateParam(ValueObject vo){
		return true;
		/*	Set<ConstraintViolation<ValueObject>> constraints = validator.validate(vo, First.class);
		if(constraints.size() > 0){
			Iterator<ConstraintViolation<ValueObject>> it = constraints.iterator();
			while(it.hasNext()){
				System.out.println(it.next().getMessage());
			}
			return false;
		}else{
			return true;
		}*/
	}

	//增加明细行
	public void doAddDetailRow(){}

	//日志处理
	public String doAddLog(){return null;}

	//导出EXCEL初始化
	public void doExportExcelInit(){}

	//打印初始化
	public void doPrintInit(){}

	//导出EXCEL
	public void doExportExcel(){}

	//打印
	public void doPrint(){}
}
