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

        Cell move = new Cell();
        // Check The Player Row Location
        while (true) {
            System.out.print("Enter row: ");
            move.row = s.nextInt();
            System.out.println();
            if ((move.row > 0) && (move.row <= this.game.board.length)) {
                break;
            }
        }
        // Check The Player Column Location
        while (true) {
            System.out.print("Enter column: ");
            move.col = s.nextInt();
            System.out.println();
            if ((move.col > 0) && (move.col <=  this.game.board[0].length)) {
                break;
            }
        }


        //TODO: WRITE CODE THAT CHECKS IF THE SELECTED BLOB IS A PLAYER'S BLOB
        //TODO: WRITE CODE THAT CHOOSES THE MOVEMENT LOCATION AND TYPE FOR THE SELECTED BLOB


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
