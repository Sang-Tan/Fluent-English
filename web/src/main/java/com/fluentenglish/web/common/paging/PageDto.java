package com.fluentenglish.web.common.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageDto {
    private List<?> data;
    private PaginationDto pagination;
}
