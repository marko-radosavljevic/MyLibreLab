/*
 * Copyright (C) 2020 MyLibreLab
 * Based on MyOpenLab by Carmelo Salafia www.myopenlab.de
 * Copyright (C) 2004  Carmelo Salafia cswi@gmx.de
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package elements.circuit.string.constpackageant.jv.lf.constpackage;// *****************************************************************************

import java.awt.*;
import java.awt.geom.Rectangle2D;

import elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSString;

public class Const extends JVSMain {
    private final int pw = 7; // Pin Width
    private int width;
    private final int height;
    private VSString constValue = new VSString("\n");
    private Graphics2D g2;
    private final Font fnt = new Font("Courier", 1, 12);
    private final VSString out = new VSString();


    private final boolean changed = false;


    public Const() {
        width = 48;
        height = 25;
    }

    public void resizeWidth() {
        g2.setFont(fnt);
        String caption = "LF";
        FontMetrics fm = g2.getFontMetrics();
        Rectangle2D r = fm.getStringBounds("LF", g2);
        int newWidth = (int) r.getWidth() + 20;
        int diff = (width - newWidth);
        element.jSetSize(newWidth, height);
        element.jSetLeft(element.jGetLeft() + diff);
        width = newWidth;
    }

    public void paint(java.awt.Graphics g) {
        if (element != null) {
            g2 = (Graphics2D) g;
            Rectangle bounds = element.jGetBounds();
            g2.setFont(fnt);
            g2.setColor(Color.black);

            String caption = "LF";
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds("LF", g2);

            g2.setColor(new Color(245, 101, 234));
            g.fillRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);

            g2.setColor(Color.black);
            g.drawString("LF", bounds.x + 5, ((bounds.height) / 2) + 5);
            g.drawRect(bounds.x, bounds.y, bounds.width - 1, bounds.height - 1);

        }
    }

    public void init() {
        initPins(0, 1, 0, 0);
        setSize(width, height);

        element.jSetInnerBorderVisibility(false);
        element.jSetTopPinsVisible(false);
        element.jSetLeftPinsVisible(false);
        element.jSetBottomPinsVisible(false);

        setPin(0, ExternalIF.C_STRING, element.PIN_OUTPUT);
        element.jSetPinDescription(0, "Out");
        setName("LF_Const");
        constValue = new VSString("\n");
    }


    public void initInputPins() {}

    public void initOutputPins() {
        element.setPinOutputReference(0, out);
    }



    public void setPropertyEditor() {
        // element.jAddPEItem("Wert",constValue, 0,0);
        // localize();
    }


    private void localize() {
        int d = 6;
        String language;

        language = "en_US";

        element.jSetPEItemLocale(d + 0, language, "Value");

        language = "es_ES";

        element.jSetPEItemLocale(d + 0, language, "Valor");
    }

    public void propertyChanged(Object o) {
        resizeWidth();
        element.jRepaint();
    }

    public void start() {
        out.setValue(constValue.getValue());
        out.setChanged(true);
        element.notifyPin(0);
    }

    public void process() {

    }

    public void loadFromStream(java.io.FileInputStream fis) {
        // constValue.loadFromStream(fis);
    }

    public void saveToStream(java.io.FileOutputStream fos) {
        // constValue.saveToStream(fos);
    }
}