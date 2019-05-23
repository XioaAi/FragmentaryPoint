package com.zxd.xiaoabapp;

public class LevelHomeStatusBean {
    private int CurrentLevelIndex;
    private boolean CurrentLevel;
    private boolean VIPLevel;

    public int getCurrentLevelIndex() {
        return CurrentLevelIndex;
    }

    public void setCurrentLevelIndex(int currentLevelIndex) {
        CurrentLevelIndex = currentLevelIndex;
    }

    public boolean isCurrentLevel() {
        return CurrentLevel;
    }

    public void setCurrentLevel(boolean currentLevel) {
        CurrentLevel = currentLevel;
    }

    public boolean isVIPLevel() {
        return VIPLevel;
    }

    public void setVIPLevel(boolean VIPLevel) {
        this.VIPLevel = VIPLevel;
    }
}
