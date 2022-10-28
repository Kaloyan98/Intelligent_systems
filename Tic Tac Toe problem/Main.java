import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose who goes first by entering the specific number:");
            System.out.println("1) Player");
            System.out.println("2) AI");

            int choice = scanner.nextInt();

            if (choice == 1) {
                Game game = new Game(CellState.X);
                game.executeGame();
                break;
            }
            if (choice == 2) {
                Game game = new Game(CellState.O);
                game.executeGame();
                break;
            }
        }
    }
}
