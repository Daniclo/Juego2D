package Main;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Roca extends Item {

    GamePanel gamePanel;

    public Roca(GamePanel gamePanel){

        this.gamePanel = gamePanel;

        nombre = "Roca";
        try {

            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/Roca.png")));
            sprite = uTool.escalarImagen(sprite,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);
            sprite2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/items/RocaDestruida.png")));
            sprite2 = uTool.escalarImagen(sprite2,gamePanel.tamanyoFinalSprites,gamePanel.tamanyoFinalSprites);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        colision = true;

    }

}
