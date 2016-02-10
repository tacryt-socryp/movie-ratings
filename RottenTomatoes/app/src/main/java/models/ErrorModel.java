package models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by logan on 2/7/16.
 */
public class ErrorModel {
    @SerializedName("message")
    public String message;

    public ErrorModel(String msg) {
        message = msg;
    }

}
