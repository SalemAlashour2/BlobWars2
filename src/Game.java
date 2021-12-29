import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Board game;
    Scanner s = new Scanner(System.in);

    /**
     * THESE TWO LISTS DESCRIBE THE BLOBS IN THE BOARD EACH HAS A POSITION
     */
   static   ArrayList<Blob> playerBlobs;
   static   ArrayList<Blob> computerBlobs;

    // The Player Function
    private void playerPlay() {

        Cell selectCell = new Cell();
        Cell locationCell = new Cell();


        while (true) {
            System.out.println("Select Blob ");
            System.out.print("Enter row : ");
            selectCell.i = s.nextInt();
            System.out.print("Enter col : ");
            selectCell.j = s.nextInt();
            selectCell.i--;
            selectCell.j--;
            if (checkBoundedBoard(selectCell.i, selectCell.j)) {
                if (game.board[selectCell.i][selectCell.j] == 'b') {
                    System.out.println("your choose is : b " + (selectCell.i + 1) + " " + (selectCell.j + 1));
                    break;
                } else System.out.println("wrong choose");
            } else System.out.println("out of board bound");
        }

        while (true) {
            System.out.println("next Blob location ");
            System.out.print("Enter row : ");
            locationCell.i = s.nextInt();
            System.out.print("Enter col : ");
            locationCell.j = s.nextInt();
            locationCell.i--;
            locationCell.j--;

            if (checkType(selectCell, locationCell) == 0) {
                System.out.println("this movement is not allowed");
            } else {
                if (checkType(selectCell, locationCell) == 1) {
                    game.insert('b', locationCell);

                } else game.move('b', locationCell, selectCell);
                break;
            }
        }
    }

    private double getDistance(Cell one, Cell two) {
        int ii = Math.abs(one.i - two.i);
        int jj = Math.abs(one.j - two.j);
        return Math.sqrt((ii * ii) + (jj * jj));
    }

    private int checkType(Cell one, Cell two) {
        if (getDistance(one, two) > 2) return 0;
        else {
            if (getDistance(one, two) == 1) return 1;
            else return 2;
        }
    }

    private boolean checkBoundedBoard(int i, int j) {
        return (i >= 0 && i < game.getRowLength()) || (j >= 0 && j < game.getColLength());
    }

    // The Computer Function
    private void computerPlay() {
        this.game.takeMove();
    }

    public void start() {


        System.out.print("Enter the Game Level: ");
        int level = s.nextInt();



        this.game = new Board(8, 8, level);

        this.game.printBoard();

        while (true) {
            // Player Turn
            this.playerPlay();
            System.out.println("_____Player Turn______");
            this.game.printBoard();
            if(this.game.finish()) {
                break;
            }

            // Computer Turn
            this.computerPlay();
            System.out.println("_____Computer Turn______");
            this.game.printBoard();
            if (this.game.finish()) {
                break;
            }
        }

    }

    public static void main(String[] args)
    {




        Game lets_play = new Game();
        lets_play.start();
    }
}
