package com.ruoyi.electrical.project.service;

import java.util.List;

import com.ruoyi.electrical.project.domain.AreaDict;
import com.ruoyi.electrical.project.domain.ProjectArea;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 项目区域Service接口
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public interface IProjectAreaService {
	/**
	 * 查询项目区域
	 * 
	 * @param id 项目区域主键
	 * @return 项目区域
	 */
	public ProjectArea selectProjectAreaById(Long id);

	/**
	 * 查询项目区域列表
	 * 
	 * @param projectArea 项目区域
	 * @return 项目区域集合
	 */
	public List<ProjectArea> selectProjectAreaList(ProjectArea projectArea);

	/**
	 * 新增项目区域
	 * 
	 * @param projectArea 项目区域
	 * @return 结果
	 */
	public int insertProjectArea(ProjectArea projectArea);

	/**
	 * 修改项目区域
	 * 
	 * @param projectArea 项目区域
	 * @return 结果
	 */
	public int updateProjectArea(ProjectArea projectArea);

	/**
	 * 批量删除项目区域
	 * 
	 * @param ids 需要删除的项目区域主键集合
	 * @return 结果
	 */
	public int deleteProjectAreaByIds(Long[] ids);

	/**
	 * 删除项目区域信息
	 * 
	 * @param id 项目区域主键
	 * @return 结果
	 */
	public int deleteProjectAreaById(Long id);

	public List<DictVO> queryProjectAreaDictByProjectId(Long projectId);

	public List<DictVO> queryProjectAreaDict();

	public List<AreaDict> queryProjectAreaDictByProjectIdAndType(Long projectId, String type);

	public List<ProjectArea> queryProjectAreaByProjectId(Long projectId);
}
