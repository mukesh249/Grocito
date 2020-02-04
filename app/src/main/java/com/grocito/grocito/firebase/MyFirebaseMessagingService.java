package com.grocito.grocito.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.grocito.grocito.R;
import com.grocito.grocito.activities.HomeScreen;
import com.grocito.grocito.activities.Login;
import com.grocito.grocito.common.Constrants;
import com.grocito.grocito.common.SharedPrefManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String CHANNEL_NAME = "Grocito";
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final String TAG = "tag";

    private int numMessages = 0;
//    private FCMData fcmData;
    int count = 0;

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN", s);
        SharedPrefManager.setDeviceToken(Constrants.Token,s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Map<String, String> data = remoteMessage.getData();
            Log.d("FROM", remoteMessage.getFrom());

            sendNotification(notification, data);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {

        String title = data.get("title");
        String message = data.get("description");
        String image = data.get("image");

        Intent intent = null;
        if (SharedPrefManager.getInstance(this).isLogin(Constrants.IsLogin)) {
            intent = new Intent(this, HomeScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else {
            intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "Grocito");
        if (!image.equalsIgnoreCase("0")&&getBitmapFromURL(image)!=null){
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(getBitmapFromURL(image))
                    .setSummaryText(Html.fromHtml(message).toString()));
        }else {
              notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        }

        notificationBuilder.setContentTitle(title)
//                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "Grocito", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            Uri notificationSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            if (notificationSoundUri != null) {
                // Changing Default mode of notification_green
                notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);

                // Creating an Audio Attribute
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build();

                channel.setSound(notificationSoundUri, audioAttributes);
            }

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(1, notificationBuilder.build());


    }
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
