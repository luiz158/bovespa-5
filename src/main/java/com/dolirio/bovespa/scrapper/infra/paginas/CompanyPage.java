package com.dolirio.bovespa.scrapper.infra.paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

public class CompanyPage {

    private static final String EMPRESAS = "http://bvmf.bmfbovespa.com.br/cias-listadas/empresas-listadas/BuscaEmpresaListada.aspx?idioma=pt-br";
    private static final String BTN_TODAS = "ctl00$contentPlaceHolderConteudo$BuscaNomeEmpresa1$btnTodas";
    private static final String LISTA_EMPRESAS = "ctl00_contentPlaceHolderConteudo_BuscaNomeEmpresa1_grdEmpresa_ctl01";

    private final WebDriver wd;


    public CompanyPage(WebDriver wd) {
        this.wd = wd;
    }

    public void open() {
        wd.get(EMPRESAS);
        waiting().until(WebDriver::getPageSource);
    }

    public void listAllCompanies() {
        waiting().until(wd -> wd.findElement(By.name(BTN_TODAS))).click();
    }

    public int quantity() {
        return listAll().size();
    }

    private List<WebElement> listAll() {
        WebElement table = waiting().until(wd -> wd.findElement(By.id(LISTA_EMPRESAS)));
        return table.findElements(By.tagName("tr"));
    }

    public CompanyLink getCompanyLink(int index) {
        WebElement tr = listAll().get(index);
        List<WebElement> tds = waiting().until(wd -> tr.findElements(By.tagName("td")));
        return new CompanyLink(wd, tds.get(0));
    }

    private Wait<WebDriver> waiting() {
        return new FluentWait<>(wd)
                .withTimeout(80, SECONDS)
                .pollingEvery(2, SECONDS)
                .ignoring(NoSuchElementException.class);
    }

    public static class CompanyLink {

        private final WebDriver wd;
        private final WebElement link;

        CompanyLink(WebDriver wd, WebElement link) {
            this.wd = wd;
            this.link = link;
        }

        public String getName() {
            return link.getText();
        }

        public CompanyDataPage openDataPage() {
            link.findElement(By.tagName("a")).click();
            CompanyDataPage companyDataPage = new CompanyDataPage(wd);
            companyDataPage.openDataIframe();
            return companyDataPage;
        }
    }
}
