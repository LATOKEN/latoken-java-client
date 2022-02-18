package com.latoken.api.client.v2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data 
@JsonIgnoreProperties(ignoreUnknown = true)
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



