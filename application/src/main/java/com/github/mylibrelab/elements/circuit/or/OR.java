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

package com.github.mylibrelab.elements.circuit.or;// *****************************************************************************

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ElementActionEvent;
import VisualLogic.ExternalIF;
import VisualLogic.variables.VSObject;

public class OR extends JVSMain {
    VSObject vv = null;
    private Image image;
    private VSObject inA;
    private VSObject inB;
    private VSObject out = new VSObject();
    private Color color = Color.BLACK;

    public void paint(java.awt.Graphics g) {
        if (element != null) {
            Rectangle bounds = element.jGetBounds();

            g.setColor(color);

            int w = bounds.width;
            int h = bounds.height;
            int d = w / 2;

            g.drawLine(bounds.x + d, bounds.y + 6, bounds.x + d, bounds.y + 16);
            g.fillOval(bounds.x + d - 3, bounds.y + (h / 2) - 3, 6, 6);

            g.drawLine(bounds.x + d, bounds.y + h / 2, bounds.x + w, bounds.y + h / 2);

            for (int i = 0; i < 2; i++) {
                int yy = bounds.y + 6 + (i * 11);
                g.drawLine(bounds.x, yy, bounds.x + d, yy);
            }
        }

    }

    public void onDispose() {
        if (image != null) {
            image.flush();
            image = null;
        }
    }

    public void init() {
        initPins(0, 1, 0, 2);
        setSize(40, 25);
        element.jSetInnerBorderVisibility(false);
        element.jSetTopPinsVisible(false);
        element.jSetBottomPinsVisible(false);

        image = element.jLoadImage(element.jGetSourcePath() + "icon.png");

        element.jInitPins();

        setPin(0, ExternalIF.C_VARIANT, element.PIN_OUTPUT);
        setPin(1, ExternalIF.C_VARIANT, element.PIN_INPUT);
        setPin(2, ExternalIF.C_VARIANT, element.PIN_INPUT);

        setName("OR_Version_2.0");
        out.setPin(0);
    }

    public void initInputPins() {
        inA = (VSObject) element.getPinInputReference(1);
        inB = (VSObject) element.getPinInputReference(2);
    }

    public void initOutputPins() {
        out = element.jCreatePinDataType(0);
        // element.setPinOutputReference(i,out[i]);
        element.setPinOutputReference(0, out);
    }

    public void checkPinDataType() {
        boolean pinInA = element.hasPinWire(1);
        boolean pinInB = element.hasPinWire(2);

        int dtA = element.C_VARIANT;
        int dtB = element.C_VARIANT;

        if (pinInA) dtA = element.jGetPinDrahtSourceDataType(1);
        if (pinInB) dtB = element.jGetPinDrahtSourceDataType(2);


        element.jSetPinIO(1, element.PIN_INPUT);
        element.jSetPinIO(2, element.PIN_INPUT);

        color = Color.BLACK;

        if (pinInA) {
            element.jSetPinDataType(1, dtA);
            element.jSetPinDataType(2, dtA);
            element.jSetPinDataType(0, dtA);
            element.jSetPinIO(0, element.PIN_OUTPUT);

            color = element.jGetDTColor(dtA);

        } else {
            element.jSetPinDataType(1, dtB);
            element.jSetPinDataType(2, dtB);
            element.jSetPinDataType(0, dtB);
            element.jSetPinIO(0, element.PIN_OUTPUT);
            color = element.jGetDTColor(dtB);
        }

        element.jRepaint();
    }

    public void elementActionPerformed(ElementActionEvent evt) {

        if (inA instanceof VSObject && evt.getSourcePinIndex() == 1) {
            vv = inA;

            out.copyValueFrom(vv);
            out.setChanged(true);
            element.notifyPin(0);
        }

        if (inB instanceof VSObject && evt.getSourcePinIndex() == 2) {
            vv = inB;

            out.copyValueFrom(vv);
            out.setChanged(true);
            element.notifyPin(0);
        }
    }


}
