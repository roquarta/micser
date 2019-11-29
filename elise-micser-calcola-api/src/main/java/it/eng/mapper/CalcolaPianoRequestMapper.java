
package it.eng.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import it.eng.dto.CalcolaPianoRequest;

/*
 * BaseRequestMapper
 * Example of declaration of the MapStruct code generator to create an implementation. 
 *  
 * Invoke mapping methods defined in other class (SwaggerMapper). 
 * Due to this annotation @Mapper, the new object will contain the methods from SwaggerMapper.class and add the behavior. 
 *
 */
@Mapper(uses=SwaggerMapper.class)
public interface CalcolaPianoRequestMapper {

	public static final CalcolaPianoRequestMapper MAPPER = Mappers.getMapper(CalcolaPianoRequestMapper.class);
	
	// The data type DATETIME is included in BaseRequest.class but MapStruct generation will include the behavior
	// from SwaggerMapper --> Eg. Calendar dateTimeValue OffsetDateTime conversion
	public io.swagger.model.CalcolaPianoRequest toBoundary(CalcolaPianoRequest req);
	
	public CalcolaPianoRequest fromBoundary(io.swagger.model.CalcolaPianoRequest req);
	
}