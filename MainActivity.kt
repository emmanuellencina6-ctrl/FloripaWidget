package com.floripa.widget

import android.app.Activity
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.info).text =
            "FLORIPA 🇧🇷\\n23 de enero de 2027\\n\\nAgregá el widget Floripa a tu pantalla de inicio.\\n\\nEn Android, mantené presionada la pantalla de inicio → Widgets → Floripa Cuenta Regresiva.\\n\\nPara favorecer la actualización cada minuto, permití las alarmas exactas si Android te lo solicita."

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarm = getSystemService(AlarmManager::class.java)
            if (!alarm.canScheduleExactAlarms()) {
                try {
                    startActivity(Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                        Uri.parse("package:$packageName")))
                } catch (_: Exception) { }
            }
        }
        FloripaWidgetProvider.updateAll(this)
    }
}
