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

package com.github.mylibrelab.elements.front.twograph.old.datalogger;/*
                                                                      * Copyright (C) 2020 MyLibreLab
                                                                      * Based on MyOpenLab by Carmelo Salafia
                                                                      * www.myopenlab.de
                                                                      * Copyright (C) 2004 Carmelo Salafia cswi@gmx.de
                                                                      *
                                                                      * This program is free software: you can
                                                                      * redistribute it and/or modify
                                                                      * it under the terms of the GNU General Public
                                                                      * License as published by
                                                                      * the Free Software Foundation, either version 3
                                                                      * of the License, or
                                                                      * (at your option) any later version.
                                                                      *
                                                                      * This program is distributed in the hope that it
                                                                      * will be useful,
                                                                      * but WITHOUT ANY WARRANTY; without even the
                                                                      * implied warranty of
                                                                      * MERCHANTABILITY or FITNESS FOR A PARTICULAR
                                                                      * PURPOSE. See the
                                                                      * GNU General Public License for more details.
                                                                      *
                                                                      * You should have received a copy of the GNU
                                                                      * General Public License
                                                                      * along with this program. If not, see
                                                                      * <http://www.gnu.org/licenses/>.
                                                                      *
                                                                      */

import java.awt.*;

import com.github.mylibrelab.elements.tools.JVSMain;

import VisualLogic.ExternalIF;
import VisualLogic.variables.VSBoolean;
import VisualLogic.variables.VSDouble;

public class DataLogger extends JVSMain {
    public VSDouble in = null;
    public VSBoolean reset = null;
    private ExternalIF panelElement = null;
    private Image image;

    public DataLogger() {
        super();
    }

    public void onDispose() {
        image.flush();
        image = null;
    }

    public void paint(java.awt.Graphics g) {
        drawImageCentred(g, image);
    }

    public void init() {
        initPins(0, 0, 0, 2);
        setSize(40, 30);
        initPinVisibility(false, false, false, true);


        setPin(0, ExternalIF.C_DOUBLE, element.PIN_INPUT);
        setPin(1, ExternalIF.C_BOOLEAN, element.PIN_INPUT);

        element.jSetPinDescription(0, "In");
        element.jSetPinDescription(1, "Reset");

        image = element.jLoadImage(element.jGetSourcePath() + "icon.gif");


        element.jSetCaptionVisible(true);
        element.jSetCaption("Logger");

        setName("DataLogger");
    }


    public void initInputPins() {
        in = (VSDouble) element.getPinInputReference(0);
        reset = (VSBoolean) element.getPinInputReference(1);

        if (in == null) in = new VSDouble(0);
        if (reset == null) reset = new VSBoolean(false);
    }

    public void initOutputPins() {

    }

    public void process() {
        if (in != null && panelElement != null) {
            if (reset != null && reset.getValue() == true) {
                panelElement.jProcessPanel(0, 1, in);
            } else {
                panelElement.jProcessPanel(0, 0, in);
            }

        }
    }

    public void start() {
        panelElement = element.getPanelElement();
        if (panelElement != null) panelElement.jRepaint();
    }


}
