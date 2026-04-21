package s6.dsy2201.ordenes_compra.servicio;

import s6.dsy2201.ordenes_compra.dto.OrdenCompraRequestDTO;
import s6.dsy2201.ordenes_compra.dto.OrdenCompraResponseDTO;
import s6.dsy2201.ordenes_compra.modelo.OrdenCompra;
import s6.dsy2201.ordenes_compra.repositorio.OrdenCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdenCompraService {

    @Autowired
    private OrdenCompraRepository repository;

    // ── Conversión entidad → ResponseDTO ──────────────────────────────────────
    private OrdenCompraResponseDTO convertirAResponse(OrdenCompra o) {
        return new OrdenCompraResponseDTO(o.getId(), o.getProducto(), o.getCantidad(), o.getEstado());
    }

    // ── GET todos ─────────────────────────────────────────────────────────────
    public List<OrdenCompraResponseDTO> obtenerTodas() {
        return repository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // ── GET por id ────────────────────────────────────────────────────────────
    public Optional<OrdenCompraResponseDTO> obtenerPorId(int id) {
        return repository.findById(id).map(this::convertirAResponse);
    }

    // ── GET por estado ────────────────────────────────────────────────────────
    public List<OrdenCompraResponseDTO> obtenerPorEstado(String estado) {
        return repository.findByEstado(estado)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // ── GET buscar por producto ───────────────────────────────────────────────
    public List<OrdenCompraResponseDTO> buscarPorProducto(String producto) {
        return repository.findByProductoContainingIgnoreCase(producto)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    // ── POST ──────────────────────────────────────────────────────────────────
    public OrdenCompraResponseDTO crear(OrdenCompraRequestDTO dto) {
        OrdenCompra nuevaOrden = new OrdenCompra();
        nuevaOrden.setProducto(dto.getProducto());
        nuevaOrden.setCantidad(dto.getCantidad());
        // Estado por defecto "Pendiente" si no viene en el body
        nuevaOrden.setEstado(
            (dto.getEstado() == null || dto.getEstado().isBlank()) ? "Pendiente" : dto.getEstado()
        );
        return convertirAResponse(repository.save(nuevaOrden));
    }

    // ── PUT ───────────────────────────────────────────────────────────────────
    public Optional<OrdenCompraResponseDTO> actualizar(int id, OrdenCompraRequestDTO dto) {
        return repository.findById(id).map(o -> {
            o.setProducto(dto.getProducto());
            o.setCantidad(dto.getCantidad());
            o.setEstado(dto.getEstado());
            return convertirAResponse(repository.save(o));
        });
    }

    // ── DELETE ────────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}