import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 * OOP Lab Section C
 * Lab Final Assignment
 * Login System
 * @author Ahmed Shabab Noor (011 193 024)
 */

public class LoginSystem extends JFrame {

    private static final JPanel homePanel = new JPanel(new BorderLayout());

    private static final File file = new File("emailAndPass.txt");
    private static final String separator = " ### ";

    public LoginSystem(){
        super("Login System");
        setSize(new Dimension(550, 470));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JButton createAccountButton = new JButton("Create Account", 0);
        JButton logInButton = new JButton("Log In", 0);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object x = e.getSource();
                if (x.equals(createAccountButton)){
                    setContentPane(new CreateNewAccountPanel());
                }
                else if (x.equals(logInButton)){
                    setContentPane(new LogInPanel());
                }
                revalidate();
            }
        };
        createAccountButton.addActionListener(buttonListener);
        logInButton.addActionListener(buttonListener);

        //design for home page
        /*
         * level 0 -> homePanel
         * level 1 -> homeTitlePanel, homeCenterPanel
         * level 2 -> homeContentPanel
         * level 3 -> createAccountButton, logInButton
         */
        JPanel homeCenterPanel = new JPanel(new BorderLayout());
        homeCenterPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel homeTitlePanel = new JPanel();
        AddressBook.JLabel title = new AddressBook.JLabel("Login System");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        homeTitlePanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        homeTitlePanel.add(title);

        JPanel homeContentPanel = new JPanel();
        homeContentPanel.setBorder(new TitledBorder(""));
        homeContentPanel.add(new AddressBook.JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(new AddressBook.JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(createAccountButton);
        homeContentPanel.add(new AddressBook.JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(logInButton);

        homeCenterPanel.add(homeContentPanel, BorderLayout.CENTER);
        homePanel.add(homeTitlePanel, BorderLayout.NORTH);
        homePanel.add(homeCenterPanel, BorderLayout.CENTER);

        setContentPane(homePanel);
        setVisible(true);
    }

    static class JLabel extends javax.swing.JLabel{
        JLabel(String title){
            super(title);
        }
        JLabel(String title, int mode){
            super(title);
            if (mode == 0)
                setPreferredSize(new Dimension(110, 30));
            if (mode == 1){
                setFont(new Font("Arial", Font.BOLD, 14));
            }
            if (mode == 2)
                setPreferredSize(new Dimension(450, 10));
        }
    }
    static class JButton extends javax.swing.JButton{
        JButton(String title){
            super(title);
        }
        JButton(String title, int mode){
            super(title);
            if (mode == 0){ //'0' is for buttons that will stay in the home page
                setPreferredSize(new Dimension(250, 40));
            }
            if (mode == 1){ //'1' is for buttons that will stay in the add new account page
                setPreferredSize(new Dimension(30, 10));
            }
        }
    }
    static class JTextField extends javax.swing.JTextField{
        JTextField(){
            super();
            setPreferredSize(new Dimension(350, 30));
        }
    }
    static class JPasswordField extends javax.swing.JPasswordField{
        JPasswordField(){
            super();
            setPreferredSize(new Dimension(350, 30));
            setMinimumSize(new Dimension(350, 30));
        }
    }

    class CreateNewAccountPanel extends JPanel{

        JTextField nameField, emailField;
        JPasswordField passwordField, retypePasField;

        CreateNewAccountPanel(){
            JLabel nameLabel = new JLabel("Name: ", 0);
            JLabel emailLabel = new JLabel("E-mail: ", 0);
            JLabel passwordLabel = new JLabel("Password: ", 0);
            JLabel retypePassLabel = new JLabel("Retype Password: ", 0);

            nameField = new JTextField();
            emailField = new JTextField();

            passwordField = new JPasswordField();
            retypePasField = new JPasswordField();

            JLabel status = new JLabel("", 1);
            JLabel status2 = new JLabel("");

            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    char[] pass = passwordField.getPassword();
                    char[] retypePass = retypePasField.getPassword();


                    int statusVal = registerPerson(name, email, pass);

                    if (statusVal == 1) status.setText("Name can not be empty!");
                    else if (statusVal == 2) status.setText("Email can not be empty!");
                    else if (statusVal == 3) status.setText("Password can not be empty!");
                    else if (statusVal == 4) status.setText("Password must be 8 to 12 characters long!");
                    else if (statusVal == 5) status.setText("An account already exists with this email!");
                    else if (!Arrays.equals(pass, retypePass)){
                        statusVal = 6;
                        status.setText("Password didn't match!");
                    }
                    else if (emailValidator(email) == 1){
                        statusVal = 7;
                        status.setText("Incorrect email format!");
                    }

                    if (statusVal >= 1 && statusVal <= 7)
                        status.setForeground(Color.RED);

                    if (statusVal == 0){
                        status.setText("Account successfully created!");
                        status.setForeground(new Color(47, 165, 98));
                        status2.setText("Go back or create another account.");
                    }
                    revalidate();
                }
            });

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");
                    retypePasField.setText("");

                    status.setText(""); status2.setText("");
                    revalidate();
                }
            });

            JButton backButton = new JButton("Go Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setContentPane(homePanel);
                    revalidateMethod();
                }
            });

            //design for CreateAccountPanel
            /*
             * level 0 -> CreateAccountPanel
             * level 1 -> goBackPanel, outerPanel
             * level 2 -> (inside outerPanel) innerPanel
             * level 3 -> (inside innerPanel) namePanel, emailPanel, passwordPanel, retypePassPanel, bottomPanel
             * level 4 -> (inside bottomPanel) buttonPanel, statusPanel
             */
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            JPanel outerPanel = new JPanel();
            outerPanel.setBorder(new TitledBorder("Create New Account"));
            JPanel innerPanel = new JPanel(new FlowLayout());
            innerPanel.setPreferredSize(new Dimension(500, 320));

            JPanel goBackPanel = new JPanel();
            goBackPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            goBackPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            goBackPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            goBackPanel.add(backButton);

            JPanel namePanel = new JPanel();
            namePanel.add(nameLabel); namePanel.add(nameField);

            JPanel emailPanel = new JPanel();
            emailPanel.add(emailLabel); emailPanel.add(emailField);

            JPanel passwordPanel = new JPanel();
            passwordPanel.add(passwordLabel); passwordPanel.add(passwordField);

            JPanel retypePassPanel = new JPanel();
            retypePassPanel.add(retypePassLabel); retypePassPanel.add(retypePasField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(clearButton); buttonPanel.add(registerButton);

            JPanel status1Panel = new JPanel(); status1Panel.add(status);
            JPanel status2Panel = new JPanel(); status2Panel.add(status2);

            JPanel statusPanel = new JPanel(new GridLayout(2, 1));
            statusPanel.add(status1Panel);
            statusPanel.add(status2Panel);

            JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
            bottomPanel.add(buttonPanel);
            bottomPanel.add(statusPanel);

            innerPanel.add(namePanel);
            innerPanel.add(emailPanel);
            innerPanel.add(passwordPanel);
            innerPanel.add(retypePassPanel);
            innerPanel.add(bottomPanel);

            outerPanel.add(innerPanel);
            add(goBackPanel, BorderLayout.NORTH);
            add(outerPanel, BorderLayout.CENTER);
        }

        class JTextField extends javax.swing.JTextField{
            JTextField(){
                super();
                setPreferredSize(new Dimension(350, 30));
            }
        }
    }

    class LogInPanel extends JPanel{

        JTextField emailField;
        JPasswordField passwordField;

        LogInPanel(){
            JLabel emailLabel = new JLabel("E-mail: ", 0);
            JLabel passwordLabel = new JLabel("Password: ", 0);

            emailField = new JTextField();
            passwordField = new JPasswordField();

            JLabel status = new JLabel("", 1);
            JLabel status2 = new JLabel("");

            JButton logInButton = new JButton("Log In");
            logInButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    char[] pass = passwordField.getPassword();

                    int statusVal = accountChecker(email, pass);

                    if (statusVal == 1) status.setText("Email can not be empty!");
                    else if (statusVal == 2) status.setText("Password can not be empty!");
                    else if (statusVal == 3) status.setText("Incorrect email format!");
                    else if (statusVal == 4) status.setText("Incorrect email or password!");

                    if (statusVal >= 1 && statusVal <= 4)
                        status.setForeground(Color.RED);

                    if (statusVal == 0){
                        status.setText("Log in successful!");
                        status.setForeground(new Color(47, 165, 98));
                    }
                    revalidate();
                }
            });

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    emailField.setText("");
                    passwordField.setText("");

                    status.setText(""); status2.setText("");
                    revalidate();
                }
            });

            JButton backButton = new JButton("Go Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setContentPane(homePanel);
                    revalidateMethod();
                }
            });

            //design for LogInPanel
            /*
             * level 0 -> LogInPanel
             * level 1 -> goBackPanel, outerPanel
             * level 2 -> (inside outerPanel) innerPanel
             * level 3 -> (inside innerPanel) emailPanel, passwordPanel, bottomPanel
             * level 4 -> (inside bottomPanel) buttonPanel, statusPanel
             */
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            JPanel outerPanel = new JPanel();
            outerPanel.setBorder(new TitledBorder("Log In"));
            JPanel innerPanel = new JPanel(new FlowLayout());
            innerPanel.setPreferredSize(new Dimension(500, 320));

            JPanel goBackPanel = new JPanel();
            goBackPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            goBackPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            goBackPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            goBackPanel.add(backButton);


            JPanel emailPanel = new JPanel();
            emailPanel.add(emailLabel); emailPanel.add(emailField);

            JPanel passwordPanel = new JPanel();
            passwordPanel.add(passwordLabel); passwordPanel.add(passwordField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(clearButton); buttonPanel.add(logInButton);

            JPanel status1Panel = new JPanel(); status1Panel.add(status);
            JPanel status2Panel = new JPanel(); status2Panel.add(status2);

            JPanel statusPanel = new JPanel(new GridLayout(2, 1));
            statusPanel.add(status1Panel);
            statusPanel.add(status2Panel);

            JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
            bottomPanel.add(buttonPanel);
            bottomPanel.add(statusPanel);

            innerPanel.add(emailPanel);
            innerPanel.add(passwordPanel);
            innerPanel.add(bottomPanel);

            outerPanel.add(innerPanel);
            add(goBackPanel, BorderLayout.NORTH);
            add(outerPanel, BorderLayout.CENTER);
        }
    }

    void revalidateMethod(){
        this.revalidate();
    }

    //method for creating new account and adding it to the file.
    private static int registerPerson(String name, String email, char[] pass){
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;

        if (name.length() == 0) return 1; //nameField is empty
        if (email.length() == 0) return 2; //emailField is empty
        if (pass.length == 0) return 3; //passwordField is empty

        int length = pass.length;
        if (length < 8 || length > 12 ) return 4; //password isn't 8 to 12 characters long

        try{
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            Scanner scanner = new Scanner(file);

            String password = new String(pass);

            String information = name+separator+email+separator+password;

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] components = line.split(separator);
                if (components[1].equals(email))
                    return 5; //account already exists with this email
            }

            pr.println(information);

        }catch (IOException e){
            fileError();
            e.printStackTrace();
        }finally {
            try{
                if (pr != null) pr.close();
                if (br != null) br.close();
                if (fr != null) fr.close();
            }catch (IOException | NullPointerException e){
                e.printStackTrace();
            }
        }
        return 0;
    }

    //method for verifying correct email and password
    private static int accountChecker(String email, char[] pass){
        String password = new String(pass);

        if (email.length() == 0) return 1; //emailField can't be empty
        if (password.length() == 0) return 2; //passwordField can't be empty
        if (emailValidator(email) == 1) return 3; //incorrect email format

        try{
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] components = line.split(separator);

                if (components[1].equals(email) && components[2].equals(password))
                    return 0; //correct email and password

            }
        }catch (IOException e){
            fileError();
        }
        return 4; //incorrect email or password
    }
    private static int emailValidator(String email){
        if (email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$"))
            return 0;
        else
            return 1;
    }

    private static void fileError(){
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,"There's something wrong with the file!","Alert!", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        new LoginSystem();
    }
}
