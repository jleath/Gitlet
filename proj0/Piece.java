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
    boolean isKinged = false;
    
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

    /** Moves the piece to position (x, y).  Assumes the move is valid.
     *  captures any intermediate piece if applicable.
     */
    public void move(int x, int y) {
        if (Math.abs(y-yPos) == 2) {
            int toRemoveX = (int)((xPos + x) / 2);
            int toRemoveY = (int)((yPos + y) / 2);
            board.remove(toRemoveX, toRemoveY);
            hasCaptured = true;
        }
        board.place(this, x, y);
        board.remove(xPos, yPos);
        xPos = x;
        yPos = y;
        if ((isFire() && yPos == 7) || !isFire() && yPos == 0) {
            isKinged = true;
        }
        if (this.isBomb() && hasCaptured == true) {
            System.out.println("starting explosion.");
            int lowX = xPos - 1;
            int highX = xPos + 1;
            int lowY = yPos - 1;
            int highY = yPos + 1;

            System.out.println("lowX =" + lowX);
            System.out.println("highX =" + highX);
            System.out.println("lowY =" + lowY);
            System.out.println("highY =" + highY);

            for (int row = lowX; row < 8 && row <= highX; row += 1) {
                for (int col = lowY; col < 8 && col <= highY; col += 1) {
                    System.out.println("Handling piece at (" + col + ", " + row + ").");
                    if (board.pieceAt(col, row) != null &&
                        board.pieceAt(col, row).side() != this.side() && 
                        !board.pieceAt(col, row).isShield())
                        board.remove(col, row);
                }
            }
            board.remove(xPos, yPos);
        }
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
        return isKinged;
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
