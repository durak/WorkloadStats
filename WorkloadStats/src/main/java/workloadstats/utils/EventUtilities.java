/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workloadstats.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.DtStart;
import workloadstats.domain.model.Event;

/**
 * Helper utilities for calendar object handling
 * @author Ilkka
 */
public class EventUtilities {

    /**
     * Calculate duration in minutes for a calendar event
     * @param event
     * @return duration in minutes
     */
    public long getDuration(VEvent event) {
        Instant start = event.getStartDate().getDate().toInstant();
        Instant end = event.getEndDate().getDate().toInstant();
        
        long gap = ChronoUnit.MINUTES.between(start, end);
        
        return gap;
    }
    
    /**
     * Calculate sum of durations for a list of events
     * @param events
     * @return Sum of durations in minutes
     */
    public long getSumOfDurations(List<Event> events) {
        long sum = 0;
        for (VEvent event : events) {
            sum += getDuration(event);
        }
        return sum;
    }

}
