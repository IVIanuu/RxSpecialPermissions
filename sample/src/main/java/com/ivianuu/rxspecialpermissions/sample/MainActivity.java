package com.ivianuu.rxspecialpermissions.sample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.ivianuu.rxspecialpermissions.RxSpecialPermissions;
import com.ivianuu.rxspecialpermissions.permission.Permission;
import com.ivianuu.rxspecialpermissions.permission.RealPermission;
import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroup;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // normally you would do this in your applications oncreate method
        RxSpecialPermissions.init(getApplication());

        Permission accessibilityPermission = RxSpecialPermissions.permissionBuilder(this)
                .accessibilityService(DummyAccessibilityService.class)
                .title("Accessibility service")
                .description("We need this permission to read your passwords")
                .iconRes(R.mipmap.ic_launcher)
                .build();

        Permission notificationPermission = RxSpecialPermissions.permissionBuilder(this)
                .notificationListener()
                .title("Notification listener")
                .description("We need this permission to read and report your notifications to the nsa")
                .iconRes(R.mipmap.ic_launcher)
                .build();

        Permission systemOverlayPermission = RxSpecialPermissions.permissionBuilder(this)
                .systemOverlay()
                .title("System overlay")
                .description("We need this permission to block all your touches")
                .iconRes(R.mipmap.ic_launcher)
                .build();

        Permission customPermission = RxSpecialPermissions.permissionBuilder(this)
                .custom()
                .grantedProvider(new RealPermission.GrantedProvider() {
                    @Override
                    public boolean granted(@NonNull Context context) {
                        return Util.hasCustomPermission();
                    }
                })
                .intentProvider(new RealPermission.IntentProvider() {
                    @NonNull
                    @Override
                    public Intent getIntent(@NonNull Context context) {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(context, CustomPermissionActivity.class));
                        return intent;
                    }
                })
                .title("Custom permission")
                .description("Some super fancy custom permission")
                .iconRes(R.mipmap.ic_launcher)
                .build();

        PermissionGroup permissionGroup = RxSpecialPermissions.permissionGroupBuilder()
                .add(accessibilityPermission)
                .add(notificationPermission)
                .add(systemOverlayPermission)
                .add(customPermission)
                .build();

        Disposable disposable = RxSpecialPermissions.requestBuilder(this)
                .permissionGroup(permissionGroup)
                .title("Required Permissions")
                .negativeText("Cancel")
                .request()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        Toast.makeText(MainActivity.this, "has permissions ? " + granted, Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
