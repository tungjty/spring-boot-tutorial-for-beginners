package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDAO")
public class FakePersonDataAccessService implements PersonDAO {

    private static List<Person> FakeDB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        FakeDB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return FakeDB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return FakeDB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> optionalPerson = selectPersonById(id);
        if(optionalPerson.isEmpty())
            return -1;
        FakeDB.remove(optionalPerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person updatePerson) {
        return selectPersonById(id)
                .map(person -> {
                    int indexOfPersonToUpdate = FakeDB.indexOf(person);
                    if(indexOfPersonToUpdate != -1) {
                        FakeDB.set(indexOfPersonToUpdate,
                                new Person(id, updatePerson.getName()));
                        return 1;
                    }
                    return -1;
                })
                .orElse( -1); // don't do anything
    }
}
