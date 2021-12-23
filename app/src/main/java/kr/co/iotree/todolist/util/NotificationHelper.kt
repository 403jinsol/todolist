package kr.co.iotree.todolist.util

import android.content.Context
import android.content.ContextWrapper

class NotificationHelper(base: Context?) : ContextWrapper(base) {
//    private var mManager: NotificationManager? = null
//    private fun createChannels() {
//        val channel1 = NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT)
//        channel1.enableLights(true)
//        channel1.enableVibration(true)
//        channel1.lightColor = R.color.colorPrimary
//        channel1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//        manager!!.createNotificationChannel(channel1)
//
//        val channel2 = NotificationChannel(channel2ID, channel2Name, NotificationManager.IMPORTANCE_DEFAULT)
//        channel2.enableLights(true)
//        channel2.enableVibration(true)
//        channel2.lightColor = R.color.colorPrimary
//        channel2.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
//        manager!!.createNotificationChannel(channel2)
//    }
//
//    private val manager: NotificationManager?
//        get() {
//            if (mManager == null) {
//                mManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            }
//            return mManager
//        }
//
//    fun getChannel1Notification(title: String?, message: String?): NotificationCompat.Builder {
//        return NotificationCompat.Builder(applicationContext, channel1ID)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(R.drawable.ic_clover)
//    }
//
//    fun getChannel2Notification(title: String?, message: String?): NotificationCompat.Builder {
//        return NotificationCompat.Builder(applicationContext, channel2ID)
//            .setContentTitle(title)
//            .setContentText(message)
//            .setSmallIcon(R.drawable.ic_clover)
//    }
//
//    val channelNotification: NotificationCompat.Builder
//        get() = NotificationCompat.Builder(applicationContext, channel1ID)
//            .setSmallIcon(R.drawable.ic_clover)
//
//    companion object {
//        const val channel1ID = "channel1ID"
//        const val channel1Name = "channel 1 "
//        const val channel2ID = "channel2ID"
//        const val channel2Name = "channel 2 "
//    }
//
//    init {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createChannels()
//        }
//    }
}