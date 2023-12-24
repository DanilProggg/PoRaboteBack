package com.danil.forwork.Entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    User owner;
    String post;
    String description;
    String work_time;
    int experience;
    String organization;
    @Column(columnDefinition="TEXT")
    String organizationImage64;
    int salary;
    String city;
    @ElementCollection(targetClass = Long.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "vacancy_responded_users", joinColumns = @JoinColumn(name = "vacancy_id"))
    List<Long> responded;

    Long date;


    public Vacancy() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
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

    public Long getDate() {
        return date;
    }

    public void setDate() {
        Date date = new Date();
        this.date = date.getTime();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
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

    public String getOrganizationImage64() {
        return organizationImage64;
    }

    public void setOrganizationImage64(String organizationImage64) {
        this.organizationImage64 = organizationImage64;
    }
}
