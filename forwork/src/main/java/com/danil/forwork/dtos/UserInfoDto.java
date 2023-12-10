package com.danil.forwork.dtos;

public class UserInfoDto {
    private String fullname;
    private String phone;
    private Long date;

    public UserInfoDto() {
    }

    public UserInfoDto(String fullname, String phone, Long date) {
        this.fullname = fullname;
        this.phone = phone;
        this.date = date;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
