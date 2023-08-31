public abstract class Mappable {
    //instance variables storing the row and column
    private int curRow;
    private int curCol;

    //constructor for mappable that just needs a location.
    public Mappable(int r, int c) {
        curRow = r;
        curCol = c;
    }

    //made for making an enemy in the character class.
    public Mappable(int r, int c, String n) {
        curRow = r;
        curCol = c;

    }

    //requires extensions of this class to have a getSymbol method. (what symbol they are represented by on the map)
    public abstract String getSymbol();

    //getters and setters
    public int getCurRow() {
        return curRow;
    }

    public int getCurCol() {
        return curCol;
    }

    public void setCurRow(int curRow) {
        this.curRow = curRow;
    }

    public void setCurCol(int curCol) {
        this.curCol = curCol;
    }

    //requires extensions of this class to have a isAttackable method. determines whether they are able to attack or be attacked.
    public abstract boolean isAttackable();
}
