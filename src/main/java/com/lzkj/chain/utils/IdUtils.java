package com.lzkj.chain.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

/**
 *
 * @auth zone
 * @date 2018-01-26
 */
public class IdUtils {
	private static final int ID_LENGTH = 12;

	public static String generalId() {
		Random ran = new Random();

		String step1 = (System.currentTimeMillis() + System.nanoTime() + ran.nextInt(6000)) + "";
		StringBuffer resultBuffer = new StringBuffer(new BigDecimal(step1).longValue() + "");
		if (step1.length() != ID_LENGTH) {
			if (step1.length() > ID_LENGTH) {
				resultBuffer.setLength(ID_LENGTH);
			} else {
				int zeroNum = ID_LENGTH - step1.length();
				for (int i = 0; i < zeroNum; i++) {
					resultBuffer.append("0");
				}
			}
		}
		int charAt = ran.nextInt(ID_LENGTH);
		resultBuffer.deleteCharAt(charAt);
		resultBuffer.insert(charAt, charAt + "");

		Long dateNum = new BigDecimal(DateUtils.format(new Date(), DateUtils.FULL_INDENT_PATTERN)).longValue();

		Long result = new BigDecimal(Long.parseLong(resultBuffer.toString()) + ran.nextInt() + dateNum).longValue();
		resultBuffer = new StringBuffer(result + "");
		if (step1.length() != ID_LENGTH) {
			if (step1.length() > ID_LENGTH) {
				resultBuffer.setLength(ID_LENGTH);
			} else {
				int zeroNum = ID_LENGTH - step1.length();
				for (int i = 0; i < zeroNum; i++) {
					resultBuffer.append("0");
				}
			}

		}
		return resultBuffer.toString();

	}
}
