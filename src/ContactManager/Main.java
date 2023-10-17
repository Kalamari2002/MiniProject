package ContactManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

class ContactList extends VBox {
    Stage mainStage;
//
    ContactList(Stage primaryStage) {
        this.setSpacing(10); // sets spacing between contacts
        this.setPrefSize(500, 560);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.mainStage = primaryStage;
    }

    public void updateContactIndices() {
        int index = 1;
        for (int i = 0; i < this.getChildren().size(); i++) {
            if (this.getChildren().get(i) instanceof Contact) {
                ((Contact) this.getChildren().get(i)).setContactIndex(index);
                index++;
            }
        }
    }

    public void removeContact(Contact toRemove) {
        this.getChildren().remove(toRemove);
        this.updateContactIndices();
    }

    /*
     * Load contacts from a file called "contacts.txt"
     * Add the contacts to the children of contactlist component
     */
    public void loadContacts() throws IOException{
        FileReader fr;
        String pathName = "resources/contacts.csv";

        try {
            fr = new FileReader(pathName);
        } catch (Exception e) {
            throw new IOException();
        } 

        BufferedReader br = new BufferedReader(fr);
        
        while(br.ready()){  //Get each line and make a contact out of it

            String currLine = br.readLine();    //Read a line
            String[] data = currLine.split(",");
            Contact toAdd = new Contact();

            toAdd.getContactName().setText(data[0].substring(1, data[0].length() - 1));  //Set the new contact to have the read line as its name
            toAdd.getPhoneNum().setText(data[1].substring(1, data[1].length() - 1));
            toAdd.getEmail().setText(data[2].substring(1, data[2].length() - 1));

            File imgFile = new File("resources/icons/anonymous.png");
            Image pic = new Image(imgFile.toURI().toString());
            toAdd.getProfilePic().setImage(pic);

            Button editButton = toAdd.getEditButton();
            editButton.setOnAction(e1 -> {
                toAdd.changeProfilePic(this.mainStage);
            });
            Button deleteButton = toAdd.getDeleteButton();
            deleteButton.setOnAction(e1 -> {
                this.removeContact(toAdd);
            });

            this.getChildren().add(toAdd); 
        }
        this.updateContactIndices();

        fr.close();
        br.close();
        System.out.println("LOAD");
    }
    /*
     * Save contacts to a file called "contacts.txt"
     */
    public void saveContacts() throws IOException{
        String pathName = "resources/contacts.csv";
        File outputFile = new File(pathName);
        FileWriter fw;

        try {
            fw = new FileWriter(outputFile);
        } catch (Exception e) {
            throw new IOException();
        }
        //fw = new FileWriter(outputFile);
        
        for (int i = 0; i < this.getChildren().size(); i++) {   //Iterate thru each contact
            if (this.getChildren().get(i) instanceof Contact) {    //Set name to a contact format
                String name = "\"" + ((Contact)this.getChildren().get(i)).getContactName().getText() + "\"";
                String phone = "\"" + ((Contact)this.getChildren().get(i)).getPhoneNum().getText() + "\"";
                String mail = "\"" + ((Contact)this.getChildren().get(i)).getEmail().getText() + "\"";
                String line = name + "," + phone + "," + mail + "," + "\n";
                fw.write(line);
            }
        }

        fw.close();
        System.out.print("SAVED!");
    }

    /*
     * Sort the contacts lexicographically
     */
    public void sortContacts() {
        ArrayList<Contact> ls = new ArrayList<Contact>();
        for(int i = 0; i < this.getChildren().size(); i++){
            if (this.getChildren().get(i) instanceof Contact){
                Contact toAdd = new Contact();
                // toAdd = (Contact)this.getChildren().get(i);
                toAdd.UpdateCredentials((Contact)this.getChildren().get(i));
                ls.add(toAdd);
            }
        }

        Collections.sort(ls);
        int cntr = 0;

        for (int i = 0; i < this.getChildren().size(); i++) {   //Rename all contacts to sort them in alphabetical order
            if (this.getChildren().get(i) instanceof Contact) {
                ((Contact) this.getChildren().get(i)).UpdateCredentials(ls.get(cntr));
                cntr++;
            }
        }
    }
}

class Footer extends HBox {

    private Button addButton;
    private Button sortButton;
    private Button loadButton;
    private Button saveButton;

    Footer() {
        this.setPrefSize(500, 60);
        this.setStyle("-fx-background-color: #F0F8FF;");
        this.setSpacing(15);

        // set a default style for buttons - background color, font size, italics
        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";

        addButton = new Button("Add Contact"); // text displayed on add button
        addButton.setStyle(defaultButtonStyle); // styling the button
        sortButton = new Button("Sort Contacts (By Name)");
        sortButton.setStyle(defaultButtonStyle);
        loadButton = new Button("Load Contacts");
        loadButton.setStyle(defaultButtonStyle);
        saveButton = new Button("Save Contacts");
        saveButton.setStyle(defaultButtonStyle);

        this.getChildren().addAll(addButton, saveButton, loadButton, sortButton); // adding buttons to footer
        this.setAlignment(Pos.CENTER); // aligning the buttons to center

    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getSortButton(){
        return sortButton;
    }
    
    public Button getLoadButton(){
        return loadButton;
    }

    public Button getSaveButton(){
        return saveButton;
    }
}

class Header extends HBox {

    Header() {
        this.setPrefSize(500, 60); // Size of the header
        this.setStyle("-fx-background-color: #F0F8FF;");

        Text titleText = new Text("Contact List"); // Text of the Header
        titleText.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");
        this.getChildren().add(titleText);
        this.setAlignment(Pos.CENTER); // Align the text to the Center
    }
}

class AppFrame extends BorderPane{

    private Header header;
    private Footer footer;
    private ContactList contactList;
    private ScrollPane scroller;

    private Button addButton;
    private Button sortButton;
    private Button loadButton;
    private Button saveButton;

    private Stage mainStage;

    AppFrame(Stage mainStage)
    {
        this.mainStage = mainStage;
        // Initialise the header Object
        header = new Header();

        // Create a contactlist Object to hold the contacts
        contactList = new ContactList(mainStage);
        
        // Initialise the Footer Object
        footer = new Footer();

        scroller = new ScrollPane(contactList);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);

        // Add header to the top of the BorderPane
        this.setTop(header);
        // Add scroller to the centre of the BorderPane
        this.setCenter(scroller);
        // Add footer to the bottom of the BorderPane
        this.setBottom(footer);
        
        //this.setRight(scroller);

        // Initialise Button Variables through the getters in Footer
        addButton = footer.getAddButton();
        sortButton = footer.getSortButton();
        loadButton = footer.getLoadButton();
        saveButton = footer.getSaveButton();

        // Call Event Listeners for the Buttons
        addListeners();
    }

    public void addListeners()
    {
        // Add button functionality
        addButton.setOnAction(e -> {
            // Create a new contact
            Contact contact = new Contact();
            // Add contact to contactlist
            contactList.getChildren().add(contact);
            // Update contact indices
            Button editButton = contact.getEditButton();
            editButton.setOnAction(e1 -> {
                contact.changeProfilePic(mainStage);
            });

            Button deleteButton = contact.getDeleteButton();
            deleteButton.setOnAction(e2 -> {
                contactList.removeContact(contact);
            });

            contactList.updateContactIndices();
        });
        
        sortButton.setOnAction(e -> {
            contactList.sortContacts();
        });

        saveButton.setOnAction(e -> {
            try {
                contactList.saveContacts();
            } catch (Exception err) {
                System.out.println("Couldn't find contacts.txt");
            }
        });

        loadButton.setOnAction(e -> {
            try {
                contactList.loadContacts();
            } catch (IOException err) {
                System.out.println("Couldn't find contacts.txt");
            }
        });
    }
}

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Setting the Layout of the Window- Should contain a Header, Footer and the ContactList
        AppFrame root = new AppFrame(primaryStage);

        // Set the title of the app
        primaryStage.setTitle("Contact Manager");
        // Create scene of mentioned size with the border pane
        primaryStage.setScene(new Scene(root, 500, 600));
        // Make window non-resizable
        primaryStage.setResizable(false);
        // Show the app
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
