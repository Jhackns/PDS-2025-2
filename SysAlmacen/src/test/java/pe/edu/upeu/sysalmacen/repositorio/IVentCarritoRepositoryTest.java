package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Producto;
import pe.edu.upeu.sysalmacen.modelo.Usuario;
import pe.edu.upeu.sysalmacen.modelo.VentCarrito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IVentCarritoRepositoryTest {
    @Autowired
    private IVentCarritoRepository ventCarritoRepository;
    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;

    private static Long carritoId;
    private static String dnirucBase = "43631917"; // Existe en data.sql

    @BeforeEach
    public void setUp() {
        Producto producto = productoRepository.findAll().stream().findFirst().orElse(null);
        Usuario usuario = usuarioRepository.findAll().stream().findFirst().orElse(null);
        assertNotNull(producto, "Se requiere al menos un producto para las pruebas");
        assertNotNull(usuario, "Se requiere al menos un usuario para las pruebas");

        VentCarrito vc = new VentCarrito();
        vc.setDniruc(dnirucBase);
        vc.setProducto(producto);
        vc.setNombreProducto(producto.getNombre());
        vc.setCantidad(2.0);
        vc.setPunitario(producto.getPu());
        vc.setPtotal(vc.getCantidad() * vc.getPunitario());
        vc.setEstado(1);
        vc.setUsuario(usuario);
        VentCarrito guardado = ventCarritoRepository.save(vc);
        carritoId = guardado.getIdCarrito();
        System.out.println("[Carrito-Test] Setup: id=" + carritoId + ", dniruc=" + guardado.getDniruc() + ", producto=" + guardado.getNombreProducto() + ", cantidad=" + guardado.getCantidad());
    }

    @Test
    @Order(1)
    public void testGuardarCarrito() {
        Producto producto = productoRepository.findAll().stream().findFirst().orElseThrow();
        Usuario usuario = usuarioRepository.findAll().stream().findFirst().orElseThrow();
        VentCarrito vc = new VentCarrito();
        vc.setDniruc(dnirucBase);
        vc.setProducto(producto);
        vc.setNombreProducto(producto.getNombre());
        vc.setCantidad(1.0);
        vc.setPunitario(producto.getPu());
        vc.setPtotal(vc.getCantidad() * vc.getPunitario());
        vc.setEstado(1);
        vc.setUsuario(usuario);
        VentCarrito guardado = ventCarritoRepository.save(vc);
        System.out.println("[Carrito-Test] Guardado: id=" + guardado.getIdCarrito() + ", ptotal=" + guardado.getPtotal());
        assertNotNull(guardado.getIdCarrito());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<VentCarrito> encontrado = ventCarritoRepository.findById(carritoId);
        System.out.println("[Carrito-Test] BuscarPorId: presente=" + encontrado.isPresent());
        assertTrue(encontrado.isPresent());
        assertEquals(carritoId, encontrado.get().getIdCarrito());
    }

    @Test
    @Order(3)
    public void testActualizarCarrito() {
        VentCarrito vc = ventCarritoRepository.findById(carritoId).orElseThrow();
        vc.setCantidad(3.0);
        vc.setPtotal(vc.getCantidad() * vc.getPunitario());
        VentCarrito actualizado = ventCarritoRepository.save(vc);
        System.out.println("[Carrito-Test] Actualizado: id=" + actualizado.getIdCarrito() + ", cantidad=" + actualizado.getCantidad() + ", ptotal=" + actualizado.getPtotal());
        assertEquals(3.0, actualizado.getCantidad());
    }

    @Test
    @Order(4)
    public void testListarCarritos() {
        List<VentCarrito> lista = ventCarritoRepository.findAll();
        System.out.println("[Carrito-Test] Listar: total=" + lista.size());
        assertFalse(lista.isEmpty());
    }

    @Test
    @Order(5)
    public void testListaCarritoClienteCustom() {
        List<VentCarrito> lista = ventCarritoRepository.listaCarritoCliente(dnirucBase);
        System.out.println("[Carrito-Test] Custom listaCarritoCliente dniruc=" + dnirucBase + ", total=" + lista.size());
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
    }

    @Test
    @Order(6)
    public void testEliminarCarrito() {
        ventCarritoRepository.deleteById(carritoId);
        Optional<VentCarrito> eliminado = ventCarritoRepository.findById(carritoId);
        System.out.println("[Carrito-Test] Eliminar: presente=" + eliminado.isPresent());
        assertFalse(eliminado.isPresent());
    }

    @Test
    @Order(7)
    public void testDeleteByDnirucCustom() {
        ventCarritoRepository.deleteByDniruc(dnirucBase);
        List<VentCarrito> lista = ventCarritoRepository.listaCarritoCliente(dnirucBase);
        System.out.println("[Carrito-Test] Custom deleteByDniruc dniruc=" + dnirucBase + ", restante=" + lista.size());
        assertEquals(0, lista.size());
    }
}