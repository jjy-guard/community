package com.newcoder.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;



import java.util.UUID;

public class CommonUtil {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String md5(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

}
