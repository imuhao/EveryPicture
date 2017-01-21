package com.ylian.common.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;

/**
 * 使用MediaPlayer播放音频的类
 * <p>
 * 建议使用单例
 *
 * @author 明明大美女
 */
public class MediaManager {

	private static MediaPlayer mMediaPlayer;
	private static boolean isPause;
	public static boolean isRunning;

	/**
	 * 播放音乐
	 *
	 * @param filePath
	 * @param onCompletionListener
	 */
	public static void playSound(String filePath, OnCompletionListener onCompletionListener) {
		if (mMediaPlayer == null) {
			mMediaPlayer = new MediaPlayer();

			//设置一个error监听器
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {

				public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
					mMediaPlayer.reset();
					return false;
				}
			});
		} else {
			mMediaPlayer.reset();
		}

		try {
			isRunning = true;
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setOnCompletionListener(onCompletionListener);
			mMediaPlayer.setDataSource(filePath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			isRunning = false;
		}
	}

	/**
	 * 停止播放
	 */
	public static void stop() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
			mMediaPlayer.stop();
			isRunning = false;
		}
	}

	/**
	 * 暂停播放
	 */
	public static void pause() {
		if (mMediaPlayer != null && mMediaPlayer.isPlaying()) { //正在播放的时候
			mMediaPlayer.pause();
			isPause = true;
		}
	}

	/**
	 * 当前是isPause状态
	 */
	public static void resume() {
		if (mMediaPlayer != null && isPause) {
			mMediaPlayer.start();
			isPause = false;
		}
	}

	/**
	 * 释放资源
	 */
	public static void release() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
}
