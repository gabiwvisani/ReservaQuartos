package tech.ada.java.reservaquartos.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.ada.java.reservaquartos.domain.Entidades.Quarto;
import tech.ada.java.reservaquartos.Repository.QuartoRepository;

public class QuartoController {

    private final QuartoRepository quartoRepository;

    public QuartoController(QuartoRepository quartoRepository) {
        this.quartoRepository = quartoRepository;
    }


    //POST
//    @PostMapping("/quartos")
//    public ResponseEntity<Quarto> cadastrarQuarto(RequestBody QuartoRequest request){
//        return ResponseEntity.status()
//    }

    // GET

    // GETBYID


    // PATCH



}
