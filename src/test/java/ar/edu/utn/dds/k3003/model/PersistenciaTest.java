package ar.edu.utn.dds.k3003.model;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenciaTest {

  static EntityManagerFactory entityManagerFactory ;
  EntityManager entityManager ;

  @BeforeAll
  public static void setUpClass() throws Exception {
    entityManagerFactory = Persistence.createEntityManagerFactory("colaboradoresdb");
  }
  @BeforeEach
  public void setup() throws Exception {
    entityManager = entityManagerFactory.createEntityManager();
  }
  @Test
  public void testConectar() {
// vacío, para ver que levante el ORM
  }
}
