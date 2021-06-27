package vue;

import constantes.Constantes;

import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

/**
 * Panel affiché pendant la lecture des données.
 */
public class PanelChargement extends JPanel {

    private final JProgressBar bar = new JProgressBar();

    public PanelChargement() {
        setLayout(new BorderLayout());

        // PANEL CENTRE
        JPanel mid = new JPanel(new GridBagLayout());
        mid.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(0, 0, 50, 0);

        JLabel titre = new JLabel("Lecture des nouvelles données, veuillez patienter...");
        titre.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        titre.setForeground(new Color(0, 255, 255));
        mid.add(titre, c);

        c.gridy = 1;

        bar.setValue(0);
        bar.setStringPainted(true);
        bar.setForeground(new Color(0, 100, 120));
        mid.add(bar, c);

        // PANEL FOOTER
        JPanel foot = new JPanel();
        foot.setOpaque(false);
        JLabel label = new JLabel("Made by Eliott ROGEAUX & Stéphane LAY");
        label.setForeground(new Color(0, 255, 255));
        foot.add(label, BorderLayout.LINE_START);

        //AJOUT TOUT
        this.add(mid, BorderLayout.CENTER);
        this.add(foot, BorderLayout.PAGE_END);

    }

    public JProgressBar getChargementBarre() {
        return bar;
    }

    /**
     * Méthode pour l'image de fond.
     *
     * @param g composant de la classe Graphics.
     * @see Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            Image bgImage = ImageIO.read(new File(Constantes.FIC_FILE + "paysage.jpg"));
            g.drawImage(bgImage, 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
