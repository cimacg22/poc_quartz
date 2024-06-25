package es.cimacg.quartz.definition;

import org.quartz.Trigger;

/**
 * Interface for quartz trigger definition included in scheduler
 * 
 * @author Minsait
 */
public interface IQuartzTriggerDefinition {

  /**
   * Get trigger
   * 
   * @return Trigger
   */
  Trigger getTrigger();
  
}
