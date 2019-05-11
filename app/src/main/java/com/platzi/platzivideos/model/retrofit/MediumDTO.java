package com.platzi.platzivideos.model.retrofit;

/**
 * Data transfer Object for Medium, used in Retrofit api client to parse response.
 */
public class MediumDTO {
    private final String url;

    MediumDTO() {
        this.url = "";
    }

    public String getUrl() {
        return url;
    }
}
