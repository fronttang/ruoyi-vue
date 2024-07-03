package com.ruoyi.electrical.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.project.domain.ProjectWorkerArea;

/**
 * 项目工作人员区域Mapper接口
 * 
 * @author fronttang
 * @date 2024-07-02
 */
public interface ProjectWorkerAreaMapper {
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
	 * 删除项目工作人员区域
	 * 
	 * @param id 项目工作人员区域主键
	 * @return 结果
	 */
	public int deleteProjectWorkerAreaById(Long id);

	/**
	 * 批量删除项目工作人员区域
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteProjectWorkerAreaByIds(Long[] ids);

	public int deleteProjectWorkerAreaByWorkerIdAndType(@Param("workerIds") Long[] workerIds,
			@Param("type") String type);

	public List<ProjectWorkerArea> selectProjectWorkerAreaByWorkerId(Long workerId);
}
