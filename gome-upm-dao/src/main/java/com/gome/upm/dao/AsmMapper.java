package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.domain.Asm;

/**
 * 
 * ASM空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    caowei    新建
 * </pre>
 */
public interface AsmMapper {

	/**
	 *
	 * 查询ASM空间.
	 *
	 * @param condition
	 * 			条件
	 * @return
	 * 			记录列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月18日    caowei    新建
	 * </pre>
	 */
	List<Asm> selectAsmListByConditions(Asm asm);

}
