package com.novoda.notils.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

public class SmartCursorWrapper extends CursorWrapper {
    private OnCloseListener mCloseListener = OnCloseListener.NULL;

    public interface OnCloseListener {
        public void onClose();
        public void onCloseFinished();

        public static final OnCloseListener NULL = new OnCloseListener() {
            @Override
            public void onClose() {
            }

            @Override
            public void onCloseFinished() {
            }
        };
    }

    public SmartCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    private void _close() {
        if (!isClosed()) {
            mCloseListener.onClose();
            close();
            mCloseListener.onCloseFinished();
        }
    }

    public void setOnCloseListener(OnCloseListener listener) {
        if (listener == null) {
            mCloseListener = OnCloseListener.NULL;
        } else {
            mCloseListener = listener;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        _close();
        super.finalize();
    }
}

