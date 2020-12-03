package org.banque.web;

import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.banque.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class BanqueController {
    @Autowired
    private IBanqueMetier iBanqueMetier;
    @GetMapping("/operations")
    public String index(){
        return "comptes";
    }

    @GetMapping("/consulterCompte")
    public String consulter(Model model, String codeCompte){
       try {
           Compte cp=iBanqueMetier.consulterCompte(codeCompte);
           Page<Operation> pageOperation=iBanqueMetier.listeOperations(codeCompte,0,5);
           model.addAttribute("listeOperation",pageOperation.getContent());
           model.addAttribute("compte",cp);
       }catch (Exception e){
           model.addAttribute("exception",e);
       }
        return "comptes";
    }
    @PostMapping("/saveOperation")
    public String operation(Model model, String codeCompte, double montant, String typeOperation, String codeCompte2){
        try {
model.addAttribute("codeCompte",codeCompte);
            if(typeOperation.equals("Vers")){
                iBanqueMetier.verser(codeCompte,montant);
            }
            else  if(typeOperation.equals("Ret")){
                iBanqueMetier.retirer(codeCompte,montant);
            }
            if(typeOperation.equals("Vir")){
                iBanqueMetier.virement(codeCompte,codeCompte2,montant);
            }

        }catch (Exception e){
            model.addAttribute("exception",e);
        }
        return "redirect:/consulterCompte?codeCompte="+codeCompte;
    }
}
