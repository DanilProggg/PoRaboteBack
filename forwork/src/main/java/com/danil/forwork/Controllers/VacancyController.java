package com.danil.forwork.Controllers;

import com.danil.forwork.Entities.Vacancy;
import com.danil.forwork.Exceptions.VacancyNotFoundException;
import com.danil.forwork.Repos.VacancyRepo;
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

    //========================================

    //========================================
    @GetMapping("/all/{page}")
    public Page<Vacancy> getAllVacancy(@PathVariable int page){
        return vacancyService.getAllWithPaginationService(page);
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

    @PostMapping("/favorites")
    public ResponseEntity<?> addFavoriteVacancies(Principal principal,@RequestBody Long id) throws VacancyNotFoundException {
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
        return vacancyService.updateVacancyById(principal, id, vacancyDto);
    }



}
