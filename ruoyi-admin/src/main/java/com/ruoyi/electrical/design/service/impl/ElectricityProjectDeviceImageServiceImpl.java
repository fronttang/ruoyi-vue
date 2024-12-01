package com.ruoyi.electrical.design.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage;
import com.ruoyi.electrical.design.mapper.ElectricityProjectDeviceImageMapper;
import com.ruoyi.electrical.design.service.IElectricityProjectDeviceImageService;

/**
 * 设备照片集Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@Service
public class ElectricityProjectDeviceImageServiceImpl implements IElectricityProjectDeviceImageService {
	@Autowired
	private ElectricityProjectDeviceImageMapper electricityProjectDeviceImageMapper;

	/**
	 * 查询设备照片集
	 * 
	 * @param id 设备照片集主键
	 * @return 设备照片集
	 */
	@Override
	public ElectricityProjectDeviceImage selectElectricityProjectDeviceImageById(Long id) {
		return electricityProjectDeviceImageMapper.selectElectricityProjectDeviceImageById(id);
	}

	/**
	 * 查询设备照片集列表
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 设备照片集
	 */
	@Override
	public List<ElectricityProjectDeviceImage> selectElectricityProjectDeviceImageList(
			ElectricityProjectDeviceImage electricityProjectDeviceImage) {
		return electricityProjectDeviceImageMapper
				.selectElectricityProjectDeviceImageList(electricityProjectDeviceImage);
	}

	/**
	 * 新增设备照片集
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 结果
	 */
	@Override
	public int insertElectricityProjectDeviceImage(ElectricityProjectDeviceImage electricityProjectDeviceImage) {
		electricityProjectDeviceImage.setCreateTime(DateUtils.getNowDate());
		return electricityProjectDeviceImageMapper.insertElectricityProjectDeviceImage(electricityProjectDeviceImage);
	}

	/**
	 * 修改设备照片集
	 * 
	 * @param electricityProjectDeviceImage 设备照片集
	 * @return 结果
	 */
	@Override
	public int updateElectricityProjectDeviceImage(ElectricityProjectDeviceImage electricityProjectDeviceImage) {
		electricityProjectDeviceImage.setUpdateTime(DateUtils.getNowDate());
		return electricityProjectDeviceImageMapper.updateElectricityProjectDeviceImage(electricityProjectDeviceImage);
	}

	/**
	 * 批量删除设备照片集
	 * 
	 * @param ids 需要删除的设备照片集主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectDeviceImageByIds(Long[] ids) {
		return electricityProjectDeviceImageMapper.deleteElectricityProjectDeviceImageByIds(ids);
	}

	/**
	 * 删除设备照片集信息
	 * 
	 * @param id 设备照片集主键
	 * @return 结果
	 */
	@Override
	public int deleteElectricityProjectDeviceImageById(Long id) {
		return electricityProjectDeviceImageMapper.deleteElectricityProjectDeviceImageById(id);
	}
}
