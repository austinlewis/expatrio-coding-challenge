package com.expatrio.assignment.payload.request;

import javax.validation.constraints.NotBlank;

public class AddDepartmentRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }
}
