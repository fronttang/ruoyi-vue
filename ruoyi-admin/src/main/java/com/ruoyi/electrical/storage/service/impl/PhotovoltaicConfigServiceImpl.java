package com.ruoyi.electrical.storage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.storage.mapper.PhotovoltaicConfigMapper;
import com.ruoyi.electrical.storage.domain.PhotovoltaicConfig;
import com.ruoyi.electrical.storage.service.IPhotovoltaicConfigService;

/**
 * 光伏参数Service业务层处理
 * 
 * @author fronttang
 * @date 2024-09-24
 */
@Service
public class PhotovoltaicConfigServiceImpl implements IPhotovoltaicConfigService {
	@Autowired
	private PhotovoltaicConfigMapper photovoltaicConfigMapper;

	/**
	 * 查询光伏参数
	 * 
	 * @param id 光伏参数主键
	 * @return 光伏参数
	 */
	@Override
	public PhotovoltaicConfig selectPhotovoltaicConfigById(Long id) {
		PhotovoltaicConfig photovoltaicConfig = photovoltaicConfigMapper.selectPhotovoltaicConfigById(id);
		return photovoltaicConfig;
	}

	/**
	 * 查询光伏参数列表
	 * 
	 * @param photovoltaicConfig 光伏参数
	 * @return 光伏参数
	 */
	@Override
	public List<PhotovoltaicConfig> selectPhotovoltaicConfigList(PhotovoltaicConfig photovoltaicConfig) {
		return photovoltaicConfigMapper.selectPhotovoltaicConfigList(photovoltaicConfig);
	}

	/**
	 * 新增光伏参数
	 * 
	 * @param photovoltaicConfig 光伏参数
	 * @return 结果
	 */
	@Override
	public int insertPhotovoltaicConfig(PhotovoltaicConfig photovoltaicConfig) {
		photovoltaicConfig.setCreateTime(DateUtils.getNowDate());
		return photovoltaicConfigMapper.insertPhotovoltaicConfig(photovoltaicConfig);
	}

	/**
	 * 修改光伏参数
	 * 
	 * @param photovoltaicConfig 光伏参数
	 * @return 结果
	 */
	@Override
	public int updatePhotovoltaicConfig(PhotovoltaicConfig photovoltaicConfig) {
		photovoltaicConfig.setUpdateTime(DateUtils.getNowDate());
		return photovoltaicConfigMapper.updatePhotovoltaicConfig(photovoltaicConfig);
	}

	/**
	 * 批量删除光伏参数
	 * 
	 * @param ids 需要删除的光伏参数主键
	 * @return 结果
	 */
	@Override
	public int deletePhotovoltaicConfigByIds(Long[] ids) {
		return photovoltaicConfigMapper.deletePhotovoltaicConfigByIds(ids);
	}

	/**
	 * 删除光伏参数信息
	 * 
	 * @param id 光伏参数主键
	 * @return 结果
	 */
	@Override
	public int deletePhotovoltaicConfigById(Long id) {
		return photovoltaicConfigMapper.deletePhotovoltaicConfigById(id);
	}
}
