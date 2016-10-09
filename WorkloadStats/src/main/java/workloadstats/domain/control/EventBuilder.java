package workloadstats.domain.control;

import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Map;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.PropertyFactoryImpl;
import net.fortuna.ical4j.model.PropertyList;
import net.fortuna.ical4j.model.property.Categories;
import net.fortuna.ical4j.model.property.DtEnd;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Summary;
import net.fortuna.ical4j.util.UidGenerator;
import workloadstats.domain.model.Course;
import workloadstats.domain.model.Event;
import workloadstats.domain.model.EventType;
import workloadstats.domain.model.Exercise;
import workloadstats.domain.model.Lecture;
import workloadstats.domain.model.Personal;
import workloadstats.domain.model.Teamwork;
import workloadstats.ui.Ac;
import workloadstats.ui.EvPropId;

/**
 * Builder for event creation and verification
 *
 * @author Ilkka
 */
public class EventBuilder {

    private EventBuilder() throws SocketException {
    }

    public static Event buildNewEvent(String summary, String startDate, String endDate, String type, String statusValue) throws ParseException, IOException, URISyntaxException {
        PropertyFactoryImpl pf = PropertyFactoryImpl.getInstance();
        UidGenerator ug = new UidGenerator("uidGen");
        PropertyList props = new PropertyList();
        props.add(new DtStart(startDate));
        props.add(new DtEnd(endDate));
        props.add(new DtStamp());
        props.add(new Summary(summary));
        props.add(ug.generateUid());
        props.add(new Categories(type));
        Property status = pf.createProperty(Property.STATUS);
        status.setValue(statusValue);
        props.add(status);
        
        EventType search = null;
        
        for (EventType et : EventType.values()) {
            if (et.name().equals(type)) {
                search = et;
            }            
        }

        Event iNeedAHome = null;
        switch (search) {
            case COURSE:
                iNeedAHome = new Course(props);
                break;
            case LECTURE:
                iNeedAHome = new Lecture(props);
                break;
            case EXERCISE:
                iNeedAHome = new Exercise(props);
                break;
            case PERSONAL:
                iNeedAHome = new Personal(props);
                break;
            case TEAMWORK:
                iNeedAHome = new Teamwork(props);
                break;
        }

        return iNeedAHome;
    }
    
    public static Event buildNewEvent(Map<EvPropId, String> userAnswers) throws ParseException, IOException, URISyntaxException {        
        String summary = userAnswers.get(EvPropId.EVENTNAME);
        String sDate = userAnswers.get(EvPropId.DATE);
        String sTime = userAnswers.get(EvPropId.STARTTIME);
        String eTime = userAnswers.get(EvPropId.ENDTIME);
        String type = userAnswers.get(EvPropId.EVENTTYPE);
        String status = userAnswers.get(EvPropId.STATUS);
        
        String startDateTime = sDate + "T" + sTime + "00";
        String endDateTime = sDate + "T" + eTime + "00";
        
        return buildNewEvent(summary, startDateTime, endDateTime, type, status);
    }
}