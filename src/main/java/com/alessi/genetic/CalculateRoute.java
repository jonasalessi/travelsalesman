package com.alessi.genetic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import com.alessi.models.City;

public class CalculateRoute {

    private Chromosome[] chromosomes;
    private int population;
    private double mutationPercent;
    private City[] cities;
    private int matingSize;
    private List<City> pointList = new ArrayList<>();
    private BiConsumer<City[], Chromosome[]> drawLines;

    public CalculateRoute(int population, double mutationPercent, List<City> pointList, BiConsumer<City[], Chromosome[]> drawLines) {
        super();
        this.population = population;
        this.mutationPercent = mutationPercent;
        this.pointList = pointList;
        this.drawLines = drawLines;
    }

    public void calcularDistancia() {
        prepareChromosomes();
        sortChromosomes(chromosomes, population);
        drawLinesBasedOnChromosomes();
    }

    private void prepareChromosomes() {
        matingSize = population / 2;
        int cuttingLength = pointList.size() / 5;

        cities = new City[pointList.size()];

        for (int x = 0; x < pointList.size(); x++) {
            cities[x] = pointList.get(x);
        }

        chromosomes = new Chromosome[population];
        for (int i = 0; i < population; i++) {
            chromosomes[i] = new Chromosome(cities);
            chromosomes[i].setCut(cuttingLength);
            chromosomes[i].setMutation(mutationPercent);
        }
    }

    public void sortChromosomes(Chromosome chromosomes[], int num) {
        Chromosome ctemp;
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < num - 1; i++) {
                if (chromosomes[i].getCost() > chromosomes[i + 1].getCost()) {
                    ctemp = chromosomes[i];
                    chromosomes[i] = chromosomes[i + 1];
                    chromosomes[i + 1] = ctemp;
                    swapped = true;
                }
            }
        }
    }

    public void drawLinesBasedOnChromosomes() {
        double thisCost;
        double oldCost = 0.0;
        int countSame = 0;

        while (countSame < 100) {

            for (int i = 0; i < matingSize; i++) {
                chromosomes[i] = chromosomes[i + matingSize];
                chromosomes[i].calculateCost(cities);
            }

            sortChromosomes(chromosomes, matingSize);

            double cost = chromosomes[0].getCost();
            thisCost = cost;

            if ((int) thisCost == (int) oldCost) {
                countSame++;
            } else {
                countSame = 0;
                oldCost = thisCost;
            }
            drawLines.accept(cities, chromosomes);
        }
    }

}
