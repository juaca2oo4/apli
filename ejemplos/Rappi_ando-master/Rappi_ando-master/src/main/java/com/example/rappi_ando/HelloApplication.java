package com.example.rappi_ando;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage addEdge;
    private static Stage editEdge;
    
    private static Stage dijkstra;


    @Override
    public void start(Stage stage)  {
        showWindow("rappi-ando.fxml");
    }
    public static void showWindow(String fxml){
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
                Parent node = fxmlLoader.load();
                Scene scene = new Scene(node);
                Stage window = new Stage();
                Image img = new Image(new File("src/main/resources/com/example/rappi_ando/Logo.png").toURI().toString());
                window.getIcons().add(img);
                window.setScene(scene);
                window.show();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public static void showTransparentWindow(String fxml){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
            Parent node = fxmlLoader.load();
            Scene scene = new Scene(node);
            Image img = new Image(new File("src/main/resources/com/example/rappi_ando/Logo.png").toURI().toString());
            if(fxml.equals("addEdge.fxml") && addEdge==null) {
                addEdge = new Stage();
                addEdge.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                addEdge.setTitle("Rappi_ando!");
                addEdge.setX(600);
                addEdge.setY(250);
                addEdge.getIcons().add(img);
                addEdge.setScene(scene);
                addEdge.show();
            }else if(fxml.equals("addEdge.fxml")) {
                addEdge.show();
                addEdge.toFront();
            }else if(fxml.equals("editEdge.fxml")&&editEdge==null){
                editEdge = new Stage();
                editEdge.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                editEdge.setTitle("Rappi_ando!");
                editEdge.setX(600);
                editEdge.setY(250);
                editEdge.getIcons().add(img);
                editEdge.setScene(scene);
                editEdge.show();
            } else if (fxml.equals("editEdge.fxml")) {
                editEdge.show();
                editEdge.toFront();
            }else if(fxml.equals("dijkstra.fxml")&&dijkstra==null){
                dijkstra = new Stage();
                dijkstra.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
                dijkstra.setTitle("Rappi_ando!");
                dijkstra.setX(600);
                dijkstra.setY(250);
                dijkstra.getIcons().add(img);
                dijkstra.setScene(scene);
                dijkstra.show();
            } else if (fxml.equals("dijkstra.fxml")) {
                dijkstra.show();
                dijkstra.toFront();
            }
            
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}