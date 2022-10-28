package solution;

public enum Direction {
    LEFT("left"),
    RIGHT("right"),
    UP("up"),
    DOWN("down");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String toString() {
        return direction;
    }
}
