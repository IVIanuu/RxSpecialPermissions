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

import com.ivianuu.rxspecialpermissions.R;
import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroup;

/**
 * Permission group request builder
 */
public class PermissionGroupRequestBuilder extends BasePermissionRequestBuilder<PermissionGroupRequestBuilder> {

    PermissionGroup permissionGroup;

    String title;
    String content;

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    public PermissionGroupRequestBuilder(@NonNull Activity activity, @NonNull PermissionGroup permissionGroup) {
        super(activity);
        this.permissionGroup = permissionGroup;
    }

    /**
     * Sets the title
     */
    public PermissionGroupRequestBuilder titleRes(@StringRes int titleRes) {
        return title(activity.getString(titleRes));
    }

    /**
     * Sets the title
     */
    public PermissionGroupRequestBuilder title(@NonNull String title) {
        this.title = title;
        return this;
    }

    /**
     * Sets the content
     */
    public PermissionGroupRequestBuilder contentRes(@StringRes int contentRes) {
        return content(activity.getString(contentRes));
    }

    /**
     * Sets the content
     */
    public PermissionGroupRequestBuilder content(@NonNull String content) {
        this.content = content;
        return this;
    }

    @Override
    protected void validate() {
        if (permissionGroup == null)  {
            throw new IllegalStateException("permission group is missing");
        }
        if (permissionGroup.getPermissions().isEmpty()) {
            throw new IllegalStateException("must contain at least 1 permissions");
        }
        if (title == null) {
            title = activity.getString(R.string.default_permission_group_request_title);
        }
    }

    @Override
    protected PermissionGroupRequestBuilder thiz() {
        return this;
    }

    @NonNull
    @Override
    protected PermissionRequest buildPermissionRequest() {
        return new PermissionGroupRequest(this);
    }
}
