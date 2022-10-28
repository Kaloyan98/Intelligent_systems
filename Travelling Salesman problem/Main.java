import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        if (N < 1) {
            System.out.println("No solution");
        }
        if (N == 1) {
            System.out.println(0);
        }
        if (N >= 2) {
            long startTime = System.currentTimeMillis();
            ProblemSolution solution = new ProblemSolution(N);
            solution.findCheapestPathThroughNCities();

            System.out.println("\nBest path found is: ");
            System.out.println(solution.getBestPath());
            System.out.println("Time taken: " + ((double)
                    (System.currentTimeMillis() - startTime) / 1000) + " seconds");
        }
    }
}
