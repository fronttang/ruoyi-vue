package com.ruoyi.electrical.role.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.role.mapper.BrandDictMapper;
import com.ruoyi.electrical.role.domain.BrandDict;
import com.ruoyi.electrical.role.service.IBrandDictService;

/**
 * 品牌生产厂家Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-30
 */
@Service
public class BrandDictServiceImpl implements IBrandDictService {
	@Autowired
	private BrandDictMapper brandDictMapper;

	/**
	 * 查询品牌生产厂家
	 * 
	 * @param dictCode 品牌生产厂家主键
	 * @return 品牌生产厂家
	 */
	@Override
	public BrandDict selectBrandDictByDictCode(Long dictCode) {
		return brandDictMapper.selectBrandDictByDictCode(dictCode);
	}

	/**
	 * 查询品牌生产厂家列表
	 * 
	 * @param brandDict 品牌生产厂家
	 * @return 品牌生产厂家
	 */
	@Override
	public List<BrandDict> selectBrandDictList(BrandDict brandDict) {
		return brandDictMapper.selectBrandDictList(brandDict);
	}

	/**
	 * 新增品牌生产厂家
	 * 
	 * @param brandDict 品牌生产厂家
	 * @return 结果
	 */
	@Override
	public int insertBrandDict(BrandDict brandDict) {
		brandDict.setCreateTime(DateUtils.getNowDate());
		brandDict.setUpdateTime(DateUtils.getNowDate());
		return brandDictMapper.insertBrandDict(brandDict);
	}

	/**
	 * 修改品牌生产厂家
	 * 
	 * @param brandDict 品牌生产厂家
	 * @return 结果
	 */
	@Override
	public int updateBrandDict(BrandDict brandDict) {
		brandDict.setUpdateTime(DateUtils.getNowDate());
		return brandDictMapper.updateBrandDict(brandDict);
	}

	/**
	 * 批量删除品牌生产厂家
	 * 
	 * @param dictCodes 需要删除的品牌生产厂家主键
	 * @return 结果
	 */
	@Override
	public int deleteBrandDictByDictCodes(Long[] dictCodes) {
		return brandDictMapper.deleteBrandDictByDictCodes(dictCodes);
	}

	/**
	 * 删除品牌生产厂家信息
	 * 
	 * @param dictCode 品牌生产厂家主键
	 * @return 结果
	 */
	@Override
	public int deleteBrandDictByDictCode(Long dictCode) {
		return brandDictMapper.deleteBrandDictByDictCode(dictCode);
	}
}
