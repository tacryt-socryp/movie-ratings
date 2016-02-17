package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/7/16.
 */
public class ErrorModel {
    @JsonProperty("message")
    public String message;

    public ErrorModel(String msg) {
        message = msg;
    }
    public ErrorModel() {}

}
