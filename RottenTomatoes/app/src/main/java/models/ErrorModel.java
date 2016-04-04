package models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by logan on 2/7/16.
 *
 */
public class ErrorModel {
    /**
     * message
     */
    @JsonProperty("message")
    public String message;

    /**
     * Make an error model with an error message
     * @param msg msg
     */
    public ErrorModel(String msg) {
        message = msg;
    }

    /**
     * empty init for Jackson compatibility
     */
    public ErrorModel() {}

}
