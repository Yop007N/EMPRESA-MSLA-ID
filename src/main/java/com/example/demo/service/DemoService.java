package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DemoService {

    private final Map<Long, String> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public List<String> findAll() {
        return List.copyOf(store.values());
    }

    public String findById(Long id) {
        String item = store.get(id);
        if (item == null) {
            throw new ResourceNotFoundException("Item", "id", id);
        }
        return item;
    }

    public Long create(String value) {
        Long id = idGenerator.getAndIncrement();
        store.put(id, value);
        return id;
    }

    public void delete(Long id) {
        if (!store.containsKey(id)) {
            throw new ResourceNotFoundException("Item", "id", id);
        }
        store.remove(id);
    }
}
