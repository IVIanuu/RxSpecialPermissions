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

package com.ivianuu.rxspecialpermissions.permission;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs;
import com.ivianuu.rxmaterialdialogs.singlebutton.SingleButtonDialogBuilder;
import com.ivianuu.rxmaterialdialogs.singlebutton.SingleButtonDialogEvent;
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions;

import io.reactivex.Single;
import rx_activity_result2.RxActivityResult;

/**
 * Requests a single permission
 */
final class SinglePermissionRequest {

    private final Activity activity;
    private final Permission permission;
    
    private SinglePermissionRequest(Activity activity,
                                    Permission permission) {
        this.activity = activity;
        this.permission = permission;
    }

    /**
     * Emits the result of the request
     */
    @CheckResult @NonNull
    static Single<Boolean> create(@NonNull Activity activity, @NonNull Permission permission) {
        return new SinglePermissionRequest(activity, permission).request();
    }

    private Single<Boolean> request() {
        if (permission.granted()) {
            // permission is granted so just return true
            return Single.just(true);
        } else {
            // build dialog
            SingleButtonDialogBuilder dialogBuilder
                    = RxMaterialDialogs.singleButtonDialog(activity);

            dialogBuilder.cancelable(RxSpecialPermissions.getConfig().areDialogsCancelable());
            dialogBuilder.title(permission.getTitle());

            if (permission.getDescription() != null) {
                dialogBuilder.content(permission.getDescription());
            }

            if (permission.getIcon() != null) {
                dialogBuilder.icon(permission.getIcon());
            }

            dialogBuilder.positiveText(RxSpecialPermissions.getConfig().getGrantText());
            dialogBuilder.negativeText(RxSpecialPermissions.getConfig().getDenyText());

            return dialogBuilder.build()
                    .map(SingleButtonDialogEvent::getWhich) // get pressed button
                    .flatMapSingle(action -> {
                        if (action == DialogAction.NEGATIVE) {
                            // user clicked the cancel button
                            return Single.just(false);
                        } else {
                            // request the permission
                            return RxActivityResult.on(activity)
                                    .startIntent(permission.getIntent())
                                    .take(1)
                                    .singleOrError()
                                    .map(result -> {
                                        // map to the current granted state
                                        return permission.granted();
                                    });

                        }
                    });
        }
    }
}
