package solution;

import java.util.ArrayList;

public class State {
    private static int SIZE;
    private final Direction direction;
    private final State parent;
    private final int currentCost;
    private int[][] tiles;
    private Coordinates zeroPosition;
    private int manhattanDistance;
    private ArrayList<Direction> possibleMoves;

    public State(int[][] state) {
        direction = null;
        parent = null;
        tiles = state;
        SIZE = state.length;
        currentCost = 0;

        setZeroPosition();
        setManhattanDistance();
        setInitialMoves();
    }

    private void setZeroPosition() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                if (tiles[row][column] == 0) {
                    zeroPosition = new Coordinates(row, column);
                    return;
                }
            }
        }
    }

    private void setManhattanDistance() {
        manhattanDistance = 0;

        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                manhattanDistance +=
                        GoalState.distanceTable[tiles[row][column]][row * tiles.length + column];
            }
        }
    }

    private void setInitialMoves() {
        possibleMoves = new ArrayList<>();
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        if (column != SIZE - 1) {
            possibleMoves.add(Direction.LEFT);
        }
        if (column != 0) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (row != SIZE - 1) {
            possibleMoves.add(Direction.UP);
        }
        if (row != 0) {
            possibleMoves.add(Direction.DOWN);
        }
    }

    public State(State parent, Direction direction) {
        this.direction = direction;
        this.parent = parent;

        currentCost = parent.currentCost + 1;
        zeroPosition = parent.zeroPosition;
        manhattanDistance = parent.manhattanDistance;

        setBlocksBeforeMove();
        updateBoard();
        setPossibleMoves();
    }

    private void setBlocksBeforeMove() {
        tiles = new int[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            System.arraycopy(parent.tiles[row], 0, tiles[row], 0, SIZE);
        }
    }

    private void updateBoard() {
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        switch (direction) {
            case LEFT: {
                updateManhattanDistance(row, column, 0, +1);
                moveZeroBy(row, column, 0, +1);
                updateZeroPosition(row, column + 1);
                break;
            }
            case RIGHT: {
                updateManhattanDistance(row, column, 0, -1);
                moveZeroBy(row, column, 0, -1);
                updateZeroPosition(row, column - 1);
                break;
            }
            case UP: {
                updateManhattanDistance(row, column, +1, 0);
                moveZeroBy(row, column, +1, 0);
                updateZeroPosition(row + 1, column);
                break;
            }
            case DOWN: {
                updateManhattanDistance(row, column, -1, 0);
                moveZeroBy(row, column, -1, 0);
                updateZeroPosition(row - 1, column);
            }
        }
    }

    private void moveZeroBy(int zeroRow, int zeroColumn, int moveRowBy, int moveColumnBy) {
        tiles[zeroRow][zeroColumn] = tiles[zeroRow + moveRowBy][zeroColumn + moveColumnBy];
        tiles[zeroRow + moveRowBy][zeroColumn + moveColumnBy] = 0;
    }

    private void updateManhattanDistance(int zeroRow, int zeroColumn, int moveRowBy, int moveColumnBy) {
        int blockValue = tiles[zeroRow + moveRowBy][zeroColumn + moveColumnBy];

        int oldPosition = (zeroRow + moveRowBy) * SIZE + zeroColumn + moveColumnBy;
        manhattanDistance -= GoalState.distanceTable[blockValue][oldPosition];

        int newPosition = zeroRow * SIZE + zeroColumn;
        manhattanDistance += GoalState.distanceTable[blockValue][newPosition];
    }

    private void updateZeroPosition(int i, int j) {
        zeroPosition = new Coordinates(i, j);
    }

    public ArrayList<Direction> getPossibleMoves() {
        return possibleMoves;
    }

    private void setPossibleMoves() {
        possibleMoves = new ArrayList<>();
        int row = zeroPosition.getRow();
        int column = zeroPosition.getColumn();

        if (column != 0 && direction != Direction.LEFT) {
            possibleMoves.add(Direction.RIGHT);
        }
        if (column != SIZE - 1 && direction != Direction.RIGHT) {
            possibleMoves.add(Direction.LEFT);
        }
        if (row != 0 && direction != Direction.UP) {
            possibleMoves.add(Direction.DOWN);
        }
        if (row != SIZE - 1 && direction != Direction.DOWN) {
            possibleMoves.add(Direction.UP);
        }
    }

    public int getFScore() {
        return currentCost + manhattanDistance;
    }

    public State getParent() {
        return parent;
    }

    public Direction getMoveTaken() {
        return direction;
    }

    public boolean isGoal() {
        return manhattanDistance == 0;
    }
}
