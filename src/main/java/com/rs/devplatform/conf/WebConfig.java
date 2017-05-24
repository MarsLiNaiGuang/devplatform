package com.rs.devplatform.conf;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan(basePackages={"com.rs.devplatform.controller"})
public class WebConfig {

	@Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false);
        resolver.setPrefix("");
        resolver.setSuffix(".html");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setExposeSessionAttributes(true);
        resolver.setRequestContextAttribute("request");
        return resolver;
    }
	
	@Bean
	public MyWebMvcConfigAdapter getMyWebMvcConfigAdapter(){
		return new MyWebMvcConfigAdapter();
	}

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
//                Ssl ssl = new Ssl();
//                //Server.jks中包含服务器私钥和证书
//                ssl.setKeyStore("tomcat.keystore");
//                ssl.setKeyStorePassword("123456");
//                container.setSsl(ssl);
                container.setPort(8080);
            }
        };
    }

   /* @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory =
                new TomcatEmbeddedServletContainerFactory() {
                    @Override
                    protected void postProcessContext(Context context) {
                        //SecurityConstraint必须存在，可以通过其为不同的URL设置不同的重定向策略。
                        SecurityConstraint securityConstraint = new SecurityConstraint();
                        securityConstraint.setUserConstraint("CONFIDENTIAL");
                        SecurityCollection collection = new SecurityCollection();
                        collection.addPattern("*//*");
                        securityConstraint.addCollection(collection);
                        context.addConstraint(securityConstraint);
                    }
                };
        factory.addAdditionalTomcatConnectors(createHttpConnector());
        return factory;
    }
    private Connector createHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(8443);
        connector.setRedirectPort(8080);

        File truststore = new File("tomcat.keystore");
        protocol.setKeystoreFile(truststore.getAbsolutePath());
        protocol.setKeystorePass("123456");
        protocol.setTruststoreFile(truststore.getAbsolutePath());
        protocol.setTruststorePass("123456");
        protocol.setClientAuth("true");
//        protocol.setKeyAlias("springboot");
        protocol.setSSLEnabled(true);

        return connector;
    }*/
}
