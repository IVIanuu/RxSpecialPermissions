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

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Notification listener providers
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class NotificationListenerProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private static final String NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    @Override
    public boolean granted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        } else {
            ContentResolver contentResolver = context.getContentResolver();
            String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
            String packageName = context.getPackageName();

            return !(enabledNotificationListeners == null || !enabledNotificationListeners.contains(packageName));
        }
    }

    @NonNull
    @Override
    public Intent getIntent(@NonNull Context context) {
        return new Intent(NOTIFICATION_LISTENER_SETTINGS);
    }
}
