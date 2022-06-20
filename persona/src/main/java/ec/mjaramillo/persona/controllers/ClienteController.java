package ec.mjaramillo.persona.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.mjaramillo.persona.models.entity.Cliente;
import ec.mjaramillo.persona.models.entity.Persona;
import ec.mjaramillo.persona.models.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@PostMapping("/crear")
	public ResponseEntity<?> agregarCliente(@RequestBody Cliente clienteRequest) {
		Cliente cliente = clienteService.agregarCliente(clienteRequest);
		return new ResponseEntity<>(cliente,HttpStatus.CREATED);
	}
	
	@GetMapping("/listarClientes")
	public List<Persona> obtenerClientes() {
		return clienteService.listarClientes();
	}
	
	@GetMapping("/obtenerCliente/{idCliente}")
	public Persona obtenerCliente(@PathVariable(value = "idCliente")  Long idCliente) {
		Persona persona =  clienteService.obtenerPersona(idCliente);		
		return persona;
	}
	
	@PutMapping("/actualizar/{idCliente}")
	public ResponseEntity<?> actualizarCliente(@PathVariable(value = "idCliente") Long idCliente, @RequestBody Cliente cliente){
		Persona clienteEditar = clienteService.editarCliente(idCliente, cliente);
		return ResponseEntity.ok(clienteEditar);
	}
	
	@DeleteMapping("/eliminar/{idPersona}")
	public Map<String, Boolean> eliminarCliente(@PathVariable(value = "idPersona") Long idPersona) {;
		clienteService.eliminarPersona(idPersona);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Estado eliminacion: ", Boolean.TRUE);
		return response;
	}
}
