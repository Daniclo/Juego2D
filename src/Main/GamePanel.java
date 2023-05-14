package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //Ajustes de la pantalla
    final int tamañoSprites = 16; //16x16 píxeles.
    final int scale = 3; //16x16 es una resolución muy baja así que lo escalamos al triple de su tamaño para mostrarlo.
    final int tamañoFinalSprites = tamañoSprites*scale; //48x48 píxeles.

    //Estas 2 variables deciden el número de tiles (48x48) que conforman la pantalla.
    final int numMaxColumnas = 24;
    final int numMaxFilas = 18;

    //Multiplicando estas filas y columnas por el tamaño de un tile obtenemos el largo y ancho.
    final int anchoPantalla = tamañoFinalSprites*numMaxColumnas;
    final int altoPantalla = tamañoFinalSprites*numMaxFilas;

    int fps = 60; //Límite máximo de FPS que queremos que se reproduzcan

    InputsTeclado inputs = new InputsTeclado();
    Thread gameThread;

    //Sobre el jugador
    int jugadorX = 100;
    int jugadorY = 100;
    int velocidadJugador = 4;

    //Constructor de ventanas de juego
    public GamePanel(){
        this.setPreferredSize(new Dimension(anchoPantalla,altoPantalla)); //Definir las dimensiones de la pantalla
        this.setBackground(Color.black); //Definir el color del fondo de la pantalla
        this.setDoubleBuffered(true); //Activar el doble buffer (rendimiento)
        this.addKeyListener(inputs); //Asociar nuestra clase inputsTeclado con la pantalla para asignar controles
        this.setFocusable(true); //Es necesario que este componente sea focuseable para poder registrar inputs
    }

    public void iniciarRelojDeJuego(){ //Al llamar a este método comienza un loop que ejecuta el método run
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() { //Loop/reloj del juego
        while (gameThread != null){

            double intervaloEscritura = (double) 1000000000 /fps; // 1/60 segundos, se actualiza la pantalla 60 veces en un segundo.
            double siguienteEscritura = System.nanoTime() + intervaloEscritura; //Establecemos una espera de 1/60 segundos entre escritura y escritura.


            //Mientras se esté ejecutando este hilo, van a ocurrir 2 acciones de manera constante:
            //1. Actualizar la información del juego
            actualizar();

            //2. Actualizar la pantalla (printear los sprites actualizados)
            repaint(); //Llamada al método paintComponent

            try {
                double esperaEscritura = siguienteEscritura - System.nanoTime(); // Cuando se acaba el repaint, determina cuanto tiempo falta hasta el próximo repaint
                esperaEscritura = esperaEscritura/1000000; //Pasamos los nanosegundos a milisegundos

                if (esperaEscritura < 0){ //Evita que el valor de la variable de espera pueda ser negativo
                    esperaEscritura = 0;
                }

                Thread.sleep((long) esperaEscritura); //Bloquea todas las acciones del loop hasta que acabe el tiempo de espera para la siguiente escritura

                siguienteEscritura += intervaloEscritura; //El siguiente tiempo de escritura será en otros 1/60 segundos

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void actualizar(){ //En este método vamos a registrar los datos de movimiento de los sprites por la pantalla
                                //para enviarlos al método paintComponent y que los actualice.

        if (inputs.upPressed){ //Mover arriba
            jugadorY -= velocidadJugador;
        }
        if (inputs.downPressed){ //Mover abajo
            jugadorY += velocidadJugador;
        }
        if (inputs.leftPressed){ //Mover izquierda
            jugadorX -= velocidadJugador;
        }
        if (inputs.rightPressed){ //Mover derecha
            jugadorX += velocidadJugador;
        }

    }
    public void paintComponent(Graphics g){

        //La clase Graphics2D extiende la clase Graphics y da más control sobre ellas para renderizar 2D.
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(jugadorX,jugadorY,tamañoFinalSprites,tamañoFinalSprites); //Rectángulo de 48x48 en la posición inicial del jugador
        g2.dispose();
    }
}
