package Main;

import Items.ItemPezGlobo;

public class AssetSetter {
    //Esta clase sirve para instanciar todos los items y similares

    GamePanel gamePanel;

    public AssetSetter (GamePanel gamePanel){

        this.gamePanel = gamePanel;

    }

    public void setItems(){ //Este método define los objetos que existen en el juego, similar a como funcionan los tiles

        gamePanel.items[0] = new ItemPezGlobo();
        gamePanel.items[0].xMundo = 744;
        gamePanel.items[0].yMundo = 200;

    }

}
