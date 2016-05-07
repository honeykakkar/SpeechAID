package HelloWorld;

import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class Controller
{
    @FXML
    public Label ControlLabel;
    Stage MainWindow;
    Parent LALayout, CPCLayout, Root;
    Scene LearnAlphabetScene, ControlPCScene, MainScene;

    @FXML
    private void handleLAButton(ActionEvent event) throws IOException
    {
        MainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        LALayout = FXMLLoader.load(getClass().getResource("LearnWindow.fxml"));
        LearnAlphabetScene = new Scene(LALayout, 600, 400);
        MainWindow.setScene(LearnAlphabetScene);
        String image = this.getClass().getResource("control background.jpg").toExternalForm();
        LearnAlphabetScene.getRoot().setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
    }

    @FXML
    private void handleCPCButton(ActionEvent event) throws IOException
    {
        MainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        CPCLayout = FXMLLoader.load(getClass().getResource("ControlWindow.fxml"));
        ControlPCScene = new Scene(CPCLayout, 600, 400);
        MainWindow.setScene(ControlPCScene);
        String image = this.getClass().getResource("control background.jpg").toExternalForm();
        ControlPCScene.getRoot().setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
    }

    @FXML
    private void handleBackButton (ActionEvent event) throws IOException
    {
        MainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        Root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        MainScene = new Scene(Root, 600, 400);
        MainWindow.setScene(MainScene);
        String image = this.getClass().getResource("control background.jpg").toExternalForm();
        MainScene.getRoot().setStyle("-fx-background-image: url('" + image + "'); " +
                "-fx-background-position: center center; " +
                "-fx-background-repeat: stretch;");
    }

    @FXML
    private void startRecognizing (MouseEvent event) throws IOException
    {
        HelloWorld MainSRTask = new HelloWorld();
        final Thread SRThread = new Thread(MainSRTask);
        ControlLabel.textProperty().bind(MainSRTask.messageProperty());
        SRThread.setDaemon(true);
        SRThread.start();
        MainSRTask.setOnSucceeded(e -> {
                    ControlLabel.textProperty().unbind();});
        event.consume();
    }


    public class HelloWorld extends Task<Void>
    {
        boolean Stop;
        HelloWorld()
        {
            Stop = false;
        }

        @Override
        protected Void call() throws Exception {
            ConfigurationManager cm;
            cm = new ConfigurationManager(HelloWorld.class.getResource("helloworld.config.xml"));
            updateMessage("Please wait, allocating resources....");
            Recognizer recognizer = (Recognizer) cm.lookup("recognizer");
            recognizer.allocate();
            Microphone microphone = (Microphone) cm.lookup("microphone");
            if (!microphone.startRecording())
            {
                updateMessage("Cannot start microphone.");
                //System.out.print("Cannot start microphone.");
                recognizer.deallocate();
                System.exit(1);
            }

            //updateMessage("Say: (Open) ( Paint | Word | Browser )");
            //System.out.print("Say: (Open) ( Paint | Word | Browser )");

            String resultText = "";
            // loop the recognition until the program exits.
            while (true) {
                updateMessage("Start speaking. Press stop to quit.\nYou may say: (Open) ( Paint | Word | Browser )\nDid you say? : " + resultText);
                //System.out.print("Start speaking. Press stop to quit.");
                Result result = recognizer.recognize();
                if (result != null || result.toString() != "")
                {
                    resultText = result.getBestFinalResultNoFiller();
                    //System.out.print("You said: " + resultText + '\n');
                    if(resultText.equalsIgnoreCase("open paint"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start mspaint");
                    }
                    if(resultText.equalsIgnoreCase("open word"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start winword");
                    }
                    if(resultText.equalsIgnoreCase("open browser"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start chrome");
                    }
                    if(resultText.equalsIgnoreCase("open salt"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start chrome \"http://salt.ischool.syr.edu/\"");
                    }
                    if(resultText.equalsIgnoreCase("open latika"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start chrome \"http://www.latikasoap.com/\"");
                    }
                    if(resultText.equalsIgnoreCase("open nirvana"))
                    {
                        Process Current = Runtime.getRuntime().exec("cmd /c start chrome \"http://www.nirvana.com/\"");
                    }
                    if(resultText.equalsIgnoreCase("open grammar"))
                    {
                        URL location = this.getClass().getProtectionDomain().getCodeSource().getLocation();
                        String GrammarFile = location.getFile() + "hello.gram";
                        Process Current = Runtime.getRuntime().exec("cmd /c start notepad++");
                    }
                }
                else
                {
                    updateMessage("I can't hear what you said.");
                    //System.out.print("I can't hear what you said.");
                }
                if(Stop)
                    break;
            }
            return null;
        }
    }
}
