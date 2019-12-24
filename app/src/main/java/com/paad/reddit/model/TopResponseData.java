package com.paad.reddit.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TopResponseData {
    @SerializedName("children")
    private ArrayList<Children> childrenList;

    public ArrayList<Children> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(ArrayList<Children> childrenList) {
        this.childrenList = childrenList;
    }
}
