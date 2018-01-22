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

package com.ivianuu.rxspecialpermissions.permission

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat

/**
 * Real permission builder
 */
class RealPermissionBuilder constructor(context: Context,
                                        private val delegate: RealPermission.Delegate) {

    private val context = context.applicationContext

    private lateinit var title: String
    private var desc: String? = null
    private var icon: Drawable? = null

    fun title(@StringRes titleRes: Int): RealPermissionBuilder {
        return title(context.getString(titleRes))
    }

    fun title(title: String): RealPermissionBuilder {
        this.title = title
        return this
    }

    fun desc(@StringRes descRes: Int): RealPermissionBuilder {
        return desc(context.getString(descRes))
    }

    fun desc(desc: String): RealPermissionBuilder {
        this.desc = desc
        return this
    }

    fun icon(@DrawableRes iconRes: Int): RealPermissionBuilder {
        return icon(ContextCompat.getDrawable(context, iconRes))
    }

    fun icon(icon: Drawable): RealPermissionBuilder {
        this.icon = icon
        return this
    }

    fun build(): Permission {
        return RealPermission(title, desc, icon, delegate)
    }
}
