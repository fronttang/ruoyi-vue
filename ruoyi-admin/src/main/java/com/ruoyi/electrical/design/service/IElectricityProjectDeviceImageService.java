package com.ruoyi.electrical.design.service;

import java.util.List;

import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage;

/**
 * 设备照片集Service接口
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
public interface IElectricityProjectDeviceImageService {
	/**
	 * 查询设备照片集
	 * 
	 * @param id 设备照片集主键
	 * @return 设备照片集
	 */
	public ElectricityProjectDeviceImage selectElectricityProjectDeviceImageById(Long id);

	/**
	 * 查询设备照片集列表
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 设备照片集集合
	 */
	public List<ElectricityProjectDeviceImage> selectElectricityProjectDeviceImageList(
			ElectricityProjectDeviceImage electricityProjectDeviceImage);

	/**
	 * 新增设备照片集
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 结果
	 */
	public int insertElectricityProjectDeviceImage(ElectricityProjectDeviceImage electricityProjectDeviceImage);

	/**
	 * 修改设备照片集
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 结果
	 */
	public int updateElectricityProjectDeviceImage(ElectricityProjectDeviceImage electricityProjectDeviceImage);

	/**
	 * 批量删除设备照片集
	 * 
	 * @param ids 需要删除的设备照片集主键集合
	 * @return 结果
	 */
	public int deleteElectricityProjectDeviceImageByIds(Long[] ids);

	/**
	 * 删除设备照片集信息
	 * 
	 * @param id 设备照片集主键
	 * @return 结果
	 */
	public int deleteElectricityProjectDeviceImageById(Long id);

}
