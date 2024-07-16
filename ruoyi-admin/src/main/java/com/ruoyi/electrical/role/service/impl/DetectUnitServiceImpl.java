package com.ruoyi.electrical.role.service.impl;

import java.util.Date;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.electrical.role.domain.DetectUnit;
import com.ruoyi.electrical.role.mapper.DetectUnitMapper;
import com.ruoyi.electrical.role.service.IDetectUnitService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 检测单位Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@Service
public class DetectUnitServiceImpl implements IDetectUnitService {
	@Autowired
	private DetectUnitMapper detectUnitMapper;

	/**
	 * 查询检测单位
	 * 
	 * @param id 检测单位主键
	 * @return 检测单位
	 */
	@Override
	public DetectUnit selectDetectUnitById(Long id) {
		return detectUnitMapper.selectDetectUnitById(id);
	}

	/**
	 * 查询检测单位列表
	 * 
	 * @param detectUnit 检测单位
	 * @return 检测单位
	 */
	@Override
	public List<DetectUnit> selectDetectUnitList(DetectUnit detectUnit) {
		return detectUnitMapper.selectDetectUnitList(detectUnit);
	}

	/**
	 * 新增检测单位
	 * 
	 * @param detectUnit 检测单位
	 * @return 结果
	 */
	@Override
	public int insertDetectUnit(DetectUnit detectUnit) {
		detectUnit.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		detectUnit.setCreateTime(DateUtils.getNowDate());
		detectUnit.setUpdateTime(new Date());
		return detectUnitMapper.insertDetectUnit(detectUnit);
	}

	/**
	 * 修改检测单位
	 * 
	 * @param detectUnit 检测单位
	 * @return 结果
	 */
	@Override
	public int updateDetectUnit(DetectUnit detectUnit) {
		detectUnit.setUpdateTime(DateUtils.getNowDate());
		return detectUnitMapper.updateDetectUnit(detectUnit);
	}

	/**
	 * 批量删除检测单位
	 * 
	 * @param ids 需要删除的检测单位主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectUnitByIds(Long[] ids) {
		return detectUnitMapper.deleteDetectUnitByIds(ids);
	}

	/**
	 * 删除检测单位信息
	 * 
	 * @param id 检测单位主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectUnitById(Long id) {
		return detectUnitMapper.deleteDetectUnitById(id);
	}

	@Override
	public List<DictVO> selectDetectUnitDict() {
		return detectUnitMapper.selectDetectUnitDict();
	}
}
