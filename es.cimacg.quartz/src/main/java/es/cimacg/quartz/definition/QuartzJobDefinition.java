package es.cimacg.quartz.definition;

import es.cimacg.quartz.jobs.QuartzJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

/**
 * Definición del trabajo Quartz.
 */
@Component
public class QuartzJobDefinition implements IQuartzJobDefinition {

    /**
     * Nombre del trabajo.
     */
    protected static final String QRTZ_QUARTZ_JOB = "Qrtz_QuartzJob";

    /**
     * Obtiene el trabajo.
     *
     * @return Trabajo.
     */
    @Override
    public JobDetail getJobDetail() {
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity(QRTZ_QUARTZ_JOB)
                .withDescription("Job de pruebas para starter")
                .storeDurably()
                .build();
    }
}
