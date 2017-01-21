package com.ylian.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by lw on 2016/9/22.
 * 功能描述:
 * 文件操作工具类
 */
public class FileUtil {
	/**
	 * 文件是否存在
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExits(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return false;
		}
		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}

	public static boolean isFileExits(String dirPath, String fileName) {
		if (StringUtils.isEmpty(dirPath))
			return false;
		if (StringUtils.isEmpty(fileName))
			return false;
		File file = new File(dirPath, fileName);
		if (file == null)
			return false;
		if (!file.exists())
			return false;
		return file.isFile();
	}

	/**
	 * 获取文件扩展名
	 *
	 * @param fileName
	 * @return
	 */
	public static String getFileFormat(String fileName) {
		if (StringUtils.isEmpty(fileName))
			return "";

		int point = fileName.lastIndexOf('.');
		return fileName.substring(point + 1);
	}

	/**
	 * 使用文件通道的方式复制文件
	 *
	 * @param from 源文件
	 * @param to   复制到的新文件
	 */

	public static void fileChannelCopy(File from, File to) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(from);
			fo = new FileOutputStream(to);
			in = fi.getChannel();//得到对应的文件通道
			out = fo.getChannel();//得到对应的文件通道
			in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
		} catch (Exception e) {
			Logger.e(e);
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (Exception e) {
				Logger.e(e);
			}
		}
	}
}
