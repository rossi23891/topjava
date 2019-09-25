package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime,
                                                                   LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate,Integer> caloriesPerDayTotal = new HashMap<>();
        List<UserMealWithExceed> filteredList = new ArrayList<>();

        for (UserMeal userMeal : mealList) {
            LocalDate mealDate =userMeal.getDateTime().toLocalDate();
            caloriesPerDayTotal.merge(mealDate,userMeal.getCalories(), Integer::sum);
        }

        for (UserMeal userMeal : mealList) {

            LocalTime lt = userMeal.getDateTime().toLocalTime();
            if(TimeUtil.isBetween(lt,startTime,endTime)){
                boolean exceed = caloriesPerDayTotal.get(userMeal.getDateTime().toLocalDate()) <= caloriesPerDay;
                filteredList.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(),
                        userMeal.getCalories(),exceed));
            }
        }

        return filteredList;
    }

}
