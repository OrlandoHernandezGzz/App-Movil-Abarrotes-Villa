<?php
    //Incluimos la clase conexión para acceder a nuestra base de datos.
    include 'conexion.php';

    //Declaramos las variables que nos daran los valores a registrar.
    $codigo_prod = $_GET['codigo'];

    //$codigo_prod = "75053895";

    $json=array();

    $consulta = "SELECT * FROM producto WHERE codigo_prod = '{$codigo_prod}'";
    $resultado = mysqli_query($conexion, $consulta);

    if($registro=mysqli_fetch_array($resultado)){
        $result["codigo_prod"]=$registro['codigo_prod'];
        $result["nombre_prod"]=$registro['nombre_prod'];
        $result["descrip_prod"]=$registro['descrip_prod'];
        $result["cantidad_prod"]=$registro['cantidad_prod'];
        $result["cantres_prod"]=$registro['cantres_prod'];
        $result["preciocom_prod"]=$registro['preciocom_prod'];
        $result["precioven_prod"]=$registro['precioven_prod'];
        $json['producto'][]=$result;
    }else{
       echo "No registra.";
    }

    mysqli_close($conexion);
	echo json_encode($json);
?>