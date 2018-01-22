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

package com.ivianuu.rxspecialpermissions

import android.content.Context
import com.ivianuu.rxspecialpermissions.permission.PermissionBuilder
import com.ivianuu.rxspecialpermissions.provider.*

/**
 * Permission builder chooser
 */
class PermissionBuilderChooser internal constructor(private val context: Context) {

    /**
     * Returns a builder with accessibility service providers
     */
    fun accessibilityService(clazz: Class<*>): PermissionBuilder {
        val providers = AccessibilityServiceProviders.create(context, clazz)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with device admin providers
     */
    @JvmOverloads fun deviceAdmin(
        clazz: Class<*>,
        explanation: String? = null
    ): PermissionBuilder {
        val providers = DeviceAdminProviders.create(context, clazz, explanation)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with ignore battery optimizations providers
     */
    fun ignoreBatteryOptimizations(): PermissionBuilder {
        val providers = IgnoreBatteryOptimizationsProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with install unknown apps providers
     */
    fun installUnknownApps(): PermissionBuilder {
        val providers = InstallUnknownAppsProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with notification listener providers
     */
    fun notificationListener(clazz: Class<*>): PermissionBuilder {
        val providers = NotificationListenerProviders.create(context, clazz)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with notification policy access providers
     */
    fun notificationPolicyAccess(): PermissionBuilder {
        val providers = NotificationPolicyAccessProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with package usage stats providers
     */
    fun packageUsageStats(): PermissionBuilder {
        val providers = PackageUsageStatsProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with system overlay providers
     */
    fun systemOverlay(): PermissionBuilder {
        val providers = SystemOverlayProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with unrestricted data access providers
     */
    fun unrestrictedDataAccess(): PermissionBuilder {
        val providers = UnrestrictedDataAccessProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with vr listener providers
     */
    fun vrListener(clazz: Class<*>): PermissionBuilder {
        val providers = VrListenerProviders.create(context, clazz)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }

    /**
     * Returns a builder with write settings providers
     */
    fun writeSettings(): PermissionBuilder {
        val providers = WriteSettingsProviders.create(context)
        return PermissionBuilder(context)
            .grantedProvider(providers)
            .intentProvider(providers)
    }
}