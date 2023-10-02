package com.example.eproject4.DTO;

import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class MatchDTO {
    private int id;
    private Date date;
    private Time time;
    private String stadium;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;

    public MatchDTO(int id, Date date, Time time, String stadium, Integer status, Timestamp created_at, Timestamp updated_at) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.stadium = stadium;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    // Các getter và setter cho các thuộc tính

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
