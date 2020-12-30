import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

/**
 * OOP Lab Section C
 * Lab Final Assignment
 * Address Book
 * @author Ahmed Shabab Noor (011 193 024)
 */

public class AddressBook extends JFrame {

    private static final JPanel homePanel = new JPanel(new BorderLayout());

    private JTextArea textAreaFromSearch = new JTextArea(15, 30);
    private JTextArea textAreaFromAllInfo = new JTextArea(15, 30);

    private JTextField searchBox, searchBoxFromSearch;

    private static final File file = new File("allInfo.txt");
    private static final String separator = " ### ";
    private static HashSet <String> contentSet = new HashSet<>();

    public AddressBook(){
        super("Address Book");
        setSize(new Dimension(550, 470));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel searchPanel = new JPanel();
        JPanel allInfoPanel = new JPanel();

        JScrollPane scrollPaneFromSearch = new JScrollPane(textAreaFromSearch);
        JScrollPane scrollPaneFromAllInfo = new JScrollPane(textAreaFromAllInfo);

        searchBox = new JTextField(30);
        searchBox.setPreferredSize(new Dimension(300, 35));

        JButton searchButton = new JButton("Search", 0);
        JButton addPersonButton = new JButton("Add New Person", 0);
        JButton allInfoButton = new JButton("View All Information", 0);

        JButton backButtonFromSearch = new JButton("Go Back");
        JButton backButtonFromAllInfo = new JButton("Go Back");


        //button action listeners
        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object x = e.getSource();
                if (x.equals(searchButton)) {
                    searchBoxFromSearch.setText(searchBox.getText());
                    searchPerson(searchBox.getText());
                    setContentPane(searchPanel);
                    searchPanel.revalidate();
                }

                if (x.equals(addPersonButton))
                    setContentPane(new AddPersonPanel());

                if (x.equals(allInfoButton)) {
                    viewAllInfo();
                    setContentPane(allInfoPanel);
                }

                if (x.equals(backButtonFromSearch) || x.equals(backButtonFromAllInfo)){
                    setContentPane(homePanel);
                    searchBox.setText("");
                }

                revalidate();
            }
        };

        searchButton.addActionListener(buttonListener);
        addPersonButton.addActionListener(buttonListener);
        allInfoButton.addActionListener(buttonListener);
        backButtonFromSearch.addActionListener(buttonListener);
        backButtonFromAllInfo.addActionListener(buttonListener);


        //design for home page
        /*
         * level 0 -> homePanel
         * level 1 -> homeTitlePanel, homeCenterPanel
         * level 2 -> homeContentPanel
         * level 3 -> searchBox, searchButton, addPersonButton, allInfoButton
         */
        JPanel homeCenterPanel = new JPanel(new BorderLayout());
        homeCenterPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel homeTitlePanel = new JPanel();
        JLabel title = new JLabel("Address Book");
        title.setFont(new Font("Arial", Font.BOLD, 23));
        homeTitlePanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        homeTitlePanel.add(title);

        JPanel homeContentPanel = new JPanel();
        homeContentPanel.setBorder(new TitledBorder(""));

        homeContentPanel.add(new JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(new JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(searchBox);
        homeContentPanel.add(new JLabel("", 2)); //empty space for decoration
        homeContentPanel.add(searchButton);
        homeContentPanel.add(addPersonButton);
        homeContentPanel.add(allInfoButton);


        homeCenterPanel.add(homeContentPanel, BorderLayout.CENTER);
        homePanel.add(homeTitlePanel, BorderLayout.NORTH);
        homePanel.add(homeCenterPanel, BorderLayout.CENTER);

        //design for the panel that will host the back button (both from search and allInfo)
        /*
         * level 0 -> goBackPanel
         * level 1 -> backButton
         */
        JPanel goBackPanelFromSearch = new JPanel();
        goBackPanelFromSearch.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        goBackPanelFromSearch.setLayout(new FlowLayout(FlowLayout.LEADING));
        goBackPanelFromSearch.setBorder(new EmptyBorder(5, 5, 5, 5));
        goBackPanelFromSearch.add(backButtonFromSearch);

        JPanel goBackPanelFromAllInfo = new JPanel();
        goBackPanelFromAllInfo.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        goBackPanelFromAllInfo.setLayout(new FlowLayout(FlowLayout.LEADING));
        goBackPanelFromAllInfo.setBorder(new EmptyBorder(5, 5, 5, 5));
        goBackPanelFromAllInfo.add(backButtonFromAllInfo);
        //end.


        //design for search panel
        /*
         * level 0 -> homePanel
         * level 1 -> searchPanel
         * level 2 -> goBackPanel, searchCenterPanel
         * level 3 -> (inside searchCenterPanel) searchingPanel, textAreaFromSearchPanel
         * level 4 -> (inside searchingPanel) searchBox, searchButton, (inside textAreaFromSearchPanel) scrollPaneFromSearch
         */
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JPanel searchCenterPanel = new JPanel();
        searchCenterPanel.setLayout(new BorderLayout());

        JPanel searchingPanel = new JPanel();
        searchingPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        JPanel textAreaFromSearchPanel = new JPanel(new BorderLayout());
        textAreaFromSearchPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        searchBoxFromSearch = new JTextField();
        JButton searchButtonFromSearch = new JButton("Search");
        searchButtonFromSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchPerson(searchBoxFromSearch.getText());
                revalidate();
            }
        });

        searchBoxFromSearch.setPreferredSize(new Dimension(300, 30));
        searchButtonFromSearch.setPreferredSize(new Dimension(100, 30));

        searchingPanel.add(searchBoxFromSearch);
        searchingPanel.add(searchButtonFromSearch);
        textAreaFromSearchPanel.add(scrollPaneFromSearch);

        searchCenterPanel.add(searchingPanel, BorderLayout.NORTH);
        searchCenterPanel.add(textAreaFromSearchPanel, BorderLayout.CENTER);
        searchCenterPanel.setBorder(new TitledBorder("Search Any Person"));

        searchPanel.add(goBackPanelFromSearch, BorderLayout.NORTH);
        searchPanel.add(searchCenterPanel, BorderLayout.CENTER);
        //end.


        //design for allInfo panel
        /*
         * level 0 -> homePanel
         * level 1 -> allInfoPanel
         * level 2 -> goBackPanel, allInfoCenterPanel
         * level 3 -> (inside allInfoCenterPanel) textAreaFromAllInfoPanel
         * level 4 -> (inside textAreaFromAllInfoPanel) scrollPaneFromAllInfo
         */
        allInfoPanel.setLayout(new BorderLayout());
        allInfoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel allInfoCenterPanel = new JPanel();
        allInfoCenterPanel.setLayout(new BorderLayout());
        allInfoCenterPanel.setBorder(new TitledBorder("Information of All People"));

        JPanel textAreaFromAllInfoPanel = new JPanel(new BorderLayout());
        textAreaFromAllInfoPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        textAreaFromAllInfoPanel.add(scrollPaneFromAllInfo);
        allInfoCenterPanel.add(textAreaFromAllInfoPanel);

        allInfoPanel.add(goBackPanelFromAllInfo, BorderLayout.NORTH);
        allInfoPanel.add(allInfoCenterPanel, BorderLayout.CENTER);
        //end.

        textAreaFromSearch.setEditable(false);
        textAreaFromAllInfo.setEditable(false);

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
                setPreferredSize(new Dimension(100, 30));
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
            if (mode == 1){ //'1' is for buttons that will stay in the add new person page
                setPreferredSize(new Dimension(30, 10));
            }
        }
    }

    class AddPersonPanel extends JPanel{

        JTextField nameField, phoneNoField, emailField;
        JTextArea addressField;

        AddPersonPanel(){
            JLabel nameLabel = new JLabel("Name: ", 0);
            JLabel phoneNoLabel = new JLabel("Phone No.: ", 0);
            JLabel emailLabel = new JLabel("E-mail: ", 0);
            JLabel addressLabel = new JLabel("Home Address: ", 0);

            nameField = new JTextField();
            phoneNoField = new JTextField();
            emailField = new JTextField();
            addressField = new JTextArea();
            addressField.setPreferredSize(new Dimension(350, 50));

            JLabel status = new JLabel("", 1);
            JLabel status2 = new JLabel("");

            JButton addPersonButton = new JButton("Add Person to Address Book");
            addPersonButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameField.getText();
                    String phoneNo = phoneNoField.getText();
                    String email = emailField.getText();
                    String address = addressField.getText();
                    int statusVal = addPersonToFile(name, phoneNo, email, address);

                    if (statusVal == 1) status.setText("Name can not be empty!");
                    else if (statusVal == 2) status.setText("Phone number can not be empty!");
                    else if (statusVal == 3) status.setText("Email can not be empty!");
                    else if (statusVal == 4) status.setText("Address can not be empty!");
                    else if (statusVal == 5) status.setText("Incorrect phone number format!");
                    else if (statusVal == 6) status.setText("Incorrect email format!");
                    else if (statusVal == 7) status.setText("Person with same details already exists!");

                    if (statusVal >= 1 && statusVal <= 7)
                        status.setForeground(Color.RED);

                    if (statusVal == 0){
                        status.setText("Information successfully added!");
                        status.setForeground(new Color(47, 165, 98));
                        status2.setText("Go back or add another person.");
                    }
                    revalidate();
                }
            });

            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameField.setText("");
                    phoneNoField.setText("");
                    emailField.setText("");
                    addressField.setText("");
                    status.setText(""); status2.setText("");
                    revalidate();
                }
            });

            JButton backButton = new JButton("Go Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setContentPane(homePanel);
                    searchBox.setText("");
                    revalidateMethod();
                }
            });

            //design for AddPersonPanel
            /*
             * level 0 -> AddPersonPanel
             * level 1 -> outerPanel
             * level 2 -> goBackPanel, allInfoCenterPanel
             * level 3 -> (inside allInfoCenterPanel) textAreaFromAllInfoPanel
             * level 4 -> (inside textAreaFromAllInfoPanel) scrollPaneFromAllInfo
             */
            setLayout(new BorderLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));
            JPanel outerPanel = new JPanel();
            outerPanel.setBorder(new TitledBorder("Add New People"));
            JPanel innerPanel = new JPanel(new FlowLayout());
            innerPanel.setPreferredSize(new Dimension(500, 320));

            JPanel goBackPanel = new JPanel();
            goBackPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            goBackPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
            goBackPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            goBackPanel.add(backButton);

            JPanel namePanel = new JPanel();
            namePanel.add(nameLabel); namePanel.add(nameField);
            JPanel phoneNoPanel = new JPanel();
            phoneNoPanel.add(phoneNoLabel); phoneNoPanel.add(phoneNoField);
            JPanel emailPanel = new JPanel();
            emailPanel.add(emailLabel); emailPanel.add(emailField);
            JPanel addressPanel = new JPanel();
            addressPanel.add(addressLabel); addressPanel.add(addressField);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(clearButton); buttonPanel.add(addPersonButton);

            JPanel status1Panel = new JPanel(); status1Panel.add(status);
            JPanel status2Panel = new JPanel(); status2Panel.add(status2);

            JPanel statusPanel = new JPanel(new GridLayout(2, 1));
            //statusPanel.add(new javax.swing.JLabel(" "));
            statusPanel.add(status1Panel);
            statusPanel.add(status2Panel);


            JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
            bottomPanel.add(buttonPanel);
            bottomPanel.add(statusPanel);

            innerPanel.add(namePanel);
            innerPanel.add(phoneNoPanel);
            innerPanel.add(emailPanel);
            innerPanel.add(addressPanel);
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

    void revalidateMethod(){
        this.revalidate();
    }

    //method for adding information of a new person to the file.
    private static int addPersonToFile(String name, String phoneNo, String email, String address){
        FileWriter fr = null;
        BufferedWriter br = null;
        PrintWriter pr = null;

        if (name.length() == 0) return 1; //nameField is empty
        if (phoneNo.length() == 0) return 2; //phoneNoField is empty
        if (email.length() == 0) return 3; //emailField is empty
        if (address.length() == 0) return 4; //addressField is empty

        int validate = phoneNoValidator(phoneNo);
        if (validate == 1) return 5; //phone number is not in correct format.
        validate = emailValidator(email);
        if (validate == 1) return 6; //email isn't in correct format.

        try{
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            pr = new PrintWriter(br);
            Scanner scanner = new Scanner(file);

            String content = name+separator+phoneNo+separator+email+separator+address;

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                if (content.equals(line)){
                    return 7; //person already exists.
                }
            }

            if (contentSet.contains(content))
                return 7;

                /*kept this hashSet so that if someone accidentally presses the addPersonButton
                 *repeatedly it can quickly tell that this person has already been added.
                 */

            else {
                contentSet.add(content);
                pr.println(content);
            }

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

    private static int phoneNoValidator(String phoneNo){
        if (phoneNo.matches("[\\d]+")) //checks if there are digits.
            if(phoneNo.matches("[\\d\\s-]*")) return 0; //checks if there are digits and/or whitespace and/or hyphens

        return 1;
    }
    private static int emailValidator(String email){
        if (email.matches("^[\\w-.]+@([\\w-]+.)+[\\w-]{2,4}$"))
            return 0;
        else
            return 1;
    }

    //method for viewing information of all people in the address book.
    private void viewAllInfo(){
        try{
            Scanner scanner = new Scanner(file);
            String info;

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] components = line.split(separator);
                info  = " Name: " + components[0] + "\n";
                info += " Phone No.: "+components[1]+"\n";
                info += " Email: "+components[2]+"\n";
                info += " Address: "+components[3]+"\n";
                textAreaFromAllInfo.append(info+"\n");
            }

        }catch (IOException e){
            fileError();
        }
    }

    //method for searching people from the address book.
    private void searchPerson(String searchName){

        textAreaFromSearch.setText("");
        try{
            Scanner scanner = new Scanner(file);
            String info;

            while (scanner.hasNext()){
                String line = scanner.nextLine();
                String[] components = line.split(separator);
                if (searchName.length() == 0){
                    return;
                }
                if (components[0].contains(searchName)){
                    info  = " Name: " + components[0] + "\n";
                    info += " Phone No.: "+components[1]+"\n";
                    info += " Email: "+components[2]+"\n";
                    info += " Address: "+components[3]+"\n";
                    textAreaFromSearch.append(info+"\n");
                }
            }
        }catch (IOException e){
            fileError();
        }
    }

    private static void fileError(){
        JFrame f=new JFrame();
        JOptionPane.showMessageDialog(f,"There's something wrong with the file!","Alert!", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        new AddressBook();
    }
}
