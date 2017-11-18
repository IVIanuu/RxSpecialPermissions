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

import android.content.ComponentName;
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
public final class NotificationListenerProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private static final String NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";

    private final Context context;
    private final Class clazz;

    private NotificationListenerProviders(Context context,
                                          Class clazz) {
        this.context = context;
        this.clazz = clazz;
    }

    /**
     * Returns new notification listener providers
     */
    @NonNull
    public static NotificationListenerProviders create(@NonNull Context context,
                                                       @NonNull Class clazz) {
        return new NotificationListenerProviders(context, clazz);
    }

    @Override
    public boolean granted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return false;
        } else {
            ComponentName cn = new ComponentName(context, clazz);
            String enabledListeners = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
            return enabledListeners != null && enabledListeners.contains(cn.flattenToString());
        }
    }

    @NonNull
    @Override
    public Intent getIntent() {
        return new Intent(NOTIFICATION_LISTENER_SETTINGS);
    }
}
