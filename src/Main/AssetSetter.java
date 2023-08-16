package Main;

import Entidades.NPC_Patrisio;
import Entidades.enemigos.Rana;
import Items.ItemPezGlobo;
import Items.Roca;

public class AssetSetter {

    //Esta clase sirve para instanciar todos los items y similares

    GamePanel gamePanel;

    public AssetSetter (GamePanel gamePanel){

        this.gamePanel = gamePanel;

    }

    public void setItems(){ //Este método define los objetos que existen en el juego, similar a como funcionan los tiles

        gamePanel.items[0] = new ItemPezGlobo(gamePanel);
        gamePanel.items[0].xMundo = 744;
        gamePanel.items[0].yMundo = 200;

        gamePanel.items[1] = new Roca(gamePanel);
        gamePanel.items[1].xMundo = 900;
        gamePanel.items[1].yMundo = 200;

        gamePanel.items[2] = new Roca(gamePanel);
        gamePanel.items[2].xMundo = 948;
        gamePanel.items[2].yMundo = 200;

        gamePanel.items[3] = new Roca(gamePanel);
        gamePanel.items[3].xMundo = 900;
        gamePanel.items[3].yMundo = 300;

        gamePanel.items[4] = new Roca(gamePanel);
        gamePanel.items[4].xMundo = 948;
        gamePanel.items[4].yMundo = 300;

    }
    public void setNPC(){

        gamePanel.npcs[0] = new NPC_Patrisio(gamePanel);
        gamePanel.npcs[0].xMundo = 551;
        gamePanel.npcs[0].yMundo = 184;

    }
    public void setEnemigo(){

        gamePanel.enemigos[0] = new Rana(gamePanel);
        gamePanel.enemigos[0].xMundo = 724;
        gamePanel.enemigos[0].yMundo = 515;

        gamePanel.enemigos[1] = new Rana(gamePanel);
        gamePanel.enemigos[1].xMundo = 870;
        gamePanel.enemigos[1].yMundo = 515;

    }

}