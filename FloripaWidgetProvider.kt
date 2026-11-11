package com.floripa.widget

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

class FloripaWidgetProvider : AppWidgetProvider() {
    companion object {
        private const val ACTION_UPDATE = "com.floripa.widget.UPDATE"
        private val ZONE = ZoneId.of("America/Argentina/Buenos_Aires")
        private val TARGET = ZonedDateTime.of(2027, 1, 23, 0, 0, 0, 0, ZONE).toInstant()

        fun updateAll(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, FloripaWidgetProvider::class.java))
            val provider = FloripaWidgetProvider()
            provider.onUpdate(context, manager, ids)
        }
    }

    override fun onUpdate(context: Context, manager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { id -> manager.updateAppWidget(id, buildViews(context)) }
        scheduleNext(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_UPDATE) updateAll(context)
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) updateAll(context)
    }

    private fun buildViews(context: Context): RemoteViews {
        val now = Instant.now()
        var seconds = Duration.between(now, TARGET).seconds.coerceAtLeast(0)
        val days = seconds / 86400; seconds %= 86400
        val hours = seconds / 3600; seconds %= 3600
        val minutes = seconds / 60
        val secs = seconds % 60
        val views = RemoteViews(context.packageName, R.layout.widget_floripa)
        views.setTextViewText(R.id.days, String.format(Locale.US, "%03d", days))
        views.setTextViewText(R.id.hours, String.format(Locale.US, "%02d", hours))
        views.setTextViewText(R.id.minutes, String.format(Locale.US, "%02d", minutes))
        views.setTextViewText(R.id.seconds, String.format(Locale.US, "%02d", secs))
        views.setOnClickPendingIntent(R.id.widget_root,
            PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
        return views
    }

    private fun scheduleNext(context: Context) {
        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, FloripaWidgetProvider::class.java).setAction(ACTION_UPDATE)
        val pending = PendingIntent.getBroadcast(context, 77, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val next = System.currentTimeMillis() + 60_000L
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && alarm.canScheduleExactAlarms()) {
                alarm.setExactAndAllowWhileIdle(AlarmManager.RTC, next, pending)
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                alarm.setExactAndAllowWhileIdle(AlarmManager.RTC, next, pending)
            } else {
                alarm.setAndAllowWhileIdle(AlarmManager.RTC, next, pending)
            }
        } catch (_: SecurityException) {
            alarm.setAndAllowWhileIdle(AlarmManager.RTC, next, pending)
        }
    }
}
