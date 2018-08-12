package com.example.android.tesis.model;

/**
 * Created by rjsan on 7/28/2018.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.google.gson.annotations.Expose;


public class ReCapchat {

    @SerializedName("success")
    @Expose
    private boolean success = false;
    @SerializedName("error-codes")
    @Expose
    private List<String> errorCodes = null;

    /**
     * No args constructor for use in serialization
     */
    public ReCapchat() {
    }

    /**
     * @param errorCodes
     * @param success
     */
    public ReCapchat(boolean success, List<String> errorCodes) {
        super();
        this.success = success;
        this.errorCodes = errorCodes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

}