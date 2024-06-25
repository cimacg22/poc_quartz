package es.indra.quartz.definition;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Definición del desencadenador Quartz.
 */
@Component
public class QuartzTriggerDefinition implements IQuartzTriggerDefinition    {


    // Identificador del desencadenador.
    private static final String QRTZ_QUARTZ_JOB_TRIGGER = QuartzJobDefinition.QRTZ_QUARTZ_JOB + "_Trigger";
    /**
     * Expresión cron.
     */
    @Value("${es.indra.quartz.cronExpression}")
    private String cronExpression;

    /**
     * Obtiene el desencadenador.
     *
     * @return Desencadenador.
     */
    @Override
    public Trigger getTrigger() {
        return TriggerBuilder.newTrigger().forJob(QuartzJobDefinition.QRTZ_QUARTZ_JOB).withIdentity(QRTZ_QUARTZ_JOB_TRIGGER)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
    }
}
