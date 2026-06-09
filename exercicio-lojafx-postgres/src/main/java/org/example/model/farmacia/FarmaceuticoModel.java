package org.example.model.farmacia;

public class FarmaceuticoModel {

    private Integer id;
    private Integer farmaciaId;
    private String nome;
    private String crf;          // Conselho Regional de Farmácia
    private boolean estaDeServico;

    public FarmaceuticoModel() {}

    public FarmaceuticoModel(String nome, String crf) {
        this.nome = nome;
        this.crf = crf;
        this.estaDeServico = false;
    }

    public Integer getId()                          { return id; }
    public void setId(Integer id)                   { this.id = id; }
    public Integer getFarmaciaId()                  { return farmaciaId; }
    public void setFarmaciaId(Integer farmaciaId)   { this.farmaciaId = farmaciaId; }
    public String getNome()                         { return nome; }
    public void setNome(String nome)                { this.nome = nome; }
    public String getCrf()                          { return crf; }
    public void setCrf(String crf)                  { this.crf = crf; }
    public boolean isEstaDeServico()                { return estaDeServico; }
    public void setEstaDeServico(boolean b)         { this.estaDeServico = b; }

    // método 1 — entrar de serviço
    public String entrarDeServico() {
        estaDeServico = true;
        return nome + " entrou de serviço";
    }

    // método 2 — sair de serviço
    public String sairDeServico() {
        estaDeServico = false;
        return nome + " saiu de serviço";
    }

    // método 3 — gerar relatório
    public String gerarRelatorio() {
        return "Farmacêutico: " + nome + " | CRF: " + crf +
               " | Status: " + (estaDeServico ? "de serviço" : "fora de serviço");
    }
}
