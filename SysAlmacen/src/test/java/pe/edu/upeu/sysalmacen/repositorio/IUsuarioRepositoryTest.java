package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Usuario;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IUsuarioRepositoryTest {
    @Autowired
    private IUsuarioRepository usuarioRepository;

    private static Long usuarioId;
    private static String usuarioUser;

    @BeforeEach
    public void setUp() {
        Usuario u = new Usuario();
        u.setUser("testuser@upeu.edu.pe");
        u.setClave("secret");
        u.setEstado("Activo");
        Usuario guardado = usuarioRepository.save(u);
        usuarioId = guardado.getIdUsuario();
        usuarioUser = guardado.getUser();
        System.out.println("[Usuario-Test] Setup: id=" + usuarioId + ", user=" + usuarioUser + ", estado=" + guardado.getEstado());
    }

    @Test
    @Order(1)
    public void testGuardarUsuario() {
        Usuario u = new Usuario();
        u.setUser("segundo@upeu.edu.pe");
        u.setClave("clave");
        u.setEstado("Activo");
        Usuario guardado = usuarioRepository.save(u);
        System.out.println("[Usuario-Test] Guardado: id=" + guardado.getIdUsuario() + ", user=" + guardado.getUser());
        assertNotNull(guardado.getIdUsuario());
        assertEquals("segundo@upeu.edu.pe", guardado.getUser());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Usuario> encontrado = usuarioRepository.findById(usuarioId);
        System.out.println("[Usuario-Test] BuscarPorId: presente=" + encontrado.isPresent());
        assertTrue(encontrado.isPresent());
        assertEquals(usuarioUser, encontrado.get().getUser());
    }

    @Test
    @Order(3)
    public void testActualizarUsuario() {
        Usuario u = usuarioRepository.findById(usuarioId).orElseThrow();
        u.setEstado("Inactivo");
        Usuario actualizada = usuarioRepository.save(u);
        System.out.println("[Usuario-Test] Actualizado: id=" + actualizada.getIdUsuario() + ", estado=" + actualizada.getEstado());
        assertEquals("Inactivo", actualizada.getEstado());
    }

    @Test
    @Order(4)
    public void testListarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        System.out.println("[Usuario-Test] Listar: total=" + usuarios.size());
        assertFalse(usuarios.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarUsuario() {
        usuarioRepository.deleteById(usuarioId);
        Optional<Usuario> eliminado = usuarioRepository.findById(usuarioId);
        System.out.println("[Usuario-Test] Eliminar: presente=" + eliminado.isPresent());
        assertFalse(eliminado.isPresent());
    }

    @Test
    @Order(6)
    public void testFindOneByUser() {
        Optional<Usuario> opt = usuarioRepository.findOneByUser(usuarioUser);
        System.out.println("[Usuario-Test] findOneByUser(" + usuarioUser + "): presente=" + opt.isPresent());
        assertTrue(opt.isPresent());
        assertEquals(usuarioUser, opt.get().getUser());
    }
}