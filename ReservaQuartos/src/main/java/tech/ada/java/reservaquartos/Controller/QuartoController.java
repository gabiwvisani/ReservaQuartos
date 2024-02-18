package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.reservaquartos.Request.QuartoRequest;
import tech.ada.java.reservaquartos.domain.Entidades.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

@RestController
public class QuartoController {

    private final QuartoRepository quartoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public QuartoController(QuartoRepository quartoRepository, ModelMapper modelMapper) {
        this.quartoRepository = quartoRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/quarto")
    public ResponseEntity<Quarto> cadastrarQuarto(@RequestBody QuartoRequest quartoRequest){
        Quarto quartoConvertido = modelMapper.map(quartoRequest, Quarto.class);
        Quarto novoQuarto = quartoRepository.save(quartoConvertido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoQuarto);
    }

    // GET

    // GETBYID

    // PATCH



}
