package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputsTeclado implements KeyListener {

    //Esta clase InputsTeclado nos va a permitir interpretar los controles del juego.
    //Los métodos de la interfaz KeyListener permiten recibir inputs de teclado y reaccionar a ellos.

    //Estos valores booleanos indican cuando una tecla está siendo pulsada o no. Usaremos los métodos para
    //actualizarlos al pulsar y soltar las  teclas.
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //En este caso, no voy a utilizar el método keyTyped.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //asigna el valor int asociado a la tecla que ha sido presionada.
                                    //Esto nos va a servir para detectar qué tecla se pulsa.
        if (code == KeyEvent.VK_W){
            upPressed = true;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S){
            downPressed = true;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode(); //en este caso, vamos a asignar el valor a false al soltar la tecla.

        if (code == KeyEvent.VK_W){
            upPressed = false;
        }
        if (code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S){
            downPressed = false;
        }
        if (code == KeyEvent.VK_D){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
}