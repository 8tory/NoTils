package com.novoda.notils.cursor;

import java.util.List;

import android.database.Cursor;

public class SmartCursorList<T> extends SimpleCursorList<T> {
    public interface OnCloseListener {
        public void onClose();
        public void onCloseFinished();
        public static OnCloseListener NULL = new OnCloseListener() {
            @Override
            public void onClose() {
            }
            @Override
            public void onCloseFinished() {
            }
        };
    }

    private OnCloseListener mCloseListener = OnCloseListener.NULL;

    public SmartCursorList(Cursor cursor, CursorMarshaller<T> marshaller) {
        this(cursor, marshaller, null);
    }

    public SmartCursorList(Cursor cursor, CursorMarshaller<T> marshaller, OnCloseListener listener) {
        super(cursor, marshaller);
        setOnCloseListener(listener);
    }

    private void _close() {
        if (!isClosed()) {
            mCloseListener.onClose();
            close();
            mCloseListener.onCloseFinished();
        }
    }

    public void setOnCloseListener(OnCloseListener listener) {
        if (listener == null) return;
        mCloseListener = listener;
    }

    @Override
    protected void finalize() throws Throwable {
        _close();
        super.finalize();
    }

    public static <T> void close(List<T> list) {
        if (!(list instanceof SmartCursorList))
            return;

        ((SmartCursorList<T>)list)._close();
    }
}
