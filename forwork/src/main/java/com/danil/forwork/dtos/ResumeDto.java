package com.danil.forwork.dtos;

import com.danil.forwork.Entities.User;

public class ResumeDto {
    User owner;
    String post;
    String fullname;
    String photoImage64;
    String phone;
    String sex;
    String age;
    String experience;
    String city;
    String additional;
    String personalQualities;




    public ResumeDto() {
    }

    public ResumeDto(User owner, String post, String fullname, String photoImage64, String phone, String sex, String age, String experience, String city, String additional, String personalQualities) {
        this.owner = owner;
        this.post = post;
        this.fullname = fullname;
        this.photoImage64 = photoImage64;
        this.phone = phone;
        this.sex = sex;
        this.age = age;
        this.experience = experience;
        this.city = city;
        this.additional = additional;
        this.personalQualities = personalQualities;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPersonalQualities() {
        return personalQualities;
    }

    public void setPersonalQualities(String personalQualities) {
        this.personalQualities = personalQualities;
    }

    public String getPhotoImage64() {
        return photoImage64;
    }

    public void setPhotoImage64(String photoImage64) {
        this.photoImage64 = photoImage64;
    }
}
