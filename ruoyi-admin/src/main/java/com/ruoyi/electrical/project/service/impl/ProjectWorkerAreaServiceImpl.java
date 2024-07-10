package com.ruoyi.electrical.project.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.dto.ProjectWorkerAreaDto;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.project.domain.ProjectWorkerArea;
import com.ruoyi.electrical.project.mapper.ProjectWorkerAreaMapper;
import com.ruoyi.electrical.project.service.IProjectAreaService;
import com.ruoyi.electrical.project.service.IProjectWorkerAreaService;

/**
 * 项目工作人员区域Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-02
 */
@Service
public class ProjectWorkerAreaServiceImpl implements IProjectWorkerAreaService {

	@Autowired
	private ProjectWorkerAreaMapper projectWorkerAreaMapper;

	@Autowired
	private IProjectAreaService projectAreaService;

	/**
	 * 查询项目工作人员区域
	 * 
	 * @param id 项目工作人员区域主键
	 * @return 项目工作人员区域
	 */
	@Override
	public ProjectWorkerArea selectProjectWorkerAreaById(Long id) {
		return projectWorkerAreaMapper.selectProjectWorkerAreaById(id);
	}

	/**
	 * 查询项目工作人员区域列表
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 项目工作人员区域
	 */
	@Override
	public List<ProjectWorkerArea> selectProjectWorkerAreaList(ProjectWorkerArea projectWorkerArea) {
		return projectWorkerAreaMapper.selectProjectWorkerAreaList(projectWorkerArea);
	}

	/**
	 * 新增项目工作人员区域
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 结果
	 */
	@Override
	public int insertProjectWorkerArea(ProjectWorkerArea projectWorkerArea) {
		projectWorkerArea.setCreateTime(DateUtils.getNowDate());
		projectWorkerArea.setUpdateTime(DateUtils.getNowDate());
		return projectWorkerAreaMapper.insertProjectWorkerArea(projectWorkerArea);
	}

	/**
	 * 修改项目工作人员区域
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 结果
	 */
	@Override
	public int updateProjectWorkerArea(ProjectWorkerArea projectWorkerArea) {
		projectWorkerArea.setUpdateTime(DateUtils.getNowDate());
		return projectWorkerAreaMapper.updateProjectWorkerArea(projectWorkerArea);
	}

	/**
	 * 批量删除项目工作人员区域
	 * 
	 * @param ids 需要删除的项目工作人员区域主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectWorkerAreaByIds(Long[] ids) {
		return projectWorkerAreaMapper.deleteProjectWorkerAreaByIds(ids);
	}

	/**
	 * 删除项目工作人员区域信息
	 * 
	 * @param id 项目工作人员区域主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectWorkerAreaById(Long id) {
		return projectWorkerAreaMapper.deleteProjectWorkerAreaById(id);
	}

	@Override
	@Transactional
	public int saveProjectWorkerArea(ProjectWorkerAreaDto data) {

		Long[] workerIds = data.getWorkerIds();

		// 先删除之前的
		projectWorkerAreaMapper.deleteProjectWorkerAreaByWorkerIdAndType(workerIds, data.getType());

		List<Long> workerIdList = Arrays.asList(workerIds);
		workerIdList.forEach((workerId) -> {
			Long[] areaIds = data.getAreaIds();
			List<Long> areaIdList = Arrays.asList(areaIds);
			areaIdList.forEach((areaId) -> {
				ProjectArea projectArea = projectAreaService.selectProjectAreaById(areaId);
				if (projectArea != null) {
					ProjectWorkerArea workerArea = new ProjectWorkerArea();
					workerArea.setType(data.getType());
					workerArea.setWorkerId(workerId);
					workerArea.setDistrict(projectArea.getDistrict());
					workerArea.setStreet(projectArea.getStreet());
					workerArea.setCommunity(projectArea.getCommunity());
					workerArea.setHamlet(projectArea.getHamlet());
					this.insertProjectWorkerArea(workerArea);
				}
			});
		});

		return 1;
	}

	@Override
	public List<ProjectWorkerArea> selectProjectWorkerAreaByWorkerId(Long workerId) {
		return projectWorkerAreaMapper.selectProjectWorkerAreaByWorkerId(workerId);
	}

	@Override
	public int deleteProjectWorkerAreaByWorkerIds(Long[] workerIds) {

		return projectWorkerAreaMapper.deleteProjectWorkerAreaByWorkerIds(workerIds);
	}
}
