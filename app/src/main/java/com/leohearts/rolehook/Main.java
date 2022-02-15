package com.leohearts.rolehook;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {
    public String TAG = "RoleHook";


    StringBuffer BytesToString(byte[] a){
        StringBuffer re = new StringBuffer();
        for (int i : a){
            re.append(i);
            re.append(' ');
        }
        return re;
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.parallel.space.pro")) {

            Log.i(TAG, "handleLoadPackage: Loaded com.google.android.apps.photos");
            XposedHelpers.findAndHookMethod("androidx.core.content.ContextCompat", lpparam.classLoader, "checkSelfPermission", Context.class, String.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return 0;
                }
            });
            XposedHelpers.findAndHookMethod("com.lbe.parallel.billing.f", lpparam.classLoader, "e", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return true;
                }
            });
        }
        if (lpparam.packageName.equals("com.ctf.play")) {
            Log.i(TAG, "handleLoadPackage: ctf");
            XposedHelpers.findAndHookMethod("android.util.Base64", lpparam.classLoader, "encodeToString", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.i(TAG, "beforeHookedMethod: " + ((byte[])param.args[0]).toString());
                }
            });
        }
    }
}
