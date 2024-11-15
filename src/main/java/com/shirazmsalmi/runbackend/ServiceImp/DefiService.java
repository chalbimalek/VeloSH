package com.shirazmsalmi.runbackend.ServiceImp;

import com.shirazmsalmi.runbackend.Entity.Defi;
import com.shirazmsalmi.runbackend.Entity.User;
import com.shirazmsalmi.runbackend.Repository.DefiRepository;
import com.shirazmsalmi.runbackend.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefiService {

    private final DefiRepository defiRepository;
    private final UserRepo userDao;


    private static final int POINTS_PER_CARPPOOLING = 10;

    @Transactional
    public Defi saveCarpooling(Defi Defi) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.findByUsername(username).orElse(null);
        if (user == null) {
            System.out.println("L'utilisateur avec le nom d'utilisateur " + username + " n'a pas été trouvé.");
            return null;
        }

        Defi defi = defiRepository.save(Defi);
        defi.setUser(user);
        defi.setStatus(false);
        return defi;
    }


    public Defi getCarpolingById(long id) {

        return defiRepository.findById(id).orElse(null);
    }

    public List<Defi> getAllCarpooling() {

        return defiRepository.getDefi();
    }





    public int nbrdecovoiturage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User annoncer = userDao.findByUsername(username).orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        Set<Defi> carpoolings = annoncer.getAnnonceDefiSet();
        int gain = 0;
        for (Defi carpooling : carpoolings) {
            if (carpooling.getStatus())
                gain++;
        }
        return gain;

    }

    @Transactional
    public int calculatePoints() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.findByUsername(username).orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        Set<Defi> defis = user.getAnnonceDefiSet();
        int som = 0;

        for (Defi defi : defis) {

            // Compter le nombre de covoiturages effectués en semaine

            if (defi.getStatus()) {
                som++;
                user.setPoints(som * POINTS_PER_CARPPOOLING);
            }
        }


        return user.getPoints();
    }




    public Set<String> getDefiUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.findByUsername(username).orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        Set<User> UserAcceptee = new HashSet<>();
        Set<String> usernames = new HashSet<>();

        List<Defi> defis = defiRepository.getDefi();
        for (Defi defi : defis) {
            UserAcceptee = defi.getUtilisateursAcceptes();
            //    System.out.println("defis name :"+defi.getName());

            if (defi.getUser().equals(user)) {
                for (User user1 : UserAcceptee) {
                    usernames.add((user1.getUsername()));
                }
            }

        }
        return usernames;
    }

    @Transactional
    public boolean Winning(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));

        Set<User> UserAcceptee = new HashSet<>();
        List<Defi> defis = defiRepository.findAll();
        for (Defi defi : defis) {
            UserAcceptee = defi.getUtilisateursAcceptes();

        }

        Set<String> usernames = getDefiUsers();
        boolean b = false;


        for (String namee : usernames) {
            if (namee.equals(name)) {
                b = true;
                for (User user2 : UserAcceptee) {
                    if (user2.getUsername().equals(namee))
                        user2.setPoints(user2.getPoints() + POINTS_PER_CARPPOOLING);
                    for (Defi d : defis) {
                        if (d.getUser().equals(user)) {
                            d.setStatus(true);
                        }
                    }
                }
            }


        }

        return b;

    }


    @Transactional
    public void ParticiperDefi(long Id) {

        // Récupérer l'utilisateur actuellement authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User chercheur = userDao.findByUsername(username).orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));

        // Récupérer le covoiturage par son identifiant
        Defi defi = defiRepository.findById(Id).orElseThrow(() -> new IllegalArgumentException("Covoiturage non trouvé"));

        // Vérifier si l'utilisateur ne peut pas réserver son propre covoiturage
        if (chercheur.equals(defi.getUser())) {
            throw new IllegalStateException("Vous ne pouvez pas réserver votre propre defi");
        }

        // Vérifier si l'utilisateur a déjà réservé ce covoiturage
        Set<User> chercheurs = defi.getUtilisateursAcceptes();
        if (chercheurs.stream().anyMatch(u -> u.getId().equals(chercheur.getId()))) {
            throw new IllegalStateException("Vous avez déjà réservé ce defi");
        }


        //chercheurs.add(chercheur);
      //  carpooling.setChercheurs(chercheurs);
        if (defi.getNbrPlaceDisponible() > 0) {
            // Accepter la réservation pour l'utilisateur spécifié
            defi.setNbrPlaceDisponible(defi.getNbrPlaceDisponible() - 1);
            defi.getUtilisateursAcceptes().add(chercheur);
            if (defi.getNbrPlaceDisponible() == 0) {
                defi.setStatus(true);// Mettre à jour la liste des chercheurs
                defiRepository.save(defi);

            }

        }

    }

}
