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

import android.app.Activity
import android.graphics.drawable.Drawable
import com.ivianuu.rxspecialpermissions.permission.Permission
import io.reactivex.Single

/**
 * Permission group
 */
interface PermissionGroup {

    /**
     * Returns the title of this permission group
     */
    val title: String?

    /**
     * Returns the desc of this permission group
     */
    val desc: String?

    /**
     * Returns the icon of this permission group
     */
    val icon: Drawable?

    /**
     * Returns the permissions of this group
     */
    val permissions: List<Permission>

    /**
     * Returns whether the permissions are granted or not
     */
    fun granted(): Boolean

    /**
     * Requests the permission and emits the result
     */
    fun request(activity: Activity): Single<Boolean>
}
