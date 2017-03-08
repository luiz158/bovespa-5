package com.dolirio.bovespa.scrapper.infra.paginas;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyDataPage {

    private static final String ID_IFRAME_DADOS_EMPRESA = "ctl00_contentPlaceHolderConteudo_iframeCarregadorPaginaExterna";
    private static final String XPATH_CODIGOS_BOVESPA = "//*[@id=\"accordionDados\"]/div/div[1]/table/tbody/tr[2]/td[2]";

    private final WebDriver wd;

    CompanyDataPage(WebDriver wd) {
        this.wd = wd;
    }

    void openDataIframe() {
        String enderecoDados = wd.findElement(By.id(ID_IFRAME_DADOS_EMPRESA)).getAttribute("src");
        wd.navigate().to(enderecoDados);

    }

    public Set<String> getBovespaCodes() {
        WebElement codesCell = wd.findElement(By.xpath(XPATH_CODIGOS_BOVESPA));
        List<WebElement> itens = codesCell.findElements(By.tagName("a"));
        List<WebElement> tables = itens.subList(1, itens.size());
        return tables.stream()
                .filter(item -> !item.getText().trim().isEmpty())
                .map(WebElement::getText).collect(Collectors.toSet());
    }
}
