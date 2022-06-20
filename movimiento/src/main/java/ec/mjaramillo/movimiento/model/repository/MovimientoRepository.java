package ec.mjaramillo.movimiento.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.mjaramillo.movimiento.model.entity.Movimiento;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long>{

	public List<Movimiento> findMovimientosByCuentasCuentaId(Long idCuenta);
	
	@Query("SELECT m FROM Movimiento m JOIN m.cuentas c WHERE c.cuentaId = ?1 ORDER BY m.fecha DESC")
	public Movimiento getLastMovimientoCuenta(Long idCuenta);
	
	@Query(value = "select sum(m.saldo) from movimiento m inner join cuenta_movimiento cm on m.movimiento_id = cm.movimiento_id "
			+ " inner join cuenta c on c.cuenta_id = cm.cuenta_id"
			+ " where to_date(cast(m.fecha as text), 'YYYY-MM-DD') >= current_date and m.tipo_movimiento = :tipoMovimiento"
			+ " and c.cuenta_id = :cuentaId group by m.fecha", nativeQuery = true)
	public Double obtenerSaldoMovimientoDiario(
			  @Param("cuentaId") Long cuentaId, @Param("tipoMovimiento") String tipoMovimiento);
	
	@Query(value="select count(cm.cuenta_id) from cuenta_movimiento cm\r\n" + 
			" inner join cuenta c on cm.cuenta_id = c.cuenta_id\r\n" + 
			" where c.cuenta_id = :cuentaId", nativeQuery = true)
	public int obtenerTotalMovimientos(@Param("cuentaId") Long cuentaId);
}
