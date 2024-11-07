package view;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;

public class LoginView extends GridPane {

    private final TextField enterUserName = new TextField();
    private final PasswordField enterPassword = new PasswordField();
    private final Button enter = new Button("");

    public LoginView() {
        this.setHgap(10);
        this.setVgap(10);
        this.enter.setPrefWidth(100);

        FileInputStream input = null;
        try {
            input = new FileInputStream("images/enter.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        assert input != null;
        ImageView backIcon = new ImageView(new Image(input));
        backIcon.setFitHeight(30);
        backIcon.setFitWidth(30);
        enter.setGraphic(backIcon);
        enter.setContentDisplay(ContentDisplay.RIGHT);

        Label userName = new Label("Username : ");
        this.add(userName, 0, 0);
        this.add(enterUserName, 1, 0);
        Label password = new Label("Password : ");
        this.add(password, 0, 1);
        this.add(enterPassword, 1, 1);
        this.add(enter, 1, 3);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 17 ; -fx-font-weight : BOLD ; -fx-border-width: 2 ; -fx-border-color: BLACK;");
    }

    public String getEnterUserName() {
        return enterUserName.getText();
    }

    public void setEnterUserName() {
        this.enterUserName.setText("");
    }

    public String getEnterPassword() {
        return enterPassword.getText();
    }

    public void setEnterPassword() {
        this.enterPassword.setText("");
    }

    public Button getEnterBtn() {
        return enter;
    }

}
