package com.solutionmatrix.tinker.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

   SUCCESS(200, "Success"),

   NOTFOUND(404, "Not Found"),

   NOTACCEPTABLE(406, "Not Acceptable, Something went wrong"),

   CONFLICT(409, "Conflict");

    private final int code;
    private final String message;
}
