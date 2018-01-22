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

package com.ivianuu.rxspecialpermissions.permissiongroup

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.RestrictTo
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.ivianuu.rxspecialpermissions.permission.Permission
import java.util.*

/**
 * Permission group builder
 */
class PermissionGroupBuilder @RestrictTo(RestrictTo.Scope.LIBRARY)
constructor(private val context: Context) {
    private val permissions = ArrayList<Permission>()
    private var title: String? = null
    private var desc: String? = null
    private var icon: Drawable? = null

    /**
     * Sets the title of this permission
     */
    fun titleRes(@StringRes titleRes: Int): PermissionGroupBuilder {
        return title(context.getString(titleRes))
    }

    /**
     * Sets the title of this permission
     */
    fun title(title: String): PermissionGroupBuilder {
        this.title = title
        return this
    }

    /**
     * Sets the desc of this permission
     */
    fun descRes(@StringRes descRes: Int): PermissionGroupBuilder {
        return desc(context.getString(descRes))
    }

    /**
     * Sets the desc of this permission
     */
    fun desc(desc: String): PermissionGroupBuilder {
        this.desc = desc
        return this
    }

    /**
     * Sets the icon of this permission
     */
    fun iconRes(@DrawableRes iconRes: Int): PermissionGroupBuilder {
        return icon(ContextCompat.getDrawable(context, iconRes))
    }

    /**
     * Sets the icon of this permission
     */
    fun icon(icon: Drawable): PermissionGroupBuilder {
        this.icon = icon
        return this
    }

    /**
     * Adds the permission to this group
     */
    fun addPermission(permission: Permission): PermissionGroupBuilder {
        this.permissions.add(permission)
        return this
    }

    /**
     * Returns the permission group
     */
    fun build(): PermissionGroup {
        return RealPermissionGroup(permissions, title, desc, icon)
    }
}
