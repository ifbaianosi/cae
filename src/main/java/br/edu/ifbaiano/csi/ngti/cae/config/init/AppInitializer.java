package br.edu.ifbaiano.csi.ngti.cae.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import br.edu.ifbaiano.csi.ngti.cae.config.JPAConfig;
import br.edu.ifbaiano.csi.ngti.cae.config.ServiceConfig;
import br.edu.ifbaiano.csi.ngti.cae.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { JPAConfig.class, ServiceConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?> [] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected Filter[] getServletFilters() {		
		CharacterEncodingFilter characterEncondingFilter = new CharacterEncodingFilter();
		characterEncondingFilter.setEncoding("UTF-8");
		characterEncondingFilter.setForceEncoding(true);
		
		HttpPutFormContentFilter httpPutForm = new HttpPutFormContentFilter();
		
		return new Filter[] { characterEncondingFilter, httpPutForm };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(new MultipartConfigElement(""));
		super.customizeRegistration(registration);
	}

}
