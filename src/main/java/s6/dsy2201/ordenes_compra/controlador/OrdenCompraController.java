package s6.dsy2201.ordenes_compra.controlador;

import s6.dsy2201.ordenes_compra.dto.OrdenCompraRequestDTO;
import s6.dsy2201.ordenes_compra.dto.OrdenCompraResponseDTO;
import s6.dsy2201.ordenes_compra.servicio.OrdenCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenCompraController {

    @Autowired
    private OrdenCompraService service;

    // GET /api/ordenes → 200 OK
    @GetMapping
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    // GET /api/ordenes/{id} → 200 OK  o  404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> obtenerPorId(@PathVariable int id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ordenes/estado/{estado} → 200 OK
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.obtenerPorEstado(estado));
    }

    // GET /api/ordenes/buscar/{producto} → 200 OK
    @GetMapping("/buscar/{producto}")
    public ResponseEntity<List<OrdenCompraResponseDTO>> buscarPorProducto(@PathVariable String producto) {
        return ResponseEntity.ok(service.buscarPorProducto(producto));
    }

    // POST /api/ordenes → 201 Created
    @PostMapping
    public ResponseEntity<OrdenCompraResponseDTO> crear(@RequestBody OrdenCompraRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }

    // PUT /api/ordenes/{id} → 200 OK  o  404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<OrdenCompraResponseDTO> actualizar(@PathVariable int id,
                                                              @RequestBody OrdenCompraRequestDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/ordenes/{id} → 204 No Content (sin body)  o  404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        if (service.eliminar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
