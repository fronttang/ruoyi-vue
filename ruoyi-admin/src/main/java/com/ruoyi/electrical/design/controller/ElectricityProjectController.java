package com.ruoyi.electrical.design.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.electrical.design.domain.ElectricityProject;
import com.ruoyi.electrical.design.domain.ElectricityProjectDevice;
import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage;
import com.ruoyi.electrical.design.domain.ElectricityProjectDeviceImage.DeviceImages;
import com.ruoyi.electrical.design.mapper.ElectricityProjectDeviceImageMapper;
import com.ruoyi.electrical.design.service.IElectricityProjectDeviceImageService;
import com.ruoyi.electrical.design.service.IElectricityProjectDeviceService;
import com.ruoyi.electrical.design.service.IElectricityProjectService;
import com.ruoyi.electrical.design.vo.ElectricityProjectDeviceImageVo;
import com.ruoyi.electrical.design.vo.ElectricityProjectDeviceVo;
import com.ruoyi.electrical.design.vo.ElectricityProjectVo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;

/**
 * 勘探数据查询Controller
 * 
 * @author ruoyi
 * @date 2024-11-24
 */
@RestController
@RequestMapping("/electricity/project")
public class ElectricityProjectController extends BaseController {

	@Autowired
	private IElectricityProjectService electricityProjectService;

	@Autowired
	private IElectricityProjectDeviceService electricityProjectDeviceService;

	@Autowired
	private IElectricityProjectDeviceImageService electricityProjectDeviceImageService;

	@Autowired
	private ElectricityProjectDeviceImageMapper electricityProjectDeviceImageMapper;

	/**
	 * 查询勘探数据查询列表
	 */
	@GetMapping("/list")
	public TableDataInfo list(ElectricityProject electricityProject) {
		startPage();
		List<ElectricityProject> projects = electricityProjectService.selectElectricityProjectList(electricityProject);

		List<ElectricityProjectVo> projectVos = new ArrayList<ElectricityProjectVo>();

		if (CollUtil.isNotEmpty(projects)) {
			projectVos = projects.stream().map((project) -> {
				ElectricityProjectVo vo = new ElectricityProjectVo();
				vo.setId(project.getId());
				vo.setName(project.getName());

				ElectricityProjectDeviceImage query = new ElectricityProjectDeviceImage();
				query.setProjectId(project.getId());

				List<ElectricityProjectDeviceImage> deviceImages = electricityProjectDeviceImageMapper
						.selectElectricityProjectDeviceImageList(query);

				Long images = 0L;
				if (CollUtil.isNotEmpty(deviceImages)) {

					Optional<Integer> reduce = deviceImages.stream().filter(i -> CollUtil.isNotEmpty(i.getImages()))
							.map(i -> i.getImages()).flatMap(i -> i.stream())
							.filter(i -> CollUtil.isNotEmpty(i.getImages())).map(i -> i.getImages().size())
							.reduce(Integer::sum);

					images = reduce.isPresent() ? reduce.get() : 0L;
				}

				vo.setImages(images);

				return vo;
			}).collect(Collectors.toList());
		}

		TableDataInfo dataTable = getDataTable(projects);
		dataTable.setRows(projectVos);
		return dataTable;
	}

	/**
	 * 查询电力设计项目设备列表
	 */
	@GetMapping("/device/list")
	public TableDataInfo list(ElectricityProjectDevice electricityProjectDevice) {
		startPage();
		List<ElectricityProjectDevice> devices = electricityProjectDeviceService
				.selectElectricityProjectDeviceList(electricityProjectDevice);

		List<ElectricityProjectDeviceVo> result = new ArrayList<ElectricityProjectDeviceVo>();

		if (CollUtil.isNotEmpty(devices)) {

			result = devices.stream().map((device) -> {
				ElectricityProjectDeviceVo vo = BeanUtil.copyProperties(device, ElectricityProjectDeviceVo.class);

				ElectricityProjectDeviceImage query = new ElectricityProjectDeviceImage();
				query.setProjectId(device.getProjectId());
				query.setDeviceId(device.getId());

				List<ElectricityProjectDeviceImage> deviceImages = electricityProjectDeviceImageMapper
						.selectElectricityProjectDeviceImageList(query);

				Long images = 0L;
				if (CollUtil.isNotEmpty(deviceImages)) {

					Optional<Integer> reduce = deviceImages.stream().filter(i -> CollUtil.isNotEmpty(i.getImages()))
							.map(i -> i.getImages()).flatMap(i -> i.stream())
							.filter(i -> CollUtil.isNotEmpty(i.getImages())).map(i -> i.getImages().size())
							.reduce(Integer::sum);

					images = reduce.isPresent() ? reduce.get() : 0L;
				}

				vo.setImages(images);

				return vo;
			}).collect(Collectors.toList());
		}

		TableDataInfo dataTable = getDataTable(devices);
		dataTable.setRows(result);
		return dataTable;
	}

	/**
	 * 查询设备照片集列表
	 */
	@GetMapping("/device/image/list")
	public TableDataInfo list(ElectricityProjectDeviceImage electricityProjectDeviceImage) {
		startPage();
		List<ElectricityProjectDeviceImage> images = electricityProjectDeviceImageService
				.selectElectricityProjectDeviceImageList(electricityProjectDeviceImage);

		List<ElectricityProjectDeviceImageVo> result = new ArrayList<ElectricityProjectDeviceImageVo>();

		if (CollUtil.isNotEmpty(images)) {
			result = images.stream().map((image) -> {
				ElectricityProjectDeviceImageVo imageVo = BeanUtil.copyProperties(image,
						ElectricityProjectDeviceImageVo.class);

				List<DeviceImages> deviceImages = image.getImages();

				deviceImages.sort(Comparator.comparing(DeviceImages::getId));

				imageVo.setImages(deviceImages);

				Long count = 0L;
				if (CollUtil.isNotEmpty(deviceImages)) {

					Optional<Integer> reduce = deviceImages.stream().filter(i -> CollUtil.isNotEmpty(i.getImages()))
							.map(i -> i.getImages().size()).reduce(Integer::sum);
					count = reduce.isPresent() ? reduce.get() : 0L;
				}
				imageVo.setCount(count);
				return imageVo;
			}).collect(Collectors.toList());
		}

		TableDataInfo dataTable = getDataTable(images);
		dataTable.setRows(result);
		return dataTable;
	}

}
