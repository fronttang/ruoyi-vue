package com.ruoyi.electrical.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.electrical.project.mapper.ClientVersionMapper;
import com.ruoyi.electrical.project.domain.ClientVersion;
import com.ruoyi.electrical.project.service.IClientVersionService;

/**
 * 版本更新Service业务层处理
 * 
 * @author fronttang
 * @date 2024-07-31
 */
@Service
public class ClientVersionServiceImpl implements IClientVersionService {
	@Autowired
	private ClientVersionMapper clientVersionMapper;

	/**
	 * 查询版本更新
	 * 
	 * @param id 版本更新主键
	 * @return 版本更新
	 */
	@Override
	public ClientVersion selectClientVersionById(Long id) {
		return clientVersionMapper.selectClientVersionById(id);
	}

	/**
	 * 查询版本更新列表
	 * 
	 * @param clientVersion 版本更新
	 * @return 版本更新
	 */
	@Override
	public List<ClientVersion> selectClientVersionList(ClientVersion clientVersion) {
		return clientVersionMapper.selectClientVersionList(clientVersion);
	}

	/**
	 * 新增版本更新
	 * 
	 * @param clientVersion 版本更新
	 * @return 结果
	 */
	@Override
	public int insertClientVersion(ClientVersion clientVersion) {
		clientVersion.setCreateTime(DateUtils.getNowDate());
		clientVersion.setUpdateTime(DateUtils.getNowDate());
		return clientVersionMapper.insertClientVersion(clientVersion);
	}

	/**
	 * 修改版本更新
	 * 
	 * @param clientVersion 版本更新
	 * @return 结果
	 */
	@Override
	public int updateClientVersion(ClientVersion clientVersion) {
		clientVersion.setUpdateTime(DateUtils.getNowDate());
		return clientVersionMapper.updateClientVersion(clientVersion);
	}

	/**
	 * 批量删除版本更新
	 * 
	 * @param ids 需要删除的版本更新主键
	 * @return 结果
	 */
	@Override
	public int deleteClientVersionByIds(Long[] ids) {
		return clientVersionMapper.deleteClientVersionByIds(ids);
	}

	/**
	 * 删除版本更新信息
	 * 
	 * @param id 版本更新主键
	 * @return 结果
	 */
	@Override
	public int deleteClientVersionById(Long id) {
		return clientVersionMapper.deleteClientVersionById(id);
	}
}
