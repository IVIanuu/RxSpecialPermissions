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
import android.graphics.drawable.Drawable;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.Single;

/**
 * Permission group
 */
public interface PermissionGroup {
    /**
     * Returns whether the permissions are granted or not
     */
    boolean granted();

    /**
     * Returns the title of this permission group
     */
    @Nullable String getTitle();

    /**
     * Returns the description of this permission group
     */
    @Nullable String getDescription();

    /**
     * Returns the icon of this permission group
     */
    @Nullable Drawable getIcon();

    /**
     * Returns the permissions of this group
     */
    @NonNull List<Permission> getPermissions();

    /**
     * Requests the permission and emits the result
     */
    @CheckResult @NonNull Single<Boolean> request(@NonNull Activity activity);
}
