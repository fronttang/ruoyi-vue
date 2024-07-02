package com.ruoyi.electrical.role.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ruoyi.electrical.role.domain.DetectUnitUser;
import com.ruoyi.electrical.vo.DictVO;

/**
 * 检测单位账号Service接口
 * 
 * @author fronttang
 * @date 2024-06-18
 */
public interface IDetectUnitUserService {
	/**
	 * 查询检测单位账号
	 * 
	 * @param id 检测单位账号主键
	 * @return 检测单位账号
	 */
	public DetectUnitUser selectDetectUnitUserById(Long id);

	/**
	 * 查询检测单位账号列表
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 检测单位账号集合
	 */
	public List<DetectUnitUser> selectDetectUnitUserList(DetectUnitUser detectUnitUser);

	/**
	 * 新增检测单位账号
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 结果
	 */
	public int insertDetectUnitUser(DetectUnitUser detectUnitUser);

	/**
	 * 修改检测单位账号
	 * 
	 * @param detectUnitUser 检测单位账号
	 * @return 结果
	 */
	public int updateDetectUnitUser(DetectUnitUser detectUnitUser);

	/**
	 * 批量删除检测单位账号
	 * 
	 * @param ids 需要删除的检测单位账号主键集合
	 * @return 结果
	 */
	public int deleteDetectUnitUserByIds(Long[] ids);

	/**
	 * 删除检测单位账号信息
	 * 
	 * @param id 检测单位账号主键
	 * @return 结果
	 */
	public int deleteDetectUnitUserById(Long id);

	public List<DictVO> sysUserDictByTypeAndProjectId(String type, Long projectId);

	public List<DictVO> sysUserDict();
}
