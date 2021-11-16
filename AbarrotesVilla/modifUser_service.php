<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a modificar.
    $us_id = $_POST['codigo'];
    $us_nombre = $_POST['nombre'];
    $us_apellidos = $_POST['apellidos'];
    $us_telefono = $_POST['telefono'];
    $us_tipo = $_POST['tipo_usuario'];

    //Preparamos nuestras consulta para modificar los datos.
    $consulta = "UPDATE usuario SET us_nombre=?, us_apellidos=?, us_telefono=?, us_tipo=? 
                WHERE us_id=?";

    //Estamos creando una variable donde se prepare la consulta.
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("sssss", $us_nombre, $us_apellidos, $us_telefono, $us_tipo, $us_id);

    if($sql->execute()){
        echo "SeActualizo";
    } else{
        echo "NoActualizo";
    }

    //Cierra la conexión de la base de datos.
    $sql->close();
    mysqli_close($conexion);

?>