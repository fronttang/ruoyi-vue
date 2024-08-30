package com.ruoyi.electrical.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
public class PicUtils {
	/**
	 * 根据指定大小压缩图片
	 *
	 * @param imageBytes  源图片字节数组
	 * @param desFileSize 指定图片大小，单位kb
	 * @param imageId     影像编号
	 * @return 压缩质量后的图片字节数组
	 */
	public static byte[] compressPicForScale(byte[] imageBytes, long desFileSize) {
		if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
			return imageBytes;
		}
		long srcSize = imageBytes.length;
		double accuracy = getAccuracy(srcSize / 1024);
		try {
			while (imageBytes.length > desFileSize * 1024) {
				ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
				Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
				imageBytes = outputStream.toByteArray();
			}
			log.info("【图片压缩】图片原大小={}kb | 压缩后大小={}kb", srcSize / 1024, imageBytes.length / 1024);
		} catch (Exception e) {
			log.error("【图片压缩】msg=图片压缩失败!", e);
		}
		return imageBytes;
	}

	/**
	 * 自动调节精度(经验数值)
	 *
	 * @param size 源图片大小
	 * @return 图片压缩质量比
	 */
	private static double getAccuracy(long size) {
		double accuracy;
		if (size < 900) {
			accuracy = 0.85;
		} else if (size < 2047) {
			accuracy = 0.6;
		} else if (size < 3275) {
			accuracy = 0.44;
		} else {
			accuracy = 0.4;
		}
		return accuracy;
	}

	public static byte[] readFileByte(String pic) {
		try {
			// 本地资源路径
			String localPath = RuoYiConfig.getProfile();
			// 数据库资源地址
			String filePath = localPath + StringUtils.substringAfter(pic, Constants.RESOURCE_PREFIX);
			return FileUtil.readBytes(filePath);
		} catch (Exception e) {

		}
		return null;
	}

//	public static void main(String[] args) {
//
//		byte[] bytes = FileUtil.readBytes(
//				"/Users/fronttang/andiantong-project/电安通资料/update/20240829_1/202408290915505261828964745585201152_57d4cae362501e394b3d2b32d27dfbb7.jpg");
//
//		long l = System.currentTimeMillis();
//		bytes = PicUtils.compressPicForScale(bytes, 100);// 图片小于50kb
//
//		System.out.println(System.currentTimeMillis() - l);
//
//		FileUtil.writeBytes(bytes, "/Users/fronttang/develop/dat/test.jpg");
//	}
}
