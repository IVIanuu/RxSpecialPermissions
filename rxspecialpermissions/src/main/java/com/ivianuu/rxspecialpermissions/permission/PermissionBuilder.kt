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
class PermissionBuilder constructor(context: Context) {

    private val context = context.applicationContext

    private var title: String? = null
    private var desc: String? = null
    private var icon: Drawable? = null
    private var grantedProvider: RealPermission.GrantedProvider? = null
    private var intentProvider: RealPermission.IntentProvider? = null

    /**
     * Sets the title of this permission
     */
    fun titleRes(@StringRes titleRes: Int): PermissionBuilder {
        return title(context.getString(titleRes))
    }

    /**
     * Sets the title of this permission
     */
    fun title(title: String): PermissionBuilder {
        this.title = title
        return this
    }

    /**
     * Sets the description of this permission
     */
    fun descRes(@StringRes descRes: Int): PermissionBuilder {
        return desc(context.getString(descRes))
    }

    /**
     * Sets the description of this permission
     */
    fun desc(desc: String): PermissionBuilder {
        this.desc = desc
        return this
    }

    /**
     * Sets the icon of this permission
     */
    fun iconRes(@DrawableRes iconRes: Int): PermissionBuilder {
        return icon(ContextCompat.getDrawable(context, iconRes))
    }

    /**
     * Sets the icon of this permission
     */
    fun icon(icon: Drawable): PermissionBuilder {
        this.icon = icon
        return this
    }

    /**
     * Sets the granted predicate
     */
    fun grantedProvider(grantedProvider: RealPermission.GrantedProvider): PermissionBuilder {
        this.grantedProvider = grantedProvider
        return this
    }

    /**
     * Sets the intent provider
     */
    fun intentProvider(intentProvider: RealPermission.IntentProvider): PermissionBuilder {
        this.intentProvider = intentProvider
        return this
    }

    /**
     * Returns the permission
     */
    fun build(): Permission {
        return RealPermission(title!!, desc, icon, grantedProvider!!, intentProvider!!)
    }
}
