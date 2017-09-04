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

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Permission
 */
public interface Permission {
    /**
     * Returns whether the permission is granted
     */
    boolean granted();
    /**
     * Returns the intent of this permission
     */
    @NonNull Intent getIntent();
    /**
     * Returns the title of this permission
     */
    @NonNull String getTitle();
    /**
     * Returns the description of this permission
     */
    @Nullable String getDescription();
    /**
     * Returns the icon of this permission
     */
    @Nullable Drawable getIcon();
}
