public class Map {
    private Mappable[][] board;

    public Map(String file) {
        MediaFile.setInputFile(file);
        String info = MediaFile.readString();
        int numRows = Integer.valueOf(info.substring(0, info.indexOf(" ")));
        int numCols = Integer.valueOf(info.substring(info.indexOf(" ") + 1));
        board = new Mappable[numRows][numCols];
        for (int r = 0; r < board.length; r++) {
            info = MediaFile.readString();
            String[] line = info.split(" ");
            for (int c = 0; c < board[r].length; c++) {
                if (line[c].equals("1")) {
                    board[r][c] = new Wall(r, c);
                } else if (line[c].equals("2")) {
                    board[r][c] = new Character(r, c);
                } else if (line[c].equals("3")) {
                    board[r][c] = new Boost(r, c);
                }
            }
        }
        printMap();
    }

    //this will allow characters and boosts to be placed on the map/console to be visible.
    public void placeObject(Mappable toPlace) {
        board[toPlace.getCurRow()][toPlace.getCurCol()] = toPlace;
    }

    //this will print the map with the correct symbols from the different classes (X, , E, C, etc.)
    public void printMap() {
        for (Mappable[] r : board) {
            for (Mappable cell : r) {
                if (cell != null) {
                    System.out.print(" " + cell.getSymbol());
                } else {
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    //takes an object off the map by setting the slot it used to reside at to null, AKA nothing--just a blank space.
    public void removeObject(Mappable toRemove) {
        board[toRemove.getCurRow()][toRemove.getCurCol()] = null;
    }

    //this will check if the slot the player is trying to move to is a null, AKA a blank space that a wall or any other object does not occupy.
    public boolean canMove(int row, int col) {
        if ((row < 0 || row > board.length) && (col < 0 || col >= board[row].length)) {
            return false;
        } else if (board[row][col] != null) {
            return false;
        } else {
            return true;
        }
    }

    //this method will check if a character is in range of another character -- this is used to test if player is next to enemy. either one slot above, below, to the left,
    //or to the right. will return true if it is in range.
    public boolean enemyInRange(Character c, Character e) {
        if (c.getCurRow() + 1 < board.length && c.getCurRow() + 1 >= 0) {
            if (board[c.getCurRow() + 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() + 1][c.getCurCol()] == board[e.getCurRow()][e.getCurCol()] && board[e.getCurRow()][e.getCurCol()].isAttackable()) {
                    return true;
                }
            }
        }

        if (c.getCurRow() - 1 < board.length && c.getCurRow() - 1 >= 0) {
            if (board[c.getCurRow() - 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() - 1][c.getCurCol()] == board[e.getCurRow()][e.getCurCol()] && board[e.getCurRow()][e.getCurCol()].isAttackable()) {
                        return true;
                }
            }
        }

        if (c.getCurCol() + 1 < board[0].length && c.getCurCol() + 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() + 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() + 1] == board[e.getCurRow()][e.getCurCol()] && board[e.getCurRow()][e.getCurCol()].isAttackable()) {
                        return true;
                }
            }
        }

        if (c.getCurCol() - 1 < board[c.getCurRow()].length && c.getCurCol() - 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() - 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() - 1] == board[e.getCurRow()][e.getCurCol()] && board[e.getCurRow()][e.getCurCol()].isAttackable()) {
                    return true;
                }
            }
        }
        return false;
    }

    //this method will check if a character is in range of a boost. either one slot above, below, to the left,
    //or to the right. will return true if it is in range.
    public boolean boostInRange(Character c, Boost b) {
        if (c.getCurRow() + 1 < board.length && c.getCurRow() + 1 >= 0) {
            if (board[c.getCurRow() + 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() + 1][c.getCurCol()] == board[b.getCurRow()][b.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurRow() - 1 < board.length && c.getCurRow() - 1 >= 0) {
            if (board[c.getCurRow()- 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() - 1][c.getCurCol()] == board[b.getCurRow()][b.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurCol() + 1 < board[0].length && c.getCurCol() + 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() + 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() + 1] == board[b.getCurRow()][b.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurCol() - 1 < board[c.getCurRow()].length && c.getCurCol() - 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() - 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() - 1] == board[b.getCurRow()][b.getCurCol()]) {
                    return true;
                }
            }
        }
        return false;
    }

    //this method will check if a character is in range of a door. either one slot above, below, to the left,
    //or to the right. will return true if it is in range.
    public boolean doorInRange(Character c, Door d) {
        if (c.getCurRow() + 1 < board.length && c.getCurRow() + 1 >= 0) {
            if (board[c.getCurRow() + 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() + 1][c.getCurCol()] == board[d.getCurRow()][d.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurRow() - 1 < board.length && c.getCurRow() - 1 >= 0) {
            if (board[c.getCurRow()- 1][c.getCurCol()] != null) {
                if (board[c.getCurRow() - 1][c.getCurCol()] == board[d.getCurRow()][d.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurCol() + 1 < board[0].length && c.getCurCol() + 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() + 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() + 1] == board[d.getCurRow()][d.getCurCol()]) {
                    return true;
                }
            }
        }

        if (c.getCurCol() - 1 < board[c.getCurRow()].length && c.getCurCol() - 1 >= 0) {
            if (board[c.getCurRow()][c.getCurCol() - 1] != null) {
                if (board[c.getCurRow()][c.getCurCol() - 1] == board[d.getCurRow()][d.getCurCol()]) {
                    return true;
                }
            }
        }
        return false;
    }
}
