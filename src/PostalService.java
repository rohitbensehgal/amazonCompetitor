import java.util.ArrayList;
import java.util.Scanner;

// this class is used to calculate the postal fee after customer ordered items.
public class PostalService
{

    private double num; //total money
    private Reciept reciept;
    private double fee; //Postal fee
    private int choose; // the postal service customer choose
    private  String address;
    private String name;
    private Customer customer;
    private Cart cart;
    private ArrayList<String> mailTask;

    // check how much customer spend base on the Reciept class
    private void checkSpend()
    {
        num = reciept.checkTotal();
    }
    // this method helps customer to set their postal address.
    // calculate the postal fee, if customer spend more than $50, fee =0,
    public double PostalFee()
    {
        String a ="";
        Scanner sc= new Scanner(System.in);
        System.out.println("Do you live in the United States, please enter Yes or No");
        a =sc.next();
        sc.close();
        if(a.equals("YES"))
        {
            System.out.println("Please enter your address");
            Scanner sc1 =new Scanner(System.in);
            address=sc1.next();
            sc1.close();
            if(num>=50)
            {
                fee =0;
            }
            else
            {
                Scanner sc2 =new Scanner(System.in);
                System.out.println("Which Postal Service do you want?\n2-DAY Delivery $ 6.5, please enther 1\n" +
                        "3-4 Day Delivery $ 2.35, please enter 2\nWeek Delivery $ 0.97, please enter 3\nFree Delivery" +
                        " for more than $50)");
                choose =sc2.nextInt();
                sc2.close();
                if(choose==1)
                {
                    fee=6.5;
                }
                else if(choose==2)
                {
                    fee=2.35;
                }
                else if(choose==3)
                {
                    fee =0.97;
                }
                else
                {
                    double i=Double.POSITIVE_INFINITY;
                    fee =i;
                }
            }
        }
        else
        {
            System.out.println("Sorry, our postal service is unavailable in your area now.");
            double i=Double.POSITIVE_INFINITY;
            fee =i;
        }
        return fee;
    }

    // this method is to create a items table which helps mail workers to collect items and send to the correct customer.
    private void setMail(boolean valid)
    {
        String MailInfo ="";
        String items ="";
        if(valid==true)
        {
            name = customer.getUsername();
            for(int i=0;i<cart.getNumItems();i++)
            {
                items+=cart.getItems().get(i).getName()+" "+cart.getItems().get(i).getNumItems()+"\n";
            }
            MailInfo ="Dear Customer:"+" "+name+"\n"+"Shopping list:"+"\n"+items+"\n"+"Address:"+address;
            mailTask.add(MailInfo);
        }
        else
        {

        }
    }
    private ArrayList<String> getMailTask()
    {
        return mailTask;
    }
}
