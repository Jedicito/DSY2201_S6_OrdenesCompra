package s6.dsy2201.ordenes_compra.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrdenCompraResponseDTO {

    private int    id;
    private String producto;
    private int    cantidad;
    private String estado;
}