package com.liuguilin.framework.cloud;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.liuguilin.framework.R;
import com.liuguilin.framework.event.EventManager;
import com.liuguilin.framework.event.MessageEvent;
import com.liuguilin.framework.utils.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import io.rong.calllib.IRongCallListener;
import io.rong.calllib.IRongReceivedCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.LocationMessage;
import io.rong.message.TextMessage;

/**
 * FileName: CloudManager
 * Founder: LiuGuiLin
 * Profile: 融云管理
 */
public class CloudManager {

    //Url
    public static final String TOKEN_URL = "http://api-cn.ronghub.com/user/getToken.json";
    //Key
    public static final String CLOUD_KEY = "k51hidwqk4yeb";
    public static final String CLOUD_SECRET = "os83U32SrAG";

    //ObjectName
    public static final String MSG_TEXT_NAME = "RC:TxtMsg";
    public static final String MSG_IMAGE_NAME = "RC:ImgMsg";
    public static final String MSG_LOCATION_NAME = "RC:LBSMsg";

    //Msg Type

    //普通消息
    public static final String TYPE_TEXT = "TYPE_TEXT";
    //添加好友消息
    public static final String TYPE_ADD_FRIEND = "TYPE_ADD_FRIEND";
    //同意添加好友的消息
    public static final String TYPE_ARGEED_FRIEND = "TYPE_ARGEED_FRIEND";

    //来电铃声
    public static final String callAudioPath = "http://downsc.chinaz.net/Files/DownLoad/sound1/201501/5363.wav";
    //挂断铃声
    public static final String callAudioHangup = "http://downsc.chinaz.net/Files/DownLoad/sound1/201501/5351.wav";

    private static volatile CloudManager mInstnce = null;

    private CloudManager() {

    }

    public static CloudManager getInstance() {
        if (mInstnce == null) {
            synchronized (CloudManager.class) {
                if (mInstnce == null) {
                    mInstnce = new CloudManager();
                }
            }
        }
        return mInstnce;
    }

    /**
     * 初始化SDK
     *
     * @param mContext
     */
    public void initCloud(Context mContext) {
        RongIMClient.init(mContext);
    }

    /**
     * 连接融云服务
     *
     * @param token
     */
    public void connect(String token) {
        LogUtils.i("connect");
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                LogUtils.e("Token Error");
            }

            @Override
            public void onSuccess(String s) {
                LogUtils.e("连接成功：" + s);
                sendConnectStatus(true);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtils.e("连接失败：" + errorCode);
                sendConnectStatus(false);
            }
        });
    }

    /**
     * 监听连接状态
     *
     * @param listener
     */
    public void setConnectionStatusListener(RongIMClient.ConnectionStatusListener listener) {
        RongIMClient.setConnectionStatusListener(listener);
    }

    /**
     * 发送服务器连接状态
     *
     * @param isConnect
     */
    private void sendConnectStatus(boolean isConnect) {
        MessageEvent messageEvent = new MessageEvent(EventManager.EVENT_SERVER_CONNECT_STATUS);
        messageEvent.setConnectStatus(isConnect);
        EventManager.post(messageEvent);
    }

    /**
     * 是否连接
     *
     * @return
     */
    public boolean isConnect() {
        return RongIMClient.getInstance().getCurrentConnectionStatus()
                == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED;
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        RongIMClient.getInstance().disconnect();
    }

    /**
     * 退出登录
     */
    public void logout() {
        RongIMClient.getInstance().logout();
    }

    /**
     * 接收消息的监听器
     *
     * @param listener
     */
    public void setOnReceiveMessageListener(RongIMClient.OnReceiveMessageListener listener) {
        RongIMClient.setOnReceiveMessageListener(listener);
    }

    /**
     * 发送消息的结果回调
     */
    private IRongCallback.ISendMessageCallback iSendMessageCallback
            = new IRongCallback.ISendMessageCallback() {

        @Override
        public void onAttached(Message message) {
            // 消息成功存到本地数据库的回调
        }

        @Override
        public void onSuccess(Message message) {
            // 消息发送成功的回调
            LogUtils.i("sendMessage onSuccess");
        }

        @Override
        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            // 消息发送失败的回调
            LogUtils.e("sendMessage onError:" + errorCode);
        }
    };

    /**
     * 发送文本消息
     * 一个手机 发送
     * 另外一个手机 接收
     *
     * @param msg
     * @param targetId
     */
    private void sendTextMessage(String msg, String targetId) {
        LogUtils.i("sendTextMessage");
        TextMessage textMessage = TextMessage.obtain(msg);
        RongIMClient.getInstance().sendMessage(
                Conversation.ConversationType.PRIVATE,
                targetId,
                textMessage,
                null,
                null,
                iSendMessageCallback
        );
    }

    /**
     * 发送文本消息
     *
     * @param msg
     * @param type
     * @param targetId
     */
    public void sendTextMessage(String msg, String type, String targetId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", msg);
            //如果没有这个Type 就是一条普通消息
            jsonObject.put("type", type);
            sendTextMessage(jsonObject.toString(), targetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RongIMClient.SendImageMessageCallback sendImageMessageCallback = new RongIMClient.SendImageMessageCallback() {
        @Override
        public void onAttached(Message message) {
            LogUtils.i("onAttached");
        }

        @Override
        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            LogUtils.i("onError:" + errorCode);
        }

        @Override
        public void onSuccess(Message message) {
            LogUtils.i("onSuccess");
        }

        @Override
        public void onProgress(Message message, int i) {
            LogUtils.i("onProgress:" + i);
        }
    };

    /**
     * 发送图片消息
     *
     * @param targetId 对方ID
     * @param file     文件
     */
    public void sendImageMessage(String targetId, File file) {
        ImageMessage imageMessage = ImageMessage.obtain(Uri.fromFile(file), Uri.fromFile(file), true);
        RongIMClient.getInstance().sendImageMessage(
                Conversation.ConversationType.PRIVATE,
                targetId,
                imageMessage,
                null,
                null,
                sendImageMessageCallback);
    }

    /**
     * 发送位置信息
     *
     * @param mTargetId
     * @param lat
     * @param lng
     * @param poi
     */
    public void sendLocationMessage(String mTargetId, double lat, double lng, String poi) {
        LocationMessage locationMessage = LocationMessage.obtain(lat, lng, poi, null);
        io.rong.imlib.model.Message message = io.rong.imlib.model.Message.obtain(
                mTargetId, Conversation.ConversationType.PRIVATE, locationMessage);
        RongIMClient.getInstance().sendLocationMessage(message,
                null, null, iSendMessageCallback);
    }

    /**
     * 查询本地的会话记录
     *
     * @param callback
     */
    public void getConversationList(RongIMClient.ResultCallback<List<Conversation>> callback) {
        RongIMClient.getInstance().getConversationList(callback);
    }

    /**
     * 加载本地的历史记录
     *
     * @param targetId
     * @param callback
     */
    public void getHistoryMessages(String targetId, RongIMClient.ResultCallback<List<Message>> callback) {
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE
                , targetId, -1, 1000, callback);
    }

    /**
     * 获取服务器的历史记录
     *
     * @param targetId
     * @param callback
     */
    public void getRemoteHistoryMessages(String targetId, RongIMClient.ResultCallback<List<Message>> callback) {
        RongIMClient.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.PRIVATE
                , targetId, 0, 20, callback);
    }

    //-------------------------Call Api-------------------------------

    /**
     * 拨打视频/音频
     *
     * @param targetId
     * @param type
     */
    public void startCall(Context mContext, String targetId, RongCallCommon.CallMediaType type) {
        //检查设备可用
        if (!isVoIPEnabled(mContext)) {
            return;
        }
        if(!isConnect()){
            Toast.makeText(mContext, mContext.getString(R.string.text_server_status), Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> userIds = new ArrayList<>();
        userIds.add(targetId);
        RongCallClient.getInstance().startCall(
                Conversation.ConversationType.PRIVATE,
                targetId,
                userIds,
                null,
                type,
                null);
    }

    /**
     * 音频
     *
     * @param targetId
     */
    public void startAudioCall(Context mContext, String targetId) {
        startCall(mContext, targetId, RongCallCommon.CallMediaType.AUDIO);
    }

    /**
     * 视频
     *
     * @param targetId
     */
    public void startVideoCall(Context mContext, String targetId) {
        startCall(mContext, targetId, RongCallCommon.CallMediaType.VIDEO);
    }

    /**
     * 监听音频通话
     *
     * @param listener
     */
    public void setReceivedCallListener(IRongReceivedCallListener listener) {
        if (null == listener) {
            return;
        }
        RongCallClient.setReceivedCallListener(listener);
    }

    /**
     * 接听
     *
     * @param callId
     */
    public void acceptCall(String callId) {
        LogUtils.i("acceptCall:" + callId);
        RongCallClient.getInstance().acceptCall(callId);
    }

    /**
     * 挂断
     *
     * @param callId
     */
    public void hangUpCall(String callId) {
        LogUtils.i("hangUpCall:" + callId);
        RongCallClient.getInstance().hangUpCall(callId);
    }

    /**
     * 切换媒体
     *
     * @param mediaType
     */
    public void changeCallMediaType(RongCallCommon.CallMediaType mediaType) {
        RongCallClient.getInstance().changeCallMediaType(mediaType);
    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {
        RongCallClient.getInstance().switchCamera();
    }

    /**
     * 摄像头开关
     *
     * @param enabled
     */
    public void setEnableLocalVideo(boolean enabled) {
        RongCallClient.getInstance().setEnableLocalVideo(enabled);
    }

    /**
     * 音频开关
     *
     * @param enabled
     */
    public void setEnableLocalAudio(boolean enabled) {
        RongCallClient.getInstance().setEnableLocalAudio(enabled);
    }

    /**
     * 免提开关
     *
     * @param enabled
     */
    public void setEnableSpeakerphone(boolean enabled) {
        RongCallClient.getInstance().setEnableSpeakerphone(enabled);
    }

    /**
     * 开启录音
     *
     * @param filePath
     */
    public void startAudioRecording(String filePath) {
        RongCallClient.getInstance().startAudioRecording(filePath);
    }

    /**
     * 关闭录音
     */
    public void stopAudioRecording() {
        RongCallClient.getInstance().stopAudioRecording();
    }

    /**
     * 监听通话状态
     *
     * @param listener
     */
    public void setVoIPCallListener(IRongCallListener listener) {
        if (null == listener) {
            return;
        }
        RongCallClient.getInstance().setVoIPCallListener(listener);
    }

    /**
     * 检查设备是否可用通话
     *
     * @param mContext
     */
    public boolean isVoIPEnabled(Context mContext) {
        if (!RongCallClient.getInstance().isVoIPEnabled(mContext)) {
            Toast.makeText(mContext, mContext.getString(R.string.text_devices_not_supper_audio), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

