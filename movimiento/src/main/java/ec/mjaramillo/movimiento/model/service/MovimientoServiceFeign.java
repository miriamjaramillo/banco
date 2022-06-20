package ec.mjaramillo.movimiento.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ec.mjaramillo.movimiento.clientes.CuentaClienteRest;
import ec.mjaramillo.movimiento.exception.ResourceNotFoundException;
import ec.mjaramillo.movimiento.model.entity.Cuenta;
import ec.mjaramillo.movimiento.model.entity.Movimiento;
import ec.mjaramillo.movimiento.model.repository.CuentaRepository;
import ec.mjaramillo.movimiento.model.repository.MovimientoRepository;

@PropertySource("classpath:parametrizacion.properties")
@Primary
@Service("serviceMovimientoFeign")
public class MovimientoServiceFeign implements MovimientoService {
	
	@Autowired
	private Environment env;
	@Autowired
	private CuentaClienteRest cuentaFeign;
	@Autowired
	private MovimientoRepository movimientoRepository;
	@Autowired
	private CuentaRepository cuentaRepository;

	@Override
	public Cuenta obtenerCuentaPorId(Long idCuenta) {
		return cuentaFeign.obtenerCuentaPorId(idCuenta);
	}
	
	@Override
	public Cuenta actualizarCuenta(Long idCuenta, Cuenta cuenta) {
		return cuentaFeign.actualizarCuenta(idCuenta, cuenta);
	}

	@Override
	public List<Movimiento> obtenerMovimientosPorCuentaId(Long idCuenta) {
		Cuenta cuentaExiste = cuentaFeign.obtenerCuentaPorId(idCuenta);
		
		if (cuentaExiste == null) {
			throw new ResourceNotFoundException("No existe la cuenta con id = " + idCuenta);
		}
		
		List<Movimiento> movimientos = movimientoRepository.findMovimientosByCuentasCuentaId(idCuenta);
		
		return movimientos;
	}
	
	@Override
	public Movimiento obtenerUltimoMovimientoPorCuentaId(Long idCuenta) {
		
		Movimiento ultimoMovimiento = null;
		Cuenta cuentaExiste = cuentaFeign.obtenerCuentaPorId(idCuenta);
		
		if (cuentaExiste != null) {
			ultimoMovimiento = movimientoRepository.getLastMovimientoCuenta(idCuenta);
			
			if (ultimoMovimiento == null) {
				//throw new ResourceNotFoundException("No existe movimientos para la cuenta numero:: " + idCuenta);
			}
			
			return ultimoMovimiento;
			
		}else {
			throw new ResourceNotFoundException("No existe la cuenta con id = " + idCuenta);
		}
		
	}

	@Override
	public Movimiento movimientoPorCuenta(Long idCuenta, Movimiento movimiento) {        
		Cuenta cuentaExiste = cuentaFeign.obtenerCuentaPorId(idCuenta);
		Movimiento ultimoMovimiento = obtenerUltimoMovimientoPorCuentaId(idCuenta);
		int numMovimientos = movimientoRepository.obtenerTotalMovimientos(idCuenta);
		
		if (cuentaExiste != null) {
			
			Movimiento nuevoMovimiento = new Movimiento(movimiento.getTipoMovimiento(), movimiento.getValor());
			
			if (ultimoMovimiento != null) {
				
				Double saldoMovimientoDiario = movimientoRepository.obtenerSaldoMovimientoDiario(cuentaExiste.getCuentaId(), "RET");
				saldoMovimientoDiario = (saldoMovimientoDiario == null) ? Double.valueOf(0) : saldoMovimientoDiario;
				if (movimiento.getTipoMovimiento().equals("RET")) {
					if (nuevoMovimiento.getValor() > ultimoMovimiento.getSaldo()) {
						throw new ResourceNotFoundException("No es posible realizar un RETIRO del numero de cuenta:: " + idCuenta + ", no tiene saldo disponible.");
					}else {
						if ((saldoMovimientoDiario+movimiento.getValor()) >= Double.valueOf(env.getProperty("limite_diario"))) {
							throw new ResourceNotFoundException("Cupo diario excedido.");
						}else {
							nuevoMovimiento.setSaldo(ultimoMovimiento.getSaldo() - movimiento.getValor());
						}
					}
				}else {
					nuevoMovimiento.setSaldo(ultimoMovimiento.getSaldo() + movimiento.getValor());
				}
				
				Movimiento creacionMovimiento = movimientoRepository.save(nuevoMovimiento);
				List<Movimiento> listMovimientos = new ArrayList<>();
				listMovimientos.add(creacionMovimiento);
				
				cuentaExiste.setMovimientos(listMovimientos);
				
				cuentaRepository.save(cuentaExiste);
				
				return nuevoMovimiento;
			}else {
				if (movimiento.getTipoMovimiento() != null && movimiento.getValor() != null && movimiento.getValor() > 0) {
					if (movimiento.getTipoMovimiento().equals("DEP")) {
						
						nuevoMovimiento.setSaldo(movimiento.getValor());
						Movimiento creacionMovimiento = movimientoRepository.save(nuevoMovimiento);
						List<Movimiento> listMovimientos = new ArrayList<>();
						listMovimientos.add(creacionMovimiento);
						
						//Actualiza el saldo inicial
						if (numMovimientos == 0) {
							cuentaExiste.setSaldoInicial(nuevoMovimiento.getSaldo());
						}
						cuentaExiste.setMovimientos(listMovimientos);
						
						cuentaRepository.save(cuentaExiste);
						
						return nuevoMovimiento;
					} else {
						throw new ResourceNotFoundException("No es posible realizar un RETIRO del numero de cuenta:: " + idCuenta + ", no tiene saldo disponible.");
					}
				}else {
					throw new ResourceNotFoundException("Los valores ingresados son incorrectos, verifique los datos.");
				}
			}
			
		}else {
			throw new ResourceNotFoundException("No existe la cuenta con id = " + idCuenta);
		}
	}

	

}
