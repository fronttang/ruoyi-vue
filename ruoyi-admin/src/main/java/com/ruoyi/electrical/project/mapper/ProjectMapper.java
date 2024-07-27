package com.ruoyi.electrical.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.project.domain.Project;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 项目Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public interface ProjectMapper {
	/**
	 * 查询项目
	 * 
	 * @param id 项目主键
	 * @return 项目
	 */
	public Project selectProjectById(Long id);

	/**
	 * 查询项目列表
	 * 
	 * @param project 项目
	 * @return 项目集合
	 */
	public List<Project> selectProjectList(Project project);

	public Integer checkProjectName(Project project);

	/**
	 * 查询项目列表-字典
	 * 
	 * @return 项目集合
	 */
	public List<DictVO> selectProjectDict();

	/**
	 * 新增项目
	 * 
	 * @param project 项目
	 * @return 结果
	 */
	public int insertProject(Project project);

	/**
	 * 修改项目
	 * 
	 * @param project 项目
	 * @return 结果
	 */
	public int updateProject(Project project);

	/**
	 * 删除项目
	 * 
	 * @param id 项目主键
	 * @return 结果
	 */
	public int deleteProjectById(Long id);

	/**
	 * 批量删除项目
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteProjectByIds(Long[] ids);

	public List<DictVO> selectProjectDictByDetectId(@Param("detectId") Long detectId);

	public List<DictVO> selectProjectDictByWorkerId(@Param("userId") Long userId);

}
