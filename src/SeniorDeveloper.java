// Franklan Taylor
// COMP360-001
// 11/11/2025
// The senior developer gets the same benefits as the junior developer, but with an additional 100 stock bonus as well.

public class SeniorDeveloper extends JuniorDeveloper{
    protected int _stocks;

    public SeniorDeveloper()
    {
        super();
        _role = "Senior Developer";
        // _salary = 15000;
        _stocks = 0;
    } // SeniorDeveloper()

    public SeniorDeveloper(String firstName, String lastName, int socialSecurityNumber)
    {
        super(firstName, lastName, socialSecurityNumber);
        _role = "Senior Developer";
        // _salary = 15000;
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
