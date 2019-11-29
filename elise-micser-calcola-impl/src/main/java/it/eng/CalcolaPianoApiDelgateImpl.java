
package it.eng;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.api.CalcolaPianoApiDelegate;
import it.eng.dto.CalcolaPianoRequest;
import it.eng.dto.CalcolaPianoResponse;
import it.eng.mapper.CalcolaPianoRequestMapper;
import it.eng.mapper.CalcolaPianoResponseMapper;
import it.eng.validator.ValidatorDelegate;

/**
 * Implementation of CallApi
 * this method expose the business logic of API
 * A delegate to be called by the CallApiController.
 */
@Component
public class CalcolaPianoApiDelgateImpl implements CalcolaPianoApiDelegate {
	private static Logger logger =
			LoggerFactory.getLogger(CalcolaPianoApiDelgateImpl.class);
	
	@Autowired
	private ValidatorDelegate validatorDelegate;

	@Override
	public void initBinder (WebDataBinder binder) {

		binder.addValidators(validatorDelegate);
	}

	// The HTTP DELETE request method to deletes the specified resources
		// return BaseResponse (Ok)
		@HystrixCommand(fallbackMethod = "defaultError" 
//				,commandProperties = {
//				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1") }, threadPoolProperties = {
//						@HystrixProperty(name = "coreSize", value = "1"), // the maximum number of HystrixCommands that can
																			// execute concurrently. Default 10
//						@HystrixProperty(name = "maxQueueSize", value = "-1"), // If -1 then SynchronousQueue will be used,
																				// otherwise a positive value will be used
																				// with LinkedBlockingQueue.
//		}
	)



		public ResponseEntity<io.swagger.model.CalcolaPianoResponse> calcolaPiano(io.swagger.model.CalcolaPianoRequest baseRequest) {
			try {
				logger.info("baseRequest: " + baseRequest.toString());
				CalcolaPianoRequest request = CalcolaPianoRequestMapper.MAPPER.fromBoundary(baseRequest);
				logger.info("mappedRequest: " + request.toString());
				CalcolaPianoResponse resp = new CalcolaPianoResponse();
				resp.setImportoRata(request.getImportoRichiesto()/360);
				io.swagger.model.CalcolaPianoResponse response = CalcolaPianoResponseMapper.MAPPER.toBoundary(resp);
				return new ResponseEntity<io.swagger.model.CalcolaPianoResponse>(response, HttpStatus.OK);
			} catch (Exception e) {
				logger.error("Error in method callApiPut", e);
				CalcolaPianoResponse resp = new CalcolaPianoResponse();
				io.swagger.model.CalcolaPianoResponse response = CalcolaPianoResponseMapper.MAPPER.toBoundary(resp);
				String c = null;
				c.getBytes();
				return new ResponseEntity<io.swagger.model.CalcolaPianoResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}


//		@HystrixCommand
//		private ResponseEntity<io.swagger.model.BaseResponse> defaultError(io.swagger.model.BaseRequest baseRequest) throws java.lang.Exception {
//			BaseResponse resp = new BaseResponse(Boolean.FALSE);
//			io.swagger.model.BaseResponse response = BaseResponseMapper.MAPPER.toBoundary(resp);
//			logger.info("testing new centralized exception handler");
//			throw new java.lang.Exception();
////			return new ResponseEntity(response, HttpStatus.NO_CONTENT);
//		}

		

		
		
		
		// Example of Logging annotation. You can use this annotation to trace on Jaeger
		// the
		// method performance
		// @Logging(performance=true)
		// private void perf() throws InterruptedException {
		// Thread.sleep(200);
		// }
}