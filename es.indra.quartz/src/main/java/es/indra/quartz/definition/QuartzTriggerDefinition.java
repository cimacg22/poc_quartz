package es.indra.quartz.definition;

import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QuartzTriggerDefinition implements IQuartzTriggerDefinition    {

    @Value("${es.indra.quartz.cronExpression}")
    private String cronExpression;

    @Override
    public Trigger getTrigger() {
        return TriggerBuilder.newTrigger().forJob("Qrtz_QuartzJob").withIdentity("Qrtz_QuartzJob_Trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
    }
}
