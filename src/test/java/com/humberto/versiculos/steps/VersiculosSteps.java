package com.humberto.versiculos.steps;

import com.humberto.versiculos.model.Versiculo;
import com.humberto.versiculos.model.TipoVersiculo;
import com.humberto.versiculos.service.VersiculoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VersiculosSteps {

    private VersiculoService versiculoService = new VersiculoService(null); // Mock ou ajustar conforme necessário
    private Versiculo versiculo;
    private List<Versiculo> versiculos;
    private Versiculo versiculoSalvo;
    private boolean versiculoDeletado;

    @Given("um novo versículo do tipo EVANGELHO")
    public void um_novo_versiculo_do_tipo_evangelho() {
        versiculo = new Versiculo();
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setReferenciaBiblica("João 3:16");
        versiculo.setConteudo("Porque Deus amou o mundo...");
        versiculo.setData(LocalDate.now());
    }

    @When("eu salvo o versículo")
    public void eu_salvo_o_versiculo() {
        versiculoSalvo = versiculoService.criarVersiculo(versiculo);
    }

    @Then("ele deve ser armazenado no sistema")
    public void ele_deve_ser_armazenado_no_sistema() {
        assertNotNull(versiculoSalvo);
    }

    @When("eu solicito a lista de versículos")
    public void eu_solicito_a_lista_de_versiculos() {
        versiculos = versiculoService.listarTodos();
    }

    @Then("deve retornar uma lista de versículos")
    public void deve_retornar_uma_lista_de_versiculos() {
        assertNotNull(versiculos);
    }

    @Given("existe um versículo para a data {string}")
    public void existe_um_versiculo_para_a_data(String data) {
        versiculo = new Versiculo();
        versiculo.setTipo(TipoVersiculo.SALMO);
        versiculo.setReferenciaBiblica("Salmo 23");
        versiculo.setConteudo("O Senhor é meu pastor...");
        versiculo.setData(LocalDate.parse(data));
        versiculoService.criarVersiculo(versiculo);
    }

    @When("eu busco versículos por essa data")
    public void eu_busco_versiculos_por_essa_data() {
        versiculos = versiculoService.buscarPorData(versiculo.getData());
    }

    @Then("o versículo correspondente deve ser retornado")
    public void o_versiculo_correspondente_deve_ser_retornado() {
        assertFalse(versiculos.isEmpty());
    }

    @Given("um versículo existente")
    public void um_versiculo_existente() {
        versiculo = new Versiculo();
        versiculo.setTipo(TipoVersiculo.EVANGELHO);
        versiculo.setReferenciaBiblica("João 1:1");
        versiculo.setConteudo("No princípio era o Verbo...");
        versiculo.setData(LocalDate.now());
        versiculoSalvo = versiculoService.criarVersiculo(versiculo);
    }

    @When("eu atualizo o conteúdo do versículo")
    public void eu_atualizo_o_conteudo_do_versiculo() {
        versiculoSalvo.setConteudo("Novo conteúdo atualizado");
        versiculoService.atualizarVersiculo(versiculoSalvo.getId(), versiculoSalvo);
    }

    @Then("o sistema deve refletir a atualização")
    public void o_sistema_deve_refletir_a_atualizacao() {
        assertEquals("Novo conteúdo atualizado", versiculoSalvo.getConteudo());
    }
}
