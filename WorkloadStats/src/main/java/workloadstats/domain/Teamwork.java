/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.domain;

import net.fortuna.ical4j.model.DateTime;

/**
 *
 * @author Ilkka
 */
public class Teamwork extends Event {
    
    public Teamwork(DateTime start, DateTime end) {
        super(start, end);
    }
    
}
