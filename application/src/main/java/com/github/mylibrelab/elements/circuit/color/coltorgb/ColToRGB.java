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

package com.github.mylibrelab.elements.circuit.color.coltorgb;// *****************************************************************************

import java.awt.Color;
import java.awt.Image;
import java.util.Locale;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSColor;
import VisualLogic.variables.VSInteger;

public class ColToRGB extends JVSMain {
    public VSInteger outR = new VSInteger(0);
    public VSInteger outG = new VSInteger(0);
    public VSInteger outB = new VSInteger(0);

    public VSColor in;

    private Image image;

    public void onDispose() {
        if (image != null) {
            image.flush();
            image = null;
        }
    }


    private boolean changed = false;

    public void paint(java.awt.Graphics g) {
        drawImageCentred(g, image);
    }


    public void init() {
        initPins(0, 3, 0, 1);
        setSize(35 + 20, 35);
        initPinVisibility(false, true, false, true);

        image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

        setPin(0, ExternalIF.C_INTEGER, element.PIN_OUTPUT);
        setPin(1, ExternalIF.C_INTEGER, element.PIN_OUTPUT);
        setPin(2, ExternalIF.C_INTEGER, element.PIN_OUTPUT);

        setPin(3, ExternalIF.C_COLOR, element.PIN_INPUT);

        String strLocale = Locale.getDefault().toString();

        if (strLocale.equalsIgnoreCase("de_DE")) {
            element.jSetPinDescription(0, "Red");
            element.jSetPinDescription(1, "Green");
            element.jSetPinDescription(2, "Blue");
            element.jSetPinDescription(3, "Color");
        }
        if (strLocale.equalsIgnoreCase("en_US")) {
            element.jSetPinDescription(0, "Red");
            element.jSetPinDescription(1, "Green");
            element.jSetPinDescription(2, "Blue");
            element.jSetPinDescription(3, "Color");
        }
        if (strLocale.equalsIgnoreCase("es_ES")) {
            element.jSetPinDescription(0, "Red");
            element.jSetPinDescription(1, "Verde");
            element.jSetPinDescription(2, "Azul");
            element.jSetPinDescription(3, "Color");
        }


        element.jSetCaptionVisible(true);
        element.jSetCaption("Col2RGB");
        setName("Col2RGB");
    }



    public void setPropertyEditor() {}

    public void propertyChanged(Object o) {}

    public void initInputPins() {
        in = (VSColor) element.getPinInputReference(3);

        if (in == null) {
            in = new VSColor(new Color(0, 0, 0));
        }
    }

    public void initOutputPins() {
        element.setPinOutputReference(0, outR);
        element.setPinOutputReference(1, outG);
        element.setPinOutputReference(2, outB);
    }

    public void start() {
        changed = true;
    }

    public void process() {
        outR.setValue(in.getValue().getRed());
        outG.setValue(in.getValue().getGreen());
        outB.setValue(in.getValue().getBlue());
        element.notifyPin(0);
        element.notifyPin(1);
        element.notifyPin(2);
    }



}