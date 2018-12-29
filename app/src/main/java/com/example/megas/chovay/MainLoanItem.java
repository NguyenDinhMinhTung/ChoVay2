package com.example.megas.chovay;

public class MainLoanItem {
    int id;
    String name;
    int groupId;
    int amount;

    public MainLoanItem(int id, String name, int groupId, int amount) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
