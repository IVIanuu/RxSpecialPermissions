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

package com.ivianuu.rxspecialpermissions.permissiongroup;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivianuu.rxactivityresult.RxActivityResult;
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomListDialogBuilder;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomListDialogEvent;
import com.ivianuu.rxmaterialdialogs.listcustom.CustomModelListItem;
import com.ivianuu.rxspecialpermissions.R;
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions;
import com.ivianuu.rxspecialpermissions.permission.Permission;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

/**
 * Requests a permission group
 */
final class PermissionGroupRequest {

    private final Activity activity;
    private final RxActivityResult rxActivityResult;
    private final PermissionGroup permissionGroup;
    
    private PermissionGroupRequest(Activity activity,
                                   RxActivityResult rxActivityResult,
                                   PermissionGroup permissionGroup) {
        this.activity = activity;
        this.rxActivityResult = rxActivityResult;
        this.permissionGroup = permissionGroup;
    }

    /**
     * Emits the result of the request
     */
    @CheckResult @NonNull
    static Single<Boolean> create(@NonNull Activity activity,
                                  @NonNull PermissionGroup permissionGroup) {
        return new PermissionGroupRequest(activity, new RxActivityResult(activity),
                permissionGroup).request();
    }
    
    private Single<Boolean> request() {
        return Single.create(e -> {
            if (permissionGroup.granted()) {
                // all permissions are granted so emit just true
                e.onSuccess(true);
            } else {
                // we have to request some permissions

                // create dialog
                CustomListDialogBuilder<PermissionListItem> dialogBuilder
                        = RxMaterialDialogs.customListDialog(activity);
                dialogBuilder.cancelable(RxSpecialPermissions.getConfig().areDialogsCancelable());
                dialogBuilder.negativeText(RxSpecialPermissions.getConfig().getDenyText());
                if (permissionGroup.getTitle() != null) {
                    dialogBuilder.title(permissionGroup.getTitle());
                }
                if (permissionGroup.getDescription() != null) {
                    dialogBuilder.content(permissionGroup.getDescription());
                }

                // add permissions
                for (Permission permission : permissionGroup.getPermissions()) {
                    if (permission.granted()) continue; // ignore granted permissions
                    dialogBuilder.addItem(new PermissionListItem(permission));
                }

                // pass the result to our observer
                // pass errors to our observer
                final Disposable disposable = dialogBuilder.build()
                        .map(CustomListDialogEvent::getItem)
                        .map(CustomModelListItem::getModel)
                        .flatMapSingleElement(permission -> {
                            // now we need to request the permission
                            return rxActivityResult
                                    .start(permission.getIntent())
                                    .toSingle()
                                    .flatMap(result -> {
                                        // now we run the request again to do a loop
                                        return request();
                                    });
                        })
                        .subscribe(e::onSuccess, e::onError, () -> {
                            e.onSuccess(false);
                        });

                // cancel the dialogs
                e.setCancellable(disposable::dispose);
            }
        });
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
