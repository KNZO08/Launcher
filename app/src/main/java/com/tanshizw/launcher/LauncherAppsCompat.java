package com.tanshizw.launcher;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by archermind on 6/13/16.
 */
public class LauncherAppsCompat {
    private PackageManager mPm;
    private Context mContext;
    private static LauncherAppsCompat sInstance;
    private static Object sInstanceLock = new Object();

    LauncherAppsCompat(Context context) {
        mPm = context.getPackageManager();
        mContext = context;
    }

    public static LauncherAppsCompat getInstance(Context context) {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new LauncherAppsCompat(context.getApplicationContext());
            }
            return sInstance;
        }
    }

    public List<LauncherActivityInfoCompat> getActivityList(String packageName) {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mainIntent.setPackage(packageName);
        List<ResolveInfo> infos = mPm.queryIntentActivities(mainIntent, 0);
        List<LauncherActivityInfoCompat> list =
                new ArrayList<LauncherActivityInfoCompat>(infos.size());
        for (ResolveInfo info : infos) {
            list.add(new LauncherActivityInfoCompat(mContext, info));
        }
        return list;
    }

    public LauncherActivityInfoCompat resolveActivity(Intent intent) {
        ResolveInfo info = mPm.resolveActivity(intent, 0);
        if (info != null) {
            return new LauncherActivityInfoCompat(mContext, info);
        }
        return null;
    }
}
