import java.util.ArrayList;

public class Board {

    char[][] board;
    int level = 0;







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




    // Check If The Game Is Finished
    boolean finish(ArrayList<Blob> computerBlobs,ArrayList<Blob> playerBlobs) {

        if (playerBlobs.size() == 0) {
          //  System.out.println("You lose.....you will never win :]");
            return true;
        } else if (computerBlobs.size() == 0) {
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


    int evaluate(ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        int utility = 0;

        //TODO: WRITE EVALUATE CODE

        if (computerBlobs.size() == 0)
            return -10;
        if (playerBlobs.size() == 0)
            return 10;

        if (computerBlobs.size() > playerBlobs.size())
            utility+=2;
        if (computerBlobs.size() == playerBlobs.size())
            utility++;
        if (computerBlobs.size() < playerBlobs.size())
            utility-=2;



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
                changeBlob(movedBlob, true,computerBlobs,playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i-1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false,computerBlobs,playerBlobs);
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
                changeBlob(movedBlob, true,computerBlobs,playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i+1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false,computerBlobs,playerBlobs);
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
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i, blob.j+1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
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


    boolean moveUp1Right2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i-1, blob.j+2,computerBlobs,playerBlobs))
        {   blob.i = blob.i-1;
            blob.j = blob.j+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveDown1Right2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+1, blob.j+2,computerBlobs,playerBlobs))
        {   blob.i = blob.i+1;
            blob.j = blob.j+2;
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
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i, blob.j-1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
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



    boolean moveUp1Left2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i-1, blob.j-2,computerBlobs,playerBlobs))
        {   blob.i = blob.i-1;
            blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveDown1Left2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+1, blob.j-2,computerBlobs,playerBlobs))
        {   blob.i = blob.i+1;
            blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveUpRight1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j+1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i-1, blob.j+1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i-1, blob.j+1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }


   boolean moveUp2Right1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
   {
       if(canMove(blob,blob.i-2, blob.j+1,computerBlobs,playerBlobs))
       {   blob.i = blob.i-2;
           blob.j = blob.j+1;
           changeBlob(blob,isMax,computerBlobs,playerBlobs);
           return true;
       }
       else return false;
   }




    boolean moveUpRight2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i-2, blob.j+2,computerBlobs,playerBlobs))
        {   blob.i = blob.i-2;
            blob.j = blob.j+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveUpLeft1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j-1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i-1, blob.j-1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i-1, blob.j-1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }

    boolean moveUp2Left1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i-2, blob.j-1,computerBlobs,playerBlobs))
        {   blob.i = blob.i-2;
            blob.j = blob.j-1;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveUpLeft2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i-2, blob.j-2,computerBlobs,playerBlobs))
        {   blob.i = blob.i-2;
            blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }



    boolean moveDownRight1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j+1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i+1, blob.j+1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i+1, blob.j+1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }

    boolean moveDownRight2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j+2,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            blob.j = blob.j +2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveDownLeft1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j-1,computerBlobs,playerBlobs))
        {   if (isMax) {
            movedBlob = new Blob(blob.i+1, blob.j-1);
            computerBlobs.add(movedBlob);
            changeBlob(movedBlob, true,computerBlobs,playerBlobs);
            return true;
        } else {
            movedBlob = new Blob(blob.i+1, blob.j-1);
            playerBlobs.add(movedBlob);
            changeBlob(movedBlob, false,computerBlobs,playerBlobs);
            return true;
        }
        }
        else
            return false;
    }



    boolean moveDownLeft2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j-2,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    boolean moveDown2Right1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j+1,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            blob.j = blob.j+1;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }

    boolean moveDown2Left1(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j-1,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            blob.j = blob.j-1;
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
        score = evaluate(computerBlobs,playerBlobs);
        if (finish(computerBlobs,playerBlobs))
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
                if(moveUp1(computerblob, true,computerBlobs,playerBlobs))
                {

                    int b = minimax(depth + 1, false,fakeComputerBlobs,fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                    computerblob.i+=2;
                }

                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                    computerblob.i-=2;
                }

                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1Right2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=1;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1Left2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=1;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2Right1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j-=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2Left1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1Left2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=1;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1Right2(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=1;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2Right1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j-=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2Left1(computerblob, true,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=2;
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
                if(moveUp1(computerblob, false,computerBlobs,playerBlobs))
                {

                    int b = minimax(depth + 1, true,fakeComputerBlobs,fakePlayerBlobs);
                    if (b > best)
                        best = b;

                    computerblob.i+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                    computerblob.i-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1Right2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=1;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp1Left2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=1;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2Right1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j-=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2Left1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1Left2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=1;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1Right2(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=1;
                    computerblob.j-=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2Right1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i-=2;
                    computerblob.j-=1;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2Left1(computerblob, false,computerBlobs,playerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                    computerblob.i+=2;
                    computerblob.j+=2;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


            }

            return best;
        }


    }


    // Start From Here To Find The Best Move By Calling The Minimax Algorithm For Each Of The Computer Possible Moves
    Move bestMove(ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        int best = -1000;


        //TODO: COMPLETE BEST MOVE FUNCTION

        ArrayList<Blob> fakeComputerBlobs = new ArrayList<>();
        ArrayList<Blob> fakePlayerBlobs = new ArrayList<>();
        String movement = "UP1";
        Blob bestblob = new Blob(0,0);


        for (Blob computerblob: computerBlobs
             ) {
            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp1(computerblob,true,computerBlobs,playerBlobs))
            {

                int b = minimax(0, false,fakeComputerBlobs,fakePlayerBlobs);
                if (b > best)
                {best = b;
                  movement = "UP1";
                  bestblob.i = computerblob.i;
                  bestblob.j = computerblob.j;
                }


            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();

            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp2(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                { best = b;
                  movement = "UP2";
                  bestblob.i = computerblob.i;
                  bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown1(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                { best = b;
                 movement = "DOWN1";
                 bestblob.i = computerblob.i;
                 bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown2(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                { best = b;
                 movement = "DOWN2";
                 bestblob.i = computerblob.i;
                 bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveRight1(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                { best = b;
                 movement = "RIGHT1";
                 bestblob.i = computerblob.i;
                 bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();





            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveRight2(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                movement = "RIGHT2";
                bestblob.i = computerblob.i;
                bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveLeft1(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                { best = b;
                  movement = "LEFT1";
                bestblob.i = computerblob.i;
                bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveLeft2(computerblob,true,computerBlobs,playerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                movement = "LEFT2";
                bestblob.i = computerblob.i;
                bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();
        }

        return new Move(bestblob,movement);
    }

    void takeMove() {

        //TODO: WRITE TAKE MOVE FUNCTION


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


