package com.dolirio.bovespa.scrapper.domain;

import java.util.HashSet;
import java.util.Set;

public class Company {

    private final String name;
    private Set<String> codes;

    public Company(String name) {
        this.name = name;
        codes = new HashSet<>();
    }

    public void addCodes(Set<String> codes) {
        this.codes.addAll(codes);
    }
}
