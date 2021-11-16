<?php
    //Hacemos la conexión hacia nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables.
    $codigo_prod = $_POST['codigo'];
    $cantidad_prod = $_POST['cantidad'];

    //Preparamos nuestra consulta para modificar los datos.
    $consulta = "UPDATE producto SET cantidad_prod=? WHERE codigo_prod=?";

    //Estamos creando una variable donde se prepare la consulta.
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("ss", $cantidad_prod, $codigo_prod);

    if($sql->execute()){
        echo "SeActualizo";
    } else{
        echo "NoActualizo";
    }

    //Cierra la conexión de la base de datos.
    $sql->close();
    mysqli_close($conexion);
?>