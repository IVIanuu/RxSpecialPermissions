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

package com.ivianuu.rxspecialpermissions.permissionrequest;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs;
import com.ivianuu.rxmaterialdialogs.singlebutton.SingleButtonDialogBuilder;
import com.ivianuu.rxmaterialdialogs.singlebutton.SingleButtonDialogEvent;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import rx_activity_result2.Result;
import rx_activity_result2.RxActivityResult;

/**
 * Requests a single permission
 */
final class SinglePermissionRequest implements PermissionRequest {

    private final SinglePermissionRequestBuilder requestBuilder;

    SinglePermissionRequest(@NonNull SinglePermissionRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    @NonNull
    @Override
    public Single<Boolean> request() {
        if (requestBuilder.permission.granted()) {
            // permission is granted so just return true
            return Single.just(true);
        } else {
            // build dialog
            SingleButtonDialogBuilder dialogBuilder
                    = RxMaterialDialogs.singleButtonDialog(requestBuilder.activity);

            dialogBuilder.cancelable(requestBuilder.cancelable);
            dialogBuilder.title(requestBuilder.permission.getTitle());

            if (requestBuilder.permission.getDescription() != null) {
                dialogBuilder.content(requestBuilder.permission.getDescription());
            }

            if (requestBuilder.permission.getIcon() != null) {
                dialogBuilder.icon(requestBuilder.permission.getIcon());
            }

            dialogBuilder.positiveText(requestBuilder.positiveText);
            dialogBuilder.negativeText(requestBuilder.negativeText);

            return dialogBuilder.build()
                    .map(new Function<SingleButtonDialogEvent, DialogAction>() {
                        @Override
                        public DialogAction apply(SingleButtonDialogEvent event) throws Exception {
                            return event.getWhich(); // get pressed button
                        }
                    })
                    .flatMapSingle(new Function<DialogAction, SingleSource<Boolean>>() {
                        @Override
                        public SingleSource<Boolean> apply(final DialogAction action) throws Exception {
                            if (action == DialogAction.NEGATIVE) {
                                // user clicked the cancel button
                                return Single.just(false);
                            } else {
                                // request the permission
                                return RxActivityResult.on(requestBuilder.activity)
                                        .startIntent(requestBuilder.permission.getIntent())
                                        .take(1)
                                        .singleOrError()
                                        .map(new Function<Result<Activity>, Boolean>() {
                                            @Override
                                            public Boolean apply(Result<Activity> result) throws Exception {
                                                // map to the current granted state
                                                return requestBuilder.permission.granted();
                                            }
                                        });

                            }
                        }
                    });
        }
    }
}
