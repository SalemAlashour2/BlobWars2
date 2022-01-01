import java.util.ArrayList;

public class Board {

    char[][] board;
    int level;

    public Board(int row, int col, int level) {

        this.board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                this.board[i][j] = '_';
            }
        }

        Game.playerBlobs.add(new Blob(0, 0));
        Game.playerBlobs.add(new Blob(row-1, 0));
        Game.computerBlobs.add(new Blob(0, col-1));
        Game.computerBlobs.add(new Blob(row-1, col-1));

        board[0][0] = 'b';
        board[row-1][0] = 'b';
        board[0][col-1] = 'r';
        board[row-1][col-1] = 'r';

        this.level = level;
    }


    public void addToList(ArrayList<Blob> destination,ArrayList<Blob> source)
    {
        for (Blob blob:
             source) {
            destination.add(new Blob(blob.i, blob.j));
        }
    }



    // Insert Value In Empty Place (Needed For Player)
    public void insert(char value, Cell pos) {
        if (checkBoundedBoard(pos.i, pos.j))
            if (this.board[pos.i][pos.j] == '_') {
                this.board[pos.i][pos.j] = value;
                if (value == 'b') Game.playerBlobs.add(new Blob(pos.i, pos.j)) ;
                else Game.computerBlobs.add(new Blob(pos.i, pos.j)) ;
                paintBlob(value,pos);

            }
    }


    public void move(char value, Cell pos, Cell p_pos) {
        if (checkBoundedBoard(pos.i, pos.j))
            if (board[pos.i][pos.j] == '_') {
                board[p_pos.i][p_pos.j] = '_';
                board[pos.i][pos.j] = value;
                deleteBlob(value,pos,p_pos);
                paintBlob(value,pos);

            }


    }

    void deleteBlob(char blob,Cell pos,Cell p_pos){
        if (blob =='b'){
            for (Blob b: Game.playerBlobs){
                if (b.i == p_pos.i && b.j == p_pos.j){
                    Game.playerBlobs.remove(b);
                    break;
                }
            }
            Game.playerBlobs.add(new Blob(pos.i, pos.j));
        }
        else {
            for (Blob b: Game.computerBlobs){
                if (b.i == p_pos.i && b.j == p_pos.j){
                    Game.computerBlobs.remove(b);
                    break;
                }
            }
            Game.computerBlobs.add(new Blob(pos.i, pos.j));
        }
    }

    public void paintBlob(char blob, Cell pos) {
        int i = pos.i;
        int j = pos.j;


        if (blob == 'b') {
            for (int ii = 0; ii < Game.computerBlobs.size(); ) {

                Blob b = Game.computerBlobs.get(ii);
                boolean ok = false;
                if (checkBoundedBoard(i+1,j) && b.i == i + 1 && b.j == j) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i + 1][j] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j) && b.i == i - 1 && b.j == j) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i - 1][j] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i,j+1) && b.i == i && b.j == j + 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i][j + 1] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i,j-1) && b.i == i && b.j == j - 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i][j - 1] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i+1,j+1) &&b.i == i + 1 && b.j == j + 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i + 1][j + 1] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j-1) && b.i == i - 1 && b.j == j - 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i - 1][j - 1] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i+1,j-1) && b.i == i + 1 && b.j == j - 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i + 1][j - 1] = 'b';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j+1) && b.i == i - 1 && b.j == j + 1) {
                    Game.playerBlobs.add(new Blob(b.i, b.j));
                    Game.computerBlobs.remove(b);
                    board[i - 1][j + 1] = 'b';
                    ok = true;
                }
                if (!ok) ii++;
            }
        } else {
            for (int ii = 0; ii < Game.playerBlobs.size(); ) {
                Blob b = Game.playerBlobs.get(ii);
                boolean ok = false;
                if (checkBoundedBoard(i+1,j) && b.i == i + 1 && b.j == j) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);

                    board[i + 1][j] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j) && b.i == i - 1 && b.j == j) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i - 1][j] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i,j+1) && b.i == i && b.j == j + 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i][j + 1] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i,j-1) && b.i == i && b.j == j - 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i][j - 1] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i+1,j+1) && b.i == i + 1 && b.j == j + 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i + 1][j + 1] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j-1) && b.i == i - 1 && b.j == j - 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i - 1][j - 1] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i+1,j-1) && b.i == i + 1 && b.j == j - 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i + 1][j - 1] = 'r';
                    ok = true;
                }
                if (checkBoundedBoard(i-1,j+1) && b.i == i - 1 && b.j == j + 1) {
                    Game.computerBlobs.add(new Blob(b.i, b.j));
                    Game.playerBlobs.remove(b);
                    board[i - 1][j + 1] = 'r';
                    ok = true;
                }
                if (!ok) ii++;

            }

        }



    }

    /**
     * This function checks if the game reached the end.
     *
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the game reached the end and false otherwise.
     */
    boolean finish(ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        if (playerBlobs.size() == 0) {
            //  System.out.println("You lose.....you will never win :]");
            return true;
        } else if (computerBlobs.size() == 0) {
            //  System.out.println("You win.......Don't cheat next time!!");
            return true;
        }


        for (char[] chars : board) {
            for (int j = 0; j < board.length; j++) {
                if (chars[j] == '_')
                    return false;
            }
        }
        return true;
    }

    /**
     * This function is used in the minimax function.It checks if the computer is winning the game or not by comparing
     * computer's blob list length with the player's blob list length.
     *
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns an int that represents the score,that score signifies if the computer is winning or not;
     * if it's bigger than zero then the computer is winning, if it's smaller than zero then computer is losing,if it's
     * equal to zero then the game is reaching a draw status.
     */
    int evaluate(ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        int utility = 0;

        //TODO: WRITE EVALUATE CODE



        if (computerBlobs.size() > playerBlobs.size())
            utility=computerBlobs.size()-playerBlobs.size();

        if (computerBlobs.size() < playerBlobs.size())
            utility = playerBlobs.size()-computerBlobs.size();


        return utility;

    }

    /**
     * This function checks if the move is possible; it checks if the new position is taken by another blob, or it's
     * off board.
     *
     * @param blob          is the blob that is need to be moved.
     * @param i             is the location on the i axis.
     * @param j             is the location on the j axis.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the move is possible and false otherwise
     */
    boolean canMove(Blob blob, int i, int j, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        int distancei;
        int distancej;


        if(i<0 || i>7 || j<0 || j>7)
            return false;


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
     *
     * @param blob          is the blob that you want to check if any blob is around it.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     */

    void changeBlob(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        int distancei;
        int distancej;
        ArrayList<Blob> toBeRemoved = new ArrayList<>();

        if (isMax) {
            for (Blob playerBlob :
                    playerBlobs) {
                if (blob.i > playerBlob.i)
                    distancei = blob.i - playerBlob.i;
                else
                    distancei = playerBlob.i - blob.i;
                if (blob.j > playerBlob.j)
                    distancej = blob.j - playerBlob.j;
                else
                    distancej = playerBlob.j - blob.j;

                if (distancei <= 1 && distancej <= 1) {
                    toBeRemoved.add(playerBlob);
                    computerBlobs.add(playerBlob);

                }
            }
            playerBlobs.removeAll(toBeRemoved);
        } else {
            for (Blob computerBlob :
                    computerBlobs) {
                if (blob.i > computerBlob.i)
                    distancei = blob.i - computerBlob.i;
                else
                    distancei = computerBlob.i - blob.i;
                if (blob.j > computerBlob.j)
                    distancej = blob.j - computerBlob.j;
                else
                    distancej = computerBlob.j - blob.j;

                if (distancei <= 1 && distancej <= 1) {
                    toBeRemoved.add(computerBlob);
                    playerBlobs.add(computerBlob);

                }
            }
            computerBlobs.removeAll(toBeRemoved);
        }
    }


    /**
     * This function is used to move the blob upwards by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i - 1, blob.j);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i - 1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;

    }


    /**
     * This function is used to move the blob upwards by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        if (canMove(blob, blob.i - 2, blob.j, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob downwards by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveDown1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i + 1, blob.j);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i + 1, blob.j);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob downwards by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDown2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 2, blob.j, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob to the right by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveRight1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i, blob.j + 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i, blob.j + 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i, blob.j + 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob to the right by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveRight2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i, blob.j + 2, computerBlobs, playerBlobs)) {
            blob.j = blob.j + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by one step and to the right by two steps.
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp1Right2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 1, blob.j + 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 1;
            blob.j = blob.j + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }

    /**
     * This function is used to move the blob downwards by one step and to the right by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveDown1Right2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 1, blob.j + 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 1;
            blob.j = blob.j + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob to the left by one step. The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveLeft1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i, blob.j - 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i, blob.j - 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i, blob.j - 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob to left by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveLeft2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i, blob.j - 2, computerBlobs, playerBlobs)) {
            blob.j = blob.j - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by one step and to the left by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp1Left2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 1, blob.j - 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 1;
            blob.j = blob.j - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob downwards by one step and to the left by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDown1Left2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 1, blob.j - 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 1;
            blob.j = blob.j - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by one step and to the right by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUpRight1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j + 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i - 1, blob.j + 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i - 1, blob.j + 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob upwards by two steps and to the right by one step.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp2Right1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 2, blob.j + 1, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 2;
            blob.j = blob.j + 1;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by two steps and to the right by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUpRight2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 2, blob.j + 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 2;
            blob.j = blob.j + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by one step and to the left by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUpLeft1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i - 1, blob.j - 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i - 1, blob.j - 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i - 1, blob.j - 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob upwards by two steps and to the left by one step.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUp2Left1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 2, blob.j - 1, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 2;
            blob.j = blob.j - 1;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob upwards by two steps and to the left by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveUpLeft2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i - 2, blob.j - 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i - 2;
            blob.j = blob.j - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }

    /**
     * This function is used to move the blob downwards by one step and to the right by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDownRight1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j + 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i + 1, blob.j + 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i + 1, blob.j + 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob downwards by two steps and to the right by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDownRight2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 2, blob.j + 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 2;
            blob.j = blob.j + 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob downwards by one step and to the left by one step.
     * The function does not move the blob that was sent from
     * the parameter, but it spawns a new one.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDownLeft1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        Blob movedBlob;
        if (canMove(blob, blob.i + 1, blob.j - 1, computerBlobs, playerBlobs)) {
            if (isMax) {
                movedBlob = new Blob(blob.i + 1, blob.j - 1);
                computerBlobs.add(movedBlob);
                changeBlob(movedBlob, true, computerBlobs, playerBlobs);
                return true;
            } else {
                movedBlob = new Blob(blob.i + 1, blob.j - 1);
                playerBlobs.add(movedBlob);
                changeBlob(movedBlob, false, computerBlobs, playerBlobs);
                return true;
            }
        } else
            return false;
    }


    /**
     * This function is used to move the blob downwards by two steps and to the left by two steps.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveDownLeft2(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 2, blob.j - 2, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 2;
            blob.j = blob.j - 2;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob downwards by two steps and to the right by one step.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */
    boolean moveDown2Right1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 2, blob.j + 1, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 2;
            blob.j = blob.j + 1;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is used to move the blob downwards by one step and to the left by one step.
     *
     * @param blob          is the blob that you want to move.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns true if the movement was successful and false otherwise.
     */

    boolean moveDown2Left1(Blob blob, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {
        if (canMove(blob, blob.i + 2, blob.j - 1, computerBlobs, playerBlobs)) {
            blob.i = blob.i + 2;
            blob.j = blob.j - 1;
            changeBlob(blob, isMax, computerBlobs, playerBlobs);
            return true;
        } else return false;
    }


    /**
     * This function is the minimax algorithm.
     *
     * @param depth         is the tree layer that want to stop in.
     * @param isMax         is a boolean that tells if the game is being played on max or min; true is max,false is min.
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns the score.
     */
    int minimax(int depth, boolean isMax, ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        //TODO: COMPLETE MINIMAX FUNCTION

        int score;

        ArrayList<Blob> fakeComputerBlobs = new ArrayList<>();
        ArrayList<Blob> fakePlayerBlobs = new ArrayList<>();
        score = evaluate(computerBlobs, playerBlobs);

        if (finish(computerBlobs, playerBlobs))
            return score;
        if (depth == level)
            return score;

        // If Max Player
        if (isMax) {
            int best = -1000;

            for (Blob computerblob :
                    computerBlobs) {


                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {

                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }

                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }

                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1Right2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1Left2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2Right1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2Left1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1Left2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1Right2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2Right1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
                    if (b > best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2Left1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, false, fakeComputerBlobs, fakePlayerBlobs);
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
                    playerBlobs) {


                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {

                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();


                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveRight1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveRight2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveLeft1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveLeft2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpRight1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpRight2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpLeft1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;

                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUpLeft2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownRight1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;


                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownRight2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownLeft1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;
                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDownLeft2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1Right2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp1Left2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2Right1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveUp2Left1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1Left2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown1Right2(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2Right1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



                }
                fakeComputerBlobs.clear();
                fakePlayerBlobs.clear();



                addToList(fakeComputerBlobs,computerBlobs);
                addToList(fakePlayerBlobs,playerBlobs);
                if (moveDown2Left1(new Blob(computerblob.i,computerblob.j), false, fakeComputerBlobs, fakePlayerBlobs)) {
                    int b = minimax(depth + 1, true, fakeComputerBlobs, fakePlayerBlobs);
                    if (b < best)
                        best = b;



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
     *
     * @param computerBlobs is the computer's blob list.
     * @param playerBlobs   is the player's blob list.
     * @return Function returns a move object that contains the position of the selected blob, and the best movement
     */
    Move bestMove(ArrayList<Blob> computerBlobs, ArrayList<Blob> playerBlobs) {

        int best = -1000;


        //TODO: COMPLETE BEST MOVE FUNCTION

        ArrayList<Blob> fakeComputerBlobs = new ArrayList<>();
        ArrayList<Blob> fakePlayerBlobs = new ArrayList<>();
        String movement = "UP1";
        Blob bestblob = new Blob(0, 0);


        for (Blob computerblob : computerBlobs) {


            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUp1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {

                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                if (b > best) {
                    best = b;
                    movement = "UP1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }


            }

            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUp2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);


                if (b > best) {
                    best = b;
                    movement = "UP2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }


            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "DOWN1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }






            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);

                if (b > best) {
                    best = b;
                    movement = "DOWN2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }






            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);


                if (b > best) {
                    best = b;
                    movement = "RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {

                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);


                if (b > best) {
                    best = b;
                    movement = "LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                    System.out.println(best);
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUpRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "UP1RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUp1Right2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "UP1RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUp2Right1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);


                if (b > best) {
                    best = b;
                    movement = "UP2RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUpRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "UP2RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUpLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "UP1LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUp2Left1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "UP2LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();




            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);

            if (moveUp1Left2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                //System.out.println("NOW ");
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);


                if (b > best) {
                    best = b;

                    movement = "UP1LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;

                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveUpLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "UP2LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;

                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDownRight1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "DOWN1RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown1Right2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN1RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown2Right1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN2RIGHT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDownRight2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN2RIGHT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDownLeft1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);
                if (b > best) {
                    best = b;
                    movement = "DOWN1LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown2Left1(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN2LEFT1";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }
            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDown1Left2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN1LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }




            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();



            addToList(fakeComputerBlobs,computerBlobs);
            addToList(fakePlayerBlobs,playerBlobs);
            if (moveDownLeft2(new Blob(computerblob.i,computerblob.j), true, fakeComputerBlobs, fakePlayerBlobs)) {
                int b = minimax(0, false, fakeComputerBlobs, fakePlayerBlobs);



                if (b > best) {
                    best = b;
                    movement = "DOWN2LEFT2";
                    bestblob.i = computerblob.i;
                    bestblob.j = computerblob.j;
                }
            }




            fakeComputerBlobs.clear();
            fakePlayerBlobs.clear();


        }

        return new Move(bestblob, movement);
    }

    /**
     * represent the movement after best move of MiniMax algorithm
     * with 24 move
     */
    void takeMove() {

        Move moveTo = this.bestMove(Game.computerBlobs,Game.playerBlobs);
        int i = moveTo.blob.i;
        int j = moveTo.blob.j;


        System.out.println(moveTo.movement);
        System.out.println(moveTo.blob.i + "\t" +moveTo.blob.j);

        switch (moveTo.movement) {
            case "UP1" -> insert('r', new Cell(i - 1, j));
            case "UP2" -> move('r', new Cell(i - 2, j), new Cell(i, j));
            case "DOWN1" -> insert('r', new Cell(i + 1, j));
            case "DOWN2" -> move('r', new Cell(i + 2, j), new Cell(i, j));
            case "RIGHT1" -> insert('r', new Cell(i, j + 1));
            case "RIGHT2" -> move('r', new Cell(i, j + 2), new Cell(i, j));
            case "LEFT1" -> insert('r', new Cell(i, j - 1));
            case "LEFT2" -> move('r', new Cell(i, j - 2), new Cell(i, j));
            case "UP1RIGHT1" -> insert('r', new Cell(i - 1, j + 1));
            case "UP2RIGHT1" -> move('r', new Cell(i - 2, j + 1), new Cell(i, j));
            case "UP1RIGHT2" -> move('r', new Cell(i - 1, j + 2), new Cell(i, j));
            case "UP2RIGHT2" -> move('r', new Cell(i - 2, j + 2), new Cell(i, j));
            case "DOWN1RIGHT1" -> insert('r', new Cell(i + 1, j + 1));
            case "DOWN2RIGHT1" -> move('r', new Cell(i + 2, j + 1), new Cell(i, j));
            case "DOWN1RIGHT2" -> move('r', new Cell(i + 1, j + 2), new Cell(i, j));
            case "DOWN2RIGHT2" -> move('r', new Cell(i + 2, j + 2), new Cell(i, j));
            case "UP1LEFT1" -> insert('r', new Cell(i - 1, j - 1));
            case "UP2LEFT1" -> move('r', new Cell(i - 2, j - 1), new Cell(i, j));
            case "UP1LEFT2" -> move('r', new Cell(i - 1, j - 2), new Cell(i, j));
            case "UP2LEFT2" -> move('r', new Cell(i - 2, j - 2), new Cell(i, j));
            case "DOWN1LEFT1" -> insert('r', new Cell(i + 1, j - 1));
            case "DOWN2LEFT1" -> move('r', new Cell(i + 2, j - 1), new Cell(i, j));
            case "DOWN1LEFT2" -> move('r', new Cell(i + 1, j - 2), new Cell(i, j));
            case "DOWN2LEFT2" -> move('r', new Cell(i + 2, j - 2), new Cell(i, j));
        }



    }

    void printBoard() {
        for (char[] chars : board) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(chars[j] + "\t");
            }
            System.out.println();
        }
    }


    /**
     * return number of rows
     *
     * @return size as int value
     */
    private int getRowLength() {
        return board.length;
    }

    /**
     * return number of columns
     *
     * @return size as int value
     */
    private int getColLength() {
        return board[0].length;
    }

    /**
     * this function return ture if the indexes is in board
     *
     * @param i is row index
     * @param j is column index
     * @return boolean value
     */
    public boolean checkBoundedBoard(int i, int j) {


        return (i >= 0 && i < getRowLength()) || (j >= 0 && j < getColLength());
    }

}


