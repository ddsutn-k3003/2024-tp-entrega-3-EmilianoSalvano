package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.model.Colaborador;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ColaboradorRepository {
  private static AtomicLong seqId = new AtomicLong();
  private final EntityManager entityManager;

  public ColaboradorRepository(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void save(Colaborador colaborador) {
    if(Objects.isNull(colaborador.getID())) {
      colaborador.setID(seqId.getAndIncrement());
      entityManager.persist(colaborador);
    }
  }

  public Colaborador findById(Long id) {
    Optional<Colaborador> first = Optional.ofNullable(entityManager.find(Colaborador.class, id));
    return first.orElseThrow(() -> new NoSuchElementException("No se encontro el colaborador con el id: " + id));
  }
}
