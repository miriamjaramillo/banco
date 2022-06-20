package ec.mjaramillo.persona.models.repository;

import ec.mjaramillo.persona.models.entity.Cliente;

public interface ClienteRepository extends PersonaRepository{
	
	public Cliente findOneByIdentificacion(String identificacion);

}
