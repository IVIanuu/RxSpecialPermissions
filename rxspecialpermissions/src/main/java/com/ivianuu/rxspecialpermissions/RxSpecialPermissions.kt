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

import android.content.Context
import android.support.annotation.StringRes

/**
 * RxSpecialPermissions
 */
object RxSpecialPermissions {

    private var config: Config? = null

    @JvmStatic
    fun setConfig(config: Config) {
        RxSpecialPermissions.config = config
    }

    @JvmStatic
    fun getOrCreateConfig(context: Context): Config {
        var config = this.config
        if (config == null) { 
            config = Config.Builder(context)
                .negativeText(android.R.string.ok)
                .positiveText(android.R.string.cancel)
                .build()
            this.config = config
        }
        
        return config
    }

    @JvmStatic
    fun configBuilder(context: Context): Config.Builder {
        return Config.Builder(context)
    }

    data class Config constructor(
        val positiveText: String,
        val negativeText: String
    ) {

        class Builder (private val context: Context) {

            private var positiveText = context.getString(android.R.string.ok)
            private var negativeText = context.getString(android.R.string.cancel)
            
            fun positiveText(@StringRes positiveTextRes: Int): Builder {
                return positiveText(context.getString(positiveTextRes))
            }

            fun positiveText(positiveText: String): Builder {
                this.positiveText = positiveText
                return this
            }

            fun negativeText(@StringRes negativeTextRes: Int): Builder {
                return negativeText(context.getString(negativeTextRes))
            }

            fun negativeText(negativeText: String): Builder {
                this.negativeText = negativeText
                return this
            }

            fun build(): Config {
                return Config(positiveText, negativeText)
            }
        }
    }
}
