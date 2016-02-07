package link.arata.android.mrconsumable.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class ImageUtil {
    private static final String LOG_TAG = "ImageUtil";

    @Nullable
    public static String uriToPath(@NonNull Context context, @NonNull Uri uri, boolean isChooser) {
        Cursor cursor;
        String path = null;
        if (isChooser && Build.VERSION.SDK_INT >= 19) {
            // KitKat以降はギャラリーから返されるURIが異なる
            String id = DocumentsContract.getDocumentId(uri);
            String selection = "_id=?";
            String[] selectionArgs = new String[]{id.split(":")[1]};
            cursor = context.getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[]{MediaStore.MediaColumns.DATA}, selection, selectionArgs, null);
        } else {
            ContentResolver contentResolver = context.getContentResolver();
            String[] columns = {MediaStore.Images.Media.DATA};
            cursor = contentResolver.query(uri, columns, null, null, null);
        }
        if (cursor.moveToFirst()) {
            path = cursor.getString(0);
            Log.d(LOG_TAG, "getPath:path:" + path);
        }

        cursor.close();
        return path;
    }
}
