package ContactManager;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
//import javafx.scene.shape.Path;
import javafx.scene.control.Label;

import javafx.scene.text.TextAlignment;
import javafx.geometry.Insets;
import java.io.*;


class Contact extends VBox implements Comparable<Contact> {

    HBox contactHeader;
    HBox phoneBox;
    HBox emailBox;
    HBox buttonBox;

    private Label index;
    private TextField contactName;
    private TextField phoneNum;
    private TextField email;

    private ImageView profilePic = new ImageView();
    private ImageView phoneIcon = new ImageView();
    private ImageView emailIcon = new ImageView();

    private Button editButton;
    private Button deleteButton;

    private FileChooser fileChooser = new FileChooser();

    Contact() {

        this.setPrefSize(500, 200); // sets size of Contact
        this.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font-weight: bold;"); // sets background color of contact
        //selected = false;
        index = new Label();
        index.setText(""); // create index label
        index.setPrefSize(40, 20); // set size of Index label
        index.setTextAlignment(TextAlignment.CENTER); // Set alignment of index label
        index.setPadding(new Insets(10, 0, 10, 0)); // adds some padding to the contact
        this.getChildren().add(index); // add index label to contact

        contactHeader = new HBox();
        contactHeader.setPrefSize(500, 50);
        contactHeader.setPadding(new Insets(0, 0, 10, 10));

        phoneBox = new HBox();
        phoneBox.setPrefSize(500, 50);
        phoneBox.setPadding(new Insets(10, 0, 10, 70));

        emailBox = new HBox();
        emailBox.setPrefSize(500, 50);
        emailBox.setPadding(new Insets(0, 0, 25, 65));

        buttonBox = new HBox(20);
        buttonBox.setPrefSize(500, 50);
        buttonBox.setPadding(new Insets(0, 0, 30, 0));
        buttonBox.setAlignment(Pos.CENTER);

        Image image = new Image(new File("resources/icons/anonymous.png").toURI().toString());
        profilePic.setImage(image);
        profilePic.setFitHeight(50);
        profilePic.setFitWidth(50);
        //this.getChildren().add(profilePic);
        contactHeader.getChildren().add(profilePic);

        contactName = new TextField(); // create contact name text field
        contactName.setPrefSize(380, 20); // set size of text field
        //"-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        contactName.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font: 24 arial;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        contactName.setPadding(new Insets(10, 0, 10, 10)); // adds some padding to the text field
        //this.getChildren().add(contactName); // add textlabel to contact
        contactHeader.getChildren().add(contactName);
        this.getChildren().add(contactHeader);


        Image pIcon = new Image(new File("resources/icons/phone.png").toURI().toString());
        phoneIcon.setImage(pIcon);
        phoneIcon.setFitHeight(20);
        phoneIcon.setFitWidth(20);
        //this.getChildren().add(profilePic);
        phoneBox.getChildren().add(phoneIcon);

        phoneNum = new TextField(); // create contact name text field
        phoneNum.setPrefSize(380, 20); // set size of text field
        phoneNum.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font: 16 arial;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        phoneNum.setPadding(new Insets(0, 0, 10, 10)); // adds some padding to the text field
        phoneNum.setPromptText("Phone Number");
        //this.getChildren().add(phoneNum); // add textlabel to contact
        phoneBox.getChildren().add(phoneNum);
        this.getChildren().add(phoneBox);

        Image eIcon = new Image(new File("resources/icons/email.png").toURI().toString());
        emailIcon.setImage(eIcon);
        emailIcon.setFitHeight(30);
        emailIcon.setFitWidth(30);
        //this.getChildren().add(profilePic);
        emailBox.getChildren().add(emailIcon);

        email = new TextField(); // create contact name text field
        email.setPrefSize(380, 20); // set size of text field
        email.setStyle("-fx-background-color: #DAE5EA; -fx-border-width: 0; -fx-font: 16 arial;"); // set background color of texfield
        index.setTextAlignment(TextAlignment.LEFT); // set alignment of text field
        email.setPadding(new Insets(5, 0, 5, 7)); // adds some padding to the text field
        email.setPromptText("Email");
        //this.getChildren().add(email); // add textlabel to contact
        emailBox.getChildren().add(email);
        this.getChildren().add(emailBox);

        String defaultButtonStyle = "-fx-font-style: italic; -fx-background-color: #FFFFFF;  -fx-font-weight: bold; -fx-font: 11 arial;";
        String dangerousButtonStyle = "-fx-font-style: italic; -fx-background-color: #ee6b6e;  -fx-font-weight: bold; -fx-font: 11 arial;";

        editButton = new Button("Edit Profile Pic"); // text displayed on add button
        editButton.setStyle(defaultButtonStyle); // styling the button
        editButton.setPrefSize(200,50);
        //(top/right/bottom/left)
        deleteButton = new Button("Delete"); // text displayed on add button
        deleteButton.setStyle(dangerousButtonStyle); // styling the button
        deleteButton.setPrefSize(200,50);

        buttonBox.getChildren().addAll(editButton, deleteButton);
        this.getChildren().add(buttonBox);

    }

    /**
     * Used to match the contactName, phoneNum, email and profile picture 
     * of another contact.
     * @param toChange the contact that will be copied
     */
    public void UpdateCredentials(Contact toChange){
        contactName.setText(toChange.getContactName().getText());
        phoneNum.setText(toChange.getPhoneNum().getText());
        email.setText(toChange.getEmail().getText());
        profilePic.setImage(toChange.getProfilePic().getImage());
    }

    public void setContactIndex(int num) {
        this.index.setText(num + ""); // num to String
        this.contactName.setPromptText("Contact " + num);
    }

    public TextField getContactName() {
        return this.contactName;
    }

    public TextField getPhoneNum(){
        return this.phoneNum;
    }

    public TextField getEmail(){
        return this.email;
    }

    public ImageView getProfilePic(){
        return this.profilePic;
    }

    public Button getEditButton(){
        return this.editButton;
    }

    public Button getDeleteButton(){
        return this.deleteButton;
    }

    public void changeProfilePic(Stage mainStage){
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            try {
                Image image = new Image(selectedFile.toURI().toString());
                profilePic.setImage(image);
            } catch (Exception e) {
                Image image = new Image(new File("resources/icons/anonymous.png").toURI().toString());
                profilePic.setImage(image);
            }
        }
    }

    public int compareTo(Contact compareContact){
        String name = this.contactName.getText();
        String compareName = compareContact.getContactName().getText();

        return name.compareTo(compareName);
    }
}
