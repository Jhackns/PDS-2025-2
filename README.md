# Evidencias de Pruebas de Repositorios

Este documento servirá para adjuntar las capturas de pantalla de los tests funcionando para cada repositorio del proyecto.

## Cómo ejecutar las pruebas

- Ejecutar toda la suite: `mvn -q test`
- Ejecutar una clase de test específica: `mvn -q -Dtest=NombreDeLaClaseTest test`
  - Ejemplos:
    - `mvn -q -Dtest=IVentaRepositoryTest test`
    - `mvn -q -Dtest=IVentaDetalleRepositoryTest test`

> Nota (PowerShell): ejecuta los comandos directamente desde la carpeta del proyecto (`SysAlmacen`) para evitar errores de parsing.

## Repositorios cubiertos y archivos de prueba

- `IAccesoRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IAccesoRepositoryTest.java`
- `IMediaFileRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IMediaFileRepositoryTest.java`
- `IUsuarioRolRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IUsuarioRolRepositoryTest.java`
- `IVentaRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IVentaRepositoryTest.java`
- `IVentaDetalleRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IVentaDetalleRepositoryTest.java`
- `IVentCarritoRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IVentCarritoRepositoryTest.java`
- `IProductoRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IProductoRepositoryTest.java`
- `ICategoriaRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/ICategoriaRepositoryTest.java`
- `IClienteRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IClienteRepositoryTest.java`
- `IRolRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IRolRepositoryTest.java`
- `IMarcaRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IMarcaRepositoryTest.java`
- `IUnidadMedidaRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IUnidadMedidaRepositoryTest.java`
- `IUsuarioRepository` → `src/test/java/pe/edu/upeu/sysalmacen/repositorio/IUsuarioRepositoryTest.java`

## Capturas de pantalla

Inserta aquí las imágenes resultantes de la ejecución de pruebas. Puedes crear la carpeta `screenshots/` en la raíz del proyecto y pegar las capturas allí. Luego referencia cada imagen según corresponda.

- Acceso: `![Acceso](screenshots/IAccesoRepositoryTest.png)`
- MediaFile: `![MediaFile](screenshots/IMediaFileRepositoryTest.png)`
- UsuarioRol: `![UsuarioRol](screenshots/IUsuarioRolRepositoryTest.png)`
- Venta: `![Venta](screenshots/IVentaRepositoryTest.png)`
- VentaDetalle: `![VentaDetalle](screenshots/IVentaDetalleRepositoryTest.png)`
- VentCarrito: `![VentCarrito](screenshots/IVentCarritoRepositoryTest.png)`
- Producto: `![Producto](screenshots/IProductoRepositoryTest.png)`
- Categoria: `![Categoria](screenshots/ICategoriaRepositoryTest.png)`
- Cliente: `![Cliente](screenshots/IClienteRepositoryTest.png)`
- Rol: `![Rol](screenshots/IRolRepositoryTest.png)`
- Marca: `![Marca](screenshots/IMarcaRepositoryTest.png)`
- UnidadMedida: `![UnidadMedida](screenshots/IUnidadMedidaRepositoryTest.png)`
- Usuario: `![Usuario](screenshots/IUsuarioRepositoryTest.png)`

## Observaciones

- Los tests de `Venta` y `VentaDetalle` generan datos únicos (por ejemplo, `dniruc` y `user`) para evitar colisiones con restricciones de unicidad durante ejecuciones repetidas.
- La configuración de pruebas usa `@DataJpaTest` y `@AutoConfigureTestDatabase(replace = NONE)` para respetar la base de datos declarada en `application-test.properties`.