package com.dolirio.bovespa.scrapper.infra;

import com.beust.jcommander.internal.Sets;
import com.dolirio.bovespa.scrapper.domain.Company;
import com.dolirio.bovespa.scrapper.domain.repos.CompaniesRepo;
import com.dolirio.bovespa.scrapper.infra.paginas.CompanyDataPage;
import com.dolirio.bovespa.scrapper.infra.paginas.CompanyPage;
import com.dolirio.bovespa.scrapper.infra.paginas.CompanyPage.CompanyLink;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class CompanyScrapper implements CompaniesRepo {

    @Autowired
    private CompaniesJsonRepo companiesJsonRepo;

    @Override
    public Set<Company> getAll() {

        Set<Company> companies = companiesJsonRepo.getCurrentCompaniesFile();

        if (companies.size() < 400) {
            scrap(companies);
            companiesJsonRepo.saveCompaniesList(companies);
        }

        return companies;
    }

    private void scrap(Set<Company> current) {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("phantomjs.binary.path", "bin/phantomjs");

        CompanyPage companyPage = getCompanyPage(caps);
        companyPage.open();
        companyPage.listAllCompanies();

        int empresaAtual = 1 + current.size();
        int totalEmpresas = companyPage.quantity();
        int max = 0;
        System.out.printf(">>> atual: %d. total: %d\n", empresaAtual, totalEmpresas);

        Set<Company> companies = Sets.newHashSet();
        companies.addAll(current);
        companyPage = getCompanyPage(caps);
        do {
            companyPage.open();

            companyPage.listAllCompanies();

            CompanyLink companyLink = companyPage.getCompanyLink(empresaAtual++);
            String name = companyLink.getName();
            System.out.println("Empresa atual: " + name);
            Company company = new Company(name);

            CompanyDataPage companyDataPage = companyLink.openDataPage();
            Set<String> codes = companyDataPage.getBovespaCodes();
            company.addCodes(codes);
            companies.add(company);

        } while ((empresaAtual < (totalEmpresas - 1)) || max++ < 60);
    }

    private CompanyPage getCompanyPage(DesiredCapabilities caps) {
        WebDriver wd = new PhantomJSDriver(caps);
        return new CompanyPage(wd);
    }
}
