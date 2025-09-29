package com.ruoyi.electrical.danger.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.dto.OwnerUnitDangerGroupDetailDto;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.electrical.vo.OwnerUnitDangerGroupDetailVo;
import com.ruoyi.electrical.vo.OwnerUnitDangerPicturesVo;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 隐患数据Controller
 * 
 * @author ruoyi
 * @date 2024-07-15
 */
@RestController
@RequestMapping("/danger")
public class OwnerUnitDangerController extends BaseController {

	@Autowired
	private IOwnerUnitDangerService ownerUnitDangerService;

	@Autowired
	private IOwnerUnitService ownerUnitService;

	/**
	 * 查询隐患数据列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(OwnerUnitDanger ownerUnitDanger) {

		if (ownerUnitDanger.getUnitId() != null) {
			OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(ownerUnitDanger.getUnitId());
			if (ownerUnit != null) {
				ownerUnitDanger.setRounds(ownerUnit.getRounds());
			}
		}

		startPage();
		List<OwnerUnitDanger> list = ownerUnitDangerService.ownerUnitDangerList(ownerUnitDanger);
		return getDataTable(list);
	}

	@GetMapping("/unit/list")
	public TableDataInfo unitList(OwnerUnitDangerGroupDetailDto data) {
		startPage();
		List<OwnerUnitDangerGroupDetailVo> list = ownerUnitDangerService.ownerUnitDangerGroupList(data);
		
		list.forEach(item -> {
			
			OwnerUnitDanger query = new OwnerUnitDanger();
			query.setUnitId(item.getUnitId());
			List<OwnerUnitDanger> ownerUnitDangerList = ownerUnitDangerService.ownerUnitDangerList(query);
			item.setDangers(ownerUnitDangerList.stream().filter(danger -> !"9".equals(danger.getStatus())).count());
			item.setFinishs(ownerUnitDangerList.stream().filter(danger -> "2".equals(danger.getStatus())).count());
			item.setReexaminations(ownerUnitDangerList.stream().filter(danger -> "1".equals(danger.getStatus())).count());
			item.setRectifications(ownerUnitDangerList.stream().filter(danger -> "0".equals(danger.getStatus())).count());
			
		});
		
		return getDataTable(list);
	}

	@GetMapping("/unit/building/list")
	public TableDataInfo unitBuildingList(OwnerUnitDangerGroupDetailDto data) {
		startPage();
		List<OwnerUnitDangerGroupDetailVo> list = ownerUnitDangerService.ownerUnitBuildingDangerGroupList(data);
		
		list.forEach(item -> {
			
			OwnerUnitDanger query = new OwnerUnitDanger();
			query.setUnitId(item.getUnitId());
			query.setBuildingId(item.getId());
			List<OwnerUnitDanger> ownerUnitDangerList = ownerUnitDangerService.ownerUnitDangerList(query);
			item.setDangers(ownerUnitDangerList.stream().filter(danger -> !"9".equals(danger.getStatus())).count());
			item.setFinishs(ownerUnitDangerList.stream().filter(danger -> "2".equals(danger.getStatus())).count());
			item.setReexaminations(ownerUnitDangerList.stream().filter(danger -> "1".equals(danger.getStatus())).count());
			item.setRectifications(ownerUnitDangerList.stream().filter(danger -> "0".equals(danger.getStatus())).count());
			
		});

		return getDataTable(list);
	}

	/**
	 * 获取隐患数据详细信息
	 */
	// @PreAuthorize("@ss.hasPermi('danger:danger:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(ownerUnitDangerService.ownerUnitDangerById(id));
	}

	/**
	 * 楼栋字典
	 * 
	 * @param unitId
	 * @return
	 */
	@GetMapping("/building/dict/{unitId}")
	public AjaxResult buildingDict(@PathVariable Long unitId) {
		List<DictVO> dict = ownerUnitDangerService.selecOwnerUnitBuildingDict(unitId);
		return AjaxResult.success(dict);
	}

	@GetMapping("/reset/{ids}/{status}")
	public AjaxResult reset(@PathVariable Long[] ids, @PathVariable String status) {
		return AjaxResult.success(ownerUnitDangerService.resetDangerStatus(ids, status));
	}

	@GetMapping("/pictures/{unitId}")
	public AjaxResult pictures(@PathVariable Long unitId) {

		OwnerUnitDanger query = new OwnerUnitDanger();
		query.setUnitId(unitId);
		return buildOwnerUnitDangerPicturesVo(query);
	}

	@GetMapping("/pictures/{unitId}/{buildingId}")
	public AjaxResult pictures(@PathVariable Long unitId, @PathVariable Long buildingId) {

		OwnerUnitDanger query = new OwnerUnitDanger();
		query.setUnitId(unitId);
		query.setBuildingId(buildingId);

		return buildOwnerUnitDangerPicturesVo(query);
	}

	private AjaxResult buildOwnerUnitDangerPicturesVo(OwnerUnitDanger query) {
		List<OwnerUnitDanger> dangers = ownerUnitDangerService.ownerUnitDangerList(query);
		List<OwnerUnitDangerPicturesVo> result = new ArrayList<OwnerUnitDangerPicturesVo>();
		if (CollUtil.isNotEmpty(dangers)) {
			for (OwnerUnitDanger danger : dangers) {
				String dangerPic = danger.getPicture();
				if (StrUtil.isNotBlank(dangerPic)) {
					List<String> pictures = StrUtil.split(dangerPic, ",");
					if (CollUtil.isNotEmpty(pictures)) {
						for (String picture : pictures) {
							OwnerUnitDangerPicturesVo vo = new OwnerUnitDangerPicturesVo();
							vo.setName(danger.getReportLocation());
							vo.setPicture(picture);
							result.add(vo);
						}
					}
				}
			}
		}
		return AjaxResult.success(result);
	}

}
