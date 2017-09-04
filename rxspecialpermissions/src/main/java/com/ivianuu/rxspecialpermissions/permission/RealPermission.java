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
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Base permission
 */
public final class RealPermission implements Permission {

    private final Context context;
    private final String title;
    private final String description;
    private final Drawable icon;
    private final GrantedProvider grantedProvider;
    private final IntentProvider intentProvider;

    public RealPermission(@NonNull PermissionBuilder builder) {
        this.context = builder.context;
        this.title = builder.title;
        this.description = builder.description;
        this.icon = builder.icon;
        this.grantedProvider = builder.grantedProvider;
        this.intentProvider = builder.intentProvider;
    }

    @Override
    public boolean granted() {
        return grantedProvider.granted(context);
    }

    @NonNull
    @Override
    public Intent getIntent() {
        return intentProvider.getIntent(context);
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

    public interface GrantedProvider {
        /**
         * Returns whether the permission is granted
         */
        boolean granted(@NonNull Context context);
    }

    public interface IntentProvider {
        /**
         * Returns the intent
         */
        @NonNull Intent getIntent(@NonNull Context context);
    }
}
