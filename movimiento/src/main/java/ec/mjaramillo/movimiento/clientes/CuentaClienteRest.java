package ec.mjaramillo.movimiento.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ec.mjaramillo.movimiento.model.entity.Cuenta;

@FeignClient(name="cuenta.mjaramilloapis.ec", url="localhost:8082")
public interface CuentaClienteRest {

	@GetMapping("/cuentas/obtenerCuenta/{idCuenta}")
	public Cuenta obtenerCuentaPorId(@PathVariable(value = "idCuenta") Long idCuenta);
	
	@PutMapping("/actualizar/{idCuenta}")
	public Cuenta actualizarCuenta(@PathVariable(value = "idCuenta") Long idCuenta, @RequestBody Cuenta cuentaRequest);
}
