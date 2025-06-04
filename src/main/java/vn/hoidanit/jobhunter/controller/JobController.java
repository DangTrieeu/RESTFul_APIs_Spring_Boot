package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

import vn.hoidanit.jobhunter.domain.Job;

import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResCreateJobDTO;
import vn.hoidanit.jobhunter.domain.response.job.ResUpdateJobDTO;
import vn.hoidanit.jobhunter.service.JobService;
import vn.hoidanit.jobhunter.util.annotation.APIMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

@RestController
@RequestMapping("/api/v1")
public class JobController {

    final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // create job
    @PostMapping("/jobs")
    @APIMessage("Create a new job")
    public ResponseEntity<ResCreateJobDTO> postCreateJob(@RequestBody Job job) {
        ResCreateJobDTO jobCreated = this.jobService.handleCreateJob(job);
        return ResponseEntity.ok().body(jobCreated);
    }

    // fetch job by id
    @GetMapping("/jobs/{id}")
    @APIMessage("Fetch job by ID")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id) throws IdInvalidException {
        Optional<Job> currentJob = this.jobService.handleGetJobById(id);
        if (!currentJob.isPresent()) {
            throw new IdInvalidException("Job not found");
        }

        return ResponseEntity.ok().body(currentJob.get());
    }

    // fetch all jobs
    @GetMapping("/jobs")
    @APIMessage("fetch all jobs")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(@Filter Specification<Job> spec, Pageable pageable) {
        return ResponseEntity.ok().body(this.jobService.handleGetAllJobs(spec, pageable));
    }

    // delete job by id
    @DeleteMapping("/jobs/{id}")
    @APIMessage("delete job by ID")
    public ResponseEntity<String> deleteJobById(@PathVariable("id") Long id) {
        this.jobService.handleDeleteJobById(id);
        return ResponseEntity.ok().body("Job with id = " + id + " has been deleted");
    }

    // update job
    @PutMapping("/jobs")
    @APIMessage("update job")
    public ResponseEntity<ResUpdateJobDTO> putUpdateJob(@RequestBody Job reqJob) {
        ResUpdateJobDTO updatedJob = this.jobService.handleUpdateJob(reqJob);
        if (updatedJob == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(updatedJob);
    }

}
