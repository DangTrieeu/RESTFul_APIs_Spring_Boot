package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;
import vn.hoidanit.jobhunter.repository.JobRepository;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class JobService {
    final JobRepository jobRepository;
    final SkillRepository skillRepository;

    public JobService(JobRepository jobRepository,
            SkillRepository skillRepository

    ) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;

    }

    // create job
    public ResCreateJobDTO handleCreateJob(Job job) {
        if (job.getSkills() != null) {
            List<Long> reqSkills = job.getSkills()
                    .stream().map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            job.setSkills(dbSkills);
        }

        // create job
        Job currentJob = this.jobRepository.save(job);
        ResCreateJobDTO dto = this.handleConvertToCreateJobDTO(currentJob);
        if (currentJob.getSkills() != null) {
            List<String> skills = currentJob.getSkills()
                    .stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }

        return dto;
    }

    // fetch job by id
    public Optional<Job> handleGetJobById(Long id) {
        return this.jobRepository.findById(id);

    }

    // fetch all jobs
    public ResultPaginationDTO handleGetAllJobs(Specification<Job> spec, Pageable pageable) {
        Page<Job> pageSkill = this.jobRepository.findAll(spec, pageable);

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

    // delete job by id
    public void handleDeleteJobById(Long id) {
        this.jobRepository.deleteById(id);
    }

    // update job
    public ResUpdateJobDTO handleUpdateJob(Job reqJob) {
        if (reqJob.getSkills() != null) {
            List<Long> reqSkills = reqJob.getSkills()
                    .stream().map(x -> x.getId())
                    .collect(Collectors.toList());

            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkills);
            reqJob.setSkills(dbSkills);
        }

        // create job
        Job currentJob = this.jobRepository.save(reqJob);
        ResUpdateJobDTO dto = this.handleConvertToUpdateJobDTO(currentJob);
        if (currentJob.getSkills() != null) {
            List<String> skills = currentJob.getSkills()
                    .stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            dto.setSkills(skills);
        }

        return dto;
    }

    public ResCreateJobDTO handleConvertToCreateJobDTO(Job currentJob) {
        ResCreateJobDTO dto = new ResCreateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());
        dto.setActive(currentJob.isActive());
        dto.setCreatedAt(currentJob.getCreatedAt());
        dto.setCreatedBy(currentJob.getCreatedBy());
        return dto;
    }

    public ResUpdateJobDTO handleConvertToUpdateJobDTO(Job currentJob) {
        ResUpdateJobDTO dto = new ResUpdateJobDTO();
        dto.setId(currentJob.getId());
        dto.setName(currentJob.getName());
        dto.setSalary(currentJob.getSalary());
        dto.setQuantity(currentJob.getQuantity());
        dto.setLocation(currentJob.getLocation());
        dto.setLevel(currentJob.getLevel());
        dto.setStartDate(currentJob.getStartDate());
        dto.setEndDate(currentJob.getEndDate());
        dto.setActive(currentJob.isActive());
        dto.setUpdatedAt(currentJob.getUpdatedAt());
        dto.setUpdatedBy(currentJob.getUpdatedBy());
        return dto;
    }

}
