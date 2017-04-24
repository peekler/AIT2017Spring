package hu.ait.aitforum;


import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by peter on 2016. 11. 10..
 */

public class ForumMessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        String payload = "";
        if (remoteMessage.getData().size() > 0) {
            payload += remoteMessage.getData();
        }
        String body = remoteMessage.getNotification().getBody();

        Log.d("FIREBASE_AITForum_PUSH", from+"\n"+payload+"\n"+body);
    }
}