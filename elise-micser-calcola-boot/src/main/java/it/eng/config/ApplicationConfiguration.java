package it.eng.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import io.jaegertracing.Configuration.ReporterConfiguration;
import io.jaegertracing.Configuration.SamplerConfiguration;
import io.jaegertracing.Configuration.SenderConfiguration;
import it.eng.interceptor.RestTemplateHeaderModifierInterceptor;

/**
 * ApplicationConfiguration: 
 * Define the application configuration.
 *
 */
@Configuration
public class ApplicationConfiguration {

	@Value("${restTemplate.connectionTimeout}")
	private int connectionTimeout;

	@Value("${restTemplate.readTimeout}")
	private int readTimeout;

	@Bean
	public RestTemplate restTemplate (RestTemplateBuilder restTemplateBuilder)
		throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = restTemplateBuilder.setConnectTimeout(connectionTimeout).setReadTimeout(readTimeout).build();
		restTemplate.setInterceptors(Arrays.asList(new RestTemplateHeaderModifierInterceptor()));
		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}
    
    /*
     *  Configuration to Jaeger trace. Enable the jaegger process 
     */
    @Bean
	public io.opentracing.Tracer jaegerTracer(JaegerOpentracing jaegerProperties) {
		String name = jaegerProperties.getAppname();

		io.jaegertracing.Configuration jaegerConfiguration = new io.jaegertracing.Configuration(name);
		SamplerConfiguration samplerConfiguration = SamplerConfiguration.fromEnv().withParam(jaegerProperties.getSamplerconfiguration());
		ReporterConfiguration reporterConfiguration = ReporterConfiguration.fromEnv().withLogSpans(jaegerProperties.getLogspans());
		SenderConfiguration senderConf = reporterConfiguration.getSenderConfiguration()
				.withAgentHost(jaegerProperties.getEndpoint());
		reporterConfiguration.withSender(senderConf);

		return jaegerConfiguration.withReporter(reporterConfiguration).withSampler(samplerConfiguration).getTracer();
	}
    
    /*
     * Inject it.ubi.services logger definition 
     */
//    @Bean
//    public it.ubi.services.logger.jaeger.JaegerTracingLoggingConfiguration getJaegerTracingLoggingConfiguration(){
//    	it.ubi.services.logger.jaeger.JaegerTracingLoggingConfiguration config = new it.ubi.services.logger.jaeger.JaegerTracingLoggingConfiguration();
//    	return config;
//    }

}
