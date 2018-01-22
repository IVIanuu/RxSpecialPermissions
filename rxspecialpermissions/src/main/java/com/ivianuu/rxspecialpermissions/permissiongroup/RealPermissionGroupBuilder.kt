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
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import com.ivianuu.rxspecialpermissions.permission.Permission
import java.util.*

/**
 * Permission group builder
 */
class RealPermissionGroupBuilder internal constructor(private val context: Context) {
    private val permissions = ArrayList<Permission>()
    private var title: String? = null
    private var desc: String? = null
    private var icon: Drawable? = null

    fun title(title: String): RealPermissionGroupBuilder {
        this.title = title
        return this
    }

    fun title(@StringRes titleRes: Int): RealPermissionGroupBuilder {
        return title(context.getString(titleRes))
    }

    fun desc(desc: String): RealPermissionGroupBuilder {
        this.desc = desc
        return this
    }

    fun desc(@StringRes descRes: Int): RealPermissionGroupBuilder {
        return desc(context.getString(descRes))
    }

    fun icon(icon: Drawable): RealPermissionGroupBuilder {
        this.icon = icon
        return this
    }

    fun icon(@DrawableRes iconRes: Int): RealPermissionGroupBuilder {
        return icon(ContextCompat.getDrawable(context, iconRes))
    }

    fun addPermission(permission: Permission): RealPermissionGroupBuilder {
        this.permissions.add(permission)
        return this
    }

    fun build(): PermissionGroup {
        return RealPermissionGroup(permissions, title, desc, icon)
    }
}
