package ec.mjaramillo.cuenta.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ec.mjaramillo.cuenta.model.entity.Persona;

@FeignClient(name="persona.mjaramilloapis.ec", url="localhost:8081")
public interface PersonaClienteRest {
	
	@GetMapping("/clientes/obtenerCliente/{idCliente}")
	public Persona obtenerCliente(@PathVariable(value = "idCliente")  Long idCliente);
}
