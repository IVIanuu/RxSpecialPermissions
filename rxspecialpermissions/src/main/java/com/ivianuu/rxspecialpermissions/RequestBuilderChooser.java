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
import android.support.annotation.NonNull;

import com.ivianuu.rxspecialpermissions.permission.Permission;
import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroup;
import com.ivianuu.rxspecialpermissions.permissionrequest.PermissionGroupRequestBuilder;
import com.ivianuu.rxspecialpermissions.permissionrequest.SinglePermissionRequestBuilder;

/**
 * Request builder chooser
 */
public class RequestBuilderChooser {

    private final Activity activity;

    RequestBuilderChooser(@NonNull Activity activity) {
        this.activity = activity;
    }

    /**
     * Returns a single permission request builder
     */
    @NonNull
    public SinglePermissionRequestBuilder single(@NonNull Permission permission) {
        return new SinglePermissionRequestBuilder(activity, permission);
    }

    /**
     * Returns a permission group request builder
     */
    @NonNull
    public PermissionGroupRequestBuilder permissionGroup(@NonNull PermissionGroup permissionGroup) {
        return new PermissionGroupRequestBuilder(activity, permissionGroup);
    }
}
