public class Character extends Mappable {
    private int strength;
    private int defense;
    private int health;
    private String type;
    private int strBoost;

    //constructor for a character. just requires a location where the character should be placed.
    //strength is randomized between 5-30, defense is set to 15, and the health is 25.
    public Character(int r, int c) {
        super(r, c);
        strength = (int) ((Math.random() * 26) + 5); //5-30
        defense = 15;
        health = defense + 10;

    }

    //constructor for a character enemy. requires a location where the character should be placed and string to occupy the last parameter to
    //print a different symbol on the map. strength is randomized between 5-30, defense is set to 15, and the health is 25.
    public Character(int r, int c, String t) {
        super(r, c, t);
        strength = (int) ((Math.random() * 26) + 5); //5-30
        defense = 15;
        health = defense + 10;
        type = "enemy";

    }

    //getter for type (to check if it as enemy)
    public String getType() {
        return type;
    }

    //determines what symbol will be printed on the map. An 'E' if it is an enemy and a 'C' if it is a player.
    public String getSymbol() {
        if (this.getType() != null) {
            return "E";
        } return "C";
    }

    //allows me to SOP 'player' which shows the stats of the player.
    public String toString() {
        return "Health: " + health + "\n" + "Strength: " + strength + "\n" + "Defense: " + defense;
    }

    //removes the object from the map to avoid leaving a trail/shadow. it will check if with the canMove method first in order to move the player according to what
    //they inputted. will set the current location of the player to whatever direction they decided. if canMove returns false, the move method will not move the player.
    public void move(String direction, Map room) {
        room.removeObject(this);
            if (direction.equalsIgnoreCase("W")) {
                if (room.canMove(this.getCurRow() - 1, this.getCurCol())) {
                    this.setCurRow(this.getCurRow() - 1);
                }
            } else if (direction.equalsIgnoreCase("S")) {
                if (room.canMove(this.getCurRow() + 1, this.getCurCol())) {
                    this.setCurRow(this.getCurRow() + 1);
                }
            } else if (direction.equalsIgnoreCase("A")) {
                if (room.canMove(this.getCurRow(), this.getCurCol() - 1)) {
                    this.setCurCol(this.getCurCol() - 1);
                }
            } else if (direction.equalsIgnoreCase("D")) {
                if (room.canMove(this.getCurRow(), this.getCurCol() + 1)) {
                    this.setCurCol(this.getCurCol() + 1);
                }
            } else {
                System.out.println("Please select a valid direction.");
            }
        room.placeObject(this);
    }

    // many getters and setters
    public int getStrength() {
        return strength;
    }

    public int getDefense() {
        return defense;
    }

    public int getHealth() {
        return health;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isAttackable() {
        return true;
    }

    public int getStrBoost() {
        return strBoost;
    }

    public void setStrBoost(int strBoost) {
        this.strBoost = strBoost;
    }

    //attack method that randomizes an attack between 5 to 30.
    //randomizes the opponent's defense & checks if there is a strength boost that can increase the damage.
    //damage done is calculated and the numbers of damage, defense, damage done, and opponent's health is printed.
    //sets the health of the opponent to their current health minus the damage done
    public void attackOpp(Character opp) {
        int damage = (int) ((Math.random() * (this.strength - 4)) + 5);
        int oppDef = (int) ((Math.random() * (opp.getDefense() - 1)) + 2);

        if (checkStrBoost()) {
            System.out.println("Damage before: " + damage);
            damage += 5;
            System.out.println("Damage with strength BOOST: " + damage);
        }

        int damageDone = (damage - oppDef);

        if (damageDone < 0) {
            damageDone = 0;
        }

        if (oppDef + opp.health < damage) {
            opp.setHealth(0);
        }
        System.out.println("Attack: " + damage);
        System.out.println("Opponent's Defense: " + oppDef);
        System.out.println("Attack damage done: " + damageDone);

        opp.setHealth(opp.getHealth() - damageDone);
        if (opp.getHealth() < 0) {
            opp.setHealth(0);
        }
        System.out.println("Opponent's Health: " + opp.getHealth() + "\n");
    }

    //if there is a number greater than 0 set as the strength boost of a character, the method will return true. if not, it will return false.
    public boolean checkStrBoost() {
        if (this.getStrBoost() != 0) {
            return true;
        } else {
            return false;
        }
    }


}
