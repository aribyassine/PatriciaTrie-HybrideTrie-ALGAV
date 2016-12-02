package projet.algav.trie;

import com.google.gson.JsonElement;

import java.util.List;

public interface Trie {

    /**
     * Fonction qui ajoute un mot dans le dictionnaire
     * @param mot Le mot à ajouter dans le dictionnaire

     */
    void ajouter(String mot);

    /**
     * Fonction de recherche d’un mot dans le dictionnaire
     * @param mot Le à rechercher
     * @return Un boolean qui indique si le mot passé en paramètre
     * figure dans le dictionnaire
     */
    boolean recherche(String mot);

    /**
     * Fonction qui compte les mots présents dans le dictionnaire
     * @return Le nombre de mots présents dans le dictionnaire
     */
    int comptageMots();

    /**
     * Fonction qui liste les mots du dictionnaire dans l’ordre alphabétique
     * @return La liste des mots dans l’ordre alphabétique
     */
    List<String> listeMots();

    /**
     * Fonction qui compte les références vers null
     * @return Le nombre de références vers null
     */
    int comptageNil();

    /**
     * Fonction qui calcule la hauteur de l’arbre
     * @return La hauteur de l’arbre
     */
    int hauteur();

    /**
     * Fonction qui calcule la profondeur moyenne des feuilles de l’arbre
     * @return La profondeur moyenne des feuilles
     */
    double profondeurMoyenne();

    /**
     * Fonction qui prend un mot A en argument et qui indique
     * de combien de mots du dictionnaire le mot A est préﬁxe.
     * @param mot Préﬁxe a chercher
     * @return Le nombre de mots préfixé par le mot passé en paramètre
     */
    int prefixe(String mot);

    /**
     * Fonction qui prend un mot en argument et qui
     * le supprime de l’arbre s’il y ﬁgure
     * @param mot Le mot à supprimer
     * @return Un boolean qui indique si le mot à été trouvé et supprimé
     */
    boolean supprimer(String mot);

    /****************************************************************************
     * Les fonctions nécessaire pour la sérialisation et l’affichage de l'arbre *
     ****************************************************************************/

    /**
     * Fonction qui calcule la largeur de l’arbre
     * @return La largeur de l’arbre
     */
    int largeur();

    /**
     * Fonction qui indique si le dictionnaire est vide ou non
     * @return Un boolean indique si le dictionnaire est vide
     */
    boolean estVide();

    /**
     * Fonction qui sérialise un arbre en un objet JSON qui est utilisé
     * par un JavaScript pour un affichage intégrale de l’arbre.
     * Affichage non interactif
     * @return Un JSON qui décrit l'arbre
     */
    JsonElement toNotCollapsibleJSON();

    /**
     * Fonction qui sérialise un arbre en un objet JSON qui est utilisé
     * par un JavaScript pour un affichage partiel de l’arbre.
     * Affichage interactif
     * @return Un JSON qui décrit l'arbre
     */
    JsonElement toCollapsibleJSON();

    /**
     * Fonction qui donne une représentation de l’arbre
     * sous forme d’une chaîne de caractères
     * @return une représentation de l’arbre
     */
    String toString();
}
