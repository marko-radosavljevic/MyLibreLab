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

package com.github.mylibrelab.elements.circuit.flowcharts.next;// *****************************************************************************

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Stack;

import com.github.mylibrelab.elements.tools.MainFlow;

import VisualLogic.VSBasisIF;
import VisualLogic.variables.VSFlowInfo;
import VisualLogic.variables.VSInteger;
import VisualLogic.variables.VSString;

public class XNext extends MainFlow {
    private Image image;
    private VSBasisIF basis;
    private VSFlowInfo in = null;
    private final VSFlowInfo out = new VSFlowInfo();


    private Stack stack;

    public void paint(java.awt.Graphics g) {
        if (element != null) {
            Rectangle bounds = element.jGetBounds();
            Graphics2D g2 = (Graphics2D) g;

            g2.setFont(font);

            int mitteX = bounds.x + (bounds.width) / 2;
            int mitteY = bounds.y + (bounds.height) / 2;

            int distanceY = 10;

            g2.setColor(new Color(204, 204, 255));
            g2.fillRect(bounds.x, mitteY - distanceY, bounds.width, 2 * distanceY);
            g2.setColor(Color.BLACK);
            g2.drawRect(bounds.x, mitteY - distanceY, bounds.width, 2 * distanceY);


            String caption = "NEXT";
            variable.setValue(caption);

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(caption, g2);

            g2.setColor(Color.BLACK);
            g.drawString(caption, mitteX - (int) (r.getWidth() / 2), (int) (mitteY + fm.getHeight() / 2) - 3);


        }
        super.paint(g);
    }

    public String getValue() {
        return variable.getValue();
    }

    public void init() {
        standardWidth = 130;
        width = standardWidth;
        height = 40;
        toInclude = "______=";

        initPins(1, 0, 1, 0);
        setSize(width, height);
        initPinVisibility(true, true, true, true);

        element.jSetInnerBorderVisibility(false);

        image = element.jLoadImage(element.jGetSourcePath() + "image.png");

        setPin(0, element.C_FLOWINFO, element.PIN_INPUT);
        setPin(1, element.C_FLOWINFO, element.PIN_OUTPUT);

        element.jSetResizable(false);
        element.jSetCaptionVisible(false);
        element.jSetCaption("NEXT");

        setName("#FLOWCHART_NEXT#");
    }

    public void xOnInit() {
        super.xOnInit();
        propertyChanged(null);
    }



    public void propertyChanged(Object o) {
        element.jRepaint();
        resizeWidth();
        element.jRepaint();
        element.jRefreshVM();
        checkPinDataType();

    }


    public void setPropertyEditor() {
        localize();
    }


    private void localize() {
        int d = 6;
        String language;

        language = "en_US";

        element.jSetPEItemLocale(d + 0, language, "Variable");

        language = "es_ES";

        element.jSetPEItemLocale(d + 0, language, "Variable");

    }



    public void initInputPins() {
        in = (VSFlowInfo) element.getPinInputReference(0);
        basis = element.jGetBasis();
        stack = basis.getStack();
    }

    // Override!
    public void mousePressedOnIdle(MouseEvent e) {

    }

    public void initOutputPins() {
        element.setPinOutputReference(1, out);
    }

    public void process() {

        if (stack != null && basis != null) {
            if (stack.size() > 0) {
                Object o = stack.lastElement();

                if (o instanceof ArrayList) // Element.ID
                {
                    ArrayList obj = (ArrayList) o;

                    if (obj.size() == 3) {
                        VSInteger id = (VSInteger) obj.get(0);
                        VSString varName = (VSString) obj.get(1);
                        VSString expr = (VSString) obj.get(2);

                        if (basis.vsCompareExpression(in, expr.getValue()) == true) {
                            String var = varName.getValue();
                            basis.vsEvaluate(in, var + "=" + var + "+1");
                            basis.vsStartElement(id.getValue());

                            return;
                        } else {
                            if (stack.size() > 0) stack.pop();
                        }
                    }
                }
            }
        }

        out.copyValueFrom(in);
        element.notifyPin(1);
    }


    public void loadFromStream(java.io.FileInputStream fis) {}


    public void saveToStream(java.io.FileOutputStream fos) {}


}
