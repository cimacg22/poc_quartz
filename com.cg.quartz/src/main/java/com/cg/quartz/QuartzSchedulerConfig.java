package com.cg.quartz;

import java.util.Map;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Configuración del planificador Quartz.
 *
 * @Configuration
 * Indica que esta clase sirve como fuente de definición de bean para el contexto de la aplicación.
 *
 * @ConditionalOnProperty(name = "gesstiona.quartz.enabled", havingValue = "true")
 * Asegura que esta configuración se active solo si la propiedad "gesstiona.quartz.enabled"
 * está presente en el archivo de propiedades y tiene el valor "true".
 */
@Configuration
@ConditionalOnProperty(name = "com.cg.quartz.enabled", havingValue = "true")
public class QuartzSchedulerConfig {

  /**
   * Spring bean job factory
   * 
   * @param
   * applicationContext Application context
   *
   * @return
   * Spring bean job factory
   */
  @Bean
  SpringBeanJobFactory springBeanJobFactory(final ApplicationContext applicationContext) {
    // Configuracion de spring bean job factory
    final AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    // Establecemos el application context
    jobFactory.setApplicationContext(applicationContext);
    return jobFactory;
  }

  /**
   * Scheduler
   *
   * @param
   * triggers Definiciones de desencadenadores Quartz
   *
   * @param
   * jobs Definiciones de trabajos Quartz
   *
   * @param
   * applicationContext Contexto de la aplicación
   *
   * @param
   * quartzConfig Propiedades de configuración de Quartz
   *
   * @return
   * Fábrica del planificador Quartz
   *
   * @throws
   * SchedulerException Excepción lanzada en caso de errores del planificador Quartz
   */
  @Bean(name = "BeanQuartzScheduler")
  @Autowired
  SchedulerFactoryBean scheduler(Map<String, QuartzTriggerDefinition> triggers,
      Map<String, QuartzJobDefinition> jobs, final ApplicationContext applicationContext,
      final QuartzConfigProperties quartzConfig) throws SchedulerException {

    // Configuracion del scheduler
    final SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
    
    // Propiedades
    schedulerFactory.setQuartzProperties(quartzConfig.getProperties());

    // JobFactory, details y triggers
    schedulerFactory.setJobFactory(springBeanJobFactory(applicationContext));

    final JobDetail[] jobDetails =
        jobs.values().stream().map(QuartzJobDefinition::getJobDetail).toArray(JobDetail[]::new);
    final Trigger[] triggersArray =
        triggers.values().stream().map(QuartzTriggerDefinition::getTrigger).toArray(Trigger[]::new);

    schedulerFactory.setJobDetails(jobDetails);
    schedulerFactory.setTriggers(triggersArray);

    // Return factory
    return schedulerFactory;
  }

}
