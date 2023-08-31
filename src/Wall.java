public class Wall extends Mappable {
    //to create a wall, you need a lcation. row and column.
    public Wall(int r, int c) {
        super(r, c);
    }

    //when placed on the map, walls will be represented by 'X'
    public String getSymbol() {
        return "X";
    }

    //Walls are NOT attackable.
    public boolean isAttackable() {
        return false;
    }
}
