package com.danil.forwork.dtos;

import com.danil.forwork.Entities.User;

import java.util.Date;

public class VacancyDto {
    String post;
    String description;
    int salary;
    String work_time;
    String experience;
    String organization;

    String organizationImage64;

    String city;


    public VacancyDto() {
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getOrganizationImage64() {
        return organizationImage64;
    }

    public void setOrganizationImage64(String organizationImage64) {
        this.organizationImage64 = organizationImage64;
    }
}
