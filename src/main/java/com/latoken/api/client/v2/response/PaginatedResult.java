package com.latoken.api.client.v2.response;

import lombok.Data;

import java.util.List;

@Data
public final class PaginatedResult<T> {
    private int pageSize;
    private boolean hasContent;
    private boolean first;
    private List<T> content;
    private Page page;
    private boolean hasNext;

    @Data
    public static final class Page {
        private int totalElements;
        private int totalPages;
    }
}



