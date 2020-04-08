package com.deku.domain;

import lombok.Data;

import java.util.Map;

/**
 * 客户接口消息发送实体
 *
 * @author
 * @date 2018-2-6 11:00:30
 */
@Data
public class TestMessage {

    /**
     * openid
     */
    private String touser;

    /**
     * 消息类型
     */
    private String msgtype;

    /**
     * 消息内容
     */
    private Map<String, Object> text;


}