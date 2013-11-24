package com.novoda.notils.cursor;

import android.database.Cursor;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;

public class SubCursorList<T> extends SimpleCursorList {
    private int start;
    private int end;

    public SubCursorList(Cursor cursor, CursorMarshaller<T> marshaller, int offset) {
        this(cursor, marshaller, offset, Integer.MAX_VALUE);
    }

    public SubCursorList(Cursor cursor, CursorMarshaller<T> marshaller, int start, int end) {
        super(cursor, marshaller);
        this.start = start;
        this.end = (end < super.size()) ? end : super.size();
        if (start < 0) {
            throw new CursorListException("start: " + start + ", end: " + end);
        }
        if (start > end) {
            throw new CursorListException("start: " + start + " > end: " + end);
        }
    }

    @Override
    public T get(int index) {
        int i = index + start;
        if (i > end) {
            throw new CursorListException("CursorList tries to access data at index " + index);
        }
        return (T) super.get(i);
    }

    @Override
    public int size() {
        return (end - start);
    }

    private static final String TAG = "CursorList";
    private static final boolean DEBUG = false;

    public static void logD(Object... arr) {
        if (!DEBUG) return;
        android.util.Log.d(TAG, java.util.Arrays.deepToString(arr));
    }

    public static void logE(Object... arr) {
        android.util.Log.e(TAG, java.util.Arrays.deepToString(arr));
    }
}
