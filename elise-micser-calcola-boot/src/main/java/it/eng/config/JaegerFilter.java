package it.eng.config;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/*
 *  JaegerFilter
 *  Take from the logger information to construct the jaeger line
 *  parentId	= Parent of this span
 *  traceId  	= The containder of the call flows.
 *  spanID 		= Is the current call
 */
@Component
public class JaegerFilter implements Filter {

	@Autowired(required = false)
	private io.opentracing.Tracer tracer;

	@Override
	public void init (FilterConfig filterConfig) throws ServletException {

		// Not Implementhed
	}

	@Override
	public void doFilter (ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (tracer != null && tracer.activeSpan() != null && tracer.activeSpan().context() != null) {
			String[] trace = tracer.activeSpan().context().toString().split(":"); // TraceId:SpanId:ParentId:x

			MDC.put("jaeger.traceId", trace[0]);
			MDC.put("jaeger.spanId", trace[1]);
			MDC.put("jaeger.parentId", trace[2]);

		}

		chain.doFilter(request, response);

		MDC.remove("jaeger.traceId");
		MDC.remove("jaeger.spanId");
		MDC.remove("jaeger.parentId");
	}

	@Override
	public void destroy () {

		// Not Implementhed
	}
}
