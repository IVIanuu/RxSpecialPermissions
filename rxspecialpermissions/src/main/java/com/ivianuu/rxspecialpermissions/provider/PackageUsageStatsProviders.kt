/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.rxspecialpermissions.provider

import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * Package usage stats providers
 */
class PackageUsageStatsProviders private constructor(private val context: Context) :
    RealPermission.GrantedProvider, RealPermission.IntentProvider {

    override fun granted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true
        } else {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOps.checkOpNoThrow(
                "android:get_usage_stats",
                android.os.Process.myUid(), context.packageName
            )
            if (mode != AppOpsManager.MODE_ALLOWED) {
                return false
            }

            // Verify that access is possible. Some devices "lie" and return MODE_ALLOWED even when it's not.
            val now = System.currentTimeMillis()
            val usageStatsManager =
                context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            val stats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                now - 1000 * 10,
                now
            )
            return stats != null && !stats.isEmpty()
        }
    }

    override fun getIntent(): Intent {
        return Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
    }

    companion object {

        /**
         * Returns new package usage stats providers
         */
        fun create(context: Context): PackageUsageStatsProviders {
            return PackageUsageStatsProviders(context)
        }
    }
}
