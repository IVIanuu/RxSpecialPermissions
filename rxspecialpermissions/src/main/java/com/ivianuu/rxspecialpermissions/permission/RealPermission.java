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

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ivianuu.rxspecialpermissions.Permission;

import io.reactivex.Single;

/**
 * Base permission
 */
public final class RealPermission implements Permission {

    private final String title;
    private final String description;
    private final Drawable icon;
    private final GrantedProvider grantedProvider;
    private final IntentProvider intentProvider;

    public RealPermission(@NonNull String title,
                          @Nullable String description,
                          @Nullable Drawable icon,
                          @NonNull GrantedProvider grantedProvider,
                          @NonNull IntentProvider intentProvider) {
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.grantedProvider = grantedProvider;
        this.intentProvider = intentProvider;
    }

    @Override
    public boolean granted() {
        return grantedProvider.granted();
    }

    @NonNull
    @Override
    public Intent getIntent() {
        return intentProvider.getIntent();
    }

    @NonNull
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
    public Single<Boolean> request(@NonNull Activity activity) {
        return SinglePermissionRequest.create(activity, this);
    }

    public interface GrantedProvider {
        /**
         * Returns whether the permission is granted
         */
        boolean granted();
    }

    public interface IntentProvider {
        /**
         * Returns the intent
         */
        @NonNull Intent getIntent();
    }
}
