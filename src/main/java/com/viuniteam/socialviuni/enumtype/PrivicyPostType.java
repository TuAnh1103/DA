package com.viuniteam.socialviuni.enumtype;

public enum PrivicyPostType {
    ONLY_ME(3),
    FRIEND(2),
    PUBLIC(1);
    private int code;

    PrivicyPostType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
