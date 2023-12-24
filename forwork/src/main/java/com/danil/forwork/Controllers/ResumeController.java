package com.danil.forwork.Controllers;

import com.danil.forwork.Entities.Filter;
import com.danil.forwork.Entities.Resume;
import com.danil.forwork.Entities.Vacancy;
import com.danil.forwork.Exceptions.VacancyNotFoundException;
import com.danil.forwork.Repos.ResumeRepo;
import com.danil.forwork.Services.ResumeService;
import com.danil.forwork.dtos.ResumeDto;
import com.danil.forwork.dtos.VacancyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api/resumes")
public class ResumeController {
    @Autowired
    ResumeService resumeService;

    @GetMapping("/my/{page}")
    public Page<Resume> getMyResumes(Principal user, @PathVariable int page){
        return resumeService.getMyResumesService(user, page);
    }

    @PostMapping("/all")
    public ResponseEntity<?> addResume(Principal user, @RequestBody ResumeDto resume){
        return resumeService.addResumeService(user,resume);
    }

    @PostMapping("/all/{page}")
    public Page<Resume> getAllResumes(@PathVariable int page,  @RequestBody Filter filter){
        return resumeService.getAllResumesService(page, filter.getInput(), filter.getAge(),filter.getCity(),filter.getExperience());
    }

    //==============

    @GetMapping("/favorites/add/{id}")
    public ResponseEntity<?> addFavoriteResume(Principal principal,@PathVariable Long id) throws VacancyNotFoundException {
        return resumeService.addFavoriteResumesService(principal,id);
    }

    @GetMapping("/favorites/{page}")
    public Page<Resume> getFavoriteResume(Principal user, @PathVariable int page){
        return resumeService.getFavoritesResumesService(user, page);
    }
    @DeleteMapping("/favorites/delete/{id}")
    public ResponseEntity<?> deleteFavoriteResume(Principal principal, @PathVariable Long id){
        return resumeService.deleteFavoritesResumesService(principal, id);
    }

    @GetMapping("/favorites/status/{resume_id}")
    public Boolean getFavoriteStatus(Principal principal, @PathVariable Long resume_id){
        return resumeService.getFavoriteStatusService(principal, resume_id);
    }



    @GetMapping("/byId/{id}")
    public ResponseEntity<?> getById(Principal principal, @PathVariable Long id) throws VacancyNotFoundException {
        return resumeService.getResumeById(principal,id);
    }

    @DeleteMapping("/my/delete/{id}")
    public ResponseEntity<?> DeleteById(Principal user,@PathVariable Long id){
        return resumeService.deleteByIdService(user,id);
    }

    //Для обновления данных о вакансие
    @PutMapping("/my/update/{id}")
    public ResponseEntity<?> updateById(Principal principal, @PathVariable Long id, @RequestBody ResumeDto resumeDto){
        return resumeService.updateResumeById(principal, id, resumeDto);
    }

    @GetMapping("/response/{resume_id}")
    public ResponseEntity<?> toResponse(Principal principal,@PathVariable Long resume_id){
        return resumeService.toResponseService(principal,resume_id);
    }

    //Метод для получения состояния отклика
    @GetMapping("/response/status/{resume_id}")
    public Boolean getStatus(Principal principal, @PathVariable Long resume_id){
        return resumeService.getStatusService(principal, resume_id);
    }
}
