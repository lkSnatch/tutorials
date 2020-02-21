package net.snatchTech.cacheSimple.dao;

import net.snatchTech.cacheSimple.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository("fakePersonDao")
public class FakePersonDao implements PersonDao {

    private static List<Person> DB = new CopyOnWriteArrayList<>();

    @Override
    public void insertPerson(UUID id, String fullName) {
        DB.add(new Person(id, fullName));
    }

    @Override
    public void insertPerson(UUID id, String fullName, String birthday) {
        DB.add(new Person(id, fullName, birthday));
    }

    @Override
    public List<Person> getAllPeople() {
        return DB;
    }

    @Override
    public Person getByName(String fullName) {
        return DB.stream()
                .filter(person -> fullName.equals(person.getFullName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find person by name " + fullName));
    }

    @Override
    public Person getById(UUID id) {
        return DB.stream()
                .filter(person -> id.equals(person.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find person by id " + id));
    }

}
