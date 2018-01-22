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
import android.support.annotation.CheckResult
import com.afollestad.materialdialogs.DialogAction
import com.ivianuu.rxactivityresult.RxActivityResult
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions
import io.reactivex.Single

/**
 * Requests a single permission
 */
internal class SinglePermissionRequest private constructor(
    private val activity: Activity,
    private val permission: Permission
) {

    private fun request(): Single<Boolean> {
        if (permission.granted()) {
            // permission is granted so just return true
            return Single.just(true)
        } else {
            // build dialog
            val dialogBuilder = RxMaterialDialogs.singleButtonDialog(activity)

            dialogBuilder.cancelable(false)
            dialogBuilder.title(permission.title)

            if (permission.desc != null) {
                dialogBuilder.content(permission.desc!!)
            }

            if (permission.icon != null) {
                dialogBuilder.icon(permission.icon!!)
            }

            dialogBuilder.positiveText(RxSpecialPermissions.getConfig().grantText)
            dialogBuilder.negativeText(RxSpecialPermissions.getConfig().denyText)

            return dialogBuilder.build()
                .map { it.which } // get pressed button
                .flatMapSingle { action ->
                    if (action == DialogAction.NEGATIVE) {
                        // user clicked the cancel button
                        Single.just(false)
                    } else {
                        // request the permission
                        RxActivityResult(activity)
                            .start(permission.intent)
                            .map{ permission.granted() }
                            .defaultIfEmpty(false)
                            .toSingle()
                    }
                }
        }
    }

    companion object {
        /**
         * Emits the result of the request
         */
        @CheckResult
        fun create(activity: Activity, permission: Permission): Single<Boolean> {
            return SinglePermissionRequest(activity, permission).request()
        }
    }
}
