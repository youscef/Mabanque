package org.banque.web;

import org.banque.entities.Compte;
import org.banque.entities.Operation;
import org.banque.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
