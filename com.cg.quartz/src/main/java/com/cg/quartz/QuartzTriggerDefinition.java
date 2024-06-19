package com.cg.quartz;

import org.quartz.Trigger;

/**
 * Interface for quartz trigger definition included in scheduler
 * 
 * @author Minsait
 */
public interface QuartzTriggerDefinition {

  /**
   * Get trigger
   * 
   * @return Trigger
   */
  Trigger getTrigger();
  
}
