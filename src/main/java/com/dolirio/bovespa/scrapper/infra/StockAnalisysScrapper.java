package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.CompaniesService;
import com.dolirio.bovespa.scrapper.domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StockAnalisysScrapper {

    @Autowired
    private CompaniesService companiesService;

    public void scrapCompanies() {

        Set<Company> companies = companiesService.getAll();

        System.out.println("Listing companies");
        companies.forEach(System.out::println);
    }
}
