package com.alessi.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.alessi.factory.CityPointFatory;
import com.alessi.genetic.CalculateRoute;
import com.alessi.genetic.Chromosome;
import com.alessi.models.City;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class PrimaryController implements Initializable {

    @FXML
    private Pane contentPanel;
    @FXML
    private AnchorPane main;
    @FXML
    private Label lbTotal;
    @FXML
    private TextField txtPercentualMut;
    @FXML
    private TextField txtPopulacao;

    private List<City> pointList = new ArrayList<>();

    private double limitX;
    private double limitY;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limitX = main.getPrefWidth() - contentPanel.getLayoutX() - 30;
        limitY = main.getPrefHeight() - contentPanel.getLayoutY() - 60;
    }

    @FXML
    void actionCalc() {
        if (pointList.size() > 2) {
            int population = getPopulationElseDefaultValue();
            double mutationPercent = getMutationPercentElseDefaultValue();
            var calc = new CalculateRoute(population, mutationPercent, pointList, this::atualizarGL);
            calc.calcularDistancia();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Please put at least 2 poits!");
            alert.showAndWait();
        }
    }

    private double getMutationPercentElseDefaultValue() {
        try {
            return Double.parseDouble(txtPercentualMut.getText());
        } catch (Exception e) {
            return 0.20;
        }
    }

    private int getPopulationElseDefaultValue() {
        try {
            return Integer.parseInt(txtPopulacao.getText());
        } catch (Exception e) {
            return 100;
        }
    }

    @FXML
    void actionClear() {
        contentPanel.getChildren().clear();
        pointList.clear();
        lbTotal.setText("Total: 0");
    }

    @FXML
    void actionMarkPoint(MouseEvent event) {
        if (isThePointOutOfLimit(event)) {
            return;
        }

        CityPointFatory pointFactory = new CityPointFatory.Builder()
                .atX(event.getX())
                .atY(event.getY())
                .withId(pointList.size() + 1)
                .build();

        contentPanel.getChildren().add(pointFactory.getPoint());
        pointList.add(pointFactory.getCity());

        lbTotal.setText("Total: " + pointList.size());

    }

    private boolean isThePointOutOfLimit(MouseEvent event) {
        return limitX < event.getX() || event.getX() < 15 ||
                limitY < event.getY() || event.getY() < 16;
    }

    public void removeLines() {
        List<Node> newNodes = contentPanel.getChildren().stream().filter(Predicate.not(Line.class::isInstance)).collect(Collectors.toList());
        contentPanel.getChildren().clear();
        contentPanel.getChildren().addAll(newNodes);
    }

    public void atualizarGL(City[] cidades, Chromosome[] chromosomes) {
        removeLines();
        for (int i = 0; i < cidades.length; i++) {
            int icity = chromosomes[0].getCity(i);
            if (i != 0) {
                int last = chromosomes[0].getCity(i - 1);
                Line linha = new Line(cidades[icity].getXpos() + 10, cidades[icity].getYpos() + 10, cidades[last].getXpos() + 10, cidades[last].getYpos() + 10);

                linha.setStroke(Color.web("black"));
                contentPanel.getChildren().add(0, linha);
            }
        }

    }
}
