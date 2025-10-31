import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

// NOTE: It is possible to use JPanels as "<div>" sections like in HTML. Helps to organize components in java
// ^ Keep that in mind.

public class Main extends JFrame implements ActionListener
{
    Dictionary<String, JPanel> _sceneManager;                    // manages the JPanels responsible for different pages of the app.
    JButton _mainMenuButton;
    JButton _payEmployeeButton;
    JButton _addBonusButton;
    JButton _paymentInfoButton;
    GridBagConstraints _gridBagConstraints;

    public void configureWindow()
    {
        this.setTitle("Project 2 Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(new Dimension(720, 480));

        interfaceContents();

        this.pack();                                            // pack all contents
        this.setVisible(true);                                  // make the window visible
        this.setLocationRelativeTo(null);                       // place the window at the center of the screen
    } // configureWindow()

    public void interfaceContents()
    {
        // responsible for containing all the GUI/UI components and layout
        this.setLayout(new GridBagLayout());                    // instance the layout for all components
        _gridBagConstraints = new GridBagConstraints();

        // LAYOUT FUNCTIONS LIKE A GRID. KEEP THAT IN MIND
        // Position components based off position on a grid. Should be pretty simple in theory.
        _sceneManager = new Hashtable<>();                      // instantiate the dictionary
        createScenes();

        _sceneManager.get("MainScene").add(new JLabel("Main Scene"));
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Payment Info Scene"));

        // -- MAIN SCENE -- //
        mainScene();

        // -- PAYMENT INFORMATION PANEL -- //
        paymentInfoScene();
    } // interfaceContents()

    public void createScenes()
    {
        _sceneManager.put("MainScene", new JPanel());
        _sceneManager.put("PaymentInfoScene", new JPanel());

        Enumeration<JPanel> scenes = _sceneManager.elements();
        while (scenes.hasMoreElements())
        {
            JPanel currentScene = scenes.nextElement();
            currentScene.setPreferredSize(new Dimension(720, 480));

            if (_sceneManager.get("MainScene") != currentScene) {
                currentScene.setVisible(false);
            } // any scene that isn't the main scene shouldn't be visible

            this.add(currentScene);
        }
    } // createScenes()

    public void mainScene()
    {
        _gridBagConstraints.fill = GridBagConstraints.BOTH;

        _paymentInfoButton = new JButton("Request Payment Information");
        _paymentInfoButton.addActionListener(this);           // enable the program to listen for actions from btn

        _payEmployeeButton = new JButton("Pay Employee");
        _payEmployeeButton.addActionListener(this);

        // Add Components to Scene
        _gridBagConstraints.gridx = 2;
        _gridBagConstraints.gridy = 0;
        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.gridheight = 2;
        _sceneManager.get("MainScene").add(_payEmployeeButton, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 2;
        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.gridheight = 2;
        _sceneManager.get("MainScene").add(_paymentInfoButton, _gridBagConstraints);
    } // mainScene()

    public void paymentInfoScene()
    {
        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _mainMenuButton = new JButton("Main Menu");
        _mainMenuButton.addActionListener(this);

        _sceneManager.get("PaymentInfoScene").add(_mainMenuButton);
    } // paymentInfoScene()
    public static void infoVisibleTest()
    {
        // New Dev Test
        NewDeveloper newDev = new NewDeveloper("Franklan", "Taylor", 11111111);
        newDev.printPaymentInfo(); System.out.println();
        newDev.addPayment();
        newDev.addBonus();
        newDev.printPaymentInfo(); System.out.println();

        // Junior Developer Test
        JuniorDeveloper juniorDev = new JuniorDeveloper("Juny", "Dev", 22222222);
        juniorDev.printPaymentInfo(); System.out.println();
        juniorDev.addPayment();
        juniorDev.addBonus();
        juniorDev.printPaymentInfo(); System.out.println();

        JuniorDeveloper juniorDevTwo = new JuniorDeveloper("Juny The II", "Dev", 44444444);
        juniorDevTwo.printPaymentInfo(); System.out.println();
        juniorDevTwo.addPayment();
        juniorDevTwo.addBonus();
        juniorDevTwo.addPayment();
        juniorDevTwo.printPaymentInfo(); System.out.println();

        // Senior Developer Test
        SeniorDeveloper seniorDev = new SeniorDeveloper("Seny", "Dev", 33333333);
        seniorDev.printPaymentInfo(); System.out.println();
        seniorDev.addPayment();
        seniorDev.addBonus();
        seniorDev.printPaymentInfo(); System.out.println();
    } // infoVisibleTest()

    @Override
    public void actionPerformed(ActionEvent e) {
        // listens for actions from the user
        // reads it as source input
        JButton buttonSource = (JButton) e.getSource();             // Get which button is pressed based on source
        System.out.println("\u001B[33m" + "ACTION HEARD" + "\u001B[0m");

        if (buttonSource.equals(_paymentInfoButton))
        {
            System.out.println("\u001B[32m" + "SWITCHING TO 'Payment Information Scene'" + "\u001B[0m");
            _sceneManager.get("MainScene").setVisible(false);
            _sceneManager.get("PaymentInfoScene").setVisible(true);
        } else if (buttonSource.equals(_mainMenuButton))
        {
            System.out.println("\u001B[32m" + "SWITCHING TO 'Main Scene'" + "\u001B[0m");
            _sceneManager.get("PaymentInfoScene").setVisible(false);
            _sceneManager.get("MainScene").setVisible(true);
        } else {
            System.out.println("\u001B[31m" + "Input Source NOT Recognized" + "\u001B[0m");
        }

    } // actionPerformed()

    public static void main(String[] args)
    {
        Main application = new Main();
        application.configureWindow();

        //infoVisibleTest();
    } // main()
}