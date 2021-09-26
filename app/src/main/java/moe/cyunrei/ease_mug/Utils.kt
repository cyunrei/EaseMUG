package moe.cyunrei.ease_mug

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.app.NotificationManager
import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import android.view.accessibility.AccessibilityManager
import androidx.annotation.RequiresApi

class Utils {
    companion object {
        //https://stackoverflow.com/questions/5081145/android-how-do-you-check-if-a-particular-accessibilityservice-is-enabled
        fun isAccessibilityServiceEnabled(
            context: Context,
            service: Class<out AccessibilityService?>
        ): Boolean {
            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            val enabledServices =
                am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
            for (enabledService in enabledServices) {
                val enabledServiceInfo: ServiceInfo = enabledService.resolveInfo.serviceInfo
                if (enabledServiceInfo.packageName.equals(context.getPackageName()) && enabledServiceInfo.name.equals(
                        service.name
                    )
                ) return true
            }
            return false
        }
        //https://stackoverflow.com/questions/50788839/test-if-string-contains-anything-from-an-array-of-strings-kotlin
        fun CharSequence.containsAnyOfIgnoreCase(keywords: List<String>): Boolean {
            for (keyword in keywords) {
                if (this.contains(keyword, true)) return true
            }
            return false
        }
    }
}