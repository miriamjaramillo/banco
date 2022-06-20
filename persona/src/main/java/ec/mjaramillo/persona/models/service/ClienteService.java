package ec.mjaramillo.persona.models.service;

import java.util.List;

import ec.mjaramillo.persona.models.entity.Cliente;
import ec.mjaramillo.persona.models.entity.Persona;

public interface ClienteService {
	public Cliente agregarCliente(Cliente cliente);
	public Persona editarCliente(Long idCliente, Cliente cliente);
	public List<Persona> listarClientes();
	public Persona obtenerPersona(Long id);
	public Persona eliminarPersona(Long idPersona);
}
