package hu.unideb.kgsoft.scrabble;

/**
 * An interface defining common services a view class for the game should
 * provide. The abstract methods defined here will be called by the controller
 * class to update the view.
 */
public interface GameView {
    /**
     * Draws an image of the tile, which is located on the board at the given
     * row and column indices with the given tile code. Calculates the x and y
     * coordinates corresponding to the row and column indices on the board, to
     * draw the image on the graphics surface.
     * 
     * @param tileCode
     *            the unique code of the tile, that has to be drawn
     * @param row
     *            the row index of the tile on the board
     * @param col
     *            the column index of the tile on the board
     */
    public void drawTileOnBoard(int tileCode, int row, int col);
    
    public void drawTileOnTray(int tileCode, int index);
    
    public void drawTileInHand(int tileCode, double mouseX, double mouseY);

    /**
     * Draws the game board on a graphics surface. It serves as a background for
     * drawing the letter tiles. It is expected from the implementing class,
     * that the graphical representation of the tiles on the game board will
     * reflect their actual position.
     */
    public void drawBoard();

    // TODO These probably won't be necessary.
    /**
     * Returns the width of the graphics surface on which the tiles and the game
     * board can be drawn.
     * 
     * @return the width of the graphics surface
     */
    public double getWidth();

    /**
     * Returns the height of the graphics surface on which the tiles and the
     * game board can be drawn.
     * 
     * @return the height of the graphics surface
     */
    public double getHeight();

    /**
     * The view class updates the informations about the state of the game on
     * the screen. Uses the values from the provided {@link GameState} object as
     * parameter.
     * 
     * @param gameState
     *            the <code>GameState</code> object
     */
    public void updateGameStateInfo(GameState gameState);

}
