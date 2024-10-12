package org.example;

import java.util.Random;

public class MatrixOperations {

    public COOMatrix convertToCOO(double[][] matrix, int N, double density) {
        COOMatrix cooMatrix = new COOMatrix(N, N);
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (random.nextDouble() < density) {
                    double value = matrix[i][j];
                    if (value != 0) {
                        cooMatrix.addValue(i, j, value);
                    }
                }
            }
        }
        return cooMatrix;
    }

    public COOMatrix matrixMultiply(COOMatrix A, COOMatrix B) {
        return A.multiply(B);
    }
}
