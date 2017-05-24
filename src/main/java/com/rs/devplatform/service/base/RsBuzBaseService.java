package com.rs.devplatform.service.base;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.rs.framework.common.RsEntityWrapper;
import com.rs.framework.common.entity.RsEntity;
import com.rs.framework.common.entity.base.CUBaseTO;
import com.rs.framework.common.entity.page.PageVO;
import com.rs.framework.common.exception.RsBuzRollbackException;
import com.rs.framework.common.validator.ValidateError;

public interface RsBuzBaseService<T extends CUBaseTO>{

	/**
	 * Add主入口
	 * 
	 * 	返回值int 当int>0,表示成功； int=0,表示失败，通过{@code getValidateErrors()}方法来获取错误信息
	 * 
	 * @param entity
	 * @return 0:失败	>0表示成功
	 * @throws RsBuzRollbackException
	 */
	public int addEntity(RsEntity<T> entity) throws RsBuzRollbackException;
	
	public List<ValidateError> getValidateErrors();
	
	/**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param entityWrapper 实体对象封装操作类
     * @return List
     */
	public <E> List<E> selectList(RsEntityWrapper<E> wrapper);
	
	/**
	 * 根据entity条件分页查询
	 * @param page
	 * @param entityWrapper 注意：如果entityWrapper.entity是VO extends xxEntity, 
	 * 			若要orderBy生效，必须使用RsEntityWraper.orderby() / RsEntityWrapper+VO.setOrderBy()
	 * @return
	 */
	public <E extends PageVO> Page<E> selectByPageOrder(RsEntityWrapper<E> entityWrapper);
	
	/**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @param clazz 对象类型
     * @return T
     */
	public <E> E selectById(Object id, Class<E> entityClazz);
	
	/**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @param clazz 对象类型
     * @return List
     */
	public <E> List<E> selectBatchIds(List<Object> idList, Class<E> clazz);
	
	/**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return object
     */
	public <E> E selectOne(E entity);
	
}
