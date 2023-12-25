package com.vvvital.psychologistsmp.web;

import com.vvvital.psychologistsmp.dto.PsychologistCardDTO;
import com.vvvital.psychologistsmp.dto.PsychologistResponseDTO;
import com.vvvital.psychologistsmp.dto.UserDTOMapper;
import com.vvvital.psychologistsmp.model.Categories;
import com.vvvital.psychologistsmp.model.Location;
import com.vvvital.psychologistsmp.model.PsychologistCard;
import com.vvvital.psychologistsmp.model.User;
import com.vvvital.psychologistsmp.service.PsychologistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/psychologist")
@Tag(name = "User")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class PsychologistController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final PsychologistService psychologistService;
    private final UserDTOMapper userDTOMapper;

    public PsychologistController(PsychologistService psychologistService, UserDTOMapper userDTOMapper) {
        this.psychologistService = psychologistService;
        this.userDTOMapper = userDTOMapper;
    }

    @PostMapping("/save/{id}")
    @Operation(summary = "To become a psychologist",
            description = "User becomes a psychologist by id and add a psychologist card.")
    public ResponseEntity<?> becomePsychologist(@Parameter(description = "User's id") @PathVariable Long id,
                                                @RequestBody PsychologistCardDTO card, Principal principal) {
        try {
            return ResponseEntity.ok(psychologistService.becomePsychologist(id, card, principal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all psychologists")
    public ResponseEntity<List<PsychologistResponseDTO>> findAllPsychologist(
            @RequestParam(required = false, defaultValue = "ALL") String location,
            @RequestParam(required = false, defaultValue = "0") Integer priceMin,
            @RequestParam(required = false, defaultValue = "99999") Integer priceMax,
            @RequestParam(required = false, defaultValue = "0") Integer ratingMin,
            @RequestParam(required = false, defaultValue = "5") Integer ratingMax,
            @RequestParam(required = false, defaultValue = "0") Integer experienceMin,
            @RequestParam(required = false, defaultValue = "99") Integer experienceMax,
            @RequestParam(required = false) String[] categories,
            @RequestParam(required = false) String order
    ) {
        logger.info("************* find All psychologists priceMin={} priceMax={} ratingMin={} ratingMax={} *************", priceMin, priceMax, ratingMin, ratingMax);
        Set<Categories> categoriesSet = null;
        if (categories != null) {
            categoriesSet = Arrays.stream(categories).map(Categories::valueOf).collect(Collectors.toSet());
        }
        return ResponseEntity.ok(psychologistService.findAllPsych(Location.valueOf(location), priceMin, priceMax
                , ratingMin, ratingMax, experienceMin, experienceMax, categoriesSet, order));
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "Get psychologist by Id")
    public ResponseEntity<?> get(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(psychologistService.getById(id));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card",
            description = "Update ALL variables a psychologist card by specifying it's id. The response is psychologist card with price, rating, experience, description, photoLink and categories."
    )
    public ResponseEntity<?> updatePsychologistCard(@Parameter(description = "Psychologist's card id") @PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO, Principal principal) {
        try {
            return ResponseEntity.ok(psychologistService.updatePsychologistCard(id, cardDTO, principal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/psychologist-card")
    @Operation(summary = "Update psychologist card",
            description = "Update SOME variables a psychologist card by specifying it's id. The response is psychologist card with price, rating, experience, description, photoLink and categories.")
    public ResponseEntity<?> patchPsychologistCard(@Parameter(description = "Psychologist's card id") @PathVariable Long id, @RequestBody PsychologistCardDTO cardDTO, Principal principal) {
        try {
            return ResponseEntity.ok(psychologistService.patchPsychologistCard(id, cardDTO, principal));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

