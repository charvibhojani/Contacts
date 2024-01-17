package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class PushNotification extends FirebaseMessagingService {

//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
//        getFirebaseMessage(message.getNotification().getTitle(), message.getNotification().getBody());
//    }
//
//    public void getFirebaseMessage(String title, String msg) {
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "notification").setContentTitle(title).setContentText(msg).setAutoCancel(true);
//
//        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
//        manager.notify(101, builder.build());
//    }

}
