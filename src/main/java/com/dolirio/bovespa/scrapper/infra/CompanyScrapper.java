package com.dolirio.bovespa.scrapper.infra;

import com.dolirio.bovespa.scrapper.domain.Company;
import com.dolirio.bovespa.scrapper.domain.repos.CompaniesRepo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

@Repository
public class CompanyScrapper implements CompaniesRepo {

    @Override
    public Set<Company> getAll() {

        try {
            Document document = Jsoup.connect("http://www.guiainvest.com.br/setorial/setor/default.aspx")
                    .cookie("ASP.NET_SessionId", "1cus4332ynq0qpk34ih0h3cj").get();
            Elements trs = document.select("#RadGrid table tr td.rgSorted a");
            trs.forEach(tds -> {
                System.out.println("=> " + tds.attr("href") + " :" + tds.html());
            });
            return Collections.emptySet();
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao avaliar página de empresas", e);
        }
    }
}
