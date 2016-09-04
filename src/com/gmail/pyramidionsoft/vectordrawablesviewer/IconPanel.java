package com.gmail.pyramidionsoft.vectordrawablesviewer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import javax.swing.*;

import com.kitfox.svg.*;
import com.kitfox.svg.app.beans.*;

class IconPanel extends JPanel {

    private SVGDiagram diagram;
    private int id = 0;

    public IconPanel(int scaleX, int scaleY) {
        SVGIcon icon = new SVGIcon();
        icon.setPreferredSize(new Dimension(scaleX, scaleY));
        icon.setScaleToFit(true);
        setPreferredSize(new Dimension(scaleX, scaleY));
    }

    public void setSVG(String svg) {
        StringReader reader = new StringReader(svg);

        try {
            id += 1;
            SVGUniverse svgUniverse = new SVGUniverse();
            diagram = svgUniverse.getDiagram(svgUniverse.loadSVG(reader, Integer.toString(id)));
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D)g;
        final int width = getWidth();
        final int height = getHeight();

        g.setColor(getBackground());
        g.fillRect(0, 0, width, height);

        try {
            float min = Math.min(this.getHeight()/diagram.getHeight(),  this.getWidth()/diagram.getWidth());

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToScale( min, min);
            graphics2D.transform(affineTransform);

            RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setRenderingHints(renderingHints);

            diagram.render(graphics2D);
        }
        catch(Exception e2) {
            System.out.println(e2);
        }

    }

}
