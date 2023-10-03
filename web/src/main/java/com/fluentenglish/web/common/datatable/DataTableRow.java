package com.fluentenglish.web.common.datatable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DataTableRow {
    private Integer id;
    private String name;
    private String detailUrl;
    private String updateUrl;
    private String deleteUrl;
}
