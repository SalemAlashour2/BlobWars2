public class Move {

    Blob blob;
    String movement;

    public Move()
    {

    }

    /**
     *  Blob move model
     * @param blob blob mode {@link Blob}
     * @param movement type of movement as string value {@link String}
     */
    public Move(Blob blob , String movement)
    {
        this.blob = blob;
        this.movement = movement;
    }

}
