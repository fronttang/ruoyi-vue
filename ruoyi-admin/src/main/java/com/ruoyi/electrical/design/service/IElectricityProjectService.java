package com.ruoyi.electrical.design.service;

import java.util.List;

import com.ruoyi.electrical.design.domain.ElectricityProject;

/**
 * 勘探数据查询Service接口
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
public interface IElectricityProjectService {
	/**
	 * 查询勘探数据查询
	 * 
	 * @param id 勘探数据查询主键
	 * @return 勘探数据查询
	 */
	public ElectricityProject selectElectricityProjectById(Long id);

	/**
	 * 查询勘探数据查询列表
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 勘探数据查询集合
	 */
	public List<ElectricityProject> selectElectricityProjectList(ElectricityProject electricityProject);

	/**
	 * 新增勘探数据查询
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 结果
	 */
	public int insertElectricityProject(ElectricityProject electricityProject);

	/**
	 * 修改勘探数据查询
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 结果
	 */
	public int updateElectricityProject(ElectricityProject electricityProject);

	/**
	 * 批量删除勘探数据查询
	 * 
	 * @param ids 需要删除的勘探数据查询主键集合
	 * @return 结果
	 */
	public int deleteElectricityProjectByIds(Long[] ids);

	/**
	 * 删除勘探数据查询信息
	 * 
	 * @param id 勘探数据查询主键
	 * @return 结果
	 */
	public int deleteElectricityProjectById(Long id);
}
