package com.rs.devplatform.service.base.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.rs.devplatform.service.base.RsBuzBaseService;
import com.rs.framework.common.DataState;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.RsGeneralMapper;
import com.rs.framework.common.entity.RsEntity;
import com.rs.framework.common.entity.base.CUBaseTO;
import com.rs.framework.common.entity.page.PageVO;
import com.rs.framework.common.exception.RsBuzRollbackException;
import com.rs.framework.common.utils.RsReflectionUtils;
import com.rs.framework.common.validator.ValidateError;

/**
 * 业务service操作数据库父类
 * 
 * @author yuxiaobin
 *
 */
public abstract class RsBuzBaseServiceImpl<T extends CUBaseTO> implements RsBuzBaseService<T>{
	
	@Autowired
	RsGeneralMapper generalMapper;
	
	private final Logger logger = LoggerFactory.getLogger(RsBuzBaseServiceImpl.class);
	
	private ThreadLocal<List<ValidateError>> validateErrHolder = new ThreadLocal<>();
	
	/**
	 * 该方法仅子类调用validate方法不过时，将错误添加进来，方便统一获取。
	 * 
	 * 		addValidateError("001", “校验失败，{0}必须不为空”, "名字")
	 * 		>>
	 *  	“校验失败，名字必须不为空”
	 *  
	 *  	addValidateError("001", “校验失败，该值得范围必须为{0}-{1}”, "18","25")
	 *  	>>
	 *  	“校验失败，该值得范围必须为18-25”
	 * 
	 * @param errorCode	
	 * @param errorMsg		错误信息，支持通配符%s，  比如：“校验失败，{0}必须不为空”
	 * @param args				替换通配符， "名字"	--> 结合errorMsg, 输出结果为： “校验失败，名字必须不为空”
	 * @return
	 */
	protected RsBuzBaseServiceImpl<T> addValidateError(String errorCode, String errorMsg, String... args){
		if(args!=null){
			Object[] objs = args;
			try{
				errorMsg = MessageFormat.format(errorMsg, objs);
			}catch(Exception e){
				logger.warn("addValidateError(): failed to format errorMsg="+errorMsg+", args="+args+", format ignored", e);
			}
		}
		ValidateError error = new ValidateError(errorCode, errorMsg);
		List<ValidateError> errList = validateErrHolder.get();
		if(errList==null){
			errList = new ArrayList<>();
		}
		errList.add(error);
		validateErrHolder.set(errList);
		return this;
	}

	public List<ValidateError> getValidateErrors(){
		return validateErrHolder.get();
	}
	
	/**
	 * 子类实现 新增数据 前的数据校验
	 * 		
	 * @param entity
	 * @return		false:校验不通过, 需要在返回前调用{@code addValidateError()}
	 */
	protected abstract boolean validateDataAdd(RsEntity<T> entity);
	
	/**
	 * 子类实现 更新数据 前的数据校验
	 * 
	 * @param entity
	 * @return	 false:校验不通过, 需要在返回前调用{@code addValidateError()}
	 */
	protected abstract boolean validateDataUpdate(RsEntity<T> entity);
	
	/**
	 * 子类实现 新增/更新数据 前的通用数据校验
	 * 
	 * @param entity
	 * @return	 false:校验不通过, 需要在返回前调用{@code addValidateError()}
	 */
	protected abstract boolean validateDataCommon(RsEntity<T> entity);
	
	/**
	 * 在新增/更新/删除 数据前，对数据做预处理（不参与事务）
	 * 
	 * 		示例：
	 * 		handleDataBeforePostNoTransaction(RsEntity<?> entity, DataState flag){
	 * 			if(DataState.INSERT.equals(flag)){
	 * 				entity.getMaster().setId(null);
	 *			 }
	 *			else if(DataState.UPDATE.equals(flag)){
	 *				//do something here
	 *			}
	 * 		}
	 * 
	 * @param entity
	 * @param addUpdateDeleteFlag {@code DataState}
	 * @return
	 */
	protected abstract void handleDataBeforePostNoTransaction(RsEntity<T> entity, DataState addUpdateDeleteFlag);
	
	/**
	 * 在更新数据前，对数据做预处理（参与事务）
	 * @param entity
	 * @param addUpdateDeleteFlag {@code DataState}
	 * @return
	 */
	protected abstract void handleDataBeforePostWithTransaction(RsEntity<T> entity, DataState addUpdateDeleteFlag);
	
	/**
	 * 在新增/更新数据后，对数据做后续处理（不参与事务）
	 * @param addUpdateDeleteFlag {@code DataState}
	 * @param entity
	 * @return
	 */
	protected abstract void handleDataAfterPostNoTransaction(RsEntity<T> entity, DataState addUpdateDeleteFlag);
	
	/**
	 * 在新增/更新数据后，对数据做后续处理（参与事务）
	 * @param addUpdateDeleteFlag {@code DataState}
	 * @param entity
	 * @return
	 */
	protected abstract void handleDataAfterPostWithTransaction(RsEntity<T> entity, DataState addUpdateFlag);
	
	/**
	 * Add主入口
	 * 
	 * 	返回值int 当int>0,表示成功； int=0,表示失败，通过{@code getValidateErrors()}方法来获取错误信息
	 * 
	 * @param entity
	 * @return 0:失败	>0表示成功
	 * @throws RsBuzRollbackException
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public int addEntity(RsEntity<T> entity) throws RsBuzRollbackException{
		if(!validateDataCommon(entity) || !validateDataAdd(entity)){
			return 0;
		}
		try{
			handleDataBeforePostNoTransaction(entity, DataState.INSERT);
		}catch(Exception e){
			logger.error("addEntity() >> handleDataBeforePostNoTransaction() exception has been caught", e);
		}
		int effRow = doAdd(entity);
		try{
			handleDataAfterPostNoTransaction(entity, DataState.INSERT);
		}catch(Exception e){
			logger.error("addEntity() >> handleDataAfterPostNoTransaction() exception has been caught", e);
		}
		return effRow;
	}
	
//	@Transactional(propagation=Propagation.NESTED)
	private int doAdd(RsEntity<T> entity) throws RsBuzRollbackException{
		handleDataBeforePostWithTransaction(entity, DataState.INSERT);
		int effRow = this.addEntity((RsEntity<T>)entity, null, null);
		handleDataAfterPostWithTransaction(entity, DataState.INSERT);
		return effRow;
	}
	
	/**
	 * 
	 * @param entity
	 * @param parentId
	 * @param masterClazz
	 * @throws IllegalArgumentException
	 * 			当传入的RsEntity对应的master/details类型不对(不是CUBaseTO)时，会抛错
	 */
	private int addEntity(RsEntity<?> entity, String parentId, Class<?> masterClazz){
		int effRow = 0;
		Object master = entity.getMaster();
		if(master instanceof CUBaseTO){
			CUBaseTO baseTO = (CUBaseTO) master;
			if(parentId!=null){
				RsReflectionUtils.setForeignKeyValue(baseTO, parentId, masterClazz);
			}
			if(entity.getPkGenerator()!=null){
				baseTO.setId(entity.getPkGenerator().generate());
			}
			effRow = generalMapper.insert(baseTO);
			String currentId = baseTO.getId();
			List<Object> details = entity.getDetails();
			if(details!=null){
				for(Object dtl:details){
					if(CUBaseTO.class.isAssignableFrom(dtl.getClass())){
						//normal entity
						RsReflectionUtils.setForeignKeyValue(dtl, currentId, master.getClass());
						generalMapper.insert(dtl);
					}else if(dtl instanceof RsEntity){
						RsEntity<?> dtlEntity = (RsEntity<?>) dtl;
						addEntity(dtlEntity, currentId, master.getClass());
					}
				}
			}
			return effRow;
		}else{
			logger.error("doAdd() obj is not CUBaseTO, failed to insert");
			throw new IllegalArgumentException("Not a CUBaseTO for type="+master.getClass().getName());
		}
	}
	
	Validator vd = Validation.buildDefaultValidatorFactory().getValidator();
	
	public boolean validateDataByHibernateValidator(Object obj){
		Set<ConstraintViolation<Object>> constraintViolations = vd.validate(obj);
		if(constraintViolations.isEmpty()){
			return true;
		}else{
			for(ConstraintViolation<Object> val: constraintViolations){
				addValidateError("CGS-001", val.getMessage());
			}
			return false;
		}
	}

	@Override
	public <E> List<E> selectList(RsEntityWrapper<E> entityWrapper) {
		return generalMapper.selectList(entityWrapper);
	}

	@Override
	public <E extends PageVO> Page<E> selectByPageOrder(RsEntityWrapper<E> entityWrapper) {
		return generalMapper.selectByPageOrder(entityWrapper);
	}

	@Override
	public <E> E selectById(Object id, Class<E> entityClazz) {
		return generalMapper.selectById(id, entityClazz);
	}

	@Override
	public <E> List<E> selectBatchIds(List<Object> idList, Class<E> clazz) {
		return generalMapper.selectBatchIds(idList, clazz);
	}

	@Override
	public <E> E selectOne(E entity) {
		return generalMapper.selectOne(entity);
	}

	
}
