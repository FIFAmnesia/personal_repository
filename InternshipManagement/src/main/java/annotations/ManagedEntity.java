package annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import rest.service.abstracts.GenericEntity;

@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface ManagedEntity {
  /**
   * The entity that is managed by the method
   * @return
   */
  Class<? extends GenericEntity> value();
}
