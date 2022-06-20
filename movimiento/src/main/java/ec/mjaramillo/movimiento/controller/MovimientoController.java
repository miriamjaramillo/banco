package ec.mjaramillo.movimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.mjaramillo.movimiento.model.entity.Movimiento;
import ec.mjaramillo.movimiento.model.service.MovimientoService;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

	@Autowired
	@Qualifier("serviceMovimientoFeign")
	private MovimientoService movimientoService;
	
	@GetMapping("/listar/{idCuenta}/")
	public ResponseEntity<List<Movimiento>> obtenerMovimientosPorCuentaId(@PathVariable(value = "idCuenta") Long idCuenta) {
		List<Movimiento> listaMovimientos = movimientoService.obtenerMovimientosPorCuentaId(idCuenta);
		return new ResponseEntity<>(listaMovimientos, HttpStatus.OK);
	}
	
	@PostMapping("/{idCuenta}")
	public ResponseEntity<?> agregarMovimiento(@PathVariable(value = "idCuenta") Long idCuenta, @RequestBody Movimiento movimientoRequest) {
		Movimiento movimiento = movimientoService.movimientoPorCuenta(idCuenta, movimientoRequest);
		return new ResponseEntity<>(movimiento, HttpStatus.CREATED);
	}
}
