package org.banque.metier;

import org.banque.dao.CompteRepository;
import org.banque.dao.OperationRepository;
import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.banque.entities.Retrait;
import org.banque.entities.Versement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class IBanqueMetierImpl implements IBanqueMetier {
    @Autowired
    private CompteRepository compteRepository;
    @Autowired
    private OperationRepository operationRepository;
    @Override
    public Compte consulterCompte(String codeCompte)  {
         Optional <Compte> cp=compteRepository.findById(codeCompte);
           try {
               if(cp.isPresent()){
                   return   cp.get();
               }
           }catch (RuntimeException e){
           }
            return null;

    }

    @Override
    public void verser(String codeCompte, double montant) {
        Compte cp=consulterCompte(codeCompte);
        Versement v= new Versement(new Date(),montant,cp);
        operationRepository.save(v);
        cp.setSolde(cp.getSolde()+montant);
        compteRepository.save(cp);
    }

    @Override
    public void retirer(String codeCompte, double montant) {
        Compte cp=consulterCompte(codeCompte);
        Retrait r= new Retrait(new Date(),montant,cp);
        operationRepository.save(r);
        cp.setSolde(cp.getSolde()-montant);
        compteRepository.save(cp);
    }

    @Override
    public void virement(String codeCompte1, String codeComte2, double montant) {
        retirer(codeCompte1,montant);
        verser(codeComte2,montant);

    }

    @Override
    public Page<Operation> listeOperations(String codeCompte, int page, int size) {
        return operationRepository.listOperations(codeCompte,PageRequest.of(page,size));
    }
}
