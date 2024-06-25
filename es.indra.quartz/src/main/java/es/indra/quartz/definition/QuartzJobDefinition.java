package es.indra.quartz.definition;

import es.indra.quartz.jobs.QuartzJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobDefinition  implements IQuartzJobDefinition{

    @Override
    public JobDetail getJobDetail() {
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity("Qrtz_QuartzJob")
                .withDescription("Job de pruebas para starter")
                .storeDurably()
                .build();
    }
}
