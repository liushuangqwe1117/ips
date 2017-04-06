/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-8-9
 */

package cn.com.ylink.ips.core.util;

import java.security.SecureRandom;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-8-9
 * @description：随机字符串生成工具
 */

public class RandomStringGeneratorUtils {
	
	private static final char[] PRINTABLE_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ012345679"
	        .toCharArray();

    private static final int DEFAULT_MAX_RANDOM_LENGTH = 32;
    
    private SecureRandom randomizer = new SecureRandom();

    /** The maximum length the random string can be. */
    private final int maximumRandomLength;

    public RandomStringGeneratorUtils() {
        this.maximumRandomLength = DEFAULT_MAX_RANDOM_LENGTH;
    }

    public RandomStringGeneratorUtils(final int maxRandomLength) {
        this.maximumRandomLength = maxRandomLength;
    }

    public String getNewString() {
        final byte[] random = getNewStringAsBytes();

        return convertBytesToString(random);
    }


    private byte[] getNewStringAsBytes() {
        final byte[] random = new byte[this.maximumRandomLength];

        this.randomizer.nextBytes(random);
        
        return random;
    }

    private String convertBytesToString(final byte[] random) {
        final char[] output = new char[random.length];
        for (int i = 0; i < random.length; i++) {
            final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);
            output[i] = PRINTABLE_CHARACTERS[index];
        }

        return new String(output);
    }
}
