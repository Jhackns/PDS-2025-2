package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Categoria;
import pe.edu.upeu.sysalmacen.modelo.Marca;
import pe.edu.upeu.sysalmacen.modelo.Producto;
import pe.edu.upeu.sysalmacen.modelo.UnidadMedida;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IProductoRepositoryTest {
    @Autowired
    private IProductoRepository productoRepository;
    @Autowired
    private ICategoriaRepository categoriaRepository;
    @Autowired
    private IMarcaRepository marcaRepository;
    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    private static Long productoId;

    @BeforeEach
    public void setUp() {
        Categoria categoria = categoriaRepository.findAll().stream().findFirst().orElseGet(() -> {
            Categoria c = new Categoria();
            c.setNombre("General");
            return categoriaRepository.save(c);
        });

        Marca marca = marcaRepository.findAll().stream().findFirst().orElseGet(() -> {
            Marca m = new Marca();
            m.setNombre("GenBrand");
            return marcaRepository.save(m);
        });

        UnidadMedida unidad = unidadMedidaRepository.findAll().stream().findFirst().orElseGet(() -> {
            UnidadMedida u = new UnidadMedida();
            u.setNombreMedida("Unidad");
            return unidadMedidaRepository.save(u);
        });

        Producto producto = new Producto();
        producto.setNombre("Producto Prueba");
        producto.setPu(100.0);
        producto.setPuOld(90.0);
        producto.setUtilidad(10.0);
        producto.setStock(50.0);
        producto.setStockOld(0.0);
        producto.setCategoria(categoria);
        producto.setMarca(marca);
        producto.setUnidadMedida(unidad);

        Producto guardado = productoRepository.save(producto);
        productoId = guardado.getIdProducto();
    }

    @Test
    @Order(1)
    public void testGuardarProducto() {
        Categoria categoria = categoriaRepository.findAll().get(0);
        Marca marca = marcaRepository.findAll().get(0);
        UnidadMedida unidad = unidadMedidaRepository.findAll().get(0);

        Producto nuevo = new Producto();
        nuevo.setNombre("Producto Nuevo");
        nuevo.setPu(120.0);
        nuevo.setPuOld(110.0);
        nuevo.setUtilidad(10.0);
        nuevo.setStock(20.0);
        nuevo.setStockOld(0.0);
        nuevo.setCategoria(categoria);
        nuevo.setMarca(marca);
        nuevo.setUnidadMedida(unidad);

        Producto guardado = productoRepository.save(nuevo);
        assertNotNull(guardado.getIdProducto());
        assertEquals("Producto Nuevo", guardado.getNombre());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Producto> encontrado = productoRepository.findById(productoId);
        assertTrue(encontrado.isPresent());
        assertEquals("Producto Prueba", encontrado.get().getNombre());
    }

    @Test
    @Order(3)
    public void testActualizarProducto() {
        Producto producto = productoRepository.findById(productoId).orElseThrow();
        producto.setNombre("Producto Prueba Actualizado");
        Producto actualizada = productoRepository.save(producto);
        assertEquals("Producto Prueba Actualizado", actualizada.getNombre());
    }

    @Test
    @Order(4)
    public void testListarProductos() {
        List<Producto> productos = productoRepository.findAll();
        assertFalse(productos.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarProducto() {
        productoRepository.deleteById(productoId);
        Optional<Producto> eliminada = productoRepository.findById(productoId);
        assertFalse(eliminada.isPresent());
    }
}