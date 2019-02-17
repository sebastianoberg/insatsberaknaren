package com.qtrial.insatsberaknaren;

public class CalculatorUtil {

    public int calcluteDownPaymentWithParameters(int housePrice, int pantbrev, int kontantinsats) {

        // Lagfart.
        double lagfart = sumLagfart(housePrice);

        // Pantbrev.
        double nyttPantbrev = sumPantbrev(housePrice, pantbrev, kontantinsats);

        double handpenning = nyttPantbrev + lagfart + kontantinsats;
        int returnValueAsInt = (int) handpenning;

        return returnValueAsInt;
    }

    public double sumLagfart(int housePrice) {
        double prctLagfart =0.015;
        double avgiftLagfart = 825;

        double lagfart = (housePrice * prctLagfart) + avgiftLagfart;

        return lagfart;
    }

    public double sumPantbrev(int housePrice, int befintligaPantbrev, int kontantinsats) {
        double adminAvgift = 375;
        double pantbrev;

        double laneBelopp = housePrice - kontantinsats;
        double summaNyaPantbrev = laneBelopp - befintligaPantbrev;
        if (summaNyaPantbrev < 1 ) {
            pantbrev = 0;
        } else {
            pantbrev = (summaNyaPantbrev/100)*2 + adminAvgift;
        }

        return pantbrev;
    }
}
