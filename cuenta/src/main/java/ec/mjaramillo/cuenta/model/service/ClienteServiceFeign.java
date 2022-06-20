package ec.mjaramillo.cuenta.model.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.mjaramillo.cuenta.clientes.PersonaClienteRest;
import ec.mjaramillo.cuenta.exception.ResourceNotFoundException;
import ec.mjaramillo.cuenta.model.entity.Cliente;
import ec.mjaramillo.cuenta.model.entity.Cuenta;
import ec.mjaramillo.cuenta.model.entity.Persona;
import ec.mjaramillo.cuenta.model.repository.ClienteRepository;

@Service("serviceClienteFeign")
public class ClienteServiceFeign implements ClienteService {
	
	private final CuentaService cuentaService;
	private final PersonaClienteRest personaFeign;
	
	@Autowired
	public ClienteServiceFeign(ClienteRepository clienteRepository, CuentaService cuentaService, PersonaClienteRest personaFeign) {
		this.cuentaService = cuentaService;
		this.personaFeign = personaFeign;
	}

	@Override
	public Persona obtenerPersona(Long id) {
		return personaFeign.obtenerCliente(id);
	}

	@Override
	public Persona agregarCuentaAPersona(Long idPersona, Long idCuenta) {
		Persona persona = obtenerPersona(idPersona);
		Cuenta cuenta = cuentaService.obtenerCuenta(idCuenta);
		Cliente cliente = new Cliente(persona);
		
		if(Objects.nonNull(cuenta.getCuentaId())){
            throw new ResourceNotFoundException("La cuenta con " + idCuenta + " ya se encuentra asiganada a la persona con id "
            		+ cuenta.getPersona().getPersonaId());
        }
		
		cliente.agregarCuenta(cuenta);
		cuenta.setPersona(persona);
		return persona;
	}

	@Override
	public Persona eliminarCuentaDePersona(Long idPersona, Long idCuenta) {
		return null;
	}

}
