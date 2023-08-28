package com.crow.qqbot.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.log4j.Log4j2;

/**
 * <p>
 * 随机图片工具类
 * </p>
 * 
 * @author crow
 * @since 2023年8月4日 下午1:44:46
 */
@Log4j2
public class RandomPicUtil {

	/**
	 * 获取本地图片数量
	 * 
	 * @return
	 */
	public static String getLocalPicNum() {
		List<File> loopFiles = FileUtil.loopFiles("E:\\xxx\\111\\r18");
		List<File> loopFiles2 = FileUtil.loopFiles("E:\\xxx\\111\\萌白酱-jiepaizhe.com");
		return String.format("\t剩余%s发", (loopFiles.size() + loopFiles2.size()));
	}

	/**
	 * 随机获取本地图片并且删除
	 * 
	 * @param rotate 是否旋转图片，0不旋转/1旋转
	 * @return
	 */
	public static String getRamdomLocalPicAndDel(Long uin, int rotate, String path) {
		String md5 = "";
		try {

			// 获取所有目录
			File[] listFiles = new File(path).listFiles();
			if (ArrayUtil.isEmpty(listFiles)) {
				return getErrorPic();
			}

			// 随机目录
			ArrayList<File> arrayList = new ArrayList<File>(Arrays.asList(listFiles));
			File randomEle = RandomUtil.randomEle(arrayList);

			// 随机文件
			List<File> loopFiles = FileUtil.loopFiles(randomEle);
			File randomFile = RandomUtil.randomEle(loopFiles);

			BufferedImage read = ImgUtil.read(randomFile);

			if (1 == rotate) {
				java.awt.Image rotateImage = ImgUtil.rotate(read, 180);
				java.awt.Image scale = ImgUtil.scale(rotateImage, 0.9f);
				md5 = ImgUtil.toBase64(scale, "jpg");
			} else {
				java.awt.Image scale = ImgUtil.scale(read, 0.9f);
				md5 = ImgUtil.toBase64(scale, "jpg");
			}

			randomFile.delete();

		} catch (Exception e) {
			md5 = getErrorPic();
			e.printStackTrace();
			log.info("本地图片获取失败");
		}

		return md5;
	}

	/**
	 * 随机r18图片
	 * 
	 * @param uin
	 * @param rotate
	 * @return
	 */
	public static String randomR18Pic(Long uin, int rotate) {
		int randomInt = RandomUtil.randomInt(1, 3);
		if (randomInt == 1) {
			return getRamdomLocalPicAndDel(uin, rotate, "E:\\xxx\\111\\r18");
		} else {
			return getRamdomLocalPicAndDel(uin, rotate, "E:\\xxx\\111\\萌白酱-jiepaizhe.com");
		}

	}

	/**
	 * 随机图片
	 * 
	 * @param uin    群组QQ
	 * @param rotate 是否旋转图片，0不旋转/1旋转
	 * @param r18
	 * @return
	 */
	public static String randomPic(Long uin, int rotate, int r18) {
		if (r18 == 1) {
			return randomR18Pic(uin, rotate);
		} else {
			return getRamdomLocalPicAndDel(uin, rotate, "E:\\xxx\\111\\random");
		}
	}

	/**
	 * 返回错误图片
	 * 
	 * @return
	 */
	public static String getErrorPic() {
		InputStream resourceAsStream = RandomPicUtil.class.getClassLoader()
				.getResourceAsStream("static/img/error2.jpg");
		byte[] readBytes = IoUtil.readBytes(resourceAsStream);
		return Base64.encode(readBytes);
	}

}
