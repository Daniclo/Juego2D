package Entidades;

import Main.GamePanel;
public class Mensaje {

    //Esta clase almacena una serie de diálogos que no pertenecen a ningún NPC y que se muestran cuando el personaje
    //activa ciertos eventos u obtiene determinados objetos.
    GamePanel gamePanel;
    String[] mensajes = new String[20]; //Este array define el número de mensajes del sistema.


    public Mensaje(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        setMensajes();
    }


    public void mostrarMensaje(int indexMensaje){ //Cuando se llama a este método desde otras clases se le pasa un índice de mensaje, la clase Mensaje busca en su array
                                                  //qué mensaje corresponde a ese índice y lo muestra.

        gamePanel.ui.dialogoActual = mensajes[indexMensaje];
        gamePanel.gameState = gamePanel.dialogueState;
    }


    public void setMensajes(){ //Este método define todos los mensajes que el sistema puede mostrar.

        this.mensajes[0] = "¡Has obtenido un pez globo! Mantén \npulsado espacio para rodar. Ah, y ten \ncuidado con las espinas.";
        this.mensajes[1] = "Quizás podrías romper esta roca con \nalguna habilidad.";

    }
}