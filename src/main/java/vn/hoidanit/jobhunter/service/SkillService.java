package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.SkillRepository;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    // save skill
    public void handleSaveSkill(Skill skill) {
        this.skillRepository.save(skill);
    }

    // fetch skill by id
    public Skill handleFetchSkillById(Long id) {
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        if (skillOptional.isPresent())
            return skillOptional.get();
        return null;
    }

    // fetch all skills
    public ResultPaginationDTO handleFetchAllSkills(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageSkill = this.skillRepository.findAll(spec, pageable);

        ResultPaginationDTO resultDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setTotalPages(pageSkill.getTotalPages());
        meta.setTotalElements(pageSkill.getTotalElements());

        resultDTO.setMeta(meta);
        resultDTO.setResult(pageSkill.getContent());
        return resultDTO;
    }

    // delete skill by id
    public void handleDeleteSkillById(Long id) {
        // delete job (inside job_skill table)
        Optional<Skill> skillOptional = this.skillRepository.findById(id);
        Skill currentSkill = skillOptional.get();
        currentSkill.getJobs().forEach(job -> job.getSkills().remove(currentSkill));

        // delete skill
        this.skillRepository.delete(currentSkill);
    }

    // update skill
    public Skill handleUpdateSkill(Skill reqSkill) {
        Optional<Skill> optionalSkill = this.skillRepository.findById(reqSkill.getId());
        if (optionalSkill.isPresent()) {
            Skill existingSkill = optionalSkill.get();
            existingSkill.setName(reqSkill.getName());
            return this.skillRepository.save(existingSkill);
        }
        return null;
    }

    // check if skill exists by name
    public boolean handleIsSkillExistByName(String name) {
        return this.skillRepository.existsByName(name);
    }
}
