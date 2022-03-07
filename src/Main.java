
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.logging.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Main {

    private JFrame main = new JFrame("Player Information");
    public static JTextField username = new JTextField();
    private JTextField player = new JTextField(), uuid = new JTextField();
    public static JTextField namefile = new JTextField();
    private JLabel luser = new JLabel("Username:"), nickname = new JLabel("Nick:"), UUID = new JLabel("UUID:"), skinpanel = new JLabel(), exitpanel = new JLabel("X"), filename = new JLabel("Enter the file name"), saveskin = new JLabel("Skin  is saved in the folder where the program is located");
    private JButton checkuser = new JButton("Get Information"), copyUUID = new JButton("Copy"), copynick = new JButton("Copy"), downloadskin = new JButton("Download Skin"), done = new JButton("Done");
    private static String skeckline;
    private JPanel downloadpanel = new JPanel(), downloadpanel2 = new JPanel();
    

    public Main() {

        URL iconURL = getClass().getResource("steve.png");
        ImageIcon icon = new ImageIcon(iconURL);
        main.setIconImage(icon.getImage());
        main.setSize(300, 400);
        main.setLayout(null);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        luser.setBounds(5, 15, 100, 20);
         downloadpanel.setBounds(10, 150, 270, 100);
         downloadpanel2.setBounds(3, 3, 264, 94);
         username.setBounds(70, 10, 200, 30);
         checkuser.setBounds(40, 40, 200, 25);
         player.setBounds(35, 70, 190, 25);
            nickname.setBounds(5, 70, 50, 25);
            UUID.setBounds(5, 100, 50, 25);
             uuid.setBounds(35, 100, 190, 25);
              copynick.setBounds(225, 70, 55, 25);
              copyUUID.setBounds(225, 100, 55, 25);
              skinpanel.setBounds(15, 130, 250, 200);
              downloadskin.setBounds(20, 335, 250, 25);
              exitpanel.setBounds(250, 0, 50, 20);
              filename.setBounds(60, 10, 200, 20);
              namefile.setBounds(20, 30, 225, 25);
              saveskin.setBounds(15, 50, 250, 20);
              done.setBounds(75, 70, 120, 20);
              
                      main.add(luser);
        main.add(username);
        main.add(checkuser);
        player.setEditable(false);
        main.add(player);
        main.add(nickname);
        uuid.setEditable(false);
        main.add(UUID);
        main.add(uuid);
        copynick.setFont(new Font("Tahoma", Font.PLAIN, 10));
        main.add(copynick);
        copyUUID.setFont(new Font("Tahoma", Font.PLAIN, 10));
        exitpanel.setFont(new Font("Tahona", Font.BOLD, 15));
         filename.setFont(new Font("Tahona", Font.BOLD, 15));
         saveskin.setFont(new Font("Tahona", Font.PLAIN, 9));
        main.add(copyUUID);
        main.add(downloadpanel);
        downloadpanel2.setLayout(null);
        downloadpanel.add(downloadpanel2);
downloadpanel.setVisible(false);
downloadpanel.setBackground(Color.GRAY);
downloadpanel2.setBackground(Color.WHITE);
downloadpanel.setLayout(null);
downloadpanel2.add(filename);
        skinpanel.setOpaque(true);
 
     main.add(skinpanel); 
     downloadpanel2.add(exitpanel);
     downloadpanel2.add(namefile);
     downloadpanel2.add(saveskin);
     downloadpanel2.add(done);
        
        main.add(downloadskin);
        downloadskin.setVisible(false);
               main.setVisible(true);
        checkuser.addActionListener((ActionEvent e) -> {
            if(username.getText().length() < 1){
                JOptionPane.showMessageDialog(null, "You should enter the user name", "Player Information", 2);
            } else {
            String text = username.getText();
            StringBuilder sb = new StringBuilder(text);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
            text = sb.toString();
            username.setText(text);

            getCheckConnection("https://api.mojang.com/users/profiles/minecraft/" + username.getText());
            }
        });
       downloadskin.addActionListener((ActionEvent e) -> {
            downloadpanel.setVisible(true);
        });
        copynick.addActionListener((ActionEvent e) -> {

            StringSelection stringSelection = new StringSelection(player.getText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);

        });
        copyUUID.addActionListener((ActionEvent e) -> {

            StringSelection stringSelection = new StringSelection(uuid.getText());
            Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);

        });
        done.addActionListener((ActionEvent e) -> {
            if(namefile.getText().length() < 1){
                JOptionPane.showMessageDialog(null, "Enter the file name", "Player Information", 2);
            } else {
                
           LoadSkin.downloadSkin(); 
            }
        });
    
         exitpanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                 downloadpanel.setVisible(false);
            }
        });
    }
     //enter the file name

    private void getUserName(String url) {
        try {
            InputStream in = null;
            in = (new URL(url).openStream());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

                while ((skeckline = br.readLine()) != null) {

                    player.setText(skeckline.substring(0, skeckline.indexOf("id")).replace("\"", "").replace("{name:", "").replace(",", ""));

                    // System.out.print(line.replace("{", "").replace("}", "").replace("", "").replace("\n", "UUID").replace("\"", "").replace("name", "Username"));
                    uuid.getText().replace(player.getText(), "");
                    main.setTitle("Player Information " + player.getText());
                }
            } catch (IOException e) {
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getUUID(String url) {
        try {
            InputStream in = null;
            in = (new URL(url).openStream());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String line;
                while ((line = br.readLine()) != null) {

                    uuid.setText(line.replace("{", "").replace("{name:", "").replace(",", "").replace("\"", "").replace("id", "").replace(":", "").replace("name", "").replace(username.getText(), "").replace("}", ""));
downloadskin.setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getCheckConnection(String url) {
        try {
            InputStream in = null;
            in = (new URL(url).openStream());

            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
  if(in == null){

                           System.out.println("NULLLLLLLLLL");
                   }
                while ((skeckline = br.readLine()) != null) {
                //    String replace = skeckline.substring(0, skeckline.indexOf("\",\"")).replace("{\"name\":\"", "");
                String replace = skeckline.substring(0, skeckline.indexOf("\""));
                   if(skeckline == null || replace == null || in == null || br == null){

                           System.out.println("NULLLLLLLLLL");
                   } else {
                                               getUserName("https://api.mojang.com/users/profiles/minecraft/" + username.getText());
                        getUUID("https://api.mojang.com/users/profiles/minecraft/" + username.getText());
            LoadSkin.loadSkin();
          int width = LoadSkin.image.getWidth();
          int height = LoadSkin.image.getHeight();
if(width == 64 && height == 32){
             Image skinsize = LoadSkin.image.getScaledInstance(280, 140, Image.SCALE_DEFAULT);
             skinpanel.setIcon(new ImageIcon(skinsize, "Skin"));
       } else if(width == 64 && height == 64 || width == 16 && height == 16 ||width == 32 && height == 32 ) {
                   Image skinsize = LoadSkin.image.getScaledInstance(210, 210, Image.SCALE_DEFAULT);
             skinpanel.setIcon(new ImageIcon(skinsize, "Skin"));
              }
              
                        //Image skinsize = LoadSkin.image.getScaledInstance(260, 190, Image.SCALE_DEFAULT);
                        

                        
                   }

                }

            } catch (IOException e) {

            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Main gui = new Main();
    }
}
