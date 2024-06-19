package com.cg.quartz;

import org.quartz.JobDetail;

/**
 * Interface for quartz job definition included in scheduler
 * 
 * @author Minsait
 */
public interface QuartzJobDefinition {

  /**
   * Get job detail
   * 
   * @return Job detail
   */
  JobDetail getJobDetail();
  
}
