package com.imooc.meet.receiver;


import android.content.Context;

import com.imooc.meet.service.CloudService;
import com.liuguilin.framework.utils.LogUtils;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * FileName: SealNotificationReceiver
 * Founder: LiuGuiLin
 * Profile: 融云离线消息
 */
public class SealNotificationReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        LogUtils.e("onNotificationMessageArrived" + pushNotificationMessage.toString());
        // 返回 false, 会弹出融云 SDK 默认通知;
        // 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
        return true;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        // // 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面;
        // 返回 true, 则由您自定义处理逻辑。
        return true;
    }
}
