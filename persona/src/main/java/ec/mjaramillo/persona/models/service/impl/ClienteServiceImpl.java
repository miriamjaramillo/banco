package ec.mjaramillo.persona.models.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.mjaramillo.persona.exception.ResourceNotFoundException;
import ec.mjaramillo.persona.models.entity.Cliente;
import ec.mjaramillo.persona.models.entity.Persona;
import ec.mjaramillo.persona.models.repository.ClienteRepository;
import ec.mjaramillo.persona.models.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente agregarCliente(Cliente cliente) {
		Cliente existeCliente = null;
		
		try {
			existeCliente = clienteRepository.findOneByIdentificacion(cliente.getIdentificacion());
			
			if (existeCliente != null) {
				existeCliente = new Cliente(cliente.getIdentificacion(), cliente.getNombre(), cliente.getGenero(), 
									cliente.getEdad(), cliente.getDireccion(), cliente.getTelefono());
				existeCliente.setContrasena(cliente.getContrasena());
				existeCliente.setEstado(cliente.getEstado());
				
				clienteRepository.save(existeCliente);
				
			}else {
				clienteRepository.save(cliente);
			}
			
			
		} catch (Exception e) {
			
			return null;
		}
		
		return existeCliente;
	}

	@Override
	public Persona editarCliente(Long idCliente, Cliente cliente) {
		Persona personaExiste = obtenerPersona(idCliente);
		
		if (cliente.getDireccion() != null ) { personaExiste.setDireccion(cliente.getDireccion()); }
		if (cliente.getEdad() != null ) { personaExiste.setEdad(cliente.getEdad()); }
		if (cliente.getGenero() != null ) { personaExiste.setGenero(cliente.getGenero()); }
		if (cliente.getIdentificacion() != null ) { personaExiste.setIdentificacion(cliente.getIdentificacion()); }
		if (cliente.getNombre() != null ) { personaExiste.setNombre(cliente.getNombre()); }
		if (cliente.getTelefono() != null ) { personaExiste.setTelefono(cliente.getTelefono()); }
		
		clienteRepository.save(personaExiste);
		return personaExiste;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Persona> listarClientes() {
		return clienteRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Persona obtenerPersona(Long id) {
		return clienteRepository.findById(id).orElseThrow(() ->  
			new ResourceNotFoundException("Persona no encontrada con este id :: " + id)
		);
	}

	@Override
	public Persona eliminarPersona(Long idPersona) {
		Persona personaExiste = obtenerPersona(idPersona);
		clienteRepository.delete(personaExiste);
		return personaExiste;
	}

}
