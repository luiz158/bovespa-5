package com.dolirio.bovespa.scrapper.domain;

import com.dolirio.bovespa.scrapper.domain.repos.CompaniesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CompaniesService {

    @Autowired
    private CompaniesRepo companiesRepo;

    public Set<Company> getAll() {
        return companiesRepo.getAll();
    }
}
