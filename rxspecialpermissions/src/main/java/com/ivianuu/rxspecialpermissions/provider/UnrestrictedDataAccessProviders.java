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
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Unrestricted data access provider
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class UnrestrictedDataAccessProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Context context;

    private UnrestrictedDataAccessProviders(Context context) {
        this.context = context;
    }

    /**
     * Returns new unrestricted data access providers
     */
    @NonNull
    public static UnrestrictedDataAccessProviders create(@NonNull Context context) {
        return new UnrestrictedDataAccessProviders(context);
    }

    @Override
    public boolean granted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return true;
        }

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connectivityManager.getRestrictBackgroundStatus()
                == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED;
    }

    @NonNull
    @Override
    public Intent getIntent() {
        return new Intent(Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS,
                Uri.parse("package:" + context.getPackageName()));
    }
}
