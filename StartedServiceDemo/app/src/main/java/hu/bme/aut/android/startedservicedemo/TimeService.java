package hu.bme.aut.android.startedservicedemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

public class TimeService extends Service {

    private final int NOTIF_FOREGROUND_ID = 101;
    private boolean enabled = false;
    private MyTimeThread myTimeThread = null;
    private PlayReceiver playReceiver = new PlayReceiver();

    private static final String PLAY_INTENT =
        "hu.bme.aut.android.startedservicedemo.play";


    private class MyTimeThread extends Thread {
        public void run() {
            Handler h = new Handler(TimeService.this.getMainLooper());

            while (enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TimeService.this,
                                new Date(
                                        System.currentTimeMillis()).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                updateNotification(
                        new Date(System.currentTimeMillis()).toString());

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter(PLAY_INTENT);
        registerReceiver(playReceiver, filter);


        //startForeground(NOTIF_FOREGROUND_ID,
        //        getMyNotification("starting..."));

        enabled = true;
        if (myTimeThread == null) {
            myTimeThread = new MyTimeThread();
            myTimeThread.start();
        }
        return START_STICKY;
    }

    private void updateNotification(String text) {
        Notification notification = getMyNotification(text);
        NotificationManager notifMan =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifMan.notify(NOTIF_FOREGROUND_ID,notification);
    }

    private Notification getMyNotification(String text) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                NOTIF_FOREGROUND_ID,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Intent intent = new Intent(PLAY_INTENT);
        PendingIntent playIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("This the MyTimeService")
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{1000,2000,1000})
                .addAction(R.mipmap.ic_launcher,"Play",playIntent)
                .setContentIntent(contentIntent).build();
        return  notification;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        enabled = false;

        unregisterReceiver(playReceiver);

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class PlayReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }
    }
}
