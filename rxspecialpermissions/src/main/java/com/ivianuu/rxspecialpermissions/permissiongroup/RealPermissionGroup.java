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

package com.ivianuu.rxspecialpermissions.permissiongroup;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ivianuu.rxspecialpermissions.Permission;
import com.ivianuu.rxspecialpermissions.PermissionGroup;

import java.util.List;

import io.reactivex.Single;

/**
 * Permission group impl
 */
final class RealPermissionGroup implements PermissionGroup {

    private final List<Permission> permissions;
    private final String title;
    private final String description;
    private final Drawable icon;

    RealPermissionGroup(@NonNull List<Permission> permissions,
                        @Nullable String title,
                        @Nullable String description,
                        @Nullable Drawable icon) {
        this.permissions = permissions;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public boolean granted() {
        for (Permission permission : permissions) {
            if (!permission.granted()) {
                return false;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public String getTitle() {
        return title;
    }

    @Nullable
    @Override
    public String getDescription() {
        return description;
    }

    @Nullable
    @Override
    public Drawable getIcon() {
        return icon;
    }

    @NonNull
    @Override
    public List<Permission> getPermissions() {
        return permissions;
    }

    @NonNull
    @Override
    public Single<Boolean> request(@NonNull Activity activity) {
        return PermissionGroupRequest.create(activity, this);
    }
}
