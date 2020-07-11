package com.deva.katalogrestoran.model.location;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("locality")
    @Expose
    private String locality;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("latitude")
    @Expose
    private double latitude;

    @SerializedName("longitude")
    @Expose
    private double longitude;

    @SerializedName("zipcode")
    @Expose
    private String zipCode;

    @SerializedName("country_id")
    @Expose
    private int countryId;

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getZipCode() {
        return zipCode;
    }

    public int getCountryId() {
        return countryId;
    }
}
