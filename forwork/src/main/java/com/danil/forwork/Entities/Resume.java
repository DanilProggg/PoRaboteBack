package com.danil.forwork.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User owner;
    private String fullname;
    @Column(columnDefinition="TEXT")
    private String photoImage64;
    private String post;
    private String sex;
    private Long age;
    private int experience;
    private String city;
    private String phone;

    private String additional;
    private String personalQualities;

    @ElementCollection(targetClass = Long.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "resume_responded_users", joinColumns = @JoinColumn(name = "resume_id"))
    private List<Long> responded;
    private Long date;

    public Resume() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
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

    public String getPersonalQualities() {
        return personalQualities;
    }

    public void setPersonalQualities(String personalQualities) {
        this.personalQualities = personalQualities;
    }

    public Long getDate() {
        return date;
    }

    public void setDate() {
        Date date = new Date();
        this.date = date.getTime();
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

    public String getPhotoImage64() {
        return photoImage64;
    }

    public void setPhotoImage64(String photoImage64) {
        this.photoImage64 = photoImage64;
    }

    public List<Long> getResponded() {
        return responded;
    }

    public void setResponded(Long id) {
        if(this.responded == null){
            this.responded = List.of(id);
        } else {
            List<Long> temp = new ArrayList<>(); // массив из откликнувшихся людей
            temp.addAll(this.responded);
            temp.add(id);
            this.responded.add(id);
        }
    }
}
