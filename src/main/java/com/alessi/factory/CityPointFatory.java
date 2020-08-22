package com.alessi.factory;

import com.alessi.models.City;

import javafx.scene.layout.StackPane;

public class CityPointFatory {

    private City city;
    private StackPane point;

    private CityPointFatory(City city, StackPane point) {
        this.city = city;
        this.point = point;
    }

    public City getCity() {
        return city;
    }

    public StackPane getPoint() {
        return point;
    }

    public static class Builder {

        private double y;
        private double x;
        private int id;

        public Builder atX(double x) {
            this.x = x;
            return this;
        }

        public Builder atY(double y) {
            this.y = y;
            return this;
        }

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

        public CityPointFatory build() {
            City city = new City((int) x, (int) y, id);

            StackPane stack = new StackPane();
            stack.getChildren().add(city);
            stack.getChildren().add(city.getText());
            stack.setLayoutX(city.getXpos());
            stack.setLayoutY(city.getYpos());

            return new CityPointFatory(city, stack);
        }

    }
}
