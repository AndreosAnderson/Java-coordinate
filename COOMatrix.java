package org.example;

import java.util.ArrayList;
import java.util.List;

public class COOMatrix {
    private int rows;
    private int cols;
    private List<Integer> rowIndices;
    private List<Integer> colIndices;
    private List<Double> values;

    public COOMatrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.rowIndices = new ArrayList<>();
        this.colIndices = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public void addValue(int row, int col, double value) {
        if (value != 0) {
            rowIndices.add(row);
            colIndices.add(col);
            values.add(value);
        }
    }

    public COOMatrix multiply(COOMatrix B) {
        if (this.cols != B.rows) {
            throw new IllegalArgumentException("Matrix dimensions are incompatible for multiplication.");
        }

        COOMatrix result = new COOMatrix(this.rows, B.cols);
        double[][] tempResult = new double[this.rows][B.cols];

        for (int i = 0; i < this.values.size(); i++) {
            int rowA = this.rowIndices.get(i);
            int colA = this.colIndices.get(i);
            double valA = this.values.get(i);

            for (int j = 0; j < B.values.size(); j++) {
                if (B.rowIndices.get(j) == colA) {
                    int colB = B.colIndices.get(j);
                    double valB = B.values.get(j);
                    tempResult[rowA][colB] += valA * valB;
                }
            }
        }

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < B.cols; j++) {
                if (tempResult[i][j] != 0) {
                    result.addValue(i, j, tempResult[i][j]);
                }
            }
        }

        return result;
    }
}
