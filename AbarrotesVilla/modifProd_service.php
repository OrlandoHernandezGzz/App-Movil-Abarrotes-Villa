<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $codigo_prod = $_POST['codigo'];
    $nombre_prod = $_POST['producto'];
    $descrip_prod = $_POST['descripcion'];
    $cantidad_prod = $_POST['cantidad'];
    $cantres_prod = $_POST['cantidadReserva'];
    $preciocom_prod = $_POST['precioCompra'];
    $precioven_prod = $_POST['precioVenta'];

    //Preparamos nuestras consulta para modificar los datos.
    $consulta = "UPDATE producto SET nombre_prod=?, descrip_prod=?, cantidad_prod=?, cantres_prod=?, 
                  preciocom_prod=?, precioven_prod=? WHERE codigo_prod=?";

    //Estamos creando una variable donde se prepare la consulta.
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("sssssss", $nombre_prod, $descrip_prod, $cantidad_prod, $cantres_prod, $preciocom_prod,
          $precioven_prod, $codigo_prod);

    if($sql->execute()){
        echo "SeActualizo";
    } else{
        echo "NoActualizo";
    }

    //Cierra la conexión de la base de datos.
    $sql->close();
    mysqli_close($conexion);

?>