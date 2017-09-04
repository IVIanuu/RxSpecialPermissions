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

package com.ivianuu.rxspecialpermissions.permissionrequest;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;

import com.ivianuu.rxspecialpermissions.permission.Permission;

/**
 * Single permission request builder
 */
public final class SinglePermissionRequestBuilder extends BasePermissionRequestBuilder<SinglePermissionRequestBuilder> {

    Permission permission;

    String positiveText;

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public SinglePermissionRequestBuilder(@NonNull Activity activity, @NonNull Permission permission) {
        super(activity);
        this.permission = permission;
    }

    /**
     * Sets the negative text
     */
    public SinglePermissionRequestBuilder positiveTextRes(@StringRes int positiveTextRes) {
        return positiveText(activity.getString(positiveTextRes));
    }

    /**
     * Sets the negative text
     */
    public SinglePermissionRequestBuilder positiveText(@NonNull String positiveText) {
        this.positiveText = positiveText;
        return thiz();
    }

    @Override
    protected void validate() {
        if (positiveText == null) {
            throw new IllegalStateException("positive text is missing");
        }
    }

    @Override
    protected SinglePermissionRequestBuilder thiz() {
        return this;
    }

    @NonNull
    @Override
    protected PermissionRequest buildPermissionRequest() {
        return new SinglePermissionRequest(this);
    }
}
