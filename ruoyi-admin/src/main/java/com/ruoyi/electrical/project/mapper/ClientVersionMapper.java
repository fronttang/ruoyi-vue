package com.ruoyi.electrical.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ruoyi.electrical.project.domain.ClientVersion;

/**
 * 版本更新Mapper接口
 * 
 * @author fronttang
 * @date 2024-07-31
 */
public interface ClientVersionMapper 
{
    /**
     * 查询版本更新
     * 
     * @param id 版本更新主键
     * @return 版本更新
     */
    public ClientVersion selectClientVersionById(Long id);

    /**
     * 查询版本更新列表
     * 
     * @param clientVersion 版本更新
     * @return 版本更新集合
     */
    public List<ClientVersion> selectClientVersionList(ClientVersion clientVersion);

    /**
     * 新增版本更新
     * 
     * @param clientVersion 版本更新
     * @return 结果
     */
    public int insertClientVersion(ClientVersion clientVersion);

    /**
     * 修改版本更新
     * 
     * @param clientVersion 版本更新
     * @return 结果
     */
    public int updateClientVersion(ClientVersion clientVersion);

    /**
     * 删除版本更新
     * 
     * @param id 版本更新主键
     * @return 结果
     */
    public int deleteClientVersionById(Long id);

    /**
     * 批量删除版本更新
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClientVersionByIds(Long[] ids);

    @Select("SELECT *,download_url as downloadUrl FROM client_version WHERE client = #{client} ORDER BY id DESC LIMIT 1")
	public ClientVersion getLatestClientVersion(@Param("client") String client);
}
