package it.eng.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * SwaggerMapper: 
 * Example of the generation of methods and/or adding characteristics via MapStruct
 * Eg. BaseRequest contain datetime that use this methods to manage convertion
 * 
 * Mapping methods add to mapping property to set the behavior in the final code genarated
 */
public class SwaggerMapper {
	public OffsetDateTime fromCalendar(Calendar date) {
		try {
			OffsetDateTime odt = OffsetDateTime.ofInstant(date.getTime().toInstant(),ZoneId.systemDefault());
			return odt;
        }
        catch ( Exception e ) {
            return null;
        }    }
	public Calendar toCalendar(OffsetDateTime date) {
        try {
        	Calendar toDate =  Calendar.getInstance();
        	toDate.setTime(java.util.Date.from( date.toInstant()));
        	return toDate;
        }
        catch ( Exception e ) {
            return null;
        }
    }
	
}
