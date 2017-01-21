package com.ylian.common.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.util.ArrayList;

/**
 * SdCard操作工具
 */
public final class SDCardUtils {
	public static final String FILE_TYPE_APP = "app";
	public static final String FILE_TYPE_IMAGE = "image";

	private SDCardUtils() {
	        /* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 *
	 * @return
	 */
	public static long getSDCardAllSize() {
		if (isSDCardEnable()) {
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 判断SDCard是否可用
	 *
	 * @return
	 */
	public static boolean isSDCardEnable() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡路径
	 *
	 * @return
	 */
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 *
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath) {
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath())) {
			filePath = getSDCardPath();
		} else {// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统存储路径
	 *
	 * @return
	 */
	public static String getRootDirectoryPath() {
		return Environment.getRootDirectory().getAbsolutePath();
	}


	/**
	 * 再歪一点根目录
	 *
	 * @return
	 */
	public static String getLryRootDirPath() {
		if (!isSDCardEnable())
			return null;
		String rootPath = getSDCardPath() + "/lry/";
		File root = new File(rootPath);
		if (!root.exists() || !root.isDirectory())
			root.mkdirs();
		return rootPath;
	}

	/**
	 * 程序升级下载的根目录
	 *
	 * @return
	 */
	public static String getLryUpgradeDirPath() {
		if (!isSDCardEnable())
			return null;
		String rootPath = getLryRootDirPath() + "update/";
		File root = new File(rootPath);
		if (!root.exists() || !root.isDirectory())
			root.mkdirs();
		return rootPath;
	}

	/**
	 * app下载根目录
	 *
	 * @return
	 */
	public static String getLryAlbumDirPath(String name, int id) {
		if (!isSDCardEnable())
			return null;
		String dirPath = getLryRootDirPath() + "album/" + name + "_" + id + "/";
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdirs();
		return dirPath;
	}

	/**
	 * app下载根目录
	 *
	 * @return
	 */
	public static String getLryMyQrcodeImageSavePath(String url) {
		if (!isSDCardEnable())
			return null;
		String saveName = StringUtils.getMD5(url) + ".jpg";
		String dirPath = getLryRootDirPath() + "myqrcode/";
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdirs();
		String savePath = dirPath + saveName;
		return savePath;
	}

	/**
	 * @return
	 */
	public static String getLryCustomLocationImageSavePath(String url) {
		if (!isSDCardEnable())
			return null;
		String saveName = System.currentTimeMillis() + "_" + ((int) (Math.random() * 9000 + 1000)) + ".jpg";
		String dirPath = getLryRootDirPath() + "custom_location/";
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdirs();
		String savePath = dirPath + saveName;
		return savePath;
	}

	public static String getLryCustomLocationImageSavePngPath() {
		if (!isSDCardEnable())
			return null;
		String saveName = System.currentTimeMillis() + "_" + ((int) (Math.random() * 9000 + 1000)) + ".png";
		String dirPath = getLryRootDirPath() + "custom_location/";
		File dir = new File(dirPath);
		if (!dir.exists() || !dir.isDirectory())
			dir.mkdirs();
		String savePath = dirPath + saveName;
		return savePath;
	}
	/**
	 * 根据图册名字，ID和下载的网络地址集合得到存储的地址的集合
	 *
	 * @param name       图册的名字
	 * @param locationId 图册的ID
	 * @param downUrls   下载的网络地址集合
	 * @return
	 */
	public static ArrayList<String> getLryAlbumSavePaths(String name, int locationId, ArrayList<String> downUrls) {
		ArrayList<String> savePaths = new ArrayList<>();
		if (downUrls == null)
			return savePaths;

		if (StringUtils.isEmpty(name))
			return savePaths;

		// 存储的父文件夹
		String downImageDirPath = getLryAlbumDirPath(name, locationId);

		//
		for (String downUrl : downUrls) {
			String name1 = StringUtils.getMD5(downUrl);
			String name2 = FileUtil.getFileFormat(downUrl);
			String downFileName = name1 + "." + name2;
			String downFilePath = downImageDirPath + downFileName;
			savePaths.add(downFilePath);
		}
		return savePaths;
	}
}
