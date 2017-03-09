package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.Company;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Set;

@Repository
class CompaniesJsonRepo {

    Set<Company> getCurrentCompaniesFile() {

        try (InputStream is = new FileInputStream("empresas.json");
             BufferedReader bufferedWriter = new BufferedReader(new InputStreamReader(is))) {

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedWriter.readLine()) != null)
                sb.append(line);

            return new Gson().fromJson(sb.toString(), Set.class);

        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler arquivo");
        }
    }

    void saveCompaniesList(Set<Company> companies) {

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
