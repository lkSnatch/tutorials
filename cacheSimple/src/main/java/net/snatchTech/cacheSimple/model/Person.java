package net.snatchTech.cacheSimple.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.UUID;

public class Person {

    private final UUID id;
    private final String fullName;
    private String birthday;

    public Person(@JsonProperty("id") UUID id,
                  @JsonProperty("name") String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Person(@JsonProperty("id") UUID id,
                  @JsonProperty("name") String fullName,
                  @JsonProperty("birthday") String birthday) {
        this.id = id;
        this.fullName = fullName;
        this.birthday = birthday;
    }

    public UUID getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
