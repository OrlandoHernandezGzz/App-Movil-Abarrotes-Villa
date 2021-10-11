<?php
    //Importamos nuestra clase conexión.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $codigo_prod = $_POST['codigo'];
    $nombre_prod = $_POST['producto'];
    $descrip_prod = $_POST['descripcion'];
    $cantidad_prod = $_POST['cantidad'];
    $cantres_prod = $_POST['cantidadReserva'];
    $preciocom_prod = $_POST['precioCompra'];
    $precioven_prod = $_POST['precioVenta'];

    //Prepara la consulta para mandar a insertarla a la base de datos
    $consulta = "INSERT INTO producto (codigo_prod, nombre_prod, descrip_prod, cantidad_prod, 
    cantres_prod, preciocom_prod, precioven_prod) VALUES ('$codigo_prod','$nombre_prod','$descrip_prod',
    '$cantidad_prod','$cantres_prod','$preciocom_prod','$precioven_prod')";

    if(mysqli_query($conexion, $consulta)){
        echo "Registro Exitoso!";
    } else{
        echo "Error al registrar los datos". mysqli_error($conexion);
    }

    //-> Se usa para acceder a un miembro de un objeto
    $consulta->close();
    mysqli_close($conexion);

?>