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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Write system settings providers
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class WriteSettingsProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Context context;

    private WriteSettingsProviders(Context context) {
        this.context = context;
    }

    /**
     * Returns new write settings providers
     */
    @NonNull
    public static WriteSettingsProviders create(@NonNull Context context) {
        return new WriteSettingsProviders(context);
    }

    @Override
    public boolean granted() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || Settings.System.canWrite(context);
    }

    @NonNull
    @Override
    public Intent getIntent() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        return intent;
    }
}
