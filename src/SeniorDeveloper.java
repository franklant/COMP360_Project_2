public class SeniorDeveloper extends JuniorDeveloper{
    protected int _stocks;

    public SeniorDeveloper()
    {
        super();
        _role = "Senior Developer";
        _salary = 15000;
        _stocks = 0;
    } // SeniorDeveloper()

    public SeniorDeveloper(String firstName, String lastName, int socialSecurityNumber)
    {
        super(firstName, lastName, socialSecurityNumber);
        _role = "Senior Developer";
        _salary = 15000;
        _additionalBonusAmount = (_salary / 100.00);    // additional bonus include addition 1% of salary
        _stocks = 0;
    } // SeniorDeveloper(String firstName, String lastName, int socialSecurityNumber)

    public void printPaymentInfo()
    {
        super.printPaymentInfo();
        System.out.printf("# of Stocks: %d\n", _stocks);
    } // printPaymentInfo()

    public void addBonus()
    {
        super.addBonus();
        _stocks += 100;
    } // addBonus()
}
