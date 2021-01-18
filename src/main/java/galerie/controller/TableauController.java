package galerie.controller;

import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Édition des tableaux, sans gestion des erreurs
 */
@Controller
@RequestMapping(path = "/tableau")
public class TableauController {

    @Autowired
    private TableauRepository tableauDAO;

    /**
     * Affiche tous les tableaux de la base
     *
     * @param model pour transmettre les informations à la vue
     * @return le nom de la vue à afficher ('afficheTableaux.html')
     */
    @GetMapping(path = "show")
    public String afficheTousLesTableaux(Model model){
        model.addAttribute("tableaux", tableauDAO.findAll());
        return "afficheTableaux";
    }

    /**
     * Montre le formulaire permettant d'ajouter un tableau
     *
     * @param tableau initialisé par Spring, valeurs par défaut à afficher dans le formulaire
     * @return le nom de la vue à afficher ('formulaireTableau.html')
     */
    @GetMapping(path = "add")
    public String montreLeFormulairePourAjout(@ModelAttribute("tableau") Tableau tableau){
        return "formulaireTableau";
    }
}
