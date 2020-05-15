package com.example.myservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class SoundService extends Service {

    MediaPlayer mediaPlayer;
    long startTime;

    public SoundService() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.icantholdus);
        mediaPlayer.setLooping(false); // music was only once.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // start music
        long currentTime = System.currentTimeMillis();
        while (true) {
            if (currentTime - startTime > 8000) {
                mediaPlayer.start();
                break;
            }
            currentTime = System.currentTimeMillis();
        }

        // create Intent and PendingIntent for Notification
        Intent soundIntent = new Intent(this, MainActivity.class);
        PendingIntent pNotificationIntent = PendingIntent.getActivity(this, 0, soundIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // create object
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "MyMusic")
                .setSmallIcon(R.drawable.pictures).setColor(245)
                .setContentTitle("Музыка началась!").setContentText("Приятного прослушивания!")
                .setContentIntent(pNotificationIntent);

        Notification notification = nBuilder.build();
        manager.notify(1, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }
}
