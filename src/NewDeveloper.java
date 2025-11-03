import javax.swing.*;

public class NewDeveloper{
    protected String _firstName;
    protected String _lastName;
    protected String _role;
    protected int _socialSecurityNumber;        // XXX-XX-XXX
    protected int _salary;
    protected final int _BONUS_AMOUNT = 5000;   // bonus amount never changes (constant)
    protected double _totalPayment;

    public NewDeveloper()
    {
        _firstName = null;
        _lastName = null;
        _role = "New Developer";
        _socialSecurityNumber = -1;
        _salary = 10000;
        _totalPayment = 0;
    } // NewDeveloper()

    public NewDeveloper(String firstName, String lastName, int socialSecurityNumber)
    {
        _firstName = firstName;
        _lastName = lastName;
        _role = "New Developer";

        int socialSecurityNumberLength = String.valueOf(socialSecurityNumber).length();

        if (socialSecurityNumber != -1 && socialSecurityNumberLength != 8)
        {
            JOptionPane.showMessageDialog(new Main(), "Social Security Number MUST be 8 Digits!");
            throw new IllegalArgumentException("Social Security Number must be 8 digits!");
        } else {
            _socialSecurityNumber = socialSecurityNumber;
        }

        _salary = 10000;
        _totalPayment = 0;
    } // NewDeveloper(String firstName, String lastName, int socialSecurityNumber)

    public void printPaymentInfo()
    {
        System.out.printf(
                "<%s>\n"
                + "Name: %s %s\n"
                + "Social Security Number: %s \n"
                + "Total Payment: $%.2f\n",
                _role,
                _firstName,
                _lastName,
                _socialSecurityNumber,
                _totalPayment
        );
    } // printPaymentInfo()

    public void addPayment()
    {
        _totalPayment += _salary;
    } // add payment()

    public void addBonus()
    {
        _totalPayment += _BONUS_AMOUNT;
    } // addBonus()
}
