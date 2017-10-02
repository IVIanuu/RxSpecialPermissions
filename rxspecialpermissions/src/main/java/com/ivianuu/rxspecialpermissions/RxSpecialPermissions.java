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

package com.ivianuu.rxspecialpermissions;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.ivianuu.rxspecialpermissions.permissiongroup.PermissionGroupBuilder;

import rx_activity_result2.RxActivityResult;

import static com.ivianuu.rxspecialpermissions.Preconditions.checkNotNull;

/**
 * RxSpecialPermissions
 */
public final class RxSpecialPermissions {

    private static Config config;

    private RxSpecialPermissions() {
        // no instances
    }

    /**
     * This must be called before requesting permissions
     */
    public static void init(@NonNull Application application) {
        checkNotNull(application, "application == null");
        RxActivityResult.register(application);
        config = new Config.Builder(application)
                .denyTextRes(R.string.default_deny_text)
                .grantTextRes(R.string.default_grant_text)
                .build();
    }

    /**
     * Sets the config
     */
    public static void setConfig(@NonNull Config config) {
        checkNotNull(config, "config == null");
        RxSpecialPermissions.config = config;
    }

    /**
     * Returns the config
     */
    @NonNull
    public static Config getConfig() {
        return config;
    }

    /**
     * Returns a permissions builder
     */
    @NonNull
    public static PermissionBuilderChooser permissionBuilder(@NonNull Context context) {
        checkNotNull(context, "context == null");
        return new PermissionBuilderChooser(context);
    }

    /**
     * Returns a permission group builder
     */
    @NonNull
    public static PermissionGroupBuilder permissionGroupBuilder(@NonNull Context context) {
        checkNotNull(context, "context == null");
        return new PermissionGroupBuilder(context);
    }

    /**
     * Returns a config builder
     */
    @NonNull
    public static Config.Builder configBuilder(@NonNull Context context) {
        checkNotNull(context, "context == null");
        return new Config.Builder(context);
    }

    public static final class Config {

        private final String grantText;
        private final String denyText;
        private final boolean cancelableDialogs;

        private Config(String grantText, String denyText, boolean cancelableDialogs) {
            this.grantText = grantText;
            this.denyText = denyText;
            this.cancelableDialogs = cancelableDialogs;
        }

        /**
         * Returns the grant text of this config
         */
        @NonNull
        public String getGrantText() {
            return grantText;
        }

        /**
         * Returns the deny text of this config
         */
        @NonNull
        public String getDenyText() {
            return denyText;
        }

        /**
         * Returns whether the dialogs should cancel on outside click or not
         */
        public boolean areDialogsCancelable() {
            return cancelableDialogs;
        }

        public static class Builder {

            private final Context context;

            private String grantText;
            private String denyText;

            private boolean cancelableDialogs;

            private Builder(Context context) {
                this.context = context;
            }

            /**
             * Sets the grant text of this config
             */
            @NonNull
            public Builder grantTextRes(@StringRes int grantTextRes) {
                return grantText(context.getString(grantTextRes));
            }

            /**
             * Sets the grant text of this config
             */
            @NonNull
            public Builder grantText(@NonNull String grantText) {
                this.grantText = grantText;
                return this;
            }

            /**
             * Sets the deny text of this config
             */
            @NonNull
            public Builder denyTextRes(@StringRes int denyTextRes) {
                return denyText(context.getString(denyTextRes));
            }

            /**
             * Sets the deny text of this config
             */
            @NonNull
            public Builder denyText(@NonNull String denyText) {
                this.denyText = denyText;
                return this;
            }

            /**
             * Sets the dialogs cancelable
             */
            @NonNull
            public Builder cancelableDialogs(boolean cancelableDialogs) {
                this.cancelableDialogs = cancelableDialogs;
                return this;
            }

            /**
             * Returns a new config instance
             */
            @NonNull
            public Config build() {
                checkNotNull(grantText, "grantText == null");
                checkNotNull(denyText, "denyText == null");
                return new Config(grantText, denyText, cancelableDialogs);
            }
        }
    }
}
