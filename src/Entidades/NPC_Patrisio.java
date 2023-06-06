package Entidades;

import Main.GamePanel;
import java.util.Random;

public class NPC_Patrisio extends Entidad{
    public NPC_Patrisio(GamePanel gamePanel) {

        super(gamePanel);
        apuntandoA = "abajo";
        speed = 4;
        getSprites();
        setDialogos();
    }

    public void getSprites(){ //Inicializamos los sprites del npc

        abajo1 = setUpSprite("/npc/Pinguino_abajo_1");
        abajo2 = setUpSprite("/npc/Pinguino_abajo_2");
        arriba1 = setUpSprite("/npc/Pinguino_arriba_1");
        arriba2 = setUpSprite("/npc/Pinguino_arriba_2");
        izquierda1 = setUpSprite("/npc/Pinguino_izquierda_1");
        izquierda2 = setUpSprite("/npc/Pinguino_izquierda_2");
        derecha1 = setUpSprite("/npc/Pinguino_derecha_1");
        derecha2 = setUpSprite("/npc/Pinguino_derecha_2");

    }

    @Override
    public void setAccion(){ //Este método define los movimientos del npc.

        contadorAcciones++;

        if (contadorAcciones == 180 ){ //Cada 180 frames realiza una acción

            //Decide de forma aleatoria a que dirección
            Random random = new Random();
            int i = random.nextInt(100)+1;

            if (i <= 25){
                apuntandoA = "arriba";
            }
            if (i > 25 && i <= 50){
                apuntandoA = "abajo";
            }
            if (i > 50 && i <= 75){
                apuntandoA = "izquierda";
            }
            if (i > 75){
                apuntandoA = "derecha";
            }

            contadorAcciones = 0;

        }
    }

    @Override
    public void actualizar(){

        setAccion();

        colisionOn = false;
        gamePanel.checkColisiones.checkTile(this);
        colisionOn = false;
        gamePanel.checkColisiones.checkTile(this);
        gamePanel.checkColisiones.checkEntitdad(this, gamePanel.entidades);
    }
    public void setDialogos(){

        dialogos[0] = "En 1614 un galeón japonés desembarcó a orillas del río guadalquivir.";
        dialogos[1] = "Y el emperador se enamoró de una bella sevillana y se quedó allí a vivir.";
        dialogos[2] = "Muchos años después, Paco Japón es el heredero al trono.";
        dialogos[3] = "¿Esto es wakamole no?";

    }
    @Override
    public void hablar(){

        gamePanel.ui.dialogoActual = dialogos[indexDialogo];
        indexDialogo++;

    }

}