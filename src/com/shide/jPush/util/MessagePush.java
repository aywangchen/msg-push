package com.shide.jPush.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.Notification.Builder;
import com.google.gson.JsonObject;
import com.shide.jPush.bean.ExtraBean;
import com.shide.jPush.bean.MsgDTO;
import com.shide.util.DataUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 极光推送
 * 
 * @author guoxin
 * 
 */
public class MessagePush {

	static final String dd_appKey = "05cad2bc17722decf46eae2d";
	static final String dd_masterSecret = "60c2fe74971ad1fa6c433fd9";
	
	private JPushClient jpushClient;
	private static String title;
//	private String content;
//	private String message;
	private static String ALERT = "Test from API Example - alert";
	private static String MSG_CONTENT = "Test from API Example - msgContent";

	/**
	 * 初始化
	 * @param type 1:点点端; 
	 */
	public MessagePush(int type) {
		// masterSecret：注册应用的主密码,即API 主密码
	    // appKey:注册应用的应用Key
	    // maxRetryTime:最大的尝试次数，设为3表示：跟服务器进行建立连接若失败会尝试再进行两次尝试
		if(type == 1){//点点端
			jpushClient = new JPushClient(dd_masterSecret, dd_appKey, 3);			
		}
	}

//	public MessagePush(String message, String title) {
//		this(message);
//		this.title = title;
//	}

	/**
	 * 向所有人发送通知
	 * @param message 发送的通知
	 * @return
	 */
	public String sendPushAll(String message, List<ExtraBean> list) {

		PushPayload payload = buildPushObject_all_all_alert(message, list);
		// long msgId = 0;

		try {

			PushResult result = jpushClient.sendPush(payload);
//			System.out.println(result.isResultOK() + "................................");
//			System.out.println(result.getResponseCode()
//					+ "................................");

			if(result.isResultOK()){
				return "true";
			}else{
				return "发送消息失败";				
			}
			
			// msgId = result.msg_id;
		} catch (APIConnectionException e) {
			e.printStackTrace();
			return "连接超时";
		} catch (APIRequestException e) {
			e.printStackTrace();
			// msgId = e.getMsgId();
			return e.getErrorMessage();
		}

	}
	
	/**
	 * 向所有平台的别名用户发送通知
	 * @param alias 用户别名
	 * @param message 发送的通知
	 * @param list 自定义参数集合
	 * @return
	 */
	public String sendPushAlias(String alias, String message, List<ExtraBean> list) {
		
		PushPayload payload = buildPushObject_all_alias_alert(alias, message, list);

		try {

			PushResult result = jpushClient.sendPush(payload);
			//System.out.println(result.isResultOK() + "................................");
			//System.out.println(result.getResponseCode() + "................................");

			if(result.isResultOK()){
				return "true";
			}else{
				return "发送消息失败";				
			}
			
			// msgId = result.msg_id;
		} catch (APIConnectionException e) {
			///e.printStackTrace();
			return "连接超时";
		} catch (APIRequestException e) {
			///e.printStackTrace();
			// msgId = e.getMsgId();
			return e.getErrorMessage();
		}

	}
	
	/**
	 * 向所有平台的所有用户发送通知
	 * @param message 通知内容
	 * @param list 要传递的参数
	 * @return
	 */
	private PushPayload buildPushObject_all_all_alert(String message, List<ExtraBean> list) {
		return buildPushObject_all_alias_alert(null, message, list);
	}
	
    /**
     * 
     * 构建推送对象：所有平台
	 * @param alias 推送目标的别名
	 * @param message 通知内容
     * @param list 要传递的参数
     * @return
     */
    private PushPayload buildPushObject_all_alias_alert(String alias, String message, List<ExtraBean> list) {
    	Builder builder = Notification.newBuilder();
    	PushPayload ppl = null;
    	JsonObject jObject = new JsonObject();

        for(ExtraBean bean : list){//增加自定义参数
        	jObject.addProperty(bean.getKey(), bean.getValue());
        }

    	builder.addPlatformNotification(AndroidNotification.newBuilder()
    			.addExtra("jpush",jObject).build())
    			.addPlatformNotification(IosNotification.newBuilder()
				///.incrBadge(1)
				.addExtra("jpush",jObject).build());

    	if(alias == null){//所有人接收
    		
    		ppl = PushPayload.newBuilder()
					 .setPlatform(Platform.all())//所有平台
					 .setAudience(Audience.all())//所有人
					 .setNotification( builder.setAlert(message).build())
					 .build();
    		
    	}else{//指定人接收
    		
    		ppl = PushPayload.newBuilder()
							 .setPlatform(Platform.all())//所有平台
							 .setAudience(Audience.alias(alias))//向选定的人推送//18610270758别名:565fd830bfff83aa84c2be4
							 .setNotification( builder.setAlert(message).build())
							 .build();
    		
    	}
    	
    	///System.out.println("============"+ppl.toString());
    	return ppl;
    }


	public void sendMsgPush(MsgDTO msg){
		sendMsgPush(msg.getTitle(),msg.getMsgImgUrl(),msg.getSendUserName(),msg.getSendType(),msg.getMsgType(),msg.getUserid(),msg.getMsgContent());
	}

	/**
	 * 发送推送消息
	 * @param title 标题
	 * @param msgImgUrl 发送人头像地址
	 * @param sendUserName 发送人姓名
	 * @param sendType 发送类型
	 * @param msgType 消息码
	 * @param userid 接收人
	 * @param msgContent 消息内容
	 */
	public void sendMsgPush(String title, String msgImgUrl, String sendUserName, String sendType, String msgType, String userid, String msgContent){
		
		try{
			
			//向别名用户发送通知
			List<ExtraBean> list = new ArrayList();
			ExtraBean bean = new ExtraBean();
			bean.setKey("title");//消息标题
			bean.setValue(title);//标题
			list.add(bean);

			bean = new ExtraBean();
			bean.setKey("msgImgUrl");//消息头像地址
			bean.setValue(msgImgUrl);//消息头像地址
			list.add(bean);
			
			bean = new ExtraBean();
			bean.setKey("sendUserName");//发送人姓名
			bean.setValue(sendUserName);//发送人姓名
			list.add(bean);

			bean = new ExtraBean();
			bean.setKey("sendType");//消息类型
			bean.setValue(sendType);//系统消息
			list.add(bean);
			
			bean = new ExtraBean();
			bean.setKey("msgType");//消息码
			bean.setValue(msgType);
			list.add(bean);
			
			bean = new ExtraBean();
			bean.setKey("createTime");//消息创建时间
			bean.setValue(DataUtils.date2Str(new Date(),DataUtils.datetimeFormat));
			list.add(bean);
			
			
			this.sendPushAlias(userid, msgContent, list);	
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 向指定别名的客户端发送消息
	 * 
	 * @param alias
	 *            所有别名信息集合，这里表示发送所有学生编号
	 * @return 消息id
	 */
//	public long sendPushAlias(Set<String> alias) {
//		PushPayload payloadAlias = buildPushObject_android_alias_alertWithTitle(alias);
//		long msgId = 0;
//		try {
//			PushResult result = jpushClient.sendPush(payloadAlias);
//			msgId = result.msg_id;
//
//		} catch (APIConnectionException e) {
//			LOG.error("Connection error. Should retry later. ", e);
//		} catch (APIRequestException e) {
//			LOG.info("HTTP Status: " + e.getStatus());
//			LOG.info("Error Code: " + e.getErrorCode());
//			LOG.info("Error Message: " + e.getErrorMessage());
//			LOG.info("Msg ID: " + e.getMsgId());
//			msgId = e.getMsgId();
//		}
//		return msgId;
//	}

	/**
	 * 向指定组发送消息
	 * 
	 * @param tag
	 *            组名称
	 * @return 消息id
	 */
//	public long sendPushTag(String tag) {
//		PushPayload payloadtag = buildPushObject_android_tag_alertWithTitle(tag);
//		long msgId = 0;
//		try {
//			PushResult result = jpushClient.sendPush(payloadtag);
//			msgId = result.msg_id;
//			LOG.info("Got result - " + result);
//		} catch (APIConnectionException e) {
//			LOG.error("Connection error. Should retry later. ", e);
//
//		} catch (APIRequestException e) {
//			LOG.info("HTTP Status: " + e.getStatus());
//			LOG.info("Error Code: " + e.getErrorCode());
//			LOG.info("Error Message: " + e.getErrorMessage());
//			LOG.info("Msg ID: " + e.getMsgId());
//			msgId = e.getMsgId();
//		}
//		return msgId;
//	}

	/**
	 * 下列封装了三种获得消息推送对象（PushPayload）的方法
	 * buildPushObject_android_alias_alertWithTitle、
	 * buildPushObject_android_tag_alertWithTitle、 buildPushObject_all_all_alert
	 */
//	public PushPayload buildPushObject_android_alias_alertWithTitle(
//			Set<String> alias) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.alias(alias))
//				.setNotification(Notification.android(message, title, null))
//				.build();
//	}
//
//	public PushPayload buildPushObject_android_tag_alertWithTitle(String tag) {
//		return PushPayload.newBuilder().setPlatform(Platform.android())
//				.setAudience(Audience.tag(tag))
//				.setNotification(Notification.android(message, title, null))
//				.build();
//	}

	/**
	 * 向所有平台的所有用户发送通知
	 * @param message 通知内容
	 * @return
	 */
//	private PushPayload buildPushObject_all_all_alert(String message) {
//		return PushPayload.alertAll(message);
//	}
	

	/**
	 * 构建推送对象：所有平台
	 * @param alias 推送目标的别名
	 * @param message 通知内容
	 * @return
	 */
//    public static PushPayload buildPushObject_all_alias_alert(String alias, String message) {
//    	return PushPayload.newBuilder()
//                .setPlatform(Platform.all())//所有平台
//                .setAudience(Audience.alias(alias))//向选定的人推送//18610270758别名:565fd830bfff83aa84c2be4
//                .setNotification(Notification.alert(message))//消息内容
//                .build();
//    }
//    
//    /**
//     * 构建推送对象：平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 Android 通知 ALERT，并且标题为 TITLE。
//     * @return
//     */
//    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android())
//                .setAudience(Audience.tag("tag1"))
//                .setNotification(Notification.android(ALERT, title, null))
//                .build();
//    }
//
//    /**
//     * 构建推送对象：平台是 iOS，推送目标是 "tag1", "tag_all" 的并集，推送内容同时包括通知与消息 - 通知信息是 ALERT，角标数字为 5，通知声音为 "happy"，并且附加字段 from = "JPush"；消息内容是 MSG_CONTENT。通知是 APNs 推送通道的，消息是 JPush 应用内消息通道的。APNs 的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
//     * @return
//     */
//    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.ios())
//                .setAudience(Audience.tag_and("tag1", "tag_all"))
//                .setNotification(Notification.newBuilder()
//                        .addPlatformNotification(IosNotification.newBuilder()
//                                .setAlert(ALERT)
//                                .setBadge(5)
//                                .setSound("happy")
//                                .addExtra("from", "JPush")
//                                .build())
//                        .build())
//                 .setMessage(Message.content(MSG_CONTENT))
//                 .setOptions(Options.newBuilder()
//                         .setApnsProduction(true)
//                         .build())
//                 .build();
//    }
//
//    /**
//     * 构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的交集）并（"alias1" 与 "alias2" 的交集），推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush。
//     * @return
//     */
//    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.android_ios())
//                .setAudience(Audience.newBuilder()
//                        .addAudienceTarget(AudienceTarget.tag("tag1", "tag2"))
//                        .addAudienceTarget(AudienceTarget.alias("alias1", "alias2"))
//                        .build())
//                .setMessage(Message.newBuilder()
//                        .setMsgContent(MSG_CONTENT)
//                        .addExtra("from", "JPush")
//                        .build())
//                .build();
//    }
}
