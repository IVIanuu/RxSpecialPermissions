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

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.service.vr.VrListenerService
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * Vr listener providers
 */
class VrListenerProviders(private val context: Context, private val clazz: Class<*>) :
    RealPermission.GrantedProvider, RealPermission.IntentProvider {

    override fun granted(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val componentName = ComponentName(context, clazz)
            return VrListenerService.isVrModePackageEnabled(context, componentName)
        } else {
            return false
        }
    }

    override fun getIntent(): Intent {
        return Intent(Settings.ACTION_VR_LISTENER_SETTINGS)
    }

    companion object {

        /**
         * Returns new vr listener providers
         */
        fun create(
            context: Context,
            clazz: Class<*>
        ): VrListenerProviders {
            return VrListenerProviders(context, clazz)
        }
    }
}
