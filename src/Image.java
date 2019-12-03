import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Image{
  public static void DisplayImage(final String fileName) throws Exception{
        JFrame jFrame = new JFrame("Item-image");
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        BufferedImage image = null;
        try{
          image = ImageIO.read(new File(fileName));
        }catch (Exception e){
          e.printStackTrace();
          System.exit(1);
        }
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        jFrame.getContentPane().add(jLabel, BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	
                jFrame.toFront();
                jFrame.repaint();
            }
        });
  }
}