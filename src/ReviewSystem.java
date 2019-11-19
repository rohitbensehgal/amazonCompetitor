import java.util.ArrayList;
import java.util.Scanner;

// this class is used to implement the review function after customers bought something
public class ReviewSystem
{
    private String name; // get customer name
    private Customer customer; // used to call an method in Customer
    private ArrayList<Reciept> reciept;
    private boolean checkB;
    private ArrayList<String> comment; // Arraylist to store comments

    private void getCustomer()
    {
        name = customer.getUsername();
        reciept =customer.getReciepts();
    }
    // used to check customer have bought the item which they want to review or not
    public boolean CheckHistory(String a)
    {
        for(int i =0; i<reciept.size();i++)
        {
            Cart cart =reciept.get(i).getCart();
            ArrayList<Item> items= cart.getItems();
            for(int j =0; j<items.size();j++)
            {
                if(items.get(i).getName()==a)
                {
                    checkB=true;
                    return checkB;
                }
            }
        }
        return checkB;
    }
    // store comments in a Arraylist
    private ArrayList<String> review()
    {
        Scanner scanner = new Scanner(System.in);
        if(checkB==true)
        {
            String word = scanner.next();
            comment.add(word);
        }
        else
        {

        }
        return comment;
    }
}
