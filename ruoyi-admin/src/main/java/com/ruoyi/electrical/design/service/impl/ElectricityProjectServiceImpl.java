package com.ruoyi.electrical.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.design.domain.ElectricityProject;
import com.ruoyi.electrical.design.mapper.ElectricityProjectMapper;
import com.ruoyi.electrical.design.service.IElectricityProjectService;

/**
 * 勘探数据查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Service
public class ElectricityProjectServiceImpl implements IElectricityProjectService {
	@Autowired
	private ElectricityProjectMapper electricityProjectMapper;

	/**
	 * 查询勘探数据查询
	 * 
	 * @param id 勘探数据查询主键
	 * @return 勘探数据查询
	 */
	@Override
	public ElectricityProject selectElectricityProjectById(Long id) {
		return electricityProjectMapper.selectElectricityProjectById(id);
	}

	/**
	 * 查询勘探数据查询列表
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 勘探数据查询
	 */
	@Override
	public List<ElectricityProject> selectElectricityProjectList(ElectricityProject electricityProject) {
		return electricityProjectMapper.selectElectricityProjectList(electricityProject);
	}

	/**
	 * 新增勘探数据查询
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 结果
	 */
	@Override
	public int insertElectricityProject(ElectricityProject electricityProject) {
		electricityProject.setCreateTime(DateUtils.getNowDate());
		return electricityProjectMapper.insertElectricityProject(electricityProject);
	}

	/**
	 * 修改勘探数据查询
	 * 
	 * @param electricityProject 勘探数据查询
	 * @return 结果
	 */
	@Override
	public int updateElectricityProject(ElectricityProject electricityProject) {
		electricityProject.setUpdateTime(DateUtils.getNowDate());
		return electricityProjectMapper.updateElectricityProject(electricityProject);
	}

	/**
	 * 批量删除勘探数据查询
	 * 
	 * @param ids 需要删除的勘探数据查询主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectByIds(Long[] ids) {
		return electricityProjectMapper.deleteElectricityProjectByIds(ids);
	}

	/**
	 * 删除勘探数据查询信息
	 * 
	 * @param id 勘探数据查询主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectById(Long id) {
		return electricityProjectMapper.deleteElectricityProjectById(id);
	}
}
