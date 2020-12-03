package org.banque.metier;

import org.banque.entities.Client;
import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IBanqueMetier {
    public Compte consulterCompte(String codeCompte);
    public void verser(String codeCompte,double montant);
    public void retirer(String codeCompte,double montant);
    public void virement(String codeCompte1,String codeComte2,double montant);
    public Page<Operation> listeOperations(String codeCompte,int page,int size);


}
