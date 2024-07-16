package com.ruoyi.electrical.role.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.role.domain.DetectUnitUser;
import com.ruoyi.electrical.role.mapper.DetectUnitUserMapper;
import com.ruoyi.electrical.role.service.IDetectUnitUserService;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;

/**
 * 检测单位账号Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-18
 */
@Service
public class DetectUnitUserServiceImpl implements IDetectUnitUserService {

	@Autowired
	private DetectUnitUserMapper detectUnitUserMapper;

	@Autowired
	private SysUserRoleMapper userRoleMapper;

	/**
	 * 查询检测单位账号
	 * 
	 * @param id 检测单位账号主键
	 * @return 检测单位账号
	 */
	@Override
	public DetectUnitUser selectDetectUnitUserById(Long id) {
		return detectUnitUserMapper.selectDetectUnitUserById(id);
	}

	/**
	 * 查询检测单位账号列表
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 检测单位账号
	 */
	@Override
	public List<DetectUnitUser> selectDetectUnitUserList(DetectUnitUser detectUnitUser) {
		return detectUnitUserMapper.selectDetectUnitUserList(detectUnitUser);
	}

	/**
	 * 新增检测单位账号
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 结果
	 */
	@Override
	@Transactional
	public int insertDetectUnitUser(DetectUnitUser detectUnitUser) {

		detectUnitUser.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		detectUnitUser.setCreateTime(DateUtils.getNowDate());
		detectUnitUser.setUpdateTime(DateUtils.getNowDate());

		detectUnitUserMapper.insertDetectUnitUser(detectUnitUser);

		SysUserRole role = new SysUserRole();
		role.setUserId(detectUnitUser.getId());
		role.setRoleId(Long.valueOf("1" + detectUnitUser.getType()));
		userRoleMapper.batchUserRole(Arrays.asList(role));

		return 1;
	}

	/**
	 * 修改检测单位账号
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 结果
	 */
	@Override
	public int updateDetectUnitUser(DetectUnitUser detectUnitUser) {
		detectUnitUser.setUpdateTime(DateUtils.getNowDate());
		return detectUnitUserMapper.updateDetectUnitUser(detectUnitUser);
	}

	/**
	 * 批量删除检测单位账号
	 * 
	 * @param ids 需要删除的检测单位账号主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectUnitUserByIds(Long[] ids) {
		return detectUnitUserMapper.deleteDetectUnitUserByIds(ids);
	}

	/**
	 * 删除检测单位账号信息
	 * 
	 * @param id 检测单位账号主键
	 * @return 结果
	 */
	@Override
	public int deleteDetectUnitUserById(Long id) {
		return detectUnitUserMapper.deleteDetectUnitUserById(id);
	}

	@Override
	public List<DictVO> sysUserDictByTypeAndProjectId(String type, Long projectId) {
		return detectUnitUserMapper.sysUserDictByTypeAndProjectId(type, projectId);
	}

	@Override
	public List<DictVO> sysUserDict() {
		return detectUnitUserMapper.sysUserDict();
	}
}
