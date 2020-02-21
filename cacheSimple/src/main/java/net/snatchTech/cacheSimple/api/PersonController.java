package net.snatchTech.cacheSimple.api;


import net.snatchTech.cacheSimple.model.Person;
import net.snatchTech.cacheSimple.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/person")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping(path = "all")
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @PostMapping(path = "add")
    public void addPerson(@RequestParam("name") String fullName) {
        personService.addPerson(fullName);
    }

    @PostMapping(path = "addWithBirthday")
    public void addPerson(@RequestParam("name") String fullName,
                          @RequestParam("birthday") String birthday) {
        personService.addPerson(fullName, birthday);
    }

    @GetMapping(path = "get")
    public Person getByName(@RequestParam("name") String fullName) {
        return personService.getByName(fullName);
    }
}
