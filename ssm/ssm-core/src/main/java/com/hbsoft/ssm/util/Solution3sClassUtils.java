package com.hbsoft.ssm.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang.ClassUtils;
import org.springframework.util.StringUtils;

public final class Solution3sClassUtils extends ClassUtils {

    private Solution3sClassUtils() {
    }

    /**
     * Get the first argument type of the <code>clazz</code>.
     * 
     * @param clazz
     *            the class which has an argument type.
     * @return
     * @throws IndexOutOfBoundsException
     *             if the <code>clazz</code> does not has an argument type.
     */
    public static Class<?> getArgumentClass(Class<?> clazz) {
        Type controllerType = ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];
        return (Class<?>) controllerType;
    }

    /**
     * Return the getter method.
     * 
     * @param clazz
     *            the class which the getter method get from.
     * @param fieldName
     *            the fieldName of getter method.
     * @return
     */
    public static Method getGetterMethod(Class<?> clazz, String fieldName) {
        return getGetterSetterMethod(clazz, fieldName, true);
    }

    /**
     * Return the setter method.
     * 
     * @param clazz
     *            the class which the setter method get from.
     * @param fieldName
     *            the fieldName of setter method.
     * @return
     */
    public static Method getSetterMethod(Class<?> clazz, String fieldName) {
        return getGetterSetterMethod(clazz, fieldName, false);
    }

    /**
     * Return getter or setter method base on <code>getter</code> parameter.
     * 
     * @param fieldName
     *            the name of property
     * @param getter
     *            <code>true</code> getter method, <code>false</code> setter method.
     * @return
     */
    private static Method getGetterSetterMethod(Class<?> clazz, String fieldName, boolean getter) {
        try {
            String methodPrefix = getter ? "get" : "set";
            return clazz.getMethod(methodPrefix + StringUtils.capitalize(fieldName));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("field " + fieldName + " or its setter/getter method does not exist in class "
                    + clazz.getName());
        }
    }
}
