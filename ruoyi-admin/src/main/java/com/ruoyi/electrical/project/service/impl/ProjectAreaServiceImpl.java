package com.ruoyi.electrical.project.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.ProjectAreaMapper;
import com.ruoyi.electrical.project.domain.AreaDict;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.project.service.IProjectAreaService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 项目区域Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Service
public class ProjectAreaServiceImpl implements IProjectAreaService {
	@Autowired
	private ProjectAreaMapper projectAreaMapper;

	/**
	 * 查询项目区域
	 * 
	 * @param id 项目区域主键
	 * @return 项目区域
	 */
	@Override
	public ProjectArea selectProjectAreaById(Long id) {
		return projectAreaMapper.selectProjectAreaById(id);
	}

	/**
	 * 查询项目区域列表
	 * 
	 * @param projectArea 项目区域
	 * @return 项目区域
	 */
	@Override
	public List<ProjectArea> selectProjectAreaList(ProjectArea projectArea) {
		return projectAreaMapper.selectProjectAreaList(projectArea);
	}

	/**
	 * 新增项目区域
	 * 
	 * @param projectArea 项目区域
	 * @return 结果
	 */
	@Override
	public int insertProjectArea(ProjectArea projectArea) {
		projectArea.setCreateTime(DateUtils.getNowDate());
		projectArea.setUpdateTime(DateUtils.getNowDate());
		return projectAreaMapper.insertProjectArea(projectArea);
	}

	/**
	 * 修改项目区域
	 * 
	 * @param projectArea 项目区域
	 * @return 结果
	 */
	@Override
	public int updateProjectArea(ProjectArea projectArea) {
		projectArea.setUpdateTime(DateUtils.getNowDate());
		return projectAreaMapper.updateProjectArea(projectArea);
	}

	/**
	 * 批量删除项目区域
	 * 
	 * @param ids 需要删除的项目区域主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectAreaByIds(Long[] ids) {
		return projectAreaMapper.deleteProjectAreaByIds(ids);
	}

	/**
	 * 删除项目区域信息
	 * 
	 * @param id 项目区域主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectAreaById(Long id) {
		return projectAreaMapper.deleteProjectAreaById(id);
	}

	@Override
	public List<DictVO> queryProjectAreaDictByProjectId(Long projectId) {
		return projectAreaMapper.queryProjectAreaDictByProjectId(projectId);
	}

	@Override
	public List<DictVO> queryProjectAreaDict() {
		return projectAreaMapper.queryProjectAreaDict();
	}

	@Override
	public List<AreaDict> queryProjectAreaDictByProjectIdAndType(Long projectId, String type) {
		return projectAreaMapper.queryProjectAreaDictByProjectIdAndType(projectId, type);
	}
}
