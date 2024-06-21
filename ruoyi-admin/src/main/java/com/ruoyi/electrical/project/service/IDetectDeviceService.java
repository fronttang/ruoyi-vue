package com.ruoyi.electrical.project.service;

import java.util.List;
import com.ruoyi.electrical.project.domain.DetectDevice;

/**
 * 检测仪器Service接口
 * 
 * @author fronttang
 * @date 2024-06-19
 */
public interface IDetectDeviceService 
{
    /**
     * 查询检测仪器
     * 
     * @param id 检测仪器主键
     * @return 检测仪器
     */
    public DetectDevice selectDetectDeviceById(Long id);

    /**
     * 查询检测仪器列表
     * 
     * @param detectDevice 检测仪器
     * @return 检测仪器集合
     */
    public List<DetectDevice> selectDetectDeviceList(DetectDevice detectDevice);

    /**
     * 新增检测仪器
     * 
     * @param detectDevice 检测仪器
     * @return 结果
     */
    public int insertDetectDevice(DetectDevice detectDevice);

    /**
     * 修改检测仪器
     * 
     * @param detectDevice 检测仪器
     * @return 结果
     */
    public int updateDetectDevice(DetectDevice detectDevice);

    /**
     * 批量删除检测仪器
     * 
     * @param ids 需要删除的检测仪器主键集合
     * @return 结果
     */
    public int deleteDetectDeviceByIds(Long[] ids);

    /**
     * 删除检测仪器信息
     * 
     * @param id 检测仪器主键
     * @return 结果
     */
    public int deleteDetectDeviceById(Long id);
}
