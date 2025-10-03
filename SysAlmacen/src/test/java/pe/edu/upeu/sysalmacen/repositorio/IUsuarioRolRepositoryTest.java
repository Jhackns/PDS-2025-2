package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Rol;
import pe.edu.upeu.sysalmacen.modelo.Usuario;
import pe.edu.upeu.sysalmacen.modelo.UsuarioRol;
import pe.edu.upeu.sysalmacen.modelo.UsuarioRolPK;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IUsuarioRolRepositoryTest {
    @Autowired
    private IUsuarioRolRepository usuarioRolRepository;
    @Autowired
    private IUsuarioRepository usuarioRepository;
    @Autowired
    private IRolRepository rolRepository;

    private static Usuario savedUser;
    private static Rol savedRol;

    @BeforeEach
    public void setUp() {
        Usuario u = new Usuario();
        u.setUser("uruser@upeu.edu.pe");
        u.setClave("clave");
        u.setEstado("Activo");
        savedUser = usuarioRepository.save(u);

        Rol r = new Rol();
        r.setNombre(Rol.RolNombre.USER);
        r.setDescripcion("Rol Usuario");
        savedRol = rolRepository.save(r);

        UsuarioRol ur = UsuarioRol.builder().usuario(savedUser).rol(savedRol).build();
        usuarioRolRepository.save(ur);
        System.out.println("[UsuarioRol-Test] Setup: user=" + savedUser.getUser() + ", rol=" + savedRol.getNombre());
    }

    @Test
    @Order(1)
    public void testGuardarUsuarioRol() {
        Usuario u = new Usuario();
        u.setUser("uruser2@upeu.edu.pe");
        u.setClave("clave");
        u.setEstado("Activo");
        Usuario u2 = usuarioRepository.save(u);

        Rol r = new Rol();
        r.setNombre(Rol.RolNombre.DBA);
        r.setDescripcion("Rol DBA");
        Rol r2 = rolRepository.save(r);

        UsuarioRol ur2 = UsuarioRol.builder().usuario(u2).rol(r2).build();
        UsuarioRol saved = usuarioRolRepository.save(ur2);
        System.out.println("[UsuarioRol-Test] Guardado: usuario=" + saved.getUsuario().getUser() + ", rol=" + saved.getRol().getNombre());
        assertNotNull(saved.getUsuario().getIdUsuario());
        assertNotNull(saved.getRol().getIdRol());
    }

    @Test
    @Order(2)
    public void testBuscarPorIdCompuesto() {
        UsuarioRolPK pk = new UsuarioRolPK();
        // El PK usa entidades; las asignamos
        try {
            java.lang.reflect.Field fU = UsuarioRolPK.class.getDeclaredField("usuario");
            java.lang.reflect.Field fR = UsuarioRolPK.class.getDeclaredField("rol");
            fU.setAccessible(true); fR.setAccessible(true);
            fU.set(pk, savedUser); fR.set(pk, savedRol);
        } catch (Exception e) { throw new RuntimeException(e); }

        Optional<UsuarioRol> found = usuarioRolRepository.findById(pk);
        System.out.println("[UsuarioRol-Test] BuscarPorId: presente=" + found.isPresent());
        assertTrue(found.isPresent());
        assertEquals(savedUser.getUser(), found.get().getUsuario().getUser());
        assertEquals(savedRol.getNombre(), found.get().getRol().getNombre());
    }

    @Test
    @Order(3)
    public void testListarUsuarioRol() {
        List<UsuarioRol> list = usuarioRolRepository.findAll();
        System.out.println("[UsuarioRol-Test] Listar: total=" + list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    @Order(4)
    public void testFindOneByUsuarioUserCustom() {
        List<UsuarioRol> result = usuarioRolRepository.findOneByUsuarioUser(savedUser.getUser());
        System.out.println("[UsuarioRol-Test] findOneByUsuarioUser('" + savedUser.getUser() + "'): total=" + result.size());
        assertNotNull(result);
        assertTrue(result.size() >= 1);
        assertEquals(savedUser.getUser(), result.get(0).getUsuario().getUser());
    }

    @Test
    @Order(5)
    public void testEliminarUsuarioRol() {
        UsuarioRolPK pk = new UsuarioRolPK();
        try {
            java.lang.reflect.Field fU = UsuarioRolPK.class.getDeclaredField("usuario");
            java.lang.reflect.Field fR = UsuarioRolPK.class.getDeclaredField("rol");
            fU.setAccessible(true); fR.setAccessible(true);
            fU.set(pk, savedUser); fR.set(pk, savedRol);
        } catch (Exception e) { throw new RuntimeException(e); }

        usuarioRolRepository.deleteById(pk);
        Optional<UsuarioRol> deleted = usuarioRolRepository.findById(pk);
        System.out.println("[UsuarioRol-Test] Eliminar: presente=" + deleted.isPresent());
        assertFalse(deleted.isPresent());
    }
}