package pe.edu.upeu.sysalmacen.repositorio;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pe.edu.upeu.sysalmacen.modelo.Cliente;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IClienteRepositoryTest {
    @Autowired
    private IClienteRepository clienteRepository;

    private static String clienteId;

    @BeforeEach
    public void setUp() {
        Cliente cliente = new Cliente();
        cliente.setDniruc("99999999");
        cliente.setNombres("Cliente Prueba");
        cliente.setRepLegal("Cliente Prueba");
        cliente.setTipoDocumento("DNI");
        cliente.setDireccion("Av. Principal 123");
        Cliente guardado = clienteRepository.save(cliente);
        clienteId = guardado.getDniruc();
    }

    @Test
    @Order(1)
    public void testGuardarCliente() {
        Cliente nuevo = new Cliente();
        nuevo.setDniruc("88888888");
        nuevo.setNombres("Juan Perez");
        nuevo.setRepLegal("Juan Perez");
        nuevo.setTipoDocumento("DNI");
        nuevo.setDireccion("Calle 1");
        Cliente guardado = clienteRepository.save(nuevo);
        assertNotNull(guardado.getDniruc());
        assertEquals("Juan Perez", guardado.getNombres());
    }

    @Test
    @Order(2)
    public void testBuscarPorId() {
        Optional<Cliente> cliente = clienteRepository.findById(clienteId);
        assertTrue(cliente.isPresent());
        assertEquals("Cliente Prueba", cliente.get().getNombres());
    }

    @Test
    @Order(3)
    public void testActualizarCliente() {
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow();
        cliente.setNombres("Cliente Prueba Actualizado");
        Cliente actualizada = clienteRepository.save(cliente);
        assertEquals("Cliente Prueba Actualizado", actualizada.getNombres());
    }

    @Test
    @Order(4)
    public void testListarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        assertFalse(clientes.isEmpty());
    }

    @Test
    @Order(5)
    public void testEliminarCliente() {
        clienteRepository.deleteById(clienteId);
        Optional<Cliente> eliminada = clienteRepository.findById(clienteId);
        assertFalse(eliminada.isPresent());
    }
}