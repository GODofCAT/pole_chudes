package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Controller
{
    @FXML
    TextField textFieldInput;
    @FXML
    Label labelOutput;

    Socket talking = null;
    DataInputStream in = null;
    DataOutputStream out = null;

    public void ShowAlert(String message)
    {
        new Alert(Alert.AlertType.CONFIRMATION,message).showAndWait();
    }

    public void startGame(ActionEvent actionEvent) throws IOException {
        out.writeUTF("/start");
        out.flush();
        labelOutput.setText(in.readUTF());
    }
    public void question(ActionEvent actionEvent) throws IOException {
        out.writeUTF("/question");
        out.flush();
        labelOutput.setText(in.readUTF());
    }
    public void sayLetter(ActionEvent actionEvent) throws IOException {
        String label=textFieldInput.getText();
        if(label.length()==0)
        {
            ShowAlert("Empty letter");
            return;
        }
        out.writeUTF("/letter_"+label);
        out.flush();
        labelOutput.setText(in.readUTF());
    }
    public void sayWord(ActionEvent actionEvent) throws IOException {
        String label=textFieldInput.getText();
        if(label.length()==0)
        {
            ShowAlert("Empty word");
            return;
        }
        out.writeUTF("/word_"+label);
        out.flush();
        labelOutput.setText(in.readUTF());
    }

    public void connectWithServer(ActionEvent actionEvent) throws IOException {
        talking = new Socket(InetAddress.getByName("127.0.0.1"), 37152);
        in = new DataInputStream(talking.getInputStream());
        out = new DataOutputStream(talking.getOutputStream());

        ShowAlert("server connect us");
    }
}
