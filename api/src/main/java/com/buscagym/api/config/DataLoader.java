package com.buscagym.api.config;

import com.buscagym.api.model.Academia;
import com.buscagym.api.model.Plano;
import com.buscagym.api.repository.AcademiaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final AcademiaRepository repository;

    public DataLoader(AcademiaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        Academia a1 = new Academia();
        a1.setNome("Iron Fitness Club");
        a1.setCidade("São Paulo");
        a1.setBairro("Pinheiros");
        a1.setEndereco("Rua dos Pinheiros, 1200");
        a1.setNotaAvaliacao(4.9);
        
        List<Plano> planos1 = new ArrayList<>();
        Plano p1 = new Plano();
        p1.setNomePlano("Plano Black / Total");
        p1.setPrecoMensal(119.90);
        p1.setFidelidade("12 meses de adesão");
        p1.setBeneficios("Acesso a todas as unidades, área VIP e cadeiras de massagem");
        p1.setDestaque(true);
        p1.setAcademia(a1);

        Plano p2 = new Plano();
        p2.setNomePlano("Plano Smart / Básico");
        p2.setPrecoMensal(89.90);
        p2.setFidelidade("Sem fidelidade");
        p2.setBeneficios("Acesso ilimitado à unidade de matrícula");
        p2.setDestaque(false);
        p2.setAcademia(a1);

        planos1.add(p1);
        planos1.add(p2);
        a1.setPlanos(planos1);

        Academia a2 = new Academia();
        a2.setNome("BioEnergy Academia");
        a2.setCidade("São Paulo");
        a2.setBairro("Moema");
        a2.setEndereco("Av. Ibirapuera, 540");
        a2.setNotaAvaliacao(4.7);

        List<Plano> planos2 = new ArrayList<>();
        Plano p3 = new Plano();
        p3.setNomePlano("Plano Fit Livre");
        p3.setPrecoMensal(79.90);
        p3.setFidelidade("Mensalidade recorrente");
        p3.setBeneficios("Musculação completa e aulas coletivas");
        p3.setDestaque(false);
        p3.setAcademia(a2);

        planos2.add(p3);
        a2.setPlanos(planos2);

        repository.save(a1);
        repository.save(a2);
    }
}