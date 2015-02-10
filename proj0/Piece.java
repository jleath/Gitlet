/** A game piece for a game of checkers.
 *  isFire - A boolean value representing whether a piece is 
 *           fire or water.
 *  b - a Board value that the piece is on.
 *  x - An int representing the piece's horizontal position.
 *  y - An int representing the piece's vertical position.
 *  type - A string representing the piece's type, "pawn", "bomb", or
 *         "shield".
 *
 *  @author Joshua Leath
 */
public class Piece {
    boolean isFire;
    Board board;
    int xPos;
    int yPos;
    String type;
    boolean hasCaptured;
    
    /** Piece constructor
     *  @param isFire Represents whether a piece is fire or rain.
     *  @param b The board that the piece is played on.
     *  @param x The horizontal position of the piece.
     *  @param y The vertical position of the piece.
     *  @param type The piece's type: "pawn", "bomb", or "shield".
     */
    public Piece(boolean isFire, Board b, int x, int y, String type) {
        this.isFire = isFire;
        board = b;
        xPos = x;
        yPos = y;
        this.type = type;
        hasCaptured = false;
    }

    /** Prints out the details of a piece.
     */
    public void printPiece() {
        String element = "";
        if (this.isFire())
            element = "fire";
        else
            element = "water";
        System.out.println(element + " " + type + " " + xPos + " " + yPos);
    }

    /** @return True if the piece is a fire piece, else False.
     */
    public boolean isFire() {
        return this.isFire;
    }

    /** @return 0 if the piece is a fire piece, else 1.
     */
    public int side() {
        if (isFire)
            return 0;
        else
            return 1;
    }

    /** @return True if the piece is a bomb piece, else false.
     */
    public boolean isBomb() {
        return type.equals("bomb");
    }

    /** @return True if the piece is a shield piece, else false.
     */
    public boolean isShield() {
        return type.equals("shield");
    }

    /** @return True if the piece is a king piece, else false.
     */
    public boolean isKing() {
        return type.equals("king");
    }

    /** @return True if the piece is a pawn piece, else false.
     */
    public boolean isPawn() {
        return type.equals("pawn");
    }

    /** @return True if this piece has captured another piece this turn
     *          else false.
     */
    public boolean hasCaptured() {
        return hasCaptured;
    }

    /** Called at the end of each turn on the Piece that moved, ensures
     *  that the pieces hasCaptured value returns to false.
     */
    public void doneCapturing() {
        hasCaptured = false;
    }
}
