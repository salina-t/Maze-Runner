import java.util.Scanner;
public class GameRunner {
    public static void main (String[] args) {
        //Setting up room 1
        Map room1 = new Map("room1");
        Character player = new Character(2, 2);
        Character enemy = new Character(5, 1, "e");
        Boost strBoost = new Boost(1, 1);
        Door room1e = new Door(6,1);
        room1.placeObject(enemy);
        room1.placeObject(player);
        room1.placeObject(strBoost);
        room1.printMap();

        //SAVE VARIABLES (SAVE STATS OF PLAYER WHEN GOING TO NEW ROOM)
        int saveHP = 0;
        int saveStr = 0;
        int saveDef = 0;
        int saveStrB = 0;

        Scanner scan = new Scanner(System.in);
        System.out.println("╔══════════《✧》═════════╗\n \t\t WELCOME.\t\t \n╚══════════《✧》═════════╝");
        System.out.println("\nYour mission is to kill 2 enemy entities. There will be one enemy in each map.");
        System.out.println("═══════《KEY》═══════\nX = Wall\nC = Player (You)\nE = Enemy\n✧ = Boost that increases player's statistics\n✿ = Exit door to new map\n\n");

        System.out.println("Your character's statistics:");
        System.out.println(player + "\n");
        room1.printMap();
        System.out.println("What direction would you like to move? WASD");
        String input = scan.next();

        boolean endBattle = false;
        boolean lever1 = false;
        boolean lever2 = false;
        boolean newRound = false;

        // this will make sure that the battle has not ended and that the enemy is still alive so that the player can interact with enemies, boosts, and move.
        // this manages the majority of the gameplay in room 1.
        while (!endBattle && enemy.getHealth() >= 0) {
            player.move(input, room1);
            room1.printMap();
            //if the player is near the boost in room 1, the player will collect the boost and receive 5 extra strength damage points added on top of their randomized
            // attack damage. it will remove the symbol of the boost from the map once collected.
            if (room1.boostInRange(player, strBoost)) {
                System.out.println("Boost collected!...\nYou received +5 to player's strength.");
                room1.removeObject(strBoost);
                player.setStrBoost(5);
            }
            //if the player is near the enemy in room1, the player will be presented with a choice to either fight the enemy or quit the game.
            if (room1.enemyInRange(player, enemy)) {
                System.out.println("You ran into an enemy!");
                //in order to fight the enemy in room1, the battle must not be ended and the lever for round one must be false. the two levers will be switched after
                // when the player or enemy dies--the battle ends.
                while (!endBattle && !lever1) {
                    System.out.println("\n (1) Attack! \n (2) Quit Game.");
                    int select = Integer.valueOf(scan.next());

                    //if the user chooses to fight, the player will attack the enemy first, first checking if both the player and enemy are alive first.
                    if (select == 1) {
                        if (player.getHealth() > 0 && enemy.getHealth() > 0) {
                            System.out.println("Player attacks enemy!...");
                            player.attackOpp(enemy);
                        }
                        // after this attack, it will check if the player and enemy are still alive after the first attack, then the enemy will attack the player.
                        if (enemy.getHealth() > 0 && player.getHealth() > 0) {
                            System.out.println("Enemy attacks player!...");
                            enemy.attackOpp(player);

                        }
                        //if either player or enemy is dead, the battle will end. the loop will end so that there will not be a next round.
                        if (player.getHealth() <= 0 || enemy.getHealth() <= 0) {
                            endBattle = true;
                        }

                        //if player dies, it will print it to the console & levers are switched to end the code from the first room & so the code for the second room
                        //is not triggered.
                        if (player.getHealth() == 0) {
                            System.out.println("Player (you) died.");
                            endBattle = true;
                            lever1 = true;
                            lever2 = false;
                        }

                        //if enemy dies, it will print it to the console & levers are switched to end the code from the first room
                        if (enemy.getHealth() == 0) {
                            System.out.println("Enemy died.\n\n");
                            room1.removeObject(enemy);
                            room1.placeObject(room1e);
                            room1.printMap();
                            System.out.println("\n\nCongratulations! You killed enemy 1. Please proceed to the ✿ symbol to continue...");
                            endBattle = true;
                            lever1 = true;
                        }

                    //if the user wants to quit the game, levers will be switched so that the code/game ends.
                    } else if (select == 2) {
                        endBattle = true;
                        lever1 = false;
                        lever2 = false;
                    //if the user doesn't select one of the options presented, the console will ask the user to select a valid option.
                    } else {
                        System.out.println("Please select a valid option.");
                    }
                }

            }
            //if the player is dead, the code for room 2 (level 2) will not run through.
            if (player.getHealth() == 0) {
                lever2 = false;
            // if the player is alive, the code for room 2 will run.
            } else {
                lever2 = true;
            }
            //if the player doesn't interact with an enemy or boost, they will be able to keep moving around the room.
            if (!endBattle && enemy.getHealth() > 0) {
                System.out.println("What direction would you like to move? WASD");
                input = scan.next();
                room1.printMap();
            }
        }
        endBattle = false;


        //══════════════════════════════════════════《M A P / R O O M  2  C O D E 》══════════════════════════════════════════

        //while the battle hasn't ended, the lever for room 1 is still true, a new round hasn't started, and the player is alive the user can still move around room 1.
        while (!endBattle && lever1 && !newRound && player.getHealth() > 0) {

            room1.printMap();
            System.out.println("What direction would you like to move? WASD");
            input = scan.next();
            player.move(input, room1);
            room1.printMap();

            //if the player reaches the door for room 1, the lever for room 2 will be triggered. the console will print that the player is entering a new room
            if (room1.doorInRange(player, room1e)) {
                newRound = true;
                System.out.println("\n\n═══════《NEW ROOM UNLOCKED》═══════");
            }
        }
        //when the lever for a new room is true, the battle hasn't ended, and the lever for battle 2 is true, a new character with the saved stats from player
        // will be placed in room 2. the new enemy and boost will also be placed in the room.
        while(newRound && !endBattle && lever2) {
            Character p = new Character(0, 2);
            Character enemy2 = new Character(4, 2, "e");
            Boost hBoost = new Boost(1, 3);

            saveHP = player.getHealth();
            saveStr = player.getStrength();
            saveDef = player.getDefense();
            saveStrB = player.getStrBoost();

            p.setHealth(saveHP);
            p.setStrength(saveStr);
            p.setStrBoost(saveStrB);
            room1.removeObject(player);

            Map room2 = new Map("room2");
            room2.placeObject(p);
            room2.placeObject(enemy2);
            room2.placeObject(hBoost);
            room2.printMap();

            //while the battle hasnt ended and a new round (fight with new enemy) is true, the player will be able to move and interact with objects in room 2.
            // this loop holds all the code for room2.
            while (!endBattle && newRound) {
                System.out.println("What direction would you like to move? WASD");
                input = scan.next();
                p.move(input, room2);
                room2.printMap();
                //if the player is near the boost in room 2, the player will collect the boost and receive 5 extra health damage points added on top of their current
                //health. it will remove the symbol of the boost from the map once collected.
                if (room2.boostInRange(p, hBoost)) {
                    System.out.println("Boost collected!...\nYou received +5 to player's health.");
                    room2.removeObject(hBoost);
                    p.setHealth(p.getHealth() + 5);
                    System.out.println("\n" + p);
                    room2.printMap();
                }
                //if the player is near the enemy in room2, the player will be presented with a choice to either fight the enemy or quit the game.
                if (room2.enemyInRange(p, enemy2)) {
                    System.out.println("You ran into an enemy!");
                    lever2 = false;
                    //in order to fight the enemy in room2, the battle must not be ended and the lever for room 2 must be false. the two levers will be switched after
                    // when the player or enemy dies--the battle ends.
                    while (!endBattle && !lever2) {
                        System.out.println("\n (1) Attack! \n (2) Quit Game.");
                        int select = Integer.valueOf(scan.next());
                        //if the user chooses to fight, the player will attack the enemy first, first checking if both the player and enemy are alive first.
                        if (select == 1) {
                            if (p.getHealth() > 0 && enemy2.getHealth() > 0) {
                                System.out.println("Player attacks enemy!...");
                                p.attackOpp(enemy2);
                            }
                            //after this attack, it will check if the player and enemy are still alive after the first attack. Then, the enemy will attack the player.
                            if (enemy2.getHealth() > 0 && p.getHealth() > 0) {
                                System.out.println("Enemy attacks player!...");
                                enemy2.attackOpp(p);
                            }
                            //if either player or enemy is dead, the battle will end. the loop will end so that there will not be a next round.
                            if (p.getHealth() <= 0 || enemy2.getHealth() <= 0) {
                                endBattle = true;
                            }

                            //if player dies, it will print it to the console & levers are switched to end the code because the user has lost.
                            if (p.getHealth() == 0) {
                                System.out.println("Player (you) died. GAME OVER!");
                                endBattle = true;
                                lever2 = true;
                            }

                            //if enemy dies, it will print it to the console & remove the enemy symbol from the map. it will also congratulate the player for winning the game
                            // & end the game.
                            if (enemy2.getHealth() == 0) {
                                System.out.println("Enemy died.\n\n");
                                room2.removeObject(enemy2);
                                room2.printMap();
                                System.out.println("\n\nCongratulations! You WIN!!!");
                                endBattle = true;
                                lever2 = true;
                            }

                        // the game will end because the user chose to quit
                        } else if (select == 2) {
                            endBattle = true;
                            lever2 = true;
                        // if the player inputs a character that is not one of the options presented, it will ask the user to select a valid option.
                        } else {
                            System.out.println("Please select a valid option.");
                        }
                    }
                }
            }
        }
    }
}
