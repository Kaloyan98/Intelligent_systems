package solution;

public class GoalState {
    private final int size;
    public static Coordinates[] goalCoordinates;
    public static int[][] distanceTable;

    public GoalState(int[][] goal, int size) {
        this.size = size;

        setGoalCoordinates(goal);
        setDistanceTable();
    }

    private void setGoalCoordinates(int[][] goal) {
        goalCoordinates = new Coordinates[size * size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                goalCoordinates[goal[row][column]] = new Coordinates(row, column);
            }
        }
    }

    private void setDistanceTable() {
        distanceTable = new int[size * size][size * size];

        for (int tileValue = 1; tileValue < distanceTable.length; tileValue++) {
            for (int position = 0; position < distanceTable[0].length; position++) {

                int correctRow = goalCoordinates[tileValue].getRow();
                int correctColumn = goalCoordinates[tileValue].getColumn();

                int currentRow = position / size;
                int currentColumn = position % size;

                distanceTable[tileValue][position] = Math.abs(correctRow - currentRow) + Math.abs(correctColumn - currentColumn);
            }
        }
    }

    public void printTable() {
        for (int[] tableRow : distanceTable) {
            for (int position = 0; position < distanceTable[0].length; position++) {
                System.out.print(tableRow[position] + " ");
            }
            System.out.println();
        }
    }
}