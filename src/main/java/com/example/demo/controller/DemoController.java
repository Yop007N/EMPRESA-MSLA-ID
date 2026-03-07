package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Demo", description = "Endpoints de ejemplo")
@RestController
@RequestMapping("/api/demo")
@Validated
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @Operation(summary = "Obtener todos los items")
    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(demoService.findAll()));
    }

    @Operation(summary = "Obtener item por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> getById(@Parameter(description = "ID del item") @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(demoService.findById(id)));
    }

    @Operation(summary = "Crear un nuevo item")
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@Parameter(description = "Valor del item") @RequestParam @NotBlank String value) {
        Long id = demoService.create(value);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Item created", id));
    }

    @Operation(summary = "Eliminar un item por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@Parameter(description = "ID del item") @PathVariable Long id) {
        demoService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Item deleted", null));
    }
}
