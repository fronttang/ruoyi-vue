package com.ruoyi.electrical.danger.service;

import java.io.File;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.electrical.project.domain.OwnerUnit;
import com.ruoyi.electrical.project.mapper.OwnerUnitMapper;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OwnerUnitQrcodeService {

	@Autowired
	private WxMaService wxMaService;

	@Autowired
	private OwnerUnitMapper ownerUnitMapper;

	public String getOwnerUnitMngQrcode(Long id, String mngQrcode) {

		if (StrUtil.isNotBlank(mngQrcode)) {
			return mngQrcode;
		}
		try {
			String page = "pages/home/user/index";

			DES des = SecureUtil.des("0185786A0362F7F2B0C316B31D1BAD62".getBytes());

			String key = des.encryptHex(String.valueOf(id));

			WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
			byte[] qrCodeByte = qrcodeService.createWxaCodeUnlimitBytes(key, page, false,
					WxMaConstants.DEFAULT_ENV_VERSION, 430, true, null, false);

			LocalDateTime now = LocalDateTime.now();
			String timestamp = DateUtil.format(now, DatePattern.PURE_DATETIME_MS_PATTERN);
			String fileName = timestamp + IdUtil.getSnowflakeNextIdStr();
			String datePath = DateUtil.format(now, "yyyy/MM/dd");

			String filePath = StrUtil.format("{}/{}.png", datePath, fileName);

			String baseDir = RuoYiConfig.getUploadPath();
			File saveFile = FileUploadUtils.getAbsoluteFile(baseDir, filePath);

			ImgUtil.write(ImgUtil.toImage(qrCodeByte), saveFile);

			// String qrcode = ImgUtil.toBase64DataUri(ImgUtil.toImage(qrCodeByte), "png");
			String qrcode = FileUploadUtils.getPathFileName(baseDir, filePath);

			OwnerUnit update = new OwnerUnit();
			update.setId(id);
			update.setMngQrcode(qrcode);
			ownerUnitMapper.updateOwnerUnit(update);

			return qrcode;
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

}
