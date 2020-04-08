package com.deku.request;

import org.apache.commons.lang.StringUtils;

/**
 * 注册方式枚举
 *
 * @Author FENGCHANGXUE
 * @Create 2019/1/14 17:28
 **/
public enum RegistryMethodEnum {
    MOBILE("mobile", "手机号注册"),
    MAIL("mail", "邮箱注册");

    private String code;

    private String name;

    RegistryMethodEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RegistryMethodEnum valueOfCode(String code) {
        for (RegistryMethodEnum value : RegistryMethodEnum.values()) {
            if (StringUtils.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}
