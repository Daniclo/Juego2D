package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputsTeclado implements KeyListener {

    //Esta clase InputsTeclado nos va a permitir interpretar los controles del juego.
    //Los métodos de la interfaz KeyListener permiten recibir inputs de teclado y reaccionar a ellos.

    GamePanel gamePanel;
    public InputsTeclado(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    //Estos valores booleanos indican cuando una tecla está siendo pulsada o no. Usaremos los métodos para
    //actualizarlos al pulsar y soltar las  teclas.
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, zPressed, xPressed;

    @Override
    public void keyTyped(KeyEvent e) {
        //En este caso, no voy a utilizar el método keyTyped.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); //asigna el valor int asociado a la tecla que ha sido presionada.
                                    //Esto nos va a servir para detectar qué tecla se pulsa.


        //PLAYSTATE
        if (gamePanel.gameState == gamePanel.playState){
            if (code == KeyEvent.VK_UP){
                upPressed = true;
            }
            if (code == KeyEvent.VK_LEFT){
                leftPressed = true;
            }
            if (code == KeyEvent.VK_DOWN){
                downPressed = true;
            }
            if (code == KeyEvent.VK_RIGHT){
                rightPressed = true;
            }
            if (code == KeyEvent.VK_SPACE){
                spacePressed = true;
            }
            if (code == KeyEvent.VK_Z){
                zPressed = true;
            }
            if (code == KeyEvent.VK_X){
                xPressed = true;
            }
            if (code == KeyEvent.VK_H){
                if (gamePanel.toogleHitboxes){
                    gamePanel.toogleHitboxes = false;
                }else {
                    gamePanel.toogleHitboxes = true;
                }
            }
        }
        //DIALOGUESTATE
        if (gamePanel.gameState == gamePanel.dialogueState){
            if (code == KeyEvent.VK_Z){
                gamePanel.gameState = gamePanel.playState;
                gamePanel.ui.dialogoActual = null;
            }
        }

        if (code == KeyEvent.VK_ENTER){ //Al pulsar ENTER, alternamos entre el modo jugar y el modo pausa.
            if (gamePanel.gameState == gamePanel.playState){
                gamePanel.gameState = gamePanel.pauseState;
            }
            else if (gamePanel.gameState == gamePanel.pauseState){ //Esto tiene que ser con else if, con solo if no funciona por algun motivo
                gamePanel.gameState = gamePanel.playState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode(); //en este caso, vamos a asignar el valor a false al soltar la tecla.

        if (code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if (code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if (code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
        if (code == KeyEvent.VK_Z){
            zPressed = false;
        }
        if (code == KeyEvent.VK_X){
            xPressed = false;
        }

    }
}