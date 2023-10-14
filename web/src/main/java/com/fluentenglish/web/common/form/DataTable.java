package com.fluentenglish.web.common.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class DataTable {
    private boolean isArrangeable;
    private boolean isPaginable;
    private String changePositionsUrl;
    private List<DataTableRow> rows;
}
