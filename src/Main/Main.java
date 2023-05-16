package Main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        //Crear y mostrar ventana
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Juego 2D");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel); //El game panel es un nodo, lo añadimos al "stage" que en este caso es window.
        window.pack(); //Ajustar el tamaño de la ventana al tamaño óptimo de los nodos que contiene.

        window.setLocationRelativeTo(null); //Mostrar la pantalla en el centro
        window.setVisible(true);

        gamePanel.iniciarRelojDeJuego(); //Iniciar el Thread
    }
}