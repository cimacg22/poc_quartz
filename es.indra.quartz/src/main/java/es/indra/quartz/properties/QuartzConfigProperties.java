package es.indra.quartz.properties;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase QuartzConfigProperties
 *
 * @Configuration
 * Indica a Spring que debe considerar esta clase al configurar el contexto de la aplicación.
 *
 * Contiene propiedades relacionadas con la configuración de Quartz.
 *
 * @ConditionalOnProperty
 * asegura que esta configuración se active solo si la propiedad "gesstiona.quartz.enabled"
 * está presente en el archivo de propiedades y tiene el valor "true".
 *
 * @Getter y @Setter son de Lombok y generan automáticamente métodos getter y setter
 * para todos los campos de la clase, reduciendo así la necesidad de escribir código boilerplate.
 */
@Configuration
@ConditionalOnProperty(name = "es.indra.quartz.enabled", havingValue = "true")
@Getter
@Setter
public class QuartzConfigProperties {

  /**
   * schedulerInstanceName
   * Propiedad: Nombre de la instancia del planificador.
   * */
  @Value("${org.quartz.scheduler.instanceName}")
  private String schedulerInstanceName;

  /**
   * schedulerInstanceId
   * Propiedad: Id de la instancia del planificador.
   * */
  @Value("${org.quartz.scheduler.instanceId}")
  private String schedulerInstanceId;

  /**
   * schedulerInstanceIdGenerator
   * Propiedad: Generador de Id de instancia del planificador.
   * */
  @Value("${org.quartz.threadPool.class}")
  private String schedulerInstanceIdGenerator;

  /**
   * threadPoolThreadCount
   * Propiedad: Cantidad de hilos en el pool del planificador.
   */
  @Value("${org.quartz.threadPool.threadCount}")
  private String threadPoolThreadCount;

  /**
   * threadPoolThreadPriority
   * Propiedad: Prioridad de los hilos del pool del planificador.
   */
  @Value("${org.quartz.threadPool.threadPriority}")
  private String threadPoolThreadPriority;

  /**
   * jobStoreMisfireThreshold
   * Propiedad: Umbral de desencadenamiento perdido para los trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.misfireThreshold}")
  private String jobStoreMisfireThreshold;

  /**
   * jobStoreClass
   * Propiedad: Clase de almacenamiento de trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.class}")
  private String jobStoreClass;

  /**
   * jobStoreUseProperties
   * Propiedad: Indicador de uso de propiedades en la clase de almacenamiento de
   * trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.useProperties}")
  private String jobStoreUseProperties;

  /**
   * jobStoreDataSource
   * Propiedad: Origen de datos utilizado por la clase de almacenamiento de
   * trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.dataSource}")
  private String jobStoreDataSource;

  /**
   * jobStoreTablePrefix
   * Propiedad: Prefijo de tabla utilizado por la clase de almacenamiento de
   * trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.tablePrefix}")
  private String jobStoreTablePrefix;

  /**
   * jobStoreIsClustered
   * Propiedad: Indicador de clusterización de trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.isClustered}")
  private String jobStoreIsClustered;

  /**
   * jobStoreClusterCheckinInterval
   * Propiedad: Intervalo de registro de clusterización de trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.clusterCheckinInterval}")
  private String jobStoreClusterCheckinInterval;

  /**
   * jobStoreDriverDelegateClass
   * Propiedad: Clase de delegado del controlador de la base de datos para la clase de
   * almacenamiento de trabajos del planificador.
   */
  @Value("${org.quartz.jobStore.driverDelegateClass}")
  private String jobStoreDriverDelegateClass;

  /**
   * dataSourceUrl
   * Propiedad: URL del origen de datos utilizado por el planificador.
   */
  @Value("${org.quartz.dataSource.quartzds.URL}")
  private String dataSourceUrl;

  /**
   * dataSourceUser
   * Propiedad: Usuario del origen de datos utilizado por el planificador.
   */
  @Value("${org.quartz.dataSource.quartzds.user}")
  private String dataSourceUser;

  /**
   * dataSourcePassword
   * Propiedad: Contraseña del origen de datos utilizado por el planificador.
   */
  @Value("${org.quartz.dataSource.quartzds.password}")
  private String dataSourcePassword;

  /**
   * dataSourceDriver
   * Propiedad: Controlador del origen de datos utilizado por el planificador.
   */
  @Value("${org.quartz.dataSource.quartzds.driver}")
  private String dataSourceDriver;

  /**
   * dataSourceProvider.
   * Propiedad: Proveedor del origen de datos utilizado por el planificador.
   */
  @Value("${org.quartz.dataSource.quartzds.provider}")
  private String dataSourceProvider;

  /**
   * Method getProperties
   *
   * Método que devuelve las propiedades configuradas en esta clase.
   *
   * @return
   * Objeto Properties con las propiedades configuradas.
   */
  public Properties getProperties() {
    final Properties properties = new Properties();
    
    properties.setProperty("org.quartz.scheduler.instanceName", schedulerInstanceName);
    properties.setProperty("org.quartz.scheduler.instanceId", schedulerInstanceId);
    properties.setProperty("org.quartz.scheduler.instanceIdGenerator", schedulerInstanceIdGenerator);
    properties.setProperty("org.quartz.threadPool.threadCount", threadPoolThreadCount);
    properties.setProperty("org.quartz.threadPool.threadPriority", threadPoolThreadPriority);
    properties.setProperty("org.quartz.jobStore.misfireThreshold", jobStoreMisfireThreshold);
    properties.setProperty("org.quartz.jobStore.dataSource", jobStoreDataSource);
    properties.setProperty("org.quartz.jobStore.class", jobStoreClass);
    properties.setProperty("org.quartz.jobStore.useProperties", jobStoreUseProperties);
    properties.setProperty("org.quartz.jobStore.tablePrefix", jobStoreTablePrefix);
    properties.setProperty("org.quartz.jobStore.isClustered", jobStoreIsClustered);
    properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", jobStoreClusterCheckinInterval);
    properties.setProperty("org.quartz.jobStore.driverDelegateClass", jobStoreDriverDelegateClass);
    properties.setProperty("org.quartz.dataSource.quartzds.user", dataSourceUser);
    properties.setProperty("org.quartz.dataSource.quartzds.password", dataSourcePassword);
    properties.setProperty("org.quartz.dataSource.quartzds.URL", dataSourceUrl);
    properties.setProperty("org.quartz.dataSource.quartzds.driver", dataSourceDriver);
    properties.setProperty("org.quartz.dataSource.quartzds.provider", dataSourceProvider);
    
    return properties;
  }
  
}
