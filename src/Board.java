import java.util.ArrayList;

public class Board {

    char[][] board;
    int level = 0;

    ArrayList<Blob> tempComputerBlobs;
    ArrayList<Blob> tempPlayerBlobs;


    public Board(int level) {
        this.level = level;
    }


    public Board(int row, int col, int level) {

        this.board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.board[i][j] = '_';
            }
        }

        Game.playerBlobs.add(new Blob(0, 0));
        Game.playerBlobs.add(new Blob(7, 0));
        Game.computerBlobs.add(new Blob(0, 7));
        Game.computerBlobs.add(new Blob(7, 7));

        board[0][0] = 'X';
        board[7][0] = 'X';
        board[0][7] = 'O';
        board[7][7] = 'O';

        this.level = level;
    }

    // Insert Value In Empty Place (Needed For Player)
    public void insert(char value, Cell pos) {
        if (this.board[pos.row][pos.col] == '_') {
            this.board[pos.row][pos.col] = value;
        }
    }

    // Check If The Game Is Finished
    boolean finish() {

        if (tempPlayerBlobs.size() == 0) {
          //  System.out.println("You lose.....you will never win :]");
            return true;
        } else if (tempComputerBlobs.size() == 0) {
          //  System.out.println("You win.......Don't cheat next time!!");
            return true;
        }


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '_')
                    return false;
            }
        }
        return true;
    }


    int evaluate() {
        int utility = 0;

        //TODO: WRITE EVALUATE CODE

        if (tempComputerBlobs.size() == 0)
            return -10;
        if (tempPlayerBlobs.size() == 0)
            return 10;

        if (tempComputerBlobs.size() > tempPlayerBlobs.size())
            utility++;
        if (tempComputerBlobs.size() <= tempPlayerBlobs.size())
            utility--;


        return utility;

    }


    boolean canMove(Blob blob, int i, int j,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        int distancei;
        int distancej;
        if (blob.i > i)
            distancei = blob.i - i;
        else
            distancei = i - blob.i;

        if (blob.j > j)
            distancej = blob.j - j;
        else
            distancej = j - blob.j;


        if (distancei > 2 && distancej > 2)
            return false;

        if (board[i][j] == '#')
            return false;
        for (Blob playerBlob :
                playerBlobs) {
            if (i == playerBlob.i && j == playerBlob.j)
                return false;
        }
        for (Blob computerBlob :
                computerBlobs) {
            if (i == computerBlob.i && j == computerBlob.j)
                return false;
        }
        return true;
    }

    void changeBlob(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        int distancei;
        int distancej;


        if(isMax)
        {
            for (Blob playerBlob:
                 playerBlobs) {
                if (blob.i > playerBlob.i)
                    distancei = blob.i - playerBlob.i;
                else
                    distancei = playerBlob.i - blob.i;
                if(blob.j > playerBlob.j)
                    distancej = blob.j - playerBlob.j;
                else
                    distancej = playerBlob.j - blob.j;

                if(distancei == 1 || distancej == 1)
                {  computerBlobs.add(playerBlob);
                    playerBlobs.remove(playerBlob);}
            }
        }else
        {
            for (Blob computerBlob:
                    computerBlobs) {
                if (blob.i > computerBlob.i)
                    distancei = blob.i - computerBlob.i;
                else
                    distancei = computerBlob.i - blob.i;
                if(blob.j > computerBlob.j)
                    distancej = blob.j - computerBlob.j;
                else
                    distancej = computerBlob.j - blob.j;

                if(distancei == 1 || distancej == 1)
                {
                    playerBlobs.add(computerBlob);
                    computerBlobs.remove(computerBlob);
                }
            }
        }
    }

    boolean moveUp1(Blob blob, boolean isMax ,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

      Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j,computerBlobs,playerBlobs))
        {   if (isMax) {
                movedBlob = new Blob(blob.i-1, blob.j);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i-1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
                return true;
            }
        }
        else
            return false;

    }

    boolean moveUp2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {

        if(canMove(blob,blob.i-2, blob.j,computerBlobs,playerBlobs))
        {   blob.i = blob.i-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
       else return false;
    }



    boolean moveDown1(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j,computerBlobs,playerBlobs))
        {   if (isMax) {
                movedBlob = new Blob(blob.i+1, blob.j);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i+1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
                return true;
            }
        }
        else
            return false;
    }

    boolean moveDown2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }



    boolean moveRight1(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i, blob.j+1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i, blob.j+1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i, blob.j+1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }

    boolean moveRight2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i, blob.j+2,computerBlobs,playerBlobs))
        {   blob.j = blob.j+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }




    boolean moveLeft1(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i, blob.j-1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i, blob.j-1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i, blob.j-1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }


    boolean moveLeft2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i, blob.j-2,computerBlobs,playerBlobs))
        {   blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    //     Minimax Algorithm
    int minimax(int depth, boolean isMax ,ArrayList<Blob> computerBlobs,ArrayList<Blob> playerBlobs) {

        //TODO: COMPLETE MINIMAX FUNCTION

        int score;

        ArrayList<Blob> fakeComputerBlobs = new ArrayList<>();
        ArrayList<Blob> fakePlayerBlobs = new ArrayList<>();
        score = evaluate();
        if (finish())
            return score;
        if (depth == level)
            return score;

        // If Max Player
        if (isMax) {
            int best = -1000;

            for (Blob computerblob :
                    computerBlobs) {

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1(computerblob,isMax,computerBlobs,playerBlobs))
                {

                    int b = minimax(depth + 1, !isMax,fakeComputerBlobs,fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight1(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft1(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob,isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, !isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

            }

            return best;
        }

        // If Min Player
        else {
            int best = 1000;
            for (Blob computerblob :
                    computerBlobs) {

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1(computerblob,!isMax,computerBlobs,playerBlobs))
                {

                    int b = minimax(depth + 1, isMax,fakeComputerBlobs,fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight1(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft1(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob,!isMax,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, isMax, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

            }

            return best;
        }


    }


    // Start From Here To Find The Best Move By Calling The Minimax Algorithm For Each Of The Computer Possible Moves
    Cell bestMove() {

        int bestVal = -1000;
        Cell bestMove = new Cell();

        //TODO: COMPLETE BEST MOVE FUNCTION

        return bestMove;
    }

    void takeMove() {

        //TODO: WRITE TAKE MOVE FUNCTION

        tempComputerBlobs.clear();
        tempPlayerBlobs.clear();
        tempComputerBlobs.addAll(Game.computerBlobs);
        tempPlayerBlobs.addAll(Game.playerBlobs);
    }

    void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
    }


}


