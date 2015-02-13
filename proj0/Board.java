public class Board {
    private int boardSize = 8;
    private Piece[][] pieces;
    private boolean pieceSelected = false;
    private boolean pieceMoved = false;
    private boolean pieceCaptured = false;
    private int currentPlayer = 0;
    private int selectedXPos;
    private int selectedYPos;
 
    /** Board Constructors
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

    public Board() {
        this(false);
    }

    /** Returns whether or not the current player can end their turn. */
    public boolean canEndTurn() {
        return pieceMoved;
    }

    /** Ends the turn. Switches players, finishes capturing on the selected piece,
     *  and sets all flags off.
     */
    public void endTurn() {
        currentPlayer = (currentPlayer + 1) % 2;
        pieceMoved = false;
        pieceCaptured = false;
        pieceSelected = false;
        if (pieceAt(selectedXPos, selectedYPos) != null) {
            pieceAt(selectedXPos, selectedYPos).doneCapturing();
        }
        System.out.println("Ended player " + currentPlayer + "'s turn");
    }

    /** Selects the square at (X, Y). */
    public void select(int x, int y) {
        if (pieceAt(x, y) == null) {
            pieceAt(selectedXPos, selectedYPos).move(x, y);    
            pieceMoved = true;
        }
        pieceSelected = true;
        selectedXPos = x;
        selectedYPos = y;
    }

    /** Returns true if the square at position (x, y) can be selected by
     *  the current player, else false.
     */
    public boolean canSelect(int x, int y) {
        Piece p = pieceAt(x, y);
        if (p != null && !isPlayersPiece(p)) {
            return false;
        }
        if (p != null) {
            return pieceSelected == false || pieceMoved == false;
        }
        if (pieceSelected == false) {
            return false;
        }
        if (pieceAt(selectedXPos, selectedYPos) != null && pieceAt(selectedXPos, selectedYPos).hasCaptured()) {
            return isCaptureMove(selectedXPos, selectedYPos, x, y);
        }
        if (pieceMoved == false) {
            return isValidMove(selectedXPos, selectedYPos, x, y) ||
                   isCaptureMove(selectedXPos, selectedYPos, x, y);
        }
        return false;
    }

    /** Returns the piece located at board[x][y]. */
    public Piece pieceAt(int x, int y) {
        return pieces[x][y];
    }

    /** Places a Piece, P, at position (x, y) on the game board.
     *  If (x, y) is an invalid position, or P is null, does nothing.
     *  If P is already on the board, removes from its original position first.
     *  Will replace any existing piece at (x, y)
     */
    public void place(Piece p, int x, int y) {
        if (p == null || x < 0 || x >= boardSize || y < 0 || y >= boardSize)
            return;
        pieces[x][y] = p;
    }

    /** Removes the piece at position (x, y) from the game board and returns it.
     *  If (x, y) is an invalid position, returns null and alerts the user.
     */
    public Piece remove(int x, int y) {
        if (x < 0 || x >= boardSize || y < 0 || y >= boardSize) {
            System.out.println("Invalid position: (" + x + ", " + y + ")");
            return null;
        }
        if (pieces[x][y] == null) {
            System.out.println("No piece at position (" + x + ", " + y + ")");
            return null;
        }
        Piece oldPiece = pieceAt(x, y);
        pieces[x][y] = null;
        return oldPiece;
    }

    /** Returns true if moving the piece at position XI, YI to XF, YF will result
     *  in a piece being captured, else false.
     */
    private boolean isCaptureMove(int xi, int yi, int xf, int yf) {
        if (correctDirection(xi, yi, xf, yf) && pieceAt(xf, yf) == null) {
            int capturePosX = (int)((xi+xf) / 2);
            int capturePosY = (int)((yi+yf) / 2);
            Piece pieceToCapture = pieceAt(capturePosX, capturePosY);
            return Math.abs(xi - xf) == 2 && Math.abs(yi - yf) == 2 &&
                   pieceToCapture != null && pieceToCapture.side() != pieceAt(xi, yi).side();
        } else {
            return false;
        }
    }

    /** Returns true if moving a piece from XI, YI to XF, YF is a move in the
     *  correct direction.
     */
    private boolean correctDirection(int xi, int yi, int xf, int yf) {
        if (pieceAt(xi, yi).isKing()) {
            return true;
        } else if (pieceAt(xi, yi).isFire()) {
            return yf - yi > 0;
        } else {
            return yf - yi < 0;
        }
    }

    /** Returns true if the move from position XI, YI to XF, YF is a valid move
     *  else false.
     */
    private boolean isValidMove(int xi, int yi, int xf, int yf) {
        Piece p = pieceAt(xi, yi);
        if (correctDirection(xi, yi, xf, yf) && pieceAt(xf, yf) == null) {
            return Math.abs(xi-xf) == 1 && Math.abs(yi-yf) == 1;
        } else {
            return false;
        }
        
    }

    /** Returns true if the piece, P is the current player's piece, else false.
     */
    private boolean isPlayersPiece(Piece p) {
        return p.side() == currentPlayer;
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
                if ((row + col) % 2 == 0) {
                    StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                }
                else {
                    StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }

                StdDrawPlus.filledSquare(row + .5, col + .5, .5);

                StdDrawPlus.setPenColor(StdDrawPlus.WHITE);

                // Handle pieces
                if (pieces[row][col] != null) {
                    drawPiece(pieces[row][col], row, col);
                }
            }
        }
    }

    /** Returns the string "fire" if P is a fire piece, else "water"
     */
    private String getElementString(Piece p) {
        if (p.isFire())
            return "fire";
        else
            return "water";
    }

    /** Returns a string containing the type of the piece P.
     *  @return "bomb", "shield", or "pawn"
     */
    private String getTypeString(Piece p) {
        if (p.isBomb())
            return "bomb";
        if (p.isShield())
            return "shield";
        else
            return "pawn";
    }

    /** Constructs a path to a game piece image, and then draws that
     *  image on the board in the correct position.
     */
    private void drawPiece(Piece p, int x, int y) {
        String imgDir = "img/";
        String imgSuffix = ".png";
        String crownedStr = "";
        if (p.isKing())
            crownedStr = "-crowned";
        String elementStr = getElementString(p);
        String typeStr = getTypeString(p);
        String imgFile = imgDir + typeStr + "-" + 
                         elementStr + crownedStr + imgSuffix;
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
            if (StdDrawPlus.mousePressed()) {
                int x = (int)StdDrawPlus.mouseX();
                int y = (int)StdDrawPlus.mouseY();
                if (gameBoard.canSelect(x, y)) {
                    gameBoard.select(x, y);
                    StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                    StdDrawPlus.filledSquare(x + .5, y + .5, .5);
                    if (gameBoard.pieceAt(x, y) != null) {
                        gameBoard.drawPiece(gameBoard.pieceAt(x, y), x, y);
                    }
                }
            }
            if (StdDrawPlus.isSpacePressed()) {
                if (gameBoard.canEndTurn()) {
                    gameBoard.endTurn();
                }
            }
            StdDrawPlus.show(15);
        }
    }
}
