package com.cg.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * Adds auto-wiring support to quartz jobs.
 * 
 * @author Minsait
 */

/**
 * Clase que extiende SpringBeanJobFactory e implementa ApplicationContextAware.
 *
 * Esta clase se utiliza para personalizar la creación de instancias
 * de trabajos en Quartz, permitiendo la inyección de dependencias utilizando
 * la capacidad de autowiring de Spring.
 */

public final class AutoWiringSpringBeanJobFactory extends SpringBeanJobFactory
    implements ApplicationContextAware {

  /** Bean factory */
  private AutowireCapableBeanFactory beanFactory;

  /**
   * Sobrescribe el método para crear una instancia de trabajo (job) y realiza la autowiring
   * de dependencias utilizando el beanFactory.
   *
   * @param
   * bundle Bundle de información del trabajo.
   *
   * @return
   * Instancia del trabajo con autowiring de dependencias.
   *
   * @throws
   * Exception Si ocurre un error durante la creación de la instancia del trabajo.
   */
  @Override
  protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
    final Object job = super.createJobInstance(bundle);
    beanFactory.autowireBean(job);
    return job;
  }

  /**
   * Implementación del método de la interfaz ApplicationContextAware.
   *
   * Establece el ApplicationContext y obtiene el AutowireCapableBeanFactory
   * para su uso posterior.
   *
   * @param
   * applicationContext Contexto de la aplicación de Spring.
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    beanFactory = applicationContext.getAutowireCapableBeanFactory();
  }

}
