public class JuniorDeveloper extends NewDeveloper{
    protected double _additionalBonusAmount;

    public JuniorDeveloper()
    {
        super();
        _role = "Junior Developer";
        // _salary = 13000;

    } // JuniorDeveloper()

    public JuniorDeveloper(String firstName, String lastName, int socialSecurityNumber)
    {
        super(firstName, lastName, socialSecurityNumber);
        _role = "Junior Developer";
        // _salary = 13000;
        _additionalBonusAmount = (_salary / 100.00);    // additional bonus include addition 1% of salary
    } // JuniorDeveloper(String firstName, String lastName, int socialSecurityNumber)

    public void printPaymentInfo()
    {
        super.printPaymentInfo();
        System.out.printf("1 Percent Payment: $%.2f\n", _additionalBonusAmount);
    } // printPaymentInfo()

    public void addBonus()
    {
        super.addBonus();
        _totalPayment += _additionalBonusAmount;
    } // addBonus()
}
