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

package com.github.mylibrelab.elements.circuit.flowcharts.robot.setrobot;// *****************************************************************************

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.github.mylibrelab.elements.tools.MainFlow;

import VisualLogic.ExternalIF;
import VisualLogic.VSBasisIF;
import VisualLogic.variables.VSFlowInfo;
import VisualLogic.variables.VSInteger;

public class SetRobot extends MainFlow {
    private Image image;
    private VSBasisIF basis;
    private VSFlowInfo in = null;
    private final VSFlowInfo out = new VSFlowInfo();

    private ExternalIF robotCircuitElement;
    private ExternalIF robotFrontElement;


    public void onDispose() {
        robotCircuitElement = null;
        robotFrontElement = null;
    }


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

            String caption = "SetRobot(" + variable.getValue() + ")";

            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r = fm.getStringBounds(caption, g2);

            g2.setColor(Color.BLACK);
            g.drawString(caption, mitteX - (int) (r.getWidth() / 2), (int) (mitteY + fm.getHeight() / 2) - 3);
            g.drawImage(image, 10, 16, null);

        }
        super.paint(g);
    }


    public void init() {
        standardWidth = 130;
        width = standardWidth;
        height = 40;
        toInclude = "____SetRobot";

        initPins(1, 0, 1, 0);
        setSize(width, height);
        initPinVisibility(true, true, true, true);

        element.jSetInnerBorderVisibility(false);

        image = element.jLoadImage(element.jGetSourcePath() + "image.png");

        setPin(0, element.C_FLOWINFO, element.PIN_INPUT);
        setPin(1, element.C_FLOWINFO, element.PIN_OUTPUT);

        element.jSetResizable(false);
        element.jSetCaptionVisible(false);
        element.jSetCaption("GO");

        setName("#FLOWCHART_SetRobot#");

        variable.setValue("1");
    }

    public void xOnInit() {
        super.xOnInit();
    }

    public void initInputPins() {
        in = (VSFlowInfo) element.getPinInputReference(0);
        basis = element.jGetBasis();
    }

    // Override!
    /*
     * public void mousePressedOnIdle(MouseEvent e)
     * {
     *
     * }
     */

    private int strToInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (Exception ex) {
            element.jShowMessage("RobotNr have to be a Number!");
        }
        return 0;
    }

    public void start() {}

    public void initOutputPins() {
        element.setPinOutputReference(1, out);
    }

    public void process() {

        basis = element.jGetBasis();
        robotCircuitElement = element.getElementByhName(basis, "SimpleRobot3D v. 1.0");
        if (robotCircuitElement != null) {
            robotFrontElement = robotCircuitElement.getPanelElement();


            VSInteger result = new VSInteger();
            // Create Robot and return the RobotNr
            robotFrontElement.jProcessPanel(-1, (double) strToInt(variable.getValue()), result);

            if (result.getValue() > -1) {
                in.tags.put("ROBOTNR", new Integer(result.getValue()));
                out.copyValueFrom(in);
                element.notifyPin(1);

            } else {
                element.jShowMessage("Robot with ID=\"" + variable.getValue() + "\" already exists.");
                // Robot existiert bereits,... Information darf nicht mehr weitergeleitet werden!
            }
        }
    }

    public void loadFromStream(java.io.FileInputStream fis) {
        variable.loadFromStream(fis);
    }


    public void saveToStream(java.io.FileOutputStream fos) {
        variable.saveToStream(fos);
    }

}
