package com.alessi.genetic;

import com.alessi.models.City;

public class Chromosome {

    private int[] cityList;
    private double cost;
    private double mutationPercent;
    private int cutLength;

    public Chromosome(City[] cities) {
        initialize(cities);
    }

    private void initialize(City[] cities) {
        boolean taken[] = new boolean[cities.length];
        cityList = new int[cities.length];
        cost = 0.0;
        for (int i = 0; i < cityList.length; i++) {
            taken[i] = false;
        }
        for (int i = 0; i < cityList.length - 1; i++) {
            int icandidate;
            do {
                icandidate = (int) (0.999999 * Math.random() *
                        cityList.length);
            } while (taken[icandidate]);

            cityList[i] = icandidate;
            taken[icandidate] = true;

            if (i == cityList.length - 2) {
                icandidate = 0;
                while (taken[icandidate]) {
                    icandidate++;
                }
                cityList[i + 1] = icandidate;
            }
        }
        calculateCost(cities);
        cutLength = 1;
    }

    public void calculateCost(City[] cities) {
        cost = 0;
        for (int i = 0; i < cityList.length - 1; i++) {
            double dist = cities[cityList[i]].calculateDistanceFrom(cities[cityList[i + 1]]);
            cost += dist;
        }
    }

    public double getCost() {
        return cost;
    }

    public int getCity(int i) {
        return cityList[i];
    }

    void setCities(int[] list) {
        for (int i = 0; i < cityList.length; i++) {
            cityList[i] = list[i];
        }
    }

    public void setCut(int cut) {
        cutLength = cut;
    }

    public void setMutation(double prob) {
        mutationPercent = prob;
    }

    /**
     * Assuming this chromosome is the "mother" mate with the passed in "father".
     *
     * @param father     The father.
     * @param offspring1 Returns the first offspring
     * @param offspring2 Returns the second offspring.
     * @return The amount of mutation that was applied.
     */

    public int motherMateWith(Chromosome father, Chromosome offspring1, Chromosome offspring2) {
        int cutpoint1 = (int) (0.999999 * Math.random() * (cityList.length - cutLength));
        int cutpoint2 = cutpoint1 + cutLength;

        boolean taken1[] = new boolean[cityList.length];
        boolean taken2[] = new boolean[cityList.length];
        int off1[] = new int[cityList.length];
        int off2[] = new int[cityList.length];

        for (int i = 0; i < cityList.length; i++) {
            taken1[i] = false;
            taken2[i] = false;
        }

        for (int i = 0; i < cityList.length; i++) {
            if (i < cutpoint1 || i >= cutpoint2) {
                off1[i] = -1;
                off2[i] = -1;
            } else {
                int imother = cityList[i];
                int ifather = father.getCity(i);
                off1[i] = ifather;
                off2[i] = imother;
                taken1[ifather] = true;
                taken2[imother] = true;
            }
        }

        for (int i = 0; i < cutpoint1; i++) {
            if (off1[i] == -1) {
                for (int element : cityList) {
                    int imother = element;
                    if (!taken1[imother]) {
                        off1[i] = imother;
                        taken1[imother] = true;
                        break;
                    }
                }
            }
            if (off2[i] == -1) {
                for (int j = 0; j < cityList.length; j++) {
                    int ifather = father.getCity(j);
                    if (!taken2[ifather]) {
                        off2[i] = ifather;
                        taken2[ifather] = true;
                        break;
                    }
                }
            }
        }
        for (int i = cityList.length - 1; i >= cutpoint2; i--) {
            if (off1[i] == -1) {
                for (int j = cityList.length - 1; j >= 0; j--) {
                    int imother = cityList[j];
                    if (!taken1[imother]) {
                        off1[i] = imother;
                        taken1[imother] = true;
                        break;
                    }
                }
            }
            if (off2[i] == -1) {
                for (int j = cityList.length - 1; j >= 0; j--) {
                    int ifather = father.getCity(j);
                    if (!taken2[ifather]) {
                        off2[i] = ifather;
                        taken2[ifather] = true;
                        break;
                    }
                }
            }
        }

        offspring1.setCities(off1);
        offspring2.setCities(off2);

        int mutate = 0;
        if (Math.random() < mutationPercent) {
            int iswap1 = (int) (0.999999 * Math.random() * cityList.length);
            int iswap2 = (int) (0.999999 * Math.random() * cityList.length);
            int i = off1[iswap1];
            off1[iswap1] = off1[iswap2];
            off1[iswap2] = i;
            mutate++;
        }
        if (Math.random() < mutationPercent) {
            int iswap1 = (int) (0.999999 * Math.random() * cityList.length);
            int iswap2 = (int) (0.999999 * Math.random() * cityList.length);
            int i = off2[iswap1];
            off2[iswap1] = off2[iswap2];
            off2[iswap2] = i;
            mutate++;
        }
        return mutate;
    }

}
