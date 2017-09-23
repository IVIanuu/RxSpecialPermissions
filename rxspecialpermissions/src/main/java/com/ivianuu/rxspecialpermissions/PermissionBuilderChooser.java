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

package com.ivianuu.rxspecialpermissions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ivianuu.rxspecialpermissions.permission.PermissionBuilder;
import com.ivianuu.rxspecialpermissions.provider.AccessibilityServiceProviders;
import com.ivianuu.rxspecialpermissions.provider.DeviceAdminProviders;
import com.ivianuu.rxspecialpermissions.provider.IgnoreBatteryOptimizationsProviders;
import com.ivianuu.rxspecialpermissions.provider.InstallUnknownAppsProviders;
import com.ivianuu.rxspecialpermissions.provider.NotificationListenerProviders;
import com.ivianuu.rxspecialpermissions.provider.NotificationPolicyAccessProviders;
import com.ivianuu.rxspecialpermissions.provider.PackageUsageStatsProviders;
import com.ivianuu.rxspecialpermissions.provider.SystemOverlayProviders;
import com.ivianuu.rxspecialpermissions.provider.UnrestrictedDataAccessProviders;
import com.ivianuu.rxspecialpermissions.provider.VrListenerProviders;
import com.ivianuu.rxspecialpermissions.provider.WriteSettingsProviders;

/**
 * Permission builder chooser
 */
public final class PermissionBuilderChooser {

    private final Context context;

    PermissionBuilderChooser(@NonNull Context context) {
        this.context = context;
    }
    
    /**
     * Returns a builder without any providers
     */
    @NonNull
    public PermissionBuilder custom() {
        return new PermissionBuilder(context);
    }

    /**
     * Returns a builder with accessibility service providers
     */
    @NonNull
    public PermissionBuilder accessibilityService(@NonNull Class clazz) {
        AccessibilityServiceProviders providers
                = AccessibilityServiceProviders.create(context, clazz);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with device admin providers
     */
    @NonNull
    public PermissionBuilder deviceAdmin(@NonNull Class clazz) {
        return deviceAdmin(clazz, null);
    }

    /**
     * Returns a builder with device admin providers
     */
    @NonNull
    public PermissionBuilder deviceAdmin(@NonNull Class clazz,
                                         @Nullable String explanation) {
        DeviceAdminProviders providers
                = DeviceAdminProviders.create(context, clazz, explanation);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with ignore battery optimizations providers
     */
    @NonNull
    public PermissionBuilder ignoreBatteryOptimizations() {
        IgnoreBatteryOptimizationsProviders providers
                = IgnoreBatteryOptimizationsProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with install unknown apps providers
     */
    @NonNull
    public PermissionBuilder installUnknownApps() {
        InstallUnknownAppsProviders providers
                = InstallUnknownAppsProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with notification listener providers
     */
    @NonNull
    public PermissionBuilder notificationListener() {
        NotificationListenerProviders providers
                = NotificationListenerProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with notification policy access providers
     */
    @NonNull
    public PermissionBuilder notificationPolicyAccess() {
        NotificationPolicyAccessProviders providers
                = NotificationPolicyAccessProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with package usage stats providers
     */
    @NonNull
    public PermissionBuilder packageUsageStats() {
        PackageUsageStatsProviders providers
                = PackageUsageStatsProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with system overlay providers
     */
    @NonNull
    public PermissionBuilder systemOverlay() {
        SystemOverlayProviders providers
                = SystemOverlayProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with unrestricted data access providers
     */
    @NonNull
    public PermissionBuilder unrestrictedDataAccess() {
        UnrestrictedDataAccessProviders providers
                = UnrestrictedDataAccessProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with vr listener providers
     */
    @NonNull
    public PermissionBuilder vrListener(@NonNull Class clazz) {
        VrListenerProviders providers
                = VrListenerProviders.create(context, clazz);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }

    /**
     * Returns a builder with write settings providers
     */
    @NonNull
    public PermissionBuilder writeSettings() {
        WriteSettingsProviders providers
                = WriteSettingsProviders.create(context);
        return new PermissionBuilder(context)
                .grantedProvider(providers)
                .intentProvider(providers);
    }
}
