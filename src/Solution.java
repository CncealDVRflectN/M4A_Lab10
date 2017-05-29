public class Solution {
    private static double calcF(double x) {
        return 1.5 * Math.pow(Math.E, x) - 0.5 * Math.cos(x);
    }

    private static double calcFDeriv(double x) {
        return 1.5 * Math.pow(Math.E, x) + 0.5 * Math.sin(x);
    }

    private static void printArr(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    private static double calc(double[][] table, double x) {
        double[][] divDifs = new double[7][6];
        double result = 0;
        double tmp;
        for (int i = 0; i < 6; i += 2) {
            divDifs[0][i] = table[0][(i + 1) / 2];
            divDifs[0][i + 1] = table[0][(i + 1) / 2];
            divDifs[1][i] = table[1][(i + 1) / 2];
            divDifs[1][i + 1] = table[1][(i + 1) / 2];
        }
        for (int i = 0; i < 3; i++) {
            divDifs[2][2 * i] = calcFDeriv(table[0][i]);
        }
        for (int i = 0; i < 2; i++) {
            divDifs[2][2 * i + 1] = (divDifs[1][2 * i + 2] - divDifs[1][2 * i + 1]) / (divDifs[0][2 * i + 2] - divDifs[0][2 * i + 1]);
        }
        for (int i = 3; i < 7; i++) {
            for (int j = 0; j < 7 - i; j++) {
                divDifs[i][j] = (divDifs[i - 1][j + 1] - divDifs[i - 1][j]) / (divDifs[0][j + i - 1] - divDifs[0][j]);
            }
        }
        for (int i = 0; i < 6; i++) {
            tmp = divDifs[i + 1][0];
            for (int j = 0; j < i; j++) {
                tmp *= x - divDifs[0][j];
            }
            result += tmp;
        }
        return result;
    }

    public static void main(String[] args) {
        double[][] table = new double[2][3];
        int N = 2;
        table[0][0] = 1;
        table[1][0] = calcF(table[0][0]);
        table[0][1] = 1.5;
        table[1][1] = calcF(table[0][1]);
        table[0][2] = 2;
        table[1][2] = calcF(table[0][2]);
        System.out.print("X: ");
        printArr(table[0]);
        System.out.print("Y: ");
        printArr(table[1]);
        System.out.println("Интерполяционный многочлен Эрмита:");
        System.out.println("r*: " + Math.abs(calc(table, 1 + 0.1 / 3) - calcF(1 + 0.1 / 3)));
        System.out.println("r**: " + Math.abs(calc(table, 1.5 + 0.1 / 3) - calcF(1.5 + 0.1 / 3)));
        System.out.println("r***: " + Math.abs(calc(table, 2 - 0.1 / 3) - calcF(2 - 0.1 / 3)));
    }
}
