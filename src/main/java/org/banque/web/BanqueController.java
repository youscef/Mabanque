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
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BanqueController {
    @Autowired
    private IBanqueMetier iBanqueMetier;
    @GetMapping("/operations")
    public String index(){
        return "comptes";
    }

    @GetMapping("/consulterCompte")
    public String consulter(Model model, String codeCompte, @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "size", defaultValue = "5")  int size){
       try {

           Compte cp=iBanqueMetier.consulterCompte(codeCompte);
           Page<Operation> pageOperation=iBanqueMetier.listeOperations(codeCompte,page,size);
           model.addAttribute("listeOperation",pageOperation.getContent());
           model.addAttribute("compte",cp);
           int [] pages=new int[pageOperation.getTotalPages()];
           model.addAttribute("pages",pages);
       }catch (Exception e){
           model.addAttribute("exception",e);
       }
        return "comptes";
    }
    @PostMapping("/saveOperation")
    public String operation(Model model, String codeCompte, double montant, String typeOperation, String codeCompte2){
        try {
            model.addAttribute("codeCompte",codeCompte);
            model.addAttribute("typeOperation",typeOperation);
            model.addAttribute("montant",montant);

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
        System.out.println(codeCompte);

        return "redirect:/consulterCompte?codeCompte="+codeCompte;
    }
}
