package com.ruoyi.electrical.role.mapper;

import java.util.List;
import com.ruoyi.electrical.role.domain.BrandDict;

/**
 * 品牌生产厂家Mapper接口
 * 
 * @author fronttang
 * @date 2024-07-30
 */
public interface BrandDictMapper 
{
    /**
     * 查询品牌生产厂家
     * 
     * @param dictCode 品牌生产厂家主键
     * @return 品牌生产厂家
     */
    public BrandDict selectBrandDictByDictCode(Long dictCode);

    /**
     * 查询品牌生产厂家列表
     * 
     * @param brandDict 品牌生产厂家
     * @return 品牌生产厂家集合
     */
    public List<BrandDict> selectBrandDictList(BrandDict brandDict);

    /**
     * 新增品牌生产厂家
     * 
     * @param brandDict 品牌生产厂家
     * @return 结果
     */
    public int insertBrandDict(BrandDict brandDict);

    /**
     * 修改品牌生产厂家
     * 
     * @param brandDict 品牌生产厂家
     * @return 结果
     */
    public int updateBrandDict(BrandDict brandDict);

    /**
     * 删除品牌生产厂家
     * 
     * @param dictCode 品牌生产厂家主键
     * @return 结果
     */
    public int deleteBrandDictByDictCode(Long dictCode);

    /**
     * 批量删除品牌生产厂家
     * 
     * @param dictCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBrandDictByDictCodes(Long[] dictCodes);
}
