import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    Board game;
    Scanner s = new Scanner(System.in);

    /**
     * THESE TWO LISTS DESCRIBE THE BLOBS IN THE BOARD EACH HAS A POSITION
     */
   static   ArrayList<Blob> playerBlobs = new ArrayList<>();
   static   ArrayList<Blob> computerBlobs = new ArrayList<>();

    /**
     *  this method take two {@link Cell}
     *  as Selected Cell and the Location Cell
     *  from player
     *  then use move or insert method
     */
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
            if (game.checkBoundedBoard(selectCell.i, selectCell.j)) {
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

    /**
     *  get the distance between two point
     * @param one the first point
     * @param two the second point
     * @return the ditsance between one and two
     */
    private int getDistance(Cell one, Cell two) {
        int ii = Math.abs(one.i - two.i);
        int jj = Math.abs(one.j - two.j);
        return (int)Math.sqrt((ii * ii) + (jj * jj));
    }

    /**
     * this method check the type of movement
     * as one or two steps
     * @param one the current blob location
     * @param two the next blob location
     * @return 0 if invalid movement , 1 for one step and 2 for two steps
     */
    private int checkType(Cell one, Cell two) {

        System.out.println("Dis : "+getDistance(one,two) );
        if (getDistance(one, two) > 2) return 0;
        else {
            if (getDistance(one, two) == 1) return 1;
            else return 2;
        }
    }

    private void computerPlay() {
        this.game.takeMove();
    }

    public void start() {


        System.out.print("Enter the Game Level: ");
        int level = s.nextInt();
        System.out.print("\n"+"Enter board width: ");
        int width = s.nextInt();
        System.out.print("\n"+"Enter board height: ");
        int height = s.nextInt();


        this.game = new Board(width, height, level);

        this.game.printBoard();

        while (true) {
            this.playerPlay();
            System.out.println("_____Player Turn______");
            this.game.printBoard();
            if(this.game.finish(computerBlobs,playerBlobs)) {
                break;
            }

            this.computerPlay();
            System.out.println("_____Computer Turn______");
            this.game.printBoard();
            if (this.game.finish(computerBlobs,playerBlobs)) {
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
