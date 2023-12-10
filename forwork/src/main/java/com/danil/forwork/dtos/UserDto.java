package com.danil.forwork.dtos;

public class UserDto {
    private String email;
    private String password;
    private String fullname;
    private String phone;
    private Long date;

    public UserDto() {

    }

    public UserDto(String email, String password, String fullname, String phone, Long date) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
