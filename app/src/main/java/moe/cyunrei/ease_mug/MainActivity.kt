package moe.cyunrei.ease_mug

import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://stackoverflow.com/questions/31862753/android-how-to-turn-on-do-not-disturb-dnd-programmatically
        if (!(getSystemService(NOTIFICATION_SERVICE) as NotificationManager).isNotificationPolicyAccessGranted) {
            Toast.makeText(this, "Please grant EaseMUG DND permission", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }
        else if (!Utils.isAccessibilityServiceEnabled(this, EaseMUGService::class.java)) {
            Toast.makeText(this, "Please grant EaseMUG accessibility permission", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        setContentView(R.layout.activity_main)
    }

}