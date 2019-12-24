package com.example.scenes;

import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CalendarList extends Pane {

    private String role;
    private GridPane grid;
    private MainScene parent;

    public CalendarList(MainScene parent) {
        this(parent, null);
    }

    public CalendarList(MainScene parent, String role) {
        this.parent = parent;
        this.role = role;

        grid = new GridPane();

        grid.getColumnConstraints().addAll(
            new ColumnConstraints(180),
            new ColumnConstraints(180),
            new ColumnConstraints(180),
            new ColumnConstraints(180),
            new ColumnConstraints(180),
            new ColumnConstraints(180),
            new ColumnConstraints(180)
        );

        grid.getRowConstraints().addAll(
            new RowConstraints(20),
            new RowConstraints(115),
            new RowConstraints(115),
            new RowConstraints(115),
            new RowConstraints(115),
            new RowConstraints(115),
            new RowConstraints(115)
        );

        grid.setPrefSize(1200, 720);

        grid.add(new Label(" Понедельник"), 0, 0);
        grid.add(new Label(" Вторник"), 1, 0);
        grid.add(new Label(" Среда"), 2, 0);
        grid.add(new Label(" Четверг"), 3, 0);
        grid.add(new Label(" Пятница"), 4, 0);
        grid.add(new Label(" Суббота"), 5, 0);
        grid.add(new Label(" Воскресенье"), 6, 0);

        this.getChildren().add(grid);
        setDays();
    }

    private void setDays() {
        LocalDate now = LocalDate.now();
        List<LocalDate> dates = new ArrayList<>();

        Month month = now.getMonth();

        LocalDate temp = LocalDate.of(now.getYear(), month.getValue(), 1);
        while (!temp.getDayOfWeek().equals(DayOfWeek.MONDAY))
            temp = temp.minusDays(1);

        while (temp.getMonth().getValue() <= now.getMonth().getValue() && temp.getYear() == now.getYear()) {
            dates.add(temp);
            temp = temp.plusDays(1);
        }

        while (!temp.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
            dates.add(temp);
            temp = temp.plusDays(1);
        }

        Iterator<LocalDate> iter = dates.iterator();
        for (int i = 0; i < dates.size() / 7; i++) {
            for (int j = 0; j < 7; j++) {
                grid.add(new DayPane(parent, iter.next(), role), j, i + 1);
            }
        }
    }

}
