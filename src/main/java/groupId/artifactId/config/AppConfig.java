package groupId.artifactId.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.List;

@Configuration
@EnableWebMvc
public class AppConfig implements WebApplicationInitializer, WebMvcConfigurer {

    @Override
    public void onStartup(final ServletContext sc) {
        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();
        root.scan("groupId.artifactId");
        sc.addListener(new ContextLoaderListener(root));
        ServletRegistration.Dynamic appServlet =
                sc.addServlet("mvc", new DispatcherServlet(new GenericWebApplicationContext()));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .featuresToDisable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .featuresToDisable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                .featuresToEnable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .featuresToEnable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE).build();
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Bean(name = "entityManger", destroyMethod = "close")
    public EntityManager getEntityManager() {
        return Persistence.createEntityManagerFactory("hibernate").createEntityManager();
    }
}
