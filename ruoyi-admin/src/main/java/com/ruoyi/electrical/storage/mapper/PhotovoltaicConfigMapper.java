package com.ruoyi.electrical.storage.mapper;

import java.util.List;
import com.ruoyi.electrical.storage.domain.PhotovoltaicConfig;

/**
 * 光伏参数Mapper接口
 * 
 * @author fronttang
 * @date 2024-09-24
 */
public interface PhotovoltaicConfigMapper 
{
    /**
     * 查询光伏参数
     * 
     * @param id 光伏参数主键
     * @return 光伏参数
     */
    public PhotovoltaicConfig selectPhotovoltaicConfigById(Long id);

    /**
     * 查询光伏参数列表
     * 
     * @param photovoltaicConfig 光伏参数
     * @return 光伏参数集合
     */
    public List<PhotovoltaicConfig> selectPhotovoltaicConfigList(PhotovoltaicConfig photovoltaicConfig);

    /**
     * 新增光伏参数
     * 
     * @param photovoltaicConfig 光伏参数
     * @return 结果
     */
    public int insertPhotovoltaicConfig(PhotovoltaicConfig photovoltaicConfig);

    /**
     * 修改光伏参数
     * 
     * @param photovoltaicConfig 光伏参数
     * @return 结果
     */
    public int updatePhotovoltaicConfig(PhotovoltaicConfig photovoltaicConfig);

    /**
     * 删除光伏参数
     * 
     * @param id 光伏参数主键
     * @return 结果
     */
    public int deletePhotovoltaicConfigById(Long id);

    /**
     * 批量删除光伏参数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhotovoltaicConfigByIds(Long[] ids);
}
