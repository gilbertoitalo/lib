package com.liter.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataLanguage(@JsonAlias("languages") String language) {
}
