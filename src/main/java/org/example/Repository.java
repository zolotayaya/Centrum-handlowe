package org.example;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
        void save(T item);
        List<T> findAll();
        Optional<T> findById(int id);
        void delete(int id);
        List<T> findByName(String name);
}

