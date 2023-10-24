package Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ItemPezGlobo extends Item{

    //Este item sirve permite al jugador rodar y destruir rocas al pulsar espacio.

    GamePanel gamePanel;

    public ItemPezGlobo(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        nombre = "Pez Globo";
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_globo.png")));
            sprite = uTool.escalarImagen(sprite,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);

        } catch (IOException e) {
            e.printStackTrace();
        }
        colision = true;

    }
}
