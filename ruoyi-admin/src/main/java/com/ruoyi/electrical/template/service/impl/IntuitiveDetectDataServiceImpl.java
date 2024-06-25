package com.ruoyi.electrical.template.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ruoyi.electrical.template.mapper.IntuitiveDetectDangerMapper;
import com.ruoyi.electrical.template.mapper.IntuitiveDetectDataMapper;
import com.ruoyi.electrical.template.domain.IntuitiveDetectDanger;
import com.ruoyi.electrical.template.domain.IntuitiveDetectData;
import com.ruoyi.electrical.template.service.IIntuitiveDetectDataService;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 直观检测表内容Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-23
 */
@Service
public class IntuitiveDetectDataServiceImpl implements IIntuitiveDetectDataService 
{
    @Autowired
    private IntuitiveDetectDataMapper intuitiveDetectDataMapper;
    
    @Autowired
    private IntuitiveDetectDangerMapper intuitiveDetectDangerMapper;
    

    /**
     * 查询直观检测表内容
     * 
     * @param id 直观检测表内容主键
     * @return 直观检测表内容
     */
    @Override
    public IntuitiveDetectData selectIntuitiveDetectDataById(Long id)
    {
        return intuitiveDetectDataMapper.selectIntuitiveDetectDataById(id);
    }

    /**
     * 查询直观检测表内容列表
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 直观检测表内容
     */
    @Override
    public List<IntuitiveDetectData> selectIntuitiveDetectDataList(IntuitiveDetectData intuitiveDetectData)
    {
        return intuitiveDetectDataMapper.selectIntuitiveDetectDataList(intuitiveDetectData);
    }

    /**
     * 新增直观检测表内容
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 结果
     */
    @Override
    @Transactional
    public int insertIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData)
    {
        intuitiveDetectData.setCreateTime(DateUtils.getNowDate());
        intuitiveDetectData.setUpdateTime(DateUtils.getNowDate());
        int rows = intuitiveDetectDataMapper.insertIntuitiveDetectData(intuitiveDetectData);
        if("2".equalsIgnoreCase(intuitiveDetectData.getType()) || (intuitiveDetectData.getDetectTitle() !=null && 0 == intuitiveDetectData.getDetectTitle())) {
        	List<IntuitiveDetectDanger> dangers = intuitiveDetectData.getDangers();
        	if(!CollectionUtils.isEmpty(dangers)) {
        		for(IntuitiveDetectDanger danger: dangers) {
        			danger.setDataId(intuitiveDetectData.getId());
        			danger.setCreateTime(DateUtils.getNowDate());
        			danger.setUpdateTime(DateUtils.getNowDate());
        			intuitiveDetectDangerMapper.insertIntuitiveDetectDanger(danger);
        		}
        	}
        }
        
        return rows;
    }

    /**
     * 修改直观检测表内容
     * 
     * @param intuitiveDetectData 直观检测表内容
     * @return 结果
     */
    @Override
    @Transactional
    public int updateIntuitiveDetectData(IntuitiveDetectData intuitiveDetectData)
    {
        intuitiveDetectData.setUpdateTime(DateUtils.getNowDate());
        int row = intuitiveDetectDataMapper.updateIntuitiveDetectData(intuitiveDetectData);
        List<IntuitiveDetectDanger> dbDangers = intuitiveDetectDangerMapper.selectIntuitiveDetectDangersByDataId(intuitiveDetectData.getId());
        Map<Long, IntuitiveDetectDanger> dangerMap = new HashMap<Long, IntuitiveDetectDanger>();
        if(!CollectionUtils.isEmpty(dbDangers)) {
        	dangerMap.putAll(dbDangers.stream().collect(Collectors.toMap(IntuitiveDetectDanger::getId, Function.identity())));
        }
        if("2".equalsIgnoreCase(intuitiveDetectData.getType()) || (intuitiveDetectData.getDetectTitle() !=null && 0 == intuitiveDetectData.getDetectTitle())) {
        	List<IntuitiveDetectDanger> dangers = intuitiveDetectData.getDangers();
        	if(!CollectionUtils.isEmpty(dangers)) {
        		for(IntuitiveDetectDanger danger: dangers) {
        			if(danger.getId() == null) {
	        			danger.setDataId(intuitiveDetectData.getId());
	        			danger.setCreateTime(DateUtils.getNowDate());
	        			danger.setUpdateTime(DateUtils.getNowDate());
	        			intuitiveDetectDangerMapper.insertIntuitiveDetectDanger(danger);
        			} else {
        				dangerMap.remove(danger.getId());
        				danger.setUpdateTime(DateUtils.getNowDate());
        				danger.setDataId(intuitiveDetectData.getId());
        				intuitiveDetectDangerMapper.updateIntuitiveDetectDanger(danger);
        			}
        		}
        	}
        } 
        if(!dangerMap.isEmpty()) {
        	Long[] array = dangerMap.keySet().toArray(new Long[dangerMap.size()]);
        	intuitiveDetectDangerMapper.deleteIntuitiveDetectDangerByIds(array);
        }
        return row;
    }

    /**
     * 批量删除直观检测表内容
     * 
     * @param ids 需要删除的直观检测表内容主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteIntuitiveDetectDataByIds(Long[] ids)
    {
    	intuitiveDetectDangerMapper.deleteIntuitiveDetectDangerByDataIds(ids);
        return intuitiveDetectDataMapper.deleteIntuitiveDetectDataByIds(ids);
    }

    /**
     * 删除直观检测表内容信息
     * 
     * @param id 直观检测表内容主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteIntuitiveDetectDataById(Long id)
    {
    	intuitiveDetectDangerMapper.deleteIntuitiveDetectDangerByDataId(id);
        return intuitiveDetectDataMapper.deleteIntuitiveDetectDataById(id);
    }

	@Override
	public List<DictVO> selectIntuitiveDetectDataDict(IntuitiveDetectData intuitiveDetectData) {
		return intuitiveDetectDataMapper.selectIntuitiveDetectDataDict(intuitiveDetectData);
	}

	@Override
	public List<IntuitiveDetectData> selectIntuitiveDetectDataViewList(IntuitiveDetectData intuitiveDetectData) {
		return intuitiveDetectDataMapper.selectIntuitiveDetectDataViewList(intuitiveDetectData);
	}

	@Override
	public IntuitiveDetectData selectIntuitiveDetectDataViewById(Long id) {
		return intuitiveDetectDataMapper.selectIntuitiveDetectDataViewById(id);
	}

	@Override
	public int deleteIntuitiveDetectDataViewByIds(Long[] ids) {
		return intuitiveDetectDataMapper.deleteIntuitiveDetectDataViewByIds(ids);
	}
}
