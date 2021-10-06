package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

//                ,//test same date of another year
//                new UserMeal(LocalDateTime.of(2019, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
//                new UserMeal(LocalDateTime.of(2019, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//                new UserMeal(LocalDateTime.of(2019, Month.JANUARY, 31, 13, 0), "Обед", 500),
//                new UserMeal(LocalDateTime.of(2019, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
//        mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
//        mealsTo.forEach(System.out::println);
//        mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2010);
//        mealsTo.forEach(System.out::println);
//        mealsTo = filteredByCycles(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 1000);
//        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
//        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000));//test all records
//        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2010));//test all record do not exceed the specified value
//        System.out.println(filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 1000));//test all record  exceed the specified value

    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesByDates = new HashMap<>();
        List<UserMeal> filteredMeals = new ArrayList<>();
        for (UserMeal um : meals) {
            totalCaloriesByDates.put(um.getDateTime().toLocalDate(),
                    totalCaloriesByDates.getOrDefault(um.getDateTime().toLocalDate(), 0) + um.getCalories());

            if (TimeUtil.isBetweenHalfOpen(um.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMeals.add(um);
            }
        }

        List<UserMealWithExcess> mealWithExcesses = new ArrayList<>();
        for (UserMeal um : filteredMeals) {
            mealWithExcesses.add(new UserMealWithExcess(um.getDateTime(), um.getDescription(), um.getCalories(),
                    totalCaloriesByDates.get(um.getDateTime().toLocalDate()) > caloriesPerDay));
        }

        return mealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesByDates = meals.stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(),
                        totalCaloriesByDates.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
