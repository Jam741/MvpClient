package com.rx.android.jamspeedlibrary.utils;

/**
 * Created by Jam on 2016/3/9.
 * 验证对象是否为空
 */
public final class Preconditions {

    private Preconditions() {
    }

    /**
     * 确保引用对象不为空
     *
     * @param reference 需要验证的引用
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static <T> T checkNotNull(T reference, String mesage) {
        if (reference == null) {
            throw new NullPointerException(mesage);
        }
        return reference;
    }
}
