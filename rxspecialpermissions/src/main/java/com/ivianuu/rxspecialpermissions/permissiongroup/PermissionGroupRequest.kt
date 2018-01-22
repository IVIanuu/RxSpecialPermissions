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

package com.ivianuu.rxspecialpermissions.permissiongroup

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ivianuu.rxactivityresult.RxActivityResult
import com.ivianuu.rxmaterialdialogs.RxMaterialDialogs
import com.ivianuu.rxmaterialdialogs.listcustom.CustomModelListItem
import com.ivianuu.rxspecialpermissions.R
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions
import com.ivianuu.rxspecialpermissions.permission.Permission
import io.reactivex.Single

/**
 * Requests permissions for a [PermissionGroup]
 */
internal class PermissionGroupRequest constructor(
    private val activity: Activity,
    private val permissionGroup: PermissionGroup
) {

    fun request(): Single<Boolean> {
        return Single.create { e ->
            if (permissionGroup.granted()) {
                // all permissions are granted so emit just true
                e.onSuccess(true)
            } else {
                val config = RxSpecialPermissions.getOrCreateConfig(activity)

                // create dialog
                val dialogBuilder = RxMaterialDialogs.customListDialog<PermissionListItem>(activity)
                dialogBuilder.cancelable(false)
                dialogBuilder.negativeText(config.negativeText)

                permissionGroup.title?.let { dialogBuilder.title(it) }
                permissionGroup.desc?.let { dialogBuilder.content(it) }

                // add permissions
                permissionGroup.permissions
                    .filterNot { it.granted() }
                    .map { PermissionListItem(it) }
                    .forEach { dialogBuilder.addItem(it) }

                // pass the result to our observer
                // pass errors to our observer
                val disposable = dialogBuilder.build()
                    .map { it.item }
                    .map { it.model }
                    .flatMapSingleElement { permission ->
                        // now we need to request the permission
                        RxActivityResult(activity)
                            .start(permission.intent)
                            .toSingle()
                            .flatMap { request() }
                    }
                    .defaultIfEmpty(false)
                    .subscribe(
                        { e.onSuccess(it) },
                        { e.onError(it) }
                    )

                // cancel the dialogs
                e.setCancellable { disposable.dispose() }
            }
        }
    }

    internal class PermissionListItem(permission: Permission) :
        CustomModelListItem<Permission, PermissionListItem.Holder>(permission) {

        override fun getLayoutRes(): Int = R.layout.item_permission

        override fun createViewHolder(view: View): Holder = Holder(view)

        override fun bind(holder: Holder) {
            with(holder) {
                icon.setImageDrawable(model.icon)
                title.text = model.title
                desc.text = model.desc
            }
        }

        override fun unbind(holder: Holder) {
            with(holder) {
                icon.setImageDrawable(null)
                title.text = null
                desc.text = null
            }
        }

        internal class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val icon: ImageView = itemView.findViewById(R.id.permission_icon)
            val title: TextView = itemView.findViewById(R.id.permission_title)
            val desc: TextView = itemView.findViewById(R.id.permission_desc)
        }
    }
}
