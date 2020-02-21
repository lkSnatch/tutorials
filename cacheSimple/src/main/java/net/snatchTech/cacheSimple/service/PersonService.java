package net.snatchTech.cacheSimple.service;

import net.snatchTech.cacheSimple.dao.PersonDao;
import net.snatchTech.cacheSimple.model.Car;
import net.snatchTech.cacheSimple.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {

    private PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("fakePersonDao") PersonDao personDao) {
        this.personDao = personDao;
    }

    public void addPerson(String fullName) {
        personDao.insertPerson(fullName);
    }

    public void addPerson(String fullName, String birthday) {
        personDao.insertPerson(fullName, birthday);
    }

    public List<Person> getAllPeople() {
        return personDao.getAllPeople();
    }

    public Person getByName(String fullName) {
        try {
            // imagine that this operation too complicated and DB has a worst connection
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Somebody has interrupted this thread " + Thread.currentThread().getName());
        }
        return personDao.getByName(fullName);
    }

    public Person getById(UUID id) {
        try {
            // imagine that this operation too complicated and DB has a worst connection
            Thread.sleep(2_000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Somebody has interrupted this thread " + Thread.currentThread().getName());
        }
        return personDao.getById(id);
    }
}
