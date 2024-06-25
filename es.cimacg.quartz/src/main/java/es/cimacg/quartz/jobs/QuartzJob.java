package es.cimacg.quartz.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@Slf4j
/**
 * Clase que define el trabajo a realizar por Quartz.
 */
public class QuartzJob implements Job {

    /**
     * Método que se ejecuta cuando se lanza el trabajo.
     *
     * @param context Contexto del trabajo.
     * @throws JobExecutionException Excepción lanzada si se produce un error en la ejecución del trabajo.
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Inicia JOB!!!!");
    }
}
