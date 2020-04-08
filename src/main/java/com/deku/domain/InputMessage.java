package com.deku.domain;

import java.io.Serializable;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * POST的XML数据包转换为消息接受对象
 * 
 * <p>
 * 由于POST的是XML数据包，所以不确定为哪种接受消息，<br/>
 * 所以直接将所有字段都进行转换，最后根据<tt>MsgType</tt>字段来判断取何种数据
 * </p>
 * 
 */
@XStreamAlias("xml")
@Data
public class InputMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	@XStreamAlias("ToUserName")
	private String toUserName;
	@XStreamAlias("FromUserName")
	private String fromUserName;
	@XStreamAlias("CreateTime")
	private Long createTime;
	@XStreamAlias("MsgType")
	private String msgType = "text";
	@XStreamAlias("MsgId")
	private Long msgId;
	/**
	 * 文本消息
	 */
	@XStreamAlias("Content")
	private String content;
	/**
	 * 图片消息
	 */
	@XStreamAlias("PicUrl")
	private String picUrl;
	/**
	 * 位置消息
	 */
	@XStreamAlias("LocationX")
	private String locationX;
	@XStreamAlias("LocationY")
	private String locationY;
	@XStreamAlias("Scale")
	private Long scale;
	@XStreamAlias("Label")
	private String label;
	/**
	 * 链接消息
	 */
	@XStreamAlias("Title")
	private String title;
	@XStreamAlias("Description")
	private String description;
	@XStreamAlias("Url")
	private String url;
	/**
	 * 语音信息
	 */
	@XStreamAlias("MediaId")
	private String mediaId;
	@XStreamAlias("Format")
	private String format;
	@XStreamAlias("recognition")
	private String recognition;
	/**
	 * 事件
	 */
	@XStreamAlias("Event")
	private String event;
	@XStreamAlias("EventKey")
	private String eventKey;
	@XStreamAlias("Ticket")
	private String ticket;

}