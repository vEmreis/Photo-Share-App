import java.awt.event.*;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CameraGUI extends JFrame {

    private JLabel imageHolder;
    private JButton capture;
    private JButton save;
    private JButton brightness;
    private JButton contrast;
    private JButton whiteBlack;
    private JButton share;
    private BufferedImage imageSaver;
    private VideoFeedTaker videoFeedTaker = new VideoFeedTaker();
    private Filters filters;

    Webcam webcam;

    public CameraGUI() {
        initComponents();
        filters = new Filters();
        videoFeedTaker.start();
        //Resim Çekme Butonu
        capture.addActionListener(e -> {
            videoFeedTaker.stop();
            BufferedImage image = webcam.getImage();
            imageSaver = image;
            imageHolder.setIcon(new ImageIcon(image));
        });

        save.addActionListener(e -> {
            if (imageSaver != null) {
                try {
                    //Resimi Kaydetme Yeri
                    String savePath ="saved-image.png";
                    JOptionPane.showMessageDialog(null, "Resim Başarıyla Kaydedildi :" +savePath );
                    ImageIO.write(imageSaver, "PNG", new File(savePath));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Önce Bir Resim Çekiniz!");
            }
        });

        //Resmi Aydınlatma
        brightness.addActionListener(e -> {
            if (imageSaver != null) {
                BufferedImage image = filters.brightnessControl(imageSaver, 2f);
                imageSaver = image;
                imageHolder.setIcon(new ImageIcon(image));
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Önce Bir Resim Çekiniz!");
            }

        });

        //Resmi Karartma
        contrast.addActionListener(e -> {
            if (imageSaver != null) {
                BufferedImage image = filters.brightnessControl(imageSaver, 0.5f);
                imageSaver = image;
                imageHolder.setIcon(new ImageIcon(image));
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Önce Bir Resim Çekiniz!");
            }
        });

        //Siyah - Beyaz Filtresi
        whiteBlack.addActionListener(e -> {
            if (imageSaver != null) {
            BufferedImage image = filters.whiteBlackFilter(imageSaver);
            imageSaver = image;
            imageHolder.setIcon(new ImageIcon(image));
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Önce Bir Resim Çekiniz!");
            }
        });

        //Paylaş Butonu
        share.addActionListener(e -> {
            if (imageSaver != null) {
            String base64String = ImageBase64Converters.fromBufferedImage(imageSaver);
            try {
                APIRequest.requestImgBBServer(base64String);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            } else {
                JOptionPane.showMessageDialog(null, "Lütfen Önce Bir Resim Çekiniz!");
            }

        });


    }

    private void initComponents() {
        imageHolder = new JLabel();
        capture = new JButton();
        save = new JButton();
        brightness = new JButton();
        contrast = new JButton();
        whiteBlack = new JButton();
        share = new JButton();

        //======== this ========
        setTitle("ESA");
        var contentPane = getContentPane();

        //---- capture ----
        capture.setText("Resim \u00c7ek");

        //---- save ----
        save.setText("Kaydet");

        //---- illuminate ----
        brightness.setText("Ayd\u0131nlat");

        //---- contrast ----
        contrast.setText("Karart");

        //---- whiteBlack ----
        whiteBlack.setText("Siyah -  Beyaz");

        //---- share ----
        share.setText("PAYLA\u015e");

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(save, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(capture, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(GroupLayout.Alignment.LEADING, contentPaneLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(imageHolder, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE)))
                                .addGap(40, 40, 40)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(brightness, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(contrast, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(whiteBlack, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(share, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24))
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addGroup(contentPaneLayout.createParallelGroup()
                                        .addComponent(imageHolder, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(brightness, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(contrast, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(whiteBlack, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(contentPaneLayout.createSequentialGroup()
                                                .addComponent(capture, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(save, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(share, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        setVisible(true);
        webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(new Dimension(320, 240));
        webcam.open();
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        new CameraGUI();
    }

    //Başlangıçta Kamera Görüntüsünü Flow Eden Sınıf
    class VideoFeedTaker extends Thread {
        @Override
        public void run() {
            while (true) {
                Image image = webcam.getImage();
                imageHolder.setIcon(new ImageIcon(image));
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void capture(ActionEvent e) {
        // TODO add your code here
    }

}
