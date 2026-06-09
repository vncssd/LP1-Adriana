package org.example.model.farmacia;

import java.util.LinkedList;
import java.util.List;

public class FarmaciaModel {

    private Integer id;
    private boolean estaAberta;
    private List<MedicamentoModel> medicamentos = new LinkedList<>();

    public FarmaciaModel() {}

    public Integer getId()                              { return id; }
    public void setId(Integer id)                       { this.id = id; }
    public boolean isEstaAberta()                       { return estaAberta; }
    public void setEstaAberta(boolean estaAberta)       { this.estaAberta = estaAberta; }
    public List<MedicamentoModel> getMedicamentos()     { return medicamentos; }

    // método 1 — abrir
    public String abrir() {
        if (estaAberta) return "farmácia já está aberta!";
        estaAberta = true;
        return "farmácia aberta";
    }

    // método 2 — fechar
    public String fechar() {
        if (!estaAberta) return "farmácia já está fechada!";
        estaAberta = false;
        return "farmácia fechada";
    }

    // método 3 — verificar estoque crítico (quantidade <= limiar)
    public List<MedicamentoModel> verificarEstoqueCritico(int limiar) {
        List<MedicamentoModel> criticos = new LinkedList<>();
        for (MedicamentoModel m : medicamentos) {
            if (m.getQuantidadeEmEstoque() <= limiar) criticos.add(m);
        }
        return criticos;
    }

    public void adicionarMedicamento(MedicamentoModel m) { medicamentos.add(m); }
}
