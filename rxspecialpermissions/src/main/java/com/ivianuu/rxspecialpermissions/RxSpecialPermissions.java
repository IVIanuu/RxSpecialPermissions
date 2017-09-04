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

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroupBuilder;

import rx_activity_result2.RxActivityResult;

/**
 * RxSpecialPermissions
 */
public final class RxSpecialPermissions {

    private RxSpecialPermissions() {
        // no instances
    }

    /**
     * This must be called before requesting permissions
     */
    public static void init(@NonNull Application application) {
        RxActivityResult.register(application);
    }

    /**
     * Returns a permissions builder
     */
    @NonNull
    public static PermissionBuilderChooser permissionBuilder(@NonNull Context context) {
        return new PermissionBuilderChooser(context);
    }

    /**
     * Returns a permission group builder
     */
    @NonNull
    public static PermissionGroupBuilder permissionGroupBuilder() {
        return new PermissionGroupBuilder();
    }

    /**
     * Returns request builder
     */
    @NonNull
    public static RequestBuilderChooser requestBuilder(@NonNull Activity activity) {
        return new RequestBuilderChooser(activity);
    }
}
