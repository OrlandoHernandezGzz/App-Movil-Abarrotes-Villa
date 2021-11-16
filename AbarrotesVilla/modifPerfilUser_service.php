<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a modificar.
    $us_id = $_POST['us_id'];
    $us_usuario = $_POST['us_usuario'];
    $us_password = $_POST['us_password'];
  
    //Preparamos nuestras consulta para modificar los datos.
    $consulta = "UPDATE usuario SET us_usuario=?, us_password=? WHERE us_id=?";

    //Estamos creando una variable donde se prepare la consulta.
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("sss", $us_usuario, $us_password, $us_id);

    if($sql->execute()){
        echo "SeActualizo";
    } else{
        echo "NoActualizo";
    }

    //Cierra la conexión de la base de datos.
    $sql->close();
    mysqli_close($conexion);

?>