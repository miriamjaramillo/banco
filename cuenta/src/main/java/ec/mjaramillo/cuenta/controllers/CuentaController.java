package ec.mjaramillo.cuenta.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.mjaramillo.cuenta.model.entity.Cuenta;
import ec.mjaramillo.cuenta.model.service.CuentaService;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
	
	@Autowired
	@Qualifier("serviceCuentaFeign")
	private CuentaService cuentaService;
	
	@GetMapping("/listarCuentas")
	public List<Cuenta> obtenerCuentas() {
		return cuentaService.listarCuentas();
	}
	
	@RequestMapping(value = "/agregar/{idPersona}", method = RequestMethod.POST)
    public Cuenta agregarCuentaPersona(@PathVariable(value = "idPersona") Long idPersona, @RequestBody Cuenta cuentaRequest){
        Cuenta cuentaCliente = cuentaService.agregarCuenta(cuentaRequest, idPersona);
        return cuentaCliente;
    }
	
	@PutMapping("/actualizar/{idCuenta}")
	public ResponseEntity<?> actualizarCuenta(@PathVariable(value = "idCuenta") Long idCuenta, @RequestBody Cuenta cuentaRequest){
		Cuenta cuentaEditar = cuentaService.editarCuenta(idCuenta, cuentaRequest);
		return ResponseEntity.ok(cuentaEditar);
	}
	
	@DeleteMapping("/eliminar/{idCuenta}")
	public Map<String, Boolean> eliminarCuenta(@PathVariable(value = "idCuenta") Long idCuenta) {
		Map<String, Boolean> response = new HashMap<>();
		cuentaService.eliminarCuenta(idCuenta);
		response.put("Cuenta eliminada", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/obtenerCuenta/{idCuenta}")
	public ResponseEntity<?> obtenerCuentaPorId(@PathVariable(value = "idCuenta") Long idCuenta) {
		Cuenta cuenta = cuentaService.obtenerCuenta(idCuenta);
		return ResponseEntity.ok().body(cuenta);
	}
	
}
