package com.viuniteam.socialviuni.utils;

public class ShortContent {
    public static String convertToShortContent(String content){
        return content.length() > 30 ? content.substring(0,30)+"..." : content;
    }
}
