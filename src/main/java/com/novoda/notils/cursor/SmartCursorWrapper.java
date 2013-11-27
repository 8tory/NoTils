package com.novoda.notils.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

public class SmartCursorWrapper extends CursorWrapper {
    SmartCursorWrapper(Cursor cursor) { super(cursor); }

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
}

