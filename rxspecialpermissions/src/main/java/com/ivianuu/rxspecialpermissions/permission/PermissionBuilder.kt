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

package com.ivianuu.rxspecialpermissions.permission

import android.content.Context
import com.ivianuu.rxspecialpermissions.delegate.*

/**
 * Factory for [RealPermissionBuilder]'s
 */
object PermissionBuilder {

    @JvmStatic
    fun accessibility(
        context: Context,
        clazz: Class<*>
    ): RealPermissionBuilder = withDelegate(context, AccessibilityDelegate(context, clazz))

    @JvmOverloads
    @JvmStatic
    fun deviceAdmin(
        context: Context,
        clazz: Class<*>,
        explanation: String? = null
    ): RealPermissionBuilder = withDelegate(context, DeviceAdminDelegate(context, clazz, explanation))

    @JvmStatic
    fun ignoreBatteryOptimizations(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, IgnoreBatteryOptimizationsDelegate(context))

    @JvmStatic
    fun installUnknownApps(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, InstallUnknownAppsDelegate(context))

    @JvmStatic
    fun notificationListener(
        context: Context,
        clazz: Class<*>
    ): RealPermissionBuilder = withDelegate(context, NotificationListenerDelegate(context, clazz))

    @JvmStatic
    fun notificationPolicyAccess(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, NotificationPolicyAccessDelegate(context))

    @JvmStatic
    fun packageUsageStats(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, PackageUsageStatsDelegate(context))

    @JvmStatic
    fun systemOverlay(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, SystemOverlayDelegate(context))

    @JvmStatic
    fun unrestrictedDataAccess(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, UnrestrictedDataAccessDelegate(context))

    @JvmStatic
    fun vrListener(
        context: Context,
        clazz: Class<*>
    ): RealPermissionBuilder = withDelegate(context, VrListenerDelegate(context, clazz))

    @JvmStatic
    fun writeSettings(
        context: Context
    ): RealPermissionBuilder = withDelegate(context, WriteSettingsDelegate(context))

    @JvmStatic
    fun withDelegate(
        context: Context,
        delegate: RealPermission.Delegate
    ): RealPermissionBuilder =
        RealPermissionBuilder(context, delegate)
}