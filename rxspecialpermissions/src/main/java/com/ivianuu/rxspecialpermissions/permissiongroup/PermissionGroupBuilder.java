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

import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;

import com.ivianuu.rxspecialpermissions.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * Permission group builder
 */
public final class PermissionGroupBuilder {

    private final List<Permission> permissions = new ArrayList<>();

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public PermissionGroupBuilder() {

    }

    /**
     * Adds the permission to this group
     */
    public PermissionGroupBuilder add(@NonNull Permission permission) {
        this.permissions.add(permission);;
        return this;
    }

    /**
     * Returns the permission group
     */
    @NonNull
    public PermissionGroup build() {
        return new RealPermissionGroup(permissions);
    }
}
