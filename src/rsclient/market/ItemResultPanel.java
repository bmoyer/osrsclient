/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsclient.market;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import logic.ZybezItemListing;
import logic.ZybezOffer;
import net.miginfocom.swing.MigLayout;
import org.pushingpixels.trident.Timeline;

/**
 *
 * @author ben
 */
public class ItemResultPanel extends JPanel implements MouseListener {

    private JLabel itemNameLabel, itemPriceLabel, itemPictureLabel;
    private ItemListingRolloverListener rolloverListener;
    private ArrayList<ZybezOffer> offerList = new ArrayList();
    private final ImageIcon emptyItem = new ImageIcon(getClass().getClassLoader().getResource("resources/emptyitem.png"));
    private Timeline rolloverTimeline;

    public ItemResultPanel() {
        setup();
        setupAnimation();
    }

    public void setup() {
        setLayout(new MigLayout("ins 0"));
        setBackground(Color.darkGray);

        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        itemNameLabel = new JLabel("--");

        //this prevents several item names from stretching too large
        itemNameLabel.setMaximumSize(new Dimension(160, 50));

        itemPriceLabel = new JLabel("--");

        itemPictureLabel = new JLabel(emptyItem);
        itemPictureLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        itemNameLabel.setForeground(Color.white);
        itemPriceLabel.setForeground(Color.white);

        addMouseListener(this);

        add(itemPictureLabel, "cell 0 0, width 20, height 20, dock west,");
        add(itemNameLabel, "cell 1 0");
        add(itemPriceLabel, "cell 1 1");
    }

    public void clearContents() {

        itemNameLabel.setText("--");
        itemPriceLabel.setText("--");
        itemPictureLabel.setIcon(emptyItem);
        offerList.clear();
    }

    private ImageIcon getItemPicture(ZybezItemListing z) throws IOException {

        URL url = new URL(z.getImageURL());

        url = new URL(z.getImageURL());
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
        BufferedImage i = ImageIO.read(in);

        ImageIcon o = new ImageIcon(i);

        return o;

    }

    public void setDetails(ZybezItemListing z) {
        itemNameLabel.setText(z.getItemName());
        double doublePrice = Double.parseDouble(z.getAveragePrice());
        String formattedPrice = NumberFormat.getIntegerInstance().format((int) doublePrice);
        itemPriceLabel.setText(formattedPrice.toString()+ " gp");
        try {
            ImageIcon ii = getItemPicture(z);
            itemPictureLabel.setIcon(ii);
        } catch (IOException ex) {
            Logger.getLogger(ItemResultPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setOffers(ArrayList<ZybezOffer> o) {
        offerList = o;
    }

    public ArrayList<ZybezOffer> getOffers() {
        return offerList;
    }

    public void setRolloverListener(ItemListingRolloverListener rolloverListener) {
        this.rolloverListener = rolloverListener;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (rolloverListener != null) {
            rolloverListener.onClicked(this);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        if (rolloverListener != null) {
            rolloverTimeline.play();
        }
    }

    @Override
    public void mouseExited(MouseEvent me) {
        if (rolloverListener != null) {
            rolloverTimeline.playReverse();
        }
    }

    private void setupAnimation() {
        rolloverTimeline = new Timeline(this);
        rolloverTimeline.addPropertyToInterpolate("background", this.getBackground(), new Color(91, 91, 91));
        rolloverTimeline.setDuration(150);
    }

    public void selectPanel() {
        this.setBackground(new Color(91, 91, 91));
        rolloverTimeline.addPropertyToInterpolate("background", this.getBackground(), new Color(91, 91, 91));
    }

    public void deselectPanel() {
        this.setBackground(new Color(64, 64, 64));
        rolloverTimeline.addPropertyToInterpolate("background", this.getBackground(), new Color(91, 91, 91));
    }

}
