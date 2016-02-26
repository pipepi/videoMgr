package com.aepan.sysmgr.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aepan.sysmgr.web.controller.VideoController;

/**
 * 验证码
 * @author mai.yang
 * @date 2013-3-12 下午04:26:48
 */
public class GenerateCheckCode {
	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	public static Object[] getCheckCode(int width, int height, int nums) {
		String code = "";
		try {
			BufferedImage image = new BufferedImage(width, height, 1);

			Graphics g = image.getGraphics();

			Random random = new Random();

			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			g.setFont(new Font("Times New Roman", 0, 18));

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			for (int i = 0; i < nums; i++) {
				String rand = String.valueOf(random.nextInt(10));
				code = code + rand;

				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));

				g.drawString(rand, 13 * i + 6, 20);
			}

			g.dispose();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageOutputStream imageOut = ImageIO
					.createImageOutputStream(output);
			ImageIO.write(image, "JPEG", imageOut);
			imageOut.close();
			ByteArrayInputStream input = new ByteArrayInputStream(
					output.toByteArray());

			Object[] obj = new Object[2];
			obj[0] = input;
			obj[1] = code;
			return obj;
		} catch (IOException e) {
			logger.error("check code generate failed", e);
		}
		return null;
	}
}
