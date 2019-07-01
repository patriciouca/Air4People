package es.uca.air4people.air4people.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.uca.air4people.air4people.BD.AndroidBaseDatos;
import es.uca.air4people.air4people.BD.EstacionBD;
import es.uca.air4people.air4people.memoria.MemoriaAplicacion;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG="hola";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                Log.d(TAG, "Message Notification title: " + remoteMessage.getNotification().getTitle());
                AndroidBaseDatos baseDatos=((MemoriaAplicacion) getApplicationContext()).getBase();
                baseDatos.addNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            } else {
                // Handle message within 10 seconds
                Log.d(TAG, "Message Notification title: " + remoteMessage.getNotification().getTitle());
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                AndroidBaseDatos baseDatos=((MemoriaAplicacion) getApplicationContext()).getBase();
                baseDatos.addNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification title: " + remoteMessage.getNotification().getTitle());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            AndroidBaseDatos baseDatos=((MemoriaAplicacion) getApplicationContext()).getBase();
            baseDatos.addNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }
    }
}
