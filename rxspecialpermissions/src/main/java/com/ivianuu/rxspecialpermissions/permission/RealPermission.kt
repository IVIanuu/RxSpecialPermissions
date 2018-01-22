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

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable

import io.reactivex.Single

/**
 * Real implementation of a [Permission]
 */
class RealPermission(
    override val title: String,
    override val desc: String?,
    override val icon: Drawable?,
    private val delegate: Delegate
) : Permission {

    override val intent: Intent
        get() = delegate.buildIntent()

    override fun granted(): Boolean = delegate.granted()

    override fun request(activity: Activity): Single<Boolean> {
        return SinglePermissionRequest(activity, this).request()
    }

    interface Delegate {

        fun granted(): Boolean

        fun buildIntent(): Intent

    }
}
