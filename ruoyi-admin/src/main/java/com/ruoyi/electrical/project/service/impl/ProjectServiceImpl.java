package com.ruoyi.electrical.project.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.mapper.ProjectMapper;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 项目Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@Service
public class ProjectServiceImpl implements IProjectService {
	@Autowired
	private ProjectMapper projectMapper;

	/**
	 * 查询项目
	 * 
	 * @param id 项目主键
	 * @return 项目
	 */
	@Override
	public Project selectProjectById(Long id) {
		return projectMapper.selectProjectById(id);
	}

	/**
	 * 查询项目列表
	 * 
	 * @param project 项目
	 * @return 项目
	 */
	@Override
	public List<Project> selectProjectList(Project project) {
		return projectMapper.selectProjectList(project);
	}

	@Override
	public Integer checkProjectName(Project project) {
		return projectMapper.checkProjectName(project);
	}

	/**
	 * 新增项目
	 * 
	 * @param project 项目
	 * @return 结果
	 */
	@Override
	public int insertProject(Project project) {
		project.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		project.setCreateTime(DateUtils.getNowDate());
		project.setUpdateTime(DateUtils.getNowDate());
		return projectMapper.insertProject(project);
	}

	/**
	 * 修改项目
	 * 
	 * @param project 项目
	 * @return 结果
	 */
	@Override
	public int updateProject(Project project) {
		project.setUpdateTime(DateUtils.getNowDate());
		return projectMapper.updateProject(project);
	}

	/**
	 * 批量删除项目
	 * 
	 * @param ids 需要删除的项目主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectByIds(Long[] ids) {
		return projectMapper.deleteProjectByIds(ids);
	}

	/**
	 * 删除项目信息
	 * 
	 * @param id 项目主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectById(Long id) {
		return projectMapper.deleteProjectById(id);
	}

	@Override
	public List<DictVO> selectProjectDict() {
		return projectMapper.selectProjectDict();
	}

	// TODO 根据用户类型获取用户的项目
	@Override
	public List<DictVO> getUserProjectDict(SysUser user) {

		// 超级管理员
		if ("00".equalsIgnoreCase(user.getUserType())) {
			return projectMapper.selectProjectDict();
		} else if ("01".equalsIgnoreCase(user.getUserType())) {
			// 检测单位管理员
			return projectMapper.selectProjectDictByDetectId(user.getDetectId());

		} else if ("02".equalsIgnoreCase(user.getUserType())) {
			// 工作人员账号
			return projectMapper.selectProjectDictByWorkerId(user.getUserId());

		} else if ("03".equalsIgnoreCase(user.getUserType())) {
			// 街区账号

		} else if ("04".equalsIgnoreCase(user.getUserType())) {
			// 网格员

		} else if ("05".equalsIgnoreCase(user.getUserType())) {
			// 业主单元账号

		}
		return new ArrayList<DictVO>();
	}

}
