import java.util.Scanner;

class CombatUI {
    Combat c;
    
    public static void main(String[] args) {
        CombatUI ui = new CombatUI();
        Scanner sc = new Scanner(System.in);
        ui.c = new Combat(sc.nextInt(), sc.nextInt());
        boolean flag = true;
        while(flag) {
            System.out.flush();
            System.out.print(ui.c);
            String command = sc.next();
            int cas = 0;
            switch (command.charAt(1)) {
                case 'w':
                    break;
                case 'a':
                    cas = 2;
                    break;
                case 's':
                    cas = 1;
                    break;
                case 'd':
                    cas = 3;
                    break;
                default:
                    break;
            }
            
            switch (command.charAt(0)) {
                case 'm':
                    ui.c.move(cas);
                    break;
                case 's':
                    if(ui.c.shoot(cas)) {
                        flag = false;
                    }
                    break;
            }    
        }
    } 
}