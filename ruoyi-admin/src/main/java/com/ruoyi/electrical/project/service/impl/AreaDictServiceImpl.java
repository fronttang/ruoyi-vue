package com.ruoyi.electrical.project.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.AreaDictMapper;
import com.ruoyi.electrical.project.domain.AreaDict;
import com.ruoyi.electrical.project.service.IAreaDictService;
import com.ruoyi.system.mapper.SysDictDataMapper;

/**
 * 区域字典Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-20
 */
@Service
public class AreaDictServiceImpl implements IAreaDictService {
	@Autowired
	private AreaDictMapper areaDictMapper;

	@Autowired
	private SysDictDataMapper dictDataMapper;

	/**
	 * 查询区域字典
	 * 
	 * @param dictCode 区域字典主键
	 * @return 区域字典
	 */
	@Override
	public AreaDict selectAreaDictByDictCode(Long dictCode) {
		return areaDictMapper.selectAreaDictByDictCode(dictCode);
	}

	/**
	 * 查询区域字典列表
	 * 
	 * @param areaDict 区域字典
	 * @return 区域字典
	 */
	@Override
	public List<AreaDict> selectAreaDictList(AreaDict areaDict) {
		return areaDictMapper.selectAreaDictList(areaDict);
	}

	/**
	 * 新增区域字典
	 * 
	 * @param areaDict 区域字典
	 * @return 结果
	 */
	@Override
	public int insertAreaDict(AreaDict areaDict) {
		areaDict.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		areaDict.setCreateTime(DateUtils.getNowDate());
		areaDict.setUpdateTime(DateUtils.getNowDate());
		int row = areaDictMapper.insertAreaDict(areaDict);
		if (row > 0) {
			List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(areaDict.getDictType());
			DictUtils.setDictCache(areaDict.getDictType(), dictDatas);
		}
		return row;
	}

	/**
	 * 修改区域字典
	 * 
	 * @param areaDict 区域字典
	 * @return 结果
	 */
	@Override
	public int updateAreaDict(AreaDict areaDict) {
		areaDict.setUpdateTime(DateUtils.getNowDate());
		return areaDictMapper.updateAreaDict(areaDict);
	}

	/**
	 * 批量删除区域字典
	 * 
	 * @param dictCodes 需要删除的区域字典主键
	 * @return 结果
	 */
	@Override
	public int deleteAreaDictByDictCodes(Long[] dictCodes) {
		return areaDictMapper.deleteAreaDictByDictCodes(dictCodes);
	}

	/**
	 * 删除区域字典信息
	 * 
	 * @param dictCode 区域字典主键
	 * @return 结果
	 */
	@Override
	public int deleteAreaDictByDictCode(Long dictCode) {
		return areaDictMapper.deleteAreaDictByDictCode(dictCode);
	}
}
