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

    /** Figures out what type of piece needs to be added at (i, j) on the
     *  gameboard and then adds it to the board's pieces array.
     */
    private void addPiece(int i, int j) {
        // determine whether a piece is fire or not
        boolean isFire = false;
        String type = "";
        if (j < 3) {
            isFire = true;
        } else if (j > 4) {
            isFire = false;
        } else {
            pieces[i][j] = null;
            return;
        }
        // Determine what kind of piece we need or if we don't need one at all
        if (j == 0 || j == boardSize - 1) {
            type = "pawn";
        } else if (j == 1 || j == boardSize - 2) {
            type = "shield";
        } else if (j == 2 || j == boardSize - 3) {
            type = "bomb";
        } else {
            pieces[i][j] = null;
            return;
        }
        // add the piece
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
