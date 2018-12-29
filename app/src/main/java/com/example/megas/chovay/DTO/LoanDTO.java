package com.example.megas.chovay.DTO;

public class LoanDTO {
    int id;
    int peopleId;
    int amount;
    int status;

    String CreatedTime;
    String note;

    public LoanDTO(int id, int peopleId, int amount, int status, String createdTime, String note) {
        this.id = id;
        this.peopleId = peopleId;
        this.amount = amount;
        this.status = status;
        CreatedTime = createdTime;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(int peopleId) {
        this.peopleId = peopleId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
