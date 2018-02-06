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
import com.afollestad.materialdialogs.DialogAction
import com.ivianuu.rxactivityresult.RxActivityResult
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions
import io.reactivex.Single

/**
 * Requests a single permission
 */
internal class SinglePermissionRequest constructor(
    private val activity: Activity,
    private val permission: Permission
) {

    internal fun request(): Single<Boolean> {
        if (permission.granted()) {
            // permission is granted so just return true
            return Single.just(true)
        } else {
            // build dialog
            val dialogBuilder = RxMaterialDialogs.singleButtonDialog(activity)

            dialogBuilder.cancelable(false)
            dialogBuilder.title(permission.title)
            permission.desc?.let { dialogBuilder.content(it) }
            permission.icon?.let { dialogBuilder.icon(it) }

            val config = RxSpecialPermissions.getOrCreateConfig(activity)
            dialogBuilder.positiveText(config.positiveText)
            dialogBuilder.negativeText(config.negativeText)

            return dialogBuilder.build()
                .map { it.which }
                .flatMapSingle { action ->
                    if (action == DialogAction.NEGATIVE) {
                        // user clicked the cancel button
                        Single.just(false)
                    } else {
                        // request the permission
                        RxActivityResult.get(activity)
                            .start(permission.intent)
                            .map{ permission.granted() }
                            .defaultIfEmpty(false)
                            .toSingle()
                    }
                }
        }
    }
}
