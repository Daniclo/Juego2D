package Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class ItemPezVida extends Item{

    //Esta clase representa tanto la imagen que se va a usar para representar la vida como el objeto que fisicamente va a permitirte curarte (revisable)

    GamePanel gamePanel;

    public ItemPezVida(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        nombre = "Pez";
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_completo.png")));
            sprite = uTool.escalarImagen(sprite,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);
            sprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Pez_vacio.png")));
            sprite2 = uTool.escalarImagen(sprite2,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);
        } catch (IOException e) {
            e.printStackTrace();
        }
        colision = false;

    }
}
