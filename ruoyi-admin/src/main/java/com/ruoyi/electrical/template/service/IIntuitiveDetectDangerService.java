package com.ruoyi.electrical.template.service;

import java.util.List;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;

/**
 * 检测内容隐患Service接口
 * 
 * @author fronttang
 * @date 2024-06-23
 */
public interface IIntuitiveDetectDangerService {
	/**
	 * 查询检测内容隐患
	 * 
	 * @param id 检测内容隐患主键
	 * @return 检测内容隐患
	 */
	public IntuitiveDetectDanger selectIntuitiveDetectDangerById(Long id);

	/**
	 * 查询检测内容隐患列表
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 检测内容隐患集合
	 */
	public List<IntuitiveDetectDanger> selectIntuitiveDetectDangerList(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 新增检测内容隐患
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 结果
	 */
	public int insertIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 修改检测内容隐患
	 * 
	 * @param intuitiveDetectDanger 检测内容隐患
	 * @return 结果
	 */
	public int updateIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger);

	/**
	 * 批量删除检测内容隐患
	 * 
	 * @param ids 需要删除的检测内容隐患主键集合
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDangerByIds(Long[] ids);

	/**
	 * 删除检测内容隐患信息
	 * 
	 * @param id 检测内容隐患主键
	 * @return 结果
	 */
	public int deleteIntuitiveDetectDangerById(Long id);

	/**
	 * 检测表内容隐患
	 * 
	 * @param id
	 * @return
	 */
	public List<IntuitiveDetectDanger> selectIntuitiveDetectDangersByDataId(Long dataId);

	public Long countDangersByDataId(Long dataId);
}
