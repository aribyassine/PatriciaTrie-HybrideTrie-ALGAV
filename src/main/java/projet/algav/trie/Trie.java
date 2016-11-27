package projet.algav.trie;

import com.google.gson.JsonElement;

import java.util.List;

/**
 * Created by Yassine on 27/11/2016.
 */
public interface Trie {
    boolean estVide();

    void ajouter(String mot);

    boolean supprimer(String mot);

    boolean recherche(String mot);

    int prefixe(String mot);

    int comptageNil();

    int hauteur();

    List<String> listeMots();

    int comptageMots();

    double profondeurMoyenne();

    JsonElement toNoCollapsibleJSON();

    JsonElement toCollapsibleJSON();
}
