package com.deveficiente.nossobanco.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController()
public class EnderecoController {
  @Autowired
  private PropostaRepository propostaRepository;

  @PostMapping("/api/proposta/{id}/parte-2")
  public ResponseEntity parte(@RequestBody @Valid EnderecoPropostaRequest request, @PathVariable("id") Long id,
                              UriComponentsBuilder uriComponentsBuilder) {
    Endereco novoEndereco = request.toModel();
    Optional<Proposta> optionalProposta = propostaRepository.findById(id);
    if (optionalProposta.isPresent()) {
      Proposta proposta = optionalProposta.get();
      proposta.adicionaEndereco(novoEndereco);
      propostaRepository.save(proposta);
      URI uri = uriComponentsBuilder.path("/api/proposta/{id}/parte-3").buildAndExpand(id).toUri();
      return ResponseEntity.created(uri).build();
    }
    return ResponseEntity.notFound().build();
  }
}
