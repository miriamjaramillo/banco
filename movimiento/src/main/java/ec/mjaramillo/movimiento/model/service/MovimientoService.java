package ec.mjaramillo.movimiento.model.service;

import java.util.List;

import ec.mjaramillo.movimiento.model.entity.Cuenta;
import ec.mjaramillo.movimiento.model.entity.Movimiento;

public interface MovimientoService {
	
	public Cuenta obtenerCuentaPorId(Long idCuenta);
	public Cuenta actualizarCuenta(Long idCuenta, Cuenta cuenta);
	public List<Movimiento> obtenerMovimientosPorCuentaId(Long idCuenta);
	public Movimiento obtenerUltimoMovimientoPorCuentaId(Long idCuenta);
	public Movimiento movimientoPorCuenta(Long idCuenta, Movimiento movimiento);
}
