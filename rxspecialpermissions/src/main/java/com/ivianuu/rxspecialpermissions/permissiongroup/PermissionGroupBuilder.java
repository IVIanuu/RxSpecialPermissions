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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.ivianuu.rxspecialpermissions.permission.Permission;

import java.util.ArrayList;
import java.util.List;

import static com.ivianuu.preconditions.Preconditions.checkCollectionNotEmpty;

/**
 * Permission group builder
 */
public final class PermissionGroupBuilder {

    private final Context context;
    private final List<Permission> permissions = new ArrayList<>();
    private String title;
    private String description;
    private Drawable icon;
    
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public PermissionGroupBuilder(@NonNull Context context) {
        this.context = context;
    }

    /**
     * Sets the title of this permission
     */
    @NonNull
    public PermissionGroupBuilder titleRes(@StringRes int titleRes) {
        return title(context.getString(titleRes));
    }

    /**
     * Sets the title of this permission
     */
    @NonNull
    public PermissionGroupBuilder title(@NonNull String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the description of this permission
     */
    @NonNull
    public PermissionGroupBuilder descriptionRes(@StringRes int descriptionRes) {
        return description(context.getString(descriptionRes));
    }

    /**
     * Sets the description of this permission
     */
    @NonNull
    public PermissionGroupBuilder description(@NonNull String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the icon of this permission
     */
    @NonNull
    public PermissionGroupBuilder iconRes(@DrawableRes int iconRes) {
        return icon(ContextCompat.getDrawable(context, iconRes));
    }

    /**
     * Sets the icon of this permission
     */
    @NonNull
    public PermissionGroupBuilder icon(@NonNull Drawable icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Adds the permission to this group
     */
    @NonNull
    public PermissionGroupBuilder addPermission(@NonNull Permission permission) {
        this.permissions.add(permission);;
        return this;
    }

    /**
     * Returns the permission group
     */
    @NonNull
    public PermissionGroup build() {
        checkCollectionNotEmpty(permissions, "permissions empty");
        return new RealPermissionGroup(permissions, title, description, icon);
    }
}
