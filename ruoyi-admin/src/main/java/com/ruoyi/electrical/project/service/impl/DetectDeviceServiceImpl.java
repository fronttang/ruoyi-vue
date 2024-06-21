package com.ruoyi.electrical.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.DetectDeviceMapper;
import com.ruoyi.electrical.project.domain.DetectDevice;
import com.ruoyi.electrical.project.service.IDetectDeviceService;

/**
 * 检测仪器Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Service
public class DetectDeviceServiceImpl implements IDetectDeviceService 
{
    @Autowired
    private DetectDeviceMapper detectDeviceMapper;

    /**
     * 查询检测仪器
     * 
     * @param id 检测仪器主键
     * @return 检测仪器
     */
    @Override
    public DetectDevice selectDetectDeviceById(Long id)
    {
        return detectDeviceMapper.selectDetectDeviceById(id);
    }

    /**
     * 查询检测仪器列表
     * 
     * @param detectDevice 检测仪器
     * @return 检测仪器
     */
    @Override
    public List<DetectDevice> selectDetectDeviceList(DetectDevice detectDevice)
    {
        return detectDeviceMapper.selectDetectDeviceList(detectDevice);
    }

    /**
     * 新增检测仪器
     * 
     * @param detectDevice 检测仪器
     * @return 结果
     */
    @Override
    public int insertDetectDevice(DetectDevice detectDevice)
    {
    	detectDevice.setUpdateTime(DateUtils.getNowDate());
        detectDevice.setCreateTime(DateUtils.getNowDate());
        return detectDeviceMapper.insertDetectDevice(detectDevice);
    }

    /**
     * 修改检测仪器
     * 
     * @param detectDevice 检测仪器
     * @return 结果
     */
    @Override
    public int updateDetectDevice(DetectDevice detectDevice)
    {
        detectDevice.setUpdateTime(DateUtils.getNowDate());
        return detectDeviceMapper.updateDetectDevice(detectDevice);
    }

    /**
     * 批量删除检测仪器
     * 
     * @param ids 需要删除的检测仪器主键
     * @return 结果
     */
    @Override
    public int deleteDetectDeviceByIds(Long[] ids)
    {
        return detectDeviceMapper.deleteDetectDeviceByIds(ids);
    }

    /**
     * 删除检测仪器信息
     * 
     * @param id 检测仪器主键
     * @return 结果
     */
    @Override
    public int deleteDetectDeviceById(Long id)
    {
        return detectDeviceMapper.deleteDetectDeviceById(id);
    }
}
