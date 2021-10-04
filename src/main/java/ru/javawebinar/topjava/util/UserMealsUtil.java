package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

//        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000));
        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2010));
        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 1000));

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        return null;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<Integer, Integer> dayTotalCalories = meals.stream()
                .filter(um -> (um.getDateTime().toLocalTime().isAfter(startTime) || um.getDateTime().toLocalTime() == startTime)
                        && ((um.getDateTime().toLocalTime().isBefore(endTime)) || um.getDateTime().toLocalTime() == endTime))
                .collect(Collectors.groupingBy(o -> o.getDateTime().getDayOfYear(),
                        Collectors.summingInt(value -> value.getCalories())));
        List<Integer> days = dayTotalCalories.entrySet().stream().filter(o -> o.getValue() > caloriesPerDay)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        List<UserMealWithExcess> res = new ArrayList<>();

        for (Integer day : days) {
            res.addAll(meals.stream().filter(userMeal -> userMeal.getDateTime().getDayOfYear() == day).map(userMeal ->
                            new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true))
                    .collect(Collectors.toList()));
        }
        return res;
    }
}
