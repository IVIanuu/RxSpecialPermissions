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
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * A [RealPermission.Delegate] for the [Manifest.permission.BIND_ACCESSIBILITY_SERVICE] permission
 */
internal class AccessibilityDelegate constructor(
    private val context: Context,
    private val clazz: Class<*>
) : RealPermission.Delegate {

    override fun granted(): Boolean {
        val contentResolver = context.contentResolver

        try {
            val r = Settings.Secure.getInt(contentResolver, Settings.Secure.ACCESSIBILITY_ENABLED)
            if (r != 1) return false
        } catch (e: Settings.SettingNotFoundException) {
        }

        val flat = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        return flat != null && flat.contains(clazz.canonicalName)
    }

    override fun buildIntent(): Intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
}
