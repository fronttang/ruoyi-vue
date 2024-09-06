package com.ruoyi.electrical.danger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.electrical.danger.domain.ChargeInfrared;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.handler.IFormbDangerHandler;
import com.ruoyi.electrical.danger.mapper.ChargeInfraredMapper;
import com.ruoyi.electrical.danger.service.IChargeInfraredService;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;

/**
 * 红外判定Service业务层处理
 * 
 * @author fronttang
 * @date 2024-08-11
 */
@Service
public class ChargeInfraredServiceImpl implements IChargeInfraredService {

	@Autowired
	private ChargeInfraredMapper chargeInfraredMapper;

	@Autowired
	private IOwnerUnitDangerService unitDangerService;

	/**
	 * 查询红外判定
	 * 
	 * @param id 红外判定主键
	 * @return 红外判定
	 */
	@Override
	public ChargeInfrared selectChargeInfraredById(Long id) {
		return chargeInfraredMapper.selectChargeInfraredById(id);
	}

	/**
	 * 查询红外判定列表
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 红外判定
	 */
	@Override
	public List<ChargeInfrared> selectChargeInfraredList(ChargeInfrared chargeInfrared) {
		return chargeInfraredMapper.selectChargeInfraredList(chargeInfrared);
	}

	/**
	 * 新增红外判定
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 结果
	 */
	@Override
	public int insertChargeInfrared(ChargeInfrared chargeInfrared) {
		chargeInfrared.setCreateTime(DateUtils.getNowDate());
		return chargeInfraredMapper.insertChargeInfrared(chargeInfrared);
	}

	/**
	 * 修改红外判定
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 结果
	 */
	@Override
	public int updateChargeInfrared(ChargeInfrared chargeInfrared) {

		OwnerUnitDanger danger = unitDangerService.selectOwnerUnitDangerById(chargeInfrared.getId());
		if (danger != null) {
			JSONObject formb = danger.getFormb();
			if (formb != null) {
				JSONObject formbData = formb.getJSONObject("data");
				if (formbData != null) {
					formbData.put("result", chargeInfrared.getResult());
					formbData.put("infraredPic", chargeInfrared.getInfraredPic());

					OwnerUnitDanger update = new OwnerUnitDanger();
					update.setId(chargeInfrared.getId());
					update.setFormb(formb);

					if (IFormbDangerHandler.QUALIFIED.equals(chargeInfrared.getResult())) {
						// 合格变成非隐患
						update.setStatus("9");
					}

					return unitDangerService.updateOwnerUnitDanger(update);
				}
			}
		}
		return 0;
		// chargeInfrared.setUpdateTime(DateUtils.getNowDate());
		// return chargeInfraredMapper.updateChargeInfrared(chargeInfrared);
	}

	/**
	 * 批量删除红外判定
	 * 
	 * @param ids 需要删除的红外判定主键
	 * @return 结果
	 */
	@Override
	public int deleteChargeInfraredByIds(Long[] ids) {
		return chargeInfraredMapper.deleteChargeInfraredByIds(ids);
	}

	/**
	 * 删除红外判定信息
	 * 
	 * @param id 红外判定主键
	 * @return 结果
	 */
	@Override
	public int deleteChargeInfraredById(Long id) {
		return chargeInfraredMapper.deleteChargeInfraredById(id);
	}
}
