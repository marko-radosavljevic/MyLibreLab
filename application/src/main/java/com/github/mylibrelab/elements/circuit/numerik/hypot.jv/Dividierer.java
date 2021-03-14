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

package com.github.mylibrelab.elements.circuit.numerik.hypot.jv;// *****************************************************************************

import java.awt.*;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSByte;
import VisualLogic.variables.VSDouble;
import VisualLogic.variables.VSInteger;
import VisualLogic.variables.VSObject;

public class Dividierer extends JVSMain {
    private Image image;

    private VSObject inA;
    private VSObject inB;
    private VSObject out;

    public void paint(java.awt.Graphics g) {
        drawImageCentred(g, image);
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

        image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");

        element.jInitPins();

        setPin(0, ExternalIF.C_VARIANT, element.PIN_OUTPUT);
        setPin(1, ExternalIF.C_VARIANT, element.PIN_INPUT);
        setPin(2, ExternalIF.C_VARIANT, element.PIN_INPUT);

        element.jSetPinDescription(0, "out");
        element.jSetPinDescription(1, "inA");
        element.jSetPinDescription(2, "inB");

        setName("Hypot JV");

    }

    public void initInputPins() {
        inA = (VSObject) element.getPinInputReference(1);
        inB = (VSObject) element.getPinInputReference(2);
    }

    public void initOutputPins() {
        out = element.jCreatePinDataType(0);
        element.setPinOutputReference(0, out);
    }

    public void checkPinDataType() {
        boolean pinInA = element.hasPinWire(1);
        boolean pinInB = element.hasPinWire(2);

        int dtA = element.C_VARIANT;
        int dtB = element.C_VARIANT;
        int dtC = element.C_VARIANT;

        if (pinInA == true) dtA = element.jGetPinDrahtSourceDataType(1);
        if (pinInB == true) dtB = element.jGetPinDrahtSourceDataType(2);

        element.jSetPinDataType(1, dtA);
        element.jSetPinDataType(2, dtB);

        if (dtA == element.C_VARIANT && dtB == element.C_VARIANT) dtC = element.C_VARIANT;

        if (dtA == element.C_DOUBLE && dtB == element.C_VARIANT) dtC = element.C_DOUBLE;
        if (dtA == element.C_INTEGER && dtB == element.C_VARIANT) dtC = element.C_INTEGER;
        if (dtA == element.C_BYTE && dtB == element.C_VARIANT) dtC = element.C_BYTE;

        if (dtA == element.C_VARIANT && dtB == element.C_DOUBLE) dtC = element.C_DOUBLE;
        if (dtA == element.C_VARIANT && dtB == element.C_INTEGER) dtC = element.C_INTEGER;
        if (dtA == element.C_VARIANT && dtB == element.C_BYTE) dtC = element.C_BYTE;


        if (dtA == element.C_DOUBLE && dtB == element.C_DOUBLE) dtC = element.C_DOUBLE;
        if (dtA == element.C_DOUBLE && dtB == element.C_INTEGER) dtC = element.C_DOUBLE;
        if (dtA == element.C_DOUBLE && dtB == element.C_BYTE) dtC = element.C_DOUBLE;

        if (dtA == element.C_INTEGER && dtB == element.C_DOUBLE) dtC = element.C_DOUBLE;
        if (dtA == element.C_INTEGER && dtB == element.C_INTEGER) dtC = element.C_INTEGER;
        if (dtA == element.C_INTEGER && dtB == element.C_BYTE) dtC = element.C_INTEGER;

        if (dtA == element.C_BYTE && dtB == element.C_DOUBLE) dtC = element.C_DOUBLE;
        if (dtA == element.C_BYTE && dtB == element.C_INTEGER) dtC = element.C_INTEGER;
        if (dtA == element.C_BYTE && dtB == element.C_BYTE) dtC = element.C_BYTE;


        element.jSetPinDataType(0, dtC);

        element.jRepaint();
    }


    double a, b;

    public void process() {
        if (inA == null) a = 0;
        if (inB == null) b = 0;

        if (inA instanceof VSDouble) {
            VSDouble valA = (VSDouble) inA;
            a = valA.getValue();
        }

        if (inB instanceof VSDouble) {
            VSDouble valB = (VSDouble) inB;
            b = valB.getValue();
        }

        if (inA instanceof VSInteger) {
            VSInteger valA = (VSInteger) inA;
            a = valA.getValue();
        }

        if (inB instanceof VSInteger) {
            VSInteger valB = (VSInteger) inB;
            b = valB.getValue();
        }

        if (inA instanceof VSByte) {
            VSByte valA = (VSByte) inA;
            a = toSigned(valA.getValue());
        }

        if (inB instanceof VSByte) {
            VSByte valB = (VSByte) inB;
            b = toSigned(valB.getValue());
        }


        if (out instanceof VSDouble) {
            VSDouble outX = (VSDouble) out;
            try {
                outX.setValue(Math.hypot(a, b));
            } catch (Exception e) {
                outX.setValue(-1.0);
            }
        }
        if (out instanceof VSInteger) {
            VSInteger outX = (VSInteger) out;
            try {
                outX.setValue((int) Math.hypot(a, b));
            } catch (Exception e) {
                outX.setValue(-1);
            }
        }
        if (out instanceof VSByte) {
            VSByte outX = (VSByte) out;
            try {
                outX.setValue(toUnsigned((short) Math.hypot(a, b)));
            } catch (Exception e) {
                outX.setValue((byte) 0);
            }
        }

        element.notifyPin(0);

    }


    public static byte toUnsigned(final short value) {
        return (byte) (0xFF & value);
    }

    /**
     * Converts an unsigned byte to a signed short.
     *
     * @param value an unsigned byte value
     * @return a signed short that represents the unsigned byte's value.
     */
    public static short toSigned(byte value) {
        return (short) copyBits(value, (byte) 8);
    }

    /**
     * Returns a long that contains the same n bits as the given long,with cleared upper rest.
     *
     * @param value the value which lowest bits should be copied.
     * @param bits the number of lowest bits that should be copied.
     * @return a long value that shares the same low bits as the given value.
     */
    private static long copyBits(final long value, byte bits) {
        final boolean logging = false; // turn off logging here
        long converted = 0;
        long comp = 1L << bits;
        while (--bits != -1) {
            if (((comp >>= 1) & value) != 0) {
                converted |= comp;
            }
            if (logging) {
                System.out.print((comp & value) != 0 ? "1" : "0");
            }
        }
        if (logging) {
            System.out.println();
        }
        return converted;
    }


}