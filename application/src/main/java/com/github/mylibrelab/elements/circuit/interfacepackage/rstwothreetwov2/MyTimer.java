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

package com.github.mylibrelab.elements.circuit.interfacepackage.rstwothreetwov2;

import java.util.logging.Level;
import java.util.logging.Logger;

class MyTimer extends Thread {

    public boolean stop = false;
    public long sendenEnde;
    public RS232v2 owner;
    public int delay = 200;
    public boolean gesendet = false;

    @Override
    public void run() {

        System.out.println("Timer started");
        stop = false;
        gesendet = true;
        sendenEnde = System.currentTimeMillis();

        // long millisA = System.currentTimeMillis();
        long millis = 0;
        while (!stop) {

            try {

                millis = System.currentTimeMillis();

                if (!gesendet && Math.abs(sendenEnde - millis) > owner.message_timeout.getValue()) {
                    owner.sendBytesNow();
                    gesendet = true;
                }

                // millisB = System.currentTimeMillis();
                Thread.sleep(5);
            } catch (InterruptedException ex) {
                Logger.getLogger(MyTimer.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        System.out.println("Timer end");
    }
}