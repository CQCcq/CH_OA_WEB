package com.user;

//import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfo {
    private Integer ID;
    private String Name;
    private Integer Gender;
    private Integer Mobile;
    private String Email;
    private String Address;
    private Timestamp StartTime = new Timestamp(new Date().getTime());
    private String Describe;
    private  Integer Status;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getGender() {
        return Gender;
    }

    public void setGender(Integer gender) {
        Gender = gender;
    }

    public Integer getMobile() {
        return Mobile;
    }

    public void setMobile(Integer mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Timestamp getStartTime() {
        return StartTime;
    }

    public void setStartTime(Timestamp startTime) {
        StartTime = startTime;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Gender=" + Gender +
                ", Mobile=" + Mobile +
                ", Email='" + Email + '\'' +
                ", Address='" + Address + '\'' +
                ", StartTime=" + StartTime +
                ", Describe='" + Describe + '\'' +
                ", Status=" + Status +
                '}';
    }
}
