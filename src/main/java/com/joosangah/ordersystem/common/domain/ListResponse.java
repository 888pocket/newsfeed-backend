package com.joosangah.ordersystem.common.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

public class ListResponse<T> {

    private int totalPages;
    private long totalElements;
    private int current;
    private int size;
    private boolean first;
    private boolean last;

    private List<T> content = new ArrayList<>();

    @Builder
    public void createdByPageObject(Page<T> pageData) {
        this.totalPages = pageData.getTotalPages();
        this.current = pageData.getNumber() + 1;
        this.size = pageData.getSize();
        this.first = pageData.isFirst();
        this.last = pageData.isLast();
        this.content = pageData.getContent();
        this.totalElements = pageData.getTotalElements();
    }
}
