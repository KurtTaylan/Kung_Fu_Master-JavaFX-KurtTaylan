/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFrameworkJavaFX;




import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Taylan Kurt
 */

public class Gui extends Application {

    private static Gui gui;
    private Pane root;
    private Button menuButton;
    private Button continueButton;
    private Timeline worldTime;
    Scene scene;
    private Label energyLabel;
    private Label lifeLabel;
    private Label hitPointsLabel;
    private Label enemyHitpointsLabel; 
    private Label scoreLabel;
    private Label shurikenLabel;
    
    private final Media mainTheme = new Media( getClass().getResource("/resources/Main_Theme.mp3").toString() );
    private final Media introTheme = new Media( getClass().getResource("/resources/intro.mp3").toString() );
    private final Media gameOverTheme = new Media( getClass().getResource("/resources/outro.mp3").toString() );
    private final Media lvlCompleteTheme = new Media ( getClass().getResource("/resources/level_complete.mp3").toString() );
    private final Media EndMusicTheme  = new Media ( getClass().getResource("/resources/EndMusic.mp3").toString() );
    private MediaPlayer soundFX;
    private final MediaPlayer mainThemePlayer = new MediaPlayer(mainTheme);
    private final MediaPlayer introPlayer = new MediaPlayer(introTheme);
    private final MediaPlayer gameOverPlayer = new MediaPlayer(gameOverTheme);
    private final MediaPlayer lvlCompletePlayer = new MediaPlayer(EndMusicTheme);
    
    

    @Override
    public void start(Stage primaryStage) {
        gui = this;
        root = new Pane();
        scene = new Scene(root, 300, 250);
        scene.getStylesheets().add("style.css");
        setBackground("pane_title");

        scene.setOnKeyPressed((KeyEvent event) -> {
            //System.out.println("KeyEvent character "+event.getCode());
            Game.getInstance().keyPressed(event.getCode());
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            Game.getInstance().keyReleased(event.getCode());

        });

        scene.setOnMousePressed((MouseEvent event) -> {
            Game.getInstance().mousePressed(event.getButton() == MouseButton.PRIMARY, (int) event.getX(), (int) event.getY());
        });

        //Make fullscreen...
        Screen screen = Screen.getPrimary();
        javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(1300);
        primaryStage.setHeight(770);
        primaryStage.setResizable(false);

        primaryStage.setTitle("Kung Fu Master");
        primaryStage.setScene(scene);
        primaryStage.show();

        energyLabel = new Label();              
        lifeLabel = new Label();
        hitPointsLabel = new Label();
        enemyHitpointsLabel = new Label();
        scoreLabel = new Label();
        shurikenLabel = new Label();
        
        continueButton = new Button();
        continueButton.setText("Press Button To Continue...");
        continueButton.setVisible(false);
        
        energyLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        energyLabel.setTextFill(Color.WHITE);
        energyLabel.setOpacity(100.0);
        energyLabel.setStyle("-fx-background-color: black;");
        energyLabel.setMinWidth(128.0);
        energyLabel.setLayoutX(0);
        energyLabel.setLayoutY(0);
        
        hitPointsLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        hitPointsLabel.setTextFill(Color.WHITE);
        hitPointsLabel.setOpacity(100.0);
        hitPointsLabel.setStyle("-fx-background-color: black;");
        hitPointsLabel.setMinWidth(128.0);
        hitPointsLabel.setLayoutX(0);
        hitPointsLabel.setLayoutY(25);

        lifeLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        lifeLabel.setTextFill(Color.WHITE);
        lifeLabel.setOpacity(10.0);
        lifeLabel.setStyle("-fx-background-color: black;");
        lifeLabel.setMinWidth(128.0);
        lifeLabel.setLayoutX(0);
        lifeLabel.setLayoutY(50);
        
        enemyHitpointsLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        enemyHitpointsLabel.setTextFill(Color.WHITE);
        enemyHitpointsLabel.setOpacity(100.0);
        enemyHitpointsLabel.setStyle("-fx-background-color: black;");
        enemyHitpointsLabel.setLayoutX((1150));
        enemyHitpointsLabel.setVisible(false);
        
        scoreLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setOpacity(100.0);
        scoreLabel.setStyle("-fx-background-color: black;");
        scoreLabel.setMinWidth(128.0);
        scoreLabel.setLayoutX(0);
        scoreLabel.setLayoutY(78);
        
        shurikenLabel.setFont(new Font(STYLESHEET_MODENA, 20));
        shurikenLabel.setTextFill(Color.WHITE);
        shurikenLabel.setOpacity(100.0);
        shurikenLabel.setStyle("-fx-background-color: black;");
        shurikenLabel.setMinWidth(128.0);
        shurikenLabel.setLayoutX(0);
        shurikenLabel.setLayoutY(106);
        
        
        /*
        * When button is pressed checks with background counter which
        * stage to load
        */
        continueButton.setOnAction((ActionEvent event) -> {  
           Game.getInstance().setScreenCounter(1);
           switch (Game.getInstance().getLvlCounter()){
                    case 2:
                        Game.getInstance().setScreenCounter(1);
                        Game.getInstance().setGameState(GameState.PlayingTwo);
                        break;
                    case 3:
                        Game.getInstance().setGameState(GameState.PlayingThree);
                        break;
                }       
        });

        menuButton = new Button();
        menuButton.setText("Click to Start Game");
        
        menuButton.setOnAction((ActionEvent event) -> {
            mainThemeMusic("stop");
            Game.createGameAndSetGui(this); 
            System.out.println("The game was started...!");
            Game.getInstance().setGameState(GameState.PlayingOne);  
            lifeLabel.setVisible(true);
            hitPointsLabel.setVisible(true);
            energyLabel.setVisible(true);
            scoreLabel.setVisible(true);
            shurikenLabel.setVisible(true);
            menuButton.setVisible(false);  
            continueButton.setVisible(false);
        });
        mainThemeMusic("introTheme");
        menuButton.setLayoutX(600);
        continueButton.setLayoutX(600);
        continueButton.setLayoutY(384);


        root.getChildren().add(menuButton);
        root.getChildren().add(energyLabel);
        root.getChildren().add(lifeLabel);
        root.getChildren().add(hitPointsLabel);
        root.getChildren().add(continueButton);
        root.getChildren().add(enemyHitpointsLabel);
        root.getChildren().add(scoreLabel);
        root.getChildren().add(shurikenLabel);
    }

    public void startWorldTime() {
        worldTime = new Timeline(new KeyFrame(Duration.millis(20), (ActionEvent event) -> {
            Game.getInstance().timeTick();           
        })); 
        worldTime.setCycleCount(Timeline.INDEFINITE);
        worldTime.play();          
    }
    
     public void setBackground(String paneId){
        root.setId(paneId);
    }

    public void stopWorldTime() {
        
        worldTime.stop();
    }  
    public void pauseWorldTime(){
        worldTime.pause();
    }
    public void resumeWorldTime(){
        worldTime.play();
    }

    public void showMenuButtonWithText(String textOnButton) {
        menuButton.setText(textOnButton);
        menuButton.setVisible(true);
    }

    public void showEnergyLabelWithSeconds(int miliseconds) {

        int seconds = ((miliseconds / 1000));
        energyLabel.setText("Time Left: "+String.valueOf(seconds));
    }

    public void showLifeLabel(int life) {
        lifeLabel.setText("Life: "+String.valueOf(life));
    }

    public void showHitPointsLabel(int hitpoints) {
        hitPointsLabel.setText("Hitpoints: "+String.valueOf(hitpoints));
    }
    
    public void showEnemyHP(int hitpoints){
        if(hitpoints>=0){
            enemyHitpointsLabel.setVisible(true);
            enemyHitpointsLabel.setText("Enemy Life: "+String.valueOf(hitpoints));
        }
        
    }
    
    public void showScoreLabel(){
        scoreLabel.setText("Score: "+String.valueOf(Game.getInstance().getScore()));
    }
    
    public void showShurikenLabel(){
        shurikenLabel.setText("Shuriken: "+String.valueOf(Game.getInstance().master.getAmmo()));
    }

    public void addImageView(ImageView imageView) {
        root.getChildren().add(imageView);
    }

    public void removeImageView(ImageView imageView) {
        root.getChildren().remove(imageView);
    }
    
    public void updateFromState(GameState gameState){
        switch (gameState){
            case TitleScreen:                     
                setBackground("pane_title");
                continueButton.setVisible(false);
                break;
            case PlayingOne:
                scoreLabel.setLayoutX(0);
                scoreLabel.setLayoutY(78);
                mainThemeMusic("stop");
                setBackground("pane");
                startWorldTime();
                mainThemeMusic("mainTheme");
                Game.getInstance().createMaster();
                Game.getInstance().createEnemies();
                Game.getInstance().addLevelOneEnemies();
                menuButton.setVisible(false);
                break;
            case PlayingTwo:
                continueButton.setVisible(false);
                resumeWorldTime();   
                mainThemeMusic("mainTheme");
                setBackground("pane_lvlTwoScreenOne");
                Game.getInstance().getHeros().get(0).setX(0);
                Game.getInstance().createEnemies();
                Game.getInstance().setAddEnemies(true);
                Game.getInstance().setTime(180000);
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS());
                menuButton.setVisible(false);
                break;
            case PlayingThree:
                continueButton.setVisible(false);
                resumeWorldTime(); 
                mainThemeMusic("mainTheme");
                setBackground("pane_lvlThreeScreenOne");
                Game.getInstance().getHeros().get(0).setX(1300);
                Game.getInstance().createEnemies();
                Game.getInstance().setAddEnemies(true);
                Game.getInstance().setTime(180000);
                Game.getInstance().master.setHitpoints(Game.getInstance().master.getMAXIMUM_HITPOINTS());
                menuButton.setVisible(false);
                break;                 
            case LevelComplete:
                setBackground("pane_levelcomplete");
                mainThemeMusic("stop");          
                Game.getInstance().calculateScore(Game.getInstance().getTime());//calculate end of stage score
                showScoreLabel();
                pauseWorldTime();
                mainThemeMusic("lvlCompletetheme");
                continueButton.setVisible(true);
                break;
            case GameOver:
                mainThemeMusic("stop");
                setBackground("pane_gameover");    
                mainThemeMusic("gameOverTheme");
                showMenuButtonWithText("Press Button to restart Game");
                stopWorldTime();
                break;
            case FinalScreen:
                lifeLabel.setVisible(false);
                hitPointsLabel.setVisible(false);
                energyLabel.setVisible(false);
                scoreLabel.setLayoutX(600);
                scoreLabel.setLayoutY(384);
                scoreLabel.setVisible(true);
                shurikenLabel.setVisible(false);
                menuButton.setVisible(true);  
                continueButton.setVisible(false);
                mainThemeMusic("stop");
                mainThemeMusic("EndMusicTheme");
                setBackground("pane_finalscreen");
                showMenuButtonWithText("Press Button to restart Game");
                stopWorldTime();
                break;
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Gui getInstance() {
        return gui;
    }

    public int getHeight() {
        return (int) root.getHeight();
    }

    public int getWidth() {
        return (int) root.getWidth();
    }

    public void mainThemeMusic(String theme)
    {
        switch (theme){
            case "mainTheme":
                try
                { 
                   mainThemePlayer.play(); 
                }
                catch (Exception e)
                {
                    System.out.println( e.getMessage() );
                    System.exit(0);
                }    
                break;
            case "introTheme":
                try
                {     
                    introPlayer.play();
                }
                catch (Exception e)
                {
                    System.out.println( e.getMessage() );
                    System.exit(0);
                }
                break;
            case "gameOverTheme":
                try
                {   
                    gameOverPlayer.play();
                }
                catch (Exception e)
                {
                    System.out.println( e.getMessage() );
                    System.exit(0);
                }
                break;
            case "lvlCompletetheme":
                 try
                { 
                    MediaPlayer lvlCompletePlayer = new MediaPlayer(lvlCompleteTheme);
                    lvlCompletePlayer.play();
                }
                catch (Exception e)
                {
                    System.out.println( e.getMessage() );
                    System.exit(0);
                }
                break;
                case "EndMusicTheme":
                 try
                { 
                    
                    lvlCompletePlayer.play();
                }
                catch (Exception e)
                {
                    System.out.println( e.getMessage() );
                    System.exit(0);
                }
                break;
            case "stop":
                mainThemePlayer.stop();
                introPlayer.stop();
                gameOverPlayer.stop();
                lvlCompletePlayer.stop();
                break;
        }
            
    }  
    
    public void punchFX(){
        Media audioFile = new Media( getClass().getResource("/resources/punch.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }        
    }
    
    public void kickFX(){
        Media audioFile = new Media( getClass().getResource("/resources/kick.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }        
    }
    
    public void knifeFX(){
        Media audioFile = new Media( getClass().getResource("/resources/knife.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }      
    }
    
    public void stabFX(){
        Media audioFile = new Media( getClass().getResource("/resources/knife_stab.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }     
    }
    
    public void painFX(){
         Media audioFile = new Media( getClass().getResource("/resources/malehit.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }     
    }
    
    public void fireballFX(){
        Media audioFile = new Media( getClass().getResource("/resources/fireball.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }   
    }
    
    public void explosionFX(){
        Media audioFile = new Media( getClass().getResource("/resources/explosion.mp3").toString() );
        try
        {
            soundFX = new MediaPlayer(audioFile);
            soundFX.play();
        }
        catch (Exception e)
        {
            System.out.println( e.getMessage() );
            System.exit(0);
        }   
    }

}
