package datastore.abstracts;

import java.math.BigDecimal;
import java.util.List;

public interface DataStore<E> {

  public Class<E> getEntityClass();

  public E find(Long id);

  public List<E> findList();

  public E findTree(Long id);

  public List<E> findListTree();

  // TODO - to implement if necessary;
  // public Long count();
  // public Long countDistinct(String fieldName);
  // public BigDecimal min(String fieldName);
  // public BigDecimal max(String fieldName);
  // public BigDecimal average(String fieldName);
  // public BigDecimal sum(String fieldName);

  public E persist(E dto);

  public E merge(E ent);
  
  public E merge(E dto, E managed);

  public void remove(E dto);

  public void detach(E ent);

  public void flush();

}
