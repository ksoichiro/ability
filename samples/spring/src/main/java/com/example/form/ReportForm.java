package com.example.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReportForm {
    @NotNull
    private String title;
}
