package com.imuhao.common.utils;

import android.location.Location;
import android.media.ExifInterface;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dafan on 2016/10/7 0007.
 */

public class GeoTagImage {
	/**
	 * Write Location information to image.
	 *
	 * @param imagePath : image absolute path
	 * @return : location information
	 */
	public static void MarkGeoTagImage(String imagePath, Location location) {
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GPSConvert.convert(location.getLatitude()));
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GPSConvert.latitudeRef(location.getLatitude()));
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GPSConvert.convert(location.getLongitude()));
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GPSConvert.longitudeRef(location.getLongitude()));
			SimpleDateFormat fmt_Exif = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			exif.setAttribute(ExifInterface.TAG_DATETIME, fmt_Exif.format(new Date(System.currentTimeMillis())));
			exif.saveAttributes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read location information from image.
	 *
	 * @param imagePath : image absolute path
	 * @return : loation information
	 */
	public static Location readGeoTagImage(String imagePath) {
		Location loc = new Location("");
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			float[] latlong = new float[2];
			if (exif.getLatLong(latlong)) {
				loc.setLatitude(latlong[0]);
				loc.setLongitude(latlong[1]);
			}

			String date = exif.getAttribute(ExifInterface.TAG_DATETIME);
			SimpleDateFormat fmt_Exif = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			loc.setTime(fmt_Exif.parse(date).getTime());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return loc;
	}
}
