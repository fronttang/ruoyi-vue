package com.ruoyi.electrical.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.ChargingPileMapper;
import com.ruoyi.electrical.project.domain.ChargingPile;
import com.ruoyi.electrical.project.service.IChargingPileService;
import com.ruoyi.electrical.report.dto.station.ChargingPileInfo;
import com.ruoyi.electrical.report.dto.station.StationPeprePic;

/**
 * 充电桩Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-07-18
 */
@Service
public class ChargingPileServiceImpl implements IChargingPileService {
	@Autowired
	private ChargingPileMapper chargingPileMapper;

	/**
	 * 查询充电桩
	 * 
	 * @param id 充电桩主键
	 * @return 充电桩
	 */
	@Override
	public ChargingPile selectChargingPileById(Long id) {
		return chargingPileMapper.selectChargingPileById(id);
	}

	/**
	 * 查询充电桩列表
	 * 
	 * @param chargingPile 充电桩
	 * @return 充电桩
	 */
	@Override
	public List<ChargingPile> selectChargingPileList(ChargingPile chargingPile) {
		return chargingPileMapper.selectChargingPileList(chargingPile);
	}

	/**
	 * 新增充电桩
	 * 
	 * @param chargingPile 充电桩
	 * @return 结果
	 */
	@Override
	public int insertChargingPile(ChargingPile chargingPile) {
		chargingPile.setCreateTime(DateUtils.getNowDate());
		return chargingPileMapper.insertChargingPile(chargingPile);
	}

	/**
	 * 修改充电桩
	 * 
	 * @param chargingPile 充电桩
	 * @return 结果
	 */
	@Override
	public int updateChargingPile(ChargingPile chargingPile) {
		chargingPile.setUpdateTime(DateUtils.getNowDate());
		return chargingPileMapper.updateChargingPile(chargingPile);
	}

	/**
	 * 批量删除充电桩
	 * 
	 * @param ids 需要删除的充电桩主键
	 * @return 结果
	 */
	@Override
	public int deleteChargingPileByIds(Long[] ids) {
		return chargingPileMapper.deleteChargingPileByIds(ids);
	}

	/**
	 * 删除充电桩信息
	 * 
	 * @param id 充电桩主键
	 * @return 结果
	 */
	@Override
	public int deleteChargingPileById(Long id) {
		return chargingPileMapper.deleteChargingPileById(id);
	}

	@Override
	public Integer countChargingPileDangers(Long pileId, Long formDataId) {
		return chargingPileMapper.countChargingPileDangers(pileId, formDataId);
	}

	@Override
	public List<ChargingPileInfo> selectStationPileList(Long unitId) {
		return chargingPileMapper.selectStationPileList(unitId);
	}

	@Override
	public List<StationPeprePic> getStationPeprePicture(Long unitId, Long rounds) {
		return chargingPileMapper.getStationPeprePicture(unitId, rounds);
	}
}
