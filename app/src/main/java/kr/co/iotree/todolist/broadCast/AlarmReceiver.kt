package kr.co.iotree.todolist.broadCast

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.MainActivity
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.util.getToday

class AlarmReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            else -> {
                Log.d(TAG, "Received intent : $intent")
                notificationManager = context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

                createNotificationChannel()
                deliverNotification(context)
            }
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val db = TodoDatabase.getInstance(context)
        val contentText = if (db.todoDao().getCompleteTodo(getToday("yyyyMMd"), false).size > 0) {
            db.todoDao().getCompleteTodo(getToday("yyyyMMd"), false)[0].content
        } else {
            context.resources.getString(R.string.no_todo_notification)
        }

        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_clover)
                .setContentTitle("TodoList")
                .setContentText(contentText)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 0
        const val NOTIFICATION_NAME = "Stand up notification"
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}