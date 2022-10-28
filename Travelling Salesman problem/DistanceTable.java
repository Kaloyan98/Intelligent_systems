import java.util.Random;

public class DistanceTable {
    public static int[][] pathCosts;

    public DistanceTable(int N) {
        initialiseDistanceTable(N);
        printDistanceTable();
    }

    public static void initialiseDistanceTable(int N) {
        pathCosts = new int[N][N];

        Random random = new Random();
        for (int from = 0; from < N; from++) {
            for (int to = from; to < N; to++) {
                if (from == to) {
                    pathCosts[from][to] = 0;
                } else {
                    int randomCost = random.nextInt(10000) + 1;

                    pathCosts[from][to] = randomCost;
                    pathCosts[to][from] = randomCost;
                }
            }
        }
    }

    public static void printDistanceTable() {
        System.out.printf("%8s","\\");
        for (int to = 0; to < pathCosts.length; to++) {
            System.out.printf("%8d", to);
        }
        System.out.println();

        for (int from = 0; from < pathCosts.length; from++) {
            System.out.printf("%8d", from);
            for (int to = 0; to < pathCosts[0].length; to++) {
                System.out.printf("%8d", pathCosts[from][to]);
            }
            System.out.println();
        }
        System.out.println();
    }
}

