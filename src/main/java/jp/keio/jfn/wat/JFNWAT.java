package jp.keio.jfn.wat;

import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.ServletContextAware;
import org.springframework.beans.factory.config.CustomScopeConfigurer;

import jp.keio.jfn.wat.scopes.ViewScope;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import com.sun.faces.config.ConfigureListener;

/**
 * This is the main class for the JFNWAT application.
 */
@SpringBootApplication
@Configuration
@ComponentScan
@Import(SecurityConfig.class)
public class JFNWAT implements ServletContextAware {

	public static void main(String[] args) throws Exception {
        SpringApplication springApplication =
                new SpringApplication(JFNWAT.class);
        springApplication.addListeners(
                new ApplicationPidFileWriter("./bin/jfnwat.pid"));
        springApplication.run(args);
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
		servletContext.setInitParameter("primefaces.THEME", "adamantium");
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		FacesServlet servlet = new FacesServlet();
		return new ServletRegistrationBean(servlet, "*.jsf");
	}

	@Bean
	public static ViewScope viewScope() {
		return new ViewScope();
	}

	@Bean
	public static CustomScopeConfigurer scopeConfigurer() {
		CustomScopeConfigurer configurer = new CustomScopeConfigurer();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("view", viewScope());
		configurer.setScopes(hashMap);
		return configurer;
	}

	@Bean
	public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
		return new ServletListenerRegistrationBean<ConfigureListener>(
				new ConfigureListener());
	}

}
