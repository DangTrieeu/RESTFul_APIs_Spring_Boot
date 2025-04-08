package vn.hoidanit.jobhunter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handleSaveCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public List<Company> handleFetchAllCompanies() {
        return this.companyRepository.findAll();
    }

    public Company handleFetchCompanyById(long id) {
        return this.companyRepository.findById(id);
    }

    public void handleDeleteCompanyById(long id) {
        this.companyRepository.deleteById(id);
    }

    public Company handleUpdateCompany(Company company) {
        Company currentCompany = this.companyRepository.findById(company.getId());
        if (currentCompany != null) {
            currentCompany.setName(company.getName());
            currentCompany.setAddress(company.getAddress());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setLogo(company.getLogo());
            this.companyRepository.save(currentCompany);
        }
        return currentCompany;
    }
}
