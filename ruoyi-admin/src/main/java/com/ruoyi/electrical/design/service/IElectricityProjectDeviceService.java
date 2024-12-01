package com.ruoyi.electrical.design.service;

import java.util.List;

import com.ruoyi.electrical.design.domain.ElectricityProjectDevice;

/**
 * 电力设计项目设备Service接口
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
public interface IElectricityProjectDeviceService {
	/**
	 * 查询电力设计项目设备
	 * 
	 * @param id 电力设计项目设备主键
	 * @return 电力设计项目设备
	 */
	public ElectricityProjectDevice selectElectricityProjectDeviceById(Long id);

	/**
	 * 查询电力设计项目设备列表
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 电力设计项目设备集合
	 */
	public List<ElectricityProjectDevice> selectElectricityProjectDeviceList(
			ElectricityProjectDevice electricityProjectDevice);

	/**
	 * 新增电力设计项目设备
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 结果
	 */
	public int insertElectricityProjectDevice(ElectricityProjectDevice electricityProjectDevice);

	/**
	 * 修改电力设计项目设备
	 * 
	 * @param electricityProjectDevice 电力设计项目设备
	 * @return 结果
	 */
	public int updateElectricityProjectDevice(ElectricityProjectDevice electricityProjectDevice);

	/**
	 * 批量删除电力设计项目设备
	 * 
	 * @param ids 需要删除的电力设计项目设备主键集合
	 * @return 结果
	 */
	public int deleteElectricityProjectDeviceByIds(Long[] ids);

	/**
	 * 删除电力设计项目设备信息
	 * 
	 * @param id 电力设计项目设备主键
	 * @return 结果
	 */
	public int deleteElectricityProjectDeviceById(Long id);

}
