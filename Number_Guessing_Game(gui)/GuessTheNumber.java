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
  private int target, numGuesses, pts=0, atp=0, lower=0, upper=0, i=0, root_width=500, root_height=525;     private String cssFile="./css/-fx-mid-style.css";
  private Label points = new Label("Points : "+pts), attempts = new Label("Attempts Left : "+atp), label = new Label("Enter your guess"), messageLabel = new Label();
  private TextField textField = new TextField();    
  private Button button = new Button("Guess"), restart = new Button("Restart"), home = new Button("Back to Home");
  private RadioButton radioButton = new RadioButton("");
  private Stage stage;  
  private Media s0 = new Media(new File("sound/start.mp3").toURI().toString()),
                s1 = new Media(new File("sound/click.mp3").toURI().toString()), 
                s2 = new Media(new File("sound/click4.mp3").toURI().toString()),
                s3 = new Media(new File("sound/click3.mp3").toURI().toString()),
                s4 = new Media(new File("sound/loss.mp3").toURI().toString());
  private MediaPlayer mp;

  public static void main(String[] args) {  launch(args);   }

  @Override public void start(Stage primaryStage) {
    bootxpsiace(0,500);
    stage = primaryStage;
    points = new Label("Points : "+pts); label = new Label("CHOOSE A LEVEL");   // Create the GUI components
    Button lv1 = new Button("Level 1 (0 to 10)"), lv2 = new Button("Level 2 (0 to 500)"), lv3 = new Button("Level 3 (-500 to 500)"), lv4 = new Button("Custom Level");
    lv1.setOnAction(new EventHandler<ActionEvent>() {   @Override public void handle(ActionEvent event) {   bootxpsiace(1,250);    startlevel(1);   }   }   );
    lv2.setOnAction(new EventHandler<ActionEvent>() {   @Override public void handle(ActionEvent event) {   bootxpsiace(1,250);    startlevel(2);   }   }   );
    lv3.setOnAction(new EventHandler<ActionEvent>() {   @Override public void handle(ActionEvent event) {   bootxpsiace(1,250);    startlevel(3);   }   }   );
    lv4.setOnAction(new EventHandler<ActionEvent>() {   @Override public void handle(ActionEvent event) {   bootxpsiace(1,250);    l4rangechoice();   }   }   );
    // Set up the layout 
    VBox root = new VBox(root_width/15);
    root.setPadding(new Insets(root_height/20));
    root.setAlignment(Pos.CENTER);
    root.getChildren().addAll(points, label, lv1, lv2, lv3, lv4);
    setborder(root);    
    // Set up the scene and the stage
    Scene scene = new Scene(root, root_width, root_height);        scene.getStylesheets().add(cssFile);
    primaryStage.setTitle("Guess the Number Game");
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private void adpts(int v){    pts+=Math.ceil(v/Math.sqrt(v)); /*ptshw.setText("Points : "+pts);*/   }

  private void showpts(){    points.setText("Points : "+atp);    }

  private void CreateBasicGUI(VBox root){
    /*Create the GUI components*/ points.setText("Points : "+pts); attempts.setText("Attempts Left : "+atp); label.setText("Guess a number between "+lower+" & "+upper); messageLabel.setText(""); button.setText("Guess"); restart.setText("Restart"); home.setText("Back to Home");
    /*Set up the layout*/   root.setPadding(new Insets(root_height/20));    root.setAlignment(Pos.CENTER);    root.getChildren().addAll(points, attempts, label, textField, button, messageLabel);
  }

  private void CalcBrainET(int guess,/*textfiels variable to get inputed string*/ VBox root/*VBox element to set GUI within conditions*/, int i/*High vaue to add subtracted points from*/){
        textField.setText("");     textField.requestFocus();     bootxpsiace(3,150);     numGuesses++; 
        if (guess < target) {   messageLabel.setText("Enter a number Greater than "+guess); 
        }   else if (guess > target) {  messageLabel.setText("Enter a number Less than "+guess); 
        }   else { bootxpsiace(2,150);/*creating winning sound*/  adpts(i+1-numGuesses);/*adding deserving points*/    showpts();/*Updating Points*/  
              messageLabel.setText("Correct !   You Won !\nYou guessed "+target+" in " + numGuesses + " attempts.");/*Updating the message label with congratulations*/   
              root.getChildren().clear();/*Removing all the elements from the screen*/  root.getChildren().addAll(points, messageLabel, restart, home);/*Adding the elements again to the screen*/
        }   if(--atp == 0 && guess != target){  bootxpsiace(-1,150);/*creating loosing sound*/  messageLabel.setText("You Lost !\nYou couldn't guess "+target+" in " + numGuesses + " attempts."); /*Updating the message label with loss*/  
              root.getChildren().clear();/*Removing all the elements from the screen*/   root.getChildren().addAll(points, messageLabel, restart, home);/*Adding the elements again to the screen*/    return;/*Ending the statements here*/
        }   attempts.setText("Attempts Left : "+atp); /*updating the number of guesses left/available for further use*/
  }

  private void startlevel(int lvl){
    switch(lvl){    case 1:lower=0;upper=10;break;    case 2:lower=0;upper=500;break;    case 3:lower=-500;upper=500;break;    case 4: break;   default:return;}
    target = new Random().nextInt(upper-lower+1)+lower;/*Generate a random number between the lower to upper ranger values*/    numGuesses = 0;/*Variable to save user's input*/    
    atp=(int)Math.ceil(0.2*Math.sqrt(upper+Math.abs(lower))); atp+=Math.abs(atp-3)<8?Math.abs(atp-3):0; atp=(upper-lower<=3)?1:(upper-lower>3&&upper-lower<=7)?2:(upper-lower>550&&upper-lower<1249)?atp+1:(upper-lower>1250)?(int)(atp-0.25*atp):atp;  /*available trial points(guesses)*/     i=atp;
    VBox root = new VBox(root_height/15);/*Create the layout3*/   CreateBasicGUI(root);/*Do the other Necessary/Bsic Tasks including changing/resetting elements*/
    button.setOnAction(new EventHandler<ActionEvent>() {    @Override public void handle(ActionEvent event) {/* Set up the event handler for the button*/ CalcBrainET(Integer.parseInt(textField.getText()),root,i);/*Processing further according to input*/    }   }   );
    restart.setOnAction(new EventHandler<ActionEvent>() {    @Override public void handle(ActionEvent event) {   bootxpsiace(1,250);   startlevel(lvl);   }   }   );
    home.setOnAction(new EventHandler<ActionEvent>() {    @Override public void handle(ActionEvent event) {   start(stage);   }   }   );/*Back to home*/    //sceneShow(root,lower,upper);/**/
    setborder(root);    Scene scene = new Scene(root, root_width, root_height);/*Setting up the scene*/    scene.getStylesheets().add(cssFile);    stage.setTitle("Guess a Number btw "+lower+" & "+upper);/*Setting up the stage*/    stage.setScene(scene);/*Setting scene on stage*/    stage.show();/*Showing the stage*/
  }

  private void l4rangechoice()  {
      label.setText("Enter The Lower & Upper Range");    TextField low = new TextField(),up = new TextField();    button.setText("Enter into Game");    low.setPromptText("Enter The Lower Range");    up.setPromptText("Enter The Upper Range");
      button.setOnAction(new EventHandler<ActionEvent>() {    @Override public void handle(ActionEvent event) {/* Set up the event handler for the button*/ lower=Integer.parseInt(low.getText());   upper=Integer.parseInt(up.getText());   bootxpsiace(1,250);   startlevel(4);}   }  );
      VBox root = new VBox(root_height/15);/*Create the layout*/   root.setPadding(new Insets(root_height/20));/*Set up the layout*/    root.setAlignment(Pos.CENTER);    root.getChildren().addAll(label,low,up,button);
      setborder(root);    Scene scene = new Scene(root, root_width, root_height);/*Setting up the scene*/    scene.getStylesheets().add(cssFile);    stage.setTitle("Range Input");/*Setting up the stage*/    stage.setScene(scene);/*Setting scene on stage*/    stage.show();/*Showing the stage*/
  }

  private void setborder(VBox root /*StackPane pane*/) {    root.setAlignment(Pos.CENTER); /*complimentry thing*/
        Color[] colors = Stream.of("darkorange", "tomato", "deeppink", "blueviolet", "steelblue", "cornflowerblue", "lightseagreen", "#6fba82", "chartreuse", "crimson").map(Color::web).toArray(Color[]::new);
        List<Border> list = new ArrayList<>();
        int mills[] = {-200};
        KeyFrame keyFrames[] = Stream.iterate(0, i -> i+1).limit(1000)
                .map(i -> new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0, colors[i%colors.length]), new Stop(1, colors[(i+1)%colors.length])}))
                .map(lg -> new Border(new BorderStroke(lg, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))))
                .map(b -> new KeyFrame(Duration.millis(mills[0]+=200), new KeyValue(root.borderProperty(), b, Interpolator.EASE_IN)))
                .toArray(KeyFrame[]::new);
        Timeline timeline = new Timeline(keyFrames);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
  
  private void mpc(int x){
    switch(x){
      case -1: mp = new MediaPlayer(s4); break;
      case 0 : mp = new MediaPlayer(s0); break;
      case 1 : mp = new MediaPlayer(s1); break;
      case 2 : mp = new MediaPlayer(s2); break;
      case 3 : mp = new MediaPlayer(s3); break;
    }
  }
  
  private void bootxpsiace(int x, int ms) { //(int Sound Number, int Delay)
        try {
            Thread.sleep(ms);
            mpc(x); mp.play();
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
