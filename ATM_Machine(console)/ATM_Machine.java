import java.util.ArrayList;
import java.util.Scanner;

class ATM_Machine {
    public static void main(String[] args) 
    {
        int ch=0;

        String acc, pin;

        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter Account Number : ");
        acc = Integer.toString(sc.nextInt());
        System.out.print("Enter Account Pin : ");
        pin = Integer.toString(sc.nextInt());
        AtmUser user = new AtmUser(acc, pin);

        if(user.verifyUser()==false)
        {
            System.out.println("Incorrect Account Number or Pin, Try Again !");
            user = null; 
            main(args);
        }
        
        do{
            System.out.print("\n1. Transactions History \n2. Withdraw \n3. Deposit \n4. Transfer \n5. Quit \nEnter Your Choice : ");
            ch = sc.nextInt();

        
            if( ch == 1 )
            {
                System.out.println("\nTransaction History :- ");
                if(user.showTransactions())
                    System.out.println(" - - - End Of Transaction History - - - ");
                else
                    System.out.println("Transaction Access Invalid ! \nPlease contact admin or help desk");
            }
            else if( ch == 2 )
            {
                System.out.print("Enter Amount to Withdraw : ");
                double amt = sc.nextDouble();
                if(user.withdraw(amt))
                    System.out.println("Withdraw Successful ! \nPlease Take your Cash ! \nYour New Balance is : " + user.getBalance());
                else
                    System.out.println("Withdraw Failed ! \nPossible Reason : Insufficiant Balance to Withdraw!");
            }
            else if( ch == 3 )
            {
                System.out.print("Enter Amount to Deposite : ");
                double amt = sc.nextDouble();
                if(user.deposite(amt))
                    System.out.println("Deposite Successful ! \nYour New Balance is : " + user.getBalance());
                else
                    System.out.println("Deposite Failed ! ");
            }
            else if( ch == 4 )
            {
                System.out.print("Enter The Transferable Amount : ");
                double amt = sc.nextDouble();
                System.out.print("Enter Destination Account Number : ");
                String desAcc = Long.toString(sc.nextLong());
                if(user.transfer(amt, desAcc))
                    System.out.println("Transfer Successsfull ! \nYour New Balance is : " + user.getBalance()); 
                else
                    System.out.println("Transfer Failed ! \nPossible Reason : Insufficiant Balance to Transfer or Wrong Credentials Entered !");
            }
            else if( ch == 5 )
            {
                System.out.println("Exit / LogOut Successfull !\n\n");
            }
            else{
                System.out.println("Invalid Choice, Try Again!");
            }
            } while(ch!=5);

        user = null; 
        // sc.close();
        main(args);
        
    }
}

class AtmUser
{

    sampleDataGen sample = new sampleDataGen();

    private String userData[][] = sample.getUserData();
    private Transactions[] userTnx = sample.getUserTransactions();

    private String Uid, User, Pin;
    
    public AtmUser ( String user, String pin ) 
    {
        User = user;
        Pin = pin;
    }


    protected boolean verifyUser ()
    {
        for(String[] userdat : userData)
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
            {
                Uid = userdat[0];
                return true;    
            }
        return false;
    }



    protected boolean compare (double amount)
    {
        for(String[] userdat : userData)
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
                if( Double.parseDouble(userdat[4]) >= amount )
                    return true;
        return false;
    }

    protected double getBalance ()
    {
        for(String[] userdat : userData)
        {
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
                return Double.parseDouble(userdat[4]);
        }
        return -1;
    }



    protected boolean showTransactions()
    {
        int i=0;
        for(String[] userdat : userData)
        {
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
            {
                userTnx[i].showTransactions();
                return true;
            }
            i++;
        }
        return false;
    }



    protected boolean withdraw (double amount) 
    {
        int i=0;
        for(String[] userdat : userData)
        {
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) && compare(amount) ) 
            {
                userdat[4] = ""+ ( getBalance() - amount );
                userTnx[i].addTransaction("-"+amount);
                return true;
            }
            i++;
        }
        return false;
    }



    protected boolean deposite (double amount) 
    {
        int i=0;
        for(String[] userdat : userData)
        {
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
            {
                userdat[4] = ""+ ( getBalance() + amount );
                userTnx[i].addTransaction("+"+amount);
                return true;
            }
            i++;
        }
        return false;
    }



    protected boolean transfer(double amount, String decAcc)
    {
        if (compare(amount) == false) 
            return false;
        
        int check=0, i=0;        

        for(String[] userdat : userData)
        {
            if( User.equals(userdat[1]) && Pin.equals(userdat[2]) ) 
            {
                userdat[4] = ""+( getBalance() - amount );
                userTnx[i].addTransaction("-"+amount);
                check++;
            }
            if( decAcc.equals(userdat[1]) )
            {
                userdat[4] = ""+( Double.parseDouble(userdat[4]) + amount );
                userTnx[i].addTransaction("+"+amount);
                check++;
            }
            i++;
        }
        
        return (check == 2)? true : false;

    }

}


class Transactions
{
    private String Uid;
    private ArrayList<String> userTnx = new ArrayList<String>(); 

    public Transactions(String uid)
    {
        Uid = uid;
    }

    protected String getUid()
    {
        return Uid;
    }

    protected void addTransaction(String tnxData)
    {
        userTnx.add(tnxData);
    }

    protected void showTransactions()
    {
        // String[][] userData = new sampleDataGen().getUserData(); 
        for(String tnxData : userTnx)
        {
            System.out.println(tnxData);
        }
    }
}



class sampleDataGen{
    /*             UserData[][] =       UID , Account Number, User pin,      User Name,       Account Balance   */
    private String[][] UserData = { {   "ae23v4",   "123456",   "1234",     "Mr. Psiace"    ,   "1000000.25" },
                                    {   "bawd9f",   "649547",   "6997",     "Sidhu Paji"    ,   "364.92" },
                                    {   "lb43lb",   "549962",   "9449",     "Aditya Kalra"  ,   "6.10" },
                                    {   "nkse0g",   "420823",   "0000",     "Rajesh Mehta"  ,   "764315.68" },
                                    {   "rnfaw4",   "964822",   "1255",     "Mehul Upmanyu" ,   "654722.30" }  };
    private Transactions[] tnx = new Transactions[5];
    protected Transactions[] getUserTransactions()
    {
        int i=0;
        for(String[] userData : UserData)
        {
            tnx[i] = new Transactions(userData[0]);
            tnx[i++].addTransaction("+"+userData[4]);
        }
        return tnx;
    }
    protected String[][] getUserData()
    {
        return UserData;
    }
}

