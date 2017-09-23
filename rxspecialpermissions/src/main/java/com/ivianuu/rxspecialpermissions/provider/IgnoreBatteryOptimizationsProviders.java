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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Ignore battery
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class IgnoreBatteryOptimizationsProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Context context;

    private IgnoreBatteryOptimizationsProviders(Context context) {
        this.context = context;
    }

    /**
     * Returns new ignore battery optimizations providers
     */
    @NonNull
    public static IgnoreBatteryOptimizationsProviders create(@NonNull Context context) {
        return new IgnoreBatteryOptimizationsProviders(context);
    }

    @Override
    public boolean granted() {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
    }

    @NonNull
    @Override
    public Intent getIntent() {
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        return intent;
    }
}
