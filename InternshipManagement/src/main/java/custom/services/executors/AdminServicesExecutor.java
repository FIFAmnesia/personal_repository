package custom.services.executors;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import datastore.implementations.InternshipDataStore;
import entities.Internship;

@Stateless
public class AdminServicesExecutor {

  @EJB
  private InternshipDataStore internshipDataStore;

  public List<Internship> getAllInternships(String facultyNumber) {
    return internshipDataStore.findAllInternshipsForStudent(facultyNumber);
  }

}
