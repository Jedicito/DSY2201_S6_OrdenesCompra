package s6.dsy2201.ordenes_compra.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdenCompraRequestDTO {

    private String producto;
    private int    cantidad;
    private String estado;   // Opcional en POST — si viene null, el service pone "Pendiente"
}