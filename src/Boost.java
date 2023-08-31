public class Boost extends Mappable {

    //the symbol of a boost on the map will be a star.
    public String getSymbol() {
        return "✧";
    }

    //boosts are NOT attackable.
    public boolean isAttackable() {
        return false;
    }

    //to initialize a boost, it just needs a location on the map.
    public Boost(int r, int c) {
        super(r, c);
    }

}
