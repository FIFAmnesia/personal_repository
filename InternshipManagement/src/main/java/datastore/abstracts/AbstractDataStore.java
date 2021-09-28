package datastore.abstracts;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;

public abstract class AbstractDataStore<E> implements DataStore<E> {

  protected abstract EntityManager getEntityManager();

  @Override
  public E persist(E dto) {
    getEntityManager().persist(dto);
    return dto;
  }

  @Override
  public E merge(E ent) {
    return getEntityManager().merge(ent);
  }

  @Override
  public E merge(E dto, E managed) {
    applyChanges(managed, dto);
    this.merge(managed);

    return managed;
  }

  @Override
  public void remove(E dto) {
    getEntityManager().remove(dto);
  }

  @Override
  public void detach(E ent) {
    if (ent != null) {
      getEntityManager().detach(ent);
    }
  }

  @Override
  public void flush() {
    getEntityManager().flush();
  }

  private void applyChanges(E managed, E incoming) {
    final Class<E> entityClass = getEntityClass();
    final List<Field> fields = getClassFields(entityClass);

    for (final Field f : fields) {
      f.setAccessible(true);
      try {
        if (f.isAnnotationPresent(Column.class) && !f.isAnnotationPresent(Id.class)) {
          Object newValue = f.get(incoming);
          if (newValue == null) {
            continue;
          }
          f.set(managed, newValue);
        }
      } catch (IllegalAccessException e) {
        // do nothing - shouldn't be reaching this;
      }
    }
  }

  private List<Field> getClassFields(Class<?> clazz) {
    List<Field> fields = new ArrayList<>();

    while (clazz != null) {
      for (Field f : clazz.getDeclaredFields()) {
        fields.add(f);
      }

      clazz = clazz.getSuperclass();
    }

    return fields;
  }

}
