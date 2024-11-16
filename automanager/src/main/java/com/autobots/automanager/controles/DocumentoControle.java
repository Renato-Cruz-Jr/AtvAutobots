package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.*;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private DocumentoRepositorio documentoRepositorio;
    @Autowired
    private ClienteSelecionador selecionador;
    @Autowired
    private DocumentoCadastrador cadastrador;
    @Autowired
    private DocumentoAtualizador atualizador;
    @Autowired
    private DocumentoRemovedor removedor;
    @Autowired
    private AdicionadorLinkDocumento adicionadorLink;
    @Autowired
    private DocumentoSelecionador documentoSelecionador;
    @Autowired
    private AdicionadorLinkCliente adicionadorLinkCliente;

    @GetMapping("/visualizar/{id}")
    public ResponseEntity<Documento> visualizarDocumento(@PathVariable long id) {
        List<Documento> documentos = documentoRepositorio.findAll();
        Documento documento = documentoSelecionador.selecionar(documentos, id);
        if (documento != null) {
            adicionadorLink.adicionarLink(documento);
            ResponseEntity<Documento> resposta = new ResponseEntity<>(documento, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @PostMapping("/cadastrar/{clienteid}")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody List<Documento> documentos, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.CREATED;
        if (cliente != null) {
            cadastrador.cadastrar(cliente, documentos);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(status);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Documento>> listarDocumentos() {
        List<Documento> documentos = documentoRepositorio.findAll();
        if (!documentos.isEmpty()) {
            adicionadorLink.adicionarLink(documentos);
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return resposta;
        }
    }

    @GetMapping("/cliente/{clienteid}")
    public ResponseEntity<List<Documento>> visualizarDocumentosPorCliente(@PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        if (cliente != null) {
            adicionadorLink.adicionarLink(cliente.getDocumentos());
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(cliente.getDocumentos(), HttpStatus.FOUND);
            return resposta;
        } else {
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        }
    }

    @PutMapping("/atualizar/{clienteid}")
    public ResponseEntity<?> atualizarDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.OK;
        if (cliente != null) {
            atualizador.atualizar(cliente.getDocumentos(), documento);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/excluir/{clienteid}")
    public ResponseEntity<?> excluirDocumento(@RequestBody List<Documento> documento, @PathVariable long clienteid) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, clienteid);
        HttpStatus status = HttpStatus.OK;
        if (cliente != null) {
            removedor.excluir(cliente, documento);
            repositorio.save(cliente);
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
}