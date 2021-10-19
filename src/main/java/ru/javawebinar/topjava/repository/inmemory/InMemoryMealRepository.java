package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    private StampedLock lock = new StampedLock();

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        long stamp = lock.writeLock();
        try {

            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
                return meal;
            }
            // handle case: update, but not present in storage
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public boolean delete(int id) {
        long stamp = lock.writeLock();
        try {
            return repository.remove(id) != null;
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public Meal get(int id) {
        long stamp = lock.tryOptimisticRead();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                return getMeal(id);
            } finally {
                lock.unlock(stamp);
            }
        }
        return getMeal(id);
    }

    private Meal getMeal(int id) {
        return id == SecurityUtil.authUserId() ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll() {
        long stamp = lock.tryOptimisticRead();
        if (!lock.validate(stamp)) {
            stamp = lock.readLock();
            try {
                return getMeals();
            } finally {
                lock.unlock(stamp);
            }
        }
        return getMeals();
    }

    private List<Meal> getMeals() {
        return repository.values()
                .stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }
}

