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

package com.github.mylibrelab.elements.front.twobooleanpackage.schiebeschalter;/*
                                                                                * Copyright (C) 2020 MyLibreLab
                                                                                * Based on MyOpenLab by Carmelo Salafia
                                                                                * www.myopenlab.de
                                                                                * Copyright (C) 2004 Carmelo Salafia
                                                                                * cswi@gmx.de
                                                                                *
                                                                                * This program is free software: you can
                                                                                * redistribute it and/or modify
                                                                                * it under the terms of the GNU General
                                                                                * Public License as published by
                                                                                * the Free Software Foundation, either
                                                                                * version 3 of the License, or
                                                                                * (at your option) any later version.
                                                                                *
                                                                                * This program is distributed in the
                                                                                * hope that it will be useful,
                                                                                * but WITHOUT ANY WARRANTY; without even
                                                                                * the implied warranty of
                                                                                * MERCHANTABILITY or FITNESS FOR A
                                                                                * PARTICULAR PURPOSE. See the
                                                                                * GNU General Public License for more
                                                                                * details.
                                                                                *
                                                                                * You should have received a copy of the
                                                                                * GNU General Public License
                                                                                * along with this program. If not, see
                                                                                * <http://www.gnu.org/licenses/>.
                                                                                *
                                                                                */

import java.awt.*;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSBoolean;

public class SchiebeSchalter extends JVSMain {
    private Image image;
    private VSBoolean out = new VSBoolean();
    private VSBoolean val;
    private boolean changed = false;

    public void onDispose() {
        image.flush();
        image = null;
    }


    public void paint(java.awt.Graphics g) {

        drawImageCentred(g, image);

    }

    public void init() {
        initPins(0, 1, 0, 0);
        setSize(40, 30);
        initPinVisibility(false, true, false, false);

        setPin(0, ExternalIF.C_BOOLEAN, element.PIN_OUTPUT);

        image = element.jLoadImage(element.jGetSourcePath() + "SchiebeSchalter.gif");
        setName("Schiebeschalter");
    }


    public void changePin(int pinIndex, Object value) {
        changed = true;
        val = (VSBoolean) value;
        if (val != null) {
            out.setValue(val.getValue());
            element.notifyPin(0);
        }
    }

    public void initInputPins() {}

    public void initOutputPins() {
        element.setPinOutputReference(0, out);
    }


    public void process() {}


}