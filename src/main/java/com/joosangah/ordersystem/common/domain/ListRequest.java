package com.joosangah.ordersystem.common.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListRequest {

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 20;
    @Builder.Default
    private String orderType = "";

    public ListRequest() {
        this.page = 1;
        this.size = 20;
        this.orderType = "";
    }

    public ListRequest(int page, int size, String orderType) {
        this.page = page;
        this.size = size;
        this.orderType = orderType;
    }
}
