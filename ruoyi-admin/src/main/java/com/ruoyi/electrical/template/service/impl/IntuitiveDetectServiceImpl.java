package com.ruoyi.electrical.template.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectMapper;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 直观检测标题Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Service
public class IntuitiveDetectServiceImpl implements IIntuitiveDetectService 
{
    @Autowired
    private IntuitiveDetectMapper intuitiveDetectMapper;

    /**
     * 查询直观检测标题
     * 
     * @param id 直观检测标题主键
     * @return 直观检测标题
     */
    @Override
    public IntuitiveDetect selectIntuitiveDetectById(Long id)
    {
        return intuitiveDetectMapper.selectIntuitiveDetectById(id);
    }

    /**
     * 查询直观检测标题列表
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 直观检测标题
     */
    @Override
    public List<IntuitiveDetect> selectIntuitiveDetectList(IntuitiveDetect intuitiveDetect)
    {
        return intuitiveDetectMapper.selectIntuitiveDetectList(intuitiveDetect);
    }

    /**
     * 新增直观检测标题
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 结果
     */
    @Override
    public int insertIntuitiveDetect(IntuitiveDetect intuitiveDetect)
    {
        intuitiveDetect.setCreateTime(DateUtils.getNowDate());
        return intuitiveDetectMapper.insertIntuitiveDetect(intuitiveDetect);
    }

    /**
     * 修改直观检测标题
     * 
     * @param intuitiveDetect 直观检测标题
     * @return 结果
     */
    @Override
    public int updateIntuitiveDetect(IntuitiveDetect intuitiveDetect)
    {
        intuitiveDetect.setUpdateTime(DateUtils.getNowDate());
        return intuitiveDetectMapper.updateIntuitiveDetect(intuitiveDetect);
    }

    /**
     * 批量删除直观检测标题
     * 
     * @param ids 需要删除的直观检测标题主键
     * @return 结果
     */
    @Override
    public int deleteIntuitiveDetectByIds(Long[] ids)
    {
        return intuitiveDetectMapper.deleteIntuitiveDetectByIds(ids);
    }

    /**
     * 删除直观检测标题信息
     * 
     * @param id 直观检测标题主键
     * @return 结果
     */
    @Override
    public int deleteIntuitiveDetectById(Long id)
    {
        return intuitiveDetectMapper.deleteIntuitiveDetectById(id);
    }

	@Override
	public List<DictVO> selectIntuitiveDetectDict(Long templateId) {
		return intuitiveDetectMapper.selectIntuitiveDetectDict(templateId);
	}
}
