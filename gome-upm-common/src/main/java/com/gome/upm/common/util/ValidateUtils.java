/**
* Copyright(c) 2002-2013, 360buy.com  All Rights Reserved
*/

package com.gome.upm.common.util;

import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ValidateUtils {
    public static final String DEFAULT_SPLIT = "; ";
    /**
     * jsr303校验，
     *
     * @param errMsg
     * @return
     * @author niulu
     * @date 2013-11-22
     */
    public static String validator(List<ObjectError> errMsg, String splitPattern) {
        if (errMsg.isEmpty()) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<ObjectError> iterator = errMsg.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                if (index != 0) {
                    stringBuilder.append(splitPattern);
                }
                stringBuilder.append(iterator.next().getDefaultMessage());
                index++;
            }
            stringBuilder.trimToSize();
            return stringBuilder.toString();
        }
    }

    /**
     * jsr303校验，
     *
     * @param errMsg
     * @return
     * @author niulu
     * @date 2013-11-22
     */
    public static String validator(List<ObjectError> errMsg) {
        return validator(errMsg, DEFAULT_SPLIT);
    }

    /**
     * 使用多个参数绑定注解，springmvc不支持多个注解时，使用编程式，手动触发。
     *
     * @param cvs
     * @param splitPattern
     * @return
     * @author niulu
     * @date 2013-11-22
     */
    @SuppressWarnings("all")
    public static String validator(Set<ConstraintViolation> cvs, String splitPattern) {
        if (cvs.isEmpty()) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<?> iterator = cvs.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                if (index != 0) {
                    stringBuilder.append(splitPattern);
                }
                stringBuilder.append(((ConstraintViolation) iterator.next()).getMessage());
                index++;
            }
            stringBuilder.trimToSize();
            return stringBuilder.toString();
        }
    }

    /**
     * 使用多个参数绑定注解，springmvc不支持多个注解时，使用编程式，手动触发。
     *
     * @param cvs
     * @return
     * @author niulu
     * @date 2013-11-22
     */
    @SuppressWarnings("rawtypes")
    public static String validator(Set<ConstraintViolation> cvs) {
        return validator(cvs, DEFAULT_SPLIT);
    }
}

