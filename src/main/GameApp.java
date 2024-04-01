import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.effect.Glow;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import java.awt.Desktop;
import java.net.URISyntaxException;
import java.io.*;
import java.util.*;
import java.util.Random;
import javax.sound.sampled.*;
import java.net.URI;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import selfish.*;
import selfish.deck.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.input.KeyCode;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.scene.layout.Background;
import javafx.scene.control.CheckBox;

public class GameApp extends Application {

    private int audioButtonCount = 0;
    private boolean buttonsVisible;
    private boolean onlyTwoPlayers;
    private boolean usefulJunk_bool;
    private boolean playerBreatheKillsPlayer;
    private int sel = 0;
    private int sel2 = 0;
    private int sel3 = 0;
    private int sel4 = 0;
    private Astronaut chosen_ast = null;
    private Card chosen1 = null;
    private Card chosen = null;
    private Astronaut victim = null;
    private Label l1 = new Label();
    private Label l2 = new Label();
    private Label l3 = new Label();
    private Label l4 = new Label();
    private Label l5 = new Label();

    public void start(Stage stage) throws Exception {
        // audio(true);
        File file = new File(("..\\FX\\src\\main\\Music\\daylight-14872.wav"));
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        clip.start();

        Image img = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\268e0b01144607b38cec0309896893c7.png"));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(775);
        imgView.setFitWidth(1280);

        Image icon = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\71Z6u8VG8EL._AC_SL1500_.jpg"));

        Label l = new Label("S\tE\tL\tF\tI\tS\tH");
        Font font = new Font(40);
        l.setFont(font);
        Color grey = Color.gray(0.5);
        l.setTextFill(grey);
        l.setEffect(new Glow(50));

        Button startbtn = new Button("Start Game");
        startbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        startbtn.setOnMouseEntered(e -> startbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        startbtn.setOnMouseExited(e -> startbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        startbtn.setOnAction(e -> {
            click_audio();
            try {
                AddPlayer(clip);
            } catch (Exception exception) {
                Alert alrt = new Alert(Alert.AlertType.ERROR);
                alrt.setContentText(exception.getMessage());
                alrt.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        click_audio(); // play sound on OK button click
                        // Your code to perform on OK button click
                    }
                });
            }
        });
        Button loadbtn = new Button("Load Game");
        loadbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        loadbtn.setOnMouseEntered(e -> loadbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        loadbtn.setOnMouseExited(e -> loadbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        loadbtn.setOnAction(e -> {
            click_audio();
        });
        Button helpbtn = new Button("Help");
        helpbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        helpbtn.setOnMouseEntered(e -> helpbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        helpbtn.setOnMouseExited(e -> helpbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        helpbtn.setOnAction(e -> {
            click_audio();
            help();
        });
        Button exitbtn = new Button("Exit");
        exitbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        exitbtn.setOnMouseEntered(e -> exitbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        exitbtn.setOnMouseExited(e -> exitbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        exitbtn.setOnAction(e -> {
            click_audio();
            stage.close();
        });
        Button mutebtn = new Button("Mute Audio");
        mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        mutebtn.setOnMouseEntered(e -> mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        mutebtn.setOnMouseExited(e -> mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        mutebtn.setOnAction(e -> {
            audioButtonCount++;
            if (audioButtonCount % 2 == 0) {
                clip.start();
                mutebtn.setText("Mute Audio");
            } else {
                clip.stop();
                mutebtn.setText("Unmute Audio");
            }
        });

        VBox vb = new VBox(5);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(50);
        vb.getChildren().addAll(startbtn, loadbtn, mutebtn, helpbtn, exitbtn);
        BorderPane bp1 = new BorderPane();
        BorderPane bp2 = new BorderPane();
        bp1.setTop(l);
        bp2.setLeft(vb);
        bp1.setAlignment(l, Pos.TOP_LEFT);
        bp2.setAlignment(vb, Pos.TOP_LEFT);
        bp1.setPadding(new Insets(80, 0, 0, 80));
        bp2.setPadding(new Insets(0, 0, 0, 300));
        StackPane sp = new StackPane(imgView, bp1, bp2);
        Scene sc = new Scene(sp);
        stage.getIcons().add(icon);
        stage.setScene(sc);
        stage.setMaximized(true);
        stage.setTitle("Selfish Space Edition");
        stage.show();
    }

    public void help() {
        try {
            Desktop.getDesktop().browse(new URI(
                    "https://www.board-game.co.uk/selfish-space-edition-review/#:~:text=Selfish%20Space%20is%20a%202,and%20it%20is%20game%20over."));
        } catch (IOException | URISyntaxException exp) {
            exp.printStackTrace();
        }
    }

    // public void muteaudio(Button btn) throws Exception{
    // audioButtonCount++;
    // if (audioButtonCount % 2 == 0) {
    // audio(true);
    // btn.setText("Mute Audio");
    // } else {
    // audio(false);
    // btn.setText("Unmute Audio");
    // }
    // }

    public void click_audio() {
        try {
            File file = new File("..\\FX\\src\\main\\Music\\mixkit-negative-game-notification-249.wav");
            AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audiostream);
            clip.start();

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException audio_ex) {
            System.out.println("Error playing audio: " + audio_ex.getMessage());
        }
    }

    public void audio(boolean play) throws Exception {
        File file = new File("..\\FX\\src\\main\\Music\\daylight-14872.wav");
        AudioInputStream audiostream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audiostream);
        if (play == true) {
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);
        } else {
            clip.stop();
        }

    }

    public void AddPlayer(Clip clip) throws Exception {
        GridPane grd = new GridPane();
        Pane p = new Pane();
        Stage stg = new Stage();
        Rectangle rectangle = new Rectangle(500, 290);
        List<String> players = new ArrayList<String>();
        List<String> name_list = new ArrayList<String>();

        Image img1 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\268e0b01144607b38cec0309896893c7.png"));
        ImageView imgView1 = new ImageView(img1);
        imgView1.setPreserveRatio(true);

        Image icon2 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\71Z6u8VG8EL._AC_SL1500_.jpg"));
        Random r = new Random();
        GameEngine gm = new GameEngine(r.nextLong(),
                "..\\FX\\io\\ActionCards.txt",
                "..\\FX\\io\\SpaceCards.txt");

        Text hd = new Text("Astronauts Name");
        Label name_label = new Label("Astronaut Name:");
        Font font2 = new Font(19);
        name_label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-family: Serif;");
        name_label.setFont(font2);
        grd.setConstraints(name_label, 1, 0);
        TextField txt_name = new TextField();
        grd.setConstraints(txt_name, 3, 0);
        grd.setAlignment(Pos.CENTER);
        txt_name.setPromptText("Enter Player's Name");
        grd.getChildren().addAll(name_label, txt_name);
        grd.setHgap(15);

        Button playbtn = new Button("Play");
        playbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        playbtn.setOnMouseEntered(e -> playbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        playbtn.setOnMouseExited(e -> playbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        playbtn.setOnAction(e -> {
            click_audio();
            try {
                try {
                    if (players.size() < 2)
                        throw new Exception("Need atleast 2 players to play game...please add players!");
                    play_game(gm, players, clip);
                    stg.close();
                } catch (Exception player_exception) {
                    Alert alt = new Alert(Alert.AlertType.ERROR);
                    alt.setContentText(player_exception.getMessage());
                    alt.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            click_audio(); // play sound on OK button click
                            // Your code to perform on OK button click
                        }
                    });
                }
            } catch (Exception ept) {
                Alert alt = new Alert(Alert.AlertType.ERROR);
                alt.setContentText(ept.getMessage());
                alt.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        click_audio(); // play sound on OK button click
                        // Your code to perform on OK button click
                    }
                });
            }
        });
        Button mutebtn = new Button("Mute Audio");
        mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        mutebtn.setOnMouseEntered(e -> mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        mutebtn.setOnMouseExited(e -> mutebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        mutebtn.setOnAction(e -> {
            audioButtonCount++;
            if (audioButtonCount % 2 == 0) {
                clip.start();
                mutebtn.setText("Mute Audio");
            } else {
                clip.stop();
                mutebtn.setText("Unmute Audio");
            }
        });

        Button homebtn = new Button("Home");
        homebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        homebtn.setOnMouseEntered(e -> homebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        homebtn.setOnMouseExited(e -> homebtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        homebtn.setOnAction(e -> {
            click_audio();
            try {
                stg.close();
            } catch (Exception ept) {
                Alert alt = new Alert(Alert.AlertType.ERROR);
                alt.setContentText(ept.getMessage());
                alt.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        click_audio(); // play sound on OK button click
                        // Your code to perform on OK button click
                    }
                });
            }
        });
        Button addbtn = new Button("Add Player");
        addbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
        addbtn.setOnMouseEntered(e -> addbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
        addbtn.setOnMouseExited(e -> addbtn.setStyle(
                "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));
        addbtn.setOnAction(e -> {
            click_audio();
            try {
                if (txt_name.getText().equals("")) {
                    throw new Exception("Please enter valid name");
                }
                if (players.contains(txt_name.getText())) {
                    throw new Exception("Player already exists!");
                }
                Alert msg = new Alert(Alert.AlertType.INFORMATION);
                gm.addPlayer(txt_name.getText());
                players.add(txt_name.getText());
                msg.setTitle("Player added messsage");
                msg.setContentText("Player added: " + txt_name.getText());
                msg.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        click_audio(); // play sound on OK button click
                        // Your code to perform on OK button click
                    }

                });
                int count_a = players.size();
                Font f1 = new Font(20);
                name_list.add(txt_name.getText());
                Text text = new Text((count_a) + ". " + txt_name.getText());
                text.setFill(Color.WHITE);
                text.setFont(f1);
                p.getChildren().add(text);
                text.relocate(430, 530 + (count_a * 40));
            } catch (Exception exp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(exp.getMessage());
                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        click_audio(); // play sound on OK button click
                        // Your code to perform on OK button click
                    }
                });
            }
            txt_name.setText("");
        });
        Button removebtn = new Button("Remove Player");
        removebtn.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;");
        removebtn.setOnMouseEntered(
                e -> removebtn.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a;"));
        removebtn.setOnMouseExited(
                e -> removebtn.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;"));
        removebtn.setOnAction(e -> {
            click_audio();
            int count_r = players.size();
            if (count_r >= 1) {

                Alert confrm = new Alert(Alert.AlertType.CONFIRMATION);
                confrm.setTitle("Confirmation Dialog");
                confrm.setHeaderText("Are you sure you want to Remove player");
                confrm.setContentText("This action cannot be undone.");
                ButtonType buttonTypeOk = new ButtonType("Yes", ButtonData.OK_DONE);
                Button okButton = (Button) confrm.getDialogPane().lookupButton(buttonTypeOk);
                // confrm.getButtonTypes().setAll(buttonTypeOk,ButtonType.CANCEL);

                Optional<ButtonType> result = confrm.showAndWait();

                if (result.get() == ButtonType.OK) {
                    p.getChildren().remove(p.getChildren().size() - 1);
                    players.remove(count_r - 1);
                    Alert msg1 = new Alert(Alert.AlertType.INFORMATION);
                    msg1.setTitle("Player removed messsage");
                    msg1.setContentText("Player Removed: " + name_list.get(count_r - 1));
                    msg1.setTitle("Information Dialog");
                    msg1.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            click_audio(); // play sound on OK button click
                            // Your code to perform on OK button click
                        }
                    });

                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No more players to remove!!");
                alert.showAndWait();
            }
        });

        rectangle.setFill(Color.BLACK);
        p.getChildren().addAll(txt_name, playbtn, homebtn, addbtn, removebtn, mutebtn, name_label, rectangle, hd);
        hd.relocate(550, 520);
        hd.setFill(Color.WHITE);
        Font f = new Font(30);
        hd.setFont(f);
        rectangle.relocate(410, 485);
        playbtn.relocate(300, 220);
        mutebtn.relocate(300, 300);
        homebtn.relocate(300, 380);
        name_label.relocate(510, 299);
        txt_name.relocate(660, 300);
        addbtn.relocate(880, 245);
        removebtn.relocate(880, 355);

        StackPane stk2 = new StackPane(imgView1, p);
        Scene sc2 = new Scene(stk2);
        stg.setScene(sc2);
        stg.getIcons().add(icon2);
        stg.setMaximized(true);
        stg.setTitle("Selfish Space Edition");
        stg.show();
    }
    // =================================================================PLAY
    // GAME============================================================================================

    public void play_game(GameEngine game, List<String> players, Clip clip) {
        game.startGame();
        try {
            List<Astronaut> players1 = game.getAllPlayers();
            game.startTurn();
            Pane p2 = new Pane();
            Stage stg2 = new Stage();

            Image img2 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\wp2939697.jpg"));
            ImageView imgView2 = new ImageView(img2);
            imgView2.setPreserveRatio(true);

            Image img_ship = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\astronauts_ship.jpeg"));
            ImageView img_shipView = new ImageView(img_ship);
            img_shipView.setFitWidth(200);
            img_shipView.setFitHeight(105);

            Image img_Discard = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\GAME DISCARD.png"));
            ImageView img_DiscardView = new ImageView(img_Discard);
            img_DiscardView.setPreserveRatio(true);
            img_DiscardView.setFitWidth(130);
            img_DiscardView.setFitHeight(130);

            Image img_Space_Discard = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\SPACE DISCARD.png"));
            ImageView img_Space_DiscardView = new ImageView(img_Space_Discard);
            img_Space_DiscardView.setPreserveRatio(true);
            img_Space_DiscardView.setFitWidth(130);
            img_Space_DiscardView.setFitHeight(130);

            Image img_Deck = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\GAME DECK.png"));
            ImageView img_DeckView = new ImageView(img_Deck);
            img_DeckView.setPreserveRatio(true);
            img_DeckView.setFitWidth(130);
            img_DeckView.setFitHeight(130);

            Image icon3 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\71Z6u8VG8EL._AC_SL1500_.jpg"));

            Button GameDeckbtn = new Button();
            GameDeckbtn.setStyle("-fx-background-color: transparent;");
            Button GameDiscardbtn = new Button();
            GameDiscardbtn.setStyle("-fx-background-color: transparent;");
            Button SpaceDiscardbtn = new Button();
            SpaceDiscardbtn.setStyle("-fx-background-color: transparent;");
            Button ViewHandbtn = new Button("View Hand");
            ViewHandbtn.setStyle("-fx-background-color: #2f2f2f; -fx-text-fill: #dfdfdf;");
            Button ActionCardbtn = new Button("Action card");
            ActionCardbtn.setStyle("-fx-background-color: #2f2f2f; -fx-text-fill: #dfdfdf;");
            Button Breathebtn = new Button("Breathe");
            Breathebtn.setStyle("-fx-background-color: #2f2f2f; -fx-text-fill: #dfdfdf;");
            Button Travelbtn = new Button("Travel");
            Travelbtn.setStyle("-fx-background-color: #2f2f2f; -fx-text-fill: #dfdfdf;");
            Button EndTurnbtn = new Button("End turn");
            EndTurnbtn.setStyle("-fx-background-color: #2f2f2f; -fx-text-fill: #dfdfdf;");

            EndTurnbtn.setDisable(true);
            Breathebtn.setDisable(true);
            Travelbtn.setDisable(true);
            ActionCardbtn.setDisable(true);
            GameDiscardbtn.setDisable(true);
            SpaceDiscardbtn.setDisable(true);
            EndTurnbtn.setDisable(true);

            Button player1 = new Button();
            player1.setStyle("-fx-border-radius: 10;");
            Button player2 = new Button();
            player2.setStyle("-fx-border-radius: 10;");
            Button player3 = new Button();
            player3.setStyle("-fx-border-radius: 10;");
            Button player4 = new Button();
            player4.setStyle("-fx-border-radius: 10;");
            Button player5 = new Button();
            player5.setStyle("-fx-border-radius: 10;");

            List<Button> playerCard1 = new ArrayList<>();
            List<Button> playerCard2 = new ArrayList<>();
            List<Button> playerCard3 = new ArrayList<>();
            List<Button> playerCard4 = new ArrayList<>();
            List<Button> playerCard5 = new ArrayList<>();

            Image img_player1 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\ast_blue.png"));
            ImageView img_player1View = new ImageView(img_player1);
            img_player1View.setStyle("-fx-border-radius: 10;");
            // img_player1View.setPreserveRatio(true);
            img_player1View.setFitWidth(160);
            img_player1View.setFitHeight(90);
            // img_player1View.setStyle("-fx-border-color: transparent; -fx-border-radius:
            // 5px; -fx-border-width: 1px;");

            Image img_player2 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\Astronaut_green.png"));
            ImageView img_player2View = new ImageView(img_player2);
            img_player2View.setStyle("-fx-border-radius: 10;");
            // img_player2View.setPreserveRatio(true);
            img_player2View.setFitWidth(160);
            img_player2View.setFitHeight(90);

            Image img_player3 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\Astronaut_purple.png"));
            ImageView img_player3View = new ImageView(img_player3);
            img_player3View.setStyle("-fx-border-radius: 10;");
            // img_player3View.setPreserveRatio(true);
            img_player3View.setFitWidth(160);
            img_player3View.setFitHeight(90);

            Image img_player4 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\Astronaut_red.png"));
            ImageView img_player4View = new ImageView(img_player4);
            img_player4View.setStyle("-fx-border-radius: 10;");
            // img_player4View.setPreserveRatio(true);
            img_player4View.setFitWidth(160);
            img_player4View.setFitHeight(90);

            Image img_player5 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\Astronaut_yellow.png"));
            ImageView img_player5View = new ImageView(img_player5);
            img_player5View.setStyle("-fx-border-radius: 10;");
            // img_player5View.setPreserveRatio(true);
            img_player5View.setFitWidth(160);
            img_player5View.setFitHeight(90);

            Image dp1 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\dead.jpeg"));
            ImageView dpimv1 = new ImageView(dp1);
            dpimv1.setFitWidth(160);
            dpimv1.setFitHeight(90);

            Image dp2 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\dead.jpeg"));
            ImageView dpimv2 = new ImageView(dp2);
            dpimv2.setFitWidth(160);
            dpimv2.setFitHeight(90);

            Image dp3 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\dead.jpeg"));
            ImageView dpimv3 = new ImageView(dp3);
            dpimv3.setFitWidth(160);
            dpimv3.setFitHeight(90);

            Image dp4 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\dead.jpeg"));
            ImageView dpimv4 = new ImageView(dp4);
            dpimv4.setFitWidth(160);
            dpimv4.setFitHeight(90);

            Image dp5 = new Image(new FileInputStream("..\\FX\\src\\main\\Images\\dead.jpeg"));
            ImageView dpimv5 = new ImageView(dp5);
            dpimv5.setFitWidth(160);
            dpimv5.setFitHeight(90);

            player1.setGraphic(img_player1View);
            player1.setStyle("-fx-background-color: transparent;");
            player2.setGraphic(img_player2View);
            player2.setStyle("-fx-background-color: transparent;");
            player3.setGraphic(img_player3View);
            player3.setStyle("-fx-background-color: transparent;");
            player4.setGraphic(img_player4View);
            player4.setStyle("-fx-background-color: transparent;");
            player5.setGraphic(img_player5View);
            player5.setStyle("-fx-background-color: transparent;");
            GameDiscardbtn.setGraphic(img_DiscardView);
            GameDeckbtn.setGraphic(img_DeckView);
            SpaceDiscardbtn.setGraphic(img_Space_DiscardView);
            VBox vbBtn=new VBox(5);
            vbBtn.getChildren().addAll(ViewHandbtn,ActionCardbtn,Breathebtn,Travelbtn,EndTurnbtn);
            vbBtn.setSpacing(30);

            player1.setVisible(true);
            player2.setVisible(true);
            player3.setVisible(false);
            player4.setVisible(false);
            player5.setVisible(false);
            int count = game.getFullPlayerCount();
            p2.getChildren().addAll(img_shipView, GameDiscardbtn, GameDeckbtn, SpaceDiscardbtn, vbBtn,player1, player2, player3, player4, player5);

            if (count == 2) {
                player1.relocate(350, 620);
                Text text_player1 = new Text(players.get(0));
                l1.setText(text_player1.getText());
                p2.getChildren().add(text_player1);
                text_player1.relocate(360, 750);
                text_player1.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");

                player2.relocate(750, 620);
                Text text_player2 = new Text(players.get(1));
                l2.setText(text_player2.getText());
                p2.getChildren().add(text_player2);
                text_player2.relocate(760, 750);
                text_player2.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player1.setFill(Color.WHITE);
                text_player2.setFill(Color.WHITE);
                onlyTwoPlayers = true;
            }

            // System.out.println(text_player1,text_player2);
            if (count == 3) {
                onlyTwoPlayers = false;
                player3.setVisible(true);

                Text text_player1 = new Text(players.get(0));
                Text text_player2 = new Text(players.get(1));
                Text text_player3 = new Text(players.get(2));

                l1.setText(text_player1.getText());
                l2.setText(text_player2.getText());
                l3.setText(text_player3.getText());

                p2.getChildren().addAll(text_player1, text_player2, text_player3);
                text_player1.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player2.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player3.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");

                text_player1.setFill(Color.WHITE);
                text_player2.setFill(Color.WHITE);
                text_player3.setFill(Color.WHITE);

                text_player1.relocate(300, 750);
                text_player2.relocate(550, 750);
                text_player3.relocate(800, 750);

                player1.relocate(280, 620);
                player2.relocate(530, 620);
                player3.relocate(780, 620);
            }
            if (count == 4) {
                player3.setVisible(true);
                player4.setVisible(true);

                Text text_player1 = new Text(players.get(0));
                Text text_player2 = new Text(players.get(1));
                Text text_player3 = new Text(players.get(2));
                Text text_player4 = new Text(players.get(3));

                l1.setText(text_player1.getText());
                l2.setText(text_player2.getText());
                l3.setText(text_player3.getText());
                l4.setText(text_player4.getText());

                text_player1.setStyle("-fx-font: 20px \"serif\";");
                text_player1.setFill(Color.WHITE);
                text_player2.setStyle("-fx-font: 20px \"serif\";");
                text_player2.setFill(Color.WHITE);
                text_player3.setStyle("-fx-font: 20px \"serif\";");
                text_player3.setFill(Color.WHITE);
                text_player4.setStyle("-fx-font: 20px \"serif\";");
                text_player4.setFill(Color.WHITE);

                p2.getChildren().addAll(text_player1, text_player2, text_player3, text_player4);

                text_player1.relocate(250, 750);
                text_player2.relocate(430, 750);
                text_player3.relocate(630, 750);
                text_player4.relocate(820, 750);

                player1.relocate(230, 620);
                player2.relocate(420, 620);
                player3.relocate(610, 620);
                player4.relocate(800, 620);

                onlyTwoPlayers = false;
            }
            if (count == 5) {
                player3.setVisible(true);
                player4.setVisible(true);
                player5.setVisible(true);

                Text text_player1 = new Text(players.get(0));
                Text text_player2 = new Text(players.get(1));
                Text text_player3 = new Text(players.get(2));
                Text text_player4 = new Text(players.get(3));
                Text text_player5 = new Text(players.get(4));

                l1.setText(text_player1.getText());
                l2.setText(text_player2.getText());
                l3.setText(text_player3.getText());
                l4.setText(text_player4.getText());
                l5.setText(text_player5.getText());

                text_player1.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player2.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player3.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player4.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");
                text_player5.setStyle("-fx-background-color: white; -fx-font: 20px \"serif\";");

                text_player1.setFill(Color.WHITE);
                text_player2.setFill(Color.WHITE);
                text_player3.setFill(Color.WHITE);
                text_player4.setFill(Color.WHITE);
                text_player5.setFill(Color.WHITE);

                p2.getChildren().addAll(text_player1, text_player2, text_player3, text_player4, text_player5);

                text_player1.relocate(240, 750);
                text_player2.relocate(400, 750);
                text_player3.relocate(560, 750);
                text_player4.relocate(720, 750);
                text_player4.relocate(880, 750);

                img_player1View.setFitWidth(140);
                img_player2View.setFitWidth(140);
                img_player3View.setFitWidth(140);
                img_player4View.setFitWidth(140);
                img_player5View.setFitWidth(140);

                player1.relocate(220, 620);
                player2.relocate(380, 620);
                player3.relocate(540, 620);
                player4.relocate(700, 620);
                player5.relocate(860, 620);
                onlyTwoPlayers = false;
            }

            GameDeckbtn.relocate(10, 50);
            GameDiscardbtn.relocate(10, 140);
            SpaceDiscardbtn.relocate(10, 230);
            vbBtn.relocate(25,370);
            img_shipView.relocate(1070, 50);

            ViewHandbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
            ViewHandbtn.setOnMouseEntered(e -> ViewHandbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
            ViewHandbtn.setOnMouseExited(e -> ViewHandbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));

            ActionCardbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
            ActionCardbtn.setOnMouseEntered(e -> ActionCardbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
            ActionCardbtn.setOnMouseExited(e -> ActionCardbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));

            Breathebtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
            Breathebtn.setOnMouseEntered(e -> Breathebtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
            Breathebtn.setOnMouseExited(e -> Breathebtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));

            Travelbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
            Travelbtn.setOnMouseEntered(e -> Travelbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
            Travelbtn.setOnMouseExited(e -> Travelbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));

            EndTurnbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;");
            EndTurnbtn.setOnMouseEntered(e -> EndTurnbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a; -fx-font-family: Serif;"));
            EndTurnbtn.setOnMouseExited(e -> EndTurnbtn.setStyle(
                    "-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white; -fx-font-family: Serif;"));

            StackPane stk3 = new StackPane(imgView2, p2);
            Scene sc3 = new Scene(stk3);
            stg2.setScene(sc3);
            stg2.getIcons().add(icon3);
            stg2.setMaximized(true);
            stg2.setTitle("Selfish Space Edition");
            stg2.show();

            ViewHandbtn.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.NONE, game.getCurrentPlayer().getHandStr(), ButtonType.OK);
                alert.setTitle("Astronaut's Hand");
                alert.showAndWait();
            });

            ActionCardbtn.setOnAction(e -> {
                Stage action_stage = new Stage();
                List<Card> actions = game.getCurrentPlayer().getActions();
                List<CheckBox> chk = new ArrayList<>();
                VBox vb = new VBox(10);
                for (Card i : actions) {
                    if (i.toString().equals(GameDeck.SHIELD) == false)
                        chk.add(new CheckBox(i.toString()));
                }
                vb.getChildren().addAll(chk);
                Button play_action = new Button("Use");
                Button end_action = new Button("End");
                HBox hb = new HBox(3);
                hb.setSpacing(20);
                hb.setAlignment(Pos.CENTER);
                hb.getChildren().addAll(play_action, end_action);
                for (CheckBox i : chk) {
                    i.selectedProperty().addListener((o, oldV, newV) -> {
                        if (newV == true) {
                            chosen = actions.get(chk.indexOf(i));
                            sel2++;
                            if (sel2 > 0) {
                                for (CheckBox j : chk) {
                                    if (i != j && j.isSelected() == false)
                                        j.setDisable(true);
                                }
                            }
                        } else {
                            // chosen = null;
                            sel2--;
                            if (sel2 <= 1) {
                                for (CheckBox j : chk) {
                                    if (i != j)
                                        j.setDisable(false);
                                }
                            }
                        }
                    });
                }
                vb.getChildren().add(hb);
                vb.setBackground(Background.fill(Color.TRANSPARENT));
                Scene sce = new Scene(vb, 200, 250, Color.WHITE);
                // sce.getStylesheets().add("myStyles5.css");
                action_stage.setScene(sce);
                action_stage.show();

                end_action.setOnAction(a -> {
                    action_stage.close();
                });

                play_action.setOnAction(a -> {
                    System.out.println(chosen);
                    if (chosen.toString().equals(GameDeck.HOLE_IN_SUIT)) {
                        action_stage.close();
                        Stage stage_hole = new Stage();
                        List<CheckBox> chk2 = new ArrayList<>();
                        List<Astronaut> ast = game.getActivePlayers();
                        Button hack = new Button("Hack");
                        VBox vb2 = new VBox(10);
                        for (Astronaut i : ast) {
                            chk2.add(new CheckBox(i.toString()));
                        }
                        vb2.getChildren().addAll(chk2);
                        vb2.getChildren().add(hack);
                        for (CheckBox i : chk2) {
                            i.selectedProperty().addListener((o, oldV, newV) -> {
                                if (newV == true) {
                                    chosen_ast = ast.get(chk2.indexOf(i));
                                    sel2++;
                                    if (sel2 > 0) {
                                        for (CheckBox j : chk2) {
                                            if (i != j && j.isSelected() == false)
                                                j.setDisable(true);
                                        }
                                    }
                                } else {
                                    chosen_ast = null;
                                    sel2--;
                                    if (sel2 <= 1) {
                                        for (CheckBox j : chk2) {
                                            if (i != j)
                                                j.setDisable(false);
                                        }
                                    }
                                }
                            });
                        }
                        vb2.setBackground(Background.fill(Color.TRANSPARENT));
                        Scene sce1 = new Scene(vb2, 200, 250, Color.WHITE);
                        // sce.getStylesheets().add("myStyles5.css");
                        stage_hole.setScene(sce1);
                        stage_hole.show();

                        hack.setOnAction(d -> {
                            stage_hole.close();
                            Oxygen o2 = chosen_ast.siphon();
                            Alert alert_hack = new Alert(Alert.AlertType.INFORMATION);
                            alert_hack.setContentText("From player " + chosen_ast + o2 + " has been hacked");
                            alert_hack.setTitle("Hole In Suit");
                            alert_hack.showAndWait();

                        });
                    }

                    if (chosen.toString().equals(GameDeck.HACK_SUIT)) {
                        action_stage.close();
                        Stage next_stage = new Stage();
                        List<CheckBox> chk2 = new ArrayList<>();
                        List<Astronaut> ast = game.getActivePlayers();
                        Button next = new Button("Next");
                        Button end = new Button("End Action");
                        HBox hb2 = new HBox(3);
                        hb2.setSpacing(20);
                        hb2.setAlignment(Pos.CENTER);
                        hb2.getChildren().addAll(next, end);
                        VBox vb3 = new VBox(10);
                        for (Astronaut i : ast) {
                            chk2.add(new CheckBox(i.toString()));
                        }
                        vb3.getChildren().addAll(chk2);
                        vb3.getChildren().add(hb2);
                        for (CheckBox i : chk2) {
                            i.selectedProperty().addListener((o, oldV, newV) -> {
                                if (newV == true) {
                                    chosen_ast = ast.get(chk2.indexOf(i));
                                    sel2++;
                                    if (sel2 > 0) {
                                        for (CheckBox j : chk2) {
                                            if (i != j && j.isSelected() == false)
                                                j.setDisable(true);
                                        }
                                    }
                                } else {
                                    chosen_ast = null;
                                    sel2--;
                                    if (sel2 <= 1) {
                                        for (CheckBox j : chk2) {
                                            if (i != j)
                                                j.setDisable(false);
                                        }
                                    }
                                }
                            });
                        }
                        vb3.setBackground(Background.fill(Color.TRANSPARENT));
                        Scene sce12 = new Scene(vb3, 200, 250, Color.WHITE);
                        // sce.getStylesheets().add("myStyles5.css");
                        next_stage.setScene(sce12);
                        next_stage.show();
                        end.setOnAction(g -> {
                            next_stage.close();
                        });
                        next.setOnAction(f -> {
                            sel2 = 0;
                            next_stage.close();
                            Stage next_stage2 = new Stage();
                            List<Card> crds = chosen_ast.getHand();
                            List<CheckBox> chk3 = new ArrayList<>();
                            Button hack2 = new Button("Hack");
                            VBox vb4 = new VBox(10);
                            for (Card i : crds) {
                                chk3.add(new CheckBox(i.toString()));
                            }
                            vb4.getChildren().addAll(chk3);
                            vb4.getChildren().add(hack2);

                            for (CheckBox i : chk3) {
                                i.selectedProperty().addListener((o, oldV, newV) -> {
                                    if (newV == true) {
                                        chosen1 = actions.get(chk3.indexOf(i));
                                        sel4++;
                                        if (sel2 > 0) {
                                            for (CheckBox j : chk) {
                                                if (i != j && j.isSelected() == false)
                                                    j.setDisable(true);
                                            }
                                        }
                                    } else {
                                        // chosen1 = null;
                                        sel4--;
                                        if (sel2 <= 1) {
                                            for (CheckBox j : chk3) {
                                                if (i != j)
                                                    j.setDisable(false);
                                            }
                                        }
                                    }
                                });
                            }

                            vb4.setBackground(Background.fill(Color.TRANSPARENT));
                            Scene sce2 = new Scene(vb4, 250, 350, Color.WHITE);
                            next_stage2.setScene(sce2);
                            next_stage2.show();
                            hack2.setOnAction(xb -> {
                                sel4 = 0;
                                game.getCurrentPlayer().addToHand(chosen1);
                                Alert alert_hack = new Alert(Alert.AlertType.INFORMATION);
                                alert_hack.setContentText("From player " + chosen_ast + chosen1 + " has been hacked");
                                alert_hack.setTitle("Hack card");
                                alert_hack.showAndWait();

                            });
                        });
                    }
                });
                if (chosen.toString().equals(GameDeck.OXYGEN_SIPHON)) {
                    Stage oxygen_stage = new Stage();
                    List<CheckBox> chk4 = new ArrayList<>();
                    List<Astronaut> ast = game.getActivePlayers();
                    Button next2 = new Button("Next");
                    VBox vb3 = new VBox(10);
                    for (Astronaut i : ast) {
                        chk4.add(new CheckBox(i.toString()));
                    }
                    vb3.getChildren().addAll(chk4);
                    vb3.getChildren().add(next2);
                    for (CheckBox i : chk4) {
                        i.selectedProperty().addListener((o, oldV, newV) -> {
                            if (newV == true) {
                                chosen_ast = ast.get(chk4.indexOf(i));
                                sel2++;
                                if (sel2 > 0) {
                                    for (CheckBox j : chk4) {
                                        if (i != j && j.isSelected() == false)
                                            j.setDisable(true);
                                    }
                                }
                            } else {
                                chosen_ast = null;
                                sel2--;
                                if (sel2 <= 1) {
                                    for (CheckBox j : chk4) {
                                        if (i != j)
                                            j.setDisable(false);
                                    }
                                }
                            }
                        });
                    }
                    vb3.setBackground(Background.fill(Color.TRANSPARENT));
                    Scene sce3 = new Scene(vb3, 200, 250, Color.WHITE);
                    oxygen_stage.setScene(sce3);
                    oxygen_stage.show();
                    next2.setOnAction(b -> {
                        game.getCurrentPlayer().addToHand(chosen_ast.siphon());
                        game.getCurrentPlayer().addToHand(chosen_ast.siphon());
                        Alert alert_hack_oxy = new Alert(Alert.AlertType.INFORMATION);
                        alert_hack_oxy.setContentText("From player " + chosen_ast + " 2 Oxygens has been removed");
                        alert_hack_oxy.setTitle("Siphon");
                        alert_hack_oxy.showAndWait();

                    });
                }

                if (chosen.toString().equals(GameDeck.TRACTOR_BEAM)) {
                    Stage tractor_stage = new Stage();
                    List<CheckBox> chk5 = new ArrayList<>();
                    List<Astronaut> ast = game.getActivePlayers();
                    Button next3 = new Button("Next");
                    VBox vb4 = new VBox(10);
                    for (Astronaut i : ast) {
                        chk5.add(new CheckBox(i.toString()));
                    }
                    vb4.getChildren().addAll(chk5);
                    vb4.getChildren().add(next3);
                    for (CheckBox i : chk5) {
                        i.selectedProperty().addListener((o, oldV, newV) -> {
                            if (newV == true) {
                                chosen_ast = ast.get(chk5.indexOf(i));
                                sel2++;
                                if (sel2 > 0) {
                                    for (CheckBox j : chk5) {
                                        if (i != j && j.isSelected() == false)
                                            j.setDisable(true);
                                    }
                                }
                            } else {
                                chosen_ast = null;
                                sel2--;
                                if (sel2 <= 1) {
                                    for (CheckBox j : chk5) {
                                        if (i != j)
                                            j.setDisable(false);
                                    }
                                }
                            }
                        });
                    }
                    vb4.setBackground(Background.fill(Color.TRANSPARENT));
                    Scene sce4 = new Scene(vb4, 200, 250, Color.WHITE);
                    // sce.getStylesheets().add("myStyles5.css");
                    tractor_stage.setScene(sce4);
                    tractor_stage.show();
                    next3.setOnAction(b -> {
                        Card steal_crd = chosen_ast.steal();
                        game.getCurrentPlayer().addToHand(steal_crd);
                        Alert alert_hack_rnd = new Alert(Alert.AlertType.INFORMATION);
                        alert_hack_rnd
                                .setContentText("From player " + chosen_ast + "  " + steal_crd + " has been stolen");
                        alert_hack_rnd.setTitle("Siphon");
                        alert_hack_rnd.showAndWait();

                    });
                }
                if (chosen.toString().equals(GameDeck.LASER_BLAST)) {
                    action_stage.close();
                    Stage victim_stage = new Stage();
                    List<Astronaut> activePlayers = game.getActivePlayers();
                    List<CheckBox> playerBox = new ArrayList<>();
                    for (Astronaut i : activePlayers)
                        playerBox.add(new CheckBox(i.toString()));

                    Button confirmButton = new Button("Next");

                    VBox vb21 = new VBox(6);
                    vb21.setBackground(Background.fill(Color.TRANSPARENT));
                    vb21.getChildren().addAll(playerBox);
                    vb21.getChildren().add(confirmButton);

                    for (CheckBox i : playerBox) {
                        i.selectedProperty().addListener((o, oldV, newV) -> {
                            if (newV == true) {
                                victim = activePlayers.get(playerBox.indexOf(i));
                                sel3++;
                                if (sel3 > 0) {
                                    for (CheckBox j : playerBox) {
                                        if (i != j && j.isSelected() == false)
                                            j.setDisable(true);
                                    }
                                }
                            } else {
                                victim = null;
                                sel3--;
                                if (sel3 <= 1) {
                                    for (CheckBox j : playerBox) {
                                        if (i != j)
                                            j.setDisable(false);
                                    }

                                }
                            }
                        });
                    }

                    Scene scene_victim = new Scene(vb21, 200, 250, Color.WHITE);
                    victim_stage.setScene(scene_victim);
                    victim_stage.show();

                    confirmButton.setOnAction(eve -> {
                        sel3 = 0;
                        victim_stage.close();

                        if (victim.getTrack().size() > 0) {
                            if (victim.hasCard(GameDeck.SHIELD) > 0 && victim.hasMeltedEyeballs() == false) {
                                Stage chooseToPlayShield = new Stage();
                                Button playShield = new Button("Play Shield");
                                Button notPlayShield = new Button("Don't Play Shield");

                                HBox hb34 = new HBox(6);
                                hb34.getChildren().addAll(playShield, notPlayShield);
                                hb34.setBackground(Background.fill(Color.TRANSPARENT));
                                Scene scene89 = new Scene(hb34, 250, 100, Color.BLACK);
                                scene89.getStylesheets().add("myStyles5.css");
                                chooseToPlayShield.setScene(scene89);
                                chooseToPlayShield.show();

                                playShield.setOnAction(even -> {
                                    Alert playingShield = new Alert(Alert.AlertType.INFORMATION);
                                    playingShield.setContentText(victim.toString()
                                            + " has played shield!\nAction Card has no effect!");
                                    playingShield.showAndWait();
                                    game.getGameDiscard().add(victim.hack(GameDeck.SHIELD));
                                    chooseToPlayShield.close();
                                });

                                notPlayShield.setOnAction(even -> {
                                    Alert notPlayingShield = new Alert(Alert.AlertType.INFORMATION);
                                    notPlayingShield
                                            .setContentText(
                                                    victim.toString()
                                                            + " has not played shield!\nAction Card will have effect!");
                                    notPlayingShield.showAndWait();

                                    chooseToPlayShield.close();

                                    Alert laserBlasAlert = new Alert(Alert.AlertType.WARNING);
                                    laserBlasAlert.setContentText(game.getCurrentPlayer()
                                            + " has used laser blast against " + victim.toString());
                                    laserBlasAlert.showAndWait();
                                    game.getSpaceDiscard().add(victim.laserBlast());

                                    if (victim.toString().equals(players1.get(0).toString())) {
                                        player1.setLayoutX(
                                                playerCard1.get(playerCard1.size() - 1).getLayoutX());
                                        player1.setLayoutY(
                                                playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                        p2.getChildren()
                                                .remove(playerCard1.get(playerCard1.size() - 1));
                                        playerCard1.remove(playerCard1.size() - 1);
                                    } else if (victim.toString().equals(players1.get(1).toString())) {
                                        player2.setLayoutX(
                                                playerCard2.get(playerCard2.size() - 1).getLayoutX());
                                        player2.setLayoutY(
                                                playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                        p2.getChildren()
                                                .remove(playerCard2.get(playerCard2.size() - 1));
                                        playerCard2.remove(playerCard2.size() - 1);
                                    } else if (victim.toString().equals(players1.get(2).toString())
                                            && game.getFullPlayerCount() >= 3) {
                                        player3.setLayoutX(
                                                playerCard3.get(playerCard3.size() - 1).getLayoutX());
                                        player3.setLayoutY(
                                                playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                        p2.getChildren()
                                                .remove(playerCard3.get(playerCard3.size() - 1));
                                        playerCard3.remove(playerCard3.size() - 1);
                                    } else if (victim.toString().equals(players1.get(3).toString())
                                            && game.getFullPlayerCount() >= 4) {
                                        player4.setLayoutX(
                                                playerCard4.get(playerCard4.size() - 1).getLayoutX());
                                        player4.setLayoutY(
                                                playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                        p2.getChildren()
                                                .remove(playerCard4.get(playerCard4.size() - 1));
                                        playerCard4.remove(playerCard4.size() - 1);
                                    } else if (victim.toString().equals(players1.get(4).toString())
                                            && game.getFullPlayerCount() == 5) {
                                        player5.setLayoutX(
                                                playerCard5.get(playerCard5.size() - 1).getLayoutX());
                                        player5.setLayoutY(
                                                playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                        p2.getChildren()
                                                .remove(playerCard5.get(playerCard5.size() - 1));
                                        playerCard5.remove(playerCard5.size() - 1);
                                    }

                                });
                            } else {
                                Alert laserBlasAlert = new Alert(Alert.AlertType.WARNING);
                                laserBlasAlert.setContentText(game.getCurrentPlayer()
                                        + " has used laser blast against " + victim.toString());
                                laserBlasAlert.showAndWait();
                                game.getSpaceDiscard().add(victim.laserBlast());

                                if (victim.toString().equals(players1.get(0).toString())) {
                                    player1.setLayoutX(
                                            playerCard1.get(playerCard1.size() - 1).getLayoutX());
                                    player1.setLayoutY(
                                            playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                    p2.getChildren()
                                            .remove(playerCard1.get(playerCard1.size() - 1));
                                    playerCard1.remove(playerCard1.size() - 1);
                                } else if (victim.toString().equals(players1.get(1).toString())) {
                                    player2.setLayoutX(
                                            playerCard2.get(playerCard2.size() - 1).getLayoutX());
                                    player2.setLayoutY(
                                            playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                    p2.getChildren()
                                            .remove(playerCard2.get(playerCard2.size() - 1));
                                    playerCard2.remove(playerCard2.size() - 1);
                                } else if (victim.toString().equals(players1.get(2).toString())
                                        && game.getFullPlayerCount() >= 3) {
                                    player3.setLayoutX(
                                            playerCard3.get(playerCard3.size() - 1).getLayoutX());
                                    player3.setLayoutY(
                                            playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                    p2.getChildren()
                                            .remove(playerCard3.get(playerCard3.size() - 1));
                                    playerCard3.remove(playerCard3.size() - 1);
                                } else if (victim.toString().equals(players1.get(3).toString())
                                        && game.getFullPlayerCount() >= 4) {
                                    player4.setLayoutX(
                                            playerCard4.get(playerCard4.size() - 1).getLayoutX());
                                    player4.setLayoutY(
                                            playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                    p2.getChildren()
                                            .remove(playerCard4.get(playerCard4.size() - 1));
                                    playerCard4.remove(playerCard4.size() - 1);
                                } else if (victim.toString().equals(players1.get(4).toString())
                                        && game.getFullPlayerCount() == 5) {
                                    player5.setLayoutX(
                                            playerCard5.get(playerCard5.size() - 1).getLayoutX());
                                    player5.setLayoutY(
                                            playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                    p2.getChildren()
                                            .remove(playerCard5.get(playerCard5.size() - 1));
                                    playerCard5.remove(playerCard5.size() - 1);
                                }

                            }
                        } else {
                            Alert playerAtLast = new Alert(Alert.AlertType.WARNING);
                            playerAtLast.setContentText("Player at start!\nCannot use card on this player!");
                            playerAtLast.showAndWait();
                        }

                        if (victim.isAlive() == false) {
                            Alert deadVictim = new Alert(Alert.AlertType.INFORMATION);
                            deadVictim.setContentText("Victim " + victim.toString() + " has been killed!");
                            deadVictim.showAndWait();
                        }
                        if (onlyTwoPlayers == true)
                            ActionCardbtn.setDisable(true);
                    });
                }

                if (chosen.toString().equals(GameDeck.ROCKET_BOOSTER)) {
                    Card c_space = game.getSpaceDeck().draw();

                    if (c_space.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false) {
                        if (c_space.toString().equals(SpaceDeck.ASTEROID_FIELD)) {
                            int o = game.getCurrentPlayer().breathe();
                            if (o == 0) {
                                Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                alert_player1
                                        .setContentText("Player " + game.getCurrentPlayer().toString());
                                alert_player1.showAndWait();
                            }

                            else {
                                Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                alert_oxygencount1.setContentText("Oxygen remaning: " + (o));
                                alert_oxygencount1.showAndWait();
                            }
                        }

                        if (c_space.toString().equals(SpaceDeck.COSMIC_RADIATION)) {
                            int o = game.getCurrentPlayer().breathe();
                            if (game.getCurrentPlayer().oxygenRemaining() == 0) {
                                Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                alert_player1
                                        .setContentText("Player " + game.getCurrentPlayer().toString());
                                alert_player1.showAndWait();
                            } else {
                                Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                alert_oxygencount1
                                        .setContentText("Oxygen discared!!\n Remaning oxygen are: " + o);
                                alert_oxygencount1.showAndWait();
                            }
                        }
                        if (c_space.toString().equals(SpaceDeck.HYPERSPACE)) {
                            Card c_space1 = game.getSpaceDeck().draw();

                            if (c_space1.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false) {
                                if (c_space1.toString().equals(SpaceDeck.ASTEROID_FIELD)) {
                                    int o = game.getCurrentPlayer().breathe();
                                    if (o == 0) {
                                        Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_player1
                                                .setContentText("Player " + game.getCurrentPlayer().toString());
                                        alert_player1.showAndWait();
                                    }

                                    else {
                                        Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_oxygencount1.setContentText("Oxygen remaning: " + (o));
                                        alert_oxygencount1.showAndWait();
                                    }
                                }

                                if (c_space1.toString().equals(SpaceDeck.COSMIC_RADIATION)) {
                                    int o = game.getCurrentPlayer().breathe();
                                    if (game.getCurrentPlayer().oxygenRemaining() == 0) {
                                        Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_player1
                                                .setContentText("Player " + game.getCurrentPlayer().toString());
                                        alert_player1.showAndWait();
                                    } else {
                                        Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_oxygencount1
                                                .setContentText("Oxygen discared!!\n Remaning oxygen are: " + o);
                                        alert_oxygencount1.showAndWait();
                                    }
                                }
                                if (c_space1.toString().equals(SpaceDeck.MYSTERIOUS_NEBULA)) {
                                    for (int i = 0; i < 2; i++) {
                                        if (game.getGameDeck().size() > 0) {
                                            Card cd = game.getGameDeck().draw();
                                            game.getCurrentPlayer().addToHand(cd);
                                            Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_card1.setContentText(
                                                    cd.toString() + " card added to hand\n " + cd.getDescription());
                                            alert_card1.showAndWait();
                                        } else {
                                            GameDiscardbtn.setDisable(false);
                                            Alert alert_deck1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_deck1.setContentText("Empty deck please merge decks!!");
                                            alert_deck1.showAndWait();
                                        }
                                    }
                                }

                                if (c_space1.toString().equals(SpaceDeck.SOLAR_FLARE)) {
                                    ActionCardbtn.setDisable(true);
                                    Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert_player1.setContentText("Player " + game.getCurrentPlayer().toString()
                                            + " has melted eye balls\n Player will not be ale to play Action cards untilthe card is behind you");
                                    alert_player1.showAndWait();
                                }

                                if (c_space1.toString().equals(SpaceDeck.USEFUL_JUNK)) {
                                    GameDeckbtn.setDisable(false);
                                    usefulJunk_bool = true;
                                }

                                if (c_space1.toString().equals(SpaceDeck.METEOROID)) {
                                    List<Card> hand = game.getCurrentPlayer().getHand();
                                    if (hand.size() >= 6) {
                                        Stage meteoroidStage = new Stage();
                                        List<Card> chosenCards = new ArrayList<>();
                                        List<CheckBox> handBox = new ArrayList<>();
                                        VBox vb43 = new VBox(6);
                                        for (Card i : hand)
                                            handBox.add(new CheckBox(i.toString()));
                                        vb43.getChildren().addAll(handBox);
                                        Button confirmButton = new Button("Click to discard cards");
                                        for (CheckBox i : handBox) {
                                            i.selectedProperty().addListener((o, oldV, newV) -> {
                                                if (newV == true) {
                                                    chosenCards.add(hand.get(handBox.indexOf(i)));
                                                    sel++;
                                                    if (sel >= 2) {
                                                        for (CheckBox j : handBox) {
                                                            if (i != j && j.isSelected() == false)
                                                                j.setDisable(true);
                                                        }
                                                    }
                                                } else {
                                                    chosenCards.remove(hand.get(handBox.indexOf(i)));
                                                    sel--;
                                                    if (sel < 2) {
                                                        for (CheckBox j : handBox) {
                                                            if (i != j)
                                                                j.setDisable(false);
                                                        }
                                                    }
                                                }
                                            });
                                        }
                                        vb43.getChildren().add(confirmButton);
                                        vb43.setBackground(Background.fill(Color.TRANSPARENT));
                                        Scene sce33 = new Scene(vb43, 200, 300, Color.WHITE);
                                        // sce.getStylesheets().add("myStyles5.css");
                                        meteoroidStage.setScene(sce33);
                                        meteoroidStage.show();
                                        confirmButton.setOnAction(evt -> {
                                            if (chosenCards.size() == 2) {
                                                game.getGameDiscard().add(chosenCards.get(0));
                                                game.getGameDiscard().add(chosenCards.get(1));
                                                game.getCurrentPlayer().hack(chosenCards.get(0));
                                                game.getCurrentPlayer().hack(chosenCards.get(1));
                                                Alert message = new Alert(Alert.AlertType.INFORMATION);
                                                message.setContentText("Cards discarded successfully");
                                                message.showAndWait();
                                                meteoroidStage.close();
                                            } else {
                                                Alert message = new Alert(Alert.AlertType.WARNING);
                                                message.setContentText("Please select 2 cards!");
                                                message.showAndWait();
                                            }
                                        });
                                    } else {
                                        Alert message = new Alert(Alert.AlertType.INFORMATION);
                                        message.setContentText(
                                                "Less than 6 cards!\nProceed without discarding any!");
                                        message.showAndWait();
                                    }

                                }

                                if (c_space1.toString().equals(SpaceDeck.WORMHOLE)) {
                                    Stage wormStage = new Stage();
                                    Button b1 = new Button(players.get(0).toString());
                                    Button b2 = new Button(players.get(1).toString());
                                    Button b3 = new Button();
                                    Button b4 = new Button();
                                    Button b5 = new Button();
                                    if (game.getFullPlayerCount() == 3)
                                        b3.setText(players.get(2).toString());
                                    if (game.getFullPlayerCount() == 4) {
                                        b3.setText(players.get(2).toString());
                                        b4.setText(players.get(3).toString());
                                    }
                                    if (game.getFullPlayerCount() == 5) {
                                        b3.setText(players.get(2).toString());
                                        b4.setText(players.get(3).toString());
                                        b5.setText(players.get(4).toString());
                                    }

                                    if (game.getCurrentPlayer().toString()
                                            .equals(players.get(0).toString()))
                                        b1.setVisible(false);
                                    else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(1).toString()))
                                        b2.setVisible(false);
                                    else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                            && game.getFullPlayerCount() >= 3)
                                        b3.setVisible(false);
                                    else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                            && game.getFullPlayerCount() >= 4)
                                        b4.setVisible(false);
                                    else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                            && game.getFullPlayerCount() == 5)
                                        b5.setVisible(false);
                                    VBox buttonBox = new VBox();
                                    buttonBox.setSpacing(5);
                                    buttonBox.setBackground(Background.fill(Color.TRANSPARENT));
                                    buttonBox.getChildren().addAll(b1, b2, b3, b4, b5);
                                    Scene buttonScene = new Scene(buttonBox, 300, 400, Color.BLACK);
                                    wormStage.setScene(buttonScene);
                                    wormStage.show();

                                    b1.setOnAction(ev -> {
                                        game.getCurrentPlayer().swapTrack(players1.get(0));
                                        double x1 = 0;
                                        double y1 = 0;
                                        double x2 = 0;
                                        double y2 = 0;
                                        if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(1).toString())) {
                                            x1 = player2.getLayoutX();
                                            y1 = player2.getLayoutY();
                                            player2.setLayoutX(player1.getLayoutX());
                                            player2.setLayoutY(player1.getLayoutY());
                                            player1.setLayoutX(x1);
                                            player1.setLayoutY(y1);

                                            x2 = l2.getLayoutX();
                                            y2 = l2.getLayoutY();
                                            l2.setLayoutX(l1.getLayoutX());
                                            l2.setLayoutY(l1.getLayoutY());
                                            l1.setLayoutX(x2);
                                            l1.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(2).toString())) {
                                            x1 = player3.getLayoutX();
                                            y1 = player3.getLayoutY();
                                            player3.setLayoutX(player1.getLayoutX());
                                            player3.setLayoutY(player1.getLayoutY());
                                            player1.setLayoutX(x1);
                                            player1.setLayoutY(y1);

                                            x2 = l3.getLayoutX();
                                            y2 = l3.getLayoutY();
                                            l3.setLayoutX(l1.getLayoutX());
                                            l3.setLayoutY(l1.getLayoutY());
                                            l1.setLayoutX(x2);
                                            l1.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(3).toString())) {
                                            x1 = player4.getLayoutX();
                                            y1 = player4.getLayoutY();
                                            player4.setLayoutX(player1.getLayoutX());
                                            player4.setLayoutY(player1.getLayoutY());
                                            player1.setLayoutX(x1);
                                            player1.setLayoutY(y1);

                                            x2 = l4.getLayoutX();
                                            y2 = l4.getLayoutY();
                                            l4.setLayoutX(l1.getLayoutX());
                                            l4.setLayoutY(l1.getLayoutY());
                                            l1.setLayoutX(x2);
                                            l1.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(4).toString())) {
                                            x1 = player5.getLayoutX();
                                            y1 = player5.getLayoutY();
                                            player5.setLayoutX(player1.getLayoutX());
                                            player5.setLayoutY(player1.getLayoutY());
                                            player1.setLayoutX(x1);
                                            player1.setLayoutY(y1);

                                            x2 = l5.getLayoutX();
                                            y2 = l5.getLayoutY();
                                            l5.setLayoutX(l1.getLayoutX());
                                            l5.setLayoutY(l1.getLayoutY());
                                            l1.setLayoutX(x2);
                                            l1.setLayoutY(y2);
                                        }
                                        wormStage.close();
                                    });
                                    b2.setOnAction(ev -> {
                                        game.getCurrentPlayer().swapTrack(players1.get(1));
                                        double x1 = 0;
                                        double y1 = 0;
                                        double x2 = 0;
                                        double y2 = 0;
                                        if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(0).toString())) {
                                            x1 = player1.getLayoutX();
                                            y1 = player1.getLayoutY();
                                            player1.setLayoutX(player2.getLayoutX());
                                            player1.setLayoutY(player2.getLayoutY());
                                            player2.setLayoutX(x1);
                                            player2.setLayoutY(y1);

                                            x2 = l1.getLayoutX();
                                            y2 = l1.getLayoutY();
                                            l1.setLayoutX(l2.getLayoutX());
                                            l1.setLayoutY(l2.getLayoutY());
                                            l2.setLayoutX(x2);
                                            l2.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(2).toString())) {
                                            x1 = player3.getLayoutX();
                                            y1 = player3.getLayoutY();
                                            player3.setLayoutX(player2.getLayoutX());
                                            player3.setLayoutY(player2.getLayoutY());
                                            player2.setLayoutX(x1);
                                            player2.setLayoutY(y1);

                                            x2 = l3.getLayoutX();
                                            y2 = l3.getLayoutY();
                                            l3.setLayoutX(l2.getLayoutX());
                                            l3.setLayoutY(l2.getLayoutY());
                                            l2.setLayoutX(x2);
                                            l2.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(3).toString())) {
                                            x1 = player4.getLayoutX();
                                            y1 = player4.getLayoutY();
                                            player4.setLayoutX(player2.getLayoutX());
                                            player4.setLayoutY(player2.getLayoutY());
                                            player2.setLayoutX(x1);
                                            player2.setLayoutY(y1);

                                            x2 = l4.getLayoutX();
                                            y2 = l4.getLayoutY();
                                            l4.setLayoutX(l2.getLayoutX());
                                            l4.setLayoutY(l2.getLayoutY());
                                            l2.setLayoutX(x2);
                                            l2.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(4).toString())) {
                                            x1 = player5.getLayoutX();
                                            y1 = player5.getLayoutY();
                                            player5.setLayoutX(player2.getLayoutX());
                                            player5.setLayoutY(player2.getLayoutY());
                                            player2.setLayoutX(x1);
                                            player2.setLayoutY(y1);

                                            x2 = l5.getLayoutX();
                                            y2 = l5.getLayoutY();
                                            l5.setLayoutX(l2.getLayoutX());
                                            l5.setLayoutY(l2.getLayoutY());
                                            l2.setLayoutX(x2);
                                            l2.setLayoutY(y2);
                                        }
                                        wormStage.close();
                                    });
                                    b3.setOnAction(ev -> {
                                        game.getCurrentPlayer().swapTrack(players1.get(2));
                                        double x1 = 0;
                                        double y1 = 0;
                                        double x2 = 0;
                                        double y2 = 0;
                                        if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(0).toString())) {
                                            x1 = player1.getLayoutX();
                                            y1 = player1.getLayoutY();
                                            player1.setLayoutX(player3.getLayoutX());
                                            player1.setLayoutY(player3.getLayoutY());
                                            player3.setLayoutX(x1);
                                            player3.setLayoutY(y1);

                                            x2 = l1.getLayoutX();
                                            y2 = l1.getLayoutY();
                                            l1.setLayoutX(l3.getLayoutX());
                                            l1.setLayoutY(l3.getLayoutY());
                                            l3.setLayoutX(x2);
                                            l3.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(1).toString())) {
                                            x1 = player2.getLayoutX();
                                            y1 = player2.getLayoutY();
                                            player2.setLayoutX(player3.getLayoutX());
                                            player2.setLayoutY(player3.getLayoutY());
                                            player3.setLayoutX(x1);
                                            player3.setLayoutY(y1);

                                            x2 = l2.getLayoutX();
                                            y2 = l2.getLayoutY();
                                            l2.setLayoutX(l3.getLayoutX());
                                            l2.setLayoutY(l3.getLayoutY());
                                            l3.setLayoutX(x2);
                                            l3.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(3).toString())) {
                                            x1 = player4.getLayoutX();
                                            y1 = player4.getLayoutY();
                                            player4.setLayoutX(player3.getLayoutX());
                                            player4.setLayoutY(player3.getLayoutY());
                                            player3.setLayoutX(x1);
                                            player3.setLayoutY(y1);

                                            x2 = l4.getLayoutX();
                                            y2 = l4.getLayoutY();
                                            l4.setLayoutX(l3.getLayoutX());
                                            l4.setLayoutY(l3.getLayoutY());
                                            l3.setLayoutX(x2);
                                            l3.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(4).toString())) {
                                            x1 = player5.getLayoutX();
                                            y1 = player5.getLayoutY();
                                            player5.setLayoutX(player3.getLayoutX());
                                            player5.setLayoutY(player3.getLayoutY());
                                            player3.setLayoutX(x1);
                                            player3.setLayoutY(y1);

                                            x2 = l5.getLayoutX();
                                            y2 = l5.getLayoutY();
                                            l5.setLayoutX(l3.getLayoutX());
                                            l5.setLayoutY(l3.getLayoutY());
                                            l3.setLayoutX(x2);
                                            l3.setLayoutY(y2);
                                        }
                                        wormStage.close();
                                    });
                                    b4.setOnAction(ev -> {
                                        game.getCurrentPlayer().swapTrack(players1.get(3));
                                        double x1 = 0;
                                        double y1 = 0;
                                        double x2 = 0;
                                        double y2 = 0;
                                        if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(0).toString())) {
                                            x1 = player1.getLayoutX();
                                            y1 = player1.getLayoutY();
                                            player1.setLayoutX(player4.getLayoutX());
                                            player1.setLayoutY(player4.getLayoutY());
                                            player4.setLayoutX(x1);
                                            player4.setLayoutY(y1);

                                            x2 = l1.getLayoutX();
                                            y2 = l1.getLayoutY();
                                            l1.setLayoutX(l4.getLayoutX());
                                            l1.setLayoutY(l4.getLayoutY());
                                            l4.setLayoutX(x2);
                                            l4.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(1).toString())) {
                                            x1 = player2.getLayoutX();
                                            y1 = player2.getLayoutY();
                                            player2.setLayoutX(player4.getLayoutX());
                                            player2.setLayoutY(player4.getLayoutY());
                                            player4.setLayoutX(x1);
                                            player4.setLayoutY(y1);

                                            x2 = l2.getLayoutX();
                                            y2 = l2.getLayoutY();
                                            l2.setLayoutX(l4.getLayoutX());
                                            l2.setLayoutY(l4.getLayoutY());
                                            l4.setLayoutX(x2);
                                            l4.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(2).toString())) {
                                            x1 = player3.getLayoutX();
                                            y1 = player3.getLayoutY();
                                            player3.setLayoutX(player4.getLayoutX());
                                            player3.setLayoutY(player4.getLayoutY());
                                            player4.setLayoutX(x1);
                                            player4.setLayoutY(y1);

                                            x2 = l3.getLayoutX();
                                            y2 = l3.getLayoutY();
                                            l3.setLayoutX(l4.getLayoutX());
                                            l3.setLayoutY(l4.getLayoutY());
                                            l4.setLayoutX(x2);
                                            l4.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(4).toString())) {
                                            x1 = player5.getLayoutX();
                                            y1 = player5.getLayoutY();
                                            player5.setLayoutX(player4.getLayoutX());
                                            player5.setLayoutY(player4.getLayoutY());
                                            player4.setLayoutX(x1);
                                            player4.setLayoutY(y1);

                                            x2 = l5.getLayoutX();
                                            y2 = l5.getLayoutY();
                                            l5.setLayoutX(l4.getLayoutX());
                                            l5.setLayoutY(l4.getLayoutY());
                                            l4.setLayoutX(x2);
                                            l4.setLayoutY(y2);
                                        }
                                        wormStage.close();
                                    });
                                    b5.setOnAction(ev -> {
                                        game.getCurrentPlayer().swapTrack(players1.get(4));
                                        double x1 = 0;
                                        double y1 = 0;
                                        double x2 = 0;
                                        double y2 = 0;
                                        if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(0).toString())) {
                                            x1 = player1.getLayoutX();
                                            y1 = player1.getLayoutY();
                                            player1.setLayoutX(player5.getLayoutX());
                                            player1.setLayoutY(player5.getLayoutY());
                                            player5.setLayoutX(x1);
                                            player5.setLayoutY(y1);

                                            x2 = l1.getLayoutX();
                                            y2 = l1.getLayoutY();
                                            l1.setLayoutX(l5.getLayoutX());
                                            l1.setLayoutY(l5.getLayoutY());
                                            l5.setLayoutX(x2);
                                            l5.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(1).toString())) {
                                            x1 = player2.getLayoutX();
                                            y1 = player2.getLayoutY();
                                            player2.setLayoutX(player5.getLayoutX());
                                            player2.setLayoutY(player5.getLayoutY());
                                            player5.setLayoutX(x1);
                                            player5.setLayoutY(y1);

                                            x2 = l2.getLayoutX();
                                            y2 = l2.getLayoutY();
                                            l2.setLayoutX(l5.getLayoutX());
                                            l2.setLayoutY(l5.getLayoutY());
                                            l5.setLayoutX(x2);
                                            l5.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(2).toString())) {
                                            x1 = player3.getLayoutX();
                                            y1 = player3.getLayoutY();
                                            player3.setLayoutX(player5.getLayoutX());
                                            player3.setLayoutY(player5.getLayoutY());
                                            player5.setLayoutX(x1);
                                            player5.setLayoutY(y1);

                                            x2 = l3.getLayoutX();
                                            y2 = l3.getLayoutY();
                                            l3.setLayoutX(l5.getLayoutX());
                                            l3.setLayoutY(l5.getLayoutY());
                                            l5.setLayoutX(x2);
                                            l5.setLayoutY(y2);
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players1.get(3).toString())) {
                                            x1 = player4.getLayoutX();
                                            y1 = player4.getLayoutY();
                                            player4.setLayoutX(player5.getLayoutX());
                                            player4.setLayoutY(player5.getLayoutY());
                                            player5.setLayoutX(x1);
                                            player5.setLayoutY(y1);

                                            x2 = l4.getLayoutX();
                                            y2 = l4.getLayoutY();
                                            l4.setLayoutX(l5.getLayoutX());
                                            l4.setLayoutY(l5.getLayoutY());
                                            l5.setLayoutX(x2);
                                            l5.setLayoutY(y2);
                                        }
                                        wormStage.close();
                                    });
                                }

                                if (c_space1.toString().equals(SpaceDeck.BLANK_SPACE)) {
                                    Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert_card1.setContentText(c_space1.toString() + "\n" +
                                            c_space1.getDescription());
                                    alert_card1.showAndWait();

                                }

                                Button btn = new Button(c_space1.toString());
                                btn.setStyle(
                                        "-fx-pref-width: 160px; -fx-pref-height: 120px; -fx-background-color:#868886;");
                                btn.setOnAction(ev -> {
                                    Alert alert_card12 = new Alert(Alert.AlertType.INFORMATION);
                                    alert_card12.setContentText(c_space1.getDescription());
                                    alert_card12.setTitle(c_space1.toString());
                                    alert_card12.showAndWait();
                                });
                                ActionCardbtn.setDisable(true);
                                ViewHandbtn.setDisable(true);
                                Breathebtn.setDisable(true);
                                EndTurnbtn.setDisable(false);

                                if ((c_space1.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false)
                                        && game.getCurrentPlayer().isAlive() == true) {
                                    p2.getChildren().add(btn);
                                    if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                        playerCard1.add(btn);
                                        btn.relocate(player1.getLayoutX(), player1.getLayoutY());
                                        player1.relocate(player1.getLayoutX(),
                                                600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(1).toString())) {
                                        playerCard2.add(btn);
                                        btn.relocate(player2.getLayoutX(), player2.getLayoutY());
                                        player2.relocate(player2.getLayoutX(),
                                                600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                                    } else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                            && game.getFullPlayerCount() >= 3) {
                                        playerCard3.add(btn);
                                        btn.relocate(player3.getLayoutX(), player3.getLayoutY());
                                        player3.relocate(player3.getLayoutX(),
                                                600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                                    } else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                            && game.getFullPlayerCount() >= 4) {
                                        playerCard4.add(btn);
                                        btn.relocate(player4.getLayoutX(), player4.getLayoutY());
                                        player4.relocate(player4.getLayoutX(),
                                                600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                                    } else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                            && game.getFullPlayerCount() == 5) {
                                        playerCard5.add(btn);
                                        btn.relocate(player5.getLayoutX(), player5.getLayoutY());
                                        player5.relocate(player5.getLayoutX(),
                                                600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                                    }

                                }
                                if ((c_space1.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == true)
                                        && game.getCurrentPlayer().isAlive() == true) {
                                    if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                        game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                        game.getCurrentPlayer().getTrack()
                                                .remove(game.getCurrentPlayer().peekAtTrack());
                                        player1.relocate(playerCard1.get(playerCard1.size() - 1).getLayoutX(),
                                                playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                        p2.getChildren().remove(playerCard1.get(playerCard1.size() - 1));
                                        playerCard1.remove(playerCard1.get(playerCard1.size() - 1));
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(1).toString())) {
                                        game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                        game.getCurrentPlayer().getTrack()
                                                .remove(game.getCurrentPlayer().peekAtTrack());
                                        player2.relocate(playerCard2.get(playerCard2.size() - 1).getLayoutX(),
                                                playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                        p2.getChildren().remove(playerCard2.get(playerCard2.size() - 1));
                                        playerCard2.remove(playerCard2.get(playerCard2.size() - 1));
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(2).toString())) {
                                        game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                        game.getCurrentPlayer().getTrack()
                                                .remove(game.getCurrentPlayer().peekAtTrack());
                                        player3.relocate(playerCard3.get(playerCard3.size() - 1).getLayoutX(),
                                                playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                        p2.getChildren().remove(playerCard3.get(playerCard3.size() - 1));
                                        playerCard3.remove(playerCard3.get(playerCard3.size() - 1));
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(3).toString())) {
                                        game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                        game.getCurrentPlayer().getTrack()
                                                .remove(game.getCurrentPlayer().peekAtTrack());
                                        player4.relocate(playerCard4.get(playerCard4.size() - 1).getLayoutX(),
                                                playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                        p2.getChildren().remove(playerCard4.get(playerCard4.size() - 1));
                                        playerCard4.remove(playerCard4.get(playerCard4.size() - 1));
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players.get(4).toString())) {
                                        game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                        game.getCurrentPlayer().getTrack()
                                                .remove(game.getCurrentPlayer().peekAtTrack());
                                        player5.relocate(playerCard5.get(playerCard5.size() - 1).getLayoutX(),
                                                playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                        p2.getChildren().remove(playerCard5.get(playerCard5.size() - 1));
                                        playerCard5.remove(playerCard5.get(playerCard5.size() - 1));
                                    }
                                }
                            }
                        }
                        if (c_space.toString().equals(SpaceDeck.MYSTERIOUS_NEBULA)) {
                            for (int i = 0; i < 2; i++) {
                                if (game.getGameDeck().size() > 0) {
                                    Card cd = game.getGameDeck().draw();
                                    game.getCurrentPlayer().addToHand(cd);
                                    Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert_card1.setContentText(
                                            cd.toString() + " card added to hand\n " + cd.getDescription());
                                    alert_card1.showAndWait();
                                } else {
                                    GameDiscardbtn.setDisable(false);
                                    Alert alert_deck1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert_deck1.setContentText("Empty deck please merge decks!!");
                                    alert_deck1.showAndWait();
                                }
                            }
                        }

                        if (c_space.toString().equals(SpaceDeck.SOLAR_FLARE)) {
                            ActionCardbtn.setDisable(true);
                            Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                            alert_player1.setContentText("Player " + game.getCurrentPlayer().toString()
                                    + " has melted eye balls\n Player will not be ale to play Action cards untilthe card is behind you");
                            alert_player1.showAndWait();
                        }

                        if (c_space.toString().equals(SpaceDeck.USEFUL_JUNK)) {
                            GameDeckbtn.setDisable(false);
                            usefulJunk_bool = true;
                        }

                        if (c_space.toString().equals(SpaceDeck.METEOROID)) {
                            List<Card> hand = game.getCurrentPlayer().getHand();
                            if (hand.size() >= 6) {
                                Stage meteoroidStage = new Stage();
                                List<Card> chosenCards = new ArrayList<>();
                                List<CheckBox> handBox = new ArrayList<>();
                                VBox vb22 = new VBox(6);
                                for (Card i : hand)
                                    handBox.add(new CheckBox(i.toString()));
                                vb22.getChildren().addAll(handBox);
                                Button confirmButton = new Button("Click to discard cards");
                                for (CheckBox i : handBox) {
                                    i.selectedProperty().addListener((o, oldV, newV) -> {
                                        if (newV == true) {
                                            chosenCards.add(hand.get(handBox.indexOf(i)));
                                            sel++;
                                            if (sel >= 2) {
                                                for (CheckBox j : handBox) {
                                                    if (i != j && j.isSelected() == false)
                                                        j.setDisable(true);
                                                }
                                            }
                                        } else {
                                            chosenCards.remove(hand.get(handBox.indexOf(i)));
                                            sel--;
                                            if (sel < 2) {
                                                for (CheckBox j : handBox) {
                                                    if (i != j)
                                                        j.setDisable(false);
                                                }
                                            }
                                        }
                                    });
                                }
                                vb22.getChildren().add(confirmButton);
                                vb22.setBackground(Background.fill(Color.TRANSPARENT));
                                Scene sce22 = new Scene(vb22, 200, 300, Color.WHITE);
                                // sce.getStylesheets().add("myStyles5.css");
                                meteoroidStage.setScene(sce22);
                                meteoroidStage.show();
                                confirmButton.setOnAction(evt -> {
                                    if (chosenCards.size() == 2) {
                                        game.getGameDiscard().add(chosenCards.get(0));
                                        game.getGameDiscard().add(chosenCards.get(1));
                                        game.getCurrentPlayer().hack(chosenCards.get(0));
                                        game.getCurrentPlayer().hack(chosenCards.get(1));
                                        Alert message = new Alert(Alert.AlertType.INFORMATION);
                                        message.setContentText("Cards discarded successfully");
                                        message.showAndWait();
                                        meteoroidStage.close();
                                    } else {
                                        Alert message = new Alert(Alert.AlertType.WARNING);
                                        message.setContentText("Please select 2 cards!");
                                        message.showAndWait();
                                    }
                                });
                            } else {
                                Alert message = new Alert(Alert.AlertType.INFORMATION);
                                message.setContentText(
                                        "Less than 6 cards!\nProceed without discarding any!");
                                message.showAndWait();
                            }

                        }

                        if (c_space.toString().equals(SpaceDeck.WORMHOLE)) {
                            Stage wormStage = new Stage();
                            Button b1 = new Button(players.get(0).toString());
                            Button b2 = new Button(players.get(1).toString());
                            Button b3 = new Button();
                            Button b4 = new Button();
                            Button b5 = new Button();
                            if (game.getFullPlayerCount() == 3)
                                b3.setText(players.get(2).toString());
                            if (game.getFullPlayerCount() == 4) {
                                b3.setText(players.get(2).toString());
                                b4.setText(players.get(3).toString());
                            }
                            if (game.getFullPlayerCount() == 5) {
                                b3.setText(players.get(2).toString());
                                b4.setText(players.get(3).toString());
                                b5.setText(players.get(4).toString());
                            }

                            if (game.getCurrentPlayer().toString()
                                    .equals(players.get(0).toString()))
                                b1.setVisible(false);
                            else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(1).toString()))
                                b2.setVisible(false);
                            else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                    && game.getFullPlayerCount() >= 3)
                                b3.setVisible(false);
                            else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                    && game.getFullPlayerCount() >= 4)
                                b4.setVisible(false);
                            else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                    && game.getFullPlayerCount() == 5)
                                b5.setVisible(false);
                            VBox buttonBox = new VBox();
                            buttonBox.setSpacing(5);
                            buttonBox.setBackground(Background.fill(Color.TRANSPARENT));
                            buttonBox.getChildren().addAll(b1, b2, b3, b4, b5);
                            Scene buttonScene = new Scene(buttonBox, 300, 400, Color.BLACK);
                            wormStage.setScene(buttonScene);
                            wormStage.show();

                            b1.setOnAction(ev -> {
                                game.getCurrentPlayer().swapTrack(players1.get(0));
                                double x1 = 0;
                                double y1 = 0;
                                double x2 = 0;
                                double y2 = 0;
                                if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(1).toString())) {
                                    x1 = player2.getLayoutX();
                                    y1 = player2.getLayoutY();
                                    player2.setLayoutX(player1.getLayoutX());
                                    player2.setLayoutY(player1.getLayoutY());
                                    player1.setLayoutX(x1);
                                    player1.setLayoutY(y1);

                                    x2 = l2.getLayoutX();
                                    y2 = l2.getLayoutY();
                                    l2.setLayoutX(l1.getLayoutX());
                                    l2.setLayoutY(l1.getLayoutY());
                                    l1.setLayoutX(x2);
                                    l1.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(2).toString())) {
                                    x1 = player3.getLayoutX();
                                    y1 = player3.getLayoutY();
                                    player3.setLayoutX(player1.getLayoutX());
                                    player3.setLayoutY(player1.getLayoutY());
                                    player1.setLayoutX(x1);
                                    player1.setLayoutY(y1);

                                    x2 = l3.getLayoutX();
                                    y2 = l3.getLayoutY();
                                    l3.setLayoutX(l1.getLayoutX());
                                    l3.setLayoutY(l1.getLayoutY());
                                    l1.setLayoutX(x2);
                                    l1.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(3).toString())) {
                                    x1 = player4.getLayoutX();
                                    y1 = player4.getLayoutY();
                                    player4.setLayoutX(player1.getLayoutX());
                                    player4.setLayoutY(player1.getLayoutY());
                                    player1.setLayoutX(x1);
                                    player1.setLayoutY(y1);

                                    x2 = l4.getLayoutX();
                                    y2 = l4.getLayoutY();
                                    l4.setLayoutX(l1.getLayoutX());
                                    l4.setLayoutY(l1.getLayoutY());
                                    l1.setLayoutX(x2);
                                    l1.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(4).toString())) {
                                    x1 = player5.getLayoutX();
                                    y1 = player5.getLayoutY();
                                    player5.setLayoutX(player1.getLayoutX());
                                    player5.setLayoutY(player1.getLayoutY());
                                    player1.setLayoutX(x1);
                                    player1.setLayoutY(y1);

                                    x2 = l5.getLayoutX();
                                    y2 = l5.getLayoutY();
                                    l5.setLayoutX(l1.getLayoutX());
                                    l5.setLayoutY(l1.getLayoutY());
                                    l1.setLayoutX(x2);
                                    l1.setLayoutY(y2);
                                }
                                wormStage.close();
                            });
                            b2.setOnAction(ev -> {
                                game.getCurrentPlayer().swapTrack(players1.get(1));
                                double x1 = 0;
                                double y1 = 0;
                                double x2 = 0;
                                double y2 = 0;
                                if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(0).toString())) {
                                    x1 = player1.getLayoutX();
                                    y1 = player1.getLayoutY();
                                    player1.setLayoutX(player2.getLayoutX());
                                    player1.setLayoutY(player2.getLayoutY());
                                    player2.setLayoutX(x1);
                                    player2.setLayoutY(y1);

                                    x2 = l1.getLayoutX();
                                    y2 = l1.getLayoutY();
                                    l1.setLayoutX(l2.getLayoutX());
                                    l1.setLayoutY(l2.getLayoutY());
                                    l2.setLayoutX(x2);
                                    l2.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(2).toString())) {
                                    x1 = player3.getLayoutX();
                                    y1 = player3.getLayoutY();
                                    player3.setLayoutX(player2.getLayoutX());
                                    player3.setLayoutY(player2.getLayoutY());
                                    player2.setLayoutX(x1);
                                    player2.setLayoutY(y1);

                                    x2 = l3.getLayoutX();
                                    y2 = l3.getLayoutY();
                                    l3.setLayoutX(l2.getLayoutX());
                                    l3.setLayoutY(l2.getLayoutY());
                                    l2.setLayoutX(x2);
                                    l2.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(3).toString())) {
                                    x1 = player4.getLayoutX();
                                    y1 = player4.getLayoutY();
                                    player4.setLayoutX(player2.getLayoutX());
                                    player4.setLayoutY(player2.getLayoutY());
                                    player2.setLayoutX(x1);
                                    player2.setLayoutY(y1);

                                    x2 = l4.getLayoutX();
                                    y2 = l4.getLayoutY();
                                    l4.setLayoutX(l2.getLayoutX());
                                    l4.setLayoutY(l2.getLayoutY());
                                    l2.setLayoutX(x2);
                                    l2.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(4).toString())) {
                                    x1 = player5.getLayoutX();
                                    y1 = player5.getLayoutY();
                                    player5.setLayoutX(player2.getLayoutX());
                                    player5.setLayoutY(player2.getLayoutY());
                                    player2.setLayoutX(x1);
                                    player2.setLayoutY(y1);

                                    x2 = l5.getLayoutX();
                                    y2 = l5.getLayoutY();
                                    l5.setLayoutX(l2.getLayoutX());
                                    l5.setLayoutY(l2.getLayoutY());
                                    l2.setLayoutX(x2);
                                    l2.setLayoutY(y2);
                                }
                                wormStage.close();
                            });
                            b3.setOnAction(ev -> {
                                game.getCurrentPlayer().swapTrack(players1.get(2));
                                double x1 = 0;
                                double y1 = 0;
                                double x2 = 0;
                                double y2 = 0;
                                if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(0).toString())) {
                                    x1 = player1.getLayoutX();
                                    y1 = player1.getLayoutY();
                                    player1.setLayoutX(player3.getLayoutX());
                                    player1.setLayoutY(player3.getLayoutY());
                                    player3.setLayoutX(x1);
                                    player3.setLayoutY(y1);

                                    x2 = l1.getLayoutX();
                                    y2 = l1.getLayoutY();
                                    l1.setLayoutX(l3.getLayoutX());
                                    l1.setLayoutY(l3.getLayoutY());
                                    l3.setLayoutX(x2);
                                    l3.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(1).toString())) {
                                    x1 = player2.getLayoutX();
                                    y1 = player2.getLayoutY();
                                    player2.setLayoutX(player3.getLayoutX());
                                    player2.setLayoutY(player3.getLayoutY());
                                    player3.setLayoutX(x1);
                                    player3.setLayoutY(y1);

                                    x2 = l2.getLayoutX();
                                    y2 = l2.getLayoutY();
                                    l2.setLayoutX(l3.getLayoutX());
                                    l2.setLayoutY(l3.getLayoutY());
                                    l3.setLayoutX(x2);
                                    l3.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(3).toString())) {
                                    x1 = player4.getLayoutX();
                                    y1 = player4.getLayoutY();
                                    player4.setLayoutX(player3.getLayoutX());
                                    player4.setLayoutY(player3.getLayoutY());
                                    player3.setLayoutX(x1);
                                    player3.setLayoutY(y1);

                                    x2 = l4.getLayoutX();
                                    y2 = l4.getLayoutY();
                                    l4.setLayoutX(l3.getLayoutX());
                                    l4.setLayoutY(l3.getLayoutY());
                                    l3.setLayoutX(x2);
                                    l3.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(4).toString())) {
                                    x1 = player5.getLayoutX();
                                    y1 = player5.getLayoutY();
                                    player5.setLayoutX(player3.getLayoutX());
                                    player5.setLayoutY(player3.getLayoutY());
                                    player3.setLayoutX(x1);
                                    player3.setLayoutY(y1);

                                    x2 = l5.getLayoutX();
                                    y2 = l5.getLayoutY();
                                    l5.setLayoutX(l3.getLayoutX());
                                    l5.setLayoutY(l3.getLayoutY());
                                    l3.setLayoutX(x2);
                                    l3.setLayoutY(y2);
                                }
                                wormStage.close();
                            });
                            b4.setOnAction(ev -> {
                                game.getCurrentPlayer().swapTrack(players1.get(3));
                                double x1 = 0;
                                double y1 = 0;
                                double x2 = 0;
                                double y2 = 0;
                                if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(0).toString())) {
                                    x1 = player1.getLayoutX();
                                    y1 = player1.getLayoutY();
                                    player1.setLayoutX(player4.getLayoutX());
                                    player1.setLayoutY(player4.getLayoutY());
                                    player4.setLayoutX(x1);
                                    player4.setLayoutY(y1);

                                    x2 = l1.getLayoutX();
                                    y2 = l1.getLayoutY();
                                    l1.setLayoutX(l4.getLayoutX());
                                    l1.setLayoutY(l4.getLayoutY());
                                    l4.setLayoutX(x2);
                                    l4.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(1).toString())) {
                                    x1 = player2.getLayoutX();
                                    y1 = player2.getLayoutY();
                                    player2.setLayoutX(player4.getLayoutX());
                                    player2.setLayoutY(player4.getLayoutY());
                                    player4.setLayoutX(x1);
                                    player4.setLayoutY(y1);

                                    x2 = l2.getLayoutX();
                                    y2 = l2.getLayoutY();
                                    l2.setLayoutX(l4.getLayoutX());
                                    l2.setLayoutY(l4.getLayoutY());
                                    l4.setLayoutX(x2);
                                    l4.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(2).toString())) {
                                    x1 = player3.getLayoutX();
                                    y1 = player3.getLayoutY();
                                    player3.setLayoutX(player4.getLayoutX());
                                    player3.setLayoutY(player4.getLayoutY());
                                    player4.setLayoutX(x1);
                                    player4.setLayoutY(y1);

                                    x2 = l3.getLayoutX();
                                    y2 = l3.getLayoutY();
                                    l3.setLayoutX(l4.getLayoutX());
                                    l3.setLayoutY(l4.getLayoutY());
                                    l4.setLayoutX(x2);
                                    l4.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(4).toString())) {
                                    x1 = player5.getLayoutX();
                                    y1 = player5.getLayoutY();
                                    player5.setLayoutX(player4.getLayoutX());
                                    player5.setLayoutY(player4.getLayoutY());
                                    player4.setLayoutX(x1);
                                    player4.setLayoutY(y1);

                                    x2 = l5.getLayoutX();
                                    y2 = l5.getLayoutY();
                                    l5.setLayoutX(l4.getLayoutX());
                                    l5.setLayoutY(l4.getLayoutY());
                                    l4.setLayoutX(x2);
                                    l4.setLayoutY(y2);
                                }
                                wormStage.close();
                            });
                            b5.setOnAction(ev -> {
                                game.getCurrentPlayer().swapTrack(players1.get(4));
                                double x1 = 0;
                                double y1 = 0;
                                double x2 = 0;
                                double y2 = 0;
                                if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(0).toString())) {
                                    x1 = player1.getLayoutX();
                                    y1 = player1.getLayoutY();
                                    player1.setLayoutX(player5.getLayoutX());
                                    player1.setLayoutY(player5.getLayoutY());
                                    player5.setLayoutX(x1);
                                    player5.setLayoutY(y1);

                                    x2 = l1.getLayoutX();
                                    y2 = l1.getLayoutY();
                                    l1.setLayoutX(l5.getLayoutX());
                                    l1.setLayoutY(l5.getLayoutY());
                                    l5.setLayoutX(x2);
                                    l5.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(1).toString())) {
                                    x1 = player2.getLayoutX();
                                    y1 = player2.getLayoutY();
                                    player2.setLayoutX(player5.getLayoutX());
                                    player2.setLayoutY(player5.getLayoutY());
                                    player5.setLayoutX(x1);
                                    player5.setLayoutY(y1);

                                    x2 = l2.getLayoutX();
                                    y2 = l2.getLayoutY();
                                    l2.setLayoutX(l5.getLayoutX());
                                    l2.setLayoutY(l5.getLayoutY());
                                    l5.setLayoutX(x2);
                                    l5.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(2).toString())) {
                                    x1 = player3.getLayoutX();
                                    y1 = player3.getLayoutY();
                                    player3.setLayoutX(player5.getLayoutX());
                                    player3.setLayoutY(player5.getLayoutY());
                                    player5.setLayoutX(x1);
                                    player5.setLayoutY(y1);

                                    x2 = l3.getLayoutX();
                                    y2 = l3.getLayoutY();
                                    l3.setLayoutX(l5.getLayoutX());
                                    l3.setLayoutY(l5.getLayoutY());
                                    l5.setLayoutX(x2);
                                    l5.setLayoutY(y2);
                                } else if (game.getCurrentPlayer().toString()
                                        .equals(players1.get(3).toString())) {
                                    x1 = player4.getLayoutX();
                                    y1 = player4.getLayoutY();
                                    player4.setLayoutX(player5.getLayoutX());
                                    player4.setLayoutY(player5.getLayoutY());
                                    player5.setLayoutX(x1);
                                    player5.setLayoutY(y1);

                                    x2 = l4.getLayoutX();
                                    y2 = l4.getLayoutY();
                                    l4.setLayoutX(l5.getLayoutX());
                                    l4.setLayoutY(l5.getLayoutY());
                                    l5.setLayoutX(x2);
                                    l5.setLayoutY(y2);
                                }
                                wormStage.close();
                            });
                        }

                        if (c_space.toString().equals(SpaceDeck.BLANK_SPACE)) {
                            Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                            alert_card1.setContentText(c_space.toString() + "\n" +
                                    c_space.getDescription());
                            alert_card1.showAndWait();

                        }

                        Button btn = new Button(c_space.toString());
                        btn.setStyle(
                                "-fx-pref-width: 160px; -fx-pref-height: 120px; -fx-background-color:#868886;");
                        btn.setOnAction(ev -> {
                            Alert alert_card12 = new Alert(Alert.AlertType.INFORMATION);
                            alert_card12.setContentText(c_space.getDescription());
                            alert_card12.setTitle(c_space.toString());
                            alert_card12.showAndWait();
                        });
                        ActionCardbtn.setDisable(true);
                        ViewHandbtn.setDisable(true);
                        Breathebtn.setDisable(true);
                        EndTurnbtn.setDisable(false);

                        if ((c_space.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false)
                                && game.getCurrentPlayer().isAlive() == true) {
                            p2.getChildren().add(btn);
                            if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                playerCard1.add(btn);
                                btn.relocate(player1.getLayoutX() + 10, player1.getLayoutY());
                                player1.relocate(player1.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                            } else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(1).toString())) {
                                playerCard2.add(btn);
                                btn.relocate(player2.getLayoutX(), player2.getLayoutY());
                                player2.relocate(player2.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                    && game.getFullPlayerCount() >= 3) {
                                playerCard3.add(btn);
                                btn.relocate(player3.getLayoutX(), player3.getLayoutY());
                                player3.relocate(player3.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                    && game.getFullPlayerCount() >= 4) {
                                playerCard4.add(btn);
                                btn.relocate(player4.getLayoutX(), player4.getLayoutY());
                                player4.relocate(player4.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                    && game.getFullPlayerCount() == 5) {
                                playerCard5.add(btn);
                                btn.relocate(player5.getLayoutX(), player5.getLayoutY());
                                player5.relocate(player5.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 130);
                            }

                        }
                        if ((c_space.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == true)
                                && game.getCurrentPlayer().isAlive() == true) {
                            if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack()
                                        .remove(game.getCurrentPlayer().peekAtTrack());
                                player1.relocate(playerCard1.get(playerCard1.size() - 1).getLayoutX(),
                                        playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard1.get(playerCard1.size() - 1));
                                playerCard1.remove(playerCard1.get(playerCard1.size() - 1));
                            } else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(1).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack()
                                        .remove(game.getCurrentPlayer().peekAtTrack());
                                player2.relocate(playerCard2.get(playerCard2.size() - 1).getLayoutX(),
                                        playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard2.get(playerCard2.size() - 1));
                                playerCard2.remove(playerCard2.get(playerCard2.size() - 1));
                            } else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(2).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack()
                                        .remove(game.getCurrentPlayer().peekAtTrack());
                                player3.relocate(playerCard3.get(playerCard3.size() - 1).getLayoutX(),
                                        playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard3.get(playerCard3.size() - 1));
                                playerCard3.remove(playerCard3.get(playerCard3.size() - 1));
                            } else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(3).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack()
                                        .remove(game.getCurrentPlayer().peekAtTrack());
                                player4.relocate(playerCard4.get(playerCard4.size() - 1).getLayoutX(),
                                        playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard4.get(playerCard4.size() - 1));
                                playerCard4.remove(playerCard4.get(playerCard4.size() - 1));
                            } else if (game.getCurrentPlayer().toString()
                                    .equals(players.get(4).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack()
                                        .remove(game.getCurrentPlayer().peekAtTrack());
                                player5.relocate(playerCard5.get(playerCard5.size() - 1).getLayoutX(),
                                        playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard5.get(playerCard5.size() - 1));
                                playerCard5.remove(playerCard5.get(playerCard5.size() - 1));
                            }
                        }
                    }
                }
                Travelbtn.setDisable(true);
            });
            EndTurnbtn.setOnAction(e -> {
                if (game.getCurrentPlayer().oxygenRemaining() == 0 && playerBreatheKillsPlayer == false) {
                    if (game.getCurrentPlayer().toString().equals(players.get(0).toString()))
                        player1.setGraphic(dpimv1);
                    else if (game.getCurrentPlayer().toString().equals(players.get(1).toString()))
                        player2.setGraphic(dpimv2);
                    else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                            && game.getFullPlayerCount() >= 3)
                        player3.setGraphic(dpimv3);
                    else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                            && game.getFullPlayerCount() >= 4)
                        player4.setGraphic(dpimv4);
                    else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                            && game.getFullPlayerCount() == 5)
                        player5.setGraphic(dpimv5);
                    Alert deadAlert = new Alert(Alert.AlertType.INFORMATION);
                    deadAlert.setContentText(game.getCurrentPlayer().toString());
                    deadAlert.showAndWait();
                } else if (game.getCurrentPlayer().oxygenRemaining() == 0 && playerBreatheKillsPlayer == true)
                    playerBreatheKillsPlayer = false;

                int alive = game.endTurn();
                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setContentText("Players alive: " + alive);
                info.showAndWait();
                if (game.gameOver() == true) {
                    Alert gameOverAlert = new Alert(Alert.AlertType.INFORMATION);
                    gameOverAlert.setContentText("Game is over!");
                    if (game.getWinner() != null)
                        gameOverAlert.setContentText("\nThe winner is: " + game.getWinner().toString());
                    else
                        gameOverAlert.setContentText("\nAll players are dead!");
                    gameOverAlert.showAndWait();
                    ViewHandbtn.setDisable(true);
                    EndTurnbtn.setDisable(true);
                    Travelbtn.setDisable(true);
                    Breathebtn.setDisable(true);
                    GameDeckbtn.setDisable(true);
                }

                game.startTurn();
                info.setContentText("Current player: " + game.getCurrentPlayer());
                info.showAndWait();
                EndTurnbtn.setDisable(true);
                Travelbtn.setDisable(true);
                GameDeckbtn.setDisable(false);
            });
            SpaceDiscardbtn.setOnAction(j -> {
                game.mergeDecks(game.getSpaceDeck(), game.getSpaceDiscard());
            });

            Breathebtn.setOnAction(e -> {
                int oxy_val = game.getCurrentPlayer().breathe();
                if (oxy_val == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Player dead");
                    alert.setTitle("Breathe");
                    alert.showAndWait();
                    playerBreatheKillsPlayer = true;
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Oxygen Remaning: " + (oxy_val));
                    alert.showAndWait();
                    playerBreatheKillsPlayer = false;
                }
                GameDeckbtn.setDisable(true);
                EndTurnbtn.setDisable(false);
                ActionCardbtn.setDisable(true);
                Travelbtn.setDisable(true);
                Breathebtn.setDisable(true);
            });

            GameDeckbtn.setOnAction(e -> {
                Card c = game.getGameDeck().draw();
                game.getCurrentPlayer().addToHand(c);
                if (game.getGameDeck().size() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(c.toString() + "\n" + c.getDescription());
                    alert.setTitle("Game Deck");
                    alert.showAndWait();
                    ActionCardbtn.setDisable(false);
                    Breathebtn.setDisable(false);
                    Travelbtn.setDisable(false);
                } else {
                    GameDiscardbtn.setDisable(false);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Deck empty!!");
                    alert.showAndWait();
                }

                GameDeckbtn.setDisable(true);
                if (usefulJunk_bool == true) {
                    Breathebtn.setDisable(true);
                    Travelbtn.setDisable(true);
                    ActionCardbtn.setDisable(true);
                    EndTurnbtn.setDisable(false);
                    usefulJunk_bool = false;

                } else {
                    Breathebtn.setDisable(false);
                    Travelbtn.setDisable(false);
                    if (game.getCurrentPlayer().getTrack().size() > 0) {
                        if (game.getCurrentPlayer().hasMeltedEyeballs() == true)
                            ActionCardbtn.setDisable(true);
                        else
                            ActionCardbtn.setDisable(false);
                    } else
                        ActionCardbtn.setDisable(false);
                }

            });

            GameDiscardbtn.setOnAction(e -> {
                game.mergeDecks(game.getGameDeck(), game.getGameDiscard());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Deck Merged");
                alert.showAndWait();
                GameDiscardbtn.setDisable(false);
            });

            Travelbtn.setOnAction(e -> {
                if (game.getGameDeck().size() == 0) {
                    SpaceDiscardbtn.setDisable(false);
                }

                Card c = game.travel(game.getCurrentPlayer());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(c.toString() + "\n" + c.getDescription());
                alert.showAndWait();
                try {
                    if (game.getCurrentPlayer().oxygenRemaining() == 0) {
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setContentText("Player dead, cannot travel!!");
                        alert1.showAndWait();
                        GameDeckbtn.setDisable(true);
                    }

                    if (c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false) {
                        switch (c.toString()) {
                            case "Asteroid field":

                                int o1 = game.getCurrentPlayer().breathe();
                                if (o1 == 0) {
                                    Alert alert_player = new Alert(Alert.AlertType.INFORMATION);
                                    alert_player.setContentText("Player " + game.getCurrentPlayer().toString());
                                    alert_player.showAndWait();
                                }

                                else {
                                    Alert alert_oxygencount = new Alert(Alert.AlertType.INFORMATION);
                                    alert_oxygencount.setContentText("Oxygen remaning: " + (o1));
                                    alert_oxygencount.showAndWait();
                                }
                                break;

                            case "Cosmic radiation":

                                int o2 = game.getCurrentPlayer().breathe();
                                if (game.getCurrentPlayer().oxygenRemaining() == 0) {
                                    Alert alert_player = new Alert(Alert.AlertType.INFORMATION);
                                    alert_player.setContentText("Player " + game.getCurrentPlayer().toString());
                                    alert_player.showAndWait();
                                } else {
                                    Alert alert_oxygencount = new Alert(Alert.AlertType.INFORMATION);
                                    alert_oxygencount.setContentText("Oxygen discared!!\n Remaning oxygen are: " + o2);
                                    alert_oxygencount.showAndWait();
                                }
                                break;

                            case "Mysterious nebula":

                                for (int i = 0; i < 2; i++) {
                                    if (game.getGameDeck().size() > 0) {
                                        Card cd = game.getGameDeck().draw();
                                        game.getCurrentPlayer().addToHand(cd);
                                        Alert alert_card = new Alert(Alert.AlertType.INFORMATION);
                                        alert_card.setContentText(
                                                cd.toString() + " card added to hand\n " + cd.getDescription());
                                        alert_card.showAndWait();
                                    } else {
                                        GameDiscardbtn.setDisable(false);
                                        Alert alert_deck = new Alert(Alert.AlertType.INFORMATION);
                                        alert_deck.setContentText("Empty deck please merge decks!!");
                                        alert_deck.showAndWait();
                                    }
                                }
                                break;

                            case "Solar flare":

                                ActionCardbtn.setDisable(true);
                                Alert alert_player = new Alert(Alert.AlertType.INFORMATION);
                                alert_player.setContentText("Player " + game.getCurrentPlayer().toString()
                                        + " has melted eye balls\n Player will not be ale to play Action cards until the card is behind you");
                                alert_player.showAndWait();
                                break;

                            case "Useful junk":

                                GameDeckbtn.setDisable(false);
                                usefulJunk_bool = true;
                                break;

                            case "Wormhole":
                                Stage wormStage = new Stage();
                                Button b1 = new Button(players.get(0).toString());
                                Button b2 = new Button(players.get(1).toString());
                                Button b3 = new Button();
                                Button b4 = new Button();
                                Button b5 = new Button();
                                if (game.getFullPlayerCount() == 3)
                                    b3.setText(players.get(2).toString());
                                if (game.getFullPlayerCount() == 4) {
                                    b3.setText(players.get(2).toString());
                                    b4.setText(players.get(3).toString());
                                }
                                if (game.getFullPlayerCount() == 5) {
                                    b3.setText(players.get(2).toString());
                                    b4.setText(players.get(3).toString());
                                    b5.setText(players.get(4).toString());
                                }

                                if (game.getCurrentPlayer().toString()
                                        .equals(players.get(0).toString()))
                                    b1.setVisible(false);
                                else if (game.getCurrentPlayer().toString()
                                        .equals(players.get(1).toString()))
                                    b2.setVisible(false);
                                else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                        && game.getFullPlayerCount() >= 3)
                                    b3.setVisible(false);
                                else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                        && game.getFullPlayerCount() >= 4)
                                    b4.setVisible(false);
                                else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                        && game.getFullPlayerCount() == 5)
                                    b5.setVisible(false);
                                VBox buttonBox = new VBox();
                                buttonBox.setSpacing(5);
                                buttonBox.setBackground(Background.fill(Color.TRANSPARENT));
                                buttonBox.getChildren().addAll(b1, b2, b3, b4, b5);
                                Scene buttonScene = new Scene(buttonBox, 300, 400, Color.BLACK);
                                wormStage.setScene(buttonScene);
                                wormStage.show();

                                b1.setOnAction(ev -> {
                                    game.getCurrentPlayer().swapTrack(players1.get(0));
                                    double x1 = 0;
                                    double y1 = 0;
                                    double x2 = 0;
                                    double y2 = 0;
                                    if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(1).toString())) {
                                        x1 = player2.getLayoutX();
                                        y1 = player2.getLayoutY();
                                        player2.setLayoutX(player1.getLayoutX());
                                        player2.setLayoutY(player1.getLayoutY());
                                        player1.setLayoutX(x1);
                                        player1.setLayoutY(y1);

                                        x2 = l2.getLayoutX();
                                        y2 = l2.getLayoutY();
                                        l2.setLayoutX(l1.getLayoutX());
                                        l2.setLayoutY(l1.getLayoutY());
                                        l1.setLayoutX(x2);
                                        l1.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(2).toString())) {
                                        x1 = player3.getLayoutX();
                                        y1 = player3.getLayoutY();
                                        player3.setLayoutX(player1.getLayoutX());
                                        player3.setLayoutY(player1.getLayoutY());
                                        player1.setLayoutX(x1);
                                        player1.setLayoutY(y1);

                                        x2 = l3.getLayoutX();
                                        y2 = l3.getLayoutY();
                                        l3.setLayoutX(l1.getLayoutX());
                                        l3.setLayoutY(l1.getLayoutY());
                                        l1.setLayoutX(x2);
                                        l1.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(3).toString())) {
                                        x1 = player4.getLayoutX();
                                        y1 = player4.getLayoutY();
                                        player4.setLayoutX(player1.getLayoutX());
                                        player4.setLayoutY(player1.getLayoutY());
                                        player1.setLayoutX(x1);
                                        player1.setLayoutY(y1);

                                        x2 = l4.getLayoutX();
                                        y2 = l4.getLayoutY();
                                        l4.setLayoutX(l1.getLayoutX());
                                        l4.setLayoutY(l1.getLayoutY());
                                        l1.setLayoutX(x2);
                                        l1.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(4).toString())) {
                                        x1 = player5.getLayoutX();
                                        y1 = player5.getLayoutY();
                                        player5.setLayoutX(player1.getLayoutX());
                                        player5.setLayoutY(player1.getLayoutY());
                                        player1.setLayoutX(x1);
                                        player1.setLayoutY(y1);

                                        x2 = l5.getLayoutX();
                                        y2 = l5.getLayoutY();
                                        l5.setLayoutX(l1.getLayoutX());
                                        l5.setLayoutY(l1.getLayoutY());
                                        l1.setLayoutX(x2);
                                        l1.setLayoutY(y2);
                                    }
                                    wormStage.close();
                                });
                                b2.setOnAction(ev -> {
                                    game.getCurrentPlayer().swapTrack(players1.get(1));
                                    double x1 = 0;
                                    double y1 = 0;
                                    double x2 = 0;
                                    double y2 = 0;
                                    if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(0).toString())) {
                                        x1 = player1.getLayoutX();
                                        y1 = player1.getLayoutY();
                                        player1.setLayoutX(player2.getLayoutX());
                                        player1.setLayoutY(player2.getLayoutY());
                                        player2.setLayoutX(x1);
                                        player2.setLayoutY(y1);

                                        x2 = l1.getLayoutX();
                                        y2 = l1.getLayoutY();
                                        l1.setLayoutX(l2.getLayoutX());
                                        l1.setLayoutY(l2.getLayoutY());
                                        l2.setLayoutX(x2);
                                        l2.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(2).toString())) {
                                        x1 = player3.getLayoutX();
                                        y1 = player3.getLayoutY();
                                        player3.setLayoutX(player2.getLayoutX());
                                        player3.setLayoutY(player2.getLayoutY());
                                        player2.setLayoutX(x1);
                                        player2.setLayoutY(y1);

                                        x2 = l3.getLayoutX();
                                        y2 = l3.getLayoutY();
                                        l3.setLayoutX(l2.getLayoutX());
                                        l3.setLayoutY(l2.getLayoutY());
                                        l2.setLayoutX(x2);
                                        l2.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(3).toString())) {
                                        x1 = player4.getLayoutX();
                                        y1 = player4.getLayoutY();
                                        player4.setLayoutX(player2.getLayoutX());
                                        player4.setLayoutY(player2.getLayoutY());
                                        player2.setLayoutX(x1);
                                        player2.setLayoutY(y1);

                                        x2 = l4.getLayoutX();
                                        y2 = l4.getLayoutY();
                                        l4.setLayoutX(l2.getLayoutX());
                                        l4.setLayoutY(l2.getLayoutY());
                                        l2.setLayoutX(x2);
                                        l2.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(4).toString())) {
                                        x1 = player5.getLayoutX();
                                        y1 = player5.getLayoutY();
                                        player5.setLayoutX(player2.getLayoutX());
                                        player5.setLayoutY(player2.getLayoutY());
                                        player2.setLayoutX(x1);
                                        player2.setLayoutY(y1);

                                        x2 = l5.getLayoutX();
                                        y2 = l5.getLayoutY();
                                        l5.setLayoutX(l2.getLayoutX());
                                        l5.setLayoutY(l2.getLayoutY());
                                        l2.setLayoutX(x2);
                                        l2.setLayoutY(y2);
                                    }
                                    wormStage.close();
                                });
                                b3.setOnAction(ev -> {
                                    game.getCurrentPlayer().swapTrack(players1.get(2));
                                    double x1 = 0;
                                    double y1 = 0;
                                    double x2 = 0;
                                    double y2 = 0;
                                    if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(0).toString())) {
                                        x1 = player1.getLayoutX();
                                        y1 = player1.getLayoutY();
                                        player1.setLayoutX(player3.getLayoutX());
                                        player1.setLayoutY(player3.getLayoutY());
                                        player3.setLayoutX(x1);
                                        player3.setLayoutY(y1);

                                        x2 = l1.getLayoutX();
                                        y2 = l1.getLayoutY();
                                        l1.setLayoutX(l3.getLayoutX());
                                        l1.setLayoutY(l3.getLayoutY());
                                        l3.setLayoutX(x2);
                                        l3.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(1).toString())) {
                                        x1 = player2.getLayoutX();
                                        y1 = player2.getLayoutY();
                                        player2.setLayoutX(player3.getLayoutX());
                                        player2.setLayoutY(player3.getLayoutY());
                                        player3.setLayoutX(x1);
                                        player3.setLayoutY(y1);

                                        x2 = l2.getLayoutX();
                                        y2 = l2.getLayoutY();
                                        l2.setLayoutX(l3.getLayoutX());
                                        l2.setLayoutY(l3.getLayoutY());
                                        l3.setLayoutX(x2);
                                        l3.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(3).toString())) {
                                        x1 = player4.getLayoutX();
                                        y1 = player4.getLayoutY();
                                        player4.setLayoutX(player3.getLayoutX());
                                        player4.setLayoutY(player3.getLayoutY());
                                        player3.setLayoutX(x1);
                                        player3.setLayoutY(y1);

                                        x2 = l4.getLayoutX();
                                        y2 = l4.getLayoutY();
                                        l4.setLayoutX(l3.getLayoutX());
                                        l4.setLayoutY(l3.getLayoutY());
                                        l3.setLayoutX(x2);
                                        l3.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(4).toString())) {
                                        x1 = player5.getLayoutX();
                                        y1 = player5.getLayoutY();
                                        player5.setLayoutX(player3.getLayoutX());
                                        player5.setLayoutY(player3.getLayoutY());
                                        player3.setLayoutX(x1);
                                        player3.setLayoutY(y1);

                                        x2 = l5.getLayoutX();
                                        y2 = l5.getLayoutY();
                                        l5.setLayoutX(l3.getLayoutX());
                                        l5.setLayoutY(l3.getLayoutY());
                                        l3.setLayoutX(x2);
                                        l3.setLayoutY(y2);
                                    }
                                    wormStage.close();
                                });
                                b4.setOnAction(ev -> {
                                    game.getCurrentPlayer().swapTrack(players1.get(3));
                                    double x1 = 0;
                                    double y1 = 0;
                                    double x2 = 0;
                                    double y2 = 0;
                                    if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(0).toString())) {
                                        x1 = player1.getLayoutX();
                                        y1 = player1.getLayoutY();
                                        player1.setLayoutX(player4.getLayoutX());
                                        player1.setLayoutY(player4.getLayoutY());
                                        player4.setLayoutX(x1);
                                        player4.setLayoutY(y1);

                                        x2 = l1.getLayoutX();
                                        y2 = l1.getLayoutY();
                                        l1.setLayoutX(l4.getLayoutX());
                                        l1.setLayoutY(l4.getLayoutY());
                                        l4.setLayoutX(x2);
                                        l4.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(1).toString())) {
                                        x1 = player2.getLayoutX();
                                        y1 = player2.getLayoutY();
                                        player2.setLayoutX(player4.getLayoutX());
                                        player2.setLayoutY(player4.getLayoutY());
                                        player4.setLayoutX(x1);
                                        player4.setLayoutY(y1);

                                        x2 = l2.getLayoutX();
                                        y2 = l2.getLayoutY();
                                        l2.setLayoutX(l4.getLayoutX());
                                        l2.setLayoutY(l4.getLayoutY());
                                        l4.setLayoutX(x2);
                                        l4.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(2).toString())) {
                                        x1 = player3.getLayoutX();
                                        y1 = player3.getLayoutY();
                                        player3.setLayoutX(player4.getLayoutX());
                                        player3.setLayoutY(player4.getLayoutY());
                                        player4.setLayoutX(x1);
                                        player4.setLayoutY(y1);

                                        x2 = l3.getLayoutX();
                                        y2 = l3.getLayoutY();
                                        l3.setLayoutX(l4.getLayoutX());
                                        l3.setLayoutY(l4.getLayoutY());
                                        l4.setLayoutX(x2);
                                        l4.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(4).toString())) {
                                        x1 = player5.getLayoutX();
                                        y1 = player5.getLayoutY();
                                        player5.setLayoutX(player4.getLayoutX());
                                        player5.setLayoutY(player4.getLayoutY());
                                        player4.setLayoutX(x1);
                                        player4.setLayoutY(y1);

                                        x2 = l5.getLayoutX();
                                        y2 = l5.getLayoutY();
                                        l5.setLayoutX(l4.getLayoutX());
                                        l5.setLayoutY(l4.getLayoutY());
                                        l4.setLayoutX(x2);
                                        l4.setLayoutY(y2);
                                    }
                                    wormStage.close();
                                });
                                b5.setOnAction(ev -> {
                                    game.getCurrentPlayer().swapTrack(players1.get(4));
                                    double x1 = 0;
                                    double y1 = 0;
                                    double x2 = 0;
                                    double y2 = 0;
                                    if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(0).toString())) {
                                        x1 = player1.getLayoutX();
                                        y1 = player1.getLayoutY();
                                        player1.setLayoutX(player5.getLayoutX());
                                        player1.setLayoutY(player5.getLayoutY());
                                        player5.setLayoutX(x1);
                                        player5.setLayoutY(y1);

                                        x2 = l1.getLayoutX();
                                        y2 = l1.getLayoutY();
                                        l1.setLayoutX(l5.getLayoutX());
                                        l1.setLayoutY(l5.getLayoutY());
                                        l5.setLayoutX(x2);
                                        l5.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(1).toString())) {
                                        x1 = player2.getLayoutX();
                                        y1 = player2.getLayoutY();
                                        player2.setLayoutX(player5.getLayoutX());
                                        player2.setLayoutY(player5.getLayoutY());
                                        player5.setLayoutX(x1);
                                        player5.setLayoutY(y1);

                                        x2 = l2.getLayoutX();
                                        y2 = l2.getLayoutY();
                                        l2.setLayoutX(l5.getLayoutX());
                                        l2.setLayoutY(l5.getLayoutY());
                                        l5.setLayoutX(x2);
                                        l5.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(2).toString())) {
                                        x1 = player3.getLayoutX();
                                        y1 = player3.getLayoutY();
                                        player3.setLayoutX(player5.getLayoutX());
                                        player3.setLayoutY(player5.getLayoutY());
                                        player5.setLayoutX(x1);
                                        player5.setLayoutY(y1);

                                        x2 = l3.getLayoutX();
                                        y2 = l3.getLayoutY();
                                        l3.setLayoutX(l5.getLayoutX());
                                        l3.setLayoutY(l5.getLayoutY());
                                        l5.setLayoutX(x2);
                                        l5.setLayoutY(y2);
                                    } else if (game.getCurrentPlayer().toString()
                                            .equals(players1.get(3).toString())) {
                                        x1 = player4.getLayoutX();
                                        y1 = player4.getLayoutY();
                                        player4.setLayoutX(player5.getLayoutX());
                                        player4.setLayoutY(player5.getLayoutY());
                                        player5.setLayoutX(x1);
                                        player5.setLayoutY(y1);

                                        x2 = l4.getLayoutX();
                                        y2 = l4.getLayoutY();
                                        l4.setLayoutX(l5.getLayoutX());
                                        l4.setLayoutY(l5.getLayoutY());
                                        l5.setLayoutX(x2);
                                        l5.setLayoutY(y2);
                                    }
                                    wormStage.close();
                                });
                                break;

                            case "Meteoroid":
                                List<Card> hand = game.getCurrentPlayer().getHand();
                                if (hand.size() >= 6) {
                                    Stage meteoroidStage = new Stage();
                                    List<Card> chosenCards = new ArrayList<>();
                                    List<CheckBox> handBox = new ArrayList<>();
                                    VBox vb = new VBox(6);
                                    for (Card i : hand)
                                        handBox.add(new CheckBox(i.toString()));
                                    vb.getChildren().addAll(handBox);
                                    Button confirmButton = new Button("Click to discard cards");
                                    for (CheckBox i : handBox) {
                                        i.selectedProperty().addListener((o, oldV, newV) -> {
                                            if (newV == true) {
                                                chosenCards.add(hand.get(handBox.indexOf(i)));
                                                sel++;
                                                if (sel >= 2) {
                                                    for (CheckBox j : handBox) {
                                                        if (i != j && j.isSelected() == false)
                                                            j.setDisable(true);
                                                    }
                                                }
                                            } else {
                                                chosenCards.remove(hand.get(handBox.indexOf(i)));
                                                sel--;
                                                if (sel < 2) {
                                                    for (CheckBox j : handBox) {
                                                        if (i != j)
                                                            j.setDisable(false);
                                                    }
                                                }
                                            }
                                        });
                                    }
                                    vb.getChildren().add(confirmButton);
                                    vb.setBackground(Background.fill(Color.TRANSPARENT));
                                    Scene sce = new Scene(vb, 200, 300, Color.WHITE);
                                    // sce.getStylesheets().add("myStyles5.css");
                                    meteoroidStage.setScene(sce);
                                    meteoroidStage.show();
                                    confirmButton.setOnAction(evt -> {
                                        if (chosenCards.size() == 2) {
                                            game.getGameDiscard().add(chosenCards.get(0));
                                            game.getGameDiscard().add(chosenCards.get(1));
                                            game.getCurrentPlayer().hack(chosenCards.get(0));
                                            game.getCurrentPlayer().hack(chosenCards.get(1));
                                            Alert message = new Alert(Alert.AlertType.INFORMATION);
                                            message.setContentText("Cards discarded successfully");
                                            message.showAndWait();
                                            meteoroidStage.close();
                                        } else {
                                            Alert message = new Alert(Alert.AlertType.WARNING);
                                            message.setContentText("Please select 2 cards!");
                                            message.showAndWait();
                                        }
                                    });
                                } else {
                                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                                    message.setContentText(
                                            "Less than 6 cards!\nProceed without discarding any!");
                                    message.showAndWait();
                                }
                                break;

                            case "Blank space":

                                Alert alert_card = new Alert(Alert.AlertType.INFORMATION);
                                alert_card.setContentText(c.toString() + "\n" + c.getDescription());
                                alert_card.showAndWait();

                                break;

                            case "Hyperspace":

                                Card c_space = game.getSpaceDeck().draw();

                                if (c_space.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false) {
                                    if (c_space.toString().equals(SpaceDeck.ASTEROID_FIELD)) {
                                        int o = game.getCurrentPlayer().breathe();
                                        if (o == 0) {
                                            Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_player1
                                                    .setContentText("Player " + game.getCurrentPlayer().toString());
                                            alert_player1.showAndWait();
                                        }

                                        else {
                                            Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_oxygencount1.setContentText("Oxygen remaning: " + (o));
                                            alert_oxygencount1.showAndWait();
                                        }
                                    }

                                    if (c_space.toString().equals(SpaceDeck.COSMIC_RADIATION)) {
                                        int o = game.getCurrentPlayer().breathe();
                                        if (game.getCurrentPlayer().oxygenRemaining() == 0) {
                                            Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_player1
                                                    .setContentText("Player " + game.getCurrentPlayer().toString());
                                            alert_player1.showAndWait();
                                        } else {
                                            Alert alert_oxygencount1 = new Alert(Alert.AlertType.INFORMATION);
                                            alert_oxygencount1
                                                    .setContentText("Oxygen discared!!\n Remaning oxygen are: " + o);
                                            alert_oxygencount1.showAndWait();
                                        }
                                    }
                                    if (c_space.toString().equals(SpaceDeck.MYSTERIOUS_NEBULA)) {
                                        for (int i = 0; i < 2; i++) {
                                            if (game.getGameDeck().size() > 0) {
                                                Card cd = game.getGameDeck().draw();
                                                game.getCurrentPlayer().addToHand(cd);
                                                Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                                                alert_card1.setContentText(
                                                        cd.toString() + " card added to hand\n " + cd.getDescription());
                                                alert_card1.showAndWait();
                                            } else {
                                                GameDiscardbtn.setDisable(false);
                                                Alert alert_deck1 = new Alert(Alert.AlertType.INFORMATION);
                                                alert_deck1.setContentText("Empty deck please merge decks!!");
                                                alert_deck1.showAndWait();
                                            }
                                        }
                                    }

                                    if (c_space.toString().equals(SpaceDeck.SOLAR_FLARE)) {
                                        ActionCardbtn.setDisable(true);
                                        Alert alert_player1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_player1.setContentText("Player " + game.getCurrentPlayer().toString()
                                                + " has melted eye balls\n Player will not be ale to play Action cards untilthe card is behind you");
                                        alert_player1.showAndWait();
                                    }

                                    if (c_space.toString().equals(SpaceDeck.USEFUL_JUNK)) {
                                        GameDeckbtn.setDisable(false);
                                        usefulJunk_bool = true;
                                    }

                                    if (c_space.toString().equals(SpaceDeck.METEOROID)) {
                                        List<Card> hand1 = game.getCurrentPlayer().getHand();
                                        if (hand1.size() >= 6) {
                                            Stage meteoroidStage = new Stage();
                                            List<Card> chosenCards = new ArrayList<>();
                                            List<CheckBox> handBox = new ArrayList<>();
                                            VBox vb = new VBox(6);
                                            for (Card i : hand1)
                                                handBox.add(new CheckBox(i.toString()));
                                            vb.getChildren().addAll(handBox);
                                            Button confirmButton = new Button("Click to discard cards");
                                            for (CheckBox i : handBox) {
                                                i.selectedProperty().addListener((o, oldV, newV) -> {
                                                    if (newV == true) {
                                                        chosenCards.add(hand1.get(handBox.indexOf(i)));
                                                        sel++;
                                                        if (sel >= 2) {
                                                            for (CheckBox j : handBox) {
                                                                if (i != j && j.isSelected() == false)
                                                                    j.setDisable(true);
                                                            }
                                                        }
                                                    } else {
                                                        chosenCards.remove(hand1.get(handBox.indexOf(i)));
                                                        sel--;
                                                        if (sel < 2) {
                                                            for (CheckBox j : handBox) {
                                                                if (i != j)
                                                                    j.setDisable(false);
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                            vb.getChildren().add(confirmButton);
                                            vb.setBackground(Background.fill(Color.TRANSPARENT));
                                            Scene sce = new Scene(vb, 200, 300, Color.WHITE);
                                            // sce.getStylesheets().add("myStyles5.css");
                                            meteoroidStage.setScene(sce);
                                            meteoroidStage.show();
                                            confirmButton.setOnAction(evt -> {
                                                if (chosenCards.size() == 2) {
                                                    game.getGameDiscard().add(chosenCards.get(0));
                                                    game.getGameDiscard().add(chosenCards.get(1));
                                                    game.getCurrentPlayer().hack(chosenCards.get(0));
                                                    game.getCurrentPlayer().hack(chosenCards.get(1));
                                                    Alert message = new Alert(Alert.AlertType.INFORMATION);
                                                    message.setContentText("Cards discarded successfully");
                                                    message.showAndWait();
                                                    meteoroidStage.close();
                                                } else {
                                                    Alert message = new Alert(Alert.AlertType.WARNING);
                                                    message.setContentText("Please select 2 cards!");
                                                    message.showAndWait();
                                                }
                                            });
                                        } else {
                                            Alert message = new Alert(Alert.AlertType.INFORMATION);
                                            message.setContentText(
                                                    "Less than 6 cards!\nProceed without discarding any!");
                                            message.showAndWait();
                                        }

                                    }

                                    if (c_space.toString().equals(SpaceDeck.WORMHOLE)) {
                                        Stage wormStage1 = new Stage();
                                        Button b11 = new Button(players.get(0).toString());
                                        Button b21 = new Button(players.get(1).toString());
                                        Button b31 = new Button();
                                        Button b41 = new Button();
                                        Button b51 = new Button();
                                        if (game.getFullPlayerCount() == 3)
                                            b31.setText(players.get(2).toString());
                                        if (game.getFullPlayerCount() == 4) {
                                            b31.setText(players.get(2).toString());
                                            b41.setText(players.get(3).toString());
                                        }
                                        if (game.getFullPlayerCount() == 5) {
                                            b31.setText(players.get(2).toString());
                                            b41.setText(players.get(3).toString());
                                            b51.setText(players.get(4).toString());
                                        }

                                        if (game.getCurrentPlayer().toString()
                                                .equals(players.get(0).toString()))
                                            b11.setVisible(false);
                                        else if (game.getCurrentPlayer().toString()
                                                .equals(players.get(1).toString()))
                                            b21.setVisible(false);
                                        else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                                && game.getFullPlayerCount() >= 3)
                                            b31.setVisible(false);
                                        else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                                && game.getFullPlayerCount() >= 4)
                                            b41.setVisible(false);
                                        else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                                && game.getFullPlayerCount() == 5)
                                            b51.setVisible(false);
                                        VBox buttonBox1 = new VBox();
                                        buttonBox1.setSpacing(5);
                                        buttonBox1.setBackground(Background.fill(Color.TRANSPARENT));
                                        buttonBox1.getChildren().addAll(b11, b21, b31, b41, b51);
                                        Scene buttonScene1 = new Scene(buttonBox1, 300, 400, Color.BLACK);
                                        wormStage1.setScene(buttonScene1);
                                        wormStage1.show();

                                        b11.setOnAction(ev -> {
                                            game.getCurrentPlayer().swapTrack(players1.get(0));
                                            double x1 = 0;
                                            double y1 = 0;
                                            double x2 = 0;
                                            double y2 = 0;
                                            if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(1).toString())) {
                                                x1 = player2.getLayoutX();
                                                y1 = player2.getLayoutY();
                                                player2.setLayoutX(player1.getLayoutX());
                                                player2.setLayoutY(player1.getLayoutY());
                                                player1.setLayoutX(x1);
                                                player1.setLayoutY(y1);

                                                x2 = l2.getLayoutX();
                                                y2 = l2.getLayoutY();
                                                l2.setLayoutX(l1.getLayoutX());
                                                l2.setLayoutY(l1.getLayoutY());
                                                l1.setLayoutX(x2);
                                                l1.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(2).toString())) {
                                                x1 = player3.getLayoutX();
                                                y1 = player3.getLayoutY();
                                                player3.setLayoutX(player1.getLayoutX());
                                                player3.setLayoutY(player1.getLayoutY());
                                                player1.setLayoutX(x1);
                                                player1.setLayoutY(y1);

                                                x2 = l3.getLayoutX();
                                                y2 = l3.getLayoutY();
                                                l3.setLayoutX(l1.getLayoutX());
                                                l3.setLayoutY(l1.getLayoutY());
                                                l1.setLayoutX(x2);
                                                l1.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(3).toString())) {
                                                x1 = player4.getLayoutX();
                                                y1 = player4.getLayoutY();
                                                player4.setLayoutX(player1.getLayoutX());
                                                player4.setLayoutY(player1.getLayoutY());
                                                player1.setLayoutX(x1);
                                                player1.setLayoutY(y1);

                                                x2 = l4.getLayoutX();
                                                y2 = l4.getLayoutY();
                                                l4.setLayoutX(l1.getLayoutX());
                                                l4.setLayoutY(l1.getLayoutY());
                                                l1.setLayoutX(x2);
                                                l1.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(4).toString())) {
                                                x1 = player5.getLayoutX();
                                                y1 = player5.getLayoutY();
                                                player5.setLayoutX(player1.getLayoutX());
                                                player5.setLayoutY(player1.getLayoutY());
                                                player1.setLayoutX(x1);
                                                player1.setLayoutY(y1);

                                                x2 = l5.getLayoutX();
                                                y2 = l5.getLayoutY();
                                                l5.setLayoutX(l1.getLayoutX());
                                                l5.setLayoutY(l1.getLayoutY());
                                                l1.setLayoutX(x2);
                                                l1.setLayoutY(y2);
                                            }
                                            wormStage1.close();
                                        });
                                        b21.setOnAction(ev -> {
                                            game.getCurrentPlayer().swapTrack(players1.get(1));
                                            double x1 = 0;
                                            double y1 = 0;
                                            double x2 = 0;
                                            double y2 = 0;
                                            if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(0).toString())) {
                                                x1 = player1.getLayoutX();
                                                y1 = player1.getLayoutY();
                                                player1.setLayoutX(player2.getLayoutX());
                                                player1.setLayoutY(player2.getLayoutY());
                                                player2.setLayoutX(x1);
                                                player2.setLayoutY(y1);

                                                x2 = l1.getLayoutX();
                                                y2 = l1.getLayoutY();
                                                l1.setLayoutX(l2.getLayoutX());
                                                l1.setLayoutY(l2.getLayoutY());
                                                l2.setLayoutX(x2);
                                                l2.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(2).toString())) {
                                                x1 = player3.getLayoutX();
                                                y1 = player3.getLayoutY();
                                                player3.setLayoutX(player2.getLayoutX());
                                                player3.setLayoutY(player2.getLayoutY());
                                                player2.setLayoutX(x1);
                                                player2.setLayoutY(y1);

                                                x2 = l3.getLayoutX();
                                                y2 = l3.getLayoutY();
                                                l3.setLayoutX(l2.getLayoutX());
                                                l3.setLayoutY(l2.getLayoutY());
                                                l2.setLayoutX(x2);
                                                l2.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(3).toString())) {
                                                x1 = player4.getLayoutX();
                                                y1 = player4.getLayoutY();
                                                player4.setLayoutX(player2.getLayoutX());
                                                player4.setLayoutY(player2.getLayoutY());
                                                player2.setLayoutX(x1);
                                                player2.setLayoutY(y1);

                                                x2 = l4.getLayoutX();
                                                y2 = l4.getLayoutY();
                                                l4.setLayoutX(l2.getLayoutX());
                                                l4.setLayoutY(l2.getLayoutY());
                                                l2.setLayoutX(x2);
                                                l2.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(4).toString())) {
                                                x1 = player5.getLayoutX();
                                                y1 = player5.getLayoutY();
                                                player5.setLayoutX(player2.getLayoutX());
                                                player5.setLayoutY(player2.getLayoutY());
                                                player2.setLayoutX(x1);
                                                player2.setLayoutY(y1);

                                                x2 = l5.getLayoutX();
                                                y2 = l5.getLayoutY();
                                                l5.setLayoutX(l2.getLayoutX());
                                                l5.setLayoutY(l2.getLayoutY());
                                                l2.setLayoutX(x2);
                                                l2.setLayoutY(y2);
                                            }
                                            wormStage1.close();
                                        });
                                        b31.setOnAction(ev -> {
                                            game.getCurrentPlayer().swapTrack(players1.get(2));
                                            double x1 = 0;
                                            double y1 = 0;
                                            double x2 = 0;
                                            double y2 = 0;
                                            if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(0).toString())) {
                                                x1 = player1.getLayoutX();
                                                y1 = player1.getLayoutY();
                                                player1.setLayoutX(player3.getLayoutX());
                                                player1.setLayoutY(player3.getLayoutY());
                                                player3.setLayoutX(x1);
                                                player3.setLayoutY(y1);

                                                x2 = l1.getLayoutX();
                                                y2 = l1.getLayoutY();
                                                l1.setLayoutX(l3.getLayoutX());
                                                l1.setLayoutY(l3.getLayoutY());
                                                l3.setLayoutX(x2);
                                                l3.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(1).toString())) {
                                                x1 = player2.getLayoutX();
                                                y1 = player2.getLayoutY();
                                                player2.setLayoutX(player3.getLayoutX());
                                                player2.setLayoutY(player3.getLayoutY());
                                                player3.setLayoutX(x1);
                                                player3.setLayoutY(y1);

                                                x2 = l2.getLayoutX();
                                                y2 = l2.getLayoutY();
                                                l2.setLayoutX(l3.getLayoutX());
                                                l2.setLayoutY(l3.getLayoutY());
                                                l3.setLayoutX(x2);
                                                l3.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(3).toString())) {
                                                x1 = player4.getLayoutX();
                                                y1 = player4.getLayoutY();
                                                player4.setLayoutX(player3.getLayoutX());
                                                player4.setLayoutY(player3.getLayoutY());
                                                player3.setLayoutX(x1);
                                                player3.setLayoutY(y1);

                                                x2 = l4.getLayoutX();
                                                y2 = l4.getLayoutY();
                                                l4.setLayoutX(l3.getLayoutX());
                                                l4.setLayoutY(l3.getLayoutY());
                                                l3.setLayoutX(x2);
                                                l3.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(4).toString())) {
                                                x1 = player5.getLayoutX();
                                                y1 = player5.getLayoutY();
                                                player5.setLayoutX(player3.getLayoutX());
                                                player5.setLayoutY(player3.getLayoutY());
                                                player3.setLayoutX(x1);
                                                player3.setLayoutY(y1);

                                                x2 = l5.getLayoutX();
                                                y2 = l5.getLayoutY();
                                                l5.setLayoutX(l3.getLayoutX());
                                                l5.setLayoutY(l3.getLayoutY());
                                                l3.setLayoutX(x2);
                                                l3.setLayoutY(y2);
                                            }
                                            wormStage1.close();
                                        });
                                        b41.setOnAction(ev -> {
                                            game.getCurrentPlayer().swapTrack(players1.get(3));
                                            double x1 = 0;
                                            double y1 = 0;
                                            double x2 = 0;
                                            double y2 = 0;
                                            if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(0).toString())) {
                                                x1 = player1.getLayoutX();
                                                y1 = player1.getLayoutY();
                                                player1.setLayoutX(player4.getLayoutX());
                                                player1.setLayoutY(player4.getLayoutY());
                                                player4.setLayoutX(x1);
                                                player4.setLayoutY(y1);

                                                x2 = l1.getLayoutX();
                                                y2 = l1.getLayoutY();
                                                l1.setLayoutX(l4.getLayoutX());
                                                l1.setLayoutY(l4.getLayoutY());
                                                l4.setLayoutX(x2);
                                                l4.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(1).toString())) {
                                                x1 = player2.getLayoutX();
                                                y1 = player2.getLayoutY();
                                                player2.setLayoutX(player4.getLayoutX());
                                                player2.setLayoutY(player4.getLayoutY());
                                                player4.setLayoutX(x1);
                                                player4.setLayoutY(y1);

                                                x2 = l2.getLayoutX();
                                                y2 = l2.getLayoutY();
                                                l2.setLayoutX(l4.getLayoutX());
                                                l2.setLayoutY(l4.getLayoutY());
                                                l4.setLayoutX(x2);
                                                l4.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(2).toString())) {
                                                x1 = player3.getLayoutX();
                                                y1 = player3.getLayoutY();
                                                player3.setLayoutX(player4.getLayoutX());
                                                player3.setLayoutY(player4.getLayoutY());
                                                player4.setLayoutX(x1);
                                                player4.setLayoutY(y1);

                                                x2 = l3.getLayoutX();
                                                y2 = l3.getLayoutY();
                                                l3.setLayoutX(l4.getLayoutX());
                                                l3.setLayoutY(l4.getLayoutY());
                                                l4.setLayoutX(x2);
                                                l4.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(4).toString())) {
                                                x1 = player5.getLayoutX();
                                                y1 = player5.getLayoutY();
                                                player5.setLayoutX(player4.getLayoutX());
                                                player5.setLayoutY(player4.getLayoutY());
                                                player4.setLayoutX(x1);
                                                player4.setLayoutY(y1);

                                                x2 = l5.getLayoutX();
                                                y2 = l5.getLayoutY();
                                                l5.setLayoutX(l4.getLayoutX());
                                                l5.setLayoutY(l4.getLayoutY());
                                                l4.setLayoutX(x2);
                                                l4.setLayoutY(y2);
                                            }
                                            wormStage1.close();
                                        });
                                        b51.setOnAction(ev -> {
                                            game.getCurrentPlayer().swapTrack(players1.get(4));
                                            double x1 = 0;
                                            double y1 = 0;
                                            double x2 = 0;
                                            double y2 = 0;
                                            if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(0).toString())) {
                                                x1 = player1.getLayoutX();
                                                y1 = player1.getLayoutY();
                                                player1.setLayoutX(player5.getLayoutX());
                                                player1.setLayoutY(player5.getLayoutY());
                                                player5.setLayoutX(x1);
                                                player5.setLayoutY(y1);

                                                x2 = l1.getLayoutX();
                                                y2 = l1.getLayoutY();
                                                l1.setLayoutX(l5.getLayoutX());
                                                l1.setLayoutY(l5.getLayoutY());
                                                l5.setLayoutX(x2);
                                                l5.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(1).toString())) {
                                                x1 = player2.getLayoutX();
                                                y1 = player2.getLayoutY();
                                                player2.setLayoutX(player5.getLayoutX());
                                                player2.setLayoutY(player5.getLayoutY());
                                                player5.setLayoutX(x1);
                                                player5.setLayoutY(y1);

                                                x2 = l2.getLayoutX();
                                                y2 = l2.getLayoutY();
                                                l2.setLayoutX(l5.getLayoutX());
                                                l2.setLayoutY(l5.getLayoutY());
                                                l5.setLayoutX(x2);
                                                l5.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(2).toString())) {
                                                x1 = player3.getLayoutX();
                                                y1 = player3.getLayoutY();
                                                player3.setLayoutX(player5.getLayoutX());
                                                player3.setLayoutY(player5.getLayoutY());
                                                player5.setLayoutX(x1);
                                                player5.setLayoutY(y1);

                                                x2 = l3.getLayoutX();
                                                y2 = l3.getLayoutY();
                                                l3.setLayoutX(l5.getLayoutX());
                                                l3.setLayoutY(l5.getLayoutY());
                                                l5.setLayoutX(x2);
                                                l5.setLayoutY(y2);
                                            } else if (game.getCurrentPlayer().toString()
                                                    .equals(players1.get(3).toString())) {
                                                x1 = player4.getLayoutX();
                                                y1 = player4.getLayoutY();
                                                player4.setLayoutX(player5.getLayoutX());
                                                player4.setLayoutY(player5.getLayoutY());
                                                player5.setLayoutX(x1);
                                                player5.setLayoutY(y1);

                                                x2 = l4.getLayoutX();
                                                y2 = l4.getLayoutY();
                                                l4.setLayoutX(l5.getLayoutX());
                                                l4.setLayoutY(l5.getLayoutY());
                                                l5.setLayoutX(x2);
                                                l5.setLayoutY(y2);
                                            }
                                            wormStage1.close();
                                        });
                                    }

                                    if (c_space.toString().equals(SpaceDeck.BLANK_SPACE)) {
                                        Alert alert_card1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_card1.setContentText(c_space.toString() + "\n" +
                                                c_space.getDescription());
                                        alert_card1.showAndWait();

                                    }

                                    Button btn = new Button(c_space.toString());
                                    btn.setStyle(
                                            "-fx-pref-width: 160px; -fx-pref-height: 120px; -fx-background-color:#868886;");
                                    btn.setOnAction(ev -> {
                                        Alert alert_card12 = new Alert(Alert.AlertType.INFORMATION);
                                        alert_card12.setContentText(c_space.getDescription());
                                        alert_card12.setTitle(c_space.toString());
                                        alert_card12.showAndWait();
                                    });
                                    ActionCardbtn.setDisable(true);
                                    ViewHandbtn.setDisable(true);
                                    Breathebtn.setDisable(true);
                                    EndTurnbtn.setDisable(false);

                                   if ((c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false)
                                && game.getCurrentPlayer().isAlive() == true) {
                            p2.getChildren().add(btn);
                            if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                playerCard1.add(btn);
                                btn.relocate(player1.getLayoutX()+8, player1.getLayoutY());
                                player1.relocate(player1.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(1).toString())) {
                                playerCard2.add(btn);
                                btn.relocate(player2.getLayoutX()+8, player2.getLayoutY());
                                player2.relocate(player2.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                    && game.getFullPlayerCount() >= 3) {
                                playerCard3.add(btn);
                                btn.relocate(player3.getLayoutX()+8, player3.getLayoutY());
                                player3.relocate(player3.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                    && game.getFullPlayerCount() >= 4) {
                                playerCard4.add(btn);
                                btn.relocate(player4.getLayoutX()+8, player4.getLayoutY());
                                player4.relocate(player4.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                    && game.getFullPlayerCount() == 5) {
                                playerCard5.add(btn);
                                btn.relocate(player5.getLayoutX()+8, player5.getLayoutY());
                                player5.relocate(player5.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            }

                        }
                                    if ((c_space.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == true)
                                            && game.getCurrentPlayer().isAlive() == true) {
                                        if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                            game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                            game.getCurrentPlayer().getTrack()
                                                    .remove(game.getCurrentPlayer().peekAtTrack());
                                            player1.relocate(playerCard1.get(playerCard1.size() - 1).getLayoutX(),
                                                    playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                            p2.getChildren().remove(playerCard1.get(playerCard1.size() - 1));
                                            playerCard1.remove(playerCard1.get(playerCard1.size() - 1));
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players.get(1).toString())) {
                                            game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                            game.getCurrentPlayer().getTrack()
                                                    .remove(game.getCurrentPlayer().peekAtTrack());
                                            player2.relocate(playerCard2.get(playerCard2.size() - 1).getLayoutX(),
                                                    playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                            p2.getChildren().remove(playerCard2.get(playerCard2.size() - 1));
                                            playerCard2.remove(playerCard2.get(playerCard2.size() - 1));
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players.get(2).toString())) {
                                            game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                            game.getCurrentPlayer().getTrack()
                                                    .remove(game.getCurrentPlayer().peekAtTrack());
                                            player3.relocate(playerCard3.get(playerCard3.size() - 1).getLayoutX(),
                                                    playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                            p2.getChildren().remove(playerCard3.get(playerCard3.size() - 1));
                                            playerCard3.remove(playerCard3.get(playerCard3.size() - 1));
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players.get(3).toString())) {
                                            game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                            game.getCurrentPlayer().getTrack()
                                                    .remove(game.getCurrentPlayer().peekAtTrack());
                                            player4.relocate(playerCard4.get(playerCard4.size() - 1).getLayoutX(),
                                                    playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                            p2.getChildren().remove(playerCard4.get(playerCard4.size() - 1));
                                            playerCard4.remove(playerCard4.get(playerCard4.size() - 1));
                                        } else if (game.getCurrentPlayer().toString()
                                                .equals(players.get(4).toString())) {
                                            game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                            game.getCurrentPlayer().getTrack()
                                                    .remove(game.getCurrentPlayer().peekAtTrack());
                                            player5.relocate(playerCard5.get(playerCard5.size() - 1).getLayoutX(),
                                                    playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                            p2.getChildren().remove(playerCard5.get(playerCard5.size() - 1));
                                            playerCard5.remove(playerCard5.get(playerCard5.size() - 1));
                                        }
                                    }
                                }
                                break;
                        }

                        Button btn = new Button(c.toString());
                        btn.setStyle("-fx-pref-width: 160px; -fx-pref-height: 80px; -fx-background-color: #868886;");
                        btn.setOnAction(ev -> {
                            Alert alert_card = new Alert(Alert.AlertType.INFORMATION);
                            alert_card.setContentText(c.getDescription());
                            alert_card.setTitle(c.toString());
                            alert_card.showAndWait();
                        });
                        ActionCardbtn.setDisable(true);
                        Breathebtn.setDisable(true);
                        EndTurnbtn.setDisable(false);

                        if ((c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == false)
                                && game.getCurrentPlayer().isAlive() == true) {
                            p2.getChildren().add(btn);
                            if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                playerCard1.add(btn);
                                btn.relocate(player1.getLayoutX()+8, player1.getLayoutY());
                                player1.relocate(player1.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(1).toString())) {
                                playerCard2.add(btn);
                                btn.relocate(player2.getLayoutX()+8, player2.getLayoutY());
                                player2.relocate(player2.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())
                                    && game.getFullPlayerCount() >= 3) {
                                playerCard3.add(btn);
                                btn.relocate(player3.getLayoutX()+8, player3.getLayoutY());
                                player3.relocate(player3.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())
                                    && game.getFullPlayerCount() >= 4) {
                                playerCard4.add(btn);
                                btn.relocate(player4.getLayoutX()+8, player4.getLayoutY());
                                player4.relocate(player4.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            } else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())
                                    && game.getFullPlayerCount() == 5) {
                                playerCard5.add(btn);
                                btn.relocate(player5.getLayoutX()+8, player5.getLayoutY());
                                player5.relocate(player5.getLayoutX(),
                                        600 - (game.getCurrentPlayer().getTrack().size()) * 90);
                            }

                        }
                        if ((c.toString().equals(SpaceDeck.GRAVITATIONAL_ANOMALY) == true)
                                && game.getCurrentPlayer().isAlive() == true) {
                            if (game.getCurrentPlayer().toString().equals(players.get(0).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack().remove(game.getCurrentPlayer().peekAtTrack());
                                player1.relocate(playerCard1.get(playerCard1.size() - 1).getLayoutX(),
                                        playerCard1.get(playerCard1.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard1.get(playerCard1.size() - 1));
                                playerCard1.remove(playerCard1.get(playerCard1.size() - 1));
                            } else if (game.getCurrentPlayer().toString().equals(players.get(1).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack().remove(game.getCurrentPlayer().peekAtTrack());
                                player2.relocate(playerCard2.get(playerCard2.size() - 1).getLayoutX(),
                                        playerCard2.get(playerCard2.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard2.get(playerCard2.size() - 1));
                                playerCard2.remove(playerCard2.get(playerCard2.size() - 1));
                            } else if (game.getCurrentPlayer().toString().equals(players.get(2).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack().remove(game.getCurrentPlayer().peekAtTrack());
                                player3.relocate(playerCard3.get(playerCard3.size() - 1).getLayoutX(),
                                        playerCard3.get(playerCard3.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard3.get(playerCard3.size() - 1));
                                playerCard3.remove(playerCard3.get(playerCard3.size() - 1));
                            } else if (game.getCurrentPlayer().toString().equals(players.get(3).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack().remove(game.getCurrentPlayer().peekAtTrack());
                                player4.relocate(playerCard4.get(playerCard4.size() - 1).getLayoutX(),
                                        playerCard4.get(playerCard4.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard4.get(playerCard4.size() - 1));
                                playerCard4.remove(playerCard4.get(playerCard4.size() - 1));
                            } else if (game.getCurrentPlayer().toString().equals(players.get(4).toString())) {
                                game.getSpaceDiscard().add(game.getCurrentPlayer().peekAtTrack());
                                game.getCurrentPlayer().getTrack().remove(game.getCurrentPlayer().peekAtTrack());
                                player5.relocate(playerCard5.get(playerCard5.size() - 1).getLayoutX(),
                                        playerCard5.get(playerCard5.size() - 1).getLayoutY());
                                p2.getChildren().remove(playerCard5.get(playerCard5.size() - 1));
                                playerCard5.remove(playerCard5.get(playerCard5.size() - 1));
                            }
                        }
                    }

                    EndTurnbtn.setDisable(false);
                    Travelbtn.setDisable(true);
                } catch (Exception act) {
                    System.out.println(act.getMessage());
                }
            });
            VBox vbox = new VBox();
            vbox.setSpacing(70);
            vbox.setAlignment(Pos.CENTER);
            Button b1 = new Button("Resume");
            b1.setOnAction(a -> {
                FadeTransition ft = new FadeTransition(Duration.millis(800), p2);
                ft.setFromValue(0.1);
                ft.setToValue(1.0);
                ft.play();
                stk3.getChildren().remove(vbox);
            });
            b1.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;");
            b1.setOnMouseEntered(
                    e -> b1.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a;"));
            b1.setOnMouseExited(
                    e -> b1.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;"));

            Button b2 = new Button("Save Game");
            b2.setOnAction(a -> {
                try {
                    StackPane stk_save1 = new StackPane();
                    Stage stg_saveGame = new Stage();
                    Pane pane_saveGame = new Pane();
                    TextField txt_file = new TextField();
                    txt_file.setPromptText("Enter file name");
                    Label l_file = new Label("File Name:");
                    Button save = new Button("Save");
                    save.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: black;");
                    save.setOnMouseEntered(
                            e -> save.setStyle(
                                    "-fx-pref-width: 120px; -fx-background-color: black; -fx-text-fill: white;"));
                    save.setOnMouseExited(
                            e -> save.setStyle(
                                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: black;"));
                    Button cancel = new Button("Cancel");
                    cancel.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: black;");
                    cancel.setOnMouseEntered(
                            e -> cancel.setStyle(
                                    "-fx-pref-width: 120px; -fx-background-color: black; -fx-text-fill: white;"));
                    cancel.setOnMouseExited(
                            e -> cancel.setStyle(
                                    "-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: black;"));
                    pane_saveGame.getChildren().addAll(txt_file, l_file, save, cancel);
                    stk_save1.getChildren().add(pane_saveGame);
                    txt_file.relocate(110, 70);
                    l_file.relocate(10, 71);
                    l_file.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");
                    save.relocate(20, 155);
                    cancel.relocate(170, 155);
                    Scene sc_save = new Scene(stk_save1, 300, 200);
                    stk_save1.setStyle("-fx-background-color:black;");
                    stg_saveGame.setScene(sc_save);
                    stg_saveGame.setTitle("Save Game");
                    stg_saveGame.show();
                    save.setOnAction(b -> {
                        click_audio();
                        try {
                            game.saveState("..\\FX\\src\\main\\Saved game\\" + txt_file.getText() + ".sav");
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Game saved in " + txt_file.getText() + " file.");
                            alert.setTitle("Game Save");
                            alert.showAndWait();
                            stg_saveGame.close();
                        } catch (Exception s) {
                            System.out.println(s.getMessage());
                        }
                    });
                    cancel.setOnAction(b -> {
                        click_audio();
                        stg_saveGame.close();
                    });
                } catch (Exception y) {
                    System.out.println(y.getMessage());
                }

            });
            b2.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;");
            b2.setOnMouseEntered(
                    e -> b2.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a;"));
            b2.setOnMouseExited(
                    e -> b2.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;"));
            Button b3 = new Button("Mute");
            b3.setOnAction(a -> {
                audioButtonCount++;
                if (audioButtonCount % 2 == 0) {
                    clip.start();
                    b3.setText("Mute Audio");
                } else {
                    clip.stop();
                    b3.setText("Unmute Audio");
                }
            });
            b3.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;");
            b3.setOnMouseEntered(
                    e -> b3.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a;"));
            b3.setOnMouseExited(
                    e -> b3.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;"));
            Button b4 = new Button("Exit");
            b4.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;");
            b4.setOnMouseEntered(
                    e -> b4.setStyle("-fx-pref-width: 120px; -fx-background-color: white; -fx-text-fill: #42485a;"));
            b4.setOnMouseExited(
                    e -> b4.setStyle("-fx-pref-width: 120px; -fx-background-color: #42485a; -fx-text-fill: white;"));
            b4.setOnAction(x -> {
                stg2.close();
            });
            vbox.getChildren().addAll(b1, b2, b3, b4);
            sc3.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    // escapeButtonCount++;
                    if (buttonsVisible == false) {
                        FadeTransition ft = new FadeTransition(Duration.millis(800), p2);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.1);
                        ft.play();
                        stk3.getChildren().add(vbox);
                        FadeTransition ft_b = new FadeTransition(Duration.millis(1000), vbox);
                        ft_b.setFromValue(0.0);
                        ft_b.setToValue(1.0);
                        ft_b.play();
                        buttonsVisible = true;
                    } else {
                        FadeTransition ft = new FadeTransition(Duration.millis(800), p2);
                        ft.setFromValue(0.1);
                        ft.setToValue(1.0);
                        ft.play();
                        stk3.getChildren().remove(vbox);
                        buttonsVisible = false;
                    }
                }
            });
        } catch (Exception a) {
            System.out.println(a.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
