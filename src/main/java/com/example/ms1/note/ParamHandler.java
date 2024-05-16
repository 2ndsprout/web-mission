package com.example.ms1.note;

import lombok.Getter;
import lombok.Setter;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Setter
public class ParamHandler {

    private String keyword;

    private Boolean isSearchModal;

    private String sort;

    public ParamHandler () {
        this.keyword = "";
        this.sort = "date";
        this.isSearchModal = false;
    }
    public String  getQueryParam () {
        return String.format("keyword=%s&sort=%s&isSearchModal=%s", URLEncoder.encode(keyword, StandardCharsets.UTF_8), sort, isSearchModal.toString());
    }
    public String getParamUrl (String url) {
        return url + "?" + getQueryParam();
    }
    public String getRedirectUrl (String url) {
        return "redirect:" + getParamUrl(url);
    }
}