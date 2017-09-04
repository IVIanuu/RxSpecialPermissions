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
import android.graphics.Color;
import android.support.annotation.NonNull;

import com.ivianuu.rxmaterialdialogscommons.RxMaterialDialogsCommons;
import com.ivianuu.rxmaterialdialogscommons.listmaterial.MaterialListDialogBuilder;
import com.ivianuu.rxmaterialdialogscommons.listmaterial.MaterialListDialogEvent;
import com.ivianuu.rxmaterialdialogscommons.listmaterial.MaterialListItem;
import com.ivianuu.rxspecialpermissions.permission.Permission;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import rx_activity_result2.Result;
import rx_activity_result2.RxActivityResult;

/**
 * Requests a permission group
 */
final class PermissionGroupRequest implements PermissionRequest {

    private final PermissionGroupRequestBuilder requestBuilder;

    PermissionGroupRequest(@NonNull PermissionGroupRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    @NonNull
    @Override
    public Single<Boolean> request() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final SingleEmitter<Boolean> e) throws Exception {
                if (granted()) {
                    // all permissions are granted so emit just true
                    e.onSuccess(true);
                } else {
                    // we have to request some permissions

                    // create dialog
                    MaterialListDialogBuilder dialogBuilder
                            = RxMaterialDialogsCommons.materialListDialog(requestBuilder.activity);
                    dialogBuilder.cancelable(requestBuilder.cancelable);
                    dialogBuilder.negativeText(requestBuilder.negativeText);
                    dialogBuilder.title(requestBuilder.title);
                    dialogBuilder.content(requestBuilder.content);

                    // add permissions
                    for (Permission permission : requestBuilder.permissionGroup.getPermissions()) {
                        if (permission.granted()) continue; // ignore granted permissions
                        dialogBuilder.addItem(permissionToListItem(permission));
                    }

                    final Disposable disposable = dialogBuilder.build()
                            .map(new Function<MaterialListDialogEvent, MaterialListItem>() {
                                @Override
                                public MaterialListItem apply(MaterialListDialogEvent materialListDialogEvent) throws Exception {
                                    // get the item
                                    return materialListDialogEvent.getItem();
                                }
                            })
                            .map(new Function<MaterialListItem, Permission>() {
                                @Override
                                public Permission apply(MaterialListItem materialListItem) throws Exception {
                                    // get the permission
                                    return (Permission) materialListItem.getTag();
                                }
                            })
                            .flatMapSingleElement(new Function<Permission, SingleSource<Boolean>>() {
                                @Override
                                public SingleSource<Boolean> apply(Permission permission) throws Exception {
                                    // now we need to request the permission
                                    return RxActivityResult.on(requestBuilder.activity)
                                            .startIntent(permission.getIntent())
                                            .take(1)
                                            .singleOrError()
                                            .flatMap(new Function<Result<Activity>, SingleSource<Boolean>>() {
                                                @Override
                                                public SingleSource<Boolean> apply(Result<Activity> result) throws Exception {
                                                    // now we run the request again to do a loop
                                                    return request();
                                                }
                                            });
                                }
                            })
                            .subscribe(new Consumer<Boolean>() {
                                @Override
                                public void accept(Boolean granted) throws Exception {
                                    // pass the result to our observer
                                    if (!e.isDisposed()) {
                                        e.onSuccess(granted);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    // pass errors to our observer
                                    if (!e.isDisposed()) {
                                        e.onError(throwable);
                                    }
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {
                                    // empty user cancelled
                                    // this means the permissions were not granted
                                    if (!e.isDisposed()) {
                                        e.onSuccess(false);
                                    }
                                }
                            });

                    // dispose the dialogs on dispose
                    e.setDisposable(new Disposable() {
                        private boolean disposed;
                        @Override
                        public void dispose() {
                            if (!disposed) {
                                disposed = true;
                                disposable.dispose();
                            }
                        }

                        @Override
                        public boolean isDisposed() {
                            return disposed;
                        }
                    });
                }
            }
        });
    }

    private MaterialListItem permissionToListItem(Permission permission) {
        return new MaterialListItem.Builder(requestBuilder.activity)
                .title(permission.getTitle())
                .text(permission.getDescription())
                .icon(permission.getIcon())
                .tag(permission)
                .backgroundColor(Color.TRANSPARENT)
                .build();
    }

    private boolean granted() {
        for (Permission permission : requestBuilder.permissionGroup.getPermissions()) {
            if (!permission.granted()) {
                return false;
            }
        }

        return true;
    }

}
