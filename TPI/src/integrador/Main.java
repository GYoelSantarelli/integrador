import integrador.Menu;
import integrador.entities.Categoria;
import integrador.service.CategoriaService;
import integrador.entities.Producto;
import integrador.service.ProductoService;
import integrador.entities.Usuario;
import integrador.enums.Rol;
import integrador.service.UsuarioService;
import integrador.entities.Pedido;
import integrador.entities.DetallePedido;

import integrador.enums.Estado;
import integrador.enums.FormaPago;

import integrador.service.PedidoService;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Menu menu = new Menu();
        Scanner sc = new Scanner(System.in);

        CategoriaService categoriaService =
                new CategoriaService();
        ProductoService productoService =
                new ProductoService();
        UsuarioService usuarioService =
                new UsuarioService();   
        PedidoService pedidoService =
                new PedidoService();
        int opcionPrincipal;

        do {

            opcionPrincipal =
                    menu.mostrarMenuPrincipal();

            switch (opcionPrincipal) {

                case 1:

                    int opcionCategoria;

                    do {

                        opcionCategoria =
                                menu.mostrarMenuCategorias();

                        switch (opcionCategoria) {

                            case 1:

                                System.out.print("ID: ");
                                Long id = sc.nextLong();
                                sc.nextLine();

                                System.out.print("Nombre: ");
                                String nombre =
                                        sc.nextLine();

                                System.out.print("Descripcion: ");
                                String descripcion =
                                        sc.nextLine();

                                Categoria categoria =
                                        new Categoria(
                                                id,
                                                nombre,
                                                descripcion
                                        );

                                categoriaService
                                        .crearCategoria(categoria);

                                System.out.println(
                                        "Categoria creada correctamente."
                                );

                                break;

                            case 2:

                                System.out.println(
                                        "\n=== LISTADO ==="
                                );

                                for (Categoria c :
                                        categoriaService.listarCategorias()) {

                                    System.out.println(c);
                                }

                                break;

                            case 3:

                                System.out.print("ID a buscar: ");

                                Long idBuscar =
                                        sc.nextLong();

                                try {

                                    Categoria encontrada =
                                            categoriaService
                                                    .buscarPorId(idBuscar);

                                    System.out.println(
                                            encontrada
                                    );

                                } catch (Exception e) {

                                    System.out.println(
                                            e.getMessage()
                                    );
                                }

                                break;

                            case 4:

                                System.out.print("ID a eliminar: ");

                                Long idEliminar =
                                        sc.nextLong();

                                try {

                                    categoriaService
                                            .eliminarCategoria(idEliminar);

                                    System.out.println(
                                            "Categoria eliminada."
                                    );

                                } catch (Exception e) {

                                    System.out.println(
                                            e.getMessage()
                                    );
                                }

                                break;

                            case 0:
                                break;

                            default:

                                System.out.println(
                                        "Opcion invalida."
                                );
                        }

                    } while (opcionCategoria != 0);

                    break;

                case 2:

    int opcionProducto;

    do {

        opcionProducto =
                menu.mostrarMenuProductos();

        switch (opcionProducto) {

            case 1:

                System.out.print("ID: ");
                Long idProducto = sc.nextLong();
                sc.nextLine();

                System.out.print("Nombre: ");
                String nombreProducto = sc.nextLine();

                System.out.print("Precio: ");
                Double precio = sc.nextDouble();
                sc.nextLine();

                System.out.print("Descripcion: ");
                String descripcionProducto = sc.nextLine();

                System.out.print("Stock: ");
                int stock = sc.nextInt();
                sc.nextLine();

                Producto producto =
                        new Producto(
                                idProducto,
                                nombreProducto,
                                precio,
                                descripcionProducto,
                                stock,
                                "",
                                true
                        );

                productoService.crearProducto(producto);

                System.out.println(
                        "Producto creado correctamente."
                );

                break;

            case 2:

                System.out.println(
                        "\n=== PRODUCTOS ==="
                );

                for (Producto p :
                        productoService.listarProductos()) {

                    System.out.println(p);
                }

                break;

            case 3:

                System.out.print(
                        "ID producto: "
                );

                Long idBuscarProducto =
                        sc.nextLong();

                try {

                    Producto encontrado =
                            productoService.buscarPorId(
                                    idBuscarProducto
                            );

                    System.out.println(
                            encontrado
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 4:

                System.out.print(
                        "ID producto: "
                );

                Long idEliminarProducto =
                        sc.nextLong();

                try {

                    productoService.eliminarProducto(
                            idEliminarProducto
                    );

                    System.out.println(
                            "Producto eliminado."
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 0:
                break;

            default:
                System.out.println(
                        "Opcion invalida."
                );
        }

    } while (opcionProducto != 0);

    break;

                case 3:

    int opcionUsuario;

    do {

        opcionUsuario =
                menu.mostrarMenuUsuarios();

        switch (opcionUsuario) {

            case 1:

                System.out.print("ID: ");
                Long idUsuario = sc.nextLong();
                sc.nextLine();

                System.out.print("Nombre: ");
                String nombreUsuario = sc.nextLine();

                System.out.print("Apellido: ");
                String apellido = sc.nextLine();

                System.out.print("Mail: ");
                String mail = sc.nextLine();

                System.out.print("Celular: ");
                String celular = sc.nextLine();

                System.out.print("Contraseña: ");
                String contrasenia = sc.nextLine();

                System.out.println("Rol:");
                System.out.println("1 - ADMIN");
                System.out.println("2 - CLIENTE");

                int opcionRol = sc.nextInt();

                Rol rol =
                        (opcionRol == 1)
                                ? Rol.ADMIN
                                : Rol.CLIENTE;

                try {

                    Usuario usuario =
                            new Usuario(
                                    idUsuario,
                                    nombreUsuario,
                                    apellido,
                                    mail,
                                    celular,
                                    contrasenia,
                                    rol
                            );

                    usuarioService.crearUsuario(
                            usuario
                    );

                    System.out.println(
                            "Usuario creado correctamente."
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 2:

                System.out.println(
                        "\n=== USUARIOS ==="
                );

                for (Usuario u :
                        usuarioService.listarUsuarios()) {

                    System.out.println(u);
                }

                break;

            case 3:

                System.out.print(
                        "ID usuario: "
                );

                Long idBuscarUsuario =
                        sc.nextLong();

                try {

                    Usuario usuario =
                            usuarioService.buscarPorId(
                                    idBuscarUsuario
                            );

                    System.out.println(
                            usuario
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 4:

                System.out.print(
                        "ID usuario: "
                );

                Long idEliminarUsuario =
                        sc.nextLong();

                try {

                    usuarioService.eliminarUsuario(
                            idEliminarUsuario
                    );

                    System.out.println(
                            "Usuario eliminado."
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 0:
                break;

            default:

                System.out.println(
                        "Opcion invalida."
                );
        }

    } while (opcionUsuario != 0);

    break;

                case 4:

    int opcionPedido;

    do {

        opcionPedido =
                menu.mostrarMenuPedidos();

        switch (opcionPedido) {

            case 1:

                System.out.print("ID Pedido: ");
                Long idPedido = sc.nextLong();

                Pedido pedido =
                        new Pedido(
                                idPedido,
                                Estado.PENDIENTE,
                                FormaPago.EFECTIVO
                        );

                pedidoService.crearPedido(
                        pedido
                );

                System.out.println(
                        "Pedido creado."
                );

                break;

            case 2:

                System.out.println(
                        "\n=== PEDIDOS ==="
                );

                for (Pedido p :
                        pedidoService.listarPedidos()) {

                    System.out.println(p);
                }

                break;

            case 3:

                try {

                    System.out.print(
                            "ID Pedido: "
                    );

                    Long pedidoId =
                            sc.nextLong();

                    System.out.print(
                            "ID Producto: "
                    );

                    Long productoId =
                            sc.nextLong();

                    System.out.print(
                            "Cantidad: "
                    );

                    int cantidad =
                            sc.nextInt();

                    Producto producto =
                            productoService.buscarPorId(
                                    productoId
                            );

                    pedidoService.agregarProducto(
                            pedidoId,
                            producto,
                            cantidad
                    );

                    System.out.println(
                            "Producto agregado."
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 4:

                try {

                    System.out.print(
                            "ID Pedido: "
                    );

                    Long idDetalle =
                            sc.nextLong();

                    Pedido p =
                            pedidoService.buscarPorId(
                                    idDetalle
                            );

                    System.out.println(
                            p
                    );

                    System.out.println(
                            "\nDETALLES:"
                    );

                    for (DetallePedido d :
                            p.getDetalles()) {

                        System.out.println(
                                d
                        );
                    }

                    System.out.println(
                            "\nTOTAL: $"
                                    + p.getTotal()
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 5:

                try {

                    System.out.print(
                            "ID Pedido: "
                    );

                    Long idEliminar =
                            sc.nextLong();

                    pedidoService.eliminarPedido(
                            idEliminar
                    );

                    System.out.println(
                            "Pedido eliminado."
                    );

                } catch (Exception e) {

                    System.out.println(
                            e.getMessage()
                    );
                }

                break;

            case 0:
                break;

            default:

                System.out.println(
                        "Opcion invalida."
                );
        }

    } while (opcionPedido != 0);

    break;

                case 0:
                    System.out.println("Hasta luego");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcionPrincipal != 0);
    }
}