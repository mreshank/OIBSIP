import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

public class GuessTheNumber extends Application 
{
    
    private int target, 
                numGuesses, 
                pts = 0, 
                atp = 0, 
                lower = 0, 
                upper = 0, 
                i = 0, 
                root_width = 500, 
                root_height = 525;

    private String cssFile = "./css/-fx-mid-style.css";

    private Label points = new Label("Points : " + pts), 
                attempts = new Label("Attempts Left : " + atp),
                label = new Label("Enter your guess"), messageLabel = new Label();

    private TextField textField = new TextField();

    private Button  button = new Button("Guess"), 
                    restart = new Button("Restart"), 
                    home = new Button("Back to Home");

    private RadioButton radioButton = new RadioButton("");

    private Slider slider = new Slider(0, 10, 5);   // slider.setMin(0);slider.setMax(100);slider.setValue(50);

    private Stage stage;

    private Media s0 = new Media(new File("sound/start.mp3").toURI().toString()),
                s1 = new Media(new File("sound/click.mp3").toURI().toString()),
                s2 = new Media(new File("sound/click4.mp3").toURI().toString()),
                s3 = new Media(new File("sound/click3.mp3").toURI().toString()),
                s4 = new Media(new File("sound/loss.mp3").toURI().toString());

    private MediaPlayer mp;



    /**
     * The Starting window of the game
     * @param primaryStage  Main Stage of the Game
     */
    @Override
    public void start(Stage primaryStage) 
    {
        bootxpsiace(0, 500);

        stage = primaryStage;

        points = new Label("Points : " + pts);

        label = new Label("CHOOSE A LEVEL"); // Create the GUI components

        Button  lv1 = new Button("Level 1 (0 to 10)"), 
                lv2 = new Button("Level 2 (0 to 500)"),
                lv3 = new Button("Level 3 (-500 to 500)"), 
                lv4 = new Button("Custom Level");

        lv1.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bootxpsiace(1, 250);
                startlevel(1);
            }
        });

        lv2.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bootxpsiace(1, 250);
                startlevel(2);
            }
        });

        lv3.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bootxpsiace(1, 250);
                startlevel(3);
            }
        });

        lv4.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                bootxpsiace(1, 250);
                l4rangechoice();
            }
        });

        // Set up the layout
        VBox root = new VBox(root_width / 15);
        root.setPadding(new Insets(root_height / 20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(points, label, lv1, lv2, lv3, lv4, slider);
        setborder(root);

        // Set up the scene and the stage
        Scene scene = new Scene(root, root_width, root_height);
        scene.getStylesheets().add(cssFile);
        primaryStage.setTitle("Guess the Number Game");
        primaryStage.setScene(scene);
        primaryStage.show();

    }



    /**
     * A Function to add deserving points to the user
     * @param v     An integer that contains the Number of guesses/attempts left after the answer is Guessed Correctly, to give points accordingly
     */
    private void adpts(int v) 
    {
        pts += Math.ceil(v / Math.sqrt(v));
        /* ptshw.setText("Points : "+pts); */ 
    }



    /* A Function to Update the "Points" label with the newly added points */
    private void showpts() 
    {
        points.setText("Points : " + atp);
    }



    /**
     * A Function to Create a Basic Vbox root at once
     * @param root  the Vertical Box Layout of the level the user is playing
     */
    private void CreateBasicGUI(VBox root) 
    {
        /* Create the GUI components */ 
        points.setText("Points : " + pts);
        attempts.setText("Attempts Left : " + atp);
        label.setText("Guess a number between " + lower + " & " + upper);
        messageLabel.setText("");
        button.setText("Guess");
        restart.setText("Restart");
        home.setText("Back to Home");

        /* Set up the layout */ 
        root.setPadding(new Insets(root_height / 20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(points, attempts, label, textField, button, messageLabel);
    }



    /***
     * A Function to Calculaate for the guessed number and do the task accordingly
     * @param guess textfiels variable to get inputed string
     * @param root  VBox element to set GUI within conditions
     * @param i     High vaue to add subtracted points from
     */

    private void CalcBrainET(int guess, VBox root, int i) 
    {
        textField.setText("");
        textField.requestFocus();
        bootxpsiace(3, 150);
        numGuesses++;

        if (guess < target) 
        {
            messageLabel.setText("Enter a number Greater than " + guess);
        } 
        else if (guess > target) 
        {
            messageLabel.setText("Enter a number Less than " + guess);
        } 
        else 
        {
            /* creating winning sound */ 
            bootxpsiace(2, 150);

            /* adding deserving points */
            adpts(i + 1 - numGuesses);

            /* Updating Points */
            showpts();

            /* Updating the message label with congratulations */
            messageLabel.setText("Correct !   You Won !\nYou guessed " + target + " in " + numGuesses + " attempts.");

            /* Removing all the elements from the screen */
            root.getChildren().clear();

            /* Adding the elements again to the screen */
            root.getChildren().addAll(points, messageLabel, restart, home);
        }
        if (--atp == 0 && guess != target) 
        {
            /* creating loosing sound */
            bootxpsiace(-1, 150);

            /* Updating the message label with loss */
            messageLabel.setText("You Lost !\nYou couldn't guess " + target + " in " + numGuesses + " attempts."); 

            /* Removing all the elements from the screen */
            root.getChildren().clear();

            /* Adding the elements again to the screen */
            root.getChildren().addAll(points, messageLabel, restart, home);
            
            /* Ending the statements here */
            return;
        }

        /* updating the number of guesses left/available for further use */
        attempts.setText("Attempts Left : " + atp); 
    }



    /**
     * A Function for the selected level to start in
     * @param lvl   The number of level of the User's choice
     */
    private void startlevel(int lvl) {
        switch (lvl) {
            case 1:
                lower = 0;
                upper = 10;
                break;
            case 2:
                lower = 0;
                upper = 500;
                break;
            case 3:
                lower = -500;
                upper = 500;
                break;
            case 4:
                break;
            default:
                return;
        }
        
        /* Generate a random number between the lower to upper ranger values */
        target = new Random().nextInt(upper - lower + 1) + lower;

        /* Variable to save user's input */
        numGuesses = 0;

        /* available trial points(guesses) */ 
        atp = (int) Math.ceil(0.2 * Math.sqrt(upper + Math.abs(lower)));
        atp += Math.abs(atp - 3) < 8 ? Math.abs(atp - 3) : 0;
        atp = (upper - lower <= 3) ? 1
                : (upper - lower > 3 && upper - lower <= 7) ? 2
                        : (upper - lower > 550 && upper - lower < 1249) ? atp + 1
                                : (upper - lower > 1250) ? (int) (atp - 0.25 * atp) : atp;
        
        // i = available trial points
        i = atp; 

        /* Create the layout3 */
        VBox root = new VBox(root_height / 15);
        
        /* Do the other Necessary/Bsic Tasks including changing/resetting elements */
        CreateBasicGUI(root);

        button.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            /* Set up the event handler for the button */
            public void handle(ActionEvent event) 
            {
                /* Processing further according to input */
                CalcBrainET(Integer.parseInt(textField.getText()), root, i);
            }
        });

        restart.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) {
                bootxpsiace(1, 250);
                startlevel(lvl);
            }
        });

        /* Back to home */
        home.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) {
                start(stage);
            }
        });

        setborder(root);
        
        /* Setting up the scene */
        Scene scene = new Scene(root, root_width, root_height);
        scene.getStylesheets().add(cssFile);

        /* Setting up the stage */ 
        stage.setTitle("Guess a Number btw " + lower + " & " + upper);
        
        /* Setting scene on stage */
        stage.setScene(scene);
        
        /* Showing the stage */
        stage.show();

    }



    /* A Function to Take input of the Range if the Custom level is Selected by the User */
    private void l4rangechoice() 
    {
        label.setText("Enter The Lower & Upper Range");

        TextField   low = new TextField(), 
                    up = new TextField();

        button.setText("Enter into Game");
        low.setPromptText("Enter The Lower Range");
        up.setPromptText("Enter The Upper Range");

        /* Set up the event handler for the range submit button */ 
        button.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent event) 
            {
                lower = Integer.parseInt(low.getText());
                upper = Integer.parseInt(up.getText());
                bootxpsiace(1, 250);
                startlevel(4);
            }
        });

        /* Create the layout */
        VBox root = new VBox(root_height / 15);
        
        /* Set up the layout */
        root.setPadding(new Insets(root_height / 20));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(label, low, up, button);
        setborder(root); //Adding a colourfull gradient border to the layout

        /* Setting up the scene */
        Scene scene = new Scene(root, root_width, root_height);
        scene.getStylesheets().add(cssFile);
        
        /* Setting up the stage */
        stage.setTitle("Range Input");
        
        /* Setting scene on stage */
        stage.setScene(scene);
         
        /* Showing the stage */
        stage.show();

    }



    /**
     * A Function to create a coloured border to a layout 
     * @param root  the vertical box layout provided to put borders on
     */
    private void setborder(VBox root) 
    {
        /* complimentry thing */
        root.setAlignment(Pos.CENTER); 

        /* Selecting the Colours to put on the motion gradient */
        Color[] colors = Stream.of("darkorange", "tomato", "deeppink", "blueviolet", "steelblue", "cornflowerblue",
                "lightseagreen", "#6fba82", "chartreuse", "crimson").map(Color::web).toArray(Color[]::new);

        List<Border> list = new ArrayList<>();
        int mills[] = { -200 };

        /* Setting the Keyframes */
        KeyFrame keyFrames[] = Stream.iterate(0, i -> i + 1).limit(1000)
                .map(i -> new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                        new Stop[] { new Stop(0, colors[i % colors.length]),
                                new Stop(1, colors[(i + 1) % colors.length]) }))
                .map(lg -> new Border(
                        new BorderStroke(lg, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))))
                .map(b -> new KeyFrame(Duration.millis(mills[0] += 200),
                        new KeyValue(root.borderProperty(), b, Interpolator.EASE_IN)))
                .toArray(KeyFrame[]::new);

        Timeline timeline = new Timeline(keyFrames);
        timeline.setCycleCount(Timeline.INDEFINITE);
        /* Playing the Motion Gradient Timeline */
        timeline.play();

    }



    /**
     * A Function to Select the Sound according to the situation or event
     * @param x the number of sound from the files accordin the current stage to event happening
     */
    private void mpc(int x) 
    {
        switch (x) {
            case -1:
                mp = new MediaPlayer(s4);
                break;
            case 0:
                mp = new MediaPlayer(s0);
                break;
            case 1:
                mp = new MediaPlayer(s1);
                break;
            case 2:
                mp = new MediaPlayer(s2);
                break;
            case 3:
                mp = new MediaPlayer(s3);
                break;
        }
    }



    // Adding a sound to the overall application (int Sound Number, int Delay) , along with a delay for the program to get load.
    private void bootxpsiace(int x, int ms) 
    { 
        try 
        {
            Thread.sleep(ms);
            mpc(x);
            mp.play();
        } 
        catch (InterruptedException ex) 
        {
            Thread.currentThread().interrupt();
        }
    }



    /* THE MAIN FUNCTION */
    public static void main(String[] args) 
    {
        launch(args);
    }

}
