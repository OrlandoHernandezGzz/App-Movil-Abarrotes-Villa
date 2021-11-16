<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $us_id = $_GET['codigo'];
    //$us_id = 1;

    $json=array();

    $consulta = "SELECT * FROM usuario WHERE us_id = '{$us_id}'";
    $resultado = mysqli_query($conexion, $consulta);

    if($registro=mysqli_fetch_array($resultado)){
        $result["us_id"]=$registro['us_id'];
        $result["us_nombre"]=$registro['us_nombre'];
        $result["us_apellidos"]=$registro['us_apellidos'];
        $result["us_telefono"]=$registro['us_telefono'];
        $result["us_tipo"]=$registro['us_tipo'];
        $json['usuario'][]=$result;
    }else{
       echo "No registra.";
    }

    mysqli_close($conexion);
	echo json_encode($json);
?>