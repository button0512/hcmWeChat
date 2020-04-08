package com.deku.request;


import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by dlgu on 2016/4/26.
 */

@Data
public class SimpleRegisterRequest   implements Serializable {


    private String name;

    private String mobile;

    private String mail;

    private String gender;

    private String certificateNumber;

    private String certificateType;

    private Long levelId;

    private Double registrationFee;


    private String vcardPref;

    private Long channelId;

    private String channelInfo;

    private Long memberCardTypeId;


    private String storeCode;

    private String storeName;

    private String password;

    @Size(max = 50)
    private String hardCardNumber;

    private String remark;

    private String address;

    private String socialId;

    private Long socialCategoryId;

    private String firstName;

    private String lastName;

    private String birthday;

    private String rtype;

    private String jobTitle;

    private Integer emailNotificationFlag;

    private String verificationCode;

    //固定电话
    private String otherPhone;

    private String point;

    /**
     * 国家与详细地址编号
     */
    private String countryAreaCode;


    //邮编
    private String zipCode;

    private String openId;

    private String unionId;

    private String orginWechat;

    private String mobileCountryCode;

    private String security;

    private String answer;

    private String smsCode;

    private Long areaId;

    private String address2;

    private String city;

    /**
     * 城市
     */
    private String district;

    private String memberType;

    /**
     * 是否开卡，默认true
     */
    private Boolean registerAddMemberCard = Boolean.TRUE;


    private String registryMethod = RegistryMethodEnum.MOBILE.getCode();

    private Integer isSync;



}
