<?php
    include 'conexion.php';

    $codigo_prod = $_GET['codigo'];

    $consulta = "DELETE FROM producto WHERE codigo_prod=?";
    $sql = $conexion->prepare($consulta);
    $sql->bind_param("s",$codigo_prod);

    if($sql->execute()){
        echo "elimina";
    }else{
        echo "noElimina";
    }
    
    $sql->close();
    mysqli_close($conexion);
?>