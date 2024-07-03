package com.ruoyi.electrical.project.service;

import java.util.List;
import com.ruoyi.electrical.project.domain.ProjectWorker;

/**
 * 项目工作人员Service接口
 * 
 * @author fronttang
 * @date 2024-07-02
 */
public interface IProjectWorkerService 
{
    /**
     * 查询项目工作人员
     * 
     * @param id 项目工作人员主键
     * @return 项目工作人员
     */
    public ProjectWorker selectProjectWorkerById(Long id);

    /**
     * 查询项目工作人员列表
     * 
     * @param projectWorker 项目工作人员
     * @return 项目工作人员集合
     */
    public List<ProjectWorker> selectProjectWorkerList(ProjectWorker projectWorker);

    /**
     * 新增项目工作人员
     * 
     * @param projectWorker 项目工作人员
     * @return 结果
     */
    public int insertProjectWorker(ProjectWorker projectWorker);

    /**
     * 修改项目工作人员
     * 
     * @param projectWorker 项目工作人员
     * @return 结果
     */
    public int updateProjectWorker(ProjectWorker projectWorker);

    /**
     * 批量删除项目工作人员
     * 
     * @param ids 需要删除的项目工作人员主键集合
     * @return 结果
     */
    public int deleteProjectWorkerByIds(Long[] ids);

    /**
     * 删除项目工作人员信息
     * 
     * @param id 项目工作人员主键
     * @return 结果
     */
    public int deleteProjectWorkerById(Long id);
}
