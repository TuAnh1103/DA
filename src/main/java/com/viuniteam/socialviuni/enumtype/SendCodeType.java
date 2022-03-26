package com.viuniteam.socialviuni.enumtype;

public enum SendCodeType {
    REGISTER("đăng ký"),
    RECOVERY("khôi phục mật khẩu"),
    CHANGEEMAIL("thay đổi email");
    private String name;

    SendCodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
