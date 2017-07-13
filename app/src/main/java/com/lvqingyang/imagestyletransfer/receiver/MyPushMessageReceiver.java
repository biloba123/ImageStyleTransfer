package com.lvqingyang.imagestyletransfer.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.lvqingyang.imagestyletransfer.MainActivity;
import com.lvqingyang.imagestyletransfer.R;

import cn.bmob.push.PushConstants;

/**
 * 　　┏┓　　  ┏┓+ +
 * 　┏┛┻━ ━ ━┛┻┓ + +
 * 　┃　　　             ┃
 * 　┃　　　━　　   ┃ ++ + + +
 * ████━████     ┃+
 * 　┃　　　　　　  ┃ +
 * 　┃　　　┻　　  ┃
 * 　┃　　　　　　  ┃ + +
 * 　┗━┓　　　┏━┛
 * 　　　┃　　　┃
 * 　　　┃　　　┃ + + + +
 * 　　　┃　　　┃
 * 　　　┃　　　┃ +  神兽保佑
 * 　　　┃　　　┃    代码无bug！
 * 　　　┃　　　┃　　+
 * 　　　┃　 　　┗━━━┓ + +
 * 　　　┃ 　　　　　　　┣┓
 * 　　　┃ 　　　　　　　┏┛
 * 　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　┃┫┫　┃┫┫
 * 　　　　┗┻┛　┗┻┛+ + + +
 * ━━━━━━神兽出没━━━━━━
 * Author：LvQingYang
 * Date：2017/7/13
 * Email：biloba12345@gamil.com
 * Info：
 */

public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            NotificationManager notificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent pi = PendingIntent.getActivity(context, 0,
                        new Intent(context, MainActivity.class), 0);
                android.support.v4.app.NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setTicker("有消息提醒")
                        .setContentTitle("标题")
                        .setContentText(intent.getStringExtra("msg"))
                        .setWhen(System.currentTimeMillis())
                        .setVibrate(new long[]{0,1000})
                        .setContentIntent(pi)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                        .setSmallIcon(R.drawable.ic_launcher);
                Notification notification = builder.build();
                notificationManager.notify(0, notification);
        }
    }

}
