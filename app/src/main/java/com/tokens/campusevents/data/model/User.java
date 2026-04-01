package com.tokens.campusevents.data.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    public String id;
    public String name;
    public String email;
    public String city;
    public boolean isOrganizer;
    public Set<String> followedOrganizers;
    public Set<String> savedEventIds;

    public User(String id, String name, String email, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.city = city;
        this.isOrganizer = false;
        this.followedOrganizers = new HashSet<>();
        this.savedEventIds = new HashSet<>();
    }
}
