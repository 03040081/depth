package com.zsc.common.validator;

import com.zsc.common.exception.RException;
import org.apache.commons.lang.StringUtils;

public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RException(message);
        }
    }
}
