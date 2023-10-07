package com.fluentenglish.web.common.datatable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class DataTableRow {
    private Integer id;
    private String name;
    private Integer position;
    private String detailUrl;
    private String updateUrl;
    private String deleteUrl;
}
