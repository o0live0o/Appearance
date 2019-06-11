package com.o0live0o.app.appearance.bean;

import com.o0live0o.app.appearance.enums.CheckState;

public class ExteriorBean {

    private String itemName;
    private int itemId;
    private CheckState itemState;

    public ExteriorBean(int itemId,String itemName){
        this.itemName = itemName;
        this.itemId = itemId;
        itemState = CheckState.NOJUDGE;
    }

    public ExteriorBean(int itemId,String itemName,CheckState state){
        this.itemName = itemName;
        this.itemId = itemId;
        itemState = state;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public CheckState getItemState() {
        return itemState;
    }

    public void setItemState(CheckState itemState) {
        this.itemState = itemState;
    }
}
