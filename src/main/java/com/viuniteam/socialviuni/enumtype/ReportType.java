package com.viuniteam.socialviuni.enumtype;

public enum ReportType {
    NUDE("Khỏa thân"),
    VIOLENCE("Bạo lực"),
    FALSE_INFORMATION("Thông tin sai sự thật"),
    SPAM("Spam"),
    TERRORISM("Khủng bố"),
    OTHER_PROBLEM("Vấn đề khác");

    private String type;

    ReportType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
