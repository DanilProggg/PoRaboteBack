package com.danil.forwork.Entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "fullname")
    private String fullname;
    private String phone;
    private Long date;
    @ElementCollection(targetClass = Long.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "user_favorites_resumes", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> favorites_resumes;

    @ElementCollection(targetClass = Long.class,fetch = FetchType.LAZY)
    @CollectionTable(name = "user_favorites_vacancies", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> favorites_vacancies;

    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;


    public User() {
    }

    public User(String email, String password, String fullname, String phone, Long date, Set<Role> roles) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.phone = phone;
        this.date = date;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getFavoritesVacancies() {
        return favorites_vacancies;
    }

    public void setFavoritesVacancies(Long id) {
        if(this.favorites_vacancies == null){
            this.favorites_vacancies = List.of(id);
        } else {
        List<Long> temp = new ArrayList<>(); // массив из любимыхх вакансий
        temp.addAll(this.favorites_vacancies);
        temp.add(id);
        this.favorites_vacancies.add(id);
        }
    }

    public List<Long> getFavoritesResumes() {
        return favorites_resumes;
    }

    public void setFavoritesResumes(Long id) {
        if(this.favorites_resumes == null){
            this.favorites_resumes = List.of(id);
        } else {
            List<Long> temp = new ArrayList<>(); // массив из любимыхх вакансий
            temp.addAll(this.favorites_resumes);
            temp.add(id);
            this.favorites_resumes.add(id);
        }
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
