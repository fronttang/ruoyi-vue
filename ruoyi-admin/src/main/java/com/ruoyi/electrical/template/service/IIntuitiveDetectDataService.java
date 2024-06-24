package com.ruoyi.electrical.template.service;

import java.util.List;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;

/**
 * 直观检测表内容Service接口
 * 
 * @author fronttang
 * @date 2024-06-23
 */
public interface IIntuitiveDetectDataService 
{
    /**
     * 查询直观检测表内容
     * 
     * @param id 直观检测表内容主键
     * @return 直观检测表内容
     */
    public IntuitiveDetectData selectIntuitiveDetectDataById(Long id);

    /**
     * 查询直观检测表内容列表
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 直观检测表内容集合
     */
    public List<IntuitiveDetectData> selectIntuitiveDetectDataList(IntuitiveDetectData intuitiveDetectData);

    /**
     * 新增直观检测表内容
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 结果
     */
    public int insertIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData);

    /**
     * 修改直观检测表内容
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 结果
     */
    public int updateIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData);

    /**
     * 批量删除直观检测表内容
     * 
     * @param ids 需要删除的直观检测表内容主键集合
     * @return 结果
     */
    public int deleteIntuitiveDetectDataByIds(Long[] ids);

    /**
     * 删除直观检测表内容信息
     * 
     * @param id 直观检测表内容主键
     * @return 结果
     */
    public int deleteIntuitiveDetectDataById(Long id);
}
