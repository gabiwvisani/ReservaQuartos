package tech.ada.java.reservaquartos.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.reservaquartos.Request.AlteraValorQuartoRequest;
import tech.ada.java.reservaquartos.Request.QuartoRequest;
import tech.ada.java.reservaquartos.Domain.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/quarto")
    public List<Quarto> buscarTodos(){
        List<Quarto> listaComTodos = quartoRepository.findAll();
        return listaComTodos;
    }

    @GetMapping("/quarto/{id}")
    public ResponseEntity<?> buscarQuartoPorID(@PathVariable Integer id) {
        Optional<Quarto> optionalQuarto = quartoRepository.findById(id);

        if (optionalQuarto.isPresent()) {
            return ResponseEntity.ok(optionalQuarto.get());
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Não foi localizado um quarto com este ID.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PatchMapping("/quarto/{id}")
    public ResponseEntity<Quarto> alterarValorQuarto(
            @PathVariable Integer id,
            @RequestBody AlteraValorQuartoRequest request) throws Exception {

        Optional<Quarto> optionalQuarto = quartoRepository.findById(id);

        if (optionalQuarto.isPresent()) {
            Quarto quartoModificado = optionalQuarto.get();
            if (request.precoPorNoite() != null) quartoModificado.setPrecoPorNoite(request.precoPorNoite());

            Quarto quartoSalvo = quartoRepository.save(quartoModificado);
            return ResponseEntity.ok(quartoSalvo);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
