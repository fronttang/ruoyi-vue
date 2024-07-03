package com.ruoyi.electrical.project.service;

import java.util.List;

import javax.validation.Valid;

import com.ruoyi.electrical.dto.ProjectWorkerAreaDto;
import com.ruoyi.electrical.project.domain.ProjectWorkerArea;

/**
 * 项目工作人员区域Service接口
 * 
 * @author fronttang
 * @date 2024-07-02
 */
public interface IProjectWorkerAreaService {
	/**
	 * 查询项目工作人员区域
	 * 
	 * @param id 项目工作人员区域主键
	 * @return 项目工作人员区域
	 */
	public ProjectWorkerArea selectProjectWorkerAreaById(Long id);

	/**
	 * 查询项目工作人员区域列表
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 项目工作人员区域集合
	 */
	public List<ProjectWorkerArea> selectProjectWorkerAreaList(ProjectWorkerArea projectWorkerArea);

	/**
	 * 新增项目工作人员区域
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 结果
	 */
	public int insertProjectWorkerArea(ProjectWorkerArea projectWorkerArea);

	/**
	 * 修改项目工作人员区域
	 * 
	 * @param projectWorkerArea 项目工作人员区域
	 * @return 结果
	 */
	public int updateProjectWorkerArea(ProjectWorkerArea projectWorkerArea);

	/**
	 * 批量删除项目工作人员区域
	 * 
	 * @param ids 需要删除的项目工作人员区域主键集合
	 * @return 结果
	 */
	public int deleteProjectWorkerAreaByIds(Long[] ids);

	/**
	 * 删除项目工作人员区域信息
	 * 
	 * @param id 项目工作人员区域主键
	 * @return 结果
	 */
	public int deleteProjectWorkerAreaById(Long id);

	public int saveProjectWorkerArea(@Valid ProjectWorkerAreaDto data);

	public List<ProjectWorkerArea> selectProjectWorkerAreaByWorkerId(Long workerId);
}
