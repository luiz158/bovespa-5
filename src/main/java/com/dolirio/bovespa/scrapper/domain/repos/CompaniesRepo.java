package com.dolirio.bovespa.scrapper.domain.repos;

import com.dolirio.bovespa.scrapper.domain.Company;

import java.util.Set;

public interface CompaniesRepo {

    Set<Company> getAll(Set<Company> current);
}
