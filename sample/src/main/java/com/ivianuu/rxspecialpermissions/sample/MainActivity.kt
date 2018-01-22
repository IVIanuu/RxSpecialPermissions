package com.ivianuu.rxspecialpermissions.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.ivianuu.rxspecialpermissions.RxSpecialPermissions
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // normally you would do this in your applications oncreate method
        RxSpecialPermissions.init(application)

        // optional set the config
        val config = RxSpecialPermissions.configBuilder(this)
            .grantText("Grant")
            .denyText("Deny")
            .cancelableDialogs(false)
            .build()
        RxSpecialPermissions.setConfig(config)

        val accessibilityPermission = RxSpecialPermissions.permissionBuilder(this)
            .accessibilityService(DummyAccessibilityService::class.java)
            .title("Accessibility service")
            .desc("We need this permission to read your passwords")
            .iconRes(R.mipmap.ic_launcher)
            .build()

        val installUnknownAppsPermission = RxSpecialPermissions.permissionBuilder(this)
            .installUnknownApps()
            .title("Install unknown apps")
            .desc("We need this permission to install a virus on your phone")
            .iconRes(R.mipmap.ic_launcher)
            .build()

        val notificationPermission = RxSpecialPermissions.permissionBuilder(this)
            .notificationListener(DummyNotificationListener::class.java)
            .title("Notification listener")
            .desc("We need this permission to read and report your notifications to the nsa")
            .iconRes(R.mipmap.ic_launcher)
            .build()

        val systemOverlayPermission = RxSpecialPermissions.permissionBuilder(this)
            .systemOverlay()
            .title("System overlay")
            .desc("We need this permission to block all your touches")
            .iconRes(R.mipmap.ic_launcher)
            .build()

        val permissionGroup = RxSpecialPermissions.permissionGroupBuilder(this)
            .title("Required Permissions")
            .addPermission(accessibilityPermission)
            .addPermission(installUnknownAppsPermission)
            .addPermission(notificationPermission)
            .addPermission(systemOverlayPermission)
            .build()

        val disposable = permissionGroup.request(this)
            .subscribe { granted ->
                Toast.makeText(
                    this@MainActivity,
                    "has permissions ? " + granted!!,
                    Toast.LENGTH_SHORT
                ).show()
            }
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
