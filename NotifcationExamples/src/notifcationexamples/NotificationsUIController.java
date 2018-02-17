/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 * @altered by jasonkayser
 */
public class NotificationsUIController implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    
    @FXML
    private Button task1Button;
    
    @FXML 
    private Button task2Button;
    
    @FXML 
    private Button task3Button;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                if (task1 != null) task1.end();
                if (task2 != null) task2.end();
                if (task3 != null) task3.end();
            }
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        System.out.println("start task 1");
        if (task1 == null) {
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
            task1Button.setText("End Task 1"); //set new text value
        } else {         //if task already running
            task1.end();  //end task
            task1 = null;  
            task1Button.setText("Start Task 1"); //reset text value
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            task1Button.setText("Start Task 1"); //reset text value if task finishes
        }
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        System.out.println("start task 2");
        if (task2 == null) {
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                //check if task has finishes
                if(message.equals("Task2 done.")){   
                    task2Button.setText("Start Task 2"); //reset text
                    task2 = null;
                }
            });
            
            task2.start();
            task2Button.setText("End Task 2"); //set new text value
        }else { //if task is already runnig
            task2.end(); //end task
            task2 = null;
            task2Button.setText("Start Task 2");  //reset text
        }        
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        System.out.println("start task 3");
        if (task3 == null) {
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                textArea.appendText((String)evt.getNewValue() + "\n");
                if(((String)evt.getNewValue()).equals("Task3 done.")){ //check if task is finished
                    task3Button.setText("Start Task 3"); //reset text
                    task3 = null; 
                }
            });
            
            task3.start();
            task3Button.setText("End Task 3"); //set new text
        } else{
            task3.end(); //end running task
            task3 = null;
            task3Button.setText("Start Task 3"); //reset text
        }
    } 
}
