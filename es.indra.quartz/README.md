# es.indra.Quartz

## Objetivo

Proporcionar un `auto-configurador` de Spring para poder incluir en un artefacto
**procesos automáticos programados** gestionados por quartz.

## Cómo funciona?

Se debe incluir la siguiente configuración en el módulo en el que se quieran incluir procesos
automáticos.

```yaml
# Quartz properties
org:
  quartz:
    scheduler:
      instanceName: Proyecto-QuartzScheduler
      instanceId: AUTO
      instanceIdGenerator: org.quartz.simpl.SimpleInstanceIdGenerator
    threadPool:
      threadCount: 10
      threadPriority: 5
      class: org.quartz.simpl.SimpleThreadPool
    jobStore:
      misfireThreshold: 60000
      class: org.quartz.impl.jdbcjobstore.JobStoreTX
      useProperties: false
      dataSource: quartzds
      tablePrefix: QRTZ_
      isClustered: true
      clusterCheckinInterval: 20000
      driverDelegateClass: org.quartz.impl.jdbcjobstore.oracle.OracleDelegate
    dataSource:
      quartzds:
        user: ${QRTZ_SPRING_DATASOURCE_USERNAME:BD_USUARIOS}
        password: ${QRTZ_SPRING_DATASOURCE_PASSWORD:BD_USUARIOS}
        URL: ${QRTZ_SPRING_DATASOURCE_URL:jdbc:oracle:thin:@localhost:1521/XEPDB1}
        driver: ${QRTZ_SPRING_DATASOURCE_DRIVER:oracle.jdbc.OracleDriver}
        provider: hikaricp
```

Es importante revisar los valores, ya que los incluidos aquí (datasource, etc) son de ejemplo. En principio, el datasource será el mismo que el que utilice el microservicio en el que se quieren incorporar procesos, pero se establecen variables de entorno diferentes por si en algún momento esto no es así (QRTZ_SPRING_DATASOURCE_USERNAME, QRTZ_SPRING_DATASOURCE_PASSWORD...).

Hay que establecer un nombre de instancia concreto para cada servicio (quartz.scheduler.instanceName). En ningún caso debe dejarse el que viene de ejemplo.

El starter debe activarse con la siguiente propiedad de configuración:

```yaml
---
com:
  cg:
    quartz:
      enabled: true
```

En los ficheros yml de tests, no incluir la propiedad o incluirla con el valor false, ya que no se requiere inicializar quartz con toda la configuración cuando se cargue el contexto en los tests de integración.

Además de esta configuración, en el schema de base de datos habrá que ejecutar el script con todas las tablas de quartz (empiezan por QRTZ_). Importante establecer el esquema correcto en los índices:

```sql
CREATE TABLE QRTZ_JOB_DETAILS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  JOB_NAME VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  JOB_CLASS_NAME VARCHAR2(250) NOT NULL,
  IS_DURABLE NUMBER(1,0) NOT NULL,
  IS_NONCONCURRENT NUMBER(1,0) NOT NULL,
  IS_UPDATE_DATA NUMBER(1,0) NOT NULL,
  REQUESTS_RECOVERY NUMBER(1,0) NOT NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  JOB_NAME VARCHAR2(200) NOT NULL,
  JOB_GROUP VARCHAR2(200) NOT NULL,
  DESCRIPTION VARCHAR2(250) NULL,
  NEXT_FIRE_TIME NUMBER(13,0) NULL,
  PREV_FIRE_TIME NUMBER(13,0) NULL,
  PRIORITY NUMBER(5,0) NULL,
  TRIGGER_STATE VARCHAR2(16) NOT NULL,
  TRIGGER_TYPE VARCHAR2(8) NOT NULL,
  START_TIME NUMBER(13,0) NOT NULL,
  END_TIME NUMBER(13,0) NULL,
  CALENDAR_NAME VARCHAR2(200) NULL,
  MISFIRE_INSTR NUMBER(2,0) NULL,
  JOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP) REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME, JOB_NAME, JOB_GROUP)
);

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  REPEAT_COUNT NUMBER(7,0) NOT NULL,
  REPEAT_INTERVAL NUMBER(12,0) NOT NULL,
  TIMES_TRIGGERED NUMBER(10,0) NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CRON_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  CRON_EXPRESSION VARCHAR2(120) NOT NULL,
  TIME_ZONE_ID VARCHAR2(80),
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_SIMPROP_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  STR_PROP_1 VARCHAR2(512) NULL,
  STR_PROP_2 VARCHAR2(512) NULL,
  STR_PROP_3 VARCHAR2(512) NULL,
  INT_PROP_1 NUMBER(10,0) NULL,
  INT_PROP_2 NUMBER(10,0) NULL,
  LONG_PROP_1 NUMBER(19,0) NULL,
  LONG_PROP_2 NUMBER(19,0) NULL,
  DEC_PROP_1 NUMBER(13,4) NULL,
  DEC_PROP_2 NUMBER(13,4) NULL,
  BOOL_PROP_1 NUMBER(1,0) NULL,
  BOOL_PROP_2 NUMBER(1,0) NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_BLOB_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  BLOB_DATA BLOB NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
  FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) REFERENCES QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_CALENDARS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME VARCHAR2(200) NOT NULL,
  CALENDAR BLOB NOT NULL,
  PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);

CREATE TABLE QRTZ_FIRED_TRIGGERS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  ENTRY_ID VARCHAR2(95) NOT NULL,
  TRIGGER_NAME VARCHAR2(200) NOT NULL,
  TRIGGER_GROUP VARCHAR2(200) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  FIRED_TIME NUMBER(13,0) NOT NULL,
  SCHED_TIME NUMBER(13,0) NOT NULL,
  PRIORITY NUMBER(5,0) NOT NULL,
  STATE VARCHAR2(16) NOT NULL,
  JOB_NAME VARCHAR2(200) NULL,
  JOB_GROUP VARCHAR2(200) NULL,
  IS_NONCONCURRENT NUMBER(1,0) NULL,
  REQUESTS_RECOVERY NUMBER(1,0) NULL,
  PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);

CREATE TABLE QRTZ_SCHEDULER_STATE (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  INSTANCE_NAME VARCHAR2(200) NOT NULL,
  LAST_CHECKIN_TIME NUMBER(13,0) NOT NULL,
  CHECKIN_INTERVAL NUMBER(13,0) NOT NULL,
  PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);

CREATE TABLE QRTZ_LOCKS (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  LOCK_NAME VARCHAR2(40) NOT NULL,
  PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);

CREATE TABLE QRTZ_CALENDAR_ATTRIBUTES (
  SCHED_NAME VARCHAR2(120) NOT NULL,
  CALENDAR_NAME VARCHAR2(200) NOT NULL,
  ATTRIBUTE_NAME VARCHAR2(200) NOT NULL,
  ATTRIBUTE_VALUE VARCHAR2(500) NOT NULL,
  PRIMARY KEY (SCHED_NAME, CALENDAR_NAME, ATTRIBUTE_NAME)
);

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME, REQUESTS_RECOVERY) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME, JOB_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME, JOB_NAME, JOB_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME, JOB_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME, CALENDAR_NAME) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_STATE) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME, INSTANCE_NAME) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME, JOB_NAME, JOB_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_C_T_STATE ON QRTZ_CRON_TRIGGERS(SCHED_NAME, TRIGGER_NAME) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_ST_T_G ON QRTZ_SIMPLE_TRIGGERS(SCHED_NAME, TRIGGER_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_BLOB_TRIGGERS_T_G ON QRTZ_BLOB_TRIGGERS(SCHED_NAME, TRIGGER_GROUP) TABLESPACE TS_HOR_USU_DAT;
CREATE INDEX IDX_QRTZ_LCK_SCHED_NAME ON QRTZ_LOCKS(SCHED_NAME) TABLESPACE TS_HOR_USU_DAT;
```

Estas tablas serán las encargadas de almacenar los schedulers, los triggers (de los diferentes tipos), los jobs, sus siguientes ejecuciones, su programación, etc.

Una vez incluída la configuración, para tener un proceso automático es necesario definir 3 elementos:

- **Job**: la implementación del job como tal.
- **JobDefinition**: la definición del job, con el nombre y la descripción. Hace referencia al job. Debe implementar la interfaz QuartzJobDefinition.
- **TriggerDefinition**: la definición del trigger, con la definición del job al que hace referencia. Debe implementar la interfaz QuartzTriggerDefinition.

## Ejemplo de implementación

### Ejemplo de Job:

```java
@Component
@DisallowConcurrentExecution
@Slf4j
public class TestJob implements Job {

  @Autowired
  ApplicationContext applicationContext;
  
  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    log.info("JOB RUNNING!!!!");
    LoginUsuarioServiceImpl testService = applicationContext.getBean(LoginUsuarioServiceImpl.class);
    try {
      final TokenJWTDTO tokenJWTByUserRef = testService.getTokenJWTByUserRef("tramitador1");
      log.info("Token: " + tokenJWTByUserRef);
    } catch (UserNotFoundException e) {
      e.printStackTrace();
    }
  }

}
```

En este ejemplo se incluye el contexto de Spring, la obtención de un bean (como se haría con un scheduler, por ejemplo)
y la utilización del mismo de forma correcta.

### Ejemplo de JobDefinition:

```java
@Component
public class TestJobDefinition implements QuartzJobDefinition {

  @Override
  public JobDetail getJobDetail() {
    return JobBuilder.newJob(TestJob.class)
        .withIdentity("Qrtz_TestJob")
        .withDescription("Job de pruebas para starter")
        .storeDurably()
        .build();
  }

}
```

### Ejemplo de TriggerDefinition para CronScheduler:

```java
@Component
public class TestTriggerDefinition implements QuartzTriggerDefinition {

  @Value("${testjob.cron}")
  private String cronExpression;
  
  @Override
  public Trigger getTrigger() {
    return TriggerBuilder.newTrigger().forJob("Qrtz_TestJob").withIdentity("Qrtz_TestJob_Trigger")
        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();
  }

}
```

En este ejemplo, se necesita la variable de configuracion testjob.cron con una expresion cron.
Para ejecutar cada cinco minutos (0 - 5 - 10 - 15...): 0 0/5 * * * ?

### Ejemplo de TriggerDefinition para SimpleScheduler (no recomendado):

```java
@Component
public class TestTriggerDefinition implements QuartzTriggerDefinition {

  @Override
  public Trigger getTrigger() {

    SimpleScheduleBuilder scheduleBuilder =
        SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(1).repeatForever();

    return TriggerBuilder.newTrigger().forJob("Qrtz_TestJob").withIdentity("Qrtz_TestJob_Trigger")
        .withSchedule(scheduleBuilder).build();
  }

}
```

En este ejemplo, se ejecuta directamente cada minuto y se repite para siempre. Se recomienda
utilizar mayormente el cron, ya que se tiene un control más preciso de exactamente cuándo se va
a ejecutar el proceso.