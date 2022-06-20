package ec.mjaramillo.persona.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.mjaramillo.persona.models.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long>{

}
