package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Rol;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IRolRepositoryTest {
    @Autowired
    private IRolRepository rolRepository;

    private static Long rolId;

    @BeforeEach
    public void setUp() {
        Rol r = new Rol();
        r.setNombre(Rol.RolNombre.USER);
        r.setDescripcion("Usuario App");
        Rol guardado = rolRepository.save(r);
        rolId = guardado.getIdRol();
        System.out.println("[Rol-Test] Setup: id=" + rolId + ", nombre=" + guardado.getNombre() + ", desc=" + guardado.getDescripcion());
    }

    @Test
    @Order(1)
    public void testGuardarRol() {
        Rol r = new Rol();
        r.setNombre(Rol.RolNombre.DBA);
        r.setDescripcion("Administrador DBA");
        Rol guardado = rolRepository.save(r);
        System.out.println("[Rol-Test] Guardado: id=" + guardado.getIdRol() + ", nombre=" + guardado.getNombre() + ", desc=" + guardado.getDescripcion());
        assertNotNull(guardado.getIdRol());
        assertEquals(Rol.RolNombre.DBA, guardado.getNombre());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Rol> encontrado = rolRepository.findById(rolId);
        System.out.println("[Rol-Test] BuscarPorId: presente=" + encontrado.isPresent());
        assertTrue(encontrado.isPresent());
        assertEquals(Rol.RolNombre.USER, encontrado.get().getNombre());
    }

    @Test
    @Order(3)
    public void testActualizarRol() {
        Rol r = rolRepository.findById(rolId).orElseThrow();
        r.setDescripcion("Usuario App Actualizado");
        Rol actualizada = rolRepository.save(r);
        System.out.println("[Rol-Test] Actualizado: id=" + actualizada.getIdRol() + ", desc=" + actualizada.getDescripcion());
        assertEquals("Usuario App Actualizado", actualizada.getDescripcion());
    }

    @Test
    @Order(4)
    public void testListarRoles() {
        List<Rol> roles = rolRepository.findAll();
        System.out.println("[Rol-Test] Listar: total=" + roles.size());
        assertFalse(roles.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarRol() {
        rolRepository.deleteById(rolId);
        Optional<Rol> eliminado = rolRepository.findById(rolId);
        System.out.println("[Rol-Test] Eliminar: presente=" + eliminado.isPresent());
        assertFalse(eliminado.isPresent());
    }

    @Test
    @Order(6)
    public void testFindByNombre() {
        Optional<Rol> opt = rolRepository.findByNombre(Rol.RolNombre.DBA);
        System.out.println("[Rol-Test] findByNombre(DBA): presente=" + opt.isPresent());
        assertTrue(opt.isPresent());
        assertEquals(Rol.RolNombre.DBA, opt.get().getNombre());
    }

    @Test
    @Order(7)
    public void testFindByDescripcion() {
        Optional<Rol> opt = rolRepository.findByDescripcion("Usuario App");
        System.out.println("[Rol-Test] findByDescripcion('Usuario App'): presente=" + opt.isPresent());
        assertTrue(opt.isPresent());
        assertEquals("Usuario App", opt.get().getDescripcion());
    }
}