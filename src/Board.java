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




    /**
     * This function checks if the game reached the end.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the game reached the end and false otherwise.
       */
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

    /**
     * This function is used in the minimax function.It checks if the computer is winning the game or not by comparing
     * computer's blob list length with the player's blob list length.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns an int that represents the score,that score signifies if the computer is winning or not;
     * if it's bigger than zero then the computer is winning, if it's smaller than zero then computer is losing,if it's
     * equal to zero then the game is reaching a draw status.
     * */
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

    /**
     * This function checks if the move is possible; it checks if the new position is taken by another blob, or it's
     * off board.
     * @param blob is the blob that is need to be moved.
     * @param i is the location on the i axis.
     * @param j is the location on the j axis.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the move is possible and false otherwise
     * */
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


    /**
     * This function changes the blobs that are close to a specified blob. If the isMax value is true then the blobs that
     * are going to change are the ones from the player's blob list and the changed blobs will be added to the computer's
     * blob list, if the isMax value is false then the process will be the same but the changed blobs will be taken from
     * the computer's blob list and added to the player's blob list.
     * @param blob is the blob that you want to check if any blob is around it.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * */

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


    /**
     * This function is used to move the blob upwards by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob upwards by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

    boolean moveUp2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {

        if(canMove(blob,blob.i-2, blob.j,computerBlobs,playerBlobs))
        {   blob.i = blob.i-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
       else return false;
    }


    /**
     * This function is used to move the blob downwards by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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



    /**
     * This function is used to move the blob downwards by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

    boolean moveDown2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i+2, blob.j,computerBlobs,playerBlobs))
        {   blob.i = blob.i+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    /**
     * This function is used to move the blob to the right by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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


    /**
     * This function is used to move the blob to the right by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

    boolean moveRight2(Blob blob , boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i, blob.j+2,computerBlobs,playerBlobs))
        {   blob.j = blob.j+2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }


    /**
     * This function is used to move the blob upwards by one step and to the right by two steps.
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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

    /**
     * This function is used to move the blob downwards by one step and to the right by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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


    /**
     * This function is used to move the blob to the left by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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



    /**
     * This function is used to move the blob to left by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

    boolean moveLeft2(Blob blob, boolean isMax,ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs)
    {
        if(canMove(blob,blob.i, blob.j-2,computerBlobs,playerBlobs))
        {   blob.j = blob.j-2;
            changeBlob(blob,isMax,computerBlobs,playerBlobs);
            return true;
        }
        else return false;
    }



    /**
     * This function is used to move the blob upwards by one step and to the left by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is used to move the blob downwards by one step and to the left by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is used to move the blob upwards by one step and to the right by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is used to move the blob upwards by two steps and to the right by one step.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob upwards by two steps and to the right by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob upwards by one step and to the left by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is used to move the blob upwards by two steps and to the left by one step.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob upwards by two steps and to the left by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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

    /**
     * This function is used to move the blob downwards by one step and to the right by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is used to move the blob downwards by two steps and to the right by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob downwards by one step and to the left by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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


    /**
     * This function is used to move the blob downwards by two steps and to the left by two steps.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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


    /**
     * This function is used to move the blob downwards by two steps and to the right by one step.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */
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



    /**
     * This function is used to move the blob downwards by one step and to the left by one step.
     * @param blob is the blob that you want to move.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     * */

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



    /**
     * This function is the minimax algorithm.
     * @param depth is the tree layer that want to stop in.
     * @param isMax is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns the score.
     * */
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
                if(moveUp1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {

                    int b = minimax(depth + 1, false,fakeComputerBlobs,fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();

                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUp2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveRight1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveLeft1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUpRight1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUpLeft1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDownRight1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDownLeft1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp1Right2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp1Left2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp2Right1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp2Left1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown1Left2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown1Right2(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown2Right1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown2Left1(computerblob, true,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDown2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveRight1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveRight2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveLeft1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveLeft2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUpRight1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpRight2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUpLeft1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveUpLeft2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDownRight1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();





                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownRight2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDownLeft1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
                {
                    int b = minimax(depth+1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();




                fakeComputerBlobs.addAll(computerBlobs);
                fakePlayerBlobs.addAll(playerBlobs);
                if(moveDownLeft2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp1Right2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp1Left2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp2Right1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveUp2Left1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown1Left2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown1Right2(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown2Right1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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
                if(moveDown2Left1(computerblob, false,fakeComputerBlobs,fakePlayerBlobs))
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


    /**
     * This function is used to determine which move is the best. It picks the best blob to move and the best movement
     * for the selected blob.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs is the player's blob list.
     * @return Function returns a move object that contains the position of the selected blob, and the best movement
     * */
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
            if(moveUp1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
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
            if(moveUp2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=2;
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
            if(moveDown1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
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
            if(moveDown2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                computerblob.i-=2;
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
            if(moveRight1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
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
            if(moveRight2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.j-=2;

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
            if(moveLeft1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
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
            if(moveLeft2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.j+=2;

                if (b > best)
                {best = b;
                movement = "LEFT2";
                bestblob.i = computerblob.i;
                bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUpRight1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                    movement = "UP1RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp1Right2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=1;
                computerblob.j-=2;

                if (b > best)
                {best = b;
                    movement = "UP1RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp2Right1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=2;
                computerblob.j-=1;

                if (b > best)
                {best = b;
                    movement = "UP2RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUpRight2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=2;
                computerblob.j-=2;

                if (b > best)
                {best = b;
                    movement = "UP2RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUpLeft1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                    movement = "UP1LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp2Left1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=2;
                computerblob.j+=1;

                if (b > best)
                {best = b;
                    movement = "UP2LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUp1Left2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=1;
                computerblob.j+=2;

                if (b > best)
                {best = b;
                    movement = "UP1LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveUpLeft2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i+=2;
                computerblob.j+=2;

                if (b > best)
                {best = b;
                    movement = "UP2LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDownRight1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                    movement = "DOWN1RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown1Right2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=1;
                computerblob.j-=2;

                if (b > best)
                {best = b;
                    movement = "DOWN1RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown2Right1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=2;
                computerblob.j-=1;

                if (b > best)
                {best = b;
                    movement = "DOWN2RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();





            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDownRight2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=2;
                computerblob.j-=2;

                if (b > best)
                {best = b;
                    movement = "DOWN2RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDownLeft1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best)
                {best = b;
                    movement = "DOWN1LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown2Left1(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=2;
                computerblob.j+=1;

                if (b > best)
                {best = b;
                    movement = "DOWN2LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDown1Left2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=1;
                computerblob.j+=2;

                if (b > best)
                {best = b;
                    movement = "DOWN1LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;}
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            fakeComputerBlobs.addAll(computerBlobs);
            fakePlayerBlobs.addAll(playerBlobs);
            if(moveDownLeft2(computerblob,true,fakeComputerBlobs,fakePlayerBlobs))
            {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                computerblob.i-=2;
                computerblob.j-=2;

                if (b > best)
                {best = b;
                    movement = "DOWN2LEFT2";
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


