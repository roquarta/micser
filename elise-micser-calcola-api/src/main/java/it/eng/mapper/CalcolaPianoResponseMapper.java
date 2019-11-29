
package it.eng.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import it.eng.dto.CalcolaPianoResponse;


/*
 * BaseResponseMapper
 * Example of declaration of the MapStruct code generator to create an implementation. 
 * During compilation, MapStruct will generate an implementation of this interface
 */
@Mapper
public interface CalcolaPianoResponseMapper {

	public static final CalcolaPianoResponseMapper MAPPER = Mappers.getMapper(CalcolaPianoResponseMapper.class);
	
	// The data type DATETIME is included in BaseRequest.class but MapStruct generation will include the behavior
	// from SwaggerMapper --> Eg. Calendar dateTimeValue OffsetDateTime conversion
	public io.swagger.model.CalcolaPianoResponse toBoundary(CalcolaPianoResponse res);
	
	public CalcolaPianoResponse fromBoundary(io.swagger.model.CalcolaPianoResponse res);

}