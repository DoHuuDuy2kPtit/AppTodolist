package com.example.todolist.Notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.todolist.R;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    final String CHANNEL_ID="201";

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("time");
        String content = intent.getStringExtra("title");
        if(intent.getAction().equals("Myaction")){
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel1", NotificationManager.IMPORTANCE_HIGH );
                channel.setDescription("Kênh 1");
                notificationManager.createNotificationChannel(channel);
            }
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentTitle("Alarm " + time)
                    .setContentText(content)
                    .setSmallIcon(R.drawable.ic_notifications_active)
                    .setColor(Color.RED)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);
            notificationManager.notify(getNotificationid(), builder.build());

        }
    }
    private int getNotificationid(){
        int time = (int) new Date().getTime();
        return time;
    }
}
