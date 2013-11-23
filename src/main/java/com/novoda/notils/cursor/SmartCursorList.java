package com.novoda.notils.cursor;

import java.util.List;

import android.database.Cursor;

public class SmartCursorList<T> extends SimpleCursorList<T> {

    public SmartCursorList(Cursor cursor, CursorMarshaller<T> marshaller) {
        super(cursor, marshaller);
    }

    private void _close() {
        if (!isClosed()) {
            close();
        }
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
