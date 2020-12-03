package com.dataexa.utils;

import com.dataexa.exception.BusinessException;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author 胡志成
 * @Date 2019/12/12
 */
public class BeanCopyUtil {
    /**
     * 拷贝对象
     *
     * @param source      源
     * @param targetClass 目标对象的Class
     * @param <T>         目标对象类型
     * @return 目标对象
     */
    public static <T> T copyObj(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new BusinessException("对象拷贝失败");
        }
    }

    /**
     * 拷贝集合
     *
     * @param source      源
     * @param targetClass 目标对象的Class
     * @param <T>         目标对象类型
     * @return 目标集合
     */
    public static <T> List<T> copyList(List<?> source, Class<T> targetClass) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().map(o -> copyObj(o, targetClass)).collect(Collectors.toList());
    }
}
