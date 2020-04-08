package com.deku.domain;
 
import com.deku.repository.XStreamCDATA;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * 
 * @author morning
 * @date 2015年2月16日 下午2:29:32
 */
@Data
@XStreamAlias("xml")
public class OutputMessage {
 
	@XStreamAlias("ToUserName")
	@XStreamCDATA
	private String ToUserName;
 
	@XStreamAlias("FromUserName")
	@XStreamCDATA
	private String FromUserName;
 
	@XStreamAlias("CreateTime")
	private Long CreateTime;
 
	@XStreamAlias("MsgType")
	@XStreamCDATA
	private String MsgType = "text";
 
	private ImageMessage Image;
 

}