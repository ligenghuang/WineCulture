package com.zhifeng.wineculture.modules;

public class ScreenDto {
    private String title;
    private boolean isClick;

    public ScreenDto(String title, boolean isClick) {
        this.title = title;
        this.isClick = isClick;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
