package com.ruoyi.electrical.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.design.domain.ElectricityProjectDevice;
import com.ruoyi.electrical.design.mapper.ElectricityProjectDeviceMapper;
import com.ruoyi.electrical.design.service.IElectricityProjectDeviceService;

/**
 * 电力设计项目设备Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Service
public class ElectricityProjectDeviceServiceImpl implements IElectricityProjectDeviceService {

	@Autowired
	private ElectricityProjectDeviceMapper electricityProjectDeviceMapper;

	/**
	 * 查询电力设计项目设备
	 * 
	 * @param id 电力设计项目设备主键
	 * @return 电力设计项目设备
	 */
	@Override
	public ElectricityProjectDevice selectElectricityProjectDeviceById(Long id) {
		return electricityProjectDeviceMapper.selectElectricityProjectDeviceById(id);
	}

	/**
	 * 查询电力设计项目设备列表
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 电力设计项目设备
	 */
	@Override
	public List<ElectricityProjectDevice> selectElectricityProjectDeviceList(
			ElectricityProjectDevice electricityProjectDevice) {
		return electricityProjectDeviceMapper.selectElectricityProjectDeviceList(electricityProjectDevice);
	}

	/**
	 * 新增电力设计项目设备
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 结果
	 */
	@Override
	public int insertElectricityProjectDevice(ElectricityProjectDevice electricityProjectDevice) {
		electricityProjectDevice.setCreateTime(DateUtils.getNowDate());
		return electricityProjectDeviceMapper.insertElectricityProjectDevice(electricityProjectDevice);
	}

	/**
	 * 修改电力设计项目设备
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 结果
	 */
	@Override
	public int updateElectricityProjectDevice(ElectricityProjectDevice electricityProjectDevice) {
		electricityProjectDevice.setUpdateTime(DateUtils.getNowDate());
		return electricityProjectDeviceMapper.updateElectricityProjectDevice(electricityProjectDevice);
	}

	/**
	 * 批量删除电力设计项目设备
	 * 
	 * @param ids 需要删除的电力设计项目设备主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectDeviceByIds(Long[] ids) {
		return electricityProjectDeviceMapper.deleteElectricityProjectDeviceByIds(ids);
	}

	/**
	 * 删除电力设计项目设备信息
	 * 
	 * @param id 电力设计项目设备主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectDeviceById(Long id) {
		return electricityProjectDeviceMapper.deleteElectricityProjectDeviceById(id);
	}
}
