package hu.unideb.kgsoft.scrabble;

/**
 * The <code>Field</code> class represents a field of the game board.
 * <code>Field</code> objects contain the specific properties a field of the
 * game board can have.
 */
public class Field {

    /**
     * The possible statuses of a field in a turn.
     */
    public enum Status {
        /**
         * Means, there is no tile on the field.
         */
        EMPTY,
        /**
         * Means, there is a tile on the field placed in the current turn, and
         * can be picked up.
         */
        MOVABLE,
        /**
         * Means, there is a tile on the field placed in one of the preceding
         * turns and can't be moved.
         */
        FIXED
    }

    /**
     * The possible point multipliers a field can give.
     */
    public enum Multiplier {
        /**
         * Means, the field has no multiplier.
         */
        NONE,
        /**
         * Means, the field gives a double multiplier to the value of a word of
         * which letter is newly placed on this field.
         */
        DWORD,
        /**
         * Means, the field gives a triple multiplier to the value of a word of
         * which letter is newly placed on this field.
         */
        TWORD,
        /**
         * Means, the field gives a double multiplier to the value of a letter
         * newly placed on this field.
         */
        DLETTER,
        /**
         * Means, the field gives a triple multiplier to the value of a letter
         * newly placed on this field.
         */
        TLETTER
    }

    /**
     * Constant, which is the <code>tileCode</code> of <code>Field</code>
     * objects with no tile on them.
     */
    public static final int NOTILE = -1;

    /**
     * The code of the tile on this field.
     */
    private int tileCode;
    /**
     * The status of this field in a turn.
     */
    private Status status;
    /**
     * The multiplier of the field.
     */
    private Multiplier multiplier;
    /**
     * The code of the tile which is replaced by a joker.
     */
    private int jokerTileCode;

    /**
     * Constructs a new <code>Field</code> object, and initializes it with no
     * tile and no multiplier.
     */
    public Field() {
        this.multiplier = Multiplier.NONE;
        this.tileCode = NOTILE;
        this.status = Status.EMPTY;
        this.jokerTileCode = NOTILE;
    }
    
    public Field(Status status, Multiplier multiplier, int tileCode, int jokerTileCode) {
        this.tileCode = tileCode;
        this.multiplier = multiplier;
        this.status = status;
        this.jokerTileCode = jokerTileCode;
    }

    /**
     * Returns the tile code of the field
     * 
     * @return the tile code of the field
     */
    public int getTileCode() {
        return tileCode;
    }

    /**
     * Sets the tile code of the field
     * 
     * @param tileCode
     *            the tile code for the field
     */
    public void setTileCode(int tileCode) {
        this.tileCode = tileCode;
    }

    /**
     * Returns the multiplier of the field.
     * 
     * @return the multiplier of the field
     */
    public Multiplier getMultiplier() {
        return multiplier;
    }

    /**
     * Sets the multiplier of the field.
     * 
     * @param multiplier
     *            the multiplier for the field
     */
    public void setMultiplier(Multiplier multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Returns the status of the field in a turn
     * 
     * @return the status of the field in a turn
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the field in a turn
     * 
     * @param status
     *            the status for the field in a turn
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the code of the tile which is replaced by a joker. Its value is
     * always <code>NOTILE</code>, unless a joker tile was placed on the field.
     * 
     * @return the code of the tile replaced by a joker
     */
    public int getJokerTileCode() {
        return jokerTileCode;
    }

    /**
     * Sets the code of the tile which is replaced by a joker. It should be
     * called when a joker tile is placed on the field, or picked up.
     * 
     * @param jokerTileCode
     *            the tile code to set
     */
    public void setJokerTileCode(int jokerTileCode) {
        this.jokerTileCode = jokerTileCode;
    }

}
