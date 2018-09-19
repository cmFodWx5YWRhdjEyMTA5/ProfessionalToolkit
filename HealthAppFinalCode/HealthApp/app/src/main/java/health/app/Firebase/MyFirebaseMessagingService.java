package health.app.Firebase;

/**
 * Created by Developer Six on 8/7/2017.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;

import health.app.Activities.CustomerDashboardActivity;
import health.app.Activities.TrainerDashboardActivity;
import health.app.R;
import health.app.Utilities.Preferences;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String type,msg,sessionSlotId;
    long unique_id;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getData());
        Date now = new Date();
        unique_id = now.getTime();
        type=remoteMessage.getData().get("type");
        msg=remoteMessage.getData().get("message");
        sessionSlotId=remoteMessage.getData().get("SessionSlotId");
        Log.d("type","type"+type+msg);
            sendNotification(type,msg);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(String tag, String messageBody) {
        Intent intent = null;
        if (tag.equalsIgnoreCase("send_message")) {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","send_message");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else {
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","send_message");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("send_request"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","send_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else {
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","send_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("accept_request"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","accept_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else {
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","accept_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("reject_request"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","reject_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else {
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","reject_request");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("session_complete"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","session_complete");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else if (Preferences.getInstance().getUserType().equals("Customer")){
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","session_complete");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("session_slot_cancel"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","session_slot_cancel");
                intent.putExtra("SessionSlotId",sessionSlotId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else if (Preferences.getInstance().getUserType().equals("Customer")){
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","session_slot_cancel");
                intent.putExtra("SessionSlotId",sessionSlotId);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        else if (tag.equalsIgnoreCase("add_session_slot_after_cancel"))
        {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.putExtra("notificationTrainer","add_session_slot_after_cancel");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else if (Preferences.getInstance().getUserType().equals("Customer")){
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.putExtra("notificationCustomer","add_session_slot_after_cancel");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }


            else {
            if (Preferences.getInstance().getUserType().equals("Trainer")) {
                intent = new Intent(MyFirebaseMessagingService.this, TrainerDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            else if (Preferences.getInstance().getUserType().equals("Customer")){
                intent = new Intent(MyFirebaseMessagingService.this, CustomerDashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ptlogo)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ptlogo))
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify((int) unique_id, notificationBuilder.build());
    }
}}