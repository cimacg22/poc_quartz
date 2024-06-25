package es.cimacg.quartz.definition;

import org.quartz.JobDetail;

/**
 * Interface for quartz job definition included in scheduler
 *
 * @author Minsait
 */
public interface IQuartzJobDefinition {

    /**
     * Get job detail
     *
     * @return Job detail
     */
    JobDetail getJobDetail();

}
