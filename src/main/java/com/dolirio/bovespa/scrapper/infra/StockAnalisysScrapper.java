package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.CompaniesService;
import com.dolirio.bovespa.scrapper.domain.Company;
import com.google.gson.Gson;
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

        Gson gson = new Gson();
        String jsonContent = gson.toJson(companies);


        try (OutputStream os = new FileOutputStream("empresas.json");
             BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(os))) {

            bufferedWriter.write(jsonContent);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao escrever json", e);
        }
    }
}
