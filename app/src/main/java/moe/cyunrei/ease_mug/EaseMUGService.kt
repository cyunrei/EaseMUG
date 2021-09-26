package moe.cyunrei.ease_mug

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.RequiresApi
import moe.cyunrei.ease_mug.Utils.Companion.containsAnyOfIgnoreCase


// https://developer.android.com/guide/topics/ui/accessibility/service#create

class EaseMUGService : AccessibilityService() {

    private val packagesKeywords =
        mutableListOf("low.arc", "bangdream", "cytus") // the MUG game packages keywords
    private var end = false // The app set DND flag

    // https://stackoverflow.com/questions/3873659/android-how-can-i-get-the-current-foreground-activity-from-a-service
    @SuppressLint("SwitchIntDef")
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (event != null) {
            when (event.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    if (mNotificationManager.currentInterruptionFilter == 1 &&
                        event.packageName.containsAnyOfIgnoreCase(
                            packagesKeywords
                        )
                    ) {
                        mNotificationManager.apply {
                            setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)
                            mNotificationManager.notificationPolicy = NotificationManager.Policy(
                                NotificationManager.Policy.PRIORITY_CATEGORY_MEDIA,
                                0, 0
                            ) // Set DND
                        }
                        end = true
                    } else {
                        if (end && event.packageName != "com.android.systemui" && !event.packageName.containsAnyOfIgnoreCase(
                                packagesKeywords
                            )
                        ) // Add the com.android.systemui to fix a problem in MIUI
                        {
                            mNotificationManager.apply {
                                setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL) // Unset DND
                            }
                            end = false
                        }
                    }
                }
            }
        }
    }

    override fun onInterrupt() {}

}