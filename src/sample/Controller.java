package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Controller
{
    @FXML
    TextField textFieldInput;

    Socket talking = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public Controller() throws IOException
    {
        talking = new Socket(InetAddress.getByName("127.0.0.1"), 37152);
        in = new DataInputStream(talking.getInputStream());
        out = new DataOutputStream(talking.getOutputStream());
    }

    public void ShowAlert(String message)
    {
        new Alert(Alert.AlertType.CONFIRMATION,message).showAndWait();
    }

    public void startGame() throws IOException {
        out.writeUTF("/start");
        out.flush();
    }
    public void question() throws IOException {
        out.writeUTF("/question");
        out.flush();
    }
    public void sayLetter() throws IOException {
        String label=textFieldInput.getText();
        if(label.length()==0)
        {
            ShowAlert("Empty letter");
            return;
        }
        out.writeUTF("/letter_"+label);
        out.flush();
    }
    public void sayWord() throws IOException {
        String label=textFieldInput.getText();
        if(label.length()==0)
        {
            ShowAlert("Empty word");
            return;
        }
        out.writeUTF("/word_"+label);
        out.flush();
    }
}
