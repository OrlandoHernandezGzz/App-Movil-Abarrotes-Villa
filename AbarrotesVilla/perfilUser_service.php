<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $us_usuario = $_GET['usuario'];
    //$us_usuario = "saucedo";

    $json=array();

    $consulta = "SELECT * FROM usuario WHERE us_usuario = '{$us_usuario}'";
    $resultado = mysqli_query($conexion, $consulta);

    if($registro=mysqli_fetch_array($resultado)){
        $result["us_id"]=$registro['us_id'];
        $result["us_usuario"]=$registro['us_usuario'];
        $result["us_password"]=$registro['us_password'];
        $json['usuario'][]=$result;
    }else{
       echo "No registra.";
    }

    mysqli_close($conexion);
	echo json_encode($json);
?>