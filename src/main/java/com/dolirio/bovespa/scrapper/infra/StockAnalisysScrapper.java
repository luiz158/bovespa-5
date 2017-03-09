package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.CompaniesService;
import com.dolirio.bovespa.scrapper.domain.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Set;

@Component
public class StockAnalisysScrapper {

    @Autowired
    private CompaniesService companiesService;

    public void scrapCompanies() {

        Set<Company> companies = companiesService.getAll();

        System.out.println(companies);
    }
}
