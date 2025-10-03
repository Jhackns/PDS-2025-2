package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import pe.edu.upeu.sysalmacen.modelo.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IVentaDetalleRepositoryTest {

    @Autowired private IVentaDetalleRepository ventaDetalleRepository;
    @Autowired private IVentaRepository ventaRepository;
    @Autowired private IClienteRepository clienteRepository;
    @Autowired private IUsuarioRepository usuarioRepository;
    @Autowired private IProductoRepository productoRepository;
    @Autowired private ICategoriaRepository categoriaRepository;
    @Autowired private IMarcaRepository marcaRepository;
    @Autowired private IUnidadMedidaRepository unidadMedidaRepository;

    private Cliente crearClienteBase() {
        Cliente c = new Cliente();
        c.setDniruc("CL" + UUID.randomUUID().toString().replace("-", "").substring(0, 10));
        c.setNombres("Cliente VD");
        c.setTipoDocumento("DNI");
        c.setDireccion("Av. VD 456");
        return clienteRepository.save(c);
    }

    private Usuario crearUsuarioBase() {
        Usuario u = new Usuario();
        u.setUser("uservd_" + UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        u.setClave("clave");
        u.setEstado("ACT");
        return usuarioRepository.save(u);
    }

    private Producto crearProductoBase() {
        Categoria cat = new Categoria();
        cat.setNombre("Cat VD");
        cat = categoriaRepository.save(cat);

        Marca marca = new Marca();
        marca.setNombre("Marca VD");
        marca = marcaRepository.save(marca);

        UnidadMedida um = new UnidadMedida();
        um.setNombreMedida("Unidad VD");
        um = unidadMedidaRepository.save(um);

        Producto p = new Producto();
        p.setNombre("Prod VD");
        p.setPu(50.0);
        p.setPuOld(45.0);
        p.setUtilidad(5.0);
        p.setStock(100.0);
        p.setStockOld(120.0);
        p.setCategoria(cat);
        p.setMarca(marca);
        p.setUnidadMedida(um);
        return productoRepository.save(p);
    }

    private Venta crearVentaBase() {
        Cliente c = crearClienteBase();
        Usuario u = crearUsuarioBase();
        Venta v = new Venta();
        v.setPrecioBase(80.0);
        v.setIgv(14.4);
        v.setPrecioTotal(94.4);
        v.setCliente(c);
        v.setUsuario(u);
        v.setNumDoc("B001-000001");
        v.setFechaGener(LocalDateTime.now());
        v.setSerie("B001");
        v.setTipoDoc("BOLE");
        return ventaRepository.save(v);
    }

    private VentaDetalle crearVentaDetalleBase() {
        Venta v = crearVentaBase();
        Producto p = crearProductoBase();
        VentaDetalle vd = new VentaDetalle();
        vd.setPu(50.0);
        vd.setCantidad(2.0);
        vd.setDescuento(5.0);
        vd.setSubtotal(95.0);
        vd.setVenta(v);
        vd.setProducto(p);
        return ventaDetalleRepository.save(vd);
    }

    @Test
    @Rollback
    void testGuardarVentaDetalle() {
        VentaDetalle vd = crearVentaDetalleBase();
        assertNotNull(vd.getIdVentaDetalle());
        assertEquals(95.0, vd.getSubtotal());
    }

    @Test
    void testBuscarPorId() {
        VentaDetalle vd = crearVentaDetalleBase();
        Optional<VentaDetalle> encontrado = ventaDetalleRepository.findById(vd.getIdVentaDetalle());
        assertTrue(encontrado.isPresent());
        assertEquals(vd.getPu(), encontrado.get().getPu());
    }

    @Test
    void testActualizarVentaDetalle() {
        VentaDetalle vd = crearVentaDetalleBase();
        vd.setCantidad(3.0);
        vd.setSubtotal(145.0);
        VentaDetalle actualizado = ventaDetalleRepository.save(vd);
        assertEquals(3.0, actualizado.getCantidad());
        assertEquals(145.0, actualizado.getSubtotal());
    }

    @Test
    void testListarVentaDetalles() {
        crearVentaDetalleBase();
        crearVentaDetalleBase();
        List<VentaDetalle> lista = ventaDetalleRepository.findAll();
        assertFalse(lista.isEmpty());
    }

    @Test
    void testEliminarVentaDetalle() {
        VentaDetalle vd = crearVentaDetalleBase();
        Long id = vd.getIdVentaDetalle();
        ventaDetalleRepository.deleteById(id);
        assertFalse(ventaDetalleRepository.findById(id).isPresent());
    }
}