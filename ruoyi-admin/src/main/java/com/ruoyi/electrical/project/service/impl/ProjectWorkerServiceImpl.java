package com.ruoyi.electrical.project.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.project.domain.ProjectWorker;
import com.ruoyi.electrical.project.domain.ProjectWorkerArea;
import com.ruoyi.electrical.project.mapper.ProjectWorkerMapper;
import com.ruoyi.electrical.project.service.IProjectService;
import com.ruoyi.electrical.project.service.IProjectWorkerAreaService;
import com.ruoyi.electrical.project.service.IProjectWorkerService;

/**
 * 项目工作人员Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-02
 */
@Service
public class ProjectWorkerServiceImpl implements IProjectWorkerService {

	@Autowired
	private ProjectWorkerMapper projectWorkerMapper;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IProjectWorkerAreaService projectWorkerAreaService;

	/**
	 * 查询项目工作人员
	 * 
	 * @param id 项目工作人员主键
	 * @return 项目工作人员
	 */
	@Override
	public ProjectWorker selectProjectWorkerById(Long id) {
		ProjectWorker projectWorker = projectWorkerMapper.selectProjectWorkerById(id);
		if (projectWorker != null) {
			List<ProjectWorkerArea> workerAreas = projectWorkerAreaService.selectProjectWorkerAreaByWorkerId(id);

			Set<String> viewAreas = new HashSet<String>();
			Set<String> editAreas = new HashSet<String>();
			if (!CollectionUtils.isEmpty(workerAreas)) {
				workerAreas.forEach((area) -> {
					if ("1".equalsIgnoreCase(area.getType())) {
						if (StringUtils.hasText(area.getHamlet())) {
							viewAreas.add(area.getHamlet());
						} else if (StringUtils.hasText(area.getCommunity())) {
							viewAreas.add(area.getCommunity());
						} else if (StringUtils.hasText(area.getStreet())) {
							viewAreas.add(area.getStreet());
						} else if (StringUtils.hasText(area.getDistrict())) {
							viewAreas.add(area.getDistrict());
						}
					} else {
						if (StringUtils.hasText(area.getHamlet())) {
							editAreas.add(area.getHamlet());
						} else if (StringUtils.hasText(area.getCommunity())) {
							editAreas.add(area.getCommunity());
						} else if (StringUtils.hasText(area.getStreet())) {
							editAreas.add(area.getStreet());
						} else if (StringUtils.hasText(area.getDistrict())) {
							editAreas.add(area.getDistrict());
						}
					}
				});
			}
			projectWorker.setViewAreas(viewAreas.toArray(new String[viewAreas.size()]));
			projectWorker.setEditAreas(editAreas.toArray(new String[editAreas.size()]));
		}
		return projectWorker;
	}

	/**
	 * 查询项目工作人员列表
	 * 
	 * @param projectWorker 项目工作人员
	 * @return 项目工作人员
	 */
	@Override
	public List<ProjectWorker> selectProjectWorkerList(ProjectWorker projectWorker) {
		return projectWorkerMapper.selectProjectWorkerList(projectWorker);
	}

	/**
	 * 新增项目工作人员
	 * 
	 * @param projectWorker 项目工作人员
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertProjectWorker(ProjectWorker projectWorker) {

		Project project = projectService.selectProjectById(projectWorker.getProjectId());
		if (project == null) {
			return 0;
		}
		projectWorker.setDetectId(project.getDetectId());

		Long[] userIds = projectWorker.getUserIds();
		List<Long> userIdList = Arrays.asList(userIds);

		userIdList.forEach((userId) -> {

			projectWorker.setUserId(userId);
			List<ProjectWorker> workList = projectWorkerMapper.selectProjectWorkerList(projectWorker);
			if (CollectionUtils.isEmpty(workList)) {
				projectWorker.setCreateTime(DateUtils.getNowDate());
				projectWorker.setUpdateTime(DateUtils.getNowDate());
				projectWorkerMapper.insertProjectWorker(projectWorker);
			}
		});
		return userIdList.size();
	}

	/**
	 * 修改项目工作人员
	 * 
	 * @param projectWorker 项目工作人员
	 * @return 结果
	 */
	@Override
	public int updateProjectWorker(ProjectWorker projectWorker) {
		projectWorker.setUpdateTime(DateUtils.getNowDate());
		return projectWorkerMapper.updateProjectWorker(projectWorker);
	}

	/**
	 * 批量删除项目工作人员
	 * 
	 * @param ids 需要删除的项目工作人员主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectWorkerByIds(Long[] ids) {
		return projectWorkerMapper.deleteProjectWorkerByIds(ids);
	}

	/**
	 * 删除项目工作人员信息
	 * 
	 * @param id 项目工作人员主键
	 * @return 结果
	 */
	@Override
	public int deleteProjectWorkerById(Long id) {
		return projectWorkerMapper.deleteProjectWorkerById(id);
	}
}
