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

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Device admin provider
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class DeviceAdminProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Class clazz;
    private final String explanation;

    public DeviceAdminProviders(@NonNull Class clazz, @Nullable String explanation) {
        this.clazz = clazz;
        this.explanation = explanation;
    }

    @Override
    public boolean granted(@NonNull Context context) {
        ComponentName admin = new ComponentName(context, clazz);
        DevicePolicyManager dpm = (DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        return dpm.isAdminActive(admin);
    }

    @NonNull
    @Override
    public Intent getIntent(@NonNull Context context) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(context, clazz));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, explanation);
        return intent;
    }
}
