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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomListDialogBuilder;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomListDialogEvent;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomModelListItem;
import com.ivianuu.rxspecialpermissions.R;
import com.ivianuu.rxspecialpermissions.permission.Permission;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
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
        return Single.create(e -> {
            if (granted()) {
                // all permissions are granted so emit just true
                e.onSuccess(true);
            } else {
                // we have to request some permissions

                // create dialog
                CustomListDialogBuilder<PermissionListItem> dialogBuilder
                        = RxMaterialDialogs.customListDialog(requestBuilder.activity);
                dialogBuilder.cancelable(requestBuilder.cancelable);
                dialogBuilder.negativeText(requestBuilder.negativeText);
                dialogBuilder.title(requestBuilder.title);
                dialogBuilder.content(requestBuilder.content);

                // add permissions
                for (Permission permission : requestBuilder.permissionGroup.getPermissions()) {
                    if (permission.granted()) continue; // ignore granted permissions
                    dialogBuilder.addItem(new PermissionListItem(permission));
                }

                final Disposable disposable = dialogBuilder.build()
                        .map(CustomListDialogEvent::getItem)
                        .map(CustomModelListItem::getModel)
                        .flatMapSingleElement(permission -> {
                            // now we need to request the permission
                            return RxActivityResult.on(requestBuilder.activity)
                                    .startIntent(permission.getIntent())
                                    .take(1)
                                    .singleOrError()
                                    .flatMap(result -> {
                                        // now we run the request again to do a loop
                                        return request();
                                    });
                        })
                        .subscribe(granted -> {
                            // pass the result to our observer
                            if (!e.isDisposed()) {
                                e.onSuccess(granted);
                            }
                        }, throwable -> {
                            // pass errors to our observer
                            if (!e.isDisposed()) {
                                e.onError(throwable);
                            }
                        }, () -> {
                            // empty user cancelled
                            // this means the permissions were not granted
                            if (!e.isDisposed()) {
                                e.onSuccess(false);
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
        });
    }

    private boolean granted() {
        for (Permission permission : requestBuilder.permissionGroup.getPermissions()) {
            if (!permission.granted()) {
                return false;
            }
        }

        return true;
    }

    static class PermissionListItem extends CustomModelListItem<Permission, PermissionListItem.ViewHolder> {

        PermissionListItem(@NonNull Permission permission) {
            super(permission);
        }

        @Override
        public int getLayoutRes() {
            return R.layout.item_permission;
        }

        @NonNull
        @Override
        public ViewHolder createViewHolder(View view) {
            return new ViewHolder(view);
        }

        @Override
        public void bind(@NonNull ViewHolder holder) {
            holder.icon.setImageDrawable(getModel().getIcon());
            holder.title.setText(getModel().getTitle());
            holder.description.setText(getModel().getDescription());
        }

        @Override
        public void unbind(@NonNull ViewHolder holder) {
            holder.icon.setImageDrawable(null);
            holder.title.setText(null);
            holder.description.setText(null);
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final ImageView icon;
            private final TextView title;
            private final TextView description;
            ViewHolder(View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.permission_icon);
                title = itemView.findViewById(R.id.permission_title);
                description = itemView.findViewById(R.id.permission_description);
            }
        }
    }
}
