package ec.mjaramillo.cuenta.model.service;

import java.util.List;

import ec.mjaramillo.cuenta.model.entity.Cuenta;
import ec.mjaramillo.cuenta.model.entity.Persona;

public interface CuentaService {
	public Cuenta agregarCuenta(Cuenta cuenta, Long idPersona);
	public List<Cuenta> listarCuentas();
	public Cuenta obtenerCuenta(Long id);
	public Cuenta eliminarCuenta(Long id);
	public Cuenta editarCuenta(Long id, Cuenta cuenta);
	public List<Cuenta> obtenerCuentasPorCliente(Persona persona);
	public Persona obtenerPersonaPorId(Long idPersona);
}
