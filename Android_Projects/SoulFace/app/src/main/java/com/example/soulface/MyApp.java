package com.example.soulface;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * Created by Yura Vyrovoy on 10/13/2017.
 */

public class MyApp extends Application {

    private static MyApp mInstance = null;

    private Bitmap mBitmapToEdit = null;
    private Bitmap mBitmapLeft = null;
    private Bitmap mBitmapRight = null;

    private String mPathPhotos;

    @Override
    public void onCreate() {
        super.onCreate();
        createFolder();

        mInstance = this;
    }

    public static MyApp getInstance() {
        DebugLogger.d();
        return mInstance;
    }

    public static void setBitmapToEdit(Bitmap bitmapToEdit) {
        DebugLogger.d();

        if (mInstance == null) {
            return;
        }
        mInstance.mBitmapToEdit = bitmapToEdit;
    }
    public static Bitmap getBitmapToEdit() {
        DebugLogger.d();

        if (mInstance == null) {
            return null;
        }
        return mInstance.mBitmapToEdit;
    }

    public static void setBitmapLeft(Bitmap bitmapToEdit) {
        DebugLogger.d();

        if (mInstance == null) {
            return;
        }
        mInstance.mBitmapLeft = bitmapToEdit;
    }

    public static Bitmap getBitmapLeft() {
        DebugLogger.d();

        if (mInstance == null) {
            return null;
        }
        return mInstance.mBitmapLeft;
    }

    public static void setBitmapRight(Bitmap bitmapToEdit) {
        DebugLogger.d();

        if (mInstance == null) {
            return;
        }
        mInstance.mBitmapRight = bitmapToEdit;
    }

    public static Bitmap getBitmapRight() {
        DebugLogger.d();

        if (mInstance == null) {
            return null;
        }
        return mInstance.mBitmapRight;
    }

    public static Bitmap getVrModeBitmap(boolean bAddCaptures){
        DebugLogger.d();

        if (mInstance == null || mInstance.mBitmapLeft == null || mInstance.mBitmapRight == null) {
            return null;
        }

        if (!bAddCaptures) {
            return BitmapUtils.compileVrModeBitmap(mInstance.mBitmapLeft, mInstance.mBitmapRight,
                                                            null, null);
        } else {

            Bitmap bmpCaptionLeft = BitmapFactory.decodeResource(mInstance.getResources(), R.drawable.ic_soul_vr_mode);
            Bitmap bmpCaptionRight = BitmapFactory.decodeResource(mInstance.getResources(), R.drawable.ic_face_vr_mode);
            return BitmapUtils.compileVrModeBitmap(mInstance.mBitmapLeft, mInstance.mBitmapRight,
                    bmpCaptionLeft, bmpCaptionRight);
        }
    }

    public static Bitmap getSingleResultBitmap() {
        DebugLogger.d();

        if (mInstance == null || mInstance.mBitmapLeft == null || mInstance.mBitmapRight == null) {
            return null;
        }
        return BitmapUtils.compileOvelayedImage(mInstance.mBitmapLeft, mInstance.mBitmapRight);
    }

    private void createFolder() {
        DebugLogger.d();

        mPathPhotos = "";
        try {
            File folder = new File(Environment.getExternalStorageDirectory(), getApplicationContext().getString(R.string.app_name));
            folder.mkdir();
            mPathPhotos = folder.getAbsolutePath();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public String getPhotosPath() {
        DebugLogger.d();

        return mPathPhotos;
    }
}
