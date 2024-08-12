package com.ruoyi.electrical.danger.mapper;

import java.util.List;

import com.ruoyi.electrical.danger.domain.ChargeInfrared;

/**
 * 红外判定Mapper接口
 * 
 * @author fronttang
 * @date 2024-08-11
 */
public interface ChargeInfraredMapper {
	/**
	 * 查询红外判定
	 * 
	 * @param id 红外判定主键
	 * @return 红外判定
	 */
	public ChargeInfrared selectChargeInfraredById(Long id);

	/**
	 * 查询红外判定列表
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 红外判定集合
	 */
	public List<ChargeInfrared> selectChargeInfraredList(ChargeInfrared chargeInfrared);

	/**
	 * 新增红外判定
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 结果
	 */
	public int insertChargeInfrared(ChargeInfrared chargeInfrared);

	/**
	 * 修改红外判定
	 * 
	 * @param chargeInfrared 红外判定
	 * @return 结果
	 */
	public int updateChargeInfrared(ChargeInfrared chargeInfrared);

	/**
	 * 删除红外判定
	 * 
	 * @param id 红外判定主键
	 * @return 结果
	 */
	public int deleteChargeInfraredById(Long id);

	/**
	 * 批量删除红外判定
	 * 
	 * @param ids 需要删除的数据主键集合
	 * @return 结果
	 */
	public int deleteChargeInfraredByIds(Long[] ids);
}
