package com.ruoyi.electrical.danger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.electrical.danger.domain.OwnerUnitDanger;
import com.ruoyi.electrical.danger.service.IOwnerUnitDangerService;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.service.IOwnerUnitService;
import com.ruoyi.electrical.vo.DictVO;

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
	@PreAuthorize("@ss.hasPermi('danger:danger:list')")
	@GetMapping("/list")
	public TableDataInfo list(OwnerUnitDanger ownerUnitDanger) {
		startPage();

		if (ownerUnitDanger.getUnitId() != null) {
			OwnerUnit ownerUnit = ownerUnitService.selectOwnerUnitById(ownerUnitDanger.getUnitId());
			if (ownerUnit != null) {
				ownerUnitDanger.setRounds(ownerUnit.getRounds());
			}
		}

		List<OwnerUnitDanger> list = ownerUnitDangerService.ownerUnitDangerList(ownerUnitDanger);
		return getDataTable(list);
	}

	/**
	 * 获取隐患数据详细信息
	 */
	@PreAuthorize("@ss.hasPermi('danger:danger:query')")
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

}
