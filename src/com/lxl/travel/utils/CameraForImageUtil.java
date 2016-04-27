package com.lxl.travel.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

public class CameraForImageUtil {
	/** 启动相机状态码 */
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	/** 图片保存的Uri */
	public static Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int CROP_A_PICTURE = 120;

	/** Create a file Uri for saving an image */
	public static Uri getOutputMediaFileUri(String name) {
		return Uri.fromFile(getOutputMediaFile(name));
	}

	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(String name) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = null;
		try {
			// This location works best if you want the created images to be
			// shared
			// between applications and persist after your app has been
			// uninstalled.
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"ETGPictures");

			LogUtil.i("saveInfo", "Successfully created mediaStorageDir: "
					+ mediaStorageDir);
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.i("saveInfo", "Error in Creating mediaStorageDir: "
					+ mediaStorageDir);
		}

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				// 在SD卡上创建文件夹需要权限：
				// <uses-permission
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
				LogUtil.i(
						"saveInfo",
						"failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;

		mediaFile = new File(mediaStorageDir.getPath() + File.separator
				+ name+ ".jpg");

		return mediaFile;
	}
}
