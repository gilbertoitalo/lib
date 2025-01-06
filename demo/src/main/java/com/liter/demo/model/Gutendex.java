package com.liter.demo.model;

import java.util.List;

public record Gutendex(
        int count,
        String next,
        String previous,
        List<DataBook> results
) {
}
