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

package com.ivianuu.rxspecialpermissions.delegate

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * A [RealPermission.Delegate] for the [Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE] permission
 */
class NotificationListenerDelegate constructor(
    private val context: Context,
    private val clazz: Class<*>
) : RealPermission.Delegate {

    override fun granted(): Boolean {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            false
        } else {
            val cn = ComponentName(context, clazz)
            val enabledListeners =
                Settings.Secure.getString(context.contentResolver, "enabled_notification_listeners")
            enabledListeners != null && enabledListeners.contains(cn.flattenToString())
        }
    }

    override fun buildIntent(): Intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
}
