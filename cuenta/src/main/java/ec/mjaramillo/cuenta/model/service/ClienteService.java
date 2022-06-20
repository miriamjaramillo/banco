package ec.mjaramillo.cuenta.model.service;

import ec.mjaramillo.cuenta.model.entity.Persona;

public interface ClienteService {
	
	public Persona obtenerPersona(Long id);
	public Persona agregarCuentaAPersona(Long idPersona, Long idCuenta);
	public Persona eliminarCuentaDePersona(Long idPersona, Long idCuenta);

}
