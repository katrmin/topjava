package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@Controller
public class MealRestController  {
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        return service.getAll();
    }

    public MealTo get(int id, Integer userId) {
        return service.get(id, userId);
    }

    public MealTo create(Meal meal, Integer userId) {
        return service.create(meal, userId);
    }

    public void delete(int id, Integer userId) {
        service.delete(id, userId);
    }

    public void update(Meal meal, Integer userId) {
        service.update(meal, userId);
    }
}