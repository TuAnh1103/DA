package com.viuniteam.socialviuni.utils;

import java.util.Collections;
import java.util.List;

public final class ListUtils {
    private ListUtils(){}

    public static <T> T getFirst(List<T> list){
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    public static <T> T getLast(List<T> list){
        return list != null && !list.isEmpty() ? list.get(list.size()-1) : null;
    }
    public static <E> List<E> oneToList(E e){
        return e != null ? Collections.singletonList(e) : null;
    }
}
