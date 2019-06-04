package com.o0live0o.app.appearance.bean;

import com.o0live0o.app.appearance.enums.CheckState;

public class ExteriorBean {

    private String itemName;
    private String itemId;
    private CheckState itemState;

    public ExteriorBean(String itemId,String itemName){
        this.itemName = itemName;
        this.itemId = itemId;
        itemState = CheckState.NOJUDGE;
    }

    public ExteriorBean(String itemId,String itemName,CheckState state){
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public CheckState getItemState() {
        return itemState;
    }

    public void setItemState(CheckState itemState) {
        this.itemState = itemState;
    }
}
