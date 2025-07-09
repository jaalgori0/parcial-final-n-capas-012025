package com.uca.parcialfinalncapas.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse {
    private Object message;
    private int status;
    private String time;
    private String uri;
}
