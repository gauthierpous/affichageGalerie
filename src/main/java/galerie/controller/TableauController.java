package galerie.controller;

import galerie.dao.ArtisteRepository;
import galerie.dao.TableauRepository;
import galerie.entity.Tableau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Édition des tableaux, sans gestion des erreurs
 */
@Controller
@RequestMapping(path = "/tableau")
public class TableauController {

    @Autowired
    private TableauRepository tableauDAO;
    @Autowired
    private ArtisteRepository artisteDAO;

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
     * @param model pour transmettre les informations à la vue
     * @return le nom de la vue à afficher ('formulaireTableau.html')
     */
    @GetMapping(path = "add")
    public String montreLeFormulairePourAjout(@ModelAttribute("tableau") Tableau tableau, Model model){
        model.addAttribute("auteurs", artisteDAO.findDistinctAllAuteur());
        return "formulaireTableau";
    }


    /**
     * Appelé par 'formulaireTableau.html', méthode POST
     *
     * @param tableau Un tableau initialisée avec les valeurs saisies dans le formulaire
     * @param redirectInfo pour transmettre des paramètres lors de la redirection
     * @return une redirection vers l'affichage de la liste des tableaux
     */
    @PostMapping(path = "save")
    public String ajouteLeTableauPuisMontreLaListe(Tableau tableau, RedirectAttributes redirectInfo){
        String message;
        try {
            tableauDAO.save(tableau);
            message = "Le tableau " + tableau.getTitre() + " a été correctement enregistré";
        } catch (DataIntegrityViolationException e){
            message = "L'éxécution a échouée, veuillez recommencer";
        }
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show";
    }

    /**
     * Appelé par le lien 'Supprimer' dans 'afficheTableaux.html'
     *
     * @param tableau à partir de l'id du tableau transmis en paramètre, Spring fera une requête SQL SELECT pour
     * chercher la galerie dans la base
     * @param redirectInfo pour transmettre des paramètres lors de la redirection
     * @return une redirection vers l'affichage de la liste des tableaux
     */
    @GetMapping(path = "delete")
    public String supprimeUnTableauPuisMontreLaListe(@RequestParam("id") Tableau tableau, RedirectAttributes redirectInfo){
        String message = "Le tableau " + tableau.getTitre() + " a bien été supprimé";
        try {
            tableauDAO.delete(tableau);
        } catch (DataIntegrityViolationException e){
            message = "Impossible de supprimer le tableau, veuillez réessayer";
        }
        redirectInfo.addFlashAttribute("message", message);
        return "redirect:show";
    }
}


