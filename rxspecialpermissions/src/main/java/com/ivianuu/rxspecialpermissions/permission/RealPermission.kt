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
 * Base permission
 */
class RealPermission(
    override val title: String,
    override val desc: String?,
    override val icon: Drawable?,
    private val grantedProvider: GrantedProvider,
    private val intentProvider: IntentProvider
) : Permission {

    override val intent: Intent
        get() = intentProvider.getIntent()

    override fun granted(): Boolean {
        return grantedProvider.granted()
    }

    override fun request(activity: Activity): Single<Boolean> {
        return SinglePermissionRequest.create(activity, this)
    }

    interface GrantedProvider {
        /**
         * Returns whether the permission is granted
         */
        fun granted(): Boolean
    }

    interface IntentProvider {
        /**
         * Returns the intent
         */
        fun getIntent(): Intent
    }
}
