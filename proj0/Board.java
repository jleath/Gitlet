public class Board {
    private int boardSize = 8;
    private Piece[][] pieces;
 
    /** Board Constructor
     *  @param shouldBeEmpty Represents whether a board is empty or not.
     */
    public Board(boolean shouldBeEmpty) {
        pieces = new Piece[boardSize][boardSize];
        for (int i = 0; i < boardSize; i += 1) {
            for (int j = 0; j < boardSize; j += 1) {
                if (shouldBeEmpty == true) {
                    pieces[i][j] = null;
                } else if ((i + j) % 2 == 0) {
                    addPiece(i, j);
                }
            }
        }  
    }
    //TODO
    /** Returns the piece located at board[x][y]. */
    public Piece pieceAt(int x, int y) {
        return null;
    }
    //TODO
    /** Places a Piece, P, at position (x, y) on the game board.
     *  If (x, y) is an invalid position, or P is null, does nothing.
     *  If P is already on the board, removes from its original position first.
     *  Will replace any existing piece at (x, y)
     */
    public void place(Piece p, int x, int y) {
        return;
    }
    //TODO
    /** Removes the piece at position (x, y) from the game board and returns it.
     *  If (x, y) is an invalid position, returns null and alerts the user.
     */
    public Piece remove(int x, int y) {
        return null;
    }

    /** Returns True if the starting piece at position (x, y) is a 
     *  fire piece, else false.
     */
    private boolean startsAsFire(int x, int y) {
        return y < 3;
    }

    /** Returns true if the starting piece at position (x, y) is a
     *  null piece, else false.
     */
    private boolean startsAsNull(int x, int y) {
        return (y >= 3 && y < boardSize - 3);
    }

    /** Determines the type of the starting piece at position (x, y).
     *  @return A string containing either "pawn", "shield", or "bomb".
     */
    private String getStartingType(int x, int y) {
        if (y == 0 || y == boardSize - 1)
            return "pawn";
        else if (y == 1 || y == boardSize - 2)
            return "shield";
        else if (y == 2 || y == boardSize - 3) 
            return "bomb";
        else
            return "";
    }

    /** Figures out what type of piece needs to be added at (i, j) on the
     *  gameboard and then adds it to the board's pieces array.
     */
    private void addPiece(int i, int j) {
        // determine whether a piece is fire or not
        if (startsAsNull(i, j)) {
            pieces[i][j] = null;
            return;
        }
        boolean isFire = startsAsFire(i, j); 
        String type = getStartingType(i, j);
        pieces[i][j] = new Piece(isFire, this, i, j, type);
    }

    // returns the size of the board, default is 8
    private int size() {
        return boardSize;
    }

    /** Draws a Board to the screen. */
    private void drawBoard() {
        for (int row = 0; row < boardSize; row += 1) {
            for (int col = 0; col < boardSize; col += 1) {
                // Handle squares
                if ((row + col) % 2 == 0)
                    StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else
                    StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(row + .5, col + .5, .5);
                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                // Handle pieces
                if (this.pieces[row][col] != null) {
                    drawPiece(pieces[row][col], row, col);
                }
            }
        }
    }

    /** Draws a piece on the board. */
    private void drawPiece(Piece p, int x, int y) {
        String imgFile = "";
        String crownedStr = "";
        String typeStr;
        if (p.isFire())
            typeStr = "fire";
        else
            typeStr = "water";
        if (p.isKing())
            crownedStr = "-crowned";
        if (p.isBomb())
            imgFile = "img/bomb-" + typeStr + crownedStr + ".png";
        else if (p.isShield())
            imgFile = "img/shield-" + typeStr + crownedStr + ".png";
        else
            imgFile = "img/pawn-" + typeStr + crownedStr + ".png";
        StdDrawPlus.picture(x + .5, y + .5, imgFile, 1, 1);
    }

    /** Creates and draws a board, then begins a game of bombcheckers. */
    public static void main(String[] args) {
        // Create a board
        Board gameBoard = new Board(false);
        StdDrawPlus.setXscale(0, gameBoard.size());
        StdDrawPlus.setYscale(0, gameBoard.size());
        while (true) {
            gameBoard.drawBoard();
            // Handle other events
            StdDrawPlus.show(100);
        }
    }
}
