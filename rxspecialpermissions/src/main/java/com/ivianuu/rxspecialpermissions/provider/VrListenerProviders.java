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
import android.service.vr.VrListenerService;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.RealPermission;

/**
 * Vr listener providers
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class VrListenerProviders implements RealPermission.GrantedProvider, RealPermission.IntentProvider {

    private final Class clazz;

    public VrListenerProviders(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean granted(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ComponentName componentName = new ComponentName(context, clazz);
            return VrListenerService.isVrModePackageEnabled(context, componentName);
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public Intent getIntent(@NonNull Context context) {
        return new Intent(Settings.ACTION_VR_LISTENER_SETTINGS);
    }
}
