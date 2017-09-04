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
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;

import io.reactivex.Single;

/**
 * Base permission request builder
 */
abstract class BasePermissionRequestBuilder<T> {

    final Activity activity;

    boolean cancelable;

    String negativeText;

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    BasePermissionRequestBuilder(@NonNull Activity activity) {
        this.activity = activity;
    }

    /**
     * Sets the dialog cancelable
     */
    public T cancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return thiz();
    }

    /**
     * Sets the negative text
     */
    public T negativeTextRes(@StringRes int negativeTextRes) {
        return negativeText(activity.getString(negativeTextRes));
    }

    /**
     * Sets the negative text
     */
    public T negativeText(@NonNull String negativeText) {
        this.negativeText = negativeText;
        return thiz();
    }

    @NonNull
    public PermissionRequest build() {
        if (activity == null) {
            throw new IllegalStateException("missing activity");
        }
        if (negativeText == null) {
            throw new IllegalStateException("missing negative text");
        }

        validate();

        return buildPermissionRequest();
    }

    @CheckResult @NonNull
    public Single<Boolean> request() {
        PermissionRequest request = build();
        return request.request();
    }

    /**
     * Validates the current builder
     * Should throw an illegal state exception if a field is missing
     */
    protected abstract void validate();

    /**
     * Returns this builder
     */
    protected abstract T thiz();

    /**
     * Returns the permission request
     */
    @NonNull
    protected abstract PermissionRequest buildPermissionRequest();
}
