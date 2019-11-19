import java.util.ArrayList;

public class ReturnItemSystem
{
    private ReviewSystem reviewSystem;
    private boolean checkB;
    private Customer customer;
    private String address;
    private String name;
    private ArrayList<String> history;

    private ArrayList<String> setMail(String a)
    {
        checkB=reviewSystem.CheckHistory(a);
        if(checkB==true)
        {
            name = customer.getUsername();
            System.out.println("Dear" + name + "your return request has been approved. Please email packed item(s) to our " +
                    "company. The address is 5789 Lincoln Way. Thank you!");
            String word = name + "return" +a;
            history.add(word);
        }
        else
        {
            System.out.println("Sorry,"+a+"is not in the return list");
        }
        return history;
    }
}
