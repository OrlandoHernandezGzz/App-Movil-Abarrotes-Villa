<?php
    include 'conexion.php';

    $us_id = $_GET['codigo'];

    $consulta = "DELETE FROM usuario WHERE us_id=?";
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("s",$us_id);

    if($sql->execute()){
        echo "elimina";
    }else{
        echo "noElimina";
    }
    
    $sql->close();
    mysqli_close($conexion);
?>