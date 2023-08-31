public class Door extends Mappable {
    //the symbol of a door on the map is a flower.
    public String getSymbol() {
        return "✿";
    }

    //Doors are NOT attackable.
    public boolean isAttackable() {
        return false;
    }

    //to create a door, it just needs a location on the map.
    public Door(int r, int c) {
        super(r, c);
    }
}
