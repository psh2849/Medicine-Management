package com.example.healthcareapp.ui.fragment.addMedicine.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.healthcareapp.R
import com.example.healthcareapp.ui.MainActivity
import com.example.healthcareapp.util.Constants.Companion.CHANNEL_ID
import com.example.healthcareapp.util.Constants.Companion.RECEIVER_CONTENT
import com.example.healthcareapp.util.Constants.Companion.RECEIVER_TITLE

class AddMedicineAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val medicineIntent = Intent(context, MainActivity::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(context, 0, medicineIntent, 0)
        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(RECEIVER_TITLE)
            .setContentText(RECEIVER_CONTENT)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }
}