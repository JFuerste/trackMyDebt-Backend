package com.jfuerste.trackmydebtbackend.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JsonErrorObject {
    boolean error = true;
    String errorMessage = "";
    int errorCode;

    public JsonErrorObject(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
