package org.banque;

import org.banque.dao.ClientRepository;
import org.banque.dao.CompteRepository;
import org.banque.dao.OperationRepository;
import org.banque.entities.*;
import org.banque.metier.IBanqueMetier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@SpringBootApplication
public class BanqueApplication implements CommandLineRunner {
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CompteRepository compteRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private IBanqueMetier iBanqueMetier;

	public static void main(String[] args) {
		SpringApplication.run(BanqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Client c1=clientRepository.save(new Client("youssef","ucef.stg@gmail.com"));
		Client c2=clientRepository.save(new Client("Zakaria","zak.stg@gmail.com"));
		Client c3=clientRepository.save(new Client("Zakaria","zak.stg@gmail.com"));

		Compte cmp1=compteRepository.save(new CompteCourant("C1",new Date(),1000,c1,100));
		Compte cmp2=compteRepository.save(new CompteCourant("C2",new Date(),2000,c1,100));
		Compte cmp3=compteRepository.save(new CompteCourant("C3",new Date(),3000,c2,100));
		Compte cmp4=compteRepository.save(new CompteEpargne("C4",new Date(),3000,c2,100));

		Operation o1=operationRepository.save(new Retrait(new Date(),100,cmp1));
		Operation o2=operationRepository.save(new Versement(new Date(),200,cmp3));
		Operation o3=operationRepository.save(new Retrait(new Date(),100,cmp1));



		Compte c=iBanqueMetier.consulterCompte("C2");
		System.out.println(c.getSolde());
		iBanqueMetier.verser("C1",100);
		iBanqueMetier.retirer("C1",100);
		iBanqueMetier.virement("C1","C2",200);








	}
}
