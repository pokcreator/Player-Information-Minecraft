
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class LoadSkin {
public static BufferedImage image;
      public static void loadSkin() {
          try {
              URL url = new URL("https://minotar.net/skin/" + Main.username.getText());
      image = ImageIO.read(url);

          } catch (IOException ex) {
              Logger.getLogger(LoadSkin.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
         public static Image downloadSkin(){
             try {
			String skinName = Main.namefile.getText() + ".png";			
			BufferedImage img = ImageIO.read(new URL("https://minotar.net/skin/" + Main.username.getText()));
			File file = new File(skinName);
			if (!file.exists()) {
				file.createNewFile();
			}
			ImageIO.write(img, "png", file);
			return img;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
         }

