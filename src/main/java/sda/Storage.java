package sda;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final static Map<String, Person> records = new HashMap<>();

    public static void put(Person person) {
        records.put(person.getId(), person);
    }

    public static Map<String, Person> getAll() {
        return records;
    }

    public static Person getById(String id) {
        return records.get(id);
    }
}
