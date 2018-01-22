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
 * Requests a permission group
 */
internal class PermissionGroupRequest private constructor(
    private val activity: Activity,
    private val rxActivityResult: RxActivityResult,
    private val permissionGroup: PermissionGroup
) {

    private fun request(): Single<Boolean> {
        return Single.create { e ->
            if (permissionGroup.granted()) {
                // all permissions are granted so emit just true
                e.onSuccess(true)
            } else {
                // we have to request some permissions

                // create dialog
                val dialogBuilder = RxMaterialDialogs.customListDialog<PermissionListItem>(activity)
                dialogBuilder.cancelable(false)
                dialogBuilder.negativeText(RxSpecialPermissions.getConfig().denyText)
                if (permissionGroup.title != null) {
                    dialogBuilder.title(permissionGroup.title!!)
                }
                if (permissionGroup.desc != null) {
                    dialogBuilder.content(permissionGroup.desc!!)
                }

                // add permissions
                for (permission in permissionGroup.permissions) {
                    if (permission.granted()) continue // ignore granted permissions
                    dialogBuilder.addItem(PermissionListItem(permission))
                }

                // pass the result to our observer
                // pass errors to our observer
                val disposable = dialogBuilder.build()
                    .map { it.item }
                    .map { it.model }
                    .flatMapSingleElement { permission ->
                        // now we need to request the permission
                        rxActivityResult
                            .start(permission.intent)
                            .toSingle()
                            .flatMap { request() }
                    }
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
        CustomModelListItem<Permission, PermissionListItem.ViewHolder>(permission) {

        override fun getLayoutRes(): Int {
            return R.layout.item_permission
        }

        override fun createViewHolder(view: View): ViewHolder {
            return ViewHolder(view)
        }

        override fun bind(holder: ViewHolder) {
            holder.icon.setImageDrawable(model.icon)
            holder.title.text = model.title
            holder.desc.text = model.desc
        }

        override fun unbind(holder: ViewHolder) {
            holder.icon.setImageDrawable(null)
            holder.title.text = null
            holder.desc.text = null
        }

        internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val icon = itemView.findViewById<ImageView>(R.id.permission_icon)
            val title: TextView = itemView.findViewById<TextView>(R.id.permission_title)
            val desc: TextView = itemView.findViewById<TextView>(R.id.permission_desc)
        }
    }

    companion object {

        fun create(
            activity: Activity,
            permissionGroup: PermissionGroup
        ): Single<Boolean> {
            return PermissionGroupRequest(
                activity, RxActivityResult(activity),
                permissionGroup
            ).request()
        }
    }
}
