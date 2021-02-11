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

package com.github.mylibrelab.elements.circuit.mcu.stackinterpreter.old.outputpin;// *****************************************************************************

import java.awt.Image;
import java.util.ArrayList;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSComboBox;
import VisualLogic.variables.VSObject;

public class CElement extends JVSMain {
    private final VSComboBox dtPin = new VSComboBox();
    private Image image;
    private VSObject in = null;
    private int pinDTX = -1;

    public void paint(java.awt.Graphics g) {
        if (image != null) drawImageCentred(g, image);
    }

    public void beforeInit(String[] args) {
        if (args != null) {
            if (args.length >= 1) {
                pinDTX = Integer.parseInt(args[0]);
            }
        }
    }

    public void init() {
        initPins(0, 0, 0, 1);
        setSize(35, 27);
        initPinVisibility(false, false, false, true);

        element.jSetInnerBorderVisibility(true);

        image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

        setPin(0, element.C_VARIANT, element.PIN_INPUT);

        String[] liste = element.jGetDataTypeList();

        for (int i = 0; i < liste.length; i++) {
            dtPin.addItem(liste[i]);
        }

        dtPin.selectedIndex = 0;

        if (pinDTX > -1) {
            dtPin.selectedIndex = pinDTX;
        }


        element.jSetResizable(false);
        element.jSetCaptionVisible(true);
        element.jSetCaption("o");

        setName("#PINOUTPUT_INTERNAL#");
    }


    public void setPropertyEditor() {
        element.jAddPEItem("DT Output-Pin", dtPin, 0, 0);
    }

    private void setPinDT2(int pinIndex, VSComboBox dtPin) {
        int dt = element.jGetDataType(dtPin.getItem(dtPin.selectedIndex));
        setPin(pinIndex, dt, element.PIN_INPUT);
    }

    private void setPinDT(int pinIndex, Object o, VSComboBox dtPin) {
        if (o.equals(dtPin)) {
            setPinDT2(pinIndex, dtPin);
        }
    }

    public void propertyChanged(Object o) {
        setPinDT(0, o, dtPin);

        element.jRepaint();
    }

    public void initInputPins() {
        in = (VSObject) element.getPinInputReference(0);
    }


    public void process() {
        Object tag = element.jGetTag();

        if (tag instanceof ArrayList) {
            Integer pinIndex = null;

            ArrayList liste = (ArrayList) tag;
            if (liste.size() == 3) {
                if (liste.get(0) instanceof VSObject) {
                    // in=(VSObject)element.getPinInputReference(0);
                    VSObject o = (VSObject) liste.get(0);
                    o.copyValueFrom(in);
                }
                if (liste.get(1) instanceof Integer) {
                    pinIndex = (Integer) liste.get(1);
                }
                if (liste.get(2) instanceof ExternalIF) {
                    ExternalIF el = (ExternalIF) liste.get(2);
                    // System.out.println("PinIndex="+pinIndex);
                    el.notifyPin(pinIndex);

                }
            }
        }

    }

    public void loadFromStream(java.io.FileInputStream fis) {
        try {
            dtPin.loadFromStream(fis);
            setPinDT2(0, dtPin);
            element.jRepaint();

        } catch (Exception ex) {
        }

    }


    public void saveToStream(java.io.FileOutputStream fos) {
        dtPin.saveToStream(fos);
    }


}