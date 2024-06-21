package com.ruoyi.electrical.template.service;

import java.util.List;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;

/**
 * 直观检测标题Service接口
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public interface IIntuitiveDetectService 
{
    /**
     * 查询直观检测标题
     * 
     * @param id 直观检测标题主键
     * @return 直观检测标题
     */
    public IntuitiveDetect selectIntuitiveDetectById(Long id);

    /**
     * 查询直观检测标题列表
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 直观检测标题集合
     */
    public List<IntuitiveDetect> selectIntuitiveDetectList(IntuitiveDetect intuitiveDetect);

    /**
     * 新增直观检测标题
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 结果
     */
    public int insertIntuitiveDetect(IntuitiveDetect intuitiveDetect);

    /**
     * 修改直观检测标题
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 结果
     */
    public int updateIntuitiveDetect(IntuitiveDetect intuitiveDetect);

    /**
     * 批量删除直观检测标题
     * 
     * @param ids 需要删除的直观检测标题主键集合
     * @return 结果
     */
    public int deleteIntuitiveDetectByIds(Long[] ids);

    /**
     * 删除直观检测标题信息
     * 
     * @param id 直观检测标题主键
     * @return 结果
     */
    public int deleteIntuitiveDetectById(Long id);
}
