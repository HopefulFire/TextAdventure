package Client;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Adventure Client, It is used to run the game
 *
 * Date Last Modified: 12/14/2019
 * @author Daniel Masker, Ben Hodsdon, Emma Smith, Joseph Teahen
 *
 * CS1131, fall 2019
 * Lab Section 2
 */
public class CustomAdventureClient extends Application {

    public static final Object lockInput = 0;               //Lock object for multithreading
    public static final Object lockOutput = 0;              //Lock object for multithreading
    public static BufferedReader fromServer;                //Wrapper for getting stuff from the server
    public static TextArea textArea;                        //Area for outputting text to viewer
    public static PrintWriter toServer;                     //Wrapper for sending data to server
    public static ArrayList<String> input = new ArrayList<>();      //Stores pending input
    public static ArrayList<String> output = new ArrayList<>();     //Stores pending output
    public InputThread inputThread;                                 //Thread for getting input

    /**
     * Main loop, just starts the Application
     *
     * @param args first is ip, second is port
     */
    public static void main(String[] args) {
        CustomAdventureClient.launch(args);
    }

    /**
     * Starts the Application
     *
     * @param primaryStage Stage to show the viewer
     * @throws Exception shouldn't be thrown, but could be
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        inputThread = new InputThread();
        inputThread.start(this.getParameters().getRaw());
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefRowCount(33);
        textArea.setPrefColumnCount(100);

        //Make text standard width
        textArea.setStyle("-fx-font-family: 'monospace'");


        TextField textField = new TextField();              //Area for the user to put input

        //Setup entering text
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (!textField.getText().isEmpty()) {
                    synchronized (lockInput) {
                        input.add(textField.getText());
                        textField.setText("");
                    }
                }
            }
        });

        VBox pane = new VBox();                             //Holds text output and input boxes

        pane.getChildren().add(textArea);
        pane.getChildren().add(textField);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {                  //Updates the display for the viewer
                if (textArea.getText().length() > 10000) {
                    textArea.deleteText(0, 5000);
                    textArea.appendText("");
                }

                if (output.size() > 0) {
                    synchronized (lockOutput) {
                        while (output.size() > 0) {
                            textArea.appendText(output.remove(0));
                        }
                        textArea.appendText("");
                    }
                }
            }
        };

        //This is for developmental purposes. I'm leaving it in because
        // I plan to continue development until I'm satisfied
        /*
        Button button = new Button("Reconnect");            //For reconnecting after losing connection
        button.setOnAction(event -> {
            input.add("exit");
            inputThread.interrupt();
            try {
                fromServer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            toServer.close();
            inputThread = new InputThread();
            inputThread.start(this.getParameters().getRaw());
        });

        pane.getChildren().add(button);
        */

        timer.start();


        Scene root = new Scene(pane, 800, 600);
        primaryStage.setScene(root);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.setTitle("Generic Dungeon Crawler 2019");
        primaryStage.show();
    }


    /**
     * Thread for handling server input and output
     */
    private static class InputThread extends Thread {
        List<String> args;                      //Args for getting the thread

        /**
         * Initializes thread with args
         *
         * @param args
         */
        public void start(List args) {
            this.args = args;   //Can't fix this :(
            super.start();
        }

        /**
         * Runs once, but this one contains a loop
         */
        public void run() {
            if (args == null || args.size() != 2) {
                System.out.println("Command line arguments: server_address port");
            } else {
                try (Socket server = new Socket(args.get(0),
                        Integer.valueOf(args.get(1)))) {
                    System.out.println("Connected to Server.AdventureServer host " + server.getInetAddress());
                    fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    toServer = new PrintWriter(server.getOutputStream(), true);
                    String s = "";
                    while (input != null) {
                        if (input.size() > 0) {
                            synchronized (lockInput) {
                                while (input.size() > 0) {
                                    s = input.get(0);
                                    input.remove(0);
                                    toServer.println(s);
                                    if (s.toLowerCase().trim().equals("exit")) {
                                        System.exit(0);
                                    }
                                }
                            }
                        }
                        if (fromServer.ready()) {
                            synchronized (lockOutput) {
                                while (fromServer.ready()) {
                                    s = fromServer.readLine();
                                    if (s == null) {
                                        break;
                                    }

                                    System.out.println(s);
                                    s += "\n";
                                    output.add(s);
                                }
                            }
                        }
                    }
                    fromServer.close();
                    toServer.close();

                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}