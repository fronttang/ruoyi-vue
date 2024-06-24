package com.ruoyi.electrical.template.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectDangerMapper;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDangerService;

/**
 * 检测内容隐患Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-23
 */
@Service
public class IntuitiveDetectDangerServiceImpl implements IIntuitiveDetectDangerService 
{
    @Autowired
    private IntuitiveDetectDangerMapper intuitiveDetectDangerMapper;

    /**
     * 查询检测内容隐患
     * 
     * @param id 检测内容隐患主键
     * @return 检测内容隐患
     */
    @Override
    public IntuitiveDetectDanger selectIntuitiveDetectDangerById(Long id)
    {
        return intuitiveDetectDangerMapper.selectIntuitiveDetectDangerById(id);
    }

    /**
     * 查询检测内容隐患列表
     * 
     * @param intuitiveDetectDanger 检测内容隐患
     * @return 检测内容隐患
     */
    @Override
    public List<IntuitiveDetectDanger> selectIntuitiveDetectDangerList(IntuitiveDetectDanger intuitiveDetectDanger)
    {
        return intuitiveDetectDangerMapper.selectIntuitiveDetectDangerList(intuitiveDetectDanger);
    }

    /**
     * 新增检测内容隐患
     * 
     * @param intuitiveDetectDanger 检测内容隐患
     * @return 结果
     */
    @Override
    public int insertIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger)
    {
        intuitiveDetectDanger.setCreateTime(DateUtils.getNowDate());
        return intuitiveDetectDangerMapper.insertIntuitiveDetectDanger(intuitiveDetectDanger);
    }

    /**
     * 修改检测内容隐患
     * 
     * @param intuitiveDetectDanger 检测内容隐患
     * @return 结果
     */
    @Override
    public int updateIntuitiveDetectDanger(IntuitiveDetectDanger intuitiveDetectDanger)
    {
        intuitiveDetectDanger.setUpdateTime(DateUtils.getNowDate());
        return intuitiveDetectDangerMapper.updateIntuitiveDetectDanger(intuitiveDetectDanger);
    }

    /**
     * 批量删除检测内容隐患
     * 
     * @param ids 需要删除的检测内容隐患主键
     * @return 结果
     */
    @Override
    public int deleteIntuitiveDetectDangerByIds(Long[] ids)
    {
        return intuitiveDetectDangerMapper.deleteIntuitiveDetectDangerByIds(ids);
    }

    /**
     * 删除检测内容隐患信息
     * 
     * @param id 检测内容隐患主键
     * @return 结果
     */
    @Override
    public int deleteIntuitiveDetectDangerById(Long id)
    {
        return intuitiveDetectDangerMapper.deleteIntuitiveDetectDangerById(id);
    }

	@Override
	public List<IntuitiveDetectDanger> selectIntuitiveDetectDangersByDataId(Long dataId) {
		return intuitiveDetectDangerMapper.selectIntuitiveDetectDangersByDataId(dataId);
	}
}
