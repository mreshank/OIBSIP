import java.util.*;

public class guess_the_number 
{
    public static int pts = 0;

    public static void main(String[] args) {
        char c = 'Y';
        do {
            runch();
            System.out.print("\n\nWanna Play again ? (Y/N) : ");
            c = new Scanner(System.in).next().charAt(0);
        } while (c == 'Y' || c == 'y' || c == '1');
    }

    public static int getch() {
        System.out.print("\n Current Points : " + pts);
        System.out.print(
                "\nAvailable Levels : \n> 1. Easy Level : { 0 to 10 }\n> 2. Medium Level : { 0 to 500 }\n> 3. Hard Level : { -500 to +500 }\n> 4. Custom Level : { Any Integer Range }\nEnter Your Choice : ");
        return new Scanner(System.in).nextInt();
    }

    public static void runch() {
        int ch = getch();
        switch (ch) {
            case 1:
                lvl1();
                break;
            case 2:
                lvl2();
                break;
            case 3:
                lvl3();
                break;
            case 4:
                System.out.print("Enter the Lower Range : ");
                int l = new Scanner(System.in).nextInt();
                System.out.print("Enter the Upper Range : ");
                clvl(l, new Scanner(System.in).nextInt());
                break;
            case -999:
                return;
            default:
                System.out.println("Invalid Choice !\n");
                runch();
        }
    }

    public static void adpts(int v) {
        pts += Math.ceil(v / Math.sqrt(v));
    }

    public static void lvl1() 
    {
        int rnum = new Random().nextInt(11), uinp = 999, i = 3;
        System.out.print("Hey Player, You've got three(" 
                        + i + ") chances to guess it right ...\nEnter a number : ");
        while (uinp != rnum && (i--) > 0) 
        {
            uinp = new Scanner(System.in).nextInt();
            if (uinp == rnum) 
            {
                System.out.println("Congratulations ! You Guessed It Right...\nIt took you " 
                                    + (3 - i) + " attempt(s) to guess it correctly ...");
                adpts(i + 1);
            } 
            else if (i == 0) 
            {
                System.out.print("Sorry, You Lost !\nBetter luck next time ...");
                break;
            } 
            else if (uinp < rnum)
                System.out.print("Enter a number Greater than " + uinp + " : ");
            else if (uinp > rnum)
                System.out.print("Enter a number Less than " + uinp + " : ");
        }
    }

    public static void lvl2() 
    {
        int rnum = new Random().nextInt(501), uinp = 999, i = 6;
        System.out.print("Hey Player, You've got six(" 
                        + i + ") chances to guess it right ...\nEnter a number : ");
        while (uinp != rnum && (i--) > 0) 
        {
            uinp = new Scanner(System.in).nextInt();
            if (uinp == rnum) 
            {
                System.out.println("Congratulations ! You Guessed It Right...\nIt took you " + (6 - i)
                                     + " attempt(s) to guess it correctly ...");
                adpts(i + 1);
            } 
            else if (i == 0) 
            {
                System.out.print("Sorry, You Lost !\nBetter luck next time ...");
                break;
            } 
            else if (uinp < rnum)
                System.out.print("Enter a number Greater than " + uinp + " : ");
            else if (uinp > rnum)
                System.out.print("Enter a number Less than " + uinp + " : ");
        }
    }

    public static void lvl3() 
    {
        int rnum = new Random().nextInt(1001) - 500, uinp = 999, i = 10;
        System.out.print("Hey Player, You've got ten(" 
                        + i + ") chances to guess it right ...\nEnter a number : ");
        while (uinp != rnum && (i--) > 0) 
        {
            uinp = new Scanner(System.in).nextInt();
            if (uinp == rnum) 
            {
                System.out.println("Congratulations ! You Guessed It Right...\nIt took you " + (10 - i)
                        + " attempt(s) to guess it correctly ...");
                adpts(i + 1);
            } 
            else if (i == 0) 
            {
                System.out.print("Sorry, You Lost !\nBetter luck next time ...");
                break;
            } 
            else if (uinp < rnum)
                System.out.print("Enter a number Greater than " + uinp + " : ");
            else if (uinp > rnum)
                System.out.print("Enter a number Less than " + uinp + " : ");
        }
    }

    public static void clvl(int l, int u) 
    {
        int rnum = new Random().nextInt(u - l + 1) + l, uinp = 999,
                i = (int) Math.ceil(0.2 * Math.sqrt(u + Math.abs(l)));
        i += Math.abs(i - 3) < 8 ? Math.abs(i - 3) : 0;
        int ic = i;
        System.out.print("Hey Player, You've got " 
                        + i + " chances to guess it right ...\nEnter a number : ");
        while (uinp != rnum && (i--) > 0) 
        {
            uinp = new Scanner(System.in).nextInt();
            if (uinp == rnum) 
            {
                System.out.println("Congratulations ! You Guessed It Right...\nIt took you " 
                                    + (ic - i) + " attempt(s) to guess it correctly ...");
                adpts((i + 1) / (int) Math.sqrt(i));
            } 
            else if (i == 0) 
            {
                System.out.print("Sorry, You Lost !\nBetter luck next time ...");
                break;
            } 
            else if (uinp < rnum)
                System.out.print("Enter a number Greater than " + uinp + " : ");
            else if (uinp > rnum)
                System.out.print("Enter a number Less than " + uinp + " : ");
        }
    }
}