/**
 * 
 */
package com.gome.upm.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

/**
 * @author niulu
 * 
 *         2014年9月19日 下午2:13:21
 */
public class SimpleUtils {

	public static void main(String[] args) {
		File file = new File("d:/人.物.jpgg");

		String name = file.getName();

		String substring = name.substring(name.lastIndexOf("."));
		// JPG、JPEG、PNG和GIF
		if (!".png".equalsIgnoreCase(substring) && !".jpg".equalsIgnoreCase(substring) && !".jpeg".equalsIgnoreCase(substring) && !".gif".equalsIgnoreCase(substring)) {
			System.out.println(false);
		}

		long fileS = file.length();

		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}

		if (fileS > 1048576) {
			System.out.println(false);
		}

		System.out.println(fileSizeString);

		BufferedImage bi = null;
		try {
			bi = ImageIO.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int width = bi.getWidth(); // 像素
		int height = bi.getHeight(); // 像素
		System.out.println("width=" + width + ",height=" + height + ".");

	}
}
