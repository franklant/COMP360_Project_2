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
    Dictionary<Integer, NewDeveloper> _userManager;                  // manages a list of users inorder to retrieve payment info.

    // Main Menu Components
    JButton _mainMenuButtonUserScene;
    JButton _mainMenuButtonInfoScene;
    JButton _addNewUserButton;

    // New User Components
    JTextArea _firstNameTextArea;
    JTextArea _lastNameTextArea;
    JTextArea _socialSecurityNumberTextArea;
    JLabel _firstNameLabel;
    JLabel _lastNameLabel;
    JLabel _socialSecurityNumberLabel;
    JLabel _developerLevelLabel;
    JComboBox _developerLevelComboBox;
    JButton _submitButton;


    // Payment Info Components
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
        _userManager = new Hashtable<>();
        createScenes();

        // -- MAIN SCENE -- //
        mainScene();

        // -- PAYMENT INFORMATION PANEL -- //
        paymentInfoScene();

        // -- NEW USER PANEL -- //
        newUserScene();

    } // interfaceContents()

    public void createScenes()
    {
        _sceneManager.put("MainScene", new JPanel());
        _sceneManager.put("PaymentInfoScene", new JPanel());
        _sceneManager.put("NewUserScene", new JPanel());

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
        _sceneManager.get("MainScene").add(new JLabel("Main Scene"));

        _gridBagConstraints.fill = GridBagConstraints.BOTH;

        _paymentInfoButton = new JButton("Request Payment Information");
        _paymentInfoButton.addActionListener(this);           // enable the program to listen for actions from btn

        _addNewUserButton = new JButton("Add New User");
        _addNewUserButton.addActionListener(this);

        // Add Components to Scene
        _gridBagConstraints.gridx = 2;
        _gridBagConstraints.gridy = 0;
        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.gridheight = 2;
        _sceneManager.get("MainScene").add(_addNewUserButton, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 2;
        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.gridheight = 2;
        _sceneManager.get("MainScene").add(_paymentInfoButton, _gridBagConstraints);
    } // mainScene()

    public void newUserScene()
    {
        _sceneManager.get("NewUserScene").add(new JLabel("Add New User"));

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _mainMenuButtonUserScene = new JButton("Main Menu");
        _mainMenuButtonUserScene.addActionListener(this);

        _firstNameLabel = new JLabel("First Name");
        _firstNameTextArea = new JTextArea();
        _firstNameTextArea.setEditable(true);
        _firstNameTextArea.setPreferredSize(new Dimension(100, 20));

        _lastNameLabel = new JLabel("Last Name");
        _lastNameTextArea = new JTextArea();
        _lastNameTextArea.setEditable(true);
        _lastNameTextArea.setPreferredSize(new Dimension(100, 20));

        _socialSecurityNumberLabel = new JLabel("Social Security Number");
        _socialSecurityNumberTextArea = new JTextArea();
        _socialSecurityNumberTextArea.setEditable(true);
        _socialSecurityNumberTextArea.setPreferredSize(new Dimension(100, 20));

        String[] developerLevels = {"New Developer", "Junior Developer", "Senior Developer"};
        _developerLevelLabel = new JLabel("Developer Level");
        _developerLevelComboBox = new JComboBox(developerLevels);
        _developerLevelComboBox.setEditable(false);

        _submitButton = new JButton("Add User");
        _submitButton.addActionListener(this);

        _sceneManager.get("NewUserScene").add(_mainMenuButtonUserScene);

        _sceneManager.get("NewUserScene").add(_firstNameLabel);
        _sceneManager.get("NewUserScene").add(_firstNameTextArea);

        _sceneManager.get("NewUserScene").add(_lastNameLabel);
        _sceneManager.get("NewUserScene").add(_lastNameTextArea);

        _sceneManager.get("NewUserScene").add(_socialSecurityNumberLabel);
        _sceneManager.get("NewUserScene").add(_socialSecurityNumberTextArea);

        _sceneManager.get("NewUserScene").add(_developerLevelLabel);
        _sceneManager.get("NewUserScene").add(_developerLevelComboBox);

        _sceneManager.get("NewUserScene").add(_submitButton);
    } // newUserScene()

    public void submitInfo() {
        NewDeveloper createdUser;   // because inherited classes can also be instantiated to a variable with their base class type
        String developerLevel = _developerLevelComboBox.getSelectedItem().toString();

        String firstName = _firstNameTextArea.getText();
        String lastName = _lastNameTextArea.getText();
        int socialSecurityNumber = Integer.parseInt(_socialSecurityNumberTextArea.getText());

        if (developerLevel == null)
            throw new IllegalArgumentException("Developer Level cannot be String-ified");

        if (_userManager.get(socialSecurityNumber) != null) // this social security number already exists (people can have the same name, not SSN)
            throw new IllegalArgumentException("SSN Matches Another User");

        if (developerLevel.equals("New Developer"))
        {
            createdUser = new NewDeveloper(firstName, lastName, socialSecurityNumber);
        } else if (developerLevel.equals("Junior Developer"))
        {
            createdUser = new JuniorDeveloper(firstName, lastName, socialSecurityNumber);
        } else {
            createdUser = new SeniorDeveloper(firstName, lastName, socialSecurityNumber);
        }

        // does it still execute?
        System.out.println("Hello");
        _userManager.put(socialSecurityNumber, createdUser);    // add user into the user manager
        createdUser.printPaymentInfo();
    }

    public void paymentInfoScene()
    {
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Payment Info Scene"));

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _mainMenuButtonInfoScene = new JButton("Main Menu");
        _mainMenuButtonInfoScene.addActionListener(this);

        _sceneManager.get("PaymentInfoScene").add(_mainMenuButtonInfoScene);
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
            _sceneManager.get("NewUserScene").setVisible(false);
            _sceneManager.get("PaymentInfoScene").setVisible(true);
        } else if (buttonSource.equals(_mainMenuButtonUserScene) || buttonSource.equals(_mainMenuButtonInfoScene))
        {
            System.out.println("\u001B[32m" + "SWITCHING TO 'Main Scene'" + "\u001B[0m");
            _sceneManager.get("PaymentInfoScene").setVisible(false);
            _sceneManager.get("NewUserScene").setVisible(false);
            _sceneManager.get("MainScene").setVisible(true);
        } else if (buttonSource.equals(_addNewUserButton))
        {
            System.out.println("\u001B[32m" + "SWITCHING TO 'New User Scene'" + "\u001B[0m");
            _sceneManager.get("PaymentInfoScene").setVisible(false);
            _sceneManager.get("MainScene").setVisible(false);
            _sceneManager.get("NewUserScene").setVisible(true);
        } else if (buttonSource.equals(_submitButton))
        {
            System.out.println("\u001B[32m" + "Submitting Information . . ." + "\u001B[0m");
            submitInfo();
        }
        else {
            System.out.println("\u001B[31m" + "Input Source NOT Recognized (or set up)." + "\u001B[0m");
        }

    } // actionPerformed()

    public static void main(String[] args)
    {
        Main application = new Main();
        application.configureWindow();

        //infoVisibleTest();
    } // main()
}