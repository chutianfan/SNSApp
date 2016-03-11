package com.gitrose.mobile.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class RMprovider {
    public static final String AUTHORITY = "com.gitrose.mobile";

    public static final class PersonColumns implements BaseColumns {
        public static final String CONTENT = "content ";
        public static final Uri CONTENT_URI;
        public static final String SENDER_ID = "sender_id ";
        public static final String TABLE_NAME = "RCT_MESSAGE";
        public static final String TARGET_ID = "target_id ";

        static {
            CONTENT_URI = Uri.parse("content://com.gitrose.mobile/files/RCT_MESSAGE");
        }
    }
}
