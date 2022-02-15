package com.leohearts.rolehook;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;
import java.util.List;

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
        Log.i(TAG, "loaded");
        Class toHook = XposedHelpers.findClass("com.android.permissioncontroller.role.model.Role", lpparam.classLoader);
        Log.i(TAG, toHook.toString());
        XposedBridge.hookAllConstructors(toHook, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "Hooked");
                param.args[1] = true;
                param.args[16] = false;
                param.args[17] = false;
                param.args[18] = true;
            }
        });
    }
}
