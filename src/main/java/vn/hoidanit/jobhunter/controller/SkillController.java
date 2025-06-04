package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SkillService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class SkillController {
    final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    // create skill
    @PostMapping("/skills")
    public ResponseEntity<Skill> postCreateSkill(@Valid @RequestBody Skill reqSkill) throws IdInvalidException {
        if (this.skillService.handleIsSkillExistByName(reqSkill.getName())) {
            throw new IdInvalidException("Skill with name = " + reqSkill.getName() + " already exists");
        }
        this.skillService.handleSaveSkill(reqSkill);
        return ResponseEntity.status(HttpStatus.CREATED).body(reqSkill);
    }

    // fetch all skills
    @GetMapping("/skills")
    public ResponseEntity<ResultPaginationDTO> getFetchAllSkills(
            @Filter Specification<Skill> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                this.skillService.handleFetchAllSkills(spec, pageable));
    }

    // fetch skill by id
    @GetMapping("/skills/{id}")
    public ResponseEntity<Skill> getFetchSkillById(@PathVariable("id") Long id) {
        Skill skill = this.skillService.handleFetchSkillById(id);
        return ResponseEntity.ok().body(skill);
    }

    // delete skill by id
    @DeleteMapping("/skills/{id}")
    public ResponseEntity<String> deleteSkillById(@PathVariable("id") Long id) {
        this.skillService.handleDeleteSkillById(id);
        return ResponseEntity.ok().body("Skill with id = " + id + " has been deleted");
    }

    // update skill
    @PutMapping("/skills")
    public ResponseEntity<Skill> putUpdateSkill(@RequestBody Skill reqSkill) throws IdInvalidException {
        Skill updatedSkill = this.skillService.handleFetchSkillById(reqSkill.getId());
        if (updatedSkill == null) {
            throw new IdInvalidException("Skill with id = " + reqSkill.getId() + " does not exist");
        }
        if (this.skillService.handleIsSkillExistByName(reqSkill.getName())) {
            throw new IdInvalidException("Skill with name = " + reqSkill.getName() + " already exists");
        }
        this.skillService.handleUpdateSkill(reqSkill);
        return ResponseEntity.ok().body(reqSkill);
    }
}
