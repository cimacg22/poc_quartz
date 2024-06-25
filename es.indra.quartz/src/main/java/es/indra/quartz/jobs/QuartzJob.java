package es.indra.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@Slf4j
public class QuartzJob implements Job {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("JOB RUNNING!!!!");
        /*
        LoginUsuarioServiceImpl testService = applicationContext.getBean(LoginUsuarioServiceImpl.class);
        try {
            final TokenJWTDTO tokenJWTByUserRef = testService.getTokenJWTByUserRef("tramitador1");
            log.info("Token: " + tokenJWTByUserRef);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        */
    }
}
