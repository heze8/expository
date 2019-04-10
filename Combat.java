class Combat {
    private Integer[][] space;
    final private int obs = -1;
    private int rowLength, columnLength, r, c;
    private Integer player;
    private Integer enemy;

    public Combat(int row, int column){
        space = new Integer[row][column];
        rowLength = row;
        columnLength = column;
        player = 331;
        enemy = 1;
        r = row - 2; //second last row for player
        c = column/2; // middle for player
        space[r][c] = player;
        space[1][(int) (column * Math.random())] = enemy;
    }

    Boolean move(int cas) {
        int dr = 0;
        int dc = 0;
        switch (cas) {
            case 1:
                dr = -1;
                break;
            case 2:
                dr = 1;
                break;
            case 3:
                dc = 1;
                break;
            case 4:
                dc = -1;
                break;
            default:
                break;
        }
        if(player == null) {
            System.out.println("Error: No valid player found");
            return false;
        }
        else if(r + dr < 0 || c + dc >= rowLength || c + dc < 0){
            System.out.println("Error: out of bounds");
            return false;
        }
        else if(r + dr >= space.length){
            //retreat();
            return false;
        }
        else if(space[r + dr][c + dc] != null){
            int x = space[r + dr][c + dc];
            if(x > 0){
                System.out.println("Someone is occupying that spot");
                return false;
            }
            if (x < 0) {
                System.out.println("Obstacle there");
                return false;
            }
        }
        else {
            space[r + dr][c + dc] = player;
            r += dr;
            c += dc;
            space[r][c] = null;
            return true;
        }
        return false;
    }

    Boolean shoot(int cas) {
        int dr = 0;
        int dc = 0;
        switch(cas) {
            case 1:
                dr = -1;
                break;
            case 2:
                dr = -1;
                dc = 1;
                break;
            case 3:
                dc = 1;
                break;
            case 4:
                dr = 1;
                dc = 1;
                break;
            case 5:
                dr = 1;
                break;
            case 6:
                dr = 1;
                dc = -1;
                break;
            case 7:
                dc = -1;
                break;
            case 8:
                dr = -1;
                dc = -1;
                break;
            default:
                System.out.println("Error wrong case");
                break;
        }
        Boolean shot = true;
        int tempR = r;
        int tempC = c;
        while(shot) {
            tempR += dr;
            tempC += dc;
            if(tempR < 0 || tempR >= space.length || tempC>= rowLength || tempC < 0){
                System.out.println("Shot missed");
                shot = false;
                return false;
            }
            else {
                int curr = space[tempR][tempC];
                if(curr < 0) {
                    curr++;
                    System.out.println("Obstacle " + (curr==0?"destroyed":"hit"));
                    shot = false;
                    return false;
                }
                if(curr > 0) {
                    curr--;
                    System.out.println("Enemy " + (curr==0?"killed":"hit"));
                    shot = false;
                    return true;
                }
            }
        }
        return false;
    }

    public String toString() {
        String line = String.format("%0" + (4 * c + 3) + "d", 0).replace("0", "_");
        StringBuilder output = new StringBuilder();
        output.append(line).append("\n");
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
               output.append("|"); 
               Integer curr = space[i][j];
                    if(i == rowLength - 1) {
                        output.append("_");
                    }
                    else if(curr == null){
                        output.append(" ");
                    }
                    else if(curr < 0) {
                        output.append("@");
                    }
                    else if (curr > 0) {
                        output.append("&");
                    }
            }
            output.append("|");
            output.append("\n");
        }
        output.append("\r");

        return output.toString();
    }
}

/**
    public String toString() {
        String line = String.format("%" + c + "s", "___");
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
               output.append("|"); 
               int curr = space[i][j];
               for (int k = 0; k < 3; k++) {
                   for (int l = 0; l < 3; l++) {
                        if(curr == 0){
                            output.append(" ");
                        }
                        else if(curr < 0){
                            if(k == 0) {
                                if(j == 0) {
                                    output.append(" ");
                                }
                            }    
                            else if(k ==)
                        0
 @                      /|\
@@@                    /\
 @                 }
               }
            }
        }
        
    }
}
 */
