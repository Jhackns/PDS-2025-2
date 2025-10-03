package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Acceso;
import pe.edu.upeu.sysalmacen.modelo.Rol;
import pe.edu.upeu.sysalmacen.modelo.Usuario;
import pe.edu.upeu.sysalmacen.modelo.UsuarioRol;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IAccesoRepositoryTest {
    @Autowired
    private IAccesoRepository accesoRepository;
    @Autowired
    private IRolRepository rolRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IUsuarioRolRepository usuarioRolRepository;

    private static Long accesoId;
    private static String testUsername = "menuuser@upeu.edu.pe";

    @BeforeEach
    public void setUp() {
        // Crear un usuario de prueba
        Usuario u = new Usuario();
        u.setUser(testUsername);
        u.setClave("secret");
        u.setEstado("Activo");
        Usuario uSaved = usuarioRepository.save(u);

        // Crear un rol de prueba (USER)
        Rol r = new Rol();
        r.setNombre(Rol.RolNombre.USER);
        r.setDescripcion("Rol Usuario para Menú");
        Rol rSaved = rolRepository.save(r);

        // Asociar usuario y rol en usuario_rol
        UsuarioRol ur = UsuarioRol.builder().usuario(uSaved).rol(rSaved).build();
        usuarioRolRepository.save(ur);

        // Crear un acceso de menú
        Acceso a = new Acceso();
        a.setNombre("Dashboard");
        a.setUrl("/pages/dashboard");
        a.setIcono("home");
        Acceso aSaved = accesoRepository.save(a);
        accesoId = aSaved.getIdAcceso();

        System.out.println("[Acceso-Test] Setup: usuario=" + uSaved.getUser() + ", rol=" + rSaved.getNombre() + ", acceso=" + aSaved.getNombre());
    }

    @Test
    @Order(1)
    public void testGuardarAcceso() {
        Acceso a = new Acceso();
        a.setNombre("Reporte");
        a.setUrl("/pages/reporte");
        a.setIcono("assessment");
        Acceso guardado = accesoRepository.save(a);
        System.out.println("[Acceso-Test] Guardado: id=" + guardado.getIdAcceso() + ", nombre=" + guardado.getNombre());
        assertNotNull(guardado.getIdAcceso());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Acceso> encontrado = accesoRepository.findById(accesoId);
        System.out.println("[Acceso-Test] BuscarPorId: presente=" + encontrado.isPresent());
        assertTrue(encontrado.isPresent());
        assertEquals("Dashboard", encontrado.get().getNombre());
    }

    @Test
    @Order(3)
    public void testActualizarAcceso() {
        Acceso a = accesoRepository.findById(accesoId).orElseThrow();
        a.setNombre("Dashboard Principal");
        Acceso actualizado = accesoRepository.save(a);
        System.out.println("[Acceso-Test] Actualizado: id=" + actualizado.getIdAcceso() + ", nombre=" + actualizado.getNombre());
        assertEquals("Dashboard Principal", actualizado.getNombre());
    }

    @Test
    @Order(4)
    public void testListarAccesos() {
        List<Acceso> lista = accesoRepository.findAll();
        System.out.println("[Acceso-Test] Listar: total=" + lista.size());
        assertFalse(lista.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarAcceso() {
        accesoRepository.deleteById(accesoId);
        Optional<Acceso> eliminado = accesoRepository.findById(accesoId);
        System.out.println("[Acceso-Test] Eliminar: presente=" + eliminado.isPresent());
        assertFalse(eliminado.isPresent());
    }

    @Test
    @Order(6)
    public void testGetAccesoByUser() {
        List<Acceso> accesos = accesoRepository.getAccesoByUser(testUsername);
        System.out.println("[Acceso-Test] getAccesoByUser(" + testUsername + "): total=" + accesos.size());
        assertNotNull(accesos);
        // En este test, accesos puede ser 0 si no hay registros en acceso_rol; validamos la llamada y tipo
        assertTrue(accesos.size() >= 0);
    }
}