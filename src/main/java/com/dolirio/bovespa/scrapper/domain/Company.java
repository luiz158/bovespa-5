package com.dolirio.bovespa.scrapper.domain;

import java.util.HashSet;
import java.util.Set;

public class Company {

    private final String name;
    private final Set<String> codes;

    public Company(String name) {
        this.name = name;
        codes = new HashSet<>();
    }

    public void addCodes(Set<String> codes) {
        this.codes.addAll(codes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        return name.equals(company.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
