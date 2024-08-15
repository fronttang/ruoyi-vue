package com.ruoyi.electrical.template.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.electrical.template.domain.IntuitiveDetect;
import com.ruoyi.electrical.template.dto.IntuitiveDetectQuery;
import com.ruoyi.electrical.template.dto.TemplateImportHighDto;
import com.ruoyi.electrical.template.dto.TemplateImportHighForm;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormData;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormDataDanger;
import com.ruoyi.electrical.template.dto.TemplateImportHighFormSubData;
import com.ruoyi.electrical.template.dto.TemplateImportStationDto;
import com.ruoyi.electrical.template.service.IIntuitiveDetectService;
import com.ruoyi.electrical.template.service.ITemplateImportHighService;
import com.ruoyi.electrical.template.service.ITemplateImportStationService;
import com.ruoyi.electrical.vo.DictVO;
import com.ruoyi.system.service.ISysDictTypeService;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

/**
 * 直观检测标题Controller
 * 
 * @author fronttang
 * @date 2024-06-19
 */
@Slf4j
@RestController
@RequestMapping("/template/IntuitiveDetect")
public class IntuitiveDetectController extends BaseController {

	@Autowired
	private IIntuitiveDetectService intuitiveDetectService;

	@Autowired
	private ISysDictTypeService dictTypeService;

	@Autowired
	private ITemplateImportHighService templateImportHighService;

	@Autowired
	private ITemplateImportStationService importStationService;

	/**
	 * 查询直观检测标题列表
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:list')")
	@GetMapping("/list")
	public TableDataInfo list(IntuitiveDetectQuery intuitiveDetect) {
		startPage();
		List<IntuitiveDetect> list = intuitiveDetectService.selectIntuitiveDetectList(intuitiveDetect);
		return getDataTable(list);
	}

	/**
	 * 导出直观检测标题列表
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:export')")
	@Log(title = "直观检测标题", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, IntuitiveDetectQuery intuitiveDetect) {
		List<IntuitiveDetect> list = intuitiveDetectService.selectIntuitiveDetectList(intuitiveDetect);
		ExcelUtil<IntuitiveDetect> util = new ExcelUtil<IntuitiveDetect>(IntuitiveDetect.class);
		util.exportExcel(response, list, "直观检测标题数据");
	}

	/**
	 * 获取直观检测标题详细信息
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return success(intuitiveDetectService.selectIntuitiveDetectById(id));
	}

	/**
	 * 新增直观检测标题
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:add')")
	@Log(title = "直观检测标题", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@RequestBody IntuitiveDetect intuitiveDetect) {
		if ("C".equalsIgnoreCase(intuitiveDetect.getType())) {
			IntuitiveDetectQuery query = new IntuitiveDetectQuery();
			query.setType("C");
			query.setUnitType(intuitiveDetect.getUnitType());
			query.setTemplateId(intuitiveDetect.getTemplateId());

			List<IntuitiveDetect> detectCList = intuitiveDetectService.selectIntuitiveDetectList(query);
			if (!CollectionUtils.isEmpty(detectCList)) {
				return error("已经添加过C类检测表,不能再添加!");
			}
		}

		return toAjax(intuitiveDetectService.insertIntuitiveDetect(intuitiveDetect));
	}

	/**
	 * 修改直观检测标题
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:edit')")
	@Log(title = "直观检测标题", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@RequestBody IntuitiveDetect intuitiveDetect) {
		return toAjax(intuitiveDetectService.updateIntuitiveDetect(intuitiveDetect));
	}

	/**
	 * 删除直观检测标题
	 */
	@PreAuthorize("@ss.hasPermi('template:IntuitiveDetect:remove')")
	@Log(title = "直观检测标题", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(intuitiveDetectService.deleteIntuitiveDetectByIds(ids));
	}

	@Log(title = "直观检测标题字典", businessType = BusinessType.OTHER)
	@GetMapping("/dict/{templateId}")
	public AjaxResult dict(@PathVariable Long templateId) {
		return success(intuitiveDetectService.selectIntuitiveDetectDict(templateId));
	}

	@GetMapping("/dict")
	public AjaxResult dict(IntuitiveDetect intuitiveDetect) {
		List<DictVO> list = intuitiveDetectService.selectIntuitiveDetectListDict(intuitiveDetect);
		return success(list);
	}

	/**
	 * 高风险导入
	 * 
	 * @param file
	 * @param updateSupport
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/high/import/{templateId}")
	public AjaxResult importHighFormTemplate(MultipartFile file, boolean delete, String type,
			@PathVariable Long templateId) throws Exception {
		ImportParams params = new ImportParams();
//       导入Excel表中表名所占行
		// params.setTitleRows(1);
//       导入Excel表中属性信息所占行
		params.setHeadRows(1);
		params.setSheetName(type);

		String unitType = null;

		// 获取业主单元类型
		List<SysDictData> dictDatas = dictTypeService.selectDictDataByType("high_risk_type");
		if (CollUtil.isNotEmpty(dictDatas)) {
			Map<String, String> unitTypeMap = dictDatas.stream()
					.collect(Collectors.toMap(SysDictData::getDictLabel, SysDictData::getDictValue));

			if (CollUtil.isNotEmpty(unitTypeMap)) {
				unitType = unitTypeMap.get(type);
			}
		}

		if (StrUtil.isBlank(unitType)) {
			return AjaxResult.error();
		}

		@Cleanup
		InputStream inputStream = file.getInputStream();
		List<TemplateImportHighDto> importData = ExcelImportUtil.importExcel(file.getInputStream(),
				TemplateImportHighDto.class, params);

		Map<String, TemplateImportHighForm> formMap = new HashMap<String, TemplateImportHighForm>();

		if (CollUtil.isNotEmpty(importData)) {
			importData.forEach((indata) -> {

				TemplateImportHighForm form = formMap.get(indata.getName());
				if (form == null) {
					form = new TemplateImportHighForm();
					form.setName(indata.getName());
					form.setMaxScore(indata.getMaxScore());
					formMap.put(indata.getName(), form);
				}

				TemplateImportHighFormData formData = form.getDataMap().get(indata.getArea());
				if (formData == null) {
					formData = new TemplateImportHighFormData();
					formData.setFirstContent(indata.getArea());
					form.getDataMap().put(indata.getArea(), formData);
				}

				TemplateImportHighFormSubData subData = formData.getSubDataMap().get(indata.getContent());
				if (subData == null) {
					subData = new TemplateImportHighFormSubData();
					subData.setFirstContent(indata.getContent());
					subData.setMaxScore(indata.getMScore());
					subData.setAccMethod(indata.getAccMethod());
					formData.getSubDataMap().put(indata.getContent(), subData);
				}

				TemplateImportHighFormDataDanger danger = new TemplateImportHighFormDataDanger();

				danger.setNo(indata.getNo());
				danger.setScore(indata.getScore());
				danger.setDescription(indata.getDescription());
				danger.setSuggestions(indata.getSuggestions());
				danger.setLevel(indata.getLevel());

				subData.getDangers().add(danger);

			});
		}

		log.info("TemplateExcelHighDto:{}", JSON.toJSONString(importData));
		log.info("formMap:{}", JSON.toJSONString(formMap));

		if (CollUtil.isEmpty(formMap)) {
			return AjaxResult.error();
		}

		return toAjax(templateImportHighService.saveFormData(templateId, unitType,
				new ArrayList<TemplateImportHighForm>(formMap.values()), delete));
	}

	/**
	 * 充电站检测项导入
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/station/import/{templateId}")
	public AjaxResult importStationFormTemplate(MultipartFile file, boolean delete, @PathVariable Long templateId)
			throws Exception {

		ImportParams params = new ImportParams();
//      导入Excel表中表名所占行
		// params.setTitleRows(1);
//      导入Excel表中属性信息所占行
		params.setHeadRows(1);

		@Cleanup
		InputStream inputStream = file.getInputStream();
		List<TemplateImportStationDto> importData = ExcelImportUtil.importExcel(file.getInputStream(),
				TemplateImportStationDto.class, params);

		log.info("TemplateImportStationDto:{}", JSON.toJSONString(importData));

		if (CollUtil.isEmpty(importData)) {
			return AjaxResult.error();
		}

		return toAjax(importStationService.saveFormData(templateId, importData, delete));
	}

}
