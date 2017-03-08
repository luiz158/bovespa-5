package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.CompaniesService;
import com.dolirio.bovespa.scrapper.domain.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Component
public class StockAnalisysScrapper {

    @Autowired
    private CompaniesService companiesService;

    public void scrapCompanies() {

        Set<Company> current;
        try (InputStream is = new FileInputStream("empresas.json");
             BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is))) {

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedWriter.readLine()) != null)
                sb.append(line);

            current = new Gson().fromJson(sb.toString(), Set.class);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler arquivo");
        }

        Set<Company> companies = companiesService.getAll(current);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonContent = gson.toJson(companies);


        try (OutputStream os = new FileOutputStream("empresas.json");
             BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(os))) {

            bufferedWriter.write(jsonContent);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao escrever json", e);
        }
    }
}
