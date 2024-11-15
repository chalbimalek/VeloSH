package com.shirazmsalmi.runbackend.Controller;


import com.shirazmsalmi.runbackend.Entity.Defi;
import com.shirazmsalmi.runbackend.ServiceImp.DefiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/carpooling")
public class DefiController {

    private final DefiService defiService;


    @GetMapping("/{pid}")
    public Defi getCarpolingById (@PathVariable int pid) {
    return defiService.getCarpolingById(pid);
    }
    @GetMapping("/getall")
    public List<Defi> getAllCarpooling () {
        return defiService.getAllCarpooling();
    }

    @PreAuthorize("hasRole('ROLE_MEMBRE')")
    @PostMapping("/adddd")
    public Defi addCarpooling(@RequestBody Defi defi) {
        return defiService.saveCarpooling(defi);
    }
    @GetMapping("/accepted-users")
    public Set<String> getAcceptedUsersForDefi() {
       return defiService.getDefiUsers();
    }
    @PreAuthorize("hasRole('ROLE_MEMBRE')")

    @GetMapping("/is-winning")
    public boolean isWinning(@RequestParam String name) {
        return defiService.Winning(name);
    }


    @PostMapping("/participer")
    public ResponseEntity<String> ParticiperDefi(@RequestParam Long id) {
        try {
            defiService.ParticiperDefi(id);
            return ResponseEntity.ok("Réservation effectuée avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }




    @PreAuthorize("hasRole('ROLE_MEMBRE')")

    @GetMapping("/gain-carpooling")
    public int calculateCarpoolingGain() {
        return defiService.nbrdecovoiturage();
    }

    @GetMapping("/calculate-points")
    public ResponseEntity<Integer> calculatePoints() {
        int points = defiService.calculatePoints();
        return ResponseEntity.ok(points);
    }
    }
