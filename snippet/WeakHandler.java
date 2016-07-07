package com.miva.doorlock.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public abstract class WeakHandler<O> extends Handler {
    WeakReference<O> mOuter;

    public WeakHandler(O o) {
        mOuter = new WeakReference<>(o);
    }

    @Override
    public void handleMessage(Message msg) {
        O outer = mOuter.get();
        if (outer == null) {
            return;
        }

        handleMessage(msg, outer);
    }

    protected abstract void handleMessage(Message msg, O outer);
}