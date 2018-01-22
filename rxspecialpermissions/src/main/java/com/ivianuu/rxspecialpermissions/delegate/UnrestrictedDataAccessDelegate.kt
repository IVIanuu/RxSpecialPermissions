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

package com.ivianuu.rxspecialpermissions.delegate

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.ivianuu.rxspecialpermissions.permission.RealPermission

/**
 * A [RealPermission.Delegate] for the unrestricted data access permission
 */
class UnrestrictedDataAccessDelegate (private val context: Context) :
    RealPermission.Delegate {

    override fun granted(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return true
        }

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.restrictBackgroundStatus == ConnectivityManager.RESTRICT_BACKGROUND_STATUS_DISABLED
    }

    override fun buildIntent(): Intent = Intent(
        Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS,
        Uri.parse("package:" + context.packageName)
    )

    companion object {

        /**
         * Returns new unrestricted data access providers
         */
        fun create(context: Context): UnrestrictedDataAccessDelegate {
            return UnrestrictedDataAccessDelegate(context)
        }
    }
}
