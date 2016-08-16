package com.gome.upm.service;

import java.util.List;

import com.gome.upm.domain.Asm;

/**
 * ASM空间service
 * @author caowei-ds1
 *
 */
public interface AsmService {
	
	/**
	 *
	 * 根据条件查询ASM空间列表,不分页.
	 *
	 * @param condition
	 * 				查询条件
	 * @return
	 * 				ASM空间列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月18日    caowei-ds1    新建
	 * </pre>
	 */
	List<Asm> findAsmListByCondition(Asm condition);

}
