package com.ivianuu.rxspecialpermissions.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ivianuu.rxspecialpermissions.permission.PermissionBuilder
import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroupBuilder
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessibilityPermission = PermissionBuilder
            .accessibility(this, DummyAccessibilityService::class.java)
            .title("Accessibility service")
            .desc("We need this permission to read your passwords")
            .icon(R.mipmap.ic_launcher)
            .build()

        val notificationPermission = PermissionBuilder
            .notificationListener(this, DummyNotificationListener::class.java)
            .title("Notification listener")
            .desc("We need this permission to read and report your notifications to the nsa")
            .icon(R.mipmap.ic_launcher)
            .build()

        val systemOverlayPermission = PermissionBuilder
            .systemOverlay(this)
            .title("System overlay")
            .desc("We need this permission to block all your touches")
            .icon(R.mipmap.ic_launcher)
            .build()

        val permissionGroup = PermissionGroupBuilder
            .newBuilder(this)
            .title("Required Permissions")
            .addPermission(accessibilityPermission)
            .addPermission(notificationPermission)
            .addPermission(systemOverlayPermission)
            .build()

        compositeDisposable.add(
            permissionGroup.request(this)
                .subscribe { granted ->
                    Toast.makeText(
                        this@MainActivity,
                        "has permissions ? " + granted!!,
                        Toast.LENGTH_SHORT).show()
                }
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
