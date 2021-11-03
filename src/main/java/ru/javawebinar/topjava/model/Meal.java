package ru.javawebinar.topjava.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.BY_ID_AND_USER_ID, query = "select m FROM Meal m JOIN FETCH m.user WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.BETWEEN_START_AND_END_DATIMES_BY_USER_ID, query = "SELECT m FROM Meal m JOIN FETCH m.user " +
                "WHERE m.user.id=:userId and m.dateTime>=:startDT and m.dateTime<:endDT"),
        @NamedQuery(name = Meal.ALL_BY_USER_ID, query = "SELECT m FROM Meal m JOIN FETCH m.user where m.user.id=:userId"),
})
@Entity
@Table(name = Meal.TABLE_NAME, indexes = @Index(columnList = "user_id, date_time", unique = true))
public class Meal extends AbstractBaseEntity {

    public static final String TABLE_NAME = "meals";
    public static final String BY_ID_AND_USER_ID = "Meal.getByIdAndUserId";
    public static final String ALL_BY_USER_ID = "Meal.getAllByUserID";
    public static final String BETWEEN_START_AND_END_DATIMES_BY_USER_ID = "Meal.getBetweenHalfOpen";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp")
    @NotNull
    private LocalDateTime dateTime;

    @Size(min = 2,max = 120)
    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @Min(10)
    @Max(5000)
    private int calories;

    @NotNull
    @NotEmpty
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
