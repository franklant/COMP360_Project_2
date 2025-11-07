import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    JButton _payEmployeesButton;
    JComboBox _withOrWithoutBonusComboBox;

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
    JLabel _firstNameInfoLabel;
    JLabel _lastNameInfoLabel;
    JLabel _socialSecurityNumberInfoLabel;
    JLabel _salaryInfoLabel;
    JLabel _developerLevelInfoLabel;
    JLabel _onePercentSalaryInfoLabel;          // these components appear based on developer level
    JLabel _stocksInfoLabel;
    JLabel _totalPaymentInfoLabel;
    JLabel _annualPaymentInfoLabel;


    JLabel _label1;                             // additional labels to be hidden
    JLabel _label2;
    JComboBox _userNameComboBox;
    JButton _showInfoButton;
    ArrayList<Integer> _socialSecurityNumberList;
    GridBagConstraints _gridBagConstraints;

    public void configureWindow()
    {
        this.setTitle("Project 2 Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(720, 430));
        this.setResizable(false);

        interfaceContents();

        this.pack();                                            // pack all contents
        this.setVisible(true);                                  // make the window visible
        this.setLocationRelativeTo(null);                       // place the window at the center of the screen
    } // configureWindow()

    public void interfaceContents()
    {
        // responsible for containing all the GUI/UI components and layout
        this.setLayout(new GridBagLayout());                    // instance the layout for all components
        _socialSecurityNumberList = new ArrayList<>();

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
        _gridBagConstraints = new GridBagConstraints();
        _gridBagConstraints.fill = GridBagConstraints.BOTH;

        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 0;

        _sceneManager.get("MainScene").setLayout(new GridBagLayout());      // important to set the layout
        _sceneManager.get("MainScene").add(new JLabel("HUA DONG COMPANY© Payment System"), _gridBagConstraints);

        _paymentInfoButton = new JButton("Request Payment Information");
        _paymentInfoButton.addActionListener(this);           // enable the program to listen for actions from btn

        _addNewUserButton = new JButton("Add New Employee");
        _addNewUserButton.addActionListener(this);

        _payEmployeesButton = new JButton("Pay Employees");
        _payEmployeesButton.addActionListener(this);

        String[] withOrWithoutBonus = {"Without Bonus", "With Bonus"};
        _withOrWithoutBonusComboBox = new JComboBox(withOrWithoutBonus);

        // Add Components to Scene
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 1;
        _gridBagConstraints.insets = new Insets(20, 0, 0, 0);
        _sceneManager.get("MainScene").add(_addNewUserButton, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 2;
        _gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        _sceneManager.get("MainScene").add(_paymentInfoButton, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 3;
        _gridBagConstraints.insets = new Insets(40, 0, 10, 0);
        _sceneManager.get("MainScene").add(new JLabel("Disburse Payments:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 4;
        _gridBagConstraints.gridwidth = 1;
        _gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        _sceneManager.get("MainScene").add(_payEmployeesButton, _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 4;
        _sceneManager.get("MainScene").add(_withOrWithoutBonusComboBox, _gridBagConstraints);
    } // mainScene()

    public void payEmployees()
    {
        String withOrWithoutResult = _withOrWithoutBonusComboBox.getSelectedItem().toString();
        int confirmPayResult = JOptionPane.showConfirmDialog(
                this,
                "CONFIRM: Pay All Employees " + withOrWithoutResult + "?"
        );

        if (_userManager.isEmpty() && confirmPayResult == 0)
        {
            JOptionPane.showMessageDialog(this, "No Employees to Pay!");
            throw new IllegalArgumentException("There Are No Employees to Pay!");
        }

        if (confirmPayResult == 0) {    // User confirmed "Yes"
            if (withOrWithoutResult.equals("Without Bonus"))
            {
                Enumeration<NewDeveloper> developers = _userManager.elements();
                while (developers.hasMoreElements()) {
                    NewDeveloper developer = developers.nextElement();

                    developer.addPayment();

                    System.out.println("Total Payment to " + developer._socialSecurityNumber + ": " + developer._totalPayment);
                }
            } else {
                Enumeration<NewDeveloper> developers = _userManager.elements();
                while (developers.hasMoreElements()) {
                    NewDeveloper developer = developers.nextElement();

                    developer.addPayment();
                    developer.addBonus();

                    System.out.println("Total Payment to " + developer._socialSecurityNumber + ": " + developer._totalPayment);
                }
            }

            JOptionPane.showMessageDialog(this, "Payment to All Employees Successful!");
        }
    } // payEmployees()

    public void newUserScene()
    {
        _sceneManager.get("NewUserScene").add(new JLabel("Add New User"));
        _sceneManager.get("NewUserScene").setLayout(new GridBagLayout());

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

        // Add and Format Components
        _gridBagConstraints = new GridBagConstraints();
        _gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 0;
        _gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        _gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
        _sceneManager.get("NewUserScene").add(_mainMenuButtonUserScene, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 1;
        _gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        _sceneManager.get("NewUserScene").add(_firstNameLabel, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 1;
        _sceneManager.get("NewUserScene").add(_firstNameTextArea, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 2;
        _sceneManager.get("NewUserScene").add(_lastNameLabel, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 2;
        _sceneManager.get("NewUserScene").add(_lastNameTextArea, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 3;
        _sceneManager.get("NewUserScene").add(_socialSecurityNumberLabel, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 3;
        _sceneManager.get("NewUserScene").add(_socialSecurityNumberTextArea, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 4;
        _sceneManager.get("NewUserScene").add(_developerLevelLabel, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 4;
        _sceneManager.get("NewUserScene").add(_developerLevelComboBox, _gridBagConstraints);

        _gridBagConstraints.fill = GridBagConstraints.BOTH;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 5;
        _gridBagConstraints.gridwidth = 2;
        _gridBagConstraints.insets = new Insets(20, 5, 5, 5);
        _gridBagConstraints.anchor = GridBagConstraints.LAST_LINE_END;
        _sceneManager.get("NewUserScene").add(_submitButton, _gridBagConstraints);
    } // newUserScene()

    public void submitInfo()
    {
        NewDeveloper createdUser;   // because inherited classes can also be instantiated to a variable with their base class type
        String developerLevel = _developerLevelComboBox.getSelectedItem().toString();

        String firstName = _firstNameTextArea.getText();
        String lastName = _lastNameTextArea.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || _socialSecurityNumberTextArea.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "One Or More Fields Have Not Been Entered!");
            throw new IllegalArgumentException("1 Or More Fields Have Not Been Entered");
        }

        int socialSecurityNumber = Integer.parseInt(_socialSecurityNumberTextArea.getText());

        if (developerLevel == null)
            throw new IllegalArgumentException("Developer Level cannot be String-ified");

        if (_userManager.get(socialSecurityNumber) != null) // this social security number already exists (people can have the same name, not SSN)
        {
            JOptionPane.showMessageDialog(this, "Social Security Number Matches Another User!");
            throw new IllegalArgumentException("SSN Matches Another User");
        }

        if (developerLevel.equals("New Developer"))
        {
            createdUser = new NewDeveloper(firstName, lastName, socialSecurityNumber);
        } else if (developerLevel.equals("Junior Developer"))
        {
            createdUser = new JuniorDeveloper(firstName, lastName, socialSecurityNumber);
        } else {
            createdUser = new SeniorDeveloper(firstName, lastName, socialSecurityNumber);
        }

        _userManager.put(socialSecurityNumber, createdUser);    // add user into the user manager
        createdUser.printPaymentInfo();
        JOptionPane.showMessageDialog(this, "User Added!");
    } // submitInfo()

    public void refreshUserList()
    {
        _socialSecurityNumberList = new ArrayList<>();

        Enumeration<Integer> developers = _userManager.keys();
        while (developers.hasMoreElements()) {
            Integer currentSocialSecurityNumber = developers.nextElement();

            _socialSecurityNumberList.add(currentSocialSecurityNumber);
        }

        // remove and then readd the component to the panel
        _sceneManager.get("PaymentInfoScene").remove(_userNameComboBox);

        _userNameComboBox = new JComboBox(_socialSecurityNumberList.toArray());

        _gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 1;
        _gridBagConstraints.gridwidth = 1;
        _gridBagConstraints.anchor = GridBagConstraints.CENTER;
        _sceneManager.get("PaymentInfoScene").add(_userNameComboBox, _gridBagConstraints);
    } // refreshUserList()

    public void paymentInfoScene()
    {
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Payment Info Scene"));
        _sceneManager.get("PaymentInfoScene").setLayout(new GridBagLayout());

        //_gridBagConstraints.fill = GridBagConstraints.BOTH;
        _mainMenuButtonInfoScene = new JButton("Main Menu");
        _mainMenuButtonInfoScene.addActionListener(this);

        _userNameComboBox = new JComboBox(_socialSecurityNumberList.toArray());

        _showInfoButton = new JButton("Show Employee Information");
        _showInfoButton.addActionListener(this);

        _firstNameInfoLabel = new JLabel();
        _firstNameInfoLabel.setPreferredSize(new Dimension(100, 20));

        _lastNameInfoLabel = new JLabel();
        _lastNameInfoLabel.setPreferredSize(new Dimension(100, 20));

        _socialSecurityNumberInfoLabel = new JLabel();
        _socialSecurityNumberInfoLabel.setPreferredSize(new Dimension(100, 20));

        _salaryInfoLabel = new JLabel();
        _salaryInfoLabel.setPreferredSize(new Dimension(100, 20));

        _developerLevelInfoLabel = new JLabel();
        _developerLevelInfoLabel.setPreferredSize(new Dimension(100, 20));

        _label1 = new JLabel("1% of Salary: ");
        _onePercentSalaryInfoLabel = new JLabel();
        _onePercentSalaryInfoLabel.setPreferredSize(new Dimension(100, 20));

        _label2 = new JLabel("Stocks: ");
        _stocksInfoLabel = new JLabel();
        _stocksInfoLabel.setPreferredSize(new Dimension(100, 20));

        _totalPaymentInfoLabel = new JLabel();
        _totalPaymentInfoLabel.setPreferredSize(new Dimension(100, 20));

        _annualPaymentInfoLabel = new JLabel();
        _annualPaymentInfoLabel.setPreferredSize(new Dimension(100, 20));

        // Automatically hide these components
        _label1.setVisible(false);
        _label2.setVisible(false);
        _onePercentSalaryInfoLabel.setVisible(false);
        _stocksInfoLabel.setVisible(false);

        _gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 1;
        _gridBagConstraints.gridwidth = 1;
        _gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Select SSN for Employee"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 0;
        _sceneManager.get("PaymentInfoScene").add(_mainMenuButtonInfoScene, _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 1;
        _gridBagConstraints.gridwidth = 1;
        _sceneManager.get("PaymentInfoScene").add(_userNameComboBox, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 2;
        _gridBagConstraints.gridwidth = 2;
        _sceneManager.get("PaymentInfoScene").add(_showInfoButton, _gridBagConstraints);

        // Display info
        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 3;
        _gridBagConstraints.gridwidth = 1;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("First Name:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 3;
        _sceneManager.get("PaymentInfoScene").add(_firstNameInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 4;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Last Name:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 4;
        _sceneManager.get("PaymentInfoScene").add(_lastNameInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 5;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Social Security Number:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 5;
        _sceneManager.get("PaymentInfoScene").add(_socialSecurityNumberInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 6;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Salary:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 6;
        _sceneManager.get("PaymentInfoScene").add(_salaryInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 7;
        _gridBagConstraints.gridwidth = 2;
        _sceneManager.get("PaymentInfoScene").add(_developerLevelInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 8;
        _gridBagConstraints.gridwidth = 1;
        _sceneManager.get("PaymentInfoScene").add(_label1, _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 8;
        _sceneManager.get("PaymentInfoScene").add(_onePercentSalaryInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 9;
        _sceneManager.get("PaymentInfoScene").add(_label2, _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 9;
        _sceneManager.get("PaymentInfoScene").add(_stocksInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 10;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Total Payment:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 10;
        _sceneManager.get("PaymentInfoScene").add(_totalPaymentInfoLabel, _gridBagConstraints);

        _gridBagConstraints.gridx = 0;
        _gridBagConstraints.gridy = 11;
        _sceneManager.get("PaymentInfoScene").add(new JLabel("Annual Payment:"), _gridBagConstraints);

        _gridBagConstraints.gridx = 1;
        _gridBagConstraints.gridy = 11;
        _sceneManager.get("PaymentInfoScene").add(_annualPaymentInfoLabel, _gridBagConstraints);
    } // paymentInfoScene()

    public void showUserInfo()
    {
        // This method should be called everytime the user hits the "Show User Info" button.
        if (_userNameComboBox.getItemCount() == 0) // there are no employees within the userManager
        {
            JOptionPane.showMessageDialog(this, "No Employee Information Has Been Added!");
            throw new IllegalArgumentException("No Employee Information Is Available");
        }

        int selectedSocialSecurityNumber = Integer.parseInt(_userNameComboBox.getSelectedItem().toString());
        NewDeveloper selectedUser = _userManager.get(selectedSocialSecurityNumber);

        double calculateAnnualPayment = selectedUser._salary + selectedUser._BONUS_AMOUNT;

        _firstNameInfoLabel.setText(selectedUser._firstName);
        _lastNameInfoLabel.setText(selectedUser._lastName);

        String SSN = String.valueOf(selectedUser._socialSecurityNumber);
        String formattedSocialSecurityNumber = SSN.substring(0, 3) + "-" + SSN.substring(3, 5) + "-" + SSN.substring(5, 8);
        _socialSecurityNumberInfoLabel.setText(formattedSocialSecurityNumber);

        _salaryInfoLabel.setText("$" + selectedUser._salary + " /mo.");

        if (selectedUser.getClass().equals(NewDeveloper.class)) // If it's a new developer
        {
            _developerLevelInfoLabel.setText("<NEW DEVELOPER>");

            _label1.setVisible(false);
            _label2.setVisible(false);
            _onePercentSalaryInfoLabel.setVisible(false);
            _stocksInfoLabel.setVisible(false);
        } else if (selectedUser.getClass().equals(JuniorDeveloper.class)) {
            calculateAnnualPayment += ((JuniorDeveloper) selectedUser)._additionalBonusAmount;

            _developerLevelInfoLabel.setText("<JUNIOR DEVELOPER>");
            _onePercentSalaryInfoLabel.setText(
                    "$" + ((JuniorDeveloper) selectedUser)._additionalBonusAmount
            );

            _label1.setVisible(true);
            _label2.setVisible(false);
            _onePercentSalaryInfoLabel.setVisible(true);
            _stocksInfoLabel.setVisible(false);
        } else {
            calculateAnnualPayment += ((SeniorDeveloper) selectedUser)._additionalBonusAmount;

            _developerLevelInfoLabel.setText("<SENIOR DEVELOPER>");
            _onePercentSalaryInfoLabel.setText(
                    "$" + ((SeniorDeveloper) selectedUser)._additionalBonusAmount
            );
            _stocksInfoLabel.setText(String.valueOf(((SeniorDeveloper) selectedUser)._stocks));

            _label1.setVisible(true);
            _label2.setVisible(true);
            _onePercentSalaryInfoLabel.setVisible(true);
            _stocksInfoLabel.setVisible(true);
        }

        _totalPaymentInfoLabel.setText("$" + selectedUser._totalPayment);
        _annualPaymentInfoLabel.setText("$" + calculateAnnualPayment * 12 + " /yr.");     // monthly salary + bonuses for all 12 months
    } // showUserInfo()

    public void clearInfoFields()
    {
        // Add New Employee Fields
        _firstNameTextArea.setText("");
        _lastNameTextArea.setText("");
        _socialSecurityNumberTextArea.setText("");

        // Payment Info Fields
        _firstNameInfoLabel.setText("");
        _lastNameInfoLabel.setText("");
        _socialSecurityNumberInfoLabel.setText("");
        _salaryInfoLabel.setText("");
        _developerLevelInfoLabel.setText("<>");

        _label1.setVisible(false);
        _label2.setVisible(false);
        _onePercentSalaryInfoLabel.setVisible(false);
        _stocksInfoLabel.setVisible(false);

        _totalPaymentInfoLabel.setText("");
        _annualPaymentInfoLabel.setText("");
    } // clearInfoFields

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
            refreshUserList();
        } else if (buttonSource.equals(_mainMenuButtonUserScene) || buttonSource.equals(_mainMenuButtonInfoScene))
        {
            System.out.println("\u001B[32m" + "SWITCHING TO 'Main Scene'" + "\u001B[0m");
            _sceneManager.get("PaymentInfoScene").setVisible(false);
            _sceneManager.get("NewUserScene").setVisible(false);
            _sceneManager.get("MainScene").setVisible(true);

            clearInfoFields();
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
        } else if (buttonSource.equals(_showInfoButton))
        {
            System.out.println("\u001B[32m" + "Showing Information . . ." + "\u001B[0m");
            showUserInfo();
        } else if (buttonSource.equals(_payEmployeesButton))
        {
            System.out.println("\u001B[32m" + "Paying Employees . . ." + "\u001B[0m");
            payEmployees();
        }
        else {
            System.out.println("\u001B[31m" + "Input Source NOT Recognized (or set up)." + "\u001B[0m");
        }

    } // actionPerformed()

    public static void main(String[] args)
    {
        Main application = new Main();
        application.configureWindow();
    } // main()
}