package com.ruoyi.electrical.project.mapper;

import java.util.List;
import com.ruoyi.electrical.project.domain.AreaDict;

/**
 * 区域字典Mapper接口
 * 
 * @author fronttang
 * @date 2024-06-20
 */
public interface AreaDictMapper 
{
    /**
     * 查询区域字典
     * 
     * @param dictCode 区域字典主键
     * @return 区域字典
     */
    public AreaDict selectAreaDictByDictCode(Long dictCode);

    /**
     * 查询区域字典列表
     * 
     * @param areaDict 区域字典
     * @return 区域字典集合
     */
    public List<AreaDict> selectAreaDictList(AreaDict areaDict);

    /**
     * 新增区域字典
     * 
     * @param areaDict 区域字典
     * @return 结果
     */
    public int insertAreaDict(AreaDict areaDict);

    /**
     * 修改区域字典
     * 
     * @param areaDict 区域字典
     * @return 结果
     */
    public int updateAreaDict(AreaDict areaDict);

    /**
     * 删除区域字典
     * 
     * @param dictCode 区域字典主键
     * @return 结果
     */
    public int deleteAreaDictByDictCode(Long dictCode);

    /**
     * 批量删除区域字典
     * 
     * @param dictCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAreaDictByDictCodes(Long[] dictCodes);
}
