package ec.mjaramillo.cuenta.model.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ec.mjaramillo.cuenta.clientes.PersonaClienteRest;
import ec.mjaramillo.cuenta.exception.ResourceNotFoundException;
import ec.mjaramillo.cuenta.model.entity.Cuenta;
import ec.mjaramillo.cuenta.model.entity.Persona;
import ec.mjaramillo.cuenta.model.repository.CuentaRepository;

@Primary
@Service("serviceCuentaFeign")
public class CuentaServiceFeign implements CuentaService {
	
	private final PersonaClienteRest personaFeign;
	private final CuentaRepository cuentaRepository;
	
	@Autowired
    public CuentaServiceFeign(CuentaRepository cuentaRepository, PersonaClienteRest personaFeign) {
        this.cuentaRepository = cuentaRepository;
        this.personaFeign = personaFeign;
    }
	
	@Override
	public Persona obtenerPersonaPorId(Long id) {
		return personaFeign.obtenerCliente(id);
	}

	@Override
	public Cuenta agregarCuenta(Cuenta cuenta, Long idPersona) {
		
		//ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		//String personaJson = mapper.writeValueAsString(obtenerPersonaPorId(idPersona));
		//Persona persona = mapper.readValue(personaJson,Persona.class);

		Persona persona = personaFeign.obtenerCliente(idPersona);
		cuenta.setPersona(persona);
		return cuentaRepository.save(cuenta);
	}

	@Override
	public List<Cuenta> listarCuentas() {
		return StreamSupport
                .stream(cuentaRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
	}

	@Override
	public Cuenta obtenerCuenta(Long id) {
		return cuentaRepository.findById(id).orElseThrow(() -> 
			new ResourceNotFoundException("Cuenta no encontrada con el id :: " + id)
		);
	}

	@Override
	public Cuenta eliminarCuenta(Long id) {
		Cuenta cuenta = obtenerCuenta(id);
		cuentaRepository.delete(cuenta);
		return cuenta;
	}

	@Override
	public Cuenta editarCuenta(Long id, Cuenta cuenta) {
		Cuenta cuentaEditar = obtenerCuenta(id);
		cuentaEditar.setEstado(cuenta.getEstado());
		cuentaEditar.setNumeroCuenta(cuenta.getNumeroCuenta());
		cuentaEditar.setPersona(cuenta.getPersona());
		cuentaEditar.setSaldoInicial(cuenta.getSaldoInicial());
		cuentaEditar.setTipoCuenta(cuenta.getTipoCuenta());
		cuentaRepository.save(cuentaEditar);
		return cuentaEditar;
	}

	@Override
	public List<Cuenta> obtenerCuentasPorCliente(Persona persona) {
		return null;
	}

}
