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

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * Ignore battery
 */
class IgnoreBatteryOptimizationsProviders private constructor(private val context: Context) :
    RealPermission.GrantedProvider, RealPermission.IntentProvider {

    override fun granted(): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || powerManager.isIgnoringBatteryOptimizations(
            context.packageName
        )
    }

    override fun getIntent(): Intent {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:" + context.packageName)
        return intent
    }

    companion object {
        /**
         * Returns new ignore battery optimizations providers
         */
        fun create(context: Context): IgnoreBatteryOptimizationsProviders {
            return IgnoreBatteryOptimizationsProviders(context)
        }
    }
}
