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

package com.ivianuu.rxspecialpermissions.permission;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.ivianuu.rxspecialpermissions.Permission;

/**
 * Real permission builder
 */
public final class PermissionBuilder {

    private final Context context;

    private String title;
    private String description;
    private Drawable icon;
    private RealPermission.GrantedProvider grantedProvider;
    private RealPermission.IntentProvider intentProvider;

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public PermissionBuilder(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Sets the title of this permission
     */
    @NonNull
    public PermissionBuilder titleRes(@StringRes int titleRes) {
        return title(context.getString(titleRes));
    }

    /**
     * Sets the title of this permission
     */
    @NonNull
    public PermissionBuilder title(@NonNull String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the description of this permission
     */
    @NonNull
    public PermissionBuilder descriptionRes(@StringRes int descriptionRes) {
        return description(context.getString(descriptionRes));
    }

    /**
     * Sets the description of this permission
     */
    @NonNull
    public PermissionBuilder description(@NonNull String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets the icon of this permission
     */
    @NonNull
    public PermissionBuilder iconRes(@DrawableRes int iconRes) {
        return icon(ContextCompat.getDrawable(context, iconRes));
    }

    /**
     * Sets the icon of this permission
     */
    @NonNull
    public PermissionBuilder icon(@NonNull Drawable icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Sets the granted predicate
     */
    @NonNull
    public PermissionBuilder grantedProvider(@NonNull RealPermission.GrantedProvider grantedProvider) {
        this.grantedProvider = grantedProvider;
        return this;
    }

    /**
     * Sets the intent provider
     */
    @NonNull
    public PermissionBuilder intentProvider(@NonNull RealPermission.IntentProvider intentProvider) {
        this.intentProvider = intentProvider;
        return this;
    }

    /**
     * Returns the permission
     */
    @NonNull
    public Permission build() {
        if (title == null) {
            throw new IllegalStateException("missing title");
        }
        if (grantedProvider == null) {
            throw new IllegalStateException("missing granted provider");
        }
        if (intentProvider == null) {
            throw new IllegalStateException("missing intent provider");
        }

        return new RealPermission(title, description, icon, grantedProvider, intentProvider);
    }
}
