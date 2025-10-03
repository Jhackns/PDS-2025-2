package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.UnidadMedida;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IUnidadMedidaRepositoryTest {
    @Autowired
    private IUnidadMedidaRepository unidadMedidaRepository;

    private static Long unidadId;

    @BeforeEach
    public void setUp() {
        UnidadMedida um = new UnidadMedida();
        um.setNombreMedida("Metro");
        UnidadMedida guardada = unidadMedidaRepository.save(um);
        unidadId = guardada.getIdUnidad();
        System.out.println("[UnidadMedida-Test] Setup: id=" + unidadId + ", nombre=" + guardada.getNombreMedida());
    }

    @Test
    @Order(1)
    public void testGuardarUnidadMedida() {
        UnidadMedida nueva = new UnidadMedida();
        nueva.setNombreMedida("Litro");
        UnidadMedida guardada = unidadMedidaRepository.save(nueva);
        System.out.println("[UnidadMedida-Test] Guardado: id=" + guardada.getIdUnidad() + ", nombre=" + guardada.getNombreMedida());
        assertNotNull(guardada.getIdUnidad());
        assertEquals("Litro", guardada.getNombreMedida());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<UnidadMedida> encontrada = unidadMedidaRepository.findById(unidadId);
        System.out.println("[UnidadMedida-Test] BuscarPorId: presente=" + encontrada.isPresent());
        assertTrue(encontrada.isPresent());
        assertEquals("Metro", encontrada.get().getNombreMedida());
    }

    @Test
    @Order(3)
    public void testActualizarUnidadMedida() {
        UnidadMedida um = unidadMedidaRepository.findById(unidadId).orElseThrow();
        um.setNombreMedida("Metro Actualizado");
        UnidadMedida actualizada = unidadMedidaRepository.save(um);
        System.out.println("[UnidadMedida-Test] Actualizado: id=" + actualizada.getIdUnidad() + ", nombre=" + actualizada.getNombreMedida());
        assertEquals("Metro Actualizado", actualizada.getNombreMedida());
    }

    @Test
    @Order(4)
    public void testListarUnidadMedida() {
        List<UnidadMedida> unidades = unidadMedidaRepository.findAll();
        System.out.println("[UnidadMedida-Test] Listar: total=" + unidades.size());
        assertFalse(unidades.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarUnidadMedida() {
        unidadMedidaRepository.deleteById(unidadId);
        Optional<UnidadMedida> eliminada = unidadMedidaRepository.findById(unidadId);
        System.out.println("[UnidadMedida-Test] Eliminar: presente=" + eliminada.isPresent());
        assertFalse(eliminada.isPresent());
    }
}