# SysAlmacen

Este proyecto contiene pruebas unitarias de repositorios JPA y un conjunto de capturas que se muestran automáticamente en GitHub desde este README.

## Cómo ejecutar pruebas
- Ejecuta `mvn -q test` para correr todo el suite.
- Si deseas usar la BD de pruebas, ejecuta con `-Dspring.profiles.active=test`.

## Capturas de pruebas
Las siguientes imágenes se referencian por ruta relativa dentro del repositorio, por lo que GitHub las mostrará como vista previa automáticamente.

![IAccesoRepositoryTest](screenshots/IAccesoRepositoryTest.png)
![ICategoriaRepositoryTest](screenshots/ICategoriaRepositoryTest.png)
![IClienteRepositoryTest](screenshots/IClienteRepositoryTest.png)
![IMediaFileRepositoryTest](screenshots/IMediaFileRepositoryTest.png)
![IProductoRepositoryTest](screenshots/IProductoRepositoryTest.png)
![IRolRepositoryTest](screenshots/IRolRepositoryTest.png)
![IUnidadMedidaRepositoryTest](screenshots/IUnidadMedidaRepositoryTest.png)
![IUsuarioRepositoryTest](screenshots/IUsuarioRepositoryTest.png)
![IUsuarioRolRepositoryTest](screenshots/IUsuarioRolRepositoryTest.png)
![IVentCarritoRepositoryTest](screenshots/IVentCarritoRepositoryTest.png)
![IVentaDetalleRepositoryTest](screenshots/IVentaDetalleRepositoryTest.png)
![IVentaRepositoryTest](screenshots/IVentaRepositoryTest.png)

## Notas
- Asegúrate de versionar la carpeta `screenshots/` junto con el README para que las imágenes se muestren en GitHub.
- Usa rutas relativas (como `screenshots/archivo.png`) para garantizar la visualización correcta.