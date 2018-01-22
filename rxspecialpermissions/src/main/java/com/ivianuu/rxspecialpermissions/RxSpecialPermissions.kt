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

package com.ivianuu.rxspecialpermissions

import android.app.Application
import android.content.Context
import android.support.annotation.StringRes

import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroupBuilder

/**
 * RxSpecialPermissions
 */
object RxSpecialPermissions {

    private var config: Config? = null

    /**
     * This must be called before requesting permissions
     */
    @JvmStatic
    fun init(application: Application) {
        config = Config.Builder(application)
            .denyTextRes(R.string.default_deny_text)
            .grantTextRes(R.string.default_grant_text)
            .build()
    }

    /**
     * Sets the config
     */
    @JvmStatic
    fun setConfig(config: Config) {
        RxSpecialPermissions.config = config
    }

    /**
     * Returns the config
     */
    @JvmStatic
    fun getConfig(): Config {
        return config!!
    }

    /**
     * Returns a permissions builder
     */
    @JvmStatic
    fun permissionBuilder(context: Context): PermissionBuilderChooser {
        return PermissionBuilderChooser(context)
    }

    /**
     * Returns a permission group builder
     */
    @JvmStatic
    fun permissionGroupBuilder(context: Context): PermissionGroupBuilder {
        return PermissionGroupBuilder(context)
    }

    /**
     * Returns a config builder
     */
    @JvmStatic
    fun configBuilder(context: Context): Config.Builder {
        return Config.Builder(context)
    }

    class Config private constructor(
        val grantText: String,
        val denyText: String
    ) {

        class Builder (private val context: Context) {

            private var grantText: String? = null
            private var denyText: String? = null

            private var cancelableDialogs: Boolean = false

            /**
             * Sets the grant text of this config
             */
            fun grantTextRes(@StringRes grantTextRes: Int): Builder {
                return grantText(context.getString(grantTextRes))
            }

            /**
             * Sets the grant text of this config
             */
            fun grantText(grantText: String): Builder {
                this.grantText = grantText
                return this
            }

            /**
             * Sets the deny text of this config
             */
            fun denyTextRes(@StringRes denyTextRes: Int): Builder {
                return denyText(context.getString(denyTextRes))
            }

            /**
             * Sets the deny text of this config
             */
            fun denyText(denyText: String): Builder {
                this.denyText = denyText
                return this
            }

            /**
             * Sets the dialogs cancelable
             */
            fun cancelableDialogs(cancelableDialogs: Boolean): Builder {
                this.cancelableDialogs = cancelableDialogs
                return this
            }

            /**
             * Returns a new config instance
             */
            fun build(): Config {
                return Config(grantText!!, denyText!!)
            }
        }
    }
}
