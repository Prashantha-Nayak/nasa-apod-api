package com.apodbackend.model;

import java.time.Instant;

/**
 * Model class for APOD entry
 */
public class ApodEntry {

    private String date; // YYYY-MM-DD
    private String title;
    private String explanation;
    private String url;
    private String hdurl;
    private String mediaType;
    private String copyright;
    private Instant fetchedAt;

    // Default constructor
    public ApodEntry() {}

    // Getters and Setters
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getHdurl() { return hdurl; }
    public void setHdurl(String hdurl) { this.hdurl = hdurl; }
    public String getMediaType() { return mediaType; }
    public void setMediaType(String mediaType) { this.mediaType = mediaType; }
    public String getCopyright() { return copyright; }
    public void setCopyright(String copyright) { this.copyright = copyright; }
    public Instant getFetchedAt() { return fetchedAt; }
    public void setFetchedAt(Instant fetchedAt) { this.fetchedAt = fetchedAt; }
}
