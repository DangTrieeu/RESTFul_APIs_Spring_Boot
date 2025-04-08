package vn.hoidanit.jobhunter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.service.CompanyService;

@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> postCreateCompany(@Valid @RequestBody Company reqCompany) {
        Company companyCreated = reqCompany;
        this.companyService.handleSaveCompany(companyCreated);
        return ResponseEntity.ok().body(companyCreated);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getFetchAllCompanies() {
        List<Company> companies = this.companyService.handleFetchAllCompanies();
        return ResponseEntity.ok().body(companies);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> getFetchCompanyById(@PathVariable("id") long id) {
        Company company = this.companyService.handleFetchCompanyById(id);
        return ResponseEntity.ok().body(company);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable("id") long id) {
        this.companyService.handleDeleteCompanyById(id);
        return ResponseEntity.ok().body("company has id = " + id + " has been deleted");
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> putUpdateCompany(@RequestBody Company reqCompany) {
        Company currentCompany = this.companyService.handleUpdateCompany(reqCompany);
        return ResponseEntity.ok().body(currentCompany);
    }
}
