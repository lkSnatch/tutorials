package net.snatchTech.cacheSimple.dao;

import net.snatchTech.cacheSimple.model.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDao {

    void insertPerson(UUID id, String fullName);

    default void insertPerson(String fullName) {
        insertPerson(UUID.randomUUID(), fullName);
    }

    void insertPerson(UUID id, String fullName, String birthday);

    default void insertPerson(String fullName, String birthday) {
        insertPerson(UUID.randomUUID(), fullName, birthday);
    }

    List<Person> getAllPeople();

    Person getByName(String fullName);

    Person getById(UUID id);
}
