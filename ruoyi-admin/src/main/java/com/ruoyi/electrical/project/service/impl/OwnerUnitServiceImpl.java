package com.ruoyi.electrical.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.project.domain.ChargingPile;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.mapper.OwnerUnitMapper;
import com.ruoyi.electrical.project.service.IChargingPileService;
import com.ruoyi.electrical.project.service.IOwnerUnitService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 业主单元Service业务层处理
 * 
 * @author fronttang
 * @date 2024-06-21
 */
@Slf4j
@Service
public class OwnerUnitServiceImpl implements IOwnerUnitService {

	@Autowired
	private OwnerUnitMapper ownerUnitMapper;

	@Autowired
	private RedisCache redisCache;

	@Autowired
	private IChargingPileService chargingPileService;

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	/**
	 * 查询业主单元
	 * 
	 * @param id 业主单元主键
	 * @return 业主单元
	 */
	@Override
	public OwnerUnit selectOwnerUnitById(Long id) {
		return ownerUnitMapper.selectOwnerUnitById(id);
	}

	/**
	 * 查询业主单元列表
	 * 
	 * @param ownerUnit 业主单元
	 * @return 业主单元
	 */
	@Override
	public List<OwnerUnit> selectOwnerUnitList(OwnerUnit ownerUnit) {
		return ownerUnitMapper.selectOwnerUnitList(ownerUnit);
	}

	/**
	 * 新增业主单元
	 * 
	 * @param ownerUnit 业主单元
	 * @return 结果
	 */
	@Override
	public int insertOwnerUnit(OwnerUnit ownerUnit) {
		ownerUnit.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
		ownerUnit.setCreateTime(DateUtils.getNowDate());
		ownerUnit.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitMapper.insertOwnerUnit(ownerUnit);
	}

	/**
	 * 修改业主单元
	 * 
	 * @param ownerUnit 业主单元
	 * @return 结果
	 */
	@Override
	public int updateOwnerUnit(OwnerUnit ownerUnit) {
		ownerUnit.setUpdateTime(DateUtils.getNowDate());
		return ownerUnitMapper.updateOwnerUnit(ownerUnit);
	}

	/**
	 * 批量删除业主单元
	 * 
	 * @param ids 需要删除的业主单元主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitByIds(Long[] ids) {
		return ownerUnitMapper.deleteOwnerUnitByIds(ids);
	}

	/**
	 * 删除业主单元信息
	 * 
	 * @param id 业主单元主键
	 * @return 结果
	 */
	@Override
	public int deleteOwnerUnitById(Long id) {
		return ownerUnitMapper.deleteOwnerUnitById(id);
	}

	@Override
	public boolean startRounds(Long unitId) {

		OwnerUnit ownerUnit = ownerUnitMapper.selectOwnerUnitById(unitId);
		if (ownerUnit == null || !"4".equalsIgnoreCase(ownerUnit.getType())) {
			return false;
		}

		LoginUser loginUser = SecurityUtils.getLoginUser();

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					handlerStartRounds(ownerUnit, loginUser);
				} catch (Exception e) {
					log.error("", e);
					redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP_STATUS + unitId, "error", 1,
							TimeUnit.DAYS);
				}
			}
		};
		thread.start();

		return true;
	}

	@Transactional
	public void handlerStartRounds(OwnerUnit ownerUnit, LoginUser loginUser) throws Exception {

		List<String> steps = new ArrayList<String>();
		Long unitId = ownerUnit.getId();

		// 开始推进
		steps.add("开始推进");
		redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId, steps, 1, TimeUnit.DAYS);

		steps.add("开始复制上一轮数据");
		// 开始复制上一轮数据
		redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId, steps, 1, TimeUnit.DAYS);

		Long currentRounds = ownerUnit.getRounds();

		Long nextRounds = currentRounds + 1;

		ChargingPile query = new ChargingPile();
		query.setUnitId(ownerUnit.getId());
		query.setRounds(ownerUnit.getRounds());

		List<ChargingPile> chargingPiles = chargingPileService.selectChargingPileList(query);
		if (CollUtil.isNotEmpty(chargingPiles)) {

			chargingPiles.forEach(((pile) -> {

				pile.setId(null);
				pile.setRounds(nextRounds);
				pile.setUpdateTime(DateUtils.getNowDate());

				chargingPileService.insertChargingPile(pile);

			}));
		}

		OwnerUnitDanger dangerQuery = new OwnerUnitDanger();
		dangerQuery.setUnitId(unitId);
		dangerQuery.setRounds(currentRounds);

		List<OwnerUnitDanger> dangers = ownerUnitDangerService.selectOwnerUnitDangerList(dangerQuery);
		if (CollUtil.isNotEmpty(dangers)) {
			dangers.forEach((danger) -> {

				danger.setId(null);
				danger.setRounds(nextRounds);
				danger.setUpdateTime(DateUtils.getNowDate());

				ownerUnitDangerService.insertOwnerUnitDanger(danger);
			});
		}

		steps.add("复制成功");
		// 复制成功
		redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId, steps, 1, TimeUnit.DAYS);

		OwnerUnit update = new OwnerUnit();
		update.setId(ownerUnit.getId());
		update.setRounds(nextRounds);
		update.setUpdateBy(String.valueOf(loginUser.getUserId()));
		update.setUpdateTime(DateUtils.getNowDate());
		ownerUnitMapper.updateOwnerUnit(update);

		steps.add(StrUtil.format("轮次推进成功！上一轮次：{},当前轮次：{}.", currentRounds, nextRounds));
		// 轮次推进成功！上一轮次：2；当前轮次：3。
		redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP + unitId, steps, 1, TimeUnit.DAYS);
		redisCache.setCacheObject(CacheConstants.UNIT_ROUND_STEP_STATUS + unitId, "success", 1, TimeUnit.DAYS);
	}

	@Override
	public int checkOwnerUnitName(OwnerUnit ownerUnit) {
		return ownerUnitMapper.checkOwnerUnitName(ownerUnit);
	}

}
