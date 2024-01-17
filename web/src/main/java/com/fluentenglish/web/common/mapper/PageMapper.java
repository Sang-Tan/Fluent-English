package com.fluentenglish.web.common.mapper;

import com.fluentenglish.web.common.paging.PageDto;
import com.fluentenglish.web.common.paging.PaginationDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class PageMapper {
    public PageDto toPageDto(Page<?> page){
        List<?> data = page.getContent();
        PaginationDto paginationDto = PaginationDto.builder()
                .currentPage(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalItems((int)page.getTotalElements())
                .itemsPerPage(page.getSize())
                .build();
        return new PageDto(data, paginationDto);
    }
}
