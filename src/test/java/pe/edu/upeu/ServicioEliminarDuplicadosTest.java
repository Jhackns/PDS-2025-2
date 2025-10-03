package pe.edu.upeu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServicioEliminarDuplicadosTest {

    private List<Integer> encontrarDuplicados(int[] sorted) {
        List<Integer> dups = new ArrayList<>();
        if (sorted == null || sorted.length == 0) return dups;
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                if (dups.isEmpty() || !dups.get(dups.size() - 1).equals(sorted[i])) {
                    dups.add(sorted[i]);
                }
            }
        }
        return dups;
    }

    @Test
    public void ejemplo1() {
        int[] nums = new int[]{1, 1, 2};
        List<Integer> dups = encontrarDuplicados(nums);
        ServicioEliminarDuplicados servicio = new ServicioEliminarDuplicadosImpl();
        int k = servicio.eliminarDuplicados(nums);
        System.out.println("Ejemplo1 - Duplicados encontrados: " + dups);
        System.out.println("Ejemplo1 - Únicos (" + k + "): " + Arrays.toString(Arrays.copyOf(nums, k)));
        Assertions.assertEquals(2, k, "k debe ser 2");
        int[] expected = new int[]{1, 2};
        for (int i = 0; i < k; i++) {
            Assertions.assertEquals(expected[i], nums[i], "Elemento único en posición " + i);
        }
    }

    @Test
    public void ejemplo2() {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        List<Integer> dups = encontrarDuplicados(nums);
        ServicioEliminarDuplicados servicio = new ServicioEliminarDuplicadosImpl();
        int k = servicio.eliminarDuplicados(nums);
        System.out.println("Ejemplo2 - Duplicados encontrados: " + dups);
        System.out.println("Ejemplo2 - Únicos (" + k + "): " + Arrays.toString(Arrays.copyOf(nums, k)));
        Assertions.assertEquals(5, k, "k debe ser 5");
        int[] expected = new int[]{0, 1, 2, 3, 4};
        for (int i = 0; i < k; i++) {
            Assertions.assertEquals(expected[i], nums[i], "Elemento único en posición " + i);
        }
    }

    @Test
    public void arregloVacio() {
        int[] nums = new int[]{};
        List<Integer> dups = encontrarDuplicados(nums);
        ServicioEliminarDuplicados servicio = new ServicioEliminarDuplicadosImpl();
        int k = servicio.eliminarDuplicados(nums);
        System.out.println("Arreglo vacío - Duplicados encontrados: " + dups);
        System.out.println("Arreglo vacío - Únicos (" + k + "): " + Arrays.toString(Arrays.copyOf(nums, k)));
        Assertions.assertEquals(0, k, "k debe ser 0");
    }

    @Test
    public void ejemploMockitoMock() {
        int[] nums = new int[]{1, 1, 2};
        ServicioEliminarDuplicados servicio = Mockito.mock(ServicioEliminarDuplicados.class);
        Mockito.when(servicio.eliminarDuplicados(Mockito.eq(nums))).thenReturn(2);
        int k = servicio.eliminarDuplicados(nums);
        Mockito.verify(servicio, Mockito.times(1)).eliminarDuplicados(nums);
        Assertions.assertEquals(2, k);
    }

    @Test
    public void ejemploMockitoSpy() {
        int[] nums = new int[]{1, 1, 2};
        ServicioEliminarDuplicadosImpl real = new ServicioEliminarDuplicadosImpl();
        ServicioEliminarDuplicados spy = Mockito.spy(real);
        int k = spy.eliminarDuplicados(nums);
        Mockito.verify(spy, Mockito.times(1)).eliminarDuplicados(nums);
        Assertions.assertEquals(2, k);
        int[] expected = new int[]{1, 2};
        for (int i = 0; i < k; i++) {
            Assertions.assertEquals(expected[i], nums[i]);
        }
    }
}