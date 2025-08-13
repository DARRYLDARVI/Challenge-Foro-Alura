package com.alura.foro.controller;

import com.alura.foro.domain.topico.DatosActualizarTopico;
import com.alura.foro.domain.topico.DatosRegistroTopíco;
import com.alura.foro.domain.topico.Topico;
import com.alura.foro.domain.topico.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/topico")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Transactional
    @PostMapping
    public ResponseEntity agregarTopico(@RequestBody @Valid DatosRegistroTopíco datos, UriComponentsBuilder uriComponentsBuilder) {
        var topico = new Topico(datos);
        repository.save(topico);

        var uri = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DatosRegistroTopíco(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosRegistroTopíco>> mostrarTopicos(@PageableDefault(size = 10, sort ={"curso"}) Pageable paginacion){
        var page = repository.findAll(paginacion).map(DatosRegistroTopíco::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public DatosRegistroTopíco detallarTopico(@PathVariable Long id){
        var topico = repository.getReferenceById(id);
        return new DatosRegistroTopíco(topico);
    }

    @Transactional
    @PutMapping
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datos) {
        var topico = repository.getReferenceById(datos.id());
        topico.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosRegistroTopíco(topico));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity EliminarTopico(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
