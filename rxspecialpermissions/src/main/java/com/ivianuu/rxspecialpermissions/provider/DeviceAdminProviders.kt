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

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * Device admin provider
 */
class DeviceAdminProviders(
    private val context: Context,
    private val clazz: Class<*>,
    private val explanation: String?
) : RealPermission.GrantedProvider, RealPermission.IntentProvider {

    override fun granted(): Boolean {
        val admin = ComponentName(context, clazz)
        val dpm = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        return dpm.isAdminActive(admin)
    }

    override fun getIntent(): Intent {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, ComponentName(context, clazz))
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, explanation)
        return intent
    }

    companion object {
        /**
         * Returns new device admin providers
         */
        fun create(
            context: Context,
            clazz: Class<*>,
            explanation: String?
        ): DeviceAdminProviders {
            return DeviceAdminProviders(context, clazz, explanation)
        }
    }
}
