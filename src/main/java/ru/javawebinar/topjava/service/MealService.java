package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.createTo;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public MealTo create(Meal meal, Integer userId) {
        return createTo(repository.save(meal, userId), getAll()
                .stream()
                .filter(mealTo -> mealTo.getId() == meal.getId())
                .findFirst().get().isExcess()
        );
    }

    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public MealTo get(int id, Integer userId) {
        return createTo(checkNotFoundWithId(repository.get(id, userId), id),
                getAll()
                .stream()
                .filter(mealTo -> mealTo.getId() == id)
                        .findFirst().get().isExcess()
        );
    }

    public List<MealTo> getAll() {
        return getTos(repository.getAll(),DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, Integer userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}