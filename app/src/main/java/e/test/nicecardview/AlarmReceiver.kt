package e.test.nicecardview

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.app.NotificationCompat
import android.util.Log
import java.util.*

/**
 * Created by Noe on 20/3/2018.
 */
class AlarmReceiver : BroadcastReceiver(){

    override fun onReceive(context: Context, intent: Intent?) {
       // val now = Calendar.getInstance()
        val title = intent?.getStringExtra("title")
        val text = intent?.getStringExtra("text")
        Log.i("AlarmReceiver", "Get title = " +title)
        val mBuilder = NotificationCompat.Builder(context)
                .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                //change icon
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(text)

        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK / Intent.FLAG_ACTIVITY_CLEAR_TASK)

        val resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0)
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setAutoCancel(true)
        //add action
        val mNotificationManager = NotificationManagerCompat.from(context)
        mNotificationManager.notify(1, mBuilder.build());
    }
    }