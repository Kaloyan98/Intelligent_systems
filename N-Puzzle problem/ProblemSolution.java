package solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ProblemSolution {
    private static final int GOAL_STATE_REACHED = -1;
    public static State startState;
    public static ArrayList<String> movesToGoal;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();
        int zeroIndex = scanner.nextInt();

        int size = (int) Math.sqrt(N + 1);
        int[][] startTileBoard = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                startTileBoard[row][column] = scanner.nextInt();
            }
        }

        int[][] goalTileBoard = new int[size][size];

        if (zeroIndex == -1 || zeroIndex == N) {
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    goalTileBoard[row][column] = row * size + column + 1;
                }
            }
            goalTileBoard[size - 1][size - 1] = 0;
        } else {
            for (int row = 0; row < size; row++) {
                for (int column = 0; column < size; column++) {
                    if (zeroIndex > row * size + column) {
                        goalTileBoard[row][column] = row * size + column + 1;
                    } else if (zeroIndex == row * size + column) {
                        goalTileBoard[row][column] = 0;
                    } else {
                        goalTileBoard[row][column] = row * size + column;
                    }
                }
            }
        }

        GoalState goalState = new GoalState(goalTileBoard, size);
        startState = new State(startTileBoard);

        long startTime = System.currentTimeMillis();
        iterativeDeepeningAStar();

        System.out.println("Time: " + ((double)
                (System.currentTimeMillis() - startTime) / 1000) + " seconds");

        System.out.println(movesToGoal.size());
        for (String move : movesToGoal) {
            System.out.println(move);
        }
    }

    public static void iterativeDeepeningAStar() {
        int threshold = startState.getFScore();

        while (true) {
            int newThreshold = searchForGoal(startState, threshold);

            if (newThreshold == GOAL_STATE_REACHED) {
                return;
            }

            threshold = newThreshold;
        }
    }

    public static int searchForGoal(State state, int threshold) {
        int fScore = state.getFScore();

        if (fScore > threshold) {
            return fScore;
        }

        if (state.isGoal()) {
            getMovesFromGoalToStart(state);
            return GOAL_STATE_REACHED;
        }

        ArrayList<State> successors = new ArrayList<>();
        for (Direction direction : state.getPossibleMoves()) {
            successors.add(new State(state, direction));
        }

        int smallestHigherThreshold = Integer.MAX_VALUE;
        for (State successor : successors) {
            int successorFScore = searchForGoal(successor, threshold); //using recursion to determine the threshold

            if (successorFScore == GOAL_STATE_REACHED) {
                return GOAL_STATE_REACHED;
            }

            if (successorFScore < smallestHigherThreshold) {
                smallestHigherThreshold = successorFScore;
            }
        }

        return smallestHigherThreshold;
    }

    public static void getMovesFromGoalToStart(State state) {
        movesToGoal = new ArrayList<>();
        while (state.getParent() != null) {
            movesToGoal.add(state.getMoveTaken().toString());
            state = state.getParent();
        }

        Collections.reverse(movesToGoal);
    }
}
