import java.util.*;
import java.io.*;
import java.util.stream.*;

public class DN05 {

    static int visina;
    static int sirina;

    static int[][] preberiZacetnoPostavitev(String imeDatoteke) {
        Scanner sc = null;
        int stLadij = 0;
        try {
            sc = new Scanner(new File(imeDatoteke));
        } catch (Exception e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            System.exit(1);
        }

        try {
            String[] vs = sc.nextLine().split("x");
            visina = Integer.parseInt(vs[0]);
            sirina = Integer.parseInt(vs[1]);

            if (visina < 0 || sirina < 0) {
                throw new Exception("Napaka: Dimenzija mora biti pozitivna.");
            }

        } catch (Exception e) {
            System.out.println("Napaka: Manjka podatek o dimenzijah igralne povrsine.");
            System.exit(1);
        }
        try {
            stLadij = Integer.parseInt(sc.nextLine());
            if (stLadij < 0) {
                throw new Exception("Napaka: Stevilo ladij ne sme biti negativno.");
            }
        } catch (Exception e) {
            System.out.println("Napaka: Manjka podatek o stevilu ladij.");
            System.exit(1);
        }

        int[][] tabLadij = new int[stLadij][5];
        try {
            for (int i = 0; i < tabLadij.length; i++) {
                for (int j = 0; j < tabLadij[i].length; j++) {
                    String znak = sc.next();

                    switch (znak) {
                        case "S":
                            tabLadij[i][j] = 0;
                            break;

                        case "J":
                            tabLadij[i][j] = 1;
                            break;

                        case "V":
                            tabLadij[i][j] = 2;
                            break;

                        case "Z":
                            tabLadij[i][j] = 3;
                            break;

                        default:
                            tabLadij[i][j] = Integer.parseInt(znak);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Napaka: Podatek o stevilu ladij se ne ujema s stevilom vnosov.");
            System.exit(1);
        }
        return tabLadij;
    }

    static void izpisiPostavitev(int[][] postavitev) {
        for (int i = 0; i < postavitev.length; i++) {
            String premec = "Koordinate premca: ";
            for (int j = 0; j < postavitev[i].length; j++) {
                switch (j) {
                    case 0:
                        System.out.print("Igralec: " + postavitev[i][j] + " ");
                        break;

                    case 1:
                        premec += "(" + postavitev[i][j] + ",";
                        break;

                    case 2:
                        premec += postavitev[i][j] + ")";
                        break;

                    case 3:
                        System.out.print("Dolzina: " + postavitev[i][j] + " ");
                        break;

                    case 4:
                        System.out.print("Smer: " + postavitev[i][j] + " ");
                        break;
                }
            }
            System.out.println(premec);
        }
    }

    static boolean veljavnaLadja(boolean igralec, int x, int y, int dolzina, int smer) {
        switch (smer) {
            case 0:
                return dolzina + x <= visina;
            case 1:
                return x - dolzina >= 0;
            case 2:
                return dolzina + y - 1 <= (igralec ? 2 * sirina : sirina);
            case 3:
                return y - dolzina + 1 >= (igralec ? sirina : 0);
        }
        return false;
    }

    static void postaviLadjo(int[][] tabPovrsina, int x, int y, int dolzina, int smer, int zapStLadje) {
        for (int telo = 0; telo < dolzina; telo++) {
            switch (smer) {
                case 0:
                    tabPovrsina[x + telo][y] = zapStLadje * 10 + 2;
                    break;
                case 1:
                    tabPovrsina[x - telo][y] = zapStLadje * 10 + 2;
                    break;
                case 2:
                    tabPovrsina[x][y + telo] = zapStLadje * 10 + 2;
                    break;
                case 3:
                    tabPovrsina[x][y - telo] = zapStLadje * 10 + 2;
                    break;
            }
        }
        tabPovrsina[x][y] = zapStLadje * 10 + 1;
    }

    static int[][] izdelajIgralnoPovrsino(int[][] postavitev) {
        int[][] tabPovrsina = new int[visina][2 * sirina];

        for (int i = 0; i < postavitev.length; i++) {
            int[] trenutnaLadja = postavitev[i];
            for (int j = 0; j < postavitev[i].length; j++) {
                boolean igralec = trenutnaLadja[0] == 1;

                int x = trenutnaLadja[1],
                        y = trenutnaLadja[2] + (igralec ? sirina : 0),
                        dolzina = trenutnaLadja[3],
                        smer = trenutnaLadja[4];

                if (veljavnaLadja(igralec, x, y, dolzina, smer)) {
                    postaviLadjo(tabPovrsina, x, y, dolzina, smer, i + 1);
                }
            }
        }
        return tabPovrsina;
    }

    static void izrisiIgralnoPovrsino(int[][] igralnaPovrsina){
        for (int i = 0; i < sirina*2+3; i++) {
            System.out.print("# ");
        }
        System.out.println();

        for (int i = 1; i < visina; i++) {
            for (int j = 0; j < (sirina*2+3)*2 - 3; j++) {
                if(j == 0){
                    System.out.print("# ");
                }else if(j == (sirina*2+3)*2 - 4){
                    System.out.println("#");
                }else if(j == (sirina*2 + 1)){
                    System.out.print("# ");
                }else{
                    System.out.print(" ");
                }
            }
        }

        for (int i = 0; i < sirina*2+3; i++) {
            System.out.print("# ");
        }


        for (int i = 0; i < igralnaPovrsina.length; i++) {
            for (int j = 0; j < igralnaPovrsina[i].length; j++) {

            }

        }
    }

    public static void main(String[] args) throws Exception {
        // if(args[0].equals("postavitev")){
        // izpisiPostavitev(preberiZacetnoPostavitev(args[1]));
        // }

        // izpisiPostavitev(preberiZacetnoPostavitev("vhod.txt"));
        // preberiZacetnoPostavitev("vhod.txt");
        int[][] temp = izdelajIgralnoPovrsino(preberiZacetnoPostavitev("vhod.txt"));

        // for (int i = 0; i < temp.length; i++) {
        //     for (int j = 0; j < temp[i].length; j++) {
        //         System.out.printf("%4d ", temp[i][j]);
        //     }
        //     System.out.println();
        // }

        izrisiIgralnoPovrsino(temp);

    }
}
