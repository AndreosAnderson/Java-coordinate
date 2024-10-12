package org.example;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MatrixMultiplicationBenchmarking {

	@State(Scope.Thread)
	public static class Operands {
		private final int N = 1024;
		private final double density = 0.1;
		private final double[][] A = new double[N][N];
		private final double[][] B = new double[N][N];
		private COOMatrix cooA;
		private COOMatrix cooB;

		@Setup
		public void setup() {
			Random random = new Random();
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					A[i][j] = random.nextDouble();
					B[i][j] = random.nextDouble();
				}
			}

			MatrixOperations operations = new MatrixOperations();
			cooA = operations.convertToCOO(A, N, density);
			cooB = operations.convertToCOO(B, N, density);
		}
	}

	private double getProcessCpuLoad() {
		java.lang.management.OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

		if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
			com.sun.management.OperatingSystemMXBean sunOsBean = (com.sun.management.OperatingSystemMXBean) osBean;
			return sunOsBean.getProcessCpuLoad() * 100;
		}
		return -1; // Zwróć -1, jeśli nie można pobrać danych CPU
	}

	private long getUsedMemory() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.totalMemory() - runtime.freeMemory();
	}

	@Benchmark
	public void multiplication(Operands operands) {
		// Pomiar CPU i pamięci na początku benchmarku
		double cpuBefore = getProcessCpuLoad();
		long memoryBefore = getUsedMemory();

		// Mnożenie macierzy
		MatrixOperations operations = new MatrixOperations();
		COOMatrix result = operations.matrixMultiply(operands.cooA, operands.cooB);

		// Pomiar CPU i pamięci po zakończeniu operacji
		double cpuAfter = getProcessCpuLoad();
		long memoryAfter = getUsedMemory();

		// Wyświetlanie wyników tylko dla rzeczywistego benchmarku
		System.out.println("CPU Usage before: " + cpuBefore + "%");
		System.out.println("CPU Usage after: " + cpuAfter + "%");
		System.out.println("Memory Usage before: " + memoryBefore / (1024 * 1024) + " MB");
		System.out.println("Memory Usage after: " + memoryAfter / (1024 * 1024) + " MB");
	}
}
