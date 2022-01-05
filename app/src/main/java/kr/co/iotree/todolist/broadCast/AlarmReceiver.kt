package kr.co.iotree.todolist.broadCast

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.MainActivity
import kr.co.iotree.todolist.activity.PrefApplication.Companion.pref
import kr.co.iotree.todolist.database.TodoGroupRepository
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.LocaleUtil.Companion.OPTION_PHONE_LANGUAGE
import kr.co.iotree.todolist.util.PrefUtil.Companion.LOCALE_CODE
import kr.co.iotree.todolist.util.getToday

class AlarmReceiver : BroadcastReceiver() {
    lateinit var notificationManager: NotificationManager


    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) { //Broadcast 수신되면 자동으로 호출
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        deliverNotification(context)
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

        val repository = TodoGroupRepository.getInstance(context)
        val list = repository.getAllDayTodo(getToday("yyyyMMdd"))

        if (list.size <= 0) {
            val builder =
                NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_clover)
                    .setContentTitle(context.resources.getString(R.string.app_name))
                    .setContentText(context.resources.getText(R.string.notification_no_todo))
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            notificationManager.notify(NOTIFICATION_ID, builder.build())
        } else {
            val bigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.setBigContentTitle(context.resources.getText(R.string.notification_today))
            var bigText = ""
            for (i in list) {
                bigText += if (i == list.last())
                    " - ${i.content}"
                else
                    " - ${i.content}\n"
            }
            bigTextStyle.bigText(bigText)

            val builder =
                NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_clover)
                    .setContentTitle(context.resources.getText(R.string.notification_today))
                    .setStyle(bigTextStyle)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setAutoCancel(true)

            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                NOTIFICATION_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = NOTIFICATION_DESCRIPTION
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        const val NOTIFICATION_ID = 0
        const val NOTIFICATION_NAME = "Stand up notification"
        const val NOTIFICATION_DESCRIPTION = "AlarmManager Tests"
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }
}