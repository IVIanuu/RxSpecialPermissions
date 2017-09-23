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

package com.ivianuu.rxspecialpermissions.provider;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

import java.util.List;

/**
 * Package usage stats providers
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class PackageUsageStatsProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Context context;

    private PackageUsageStatsProviders(Context context) {
        this.context = context;
    }

    /**
     * Returns new package usage stats providers
     */
    @NonNull
    public static PackageUsageStatsProviders create(@NonNull Context context) {
        return new PackageUsageStatsProviders(context);
    }

    @Override
    public boolean granted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1){
            return true;
        } else {
            AppOpsManager appOps
                    = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow("android:get_usage_stats",
                    android.os.Process.myUid(), context.getPackageName());
            if (mode != AppOpsManager.MODE_ALLOWED) {
                return false;
            }

            // Verify that access is possible. Some devices "lie" and return MODE_ALLOWED even when it's not.
            final long now = System.currentTimeMillis();
            final UsageStatsManager usageStatsManager
                    = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            final List<UsageStats> stats
                    = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, now - 1000 * 10, now);
            return (stats != null && !stats.isEmpty());
        }
    }

    @NonNull
    @Override
    public Intent getIntent() {
        return new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
    }
}
