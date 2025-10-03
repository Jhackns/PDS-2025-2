package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import pe.edu.upeu.sysalmacen.modelo.Cliente;
import pe.edu.upeu.sysalmacen.modelo.Usuario;
import pe.edu.upeu.sysalmacen.modelo.Venta;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IVentaRepositoryTest {

    @Autowired
    private IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    private Cliente crearClienteBase() {
        Cliente c = new Cliente();
        c.setDniruc("CL" + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        c.setNombres("Cliente Test");
        c.setTipoDocumento("DNI");
        c.setDireccion("Av. Test 123");
        return clienteRepository.save(c);
    }

    private Usuario crearUsuarioBase() {
        Usuario u = new Usuario();
        u.setUser("uservt_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        u.setClave("clave");
        u.setEstado("ACT");
        return usuarioRepository.save(u);
    }

    private Venta crearVentaBase() {
        Cliente c = crearClienteBase();
        Usuario u = crearUsuarioBase();
        Venta v = new Venta();
        v.setPrecioBase(100.0);
        v.setIgv(18.0);
        v.setPrecioTotal(118.0);
        v.setCliente(c);
        v.setUsuario(u);
        v.setNumDoc("F001-000001");
        v.setFechaGener(LocalDateTime.now());
        v.setSerie("F001");
        v.setTipoDoc("FACT");
        return ventaRepository.save(v);
    }

    @Test
    @Rollback
    void testGuardarVenta() {
        Venta v = crearVentaBase();
        assertNotNull(v.getIdVenta());
        assertEquals(118.0, v.getPrecioTotal());
    }

    @Test
    void testBuscarPorId() {
        Venta v = crearVentaBase();
        Optional<Venta> encontrado = ventaRepository.findById(v.getIdVenta());
        assertTrue(encontrado.isPresent());
        assertEquals(v.getNumDoc(), encontrado.get().getNumDoc());
    }

    @Test
    void testActualizarVenta() {
        Venta v = crearVentaBase();
        v.setPrecioTotal(200.0);
        Venta actualizado = ventaRepository.save(v);
        assertEquals(200.0, actualizado.getPrecioTotal());
    }

    @Test
    void testListarVentas() {
        crearVentaBase();
        crearVentaBase();
        List<Venta> ventas = ventaRepository.findAll();
        assertFalse(ventas.isEmpty());
    }

    @Test
    void testEliminarVenta() {
        Venta v = crearVentaBase();
        Long id = v.getIdVenta();
        ventaRepository.deleteById(id);
        assertFalse(ventaRepository.findById(id).isPresent());
    }
}