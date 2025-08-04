package parkingSlotSysem;



/**The Application class is the key control hub for a parking management system. 
 * In addition to interacting with users, 
 * It controls parking spaces and vehicles,
 * verifies user input, and offers options for navigating the programme.
 * @author   Nidhi Chhoker 
 * @version  JDK version 20.0.2
 * @date     Created on 18 October 2023
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Application {

    private JTextArea outputTextArea;
    private JTextField inputField;
    private CarAnimationPanel carAnimationPanel;

 // Constructor for the Application class
    public Application() {
    	// Create instances of ParkingSlot and CarPark
        ParkingSlot parkingSlot = new ParkingSlot();
        CarPark carPark = new CarPark();
     // Create the main JFrame
        JFrame f = new JFrame();
        f.setTitle("Parking Spot System");
        f.setSize(900, 900);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        
     // Create and customize a title label
        Label title = new Label("Swinburne Parking Slot System");
        Font font1 = new Font("Arial", Font.BOLD, 55);
        title.setFont(font1);
        // Create constraints for layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.anchor = GridBagConstraints.CENTER;
        
        // Create and configure the car animation panel
        
        carAnimationPanel = new CarAnimationPanel();
        carAnimationPanel.setBackground(Color.LIGHT_GRAY);
        f.add(carAnimationPanel, BorderLayout.CENTER);
        carAnimationPanel.add(title, constraints);

     // Create and configure the staff parking slots panel
        JPanel staffSlotsPanel = new JPanel(new GridLayout(0, 10));
       
        f.add(staffSlotsPanel);

        LineBorder border = new LineBorder(Color.black, 2);
        staffSlotsPanel.setBorder(border);

     // Create and configure labels for staff and visitor parking slots
        JLabel staffParkingSlots = new JLabel(
                "<html><b><font size='5'color='blue'>Staff Parking Slots</font></b></html>");
        staffParkingSlots.setBorder(border);

        JLabel visitorParkingSlots = new JLabel(
                "<html><b><font size='5'color='orange'>Visitor Parking Slots</font></b></html>");
        // visitorParkingSlots.setEnabled(false);
        visitorParkingSlots.setBorder(border);

        staffSlotsPanel.add(staffParkingSlots);
     // Create and configure the visitor parking slots panel

        JPanel visitorSlotsPanel = new JPanel(new GridLayout(0, 10));
        ;
        // visitorSlotsPanel.setBackground(Color.cyan);
        f.add(visitorSlotsPanel);

        visitorSlotsPanel.setBorder(border);

        visitorSlotsPanel.add(visitorParkingSlots);
        
     // Create and configure the button panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4));
        buttonPanel.setPreferredSize(new Dimension(buttonPanel.getPreferredSize().width, 150));
        f.add(buttonPanel, BorderLayout.SOUTH);

        // Create and configure the input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 4));

        inputPanel.setBorder(border);
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setPreferredSize(new Dimension(inputPanel.getPreferredSize().width, 150));
        inputPanel.setBackground(Color.LIGHT_GRAY);
        f.add(inputPanel);

     // Create buttons and set their styles
        OvalButton addSlotButton = new OvalButton("<html><b><font size='3'>Add Slot</font></b></html>");
        setButtonStyle(addSlotButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton deleteSlotButton = new OvalButton("<html><b><font size='3'>Delete Slot</font></b></html>");
        setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton parkCarButton = new OvalButton("<html><b><font size='3'>Park Car</font></b></html>");
        setButtonStyle(parkCarButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton findCarButton = new OvalButton(
                "<html><b><font size='3'>Find Car by Registration number</font></b></html>");
        setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton listSlotsButton = new OvalButton("<html><b><font size='3'>List Slots</font></b></html>");
        setButtonStyle(listSlotsButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton findCarsByMakeButton = new OvalButton("<html><b><font size='3'>Find Cars by Make</font></b></html>");
        setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton removeCarButton = new OvalButton("<html><b><font size='3'>Remove Car</font></b></html>");
        setButtonStyle(removeCarButton, Color.LIGHT_GRAY, Color.WHITE);

        OvalButton exitButton = new OvalButton("<html><b><font size='3'>Exit</font></b></html>");
        setButtonStyle(exitButton, Color.LIGHT_GRAY, Color.WHITE);

        
     // Add buttons to the button panel
        buttonPanel.add(addSlotButton);
        buttonPanel.add(deleteSlotButton);
        buttonPanel.add(parkCarButton);
        buttonPanel.add(findCarButton);
        buttonPanel.add(listSlotsButton);
        buttonPanel.add(findCarsByMakeButton);
        buttonPanel.add(removeCarButton);
        buttonPanel.add(exitButton);

        f.setVisible(true);
        f.setLayout(new GridLayout(5, 1));

     // Add action listener for the "Add Slot" button
        addSlotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            	// Disable other buttons
                addSlotButton.setEnabled(false);
                deleteSlotButton.setEnabled(false);
                parkCarButton.setEnabled(false);
                findCarButton.setEnabled(false);
                listSlotsButton.setEnabled(false);
                findCarsByMakeButton.setEnabled(false);
                removeCarButton.setEnabled(false);
                exitButton.setEnabled(false);

             // Change the style of the "Add Slot" button
                setButtonStyle(addSlotButton, Color.DARK_GRAY, Color.WHITE);

             // Create and configure UI elements for adding a slot
                JLabel addSlotLabel = new JLabel("Please enter a Slot ID (e.g. A001):");
                JTextField addSlotTextField = new JTextField(10);

                JButton addSlotBtn = new JButton("Add a Slot");

                JPanel radioButtonPanel = new JPanel();
                JPanel ButtonPanel = new JPanel();

                ButtonGroup radioButtonGroup = new ButtonGroup();

                JRadioButton staffRadioButton = new JRadioButton("Slot for Staffs");
                JRadioButton visitorsRadioButton = new JRadioButton("Slot for Visitors");

                JButton cancelButton1 = new JButton("Cancel");

                radioButtonGroup.add(staffRadioButton);
                radioButtonGroup.add(visitorsRadioButton);

                radioButtonPanel.setBackground(Color.LIGHT_GRAY);

             // Add UI elements to the input panel
                inputPanel.add(addSlotLabel);
                inputPanel.add(addSlotTextField);
                radioButtonPanel.add(staffRadioButton);
                radioButtonPanel.add(visitorsRadioButton);

                inputPanel.add(radioButtonPanel);
                ButtonPanel.add(addSlotBtn);
                ButtonPanel.add(cancelButton1);
                inputPanel.revalidate();
                inputPanel.repaint();

                inputPanel.add(ButtonPanel);
                ButtonPanel.setBackground(Color.LIGHT_GRAY);

                addSlotButton.setEnabled(false);

             // Add action listener for the "Cancel" button
                cancelButton1.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    	 // Enable other buttons
                        addSlotButton.setEnabled(true);
                        deleteSlotButton.setEnabled(true);
                        parkCarButton.setEnabled(true);
                        findCarButton.setEnabled(true);
                        listSlotsButton.setEnabled(true);
                        findCarsByMakeButton.setEnabled(true);
                        removeCarButton.setEnabled(true);
                        exitButton.setEnabled(true);

                        // Hide UI elements for adding a slot
                        addSlotLabel.setVisible(false);
                        addSlotTextField.setVisible(false);
                        addSlotBtn.setVisible(false);
                        staffRadioButton.setVisible(false);
                        visitorsRadioButton.setVisible(false);
                        cancelButton1.setVisible(false);

                        // Reset the style of the "Add Slot" button
                        setButtonStyle(addSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                    }
                });

             // Add action listener for the "Add Slot" button in the dialog
                addSlotBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                    	// Create a dialog for displaying the result
                        JDialog addSlotDialog = new JDialog(f, "Message", true);
                        addSlotDialog.setSize(500, 100);

                        addSlotDialog.setResizable(false);

                        int frameX = f.getLocation().x;
                        int frameY = f.getLocation().y;
                        int frameWidth = f.getWidth();
                        int frameHeight = f.getHeight();
                        int dialogWidth = addSlotDialog.getWidth();
                        int dialogHeight = addSlotDialog.getHeight();

                        int x = frameX + (frameWidth - dialogWidth) / 2;
                        int y = frameY + (frameHeight - dialogHeight) / 2;

                        addSlotDialog.setLocation(x, y);

                     // Get the slot ID and selected radio button
                        String slotId = addSlotTextField.getText();
                        String s = "";

                        if (staffRadioButton.isSelected() || visitorsRadioButton.isSelected()) {
                            if (slotId.isBlank()) {
                                s = "Please enter slot Id. ";
                            } else {
                                s = parkingSlot.addParkingCarSlot(slotId);
                            }
                        } else {
                            s = "Please select slot type(staff/visitor)";
                        }
                     // Create a label with the result message
                        JLabel l = new JLabel(s);
                        addSlotDialog.add(l, BorderLayout.CENTER);

                        if (s.equals("The Slot is added successfully.")) {
                            
                        	// Create a button to represent the new slot

                            JButton slot = new JButton(addSlotTextField.getText() + " is available ");
                            setButtonStyle2(slot, Color.green, Color.black);
                            slot.setActionCommand(addSlotTextField.getText());

                            // Create a white border with increased thickness
                            Border customBorder = BorderFactory.createCompoundBorder(new LineBorder(Color.white, 2), // White
                                    // border
                                    // with
                                    // thickness
                                    // of
                                    // 2
                                    // pixels
                                    new EmptyBorder(10, 10, 10, 10) // Padding
                                    );
                            slot.setBorder(customBorder);

                            // Set the button's background (fill) color to green
                            slot.setBackground(Color.green);

                            // Make sure the text is fully visible by setting the foreground (text) color to
                            // black
                            slot.setForeground(Color.black);

                            if (staffRadioButton.isSelected()) {
                                staffSlotsPanel.add(slot);
                                staffSlotsPanel.revalidate();
                                staffSlotsPanel.repaint();
                            } else if (visitorsRadioButton.isSelected()) {
                                visitorSlotsPanel.add(slot);
                                visitorSlotsPanel.revalidate();
                                visitorSlotsPanel.repaint();
                            }
                            
                           
                        

                         // Create an action listener for a JButton named "slot."
                            slot.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent e) 
                                {
                                	
                                	// Get the label of the button that triggered the event
                                	
                                	String buttonLabel = slot.getText();
                                	
                                	// Print debug information about the slot and button label
                                	System.out.println("******* slot id*********"+slotId);
                                	System.out.println("******* buttonLabel********"+slot.getText());
                                	
                                	// Check if the button label contains "available"
                                	if (buttonLabel.contains("available")) {
                                	
                                	
                                	System.out.println("*******inside slot available*********");
                                	
                                	// If it contains "available," create a new JDialog for slot options
                                    JDialog slotsOptions = new JDialog(f, "Slot Options", true);
                                 // Set size, location, and layout for the dialog
                                    slotsOptions.setSize(400, 200);
                                    slotsOptions.setResizable(false);

                                 // Calculate the position for centering the dialog
                                    int frameX = f.getLocation().x;
                                    int frameY = f.getLocation().y;
                                    int frameWidth = f.getWidth();
                                    int frameHeight = f.getHeight();
                                    int dialogWidth = slotsOptions.getWidth();
                                    int dialogHeight = slotsOptions.getHeight();

                                    int x = frameX + (frameWidth - dialogWidth) / 2;
                                    int y = frameY + (frameHeight - dialogHeight) / 2;

                                    slotsOptions.setLocation(x, y);

                                 // Create panels for labels and buttons
                                    JPanel slotsOptionsLabelPanel = new JPanel();
                                    JPanel slotsOptionsButtonPanel = new JPanel();

                                    slotsOptionsLabelPanel.setBackground(Color.LIGHT_GRAY);
                                    slotsOptionsLabelPanel.setLayout(new BorderLayout());

                                    // Increase the preferred size of the label panel
                                    slotsOptionsLabelPanel.setPreferredSize(new Dimension(500, 100)); // Adjust the size
                                    // as needed

                                    JLabel slotOptionLabel = new JLabel("What would you like to do ?");
                                    slotOptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                    slotsOptionsLabelPanel.add(slotOptionLabel, BorderLayout.CENTER);

                                    slotsOptionsButtonPanel.setBackground(Color.DARK_GRAY);

                                 // Create buttons for different slot options
                                    JButton removeSlot = new JButton("Remove Slot");
                                    removeSlot.setPreferredSize(new Dimension(100, 40));
                                    slotsOptionsButtonPanel.add(removeSlot);

                                    
                                    JButton parkCarOptionButton = new JButton("Park Car");
                                    parkCarOptionButton.setPreferredSize(new Dimension(100, 40));
                                    slotsOptionsButtonPanel.add(parkCarOptionButton);

                                    JButton cancelOption = new JButton("Cancel");
                                    cancelOption.setPreferredSize(new Dimension(100, 40));
                                 // Add buttons to the button panel
                                    slotsOptionsButtonPanel.add(cancelOption);

                                    // Set the layout for the dialog's content pane
                                    slotsOptions.getContentPane().setLayout(new BorderLayout());
                                    slotsOptions.getContentPane().add(slotsOptionsLabelPanel, BorderLayout.CENTER);
                                    slotsOptions.getContentPane().add(slotsOptionsButtonPanel, BorderLayout.SOUTH);

                                    
                                 // Add action listeners for "Remove Slot" buttons
                                    removeSlot.addActionListener(new ActionListener() {

                                        public void actionPerformed(ActionEvent e) {
                                        	// Hide  components
                                            slotOptionLabel.setVisible(false);
                                            removeSlot.setVisible(false);
                                            parkCarOptionButton.setVisible(false);
                                            cancelOption.setVisible(false);

                                            // Disable  buttons
                                            addSlotButton.setEnabled(false);
                                            deleteSlotButton.setEnabled(false);
                                            parkCarButton.setEnabled(false);
                                            findCarButton.setEnabled(false);
                                            listSlotsButton.setEnabled(false);
                                            findCarsByMakeButton.setEnabled(false);
                                            removeCarButton.setEnabled(false);
                                            exitButton.setEnabled(false);

                                            // Create a confirmation label
                                            JLabel deleteSlotLabel = new JLabel("Are you sure you want to delete?");
                                            deleteSlotLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                            slotsOptionsLabelPanel.add(deleteSlotLabel, BorderLayout.CENTER);

                                            slotsOptionsButtonPanel.setBackground(Color.DARK_GRAY);

                                         // Create "Remove Slot" and "Cancel" buttons
                                            JButton deleteSlotBtn = new JButton("Remove Slot");
                                            deleteSlotBtn.setPreferredSize(new Dimension(100, 40));
                                            slotsOptionsButtonPanel.add(deleteSlotBtn);

                                            JButton cancelButton = new JButton("Cancel");
                                            cancelButton.setPreferredSize(new Dimension(100, 40));
                                            slotsOptionsButtonPanel.add(cancelButton);

                                         // Set up the layout of the slotsOptions dialog
                                            slotsOptions.getContentPane().setLayout(new BorderLayout());
                                            slotsOptions.getContentPane().add(slotsOptionsLabelPanel,
                                                    BorderLayout.CENTER);
                                            slotsOptions.getContentPane().add(slotsOptionsButtonPanel,
                                                    BorderLayout.SOUTH);

                                         // ActionListener for the "Cancel" button
                                            cancelButton.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {

                                                	// Re-enable various buttons and hide components
                                                    addSlotButton.setEnabled(true);
                                                    deleteSlotButton.setEnabled(true);
                                                    parkCarButton.setEnabled(true);
                                                    findCarButton.setEnabled(true);
                                                    listSlotsButton.setEnabled(true);
                                                    findCarsByMakeButton.setEnabled(true);
                                                    removeCarButton.setEnabled(true);
                                                    exitButton.setEnabled(true);
                                                    slotOptionLabel.setVisible(false);
                                                    deleteSlotLabel.setVisible(false);
                                                    cancelButton.setVisible(false);
                                                    deleteSlotBtn.setVisible(false);
                                                    cancelButton.setVisible(false);
                                                    cancelButton.setVisible(false);

                                                 // Close the slotsOptions dialog
                                                    slotsOptions.setVisible(false);
                                                 // Set the style of the deleteSlotButton
                                                    setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                                                }
                                            });

                                         // ActionListener for the "Remove Slot" button
                                            deleteSlotBtn.addActionListener(new ActionListener() {
                                                public void actionPerformed(ActionEvent e) {

                                                	// Hide the "Cancel" button and close the slotsOptions dialog
                                                    cancelButton.setVisible(false);
                                                    slotsOptions.dispose();
                                                    slotOptionLabel.setVisible(false);
                                                    
                                                 // Create a new dialog for displaying a message
                                                    JDialog deleteSlotDialog = new JDialog(f, "Message", true);
                                                    deleteSlotDialog.setSize(400, 200);

                                                    deleteSlotDialog.setResizable(false);

                                                 // Calculate the position of the new dialog
                                                    int frameX = f.getLocation().x;
                                                    int frameY = f.getLocation().y;
                                                    int frameWidth = f.getWidth();
                                                    int frameHeight = f.getHeight();
                                                    int dialogWidth = deleteSlotDialog.getWidth();
                                                    int dialogHeight = deleteSlotDialog.getHeight();

                                                    int x = frameX + (frameWidth - dialogWidth) / 2;
                                                    int y = frameY + (frameHeight - dialogHeight) / 2;

                                                    deleteSlotDialog.setLocation(x, y);

                                                    String slotId1 = slotId;

                                                    String s = "";

                                                    String removeCarSlot = slotId1;
                                                    String returnValue = "";

                                                 // Access the carPark hashMap and delete a car slot
                                                    HashMap<String, ArrayList<Car>> hashmap2 = carPark.hashMap;
                                                    returnValue = parkingSlot.deleteCarSlot(removeCarSlot, hashmap2);

                                                 // Create a label with the result of the operation
                                                    JLabel resultLabel = new JLabel(returnValue);

                                                    deleteSlotDialog.add(resultLabel, BorderLayout.CENTER);

                                                    if (returnValue.equals("The Slot is deleted successfully.")) {

                                                    	 // If the slot is deleted successfully, remove the corresponding button
                                                        String targetName = removeCarSlot; // Use the slot ID
                                                        Component[] components = staffSlotsPanel.getComponents();

                                                     // Iterate through staffSlotsPanel components
                                                        for (Component component : components) {
                                                            if (component instanceof JButton) {
                                                            JButton button = (JButton) component;
                                                            if (targetName.equals(button.getActionCommand())) {
                                                                staffSlotsPanel.remove(button);
                                                                f.validate();
                                                                f.repaint();
                                                                break;
                                                            }
                                                        }
                                                    }
                                                 // Iterate through visitorSlotsPanel components
                                                    Component[] component2 = visitorSlotsPanel.getComponents();

                                                    for (Component component : component2) {
                                                        if (component instanceof JButton) {
                                                            JButton button = (JButton) component;
                                                            if (targetName.equals(button.getActionCommand())) {
                                                                visitorSlotsPanel.remove(button);
                                                                f.validate();
                                                                f.repaint();
                                                                break;
                                                            }
                                                        }
                                                    }

                                                }
                                             // Create a JButton with the label "Cancel"
                                                JButton cancelButton = new JButton("Cancel");
                                             // Add an ActionListener to the cancel button
                                                cancelButton.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                    	 // Enable the deleteSlotButton
                                                        deleteSlotButton.setEnabled(true);
                                                     // Close the deleteSlotDialog
                                                        deleteSlotDialog.dispose();
                                                    }
                                                });

                                             // Add the cancel button to the deleteSlotDialog in the SOUTH position
                                                deleteSlotDialog.add(cancelButton, BorderLayout.SOUTH);
                                             // Make the deleteSlotDialog visible
                                                deleteSlotDialog.setVisible(true);
                                             // Hide the deleteSlotLabel
                                                deleteSlotLabel.setVisible(false);
                                             // Hide the deleteSlotBtn
                                                deleteSlotBtn.setVisible(false);
                                             // Hide the cancelButton
                                                cancelButton.setVisible(false);
                                                
                                             // Add an ActionListener to the deleteSlotBtn

                                                deleteSlotBtn.addActionListener(new ActionListener() {
                                                    public void actionPerformed(ActionEvent e) {
                                                    	// Create a new JDialog for adding a slot with a title and modality
                                                        JDialog addSlotDialog = new JDialog(f, "Message", true);
                                                     // Set the size of the addSlotDialog
                                                        addSlotDialog.setSize(400, 200);
                                                     // Set the close operation of the addSlotDialog
                                                        addSlotDialog.setDefaultCloseOperation(
                                                                WindowConstants.DO_NOTHING_ON_CLOSE); 

                                                        
                                                     // Make the addSlotDialog visible
                                                        addSlotDialog.setVisible(true);
                                                    }

                                                });
                                             // Enable various buttons
                                                addSlotButton.setEnabled(true);
                                                deleteSlotButton.setEnabled(true);
                                                parkCarButton.setEnabled(true);
                                                findCarButton.setEnabled(true);
                                                listSlotsButton.setEnabled(true);
                                                findCarsByMakeButton.setEnabled(true);
                                                removeCarButton.setEnabled(true);
                                                exitButton.setEnabled(true);
                                             // Hide the cancelButton
                                                cancelButton.setVisible(false);
                                             // Apply a style to the deleteSlotButton
                                                setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                                            }
                                        });

                                    }

                                });

                             // Add an ActionListener to the park car button
                                parkCarOptionButton.addActionListener(new ActionListener() {

                                    public void actionPerformed(ActionEvent e) {

                                        // Hide various buttons
                                        slotsOptions.setVisible(false);
                                        addSlotButton.setEnabled(false);
                                        deleteSlotButton.setEnabled(false);
                                        parkCarButton.setEnabled(false);
                                        findCarButton.setEnabled(false);
                                        listSlotsButton.setEnabled(false);
                                        findCarsByMakeButton.setEnabled(false);
                                        removeCarButton.setEnabled(false);
                                        exitButton.setEnabled(false);
                                        // Change the style of the parkCarButton
                                        setButtonStyle(parkCarButton, Color.DARK_GRAY, Color.WHITE);
                                        
                                        // Initialize slotIdTextFieldOptions with the value of slotId

                                        String slotIdTextFieldOptions = slotId;

                                     // Create JLabels and JTextFields for car registration, make, model, and year
                                        JLabel carRegLLabelOptions = new JLabel(
                                                "Please enter the car registration number (e.g., D1234) : ");
                                        JTextField carRegTextFieldOptions = new JTextField(10);

                                        JLabel carMakeLLabelOptions = new JLabel(
                                                "Please enter the car's make (e.g., Toyota) :");
                                        JTextField carMakeTextFieldOptions = new JTextField(10);

                                        JLabel carModelLabelOptions = new JLabel(
                                                "Please enter the car's model (e.g., Corolla) :");
                                        JTextField carModelTextFieldOptions = new JTextField(10);

                                        JLabel carYearLabelOptions = new JLabel(
                                                "Please enter the car's year (e.g., 2009) : ");
                                        JTextField carYearTextFieldOptions = new JTextField(10);

                                        // Create a "Park Car" button
                                        JButton carParkButtonOptions = new JButton("Park Car");

                                     // Add the created components to the inputPanel
                                        inputPanel.add(carRegLLabelOptions);
                                        inputPanel.add(carRegTextFieldOptions);
                                        inputPanel.add(carMakeLLabelOptions);
                                        inputPanel.add(carMakeTextFieldOptions);
                                        inputPanel.add(carModelLabelOptions);
                                        inputPanel.add(carModelTextFieldOptions);
                                        inputPanel.add(carYearLabelOptions);
                                        inputPanel.add(carYearTextFieldOptions);
                                        inputPanel.add(carParkButtonOptions, BorderLayout.SOUTH);
                                     // Revalidate and repaint the inputPanel to update the UI
                                        inputPanel.revalidate();
                                        inputPanel.repaint();

                                     // Create a "Cancel" button
                                        JButton cancelButton = new JButton("Cancel");
                                     // Add the cancel button to the inputPanel
                                        inputPanel.add(cancelButton);

                                     // Add an ActionListener to the cancel button
                                        cancelButton.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {

                                            	// Hide UI components
                                                carRegLLabelOptions.setVisible(false);
                                                carRegTextFieldOptions.setVisible(false);
                                                carMakeLLabelOptions.setVisible(false);
                                                carMakeTextFieldOptions.setVisible(false);
                                                carModelLabelOptions.setVisible(false);
                                                carModelTextFieldOptions.setVisible(false);
                                                carYearLabelOptions.setVisible(false);
                                                carYearTextFieldOptions.setVisible(false);
                                                carParkButtonOptions.setVisible(false);
                                                cancelButton.setVisible(false);

                                             // Enable  buttons
                                                addSlotButton.setEnabled(true);
                                                deleteSlotButton.setEnabled(true);
                                                parkCarButton.setEnabled(true);
                                                findCarButton.setEnabled(true);
                                                listSlotsButton.setEnabled(true);
                                                findCarsByMakeButton.setEnabled(true);
                                                removeCarButton.setEnabled(true);
                                                exitButton.setEnabled(true);
                                                
                                             // Change the style of parkCarButton and deleteSlotButton

                                                setButtonStyle(parkCarButton, Color.LIGHT_GRAY, Color.WHITE);

                                                setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                                            }
                                        });

                                        // Add an ActionListener to the carParkButtonOptions
                                        carParkButtonOptions.addActionListener(new ActionListener() {
                                            public void actionPerformed(ActionEvent e) {
                                            	
                                            	// Retrieve values from the input fields
                                                String slot_Id = slotId;
                                                String car_Reg = carRegTextFieldOptions.getText();
                                                String car_Make = carMakeTextFieldOptions.getText();
                                                String car_Model = carModelTextFieldOptions.getText();
                                                String carYear1 = carYearTextFieldOptions.getText();
                                                String message = "";
                                                
                                             // Validate the input data

                                                if (slot_Id.isBlank()) {
                                                    message = "Enter Slot Id";

                                                } else if (car_Reg.isBlank()) {
                                                    message = "Enter Car Registration Number";
                                                } else if (car_Make.isBlank()) {
                                                    message = "Enter Car Make";
                                                } else if (car_Model.isBlank()) {
                                                    message = "Enter Car Model";
                                                }

                                                else if (carYear1.isBlank()) {
                                                    message = "Enter Car Year";
                                                }

                                                else if (!isValidYearInput(carYear1)) {

                                                    message = "Invalid input. Please enter a valid year .";

                                                }

                                                else {

                                                    Integer car_Year1 = Integer.valueOf(carYear1);

                                                    int car_Year = car_Year1.intValue();

                                                    message = "";

                                                    if (staffRadioButton.isSelected()) {
                                                    	// Check if the slot is already occupied

                                                        if (carPark.hashMap.containsKey(slot_Id)) {

                                                            message = " Slot " + slot_Id
                                                                    + " already occupied. Please select a different slot for parking ";

                                                        } else {
                                                            boolean containsBob = parkingSlot.slots
                                                                    .contains(slot_Id);
                                                            if (containsBob) {

                                                                String pattern = "^[A-Z]\\d{4}$";
                                                                boolean found = false;
                                                                ArrayList<Car> car1 = carPark.cars;

                                                                for (Car car : car1) {
                                                                    if (car.getCarReg().equals(car_Reg)) {
                                                                        found = true;

                                                                    }
                                                                }
                                                                if (found == false) {
                                                                    if (Pattern.matches(pattern, car_Reg)) {

                                                                        if (car_Make.isBlank()) {

                                                                            message = "Please enter correct car's make";

                                                                        } else {

                                                                            if (car_Model.isBlank()) {

                                                                                message = "Please enter the car's model";

                                                                            }

                                                                            else {

                                                                                if (car_Year <= 2023) {

                                                                                	 // Get the current date and time
                                                                                    LocalDateTime currentDateTime = LocalDateTime
                                                                                            .now();
                                                                                    DateTimeFormatter outputFormatter = DateTimeFormatter
                                                                                            .ofPattern(
                                                                                                    "yyyy-MM-dd HH:mm:ss");
                                                                                    String formattedDateTime = currentDateTime
                                                                                            .format(outputFormatter);

                                                                                    // Find the targetName
                                                                                    String targetName = slot_Id;
                                                                                    Component[] components = staffSlotsPanel
                                                                                            .getComponents();

                                                                                    for (Component component : components) {

                                                                                        if (component instanceof JButton) {
                                                                                            JButton button = (JButton) component;
                                                                                            if (targetName.equals(
                                                                                                    button.getActionCommand())) {

                                                                                            	// Add the car to the parking
                                                                                                carPark.addCar(
                                                                                                        slot_Id,
                                                                                                        car_Reg,
                                                                                                        car_Make,
                                                                                                        car_Model,
                                                                                                        car_Year,
                                                                                                        formattedDateTime);

                                                                                                message = "Car added successfully";
                                                                                                f.validate();
                                                                                                f.repaint();
                                                                                                break;
                                                                                            } else {

                                                                                                message = "You have selected staff parking slot. Please select visitor's slot for parking";
                                                                                            }
                                                                                        } else {

                                                                                            message = "You have selected staff parking slot. Please select visitor's slot for parking";
                                                                                        }
                                                                                    }

                                                                                } else {

                                                                                    message = "Invalid input. Please enter a valid year.";
                                                                                    System.out.println(
                                                                                            "*************15********************");
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {

                                                                        message = car_Reg
                                                                                + " Car Registration number does not match the pattern. Please enter it correctly.";
                                                                        System.out.println(
                                                                                "*************16********************");
                                                                    }
                                                                } else {

                                                                    message = "Car with registration number "
                                                                            + car_Reg
                                                                            + " is already parked in the parking.";

                                                                }
                                                            } else {

                                                                message = "Slot " + slot_Id + " is not available";

                                                            }
                                                        }

                                                    }
                                                 // Handle visitor parking option (missing code)
                                                    else if (visitorsRadioButton.isSelected()) {
                                                    	// Check if the "Visitors" radio button is selected

                                                        if (carPark.hashMap.containsKey(slot_Id)) {
                                                        	// Check if the parking slot with the given slot_Id is already occupied


                                                            message = " Slot " + slot_Id
                                                                    + " already occupied. Please select a different slot for parking ";
                                                        } else {
                                                        	// If the parking slot is not occupied
                                                            boolean containsBob = parkingSlot.slots
                                                                    .contains(slot_Id);
                                                         // Check if the parking slot is available in the "parkingSlot" object
                                                            if (containsBob) {
                                                            	// If the slot is found in the parkingSlot object
                                                                String pattern = "^[A-Z]\\d{4}$";
                                                             // Define a regular expression pattern to match car registration numbers
                                                                boolean found = false;
                                                                ArrayList<Car> car1 = carPark.cars;
                                                             // Create an ArrayList of Car objects from the carPark object

                                                                for (Car car : car1) {
                                                                	 // Loop through the cars in the ArrayList
                                                                    if (car.getCarReg().equals(car_Reg)) {
                                                                    	// Check if the current car's registration matches the input car_Reg
                                                                        found = true;
                                                                    }
                                                                }
                                                                if (found == false) {
                                                                	// If the car with the given registration is not found in the ArrayList
                                                                    if (Pattern.matches(pattern, car_Reg)) {
                                                                    	// Check if the car registration matches the defined pattern

                                                                        if (car_Make.isBlank()) {

                                                                            message = "Please enter correct car's make";
                                                                        } else {

                                                                            if (car_Model.isBlank()) {

                                                                                message = "Please enter the car's model";
                                                                            }

                                                                            else {

                                                                                if (car_Year <= 2023) {
                                                                                	// Check if the car's year is less than or equal to 2023

                                                                                    LocalDateTime currentDateTime = LocalDateTime
                                                                                            .now();
                                                                                    // Get the current date and time
                                                                                    DateTimeFormatter outputFormatter = DateTimeFormatter
                                                                                            .ofPattern(
                                                                                                    "yyyy-MM-dd HH:mm:ss");
                                                                                    // Format the current date and time
                                                                                    String formattedDateTime = currentDateTime
                                                                                            .format(outputFormatter);

                                                                                    String targetName = slot_Id;
                                                                                 // Get the components within the "visitorSlotsPanel"
                                                                                    Component[] components = visitorSlotsPanel
                                                                                            .getComponents();
                                                                                    

                                                                                    for (Component component : components) {
                                                                                        if (component instanceof JButton) {
                                                                                            JButton button = (JButton) component;
                                                                                            if (targetName.equals(
                                                                                                    button.getActionCommand())) {
                                                                                            	// Check if the button corresponds to the selected parking slot
                                                                                                carPark.addCar(
                                                                                                        slot_Id,
                                                                                                        car_Reg,
                                                                                                        car_Make,
                                                                                                        car_Model,
                                                                                                        car_Year,
                                                                                                        formattedDateTime);
                                                                                             // Add the car to the carPark
                                                                                                message = "Car added successfully";
                                                                                                f.validate();
                                                                                                f.repaint();
                                                                                                break;
                                                                                            } else {
                                                                                                message = "You have selected visitor's parking slot. Please select staff's slot for parking";
                                                                                            }
                                                                                        } else {
                                                                                            message = "You have selected visitor's parking slot. Please select staff's slot for parking";
                                                                                        }

                                                                                    }

                                                                                } else {

                                                                                    message = "Invalid input. Please enter a valid year.";
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {

                                                                        message = car_Reg
                                                                                + " Car Registration number does not match the pattern. Please enter it correctly.";
                                                                    }
                                                                } else {

                                                                    message = "Car with registration number "
                                                                            + car_Reg
                                                                            + " is already parked in the parking.";
                                                                }
                                                            } else {

                                                                message = "Slot " + slot_Id + " is not available";
                                                            }
                                                        }

                                                    } else {
                                                        message = "Select Slot type";

                                                    }
                                                }
                                             // Check if either the "Staff" or "Visitors" radio button is selected
                                                if (staffRadioButton.isSelected()
                                                        || visitorsRadioButton.isSelected()) {

                                                	// Check if the message is "Car added successfully"
                                                    if (message.equals("Car added successfully")) {
                                                        String targetName = slot_Id;
                                                        // Get the components within the "staffSlotsPanel" and "visitorSlotsPanel"
                                                        Component[] components1 = staffSlotsPanel.getComponents();
                                                        Component[] components2 = visitorSlotsPanel.getComponents();

                                                        
                                                        for (Component component : components1) {
                                                            if (component instanceof JButton) {
                                                                JButton button = (JButton) component;
                                                             // Check if the button corresponds to the selected parking slot

                                                                if (targetName.equals(button.getActionCommand())) {

                                                                    button.setText(slot_Id + " is Occupied");
                                                                    
                                                                 // Change the button's text and style to indicate it's occupied
                                                                    setButtonStyle2(button, Color.red, Color.black);

                                                                    Border customBorder = BorderFactory
                                                                            .createCompoundBorder(
                                                                                    new LineBorder(Color.white, 2), // White
                                                                                    // border
                                                                                    // with
                                                                                    // thickness
                                                                                    // of
                                                                                    // 2
                                                                                    // pixels
                                                                                    new EmptyBorder(10, 10, 10, 10) // Padding
                                                                                    );
                                                                    button.setBorder(customBorder);

                                                                    button.setBackground(Color.red);

                                                                 // Customize the button's border and appearance
                                                                    button.setForeground(Color.black);

                                                              
                                                              
                                                                    f.validate();
                                                                    f.repaint();
                                                                    
                                                                    
                                                                    
                                                                    
                                                                    break;
                                                                }
                                                            }
                                                            
                                                        }
                                                        for (Component component : components2) {
                                                            if (component instanceof JButton) {
                                                                JButton button = (JButton) component;
                                                                // Check if the button corresponds to the selected parking slot
                                                                if (targetName.equals(button.getActionCommand())) {

                                                                	// Change the button's text and style to indicate it's occupied
                                                                    button.setText(slot_Id + " is Occupied");
                                                                    setButtonStyle2(button, Color.red, Color.black);

                                                                    Border customBorder = BorderFactory
                                                                            .createCompoundBorder(
                                                                                    new LineBorder(Color.white, 2), // White
                                                                                    // border
                                                                                    // with
                                                                                    // thickness
                                                                                    // of
                                                                                    // 2
                                                                                    // pixels
                                                                                    new EmptyBorder(10, 10, 10, 10) // Padding
                                                                                    );
                                                                    button.setBorder(customBorder);

                                                                    button.setBackground(Color.red);

                                                                    button.setForeground(Color.black);

                                                                    // Customize the button's border and appearance

                                                                    f.validate();
                                                                    f.repaint();
                                                                    break;
                                                                }
                                                            }

                                                        }

                                                    }
                                                }

                                                else {
                                                    message = "Please select correct slot for parking";

                                                }

                                                JDialog carParkDialog = new JDialog(f, "Message", true);
                                                carParkDialog.setSize(500, 300);
                                             // Create a dialog window for displaying a message

                                                carParkDialog.setResizable(false);

                                                int frameX = f.getLocation().x;
                                                int frameY = f.getLocation().y;
                                                int frameWidth = f.getWidth();
                                                int frameHeight = f.getHeight();
                                                int dialogWidth = carParkDialog.getWidth();
                                                int dialogHeight = carParkDialog.getHeight();
                                                
                                             // Calculate the position for the dialog window to appear in the center of the main frame

                                                int x = frameX + (frameWidth - dialogWidth) / 2;
                                                int y = frameY + (frameHeight - dialogHeight) / 2;

                                                carParkDialog.setLocation(x, y);

                                                JLabel carParkMessage = new JLabel(message);

                                                carParkDialog.add(carParkMessage, BorderLayout.CENTER);

                                             // Create and display the dialog with the message
                                                carParkDialog.setVisible(true);

                                             // Hide or disable various GUI components
                                                carRegLLabelOptions.setVisible(false);
                                                carRegTextFieldOptions.setVisible(false);
                                                carMakeLLabelOptions.setVisible(false);
                                                carMakeTextFieldOptions.setVisible(false);
                                                carModelLabelOptions.setVisible(false);
                                                carModelTextFieldOptions.setVisible(false);
                                                carYearLabelOptions.setVisible(false);
                                                carYearTextFieldOptions.setVisible(false);
                                                carParkButtonOptions.setVisible(false);
                                                cancelButton.setVisible(false);

                                                staffRadioButton.setVisible(false);
                                                visitorsRadioButton.setVisible(false);

                                             // Enable other GUI buttons and reset styles
                                                addSlotButton.setEnabled(true);
                                                deleteSlotButton.setEnabled(true);
                                                parkCarButton.setEnabled(true);
                                                findCarButton.setEnabled(true);
                                                listSlotsButton.setEnabled(true);
                                                findCarsByMakeButton.setEnabled(true);
                                                removeCarButton.setEnabled(true);
                                                exitButton.setEnabled(true);

                                                setButtonStyle(parkCarButton, Color.LIGHT_GRAY, Color.WHITE);
                                            }
                                        });

                                    }
                                });

                                // ActionListener for the "cancelOption" button
                                cancelOption.addActionListener(new ActionListener() {

                                    public void actionPerformed(ActionEvent e) {
                                    	 // Hide the "slotsOptions" dialog
                                        slotsOptions.setVisible(false);

                                    }

                                });
                             // Show the "slotsOptions" dialog
                                slotsOptions.setVisible(true);

                            }
                            	else
                            	{
                            		

                            		// Create a new dialog window for occupied slots
                            		JDialog slotsOptionsOccupied = new JDialog(f, "Slot Options", true);
                                	slotsOptionsOccupied.setSize(400, 200);
                                	slotsOptionsOccupied.setResizable(false);

                                	// Calculate the position for the dialog window to appear in the center of the main frame
                                	int frameX1 = f.getLocation().x;
                                    int frameY1 = f.getLocation().y;
                                    int frameWidth1 = f.getWidth();
                                    int frameHeight1 = f.getHeight();
                                    int dialogWidth1 = slotsOptionsOccupied.getWidth();
                                    int dialogHeight1 = slotsOptionsOccupied.getHeight();

                                    int x1 = frameX1 + (frameWidth1 - dialogWidth1) / 2;
                                    int y1 = frameY1 + (frameHeight1 - dialogHeight1) / 2;

                                    slotsOptionsOccupied.setLocation(x1, y1);

                                    JPanel slotsOptionsLabelPanel = new JPanel();
                                    JPanel slotsOptionsButtonPanel = new JPanel();

                                    slotsOptionsLabelPanel.setBackground(Color.LIGHT_GRAY);
                                    slotsOptionsLabelPanel.setLayout(new BorderLayout());

                                    // Increase the preferred size of the label panel
                                    slotsOptionsLabelPanel.setPreferredSize(new Dimension(500, 100)); 

                                    JLabel slotOptionLabel = new JLabel("What would you like to do ?");
                                    slotOptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                    slotsOptionsLabelPanel.add(slotOptionLabel, BorderLayout.CENTER);

                                    slotsOptionsButtonPanel.setBackground(Color.DARK_GRAY);

                                    // Create a "Remove Car" button
                                    JButton removecar = new JButton("Remove Car");
                                    removecar.setPreferredSize(new Dimension(100, 40));
                                    slotsOptionsButtonPanel.add(removecar);

                               
                                 // Create a "Cancel" button
                                    JButton cancelOption = new JButton("Cancel");
                                    cancelOption.setPreferredSize(new Dimension(100, 40));
                                    slotsOptionsButtonPanel.add(cancelOption);

                                    slotsOptionsOccupied.getContentPane().setLayout(new BorderLayout());
                                    slotsOptionsOccupied.getContentPane().add(slotsOptionsLabelPanel, BorderLayout.CENTER);
                                    slotsOptionsOccupied.getContentPane().add(slotsOptionsButtonPanel, BorderLayout.SOUTH);
                                    
                                    
                                    // ActionListener for the "removecar" button
                                    removecar.addActionListener(new ActionListener() {

                                        public void actionPerformed(ActionEvent e) {
                                        	
                                        	   // Disable GUI buttons
                                        	 addSlotButton.setEnabled(false);
                                             deleteSlotButton.setEnabled(false);
                                             parkCarButton.setEnabled(false);
                                             findCarButton.setEnabled(false);
                                             listSlotsButton.setEnabled(false);
                                             findCarsByMakeButton.setEnabled(false);
                                             removeCarButton.setEnabled(false);
                                             exitButton.setEnabled(false);
                                             addSlotButton.setEnabled(true);
                                             deleteSlotButton.setEnabled(true);
                                             parkCarButton.setEnabled(true);
                                             findCarButton.setEnabled(true);
                                             listSlotsButton.setEnabled(true);
                                             findCarsByMakeButton.setEnabled(true);
                                             removeCarButton.setEnabled(true);
                                             exitButton.setEnabled(true);

                                          // Close the "slotsOptionsOccupied" dialog
                                             slotsOptionsOccupied.dispose();
                                             
                                             

                                             String carSlot = slotId;
                                             String carReg= "";
                                             
                                            
                                             
                                             HashMap<String, ArrayList<Car>> getCarsBySlot = carPark.getCarsBySlot(carSlot);

                                          // Get a HashMap of cars parked in the selected slot
                                            for (Map.Entry<String, ArrayList<Car>> entry : getCarsBySlot.entrySet()) {
                                             
                                                 ArrayList<Car> valueList = entry.getValue();
                                                 for (Car value : valueList) {

                                                     if (value.getSlotId().equals(slotId)) {

                                                    	 carReg = value.getCarReg();
                                                    	// Retrieve the car registration number for the selected slot

                                                     }
                                                 }
                                             }

                                             String targetName = slotId;

                                             Component[] components1 = staffSlotsPanel.getComponents();
                                             Component[] components2 = visitorSlotsPanel.getComponents();

                                          // Call a method to remove the car from the carPark using its registration number
                                             String message = carPark.removeCarByReg(carReg);

                                        
                                          // Check if the car removal was successful
                                                 if (message.equalsIgnoreCase("Car removed successfully")) {

                                                     for (Component component : components1) {
                                                         if (component instanceof JButton) {
                                                             JButton button = (JButton) component;

                                                             if (targetName.equals(button.getActionCommand())) {

                                                                 button.setText(slotId + " is available");
                                                                 setButtonStyle2(button, Color.green, Color.black);
                                                                 button.setEnabled(true);
                                                                 staffParkingSlots.validate();
                                                                 staffParkingSlots.repaint();
                                                                 break;
                                                             }
                                                         }
                                                     }

                                                     for (Component component : components2) {
                                                         if (component instanceof JButton) {
                                                             JButton button = (JButton) component;
                                                             if (targetName.equals(button.getActionCommand())) {
                                                                 button.setText(slotId + " is available");
                                                                 setButtonStyle2(button, Color.green, Color.black);
                                                                 button.setEnabled(true);
                                                                 visitorSlotsPanel.validate();
                                                                 visitorSlotsPanel.repaint();
                                                                 f.validate();
                                                                 f.repaint();
                                                                 break;
                                                             }
                                                         }
                                                     }


                                        }

                                        }
                                    });
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                 // ActionListener for the "cancelOption button"
                                    cancelOption.addActionListener(new ActionListener() {

                                        public void actionPerformed(ActionEvent e) {
                                        	slotsOptionsOccupied.setVisible(false);

                                        }

                                    });
                                    slotsOptionsOccupied.setVisible(true);


                            	}
                        }

                        });
                    
                   
                    }

                    JButton cancelButton = new JButton("Cancel");

                    cancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);
                            setButtonStyle(addSlotButton, Color.LIGHT_GRAY, Color.WHITE);
                            addSlotDialog.dispose();
                        }
                    });

                    // Add action listener for the dialog box
                    addSlotDialog.addWindowListener(new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {

                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);

                            setButtonStyle(addSlotButton, Color.LIGHT_GRAY, Color.WHITE);
                            addSlotDialog.dispose();
                        }
                    });
                    addSlotDialog.add(cancelButton, BorderLayout.SOUTH);

                    addSlotDialog.setVisible(true);

                    addSlotLabel.setVisible(false);
                    addSlotTextField.setVisible(false);
                    addSlotBtn.setVisible(false);
                    staffRadioButton.setVisible(false);
                    visitorsRadioButton.setVisible(false);
                    cancelButton.setVisible(false);
                    cancelButton1.setVisible(false);

                    // Add action listener for the "Add Slot" button
                    addSlotBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JDialog addSlotDialog = new JDialog(f, "Message", true);
                            addSlotDialog.setSize(400, 200);

                            addSlotDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Move it

                            addSlotDialog.setVisible(true);
                            cancelButton.setVisible(false);
                            cancelButton1.setVisible(false);
                        }

                    });
                    addSlotButton.setEnabled(true);
                }
            });

        }
    });
    
    // Add action listener for the "delete Slot" button

    deleteSlotButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        	//disable buttons
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);

            setButtonStyle(deleteSlotButton, Color.DARK_GRAY, Color.WHITE);

            JLabel deleteSlotLabel = new JLabel("Please enter a Slot ID that you want to delete :");
            JTextField deleteSlotTextField = new JTextField(10);
            JButton cancelButton = new JButton("Cancel");

            JButton deleteSlotBtn = new JButton("Delete a Slot");
            inputPanel.add(deleteSlotBtn);

            inputPanel.add(deleteSlotLabel);
            inputPanel.add(deleteSlotTextField);
            inputPanel.add(deleteSlotBtn);
            inputPanel.add(cancelButton);
            inputPanel.revalidate();
            inputPanel.repaint();

            deleteSlotButton.setEnabled(false);

            // Add action listener for the "cancel" button
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	// enable buttons
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);

                    //hide buttons
                    deleteSlotLabel.setVisible(false);
                    deleteSlotTextField.setVisible(false);
                    deleteSlotBtn.setVisible(false);
                    cancelButton.setVisible(false);
                    cancelButton.setVisible(false);

                    setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });
            // Add action listener for the "delete Slot" button
            deleteSlotBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    cancelButton.setVisible(false);

                    JDialog deleteSlotDialog = new JDialog(f, "Message", true);
                    deleteSlotDialog.setSize(400, 200);

                    deleteSlotDialog.setResizable(false);

                    int frameX = f.getLocation().x;
                    int frameY = f.getLocation().y;
                    int frameWidth = f.getWidth();
                    int frameHeight = f.getHeight();
                    int dialogWidth = deleteSlotDialog.getWidth();
                    int dialogHeight = deleteSlotDialog.getHeight();

                    int x = frameX + (frameWidth - dialogWidth) / 2;
                    int y = frameY + (frameHeight - dialogHeight) / 2;

                    deleteSlotDialog.setLocation(x, y);

                    String slotId = deleteSlotTextField.getText();

                    String s = "";

                    String removeCarSlot = deleteSlotTextField.getText();
                    String returnValue = "";

                    HashMap<String, ArrayList<Car>> hashmap2 = carPark.hashMap;
                    returnValue = parkingSlot.deleteCarSlot(removeCarSlot, hashmap2);

                    JLabel resultLabel = new JLabel(returnValue);

                    deleteSlotDialog.add(resultLabel, BorderLayout.CENTER);

                    if (returnValue.equals("The Slot is deleted successfully.")) {

                        String targetName = removeCarSlot; // Use the slot ID
                        Component[] components = staffSlotsPanel.getComponents();

                        for (Component component : components) {
                            if (component instanceof JButton) {
                                JButton button = (JButton) component;
                                if (targetName.equals(button.getActionCommand())) {
                                    staffSlotsPanel.remove(button);
                                    f.validate();
                                    f.repaint();
                                    break;
                                }
                            }
                        }
                        Component[] component2 = visitorSlotsPanel.getComponents();

                        for (Component component : component2) {
                            if (component instanceof JButton) {
                                JButton button = (JButton) component;
                                if (targetName.equals(button.getActionCommand())) {
                                    visitorSlotsPanel.remove(button);
                                    f.validate();
                                    f.repaint();
                                    break;
                                }
                            }
                        }

                    }

                    JButton cancelButton = new JButton("Cancel");

                    // Add action listener for the "cancel Slot" button
                    cancelButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            deleteSlotButton.setEnabled(true);
                            deleteSlotDialog.dispose();
                        }
                    });

                    deleteSlotDialog.add(cancelButton, BorderLayout.SOUTH);
                    // addSlotDialog.pack(); // Ensure the dialog sizes correctly
                    deleteSlotDialog.setVisible(true);

                    deleteSlotLabel.setVisible(false);
                    deleteSlotTextField.setVisible(false);
                    deleteSlotBtn.setVisible(false);
                    cancelButton.setVisible(false);

                    // Add action listener for the "delete Slot" button
                    deleteSlotBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JDialog addSlotDialog = new JDialog(f, "Message", true);
                            addSlotDialog.setSize(400, 200);

                            addSlotDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Move it
                            // here

                            // Rest of your code for creating and configuring the dialog
                            // ...
                            // addSlotButton.setEnabled(true);
                            addSlotDialog.setVisible(true);
                        }

                    });
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    cancelButton.setVisible(false);
                    setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });

        }
    });

    // Add an ActionListener to the parkCar button
    parkCarButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	 // Disable various buttons
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);
            setButtonStyle(parkCarButton, Color.DARK_GRAY, Color.WHITE);
            
            // Create and set up components for input

            JLabel slotIdLLabel = new JLabel("Please enter the slot ID (e.g. A001) that you want to park at : ");
            JTextField slotIdTextField = new JTextField(10);

            JLabel carRegLLabel = new JLabel("Please enter the car registration number (e.g., D1234) : ");
            JTextField carRegTextField = new JTextField(10);

            JLabel carMakeLLabel = new JLabel("Please enter the car's make (e.g., Toyota) :");
            JTextField carMakeTextField = new JTextField(10);

            JLabel carModelLabel = new JLabel("Please enter the car's model (e.g., Corolla) :");
            JTextField carModelTextField = new JTextField(10);

            JLabel carYearLabel = new JLabel("Please enter the car's year (e.g., 2009) : ");
            JTextField carYearTextField = new JTextField(10);

            JButton carParkButton = new JButton("Park Car");

            JPanel radioButtonPanel = new JPanel();

            ButtonGroup radioButtonGroup = new ButtonGroup();

            JRadioButton staffRadioButton = new JRadioButton("Slot for Staffs");
            JRadioButton visitorsRadioButton = new JRadioButton("Slot for Visitors");

            radioButtonGroup.add(staffRadioButton);
            radioButtonGroup.add(visitorsRadioButton);

            radioButtonPanel.add(staffRadioButton);
            radioButtonPanel.add(visitorsRadioButton);

            inputPanel.add(radioButtonPanel);

            radioButtonPanel.setBackground(Color.LIGHT_GRAY);

            // Add components to inputPanel
            inputPanel.add(slotIdLLabel);
            inputPanel.add(slotIdTextField);
            inputPanel.add(carRegLLabel);
            inputPanel.add(carRegTextField);
            inputPanel.add(carMakeLLabel);
            inputPanel.add(carMakeTextField);
            inputPanel.add(carModelLabel);
            inputPanel.add(carModelTextField);
            inputPanel.add(carYearLabel);
            inputPanel.add(carYearTextField);
            inputPanel.add(carParkButton, BorderLayout.SOUTH);

            inputPanel.revalidate();
            inputPanel.repaint();

         // Create and set up a Cancel button
            JButton cancelButton = new JButton("Cancel");
            inputPanel.add(cancelButton);

            // Add an ActionListener to the Cancel button
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	// Hide various components
                 
                   
                    slotIdLLabel.setVisible(false);
                    slotIdTextField.setVisible(false);
                    carRegLLabel.setVisible(false);
                    carRegTextField.setVisible(false);
                    carMakeLLabel.setVisible(false);
                    carMakeTextField.setVisible(false);
                    carModelLabel.setVisible(false);
                    carModelTextField.setVisible(false);
                    carYearLabel.setVisible(false);
                    carYearTextField.setVisible(false);
                    carParkButton.setVisible(false);
                    cancelButton.setVisible(false);

                    staffRadioButton.setVisible(false);
                    visitorsRadioButton.setVisible(false);

                    // Enable buttons
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);

                    // Set button styles
                    setButtonStyle(parkCarButton, Color.LIGHT_GRAY, Color.WHITE);

                    setButtonStyle(deleteSlotButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });

            // Add an ActionListener to the Park Car button
            carParkButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	// Get input values
                    // Validate input and set message
                    // Perform car parking logic
                    // Show a message dialog with the result
                    String slot_Id = slotIdTextField.getText();
                    String car_Reg = carRegTextField.getText();
                    String car_Make = carMakeTextField.getText();
                    String car_Model = carModelTextField.getText();
                    String carYear1 = carYearTextField.getText();
                    String message = "";

                    if (slot_Id.isBlank()) {
                        message = "Enter Slot Id";

                    } else if (car_Reg.isBlank()) {
                        message = "Enter Car Registration Number";
                    } else if (car_Make.isBlank()) {
                        message = "Enter Car Make";
                    } else if (car_Model.isBlank()) {
                        message = "Enter Car Model";
                    }

                    else if (carYear1.isBlank()) {
                        message = "Enter Car Year";
                    }

                    else if (!isValidYearInput(carYear1)) {

                        message = "Invalid input. Please enter a valid year .";

                    }

                    else {

                        Integer car_Year1 = Integer.valueOf(carYear1);

                        int car_Year = car_Year1.intValue();

                        message = "";

                        if (staffRadioButton.isSelected()) {

                            if (carPark.hashMap.containsKey(slot_Id)) {

                                message = " Slot " + slot_Id
                                        + " already occupied. Please select a different slot for parking ";

                            } else {
                                boolean containsBob = parkingSlot.slots.contains(slot_Id);
                                if (containsBob) {

                                    String pattern = "^[A-Z]\\d{4}$";
                                    boolean found = false;
                                    ArrayList<Car> car1 = carPark.cars;

                                    for (Car car : car1) {
                                        if (car.getCarReg().equals(car_Reg)) {
                                            found = true;

                                        }
                                    }
                                    if (found == false) {
                                        if (Pattern.matches(pattern, car_Reg)) {

                                            if (car_Make.isBlank()) {

                                                message = "Please enter correct car's make";

                                            } else {

                                                if (car_Model.isBlank()) {

                                                    message = "Please enter the car's model";

                                                }

                                                else {

                                                    if (car_Year <= 2023) {

                                                        LocalDateTime currentDateTime = LocalDateTime.now();
                                                        DateTimeFormatter outputFormatter = DateTimeFormatter
                                                                .ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        String formattedDateTime = currentDateTime
                                                                .format(outputFormatter);

                                                        String targetName = slot_Id;
                                                        Component[] components = staffSlotsPanel.getComponents();

                                                        for (Component component : components) {

                                                            if (component instanceof JButton) {
                                                                JButton button = (JButton) component;
                                                                if (targetName.equals(button.getActionCommand())) {

                                                                    carPark.addCar(slot_Id, car_Reg, car_Make,
                                                                            car_Model, car_Year, formattedDateTime);

                                                                    message = "Car added successfully";
                                                                    f.validate();
                                                                    f.repaint();
                                                                    break;
                                                                } else {

                                                                    message = "You have selected staff parking slot. Please select visitor's slot for parking";
                                                                }
                                                            } else {

                                                                message = "You have selected staff parking slot. Please select visitor's slot for parking";
                                                            }
                                                        }

                                                    } else {

                                                        message = "Invalid input. Please enter a valid year.";
                                                        System.out.println("*************15********************");
                                                    }
                                                }
                                            }
                                        } else {

                                            message = car_Reg
                                                    + " Car Registration number does not match the pattern. Please enter it correctly.";
                                            System.out.println("*************16********************");
                                        }
                                    } else {

                                        message = "Car with registration number " + car_Reg
                                                + " is already parked in the parking.";
                                        System.out.println("*************17********************");
                                    }
                                } else {

                                    message = "Slot " + slot_Id + " is not available";
                                    System.out.println("*************18********************");
                                }
                            }

                        }

                        else if (visitorsRadioButton.isSelected()) {

                            if (carPark.hashMap.containsKey(slot_Id)) {

                                message = " Slot " + slot_Id
                                        + " already occupied. Please select a different slot for parking ";
                            } else {
                                boolean containsBob = parkingSlot.slots.contains(slot_Id);
                                if (containsBob) {

                                    String pattern = "^[A-Z]\\d{4}$";
                                    boolean found = false;
                                    ArrayList<Car> car1 = carPark.cars;

                                    for (Car car : car1) {
                                        if (car.getCarReg().equals(car_Reg)) {
                                            found = true;
                                        }
                                    }
                                    if (found == false) {
                                        if (Pattern.matches(pattern, car_Reg)) {

                                            if (car_Make.isBlank()) {

                                                message = "Please enter correct car's make";
                                            } else {

                                                if (car_Model.isBlank()) {

                                                    message = "Please enter the car's model";
                                                }

                                                else {

                                                    if (car_Year <= 2023) {

                                                        LocalDateTime currentDateTime = LocalDateTime.now();
                                                        DateTimeFormatter outputFormatter = DateTimeFormatter
                                                                .ofPattern("yyyy-MM-dd HH:mm:ss");
                                                        String formattedDateTime = currentDateTime
                                                                .format(outputFormatter);

                                                        String targetName = slot_Id;
                                                        Component[] components = visitorSlotsPanel.getComponents();

                                                        for (Component component : components) {
                                                            if (component instanceof JButton) {
                                                                JButton button = (JButton) component;
                                                                if (targetName.equals(button.getActionCommand())) {

                                                                    carPark.addCar(slot_Id, car_Reg, car_Make,
                                                                            car_Model, car_Year, formattedDateTime);

                                                                    message = "Car added successfully";
                                                                    f.validate();
                                                                    f.repaint();
                                                                    break;
                                                                } else {
                                                                    message = "You have selected visitor's parking slot. Please select staff's slot for parking";
                                                                }
                                                            } else {
                                                                message = "You have selected visitor's parking slot. Please select staff's slot for parking";
                                                            }

                                                        }

                                                    } else {

                                                        message = "Invalid input. Please enter a valid year.";
                                                    }
                                                }
                                            }
                                        } else {

                                            message = car_Reg
                                                    + " Car Registration number does not match the pattern. Please enter it correctly.";
                                        }
                                    } else {

                                        message = "Car with registration number " + car_Reg
                                                + " is already parked in the parking.";
                                    }
                                } else {

                                    message = "Slot " + slot_Id + " is not available";
                                }
                            }

                        } else {
                            message = "Select Slot type";

                        }
                    }

                    if (staffRadioButton.isSelected() || visitorsRadioButton.isSelected()) {

                        if (message.equals("Car added successfully")) {
                            String targetName = slot_Id;
                            Component[] components1 = staffSlotsPanel.getComponents();
                            Component[] components2 = visitorSlotsPanel.getComponents();

                            for (Component component : components1) {
                                if (component instanceof JButton) {
                                    JButton button = (JButton) component;
                                    if (targetName.equals(button.getActionCommand())) {

                                        button.setText(slot_Id + " is Occupied");
                                        setButtonStyle2(button, Color.red, Color.black);
                                      
                                      
                                        f.validate();
                                        f.repaint();
                                        break;
                                    }
                                }
                            }
                            for (Component component : components2) {
                                if (component instanceof JButton) {
                                    JButton button = (JButton) component;
                                    if (targetName.equals(button.getActionCommand())) {

                                        button.setText(slot_Id + " is Occupied");
                                        setButtonStyle2(button, Color.red, Color.black);
                                    
                                        f.validate();
                                        f.repaint();
                                        break;
                                    }
                                }

                            }

                        }
                    }

                    else {
                        message = "Please select correct slot for parking";

                    }

                    JDialog carParkDialog = new JDialog(f, "Message", true);
                    carParkDialog.setSize(500, 300);

                    carParkDialog.setResizable(false);

                    int frameX = f.getLocation().x;
                    int frameY = f.getLocation().y;
                    int frameWidth = f.getWidth();
                    int frameHeight = f.getHeight();
                    int dialogWidth = carParkDialog.getWidth();
                    int dialogHeight = carParkDialog.getHeight();

                    int x = frameX + (frameWidth - dialogWidth) / 2;
                    int y = frameY + (frameHeight - dialogHeight) / 2;

                    carParkDialog.setLocation(x, y);

                    JLabel carParkMessage = new JLabel(message);

                    carParkDialog.add(carParkMessage, BorderLayout.CENTER);

                    
                 // Hide various components
                 
                   
                    carParkDialog.setVisible(true);

                    slotIdLLabel.setVisible(false);
                    slotIdTextField.setVisible(false);
                    carRegLLabel.setVisible(false);
                    carRegTextField.setVisible(false);
                    carMakeLLabel.setVisible(false);
                    carMakeTextField.setVisible(false);
                    carModelLabel.setVisible(false);
                    carModelTextField.setVisible(false);
                    carYearLabel.setVisible(false);
                    carYearTextField.setVisible(false);
                    carParkButton.setVisible(false);
                    cancelButton.setVisible(false);

                    staffRadioButton.setVisible(false);
                    visitorsRadioButton.setVisible(false);

                    // Enable buttons
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);

                    // Set button styles
                    setButtonStyle(parkCarButton, Color.LIGHT_GRAY, Color.WHITE);
                }
            });

        }
    });

    // Add action listener for the "find car button"
    findCarButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {

        	// Disable various buttons
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);

            findCarButton.setEnabled(false);
            setButtonStyle(findCarButton, Color.DARK_GRAY, Color.WHITE);

         // Set button style for Find Car button
            JLabel carRegFindLabel = new JLabel("Please enter car registration number ");
            JTextField carRegFindTextField = new JTextField(10);
            JButton carRegFindButton = new JButton("Find a car by registration Number");
         // Create and set up components for finding a car by registration number
           
            // Add components to inputPanel
            inputPanel.add(carRegFindLabel);
            inputPanel.add(carRegFindTextField);
            inputPanel.add(carRegFindButton);

            inputPanel.revalidate();
            inputPanel.repaint();

            // Create and set up a Cancel button
            JButton cancelButton = new JButton("Cancel");
            inputPanel.add(cancelButton);

            // Add an ActionListener to the Cancel button
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	 // Hide various components
                
                   
                    cancelButton.setVisible(false);

                    deleteSlotButton.setEnabled(true);
                    carRegFindLabel.setVisible(false);
                    carRegFindTextField.setVisible(false);
                    carRegFindButton.setVisible(false);

                    // Enable buttons
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    // Set button style for Find Car button
                    setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });

            // Add action listener for the car reg find button
            carRegFindButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	// Hide  components
                    carRegFindLabel.setVisible(false);
                    carRegFindTextField.setVisible(false);
                    carRegFindButton.setVisible(false);
                    cancelButton.setVisible(false);
                    cancelButton.setVisible(false);
                    findCarButton.setEnabled(true);
                    
                    // Create a modal dialog for displaying car details

                    JDialog findCarDetailsDialog = new JDialog(f, "Car Details", true);

                 // Create a panel for layout with a grid of two columns
                    JPanel dialogPanel = new JPanel(new GridLayout(0, 2));

                 // Create another dialog for car details 
                    JDialog findCarDetailsDialog2 = new JDialog(f, "Car Details", true);

                    // Create a panel for layout with a BorderLayout
                    JPanel dialogPanel2 = new JPanel(new BorderLayout());

                    // Set the size of the first dialog
                    findCarDetailsDialog.setSize(400, 300);
                    findCarDetailsDialog.setResizable(false);
                    int frameX = f.getLocation().x;
                    int frameY = f.getLocation().y;
                    int frameWidth = f.getWidth();
                    int frameHeight = f.getHeight();
                    int dialogWidth = findCarDetailsDialog.getWidth();
                    int dialogHeight = findCarDetailsDialog.getHeight();

                 // Calculate the position to center the first dialog
                    int x = frameX + (frameWidth - dialogWidth) / 2;
                    int y = frameY + (frameHeight - dialogHeight) / 2;

               
                    findCarDetailsDialog.setLocation(x, y);
                    // Set the size of the second dialog 
                    findCarDetailsDialog2.setSize(300, 200);
                    findCarDetailsDialog2.setResizable(false);
                    
                 // Calculate the position to center the second dialog
                    int frameX1 = f.getLocation().x;
                    int frameY1 = f.getLocation().y;
                    int frameWidth1 = f.getWidth();
                    int frameHeight1 = f.getHeight();
                    int dialogWidth1 = findCarDetailsDialog2.getWidth();
                    int dialogHeight1 = findCarDetailsDialog2.getHeight();

                    int x1 = frameX + (frameWidth - dialogWidth) / 2;
                    int y1 = frameY + (frameHeight - dialogHeight) / 2;

                    findCarDetailsDialog2.setLocation(x, y);

                    // Get the car registration from the text field
                    String carReg = carRegFindTextField.getText();
                    
                    // Query the carPark object to get cars by registration number
                    HashMap<String, ArrayList<Car>> getCarsByRegNumber = carPark.getCarsByRegNumber(carReg);

                    String message = "";

                    if (carReg.isBlank()) {
                    	 // User did not enter a car registration, show an error message
                        message = "Please enter Car Registration number";

                        String messageLabel = message;

                        // Create a label and button panel
                        JPanel labelAndButtonPanel = new JPanel(new GridLayout(2, 1));

                        JLabel carNotFindLabel = new JLabel(message);
                        carNotFindLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
                        // horizontally

                        JButton okButton = new JButton("Ok");
                        okButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally

                        dialogPanel.add(okButton);

                        labelAndButtonPanel.add(carNotFindLabel);
                        labelAndButtonPanel.add(okButton);

                        dialogPanel2.add(labelAndButtonPanel, BorderLayout.CENTER);

                     // Add an ActionListener to the "Ok" button for the error message dialog
                        okButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            	
                            	// Enable  buttons and hide components
                                deleteSlotButton.setEnabled(true);
                                carRegFindLabel.setVisible(false);
                                carRegFindTextField.setVisible(false);
                                carRegFindButton.setVisible(false);
                                findCarDetailsDialog.dispose();
                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);
                                cancelButton.setVisible(false);

                                setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);
                                findCarDetailsDialog2.dispose();
                            }

                        });

                    }

                 // Check if there are no cars with the given registration number
                    else if (getCarsByRegNumber.isEmpty()) {
                    	 // Show an error message
                        message = "No cars parked with " + carReg + " registration number";
                        JPanel labelAndButtonPanel = new JPanel(new GridLayout(2, 1));

                        JLabel carNotFindLabel = new JLabel(message);
                        carNotFindLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the text
                        // horizontally

                        JButton okButton = new JButton("Ok");
                        okButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally

                        dialogPanel.add(okButton);

                        labelAndButtonPanel.add(carNotFindLabel);
                        labelAndButtonPanel.add(okButton);

                        dialogPanel2.add(labelAndButtonPanel, BorderLayout.CENTER);

                        // Add WindowListeners 
                        okButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                deleteSlotButton.setEnabled(true);
                                carRegFindLabel.setVisible(false);
                                carRegFindTextField.setVisible(false);
                                carRegFindButton.setVisible(false);
                                findCarDetailsDialog.dispose();
                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);
                                cancelButton.setVisible(false);

                                setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);
                                findCarDetailsDialog2.dispose();
                            }

                        });

                    }
                    
                    // Add WindowListeners for closing the dialogs
                    findCarDetailsDialog2.addWindowListener(new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {

                        	 // Enable buttons and set button style when closing the dialog
                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);
                            setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);
                            findCarDetailsDialog2.dispose();
                        }
                    });

                    findCarDetailsDialog.addWindowListener(new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {

                        	 // Enable buttons and set button style when closing the second dialog
                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);
                            setButtonStyle(findCarButton, Color.LIGHT_GRAY, Color.WHITE);
                            findCarDetailsDialog.dispose();
                        }
                    });

                 // Check if there are no cars with the given registration number or no registration entered
                    if (getCarsByRegNumber.isEmpty() || carReg.isBlank()) {
                        findCarDetailsDialog2.add(dialogPanel2);
                        findCarDetailsDialog2.setVisible(true);
                    }

                    else {
                    	// Display car details in the first dialog
                        for (Map.Entry<String, ArrayList<Car>> entry : getCarsByRegNumber.entrySet()) {
                            ArrayList<Car> valueList = entry.getValue();
                            for (Car value : valueList) {
                                if (value.getCarReg().equals(carReg)) {
                                    dialogPanel.add(new JLabel("Car Slot ID:"));
                                    dialogPanel.add(new JLabel(value.getSlotId()));

                                    dialogPanel.add(new JLabel("Car Registration:"));
                                    dialogPanel.add(new JLabel(value.getCarReg()));

                                    dialogPanel.add(new JLabel("Car Make:"));
                                    dialogPanel.add(new JLabel(value.getCarMake()));

                                    dialogPanel.add(new JLabel("Car Model:"));
                                    dialogPanel.add(new JLabel(value.getCarModel()));

                                    int number = value.getCarYear();
                                    String carYear = Integer.toString(number);

                                    dialogPanel.add(new JLabel("Car Year:"));
                                    dialogPanel.add(new JLabel(carYear));

                                    String storedTimeString = value.getCarTime();
                                    LocalDateTime currentDateTime = LocalDateTime.now();
                                    DateTimeFormatter formatter = DateTimeFormatter
                                            .ofPattern("yyyy-MM-dd HH:mm:ss");
                                    LocalDateTime storedTime = LocalDateTime.parse(storedTimeString, formatter);
                                    Duration duration = Duration.between(storedTime, currentDateTime);
                                    long hours = duration.toHours();
                                    long minutes = duration.toMinutesPart();
                                    long seconds = duration.toSecondsPart();

                                    String duration1 = +hours + " hours, " + minutes + " minutes, " + seconds
                                            + " seconds";

                                    dialogPanel.add(new JLabel("Duration:"));
                                    dialogPanel.add(new JLabel(duration1));

                                    findCarDetailsDialog.add(dialogPanel);
                                    findCarDetailsDialog.setVisible(true);

                                }
                            }
                        }
                    }

                }
            });

        }
    });
    
    // Add WindowListeners for findCarsByMakeButton

    findCarsByMakeButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	 // Disable various buttons when the "Find Cars by Make" button is clicked.
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);
            // Change the style of the "Find Cars by Make" button.
            setButtonStyle(findCarsByMakeButton, Color.DARK_GRAY, Color.WHITE);

         // Create UI components for finding cars by make.
            JLabel carMakeFindLabel = new JLabel("Please enter car make ");
            JTextField carMakeFindTextField = new JTextField(10);
            JButton carMakeFindButton = new JButton("Find a car ");

            // Add the UI components to the inputPanel.
            inputPanel.add(carMakeFindLabel);
            inputPanel.add(carMakeFindTextField);
            inputPanel.add(carMakeFindButton);

            // Refresh the inputPanel to reflect the changes.
            inputPanel.revalidate();
            inputPanel.repaint();

         // Create a "Cancel" button for canceling the car search process.
            JButton cancelButton = new JButton("Cancel");
            inputPanel.add(cancelButton);

            // Define action for the "Cancel" button.
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	 // Hide UI components for car search and enable buttons.
                    cancelButton.setVisible(false);

                    carMakeFindLabel.setVisible(false);
                    carMakeFindTextField.setVisible(false);
                    carMakeFindButton.setVisible(false);

                    deleteSlotButton.setEnabled(true);
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    // Reset the style of the "Find Cars by Make" button.
                    setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });

            // Define action for the "Find a car" button.
            carMakeFindButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	 // Hide UI components for car search.
                    findCarsByMakeButton.setEnabled(false);
                    carMakeFindLabel.setVisible(false);
                    carMakeFindTextField.setVisible(false);
                    carMakeFindButton.setVisible(false);
                    cancelButton.setVisible(false);

                 // Enable buttons.
                    deleteSlotButton.setEnabled(true);
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                 // Reset the style of the "Find Cars by Make" button.
                    setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);

                    // Create dialogs to display car details.
                    JDialog findCarDetailsByMakeDialog = new JDialog(f, "Car Details", true);
                    JDialog findCarDetailsByMakeDialog2 = new JDialog(f, "Car Details", true);
                    JPanel dialogPanel2 = new JPanel(new GridLayout(0, 2));
                    JPanel dialogPanel3 = new JPanel(new BorderLayout());
                    JPanel labelAndButtonPanel = new JPanel(new GridLayout(2, 1));

                    findCarDetailsByMakeDialog.setSize(400, 300);
                    findCarDetailsByMakeDialog.setResizable(false);

                    findCarDetailsByMakeDialog2.setSize(400, 300);
                    findCarDetailsByMakeDialog2.setResizable(false);

                    // Calculate the position of the dialogs relative to the main frame.
                    int frameX = f.getLocation().x;
                    int frameY = f.getLocation().y;
                    int frameWidth = f.getWidth();
                    int frameHeight = f.getHeight();
                    int dialogWidth = findCarDetailsByMakeDialog.getWidth();
                    int dialogHeight = findCarDetailsByMakeDialog.getHeight();

                    int x = frameX + (frameWidth - dialogWidth) / 2;
                    int y = frameY + (frameHeight - dialogHeight) / 2;

                    findCarDetailsByMakeDialog.setLocation(x, y);

                    int frameX1 = f.getLocation().x;
                    int frameY1 = f.getLocation().y;
                    int frameWidth1 = f.getWidth();
                    int frameHeight1 = f.getHeight();
                    int dialogWidth1 = findCarDetailsByMakeDialog2.getWidth();
                    int dialogHeight1 = findCarDetailsByMakeDialog2.getHeight();

                    int x1 = frameX + (frameWidth - dialogWidth) / 2;
                    int y1 = frameY + (frameHeight - dialogHeight) / 2;

                    findCarDetailsByMakeDialog2.setLocation(x, y);

                    String messageLabel = " ";

                    String carMake = carMakeFindTextField.getText();

                    // Search for cars by make in the car park.
                    HashMap<String, ArrayList<Car>> getCarsByMake = carPark.getCarsByMake(carMake);

                    if (carMake.isBlank()) {
                        messageLabel = "No cars are parked with " + carMake + " Make";
                        findCarDetailsByMakeDialog2.setSize(300, 200);

                        JLabel carNotFindLabel = new JLabel(messageLabel);
                        carNotFindLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // dialogPanel3.add(carNotFindLabel,BorderLayout.NORTH);

                        JButton okButton1 = new JButton("Ok");
                        okButton1.setPreferredSize(new Dimension(50, 30));
                        okButton1.setAlignmentX(Component.CENTER_ALIGNMENT);

                        labelAndButtonPanel.add(carNotFindLabel);
                        labelAndButtonPanel.add(okButton1);

                        dialogPanel3.add(labelAndButtonPanel, BorderLayout.CENTER);

                        // dialogPanel3.add(okButton1,BorderLayout.CENTER);

                        findCarDetailsByMakeDialog2.addWindowListener(new WindowAdapter() {

                            public void windowClosing(WindowEvent e) {

                            	 // Enable buttons and close the dialog.
                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);

                                carMakeFindLabel.setVisible(false);
                                carMakeFindTextField.setVisible(false);
                                carMakeFindButton.setVisible(false);

                                setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);
                                findCarDetailsByMakeDialog2.dispose();
                            }
                        });
                        okButton1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {

                            	 // Close the dialog and enable buttons.
                                carMakeFindLabel.setVisible(false);
                                carMakeFindTextField.setVisible(false);
                                carMakeFindButton.setVisible(false);
                                cancelButton.setVisible(false);

                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);
                                setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);
                                findCarDetailsByMakeDialog2.dispose();

                            }
                        });
                    }

                    else if (getCarsByMake.isEmpty()) {

                        findCarDetailsByMakeDialog2.setSize(300, 200);

                        messageLabel = "No cars are parked with " + carMake + " Make";

                        findCarDetailsByMakeDialog2.setSize(300, 200);

                        JLabel carNotFindLabel = new JLabel(messageLabel);
                        carNotFindLabel.setHorizontalAlignment(SwingConstants.CENTER);

                        // dialogPanel3.add(carNotFindLabel,BorderLayout.NORTH);

                        JButton okButton1 = new JButton("Ok");
                        okButton1.setPreferredSize(new Dimension(80, 30));
                        okButton1.setAlignmentX(Component.CENTER_ALIGNMENT);

                        labelAndButtonPanel.add(carNotFindLabel);
                        labelAndButtonPanel.add(okButton1);

                        dialogPanel3.add(labelAndButtonPanel, BorderLayout.CENTER);

                        findCarDetailsByMakeDialog2.addWindowListener(new WindowAdapter() {

                            public void windowClosing(WindowEvent e) {

                            	// Enable buttons and close the dialog.
                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);

                                carMakeFindLabel.setVisible(false);
                                carMakeFindTextField.setVisible(false);
                                carMakeFindButton.setVisible(false);

                                setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);
                                findCarDetailsByMakeDialog.dispose();
                            }
                        });
                        
                        // Add action listener for the "Ok " button
                        okButton1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {

                            	  // Close the dialog and enable buttons.
                                carMakeFindLabel.setVisible(false);
                                carMakeFindTextField.setVisible(false);
                                carMakeFindButton.setVisible(false);
                                cancelButton.setVisible(false);

                                addSlotButton.setEnabled(true);
                                deleteSlotButton.setEnabled(true);
                                parkCarButton.setEnabled(true);
                                findCarButton.setEnabled(true);
                                listSlotsButton.setEnabled(true);
                                findCarsByMakeButton.setEnabled(true);
                                removeCarButton.setEnabled(true);
                                exitButton.setEnabled(true);
                                findCarDetailsByMakeDialog2.dispose();

                                setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);

                            }
                        });

                    }

                    if (getCarsByMake.isEmpty() || carMake.isBlank()) {
                        findCarDetailsByMakeDialog2.add(dialogPanel3, BorderLayout.CENTER);
                        findCarDetailsByMakeDialog2.setVisible(true);
                        findCarDetailsByMakeDialog.setSize(300, 200);
                    }

                 // Iterate through the cars found by make and display their details.
                    for (Map.Entry<String, ArrayList<Car>> entry : getCarsByMake.entrySet()) {
                        String key = entry.getKey();
                        ArrayList<Car> valueList = entry.getValue();
                        for (Car value : valueList) {
                            if (value.getCarMake().equals(carMake)) {
                            	
                            	// Calculate the duration of parking.
                                String storedTimeString = value.getCarTime();
                                LocalDateTime currentDateTime = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime storedTime = LocalDateTime.parse(storedTimeString, formatter);
                                Duration duration = Duration.between(storedTime, currentDateTime);
                                long hours = duration.toHours();
                                long minutes = duration.toMinutesPart();
                                long seconds = duration.toSecondsPart();

                             // Display car details in the dialog.
                                dialogPanel2.add(new JLabel("Car Slot ID:"));
                                dialogPanel2.add(new JLabel(value.getSlotId()));

                                dialogPanel2.add(new JLabel("Car Registration:"));
                                dialogPanel2.add(new JLabel(value.getCarReg()));

                                dialogPanel2.add(new JLabel("Car Make:"));
                                dialogPanel2.add(new JLabel(value.getCarMake()));

                                dialogPanel2.add(new JLabel("Car Model:"));
                                dialogPanel2.add(new JLabel(value.getCarModel()));

                                int number = value.getCarYear();
                                String carYear = Integer.toString(number);

                                dialogPanel2.add(new JLabel("Car Year:"));
                                dialogPanel2.add(new JLabel(carYear));

                                String storedTimeString1 = value.getCarTime();
                                LocalDateTime currentDateTime1 = LocalDateTime.now();
                                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                LocalDateTime storedTime1 = LocalDateTime.parse(storedTimeString1, formatter1);
                                Duration duration2 = Duration.between(storedTime1, currentDateTime1);
                                long hours1 = duration.toHours();
                                long minutes1 = duration.toMinutesPart();
                                long seconds1 = duration.toSecondsPart();

                                String carDuration = +hours + " hours, " + minutes + " minutes, " + seconds
                                        + " seconds";

                                dialogPanel2.add(new JLabel("Duration:"));
                                dialogPanel2.add(new JLabel(carDuration));

                                JLabel label1 = new JLabel();
                                dialogPanel2.add(label1);
                                JLabel label2 = new JLabel();
                                dialogPanel2.add(label2);

                                label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                                label2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                                dialogPanel2.add(new JLabel("-----------------------------"));
                                dialogPanel2.add(new JLabel("-----------------------------"));

                                // Display the car details dialog.
                                findCarDetailsByMakeDialog.add(dialogPanel2);
                                findCarDetailsByMakeDialog.setVisible(true);

                                findCarDetailsByMakeDialog.addWindowListener(new WindowAdapter() {

                                    public void windowClosing(WindowEvent e) {

                                    	// Enable buttons and close the dialog.
                                        addSlotButton.setEnabled(true);
                                        deleteSlotButton.setEnabled(true);
                                        parkCarButton.setEnabled(true);
                                        findCarButton.setEnabled(true);
                                        listSlotsButton.setEnabled(true);
                                        findCarsByMakeButton.setEnabled(true);
                                        removeCarButton.setEnabled(true);
                                        exitButton.setEnabled(true);

                                        carMakeFindLabel.setVisible(false);
                                        carMakeFindTextField.setVisible(false);
                                        carMakeFindButton.setVisible(false);

                                        setButtonStyle(findCarsByMakeButton, Color.LIGHT_GRAY, Color.WHITE);
                                        findCarDetailsByMakeDialog.dispose();
                                    }
                                });

                            }
                        }

                    }
                    

                }
            });

        }
    });

    // Add action listener for the "List all Slot" button
    listSlotsButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	 // Disable  buttons when the "List All Car Slots" button is clicked.
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);

            // Change the style of the "List All Car Slots" button.
            setButtonStyle(listSlotsButton, Color.DARK_GRAY, Color.WHITE);

            if (parkingSlot.slots.isEmpty()) {

            	// Display a message dialog when there are no car slots available.
                JPanel panel = new JPanel(new GridLayout(0, 2));

                JDialog noCarSlotDialog = new JDialog(f, "Message", true);
                noCarSlotDialog.setSize(400, 200);

                noCarSlotDialog.add(panel);

                noCarSlotDialog.setResizable(true);

             // Calculate the position of the dialog relative to the main frame.
                int frameX = f.getLocation().x;
                int frameY = f.getLocation().y;
                int frameWidth = f.getWidth();
                int frameHeight = f.getHeight();
                int dialogWidth = noCarSlotDialog.getWidth();
                int dialogHeight = noCarSlotDialog.getHeight();

                int x = frameX + (frameWidth - dialogWidth) / 2;
                int y = frameY + (frameHeight - dialogHeight) / 2;

                noCarSlotDialog.setLocation(x, y);

                JLabel noCarSlotLabel = new JLabel("No slots for parking found");
                noCarSlotDialog.setSize(200, 100);
                noCarSlotDialog.add(noCarSlotLabel);
                // Enable buttons and reset the style of the "List All Car Slots" button.
                noCarSlotDialog.setVisible(true);
                addSlotButton.setEnabled(true);
                deleteSlotButton.setEnabled(true);
                parkCarButton.setEnabled(true);
                findCarButton.setEnabled(true);
                listSlotsButton.setEnabled(true);
                findCarsByMakeButton.setEnabled(true);
                removeCarButton.setEnabled(true);
                exitButton.setEnabled(true);
                setButtonStyle(listSlotsButton, Color.LIGHT_GRAY, Color.WHITE);

            } else {
            	// Retrieve information about car slots and create a list of slots.
                HashMap<String, ArrayList<Car>> hashmap = carPark.hashMap;
                ArrayList<Car> cars = carPark.cars;

                // Get the list of car slots
                ArrayList<String> stringArrayList = parkingSlot.listAllCarSlot(hashmap, cars);

                // Create a dialog to list all car slots.
                JPanel panel = new JPanel();

                JDialog listAllCarSlots = new JDialog(f, "Message", true);
                listAllCarSlots.add(panel);
                listAllCarSlots.setSize(800, 300);

                listAllCarSlots.setResizable(false);

             // Calculate the position of the dialog relative to the main frame.
                int frameX = f.getLocation().x;
                int frameY = f.getLocation().y;
                int frameWidth = f.getWidth();
                int frameHeight = f.getHeight();
                int dialogWidth = listAllCarSlots.getWidth();
                int dialogHeight = listAllCarSlots.getHeight();
                int x = frameX + (frameWidth - dialogWidth) / 2;
                int y = frameY + (frameHeight - dialogHeight) / 2;
                listAllCarSlots.setLocation(x, y);

                // Create a panel to hold the labels
                JPanel labelPanel = new JPanel();
                labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

                // Add labels with strings from stringArrayList to the panel
                for (String s : stringArrayList) {
                    JLabel listAllCarSlotLabel = new JLabel(s);
                    labelPanel.add(listAllCarSlotLabel);

                    JLabel label1 = new JLabel();
                    labelPanel.add(label1);
                    JLabel label2 = new JLabel();
                    labelPanel.add(label2);

                    label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                    label2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    labelPanel.add(new JLabel("-----------------------------"));
                    // Enable buttons and reset the style of the "List All Car Slots" button.
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    setButtonStyle(listSlotsButton, Color.LIGHT_GRAY, Color.WHITE);

                }

                // Add the label panel to the main dialog
                panel.add(labelPanel);

                // Make sure to set the layout for the main dialog
                listAllCarSlots.setLayout(new FlowLayout());

                listAllCarSlots.setVisible(true);
            }

        }
    });
    
    // Add action listener for the "Remove car " button

    removeCarButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	// Disable various buttons when the "Remove Car" button is clicked.
            addSlotButton.setEnabled(false);
            deleteSlotButton.setEnabled(false);
            parkCarButton.setEnabled(false);
            findCarButton.setEnabled(false);
            listSlotsButton.setEnabled(false);
            findCarsByMakeButton.setEnabled(false);
            removeCarButton.setEnabled(false);
            exitButton.setEnabled(false);
            
            // Change the style of the "Remove Car" button.

            setButtonStyle(removeCarButton, Color.DARK_GRAY, Color.WHITE);

            // Create UI components for removing a car.
            JLabel carRegForRemoveLabel = new JLabel("Enter car registration number  ");
            JTextField carRegForRemoveTextField = new JTextField(10);
            JButton carRegForRemoveButton = new JButton("Remove car by registration Number");

            // Add the UI components to the inputPanel.
            inputPanel.add(carRegForRemoveLabel);
            inputPanel.add(carRegForRemoveTextField);
            inputPanel.add(carRegForRemoveButton);
            // Refresh the inputPanel to reflect the changes.
            inputPanel.revalidate();
            inputPanel.repaint();

         // Create a "Cancel" button for canceling the car removal process.
            JButton cancelButton = new JButton("Cancel");
            inputPanel.add(cancelButton);

            // Define action for the "Cancel" button.
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                	// Hide UI components for car removal and enable buttons.
                    cancelButton.setVisible(false);

                    carRegForRemoveLabel.setVisible(false);
                    carRegForRemoveButton.setVisible(false);
                    carRegForRemoveTextField.setVisible(false);
                    cancelButton.setVisible(false);

                    deleteSlotButton.setEnabled(true);
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                 // Reset the style of the "Remove Car" button.
                    setButtonStyle(removeCarButton, Color.LIGHT_GRAY, Color.WHITE);

                }
            });
         // Define action for the "Remove car by registration Number" button.
            carRegForRemoveButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	
                	// Hide UI components for car removal.
                    carRegForRemoveLabel.setVisible(false);
                    carRegForRemoveTextField.setVisible(false);
                    carRegForRemoveButton.setVisible(false);
                    removeCarButton.setEnabled(true);
                    cancelButton.setVisible(false);

                 // Get the car registration number to be removed.
                    String removeCarReg = carRegForRemoveTextField.getText();

                    String slotId = "";

                    String carReg = removeCarReg;
                 // Retrieve cars with the specified registration number.
                    HashMap<String, ArrayList<Car>> getCarsByRegNumber = carPark.getCarsByRegNumber(carReg);

                    for (Map.Entry<String, ArrayList<Car>> entry : getCarsByRegNumber.entrySet()) {
                        ArrayList<Car> valueList = entry.getValue();
                        for (Car value : valueList) {

                            if (value.getCarReg().equals(carReg)) {

                                slotId = value.getSlotId();

                            }
                        }
                    }

                    String targetName = slotId;

                    Component[] components1 = staffSlotsPanel.getComponents();
                    Component[] components2 = visitorSlotsPanel.getComponents();

                    String message = carPark.removeCarByReg(removeCarReg);

                    if (removeCarReg.isBlank()) {
                    	// Inform the user to enter a car registration number if it's empty.

                        message = "Please enter Car Registration Number.";
                    } else {

                    	 // Update slot buttons if car removal is successful.
                        if (message.equalsIgnoreCase("Car removed successfully")) {

                            for (Component component : components1) {
                                if (component instanceof JButton) {
                                    JButton button = (JButton) component;

                                    if (targetName.equals(button.getActionCommand())) {

                                        button.setText(slotId + " is available");
                                        setButtonStyle2(button, Color.green, Color.black);
                                        button.setEnabled(true);
                                        staffParkingSlots.validate();
                                        staffParkingSlots.repaint();
                                        break;
                                    }
                                }
                            }

                            for (Component component : components2) {
                                if (component instanceof JButton) {
                                    JButton button = (JButton) component;
                                    if (targetName.equals(button.getActionCommand())) {
                                        button.setText(slotId + " is available");
                                        setButtonStyle2(button, Color.green, Color.black);
                                        button.setEnabled(true);
                                        visitorSlotsPanel.validate();
                                        visitorSlotsPanel.repaint();
                                        f.validate();
                                        f.repaint();
                                        break;
                                    }
                                }
                            }
                        }

                    }
                 // Create a dialog to show the removal message.
                    JDialog removeCarDialog = new JDialog(f, "Message", true);
                    removeCarDialog.setSize(500, 100);

                    removeCarDialog.setResizable(false);
                 // Calculate the position of the dialog relative to the main frame.
                    int frameX = f.getLocation().x;
                    int frameY = f.getLocation().y;
                    int frameWidth = f.getWidth();
                    int frameHeight = f.getHeight();
                    int dialogWidth = removeCarDialog.getWidth();
                    int dialogHeight = removeCarDialog.getHeight();

                    int x = frameX + (frameWidth - dialogWidth) / 2;
                    int y = frameY + (frameHeight - dialogHeight) / 2;

                    removeCarDialog.setLocation(x, y);

                 // Create a dialog panel to display the removal message.
                    JPanel dialogPanel3 = new JPanel(new BorderLayout());

                    String messageLabel = message;
                    
                    // Create a label and "OK" button in the dialog.

                    JPanel labelAndButtonPanel = new JPanel(new GridLayout(2, 1));

                    JLabel carParkMessage = new JLabel(message);
                    carParkMessage.setHorizontalAlignment(SwingConstants.CENTER); // Center the text horizontally

                    JButton okButtonRemove = new JButton("Ok");
                    okButtonRemove.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally

                    labelAndButtonPanel.add(carParkMessage);
                    labelAndButtonPanel.add(okButtonRemove);

                    dialogPanel3.add(labelAndButtonPanel, BorderLayout.CENTER);

                    removeCarDialog.add(dialogPanel3);
                 // Define actions for closing the dialog and enabling buttons.
                    removeCarDialog.addWindowListener(new WindowAdapter() {

                        public void windowClosing(WindowEvent e) {

                        	// Enable buttons and close the dialog.
                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);
                            removeCarDialog.dispose();

                            findCarsByMakeButton.setEnabled(true);
                            setButtonStyle(removeCarButton, Color.LIGHT_GRAY, Color.WHITE);
                            removeCarDialog.dispose();
                        }
                    });

                    // Define action for the "OK" button in the dialog.
                    okButtonRemove.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                        	// Close the dialog and enable buttons.
                            removeCarDialog.dispose();
                            setButtonStyle(removeCarButton, Color.LIGHT_GRAY, Color.WHITE);

                            addSlotButton.setEnabled(true);
                            deleteSlotButton.setEnabled(true);
                            parkCarButton.setEnabled(true);
                            findCarButton.setEnabled(true);
                            listSlotsButton.setEnabled(true);
                            findCarsByMakeButton.setEnabled(true);
                            removeCarButton.setEnabled(true);
                            exitButton.setEnabled(true);

                        }
                    });
                 // enable buttons.
                    addSlotButton.setEnabled(true);
                    deleteSlotButton.setEnabled(true);
                    parkCarButton.setEnabled(true);
                    findCarButton.setEnabled(true);
                    listSlotsButton.setEnabled(true);
                    findCarsByMakeButton.setEnabled(true);
                    removeCarButton.setEnabled(true);
                    exitButton.setEnabled(true);
                    removeCarDialog.setVisible(true);
                    cancelButton.setVisible(false);

                }
            });

        }

    });

 // When the "exitButton" is clicked, the system will be exited with status code 0.
    exitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);

        }
    });

}

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
    	// Create a new Application instance when the Swing thread is ready.
        new Application();

    });
}
// Set the style for OvalButton: transparent, background color, text color, no border, no focus.
private void setButtonStyle(OvalButton button, Color backgroundColor, Color textColor) {
    
	button.setOpaque(false);
    button.setBackground(backgroundColor);
    button.setForeground(textColor);
    button.setBorderPainted(false);
    button.setFocusPainted(false);
}
// Set the style for a regular JButton: opaque, background color, text color, bordered, no focus.
private void setButtonStyle2(JButton button, Color backgroundColor, Color textColor) {
    button.setOpaque(true);
    button.setBackground(backgroundColor);
    button.setForeground(textColor);
    button.setBorderPainted(true);
    button.setFocusPainted(false);

}

private class CarAnimationPanel extends JPanel {
    private int carX = 20;
    private int carY = 80;
    private Timer timer;

    public CarAnimationPanel() {
        timer = new Timer(100, new ActionListener() {
            @Override
         // Move the car to the right by 10 pixels and wrap around if it goes out of bounds.
            public void actionPerformed(ActionEvent e) {
                carX += 10;
                if (carX + 100 > getWidth()) {
                    carX = -100;
                }
             // Trigger a repaint to update the car's position.
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Color c1 = new Color(20, 160, 200);
        Color c2 = new Color(200, 60, 200);

        g.setColor(c1);
        // Draw a horizontal line at carY + 75 with color c1.
        g.drawLine(0, carY + 75, getWidth(), carY + 75);
        g.setColor(c2);
     // Fill a rounded rectangle representing the car body.
        g.fillRoundRect(carX, carY + 20, 100, 40, 5, 5);
        // Fill a rounded rectangle representing the car's roof.
        g.fillArc(carX + 90, carY + 20, 20, 40, 270, 180);
        g.setColor(c1);
     // Fill a rounded rectangle representing the car's front.
        g.fillRoundRect(carX + 10, carY, 70, 25, 10, 10);
        g.setColor(Color.WHITE);
        // Fill the windshield with white color.
        g.fillRect(carX + 20, carY + 5, 20, 25);
        g.setColor(Color.BLACK);
        // Fill the car's window with black color.
        g.fillRoundRect(carX + 55, carY + 10, 10, 20, 10, 10);
     // Fill the car's wheels with black.
        g.fillOval(carX + 10, carY + 50, 25, 25);
        g.fillOval(carX + 60, carY + 50, 25, 25);
        g.setColor(Color.WHITE);
     // Fill the wheels with white for highlights.
        g.fillOval(carX + 15, carY + 55, 10, 10);
        g.fillOval(carX + 65, carY + 55, 10, 10);
    }
}

public static boolean isValidYearInput(String input) {
	// Check if the input consists of only digits. Return true if it's a valid year input.
    return input.matches("\\d+");
}

private class OvalButton extends JButton {
    public OvalButton(String label) {
        super(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
     // Paint an OvalButton with a rounded rectangle background.
        g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getHeight(), getHeight()));
        super.paintComponent(g);
    }
}
}
