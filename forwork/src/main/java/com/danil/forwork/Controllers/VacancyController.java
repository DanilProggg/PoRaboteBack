package com.danil.forwork.Controllers;

import com.danil.forwork.Entities.Filter;
import com.danil.forwork.Entities.User;
import com.danil.forwork.Entities.Vacancy;
import com.danil.forwork.Exceptions.VacancyNotFoundException;
import com.danil.forwork.Repos.VacancyRepo;
import com.danil.forwork.Services.UserService;
import com.danil.forwork.Services.VacancyService;
import com.danil.forwork.dtos.VacancyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/vacancies")
public class VacancyController {

    @Autowired
    VacancyRepo vacancyRepo;
    @Autowired
    VacancyService vacancyService;

    @Autowired
    UserService userService;

    //========================================

    //========================================
    @PostMapping("/all/{page}")
    public Page<Vacancy> getAllVacancy(@PathVariable int page, @RequestBody Filter filter){
        return vacancyService.getAllWithPaginationService(page, filter.getInput(), filter.getSalary(), filter.getCity(), filter.getExperience());
    }


    //Получить 1 вакансию
    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getById(Principal principal, @PathVariable Long id) throws VacancyNotFoundException {
        return vacancyService.getVacancyById(principal,id);
    }


    @PostMapping("/all")
    public ResponseEntity<?> createVacancy(@RequestBody VacancyDto vacancyDto, Principal user){
        return vacancyService.createVacancyService(vacancyDto, user);
    }


    //=====
    @GetMapping("/favorites/add/{id}")
    public ResponseEntity<?> addFavoriteVacancies(Principal principal,@PathVariable Long id) throws VacancyNotFoundException {
        return vacancyService.addFavoriteVacancyService(principal,id);
    }
    @GetMapping("/favorites/{page}")
    public Page<Vacancy> getFavoriteVacancies(Principal user, @PathVariable int page){
        return vacancyService.getFavoritesVacanciesService(user, page);
    }
    @DeleteMapping("/favorites/delete/{id}")
    public ResponseEntity<?> deleteFavoriteVacancies(Principal principal, @PathVariable Long id){
        return vacancyService.deleteFavoritesVacanciesService(principal, id);
    }
    @GetMapping("/favorites/status/{vacancy_id}")
    public Boolean getFavoriteStatus(Principal principal, @PathVariable Long vacancy_id){
        return vacancyService.getFavoriteStatusService(principal, vacancy_id);
    }





    @GetMapping("/my/{page}")
    public Page<Vacancy> getMyVacancies(Principal user, @PathVariable int page){
        return vacancyService.getMyVacanciesService(user,page);
    }

    @DeleteMapping("/my/delete/{id}")
    public ResponseEntity<?> deleteById(Principal user,@PathVariable Long id){
        return vacancyService.deleteByIdService(user,id);
    }

    //Для обновления данных о вакансие
    @PutMapping("/my/update/{id}")
    public ResponseEntity<?> updateById(Principal principal, @PathVariable Long id, @RequestBody VacancyDto vacancyDto){
        return vacancyService.updateVacancyByIdService(principal, id, vacancyDto);
    }


    @GetMapping("/response/{vacancy_id}")
    public ResponseEntity<?> toResponse(Principal principal,@PathVariable Long vacancy_id){
        return vacancyService.toResponseService(principal,vacancy_id);
    }

    //Метод для получения состояния отклика
    @GetMapping("/response/status/{vacancy_id}")
    public Boolean getStatus(Principal principal, @PathVariable Long vacancy_id){
        return vacancyService.getStatusService(principal, vacancy_id);
    }





}
