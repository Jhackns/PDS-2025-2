package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Categoria;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ICategoriaRepositoryTest {
    @Autowired
    private ICategoriaRepository categoriaRepository;

    private static Long categoriaId;

    @BeforeEach
    public void setUp() {
        Categoria categoria = new Categoria();
        categoria.setNombre("Electronica");
        Categoria guardada = categoriaRepository.save(categoria);
        categoriaId = guardada.getIdCategoria();
    }

    @Test
    @Order(1)
    public void testGuardarCategoria() {
        Categoria nueva = new Categoria();
        nueva.setNombre("Hogar");
        Categoria guardada = categoriaRepository.save(nueva);
        assertNotNull(guardada.getIdCategoria());
        assertEquals("Hogar", guardada.getNombre());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        assertTrue(categoria.isPresent());
        assertEquals("Electronica", categoria.get().getNombre());
    }

    @Test
    @Order(3)
    public void testActualizarCategoria() {
        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow();
        categoria.setNombre("Electrónica y gadgets");
        Categoria actualizada = categoriaRepository.save(categoria);
        assertEquals("Electrónica y gadgets", actualizada.getNombre());
    }

    @Test
    @Order(4)
    public void testListarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        assertFalse(categorias.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarCategoria() {
        categoriaRepository.deleteById(categoriaId);
        Optional<Categoria> eliminada = categoriaRepository.findById(categoriaId);
        assertFalse(eliminada.isPresent());
    }
}