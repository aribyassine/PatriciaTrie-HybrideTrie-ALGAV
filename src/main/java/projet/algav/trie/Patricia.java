package projet.algav.trie;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * <b>ALGAV Project</b><br/>
 * Patricia Trie
 *
 * @author <a href="mailto:aribyassine@gmail.com">Yassine ARIB</a>
 * @author <a href="mailto:meghari.aghiles@gmail.com">Aghiles MEGHARI</a>
 * @version 1.0
 * @see <a href="https://github.com/aribyassine/projet-ALGAV">Code source sur GitHub</a>
 */

public class Patricia implements Trie {
    /**
     * caractère utilisé pour indiquer la ﬁn d’un mot.
     */
    final static String finMot = "Ø";
    /**
     * HashMap utilisé pour la structure de l'arbre
     * Les clés sont des chaînes de caractère qui indexe les sous arbres
     * on a fait le choix d'utiliser un Map car c’est une structure
     * particulièrement adapté pour représenter le noeud d’un Patricia-Trie,
     * cela dit notre implémentation ne respecte pas entièrement
     * la description vu en TD.
     */
    final HashMap<String, Patricia> node;
    /**
     * référence vers l’arbre parent,
     * l’attribut est notamment utilisé pour l’affichage mais pas que.
     */
    Patricia pere;

    /**
     * Constructeur qui initialise la racine de l’arbre
     */
    public Patricia() {
        this.node = new HashMap<>();
    }

    /**
     * Fonction qui ajoute un mot dans le Patricia-trie<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp; On parcourt les valeurs du noeud, et pour chacun on regarde si il y’a un préfixe commun
     * entre le mot à ajouter et la valeur du noeud si c’est le cas on on continue récursivement
     * avec le mot à ajouter auquel on a enlevé le préfixe <code>mot.substring(longueur_prefixe)</code>,
     * sinon on ajoute le mot au noeud et on rajoute le caractère de fin de mot sur le sous arbre correspondant
     * </p>
     *
     * @param mot Le mot à ajouter dans le Patricia-trie
     */
    @Override
    public void ajouter(String mot) {
        if (!mot.isEmpty()) {
            //cas terminal on ajoute le mot vide au noeud
            if (this.estVide()) {
                Patricia pt = new Patricia();
                node.put(mot, pt);
                pt.node.put(finMot, new Patricia());
                pt.setPere(this);
            } else {
                Patricia tmp;
                int lp = 0;
                // pour chaque mot du noeud
                for (String valeurNoeud : this.node.keySet()) {
                    //on cherche la longueur du plus grand prefixe commun entre le mot a ajouter
                    lp = longueurPlusGrandPrefixeCommun(valeurNoeud, mot);
                    if (lp != 0) {
                        //si on trouve un préfixe on ajoute le préfixe au noeud et on continue avec un appel récursif avec le reste du mot
                        String prefixe = prefixe(valeurNoeud, mot);
                        if (!(lp == valeurNoeud.length())) {
                            tmp = node.get(valeurNoeud);
                            node.remove(valeurNoeud);
                            Patricia pt = new Patricia(valeurNoeud.substring(lp), tmp);
                            node.put(prefixe, pt);
                            pt.setPere(this);
                            tmp.setPere(pt);
                        }
                        if (!(lp == mot.length()))
                            node.get(prefixe).ajouter(mot.substring(lp));
                        else
                            node.get(prefixe).node.put(finMot, new Patricia());
                        break;
                    }
                }
                if (lp == 0) {
                    Patricia pt = new Patricia();
                    node.put(mot, pt);
                    pt.node.put(finMot, new Patricia());
                    pt.setPere(this);
                }
            }
        }
    }

    /**
     * Fonction de recherche d’un mot dans le Patricia-trie<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;Cas terminal : Si le mot recherché est vide et que le noeud contient le caractère de fin de mot
     * on renvoie <code>true</code><br/>
     * &emsp;Cas générale : Si parmis les valeurs du noeud aucun n’est est préfixe du mot
     * recherché on renvoie <code>false</code> sinon on continue la recherche récursivement dans le sous arbre indexé
     * par le préfixe avec comme paramètre de recherche <code>mot.substring(longueur_prefixe)</code>
     * </p>
     *
     * @param mot Le mot à rechercher
     * @return Un boolean qui indique si le mot passé en paramètre figure dans le Patricia-trie
     */
    @Override
    public boolean recherche(String mot) {
        if (mot.isEmpty() && (this.hasFinMot()))
            return true;
        for (String s : this.node.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length()) {
                return node.get(s).recherche(mot.substring(lp));
            }
        }
        return false;
    }

    /**
     * Fonction qui compte les mots présents dans le Patricia-trie<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;On parcourt récursivement tous les noeuds de l’arbre, puis à chaque noeud contenant le caractère de fin de
     * mot, on incrémente le compteur.
     * <p>
     *
     * @return Le nombre de mots présents dans le Patricia-trie
     */
    @Override
    public int comptageMots() {
        int nbMot = 0;
        if (node.keySet().contains(finMot))
            nbMot++;
        for (Patricia pt : node.values())
            nbMot += pt.comptageMots();
        return nbMot;
    }

    /**
     * Fonction qui liste les mots du Patricia-trie dans l’ordre alphabétique<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;On parcourt les valeurs du noeud, et pour chacun on appelle la fonction <code>listeMotsRecursive()</code>
     * qui récupère récursivement la liste des mots dans le sous arbre correspondant puis on concatène
     * la valeur du noeud a la liste  des suffixe puis on retourne la liste triée par ordre alphabétique
     * <p>
     *
     * @return La liste des mots dans l’ordre alphabétique
     */
    @Override
    public List<String> listeMots() {
        ArrayList<String> mots = new ArrayList<>();
        for (StringBuilder sb : this.listeMotsRecursive()) {
            mots.add(sb.toString());
        }
        Collections.sort(mots);
        return mots;
    }

    /**
     * Fonction qui compte les références vers null<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;Le nombre références vers null est toujour égale à 1 dans notre implémentation cela est dû au fait
     * qu'on utilise un HashMap pour stocker les valeurs du noeud d'un Patricia-trie, l'unique valeur null provient
     * de l'attribut <code>pere</code> de la racine de l'arbre
     * <p>
     *
     * @return Le nombre de références vers null
     */
    @Override
    public int comptageNil() {
        return 1;
    }

    /**
     * Fonction qui calcule la hauteur du Patricia-trie<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;Cas terminal: On renvoie 0 Si le noeud est vide<br/>
     * &emsp;Cas générale: On renvoie 1 + le maximum parmis les tailles des sous arbres
     * <p>
     *
     * @return La hauteur de l’arbre
     */
    @Override
    public int hauteur() {
        int hMax = 0;
        if (this.node.isEmpty())
            return 0;
        for (Patricia pt : node.values()) {
            int hFils = pt.hauteur();
            if (hFils > hMax)
                hMax = hFils;
        }
        return hMax + 1;
    }

    /**
     * Fonction qui calcule la profondeur moyenne des feuilles du Patricia-trie<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;On récupère récursivement la liste de toutes les feuilles puis on utilise la fonction
     * <code>longueurDeLaBranche()</code> qui calcule la longueur de la branche menant jusqu'à la feuille,
     * On calcule ainsi la somme de toutes les branche qu'on divise par le nombre de feuilles
     * et on obtient la profondeur Moyenne des feuilles<br/>
     * <small>La fonction <code>longueurDeLaBranche()</code> n’est pas récursive elle utilise
     * l'attribut <code>pere</code> pour remonter dans la hiérarchie </small>
     * <p>
     *
     * @return La profondeur moyenne des feuilles
     */
    @Override
    public double profondeurMoyenne() {
        int sommeProfondeur = 0;
        ArrayList<Patricia> feuilles = getFeuilles();
        for (Patricia pt : feuilles)
            sommeProfondeur += pt.longueurDeLaBranche();
        return sommeProfondeur / (double) feuilles.size();
    }

    /**
     * Fonction qui prend un mot A en argument et qui indique
     * de combien de mots du Patricia-trie le mot A est préﬁxe<br/>
     * <p>
     * ->Principe :<br/>
     * &emsp;Cas terminal: Si le mot est vide on renvoie le compte des mots présents dans les sous arbre
     * grace à la fonction <code>comptageMots()</code><br/>
     * &emsp;Cas générale: On renvoie 1 + le maximum parmis les tailles des sous arbres
     * <p>
     * @param mot Préﬁxe a chercher
     * @return Le nombre de mots préfixé par le mot passé en paramètre
     */
    @Override
    public int prefixe(String mot) {
        if (mot.isEmpty())
            return this.comptageMots();
        for (String s : this.node.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && s.length() == lp)
                return node.get(s).prefixe(mot.substring(lp));
        }
        return 0;
    }

    /**
     * Fonction qui prend un mot en argument et qui
     * le supprime du Patricia-trie s’il y ﬁgure
     *
     * @param mot Le mot à supprimer
     * @return Un boolean qui indique si le mot à été trouvé et supprimé
     */
    @Override
    public boolean supprimer(String mot) {
        if (mot.isEmpty() && (this.hasFinMot())) {
            node.remove(finMot);
            return true;
        }
        for (String s : this.valeursRacine()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length())
                if (node.get(s).supprimer(mot.substring(lp))) {
                    Patricia sousArbre = node.get(s);
                    if (sousArbre.node.size() == 0) {
                        node.remove(s);
                        return true;
                    }
                    String cleFils = (String) sousArbre.node.keySet().toArray()[0];
                    if (sousArbre.node.size() == 1 && !cleFils.equals(finMot)) {
                        node.remove(s);
                        node.put(s + cleFils, sousArbre.node.get(cleFils));
                        sousArbre.node.get(cleFils).pere = this;
                        return true;
                    }
                    return false;
                }
        }
        return false;
    }

    /**********************************************************************************
     * Les fonctions nécessaire pour la sérialisation et l’affichage du Patricia Trie *
     **********************************************************************************/

    /**
     * Fonction qui calcule la largeur du Patricia-trie
     *
     * @return La largeur de du Patricia-trie
     */
    @Override
    public int largeur() {
        return comptageMots();
    }

    /**
     * Fonction qui indique si le Patricia-trie est vide ou non
     *
     * @return Un boolean indique si le Patricia-trie est vide
     */
    @Override
    public boolean estVide() {
        return this.node.isEmpty();
    }

    /**
     * Fonction qui sérialise un arbre en un objet JSON qui est utilisé
     * par un JavaScript pour un affichage intégrale du Patricia-trie.
     * Affichage non interactif
     *
     * @return Un JSON qui décrit le Patricia-trie
     */
    @Override
    public JsonElement toNotCollapsibleJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerializer<Patricia> serializer = new JsonSerializer<Patricia>() {
            public JsonElement serialize(Patricia patricia, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                if (pere != null) {
                    jsonObject.addProperty("name", maCleDansPere());
                    jsonObject.addProperty("parent", (pere.maCleDansPere() != null ? pere.maCleDansPere() : ""));
                } else {
                    jsonObject.addProperty("name", "");
                    jsonObject.addProperty("parent", "null");
                }
                JsonArray children = new JsonArray();
                for (String s : patricia.valeursRacine())
                    if (!s.equals(finMot))
                        children.add(patricia.node.get(s).toNotCollapsibleJSON());
                    else {
                        JsonObject tmp = new JsonObject();
                        tmp.addProperty("name", finMot);
                        tmp.addProperty("parent", pere != null ? (pere.maCleDansPere() != null ? pere.maCleDansPere() : "null") : "null");
                        children.add(tmp);
                    }
                jsonObject.add("children", children);
                if (pere != null)
                    return jsonObject;
                else {
                    JsonArray tmp = new JsonArray();
                    tmp.add(jsonObject);
                    return tmp;
                }
            }
        };
        gsonBuilder.registerTypeAdapter(Patricia.class, serializer);
        Gson customGson;
        customGson = gsonBuilder.create();
        return customGson.toJsonTree(this);
    }

    /**
     * Fonction qui sérialise un arbre en un objet JSON qui est utilisé
     * par un JavaScript pour un affichage partiel du Patricia-trie.
     * Affichage interactif
     *
     * @return Un JSON qui décrit le Patricia-trie
     */
    @Override
    public JsonElement toCollapsibleJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerializer<Patricia> serializer = new JsonSerializer<Patricia>() {
            public JsonElement serialize(Patricia patricia, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                if (pere != null)
                    jsonObject.addProperty("name", maCleDansPere());
                else
                    jsonObject.addProperty("name", "");
                JsonArray children = new JsonArray();
                for (String s : patricia.valeursRacine())
                    if (!s.equals(finMot))
                        children.add(patricia.node.get(s).toCollapsibleJSON());
                    else {
                        JsonObject tmp = new JsonObject();
                        tmp.addProperty("name", finMot);
                        children.add(tmp);
                    }
                jsonObject.add("children", children);
                return jsonObject;
            }
        };
        gsonBuilder.registerTypeAdapter(Patricia.class, serializer);
        Gson customGson = gsonBuilder.create();
        return customGson.toJsonTree(this);
    }

    /**
     * Fonction qui donne une représentation du Patricia-trie
     * sous forme d’une chaîne de caractères
     *
     * @return une représentation du Patricia-trie
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.valeursRacine()) {
            for (int i = 0; i < this.longueurDeLaBranche(); i++) {
                sb.append("|\t");
            }
            sb.append(s).append("\n").append(this.node.get(s).toString());
        }
        return sb.toString();
    }
    /*****************************************************************************
     * Définitions des fonctions publique propre au Patricia-Trie                *
     *****************************************************************************/

    /**
     * Fonction qui prend un Patricia-trie en argument et qui
     * le fusionne avec this
     *
     * @param pt Le Patricia-trie à fusionner
     * @return le resultat de la fusion
     */
    public Patricia fusion(Patricia pt) {
        if (!pt.estVide()) {
            for (int i = 0; i < this.node.keySet().size(); i++) {
                String s = this.valeursRacine().get(i);
                String prefixe = prefixCommun(s, pt);
                if (prefixe != null)
                    //prefixe complet dans les deux arbres
                    if (this.node.containsKey(prefixe) && pt.node.containsKey(prefixe)) {
                        for (String val : pt.node.keySet())
                            if (val.equals(prefixe)) {
                                node.get(prefixe).fusion(pt.node.get(prefixe));
                            }
                    }
                    // prefixe complet dans this et prefixe partiel dans pt
                    else if (this.node.containsKey(prefixe)) {
                        for (String val : pt.node.keySet())
                            if (val.contains(prefixe)) {
                                Patricia tmp = new Patricia();
                                tmp.ajouterCleValeur(val.substring(prefixe.length()), pt.node.get(val));
                                this.node.get(prefixe).fusion(tmp);
                            }
                    }
                    // prefixe complet dans pt et prefixe partiel dans this
                    else if (pt.node.containsKey(prefixe)) {
                        for (String val : pt.node.keySet())
                            if (val.equals(prefixe)) {
                                Patricia inter = new Patricia();
                                Patricia tmp = this.node.get(s);
                                this.ajouterCleValeur(prefixe, inter);
                                inter.ajouterCleValeur(s.substring(prefixe.length()), tmp);
                                this.node.remove(s);
                                inter.fusion(pt.node.get(prefixe));
                            }
                    }
                    //prefixe partiel dans les deux arbres
                    else {
                        Patricia inter = new Patricia();
                        Patricia tmp = this.node.get(s);
                        this.ajouterCleValeur(prefixe, inter);
                        inter.ajouterCleValeur(s.substring(prefixe.length()), tmp);
                        this.node.remove(s);
                        for (String val : pt.node.keySet())
                            if (val.contains(prefixe))
                                inter.ajouterCleValeur(val.substring(prefixe.length()), pt.node.get(val));
                    }
            }
            // pas de prefixe commun
            for (String val : pt.node.keySet())
                if (prefixCommun(val, this) == null) {
                    this.ajouterCleValeur(val, pt.node.get(val));
                }
        }

        return this;
    }

    public List<String> motsAyantCommePrefixe(String mot) {
        List<String> suffixes = listeSuffixe(mot);
        List<String> res = new ArrayList<>();
        for (String suffixe : suffixes) {
            res.add(mot + suffixe);
        }
        return res;
    }

    /*****************************************************************************
     * Définitions des fonctions privées                                         *
     *****************************************************************************/

    private List<String> listeSuffixe(String mot) {
        if (mot.isEmpty()) {
            return this.listeMots();
        }
        for (String s : this.node.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0) {
                if (mot.length() == lp && s.length() > lp)
                    return ajouterPrefixe(s.substring(lp), node.get(s).listeMots());
                return node.get(s).listeSuffixe(mot.substring(lp));
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<StringBuilder> listeMotsRecursive() {
        ArrayList<StringBuilder> res = new ArrayList<>();
        for (String s : node.keySet()) {
            //si feille on ajoute un mot vide
            if (s.equals(finMot))
                res.add(new StringBuilder());
            else {
                ArrayList<StringBuilder> suffixes = node.get(s).listeMotsRecursive();
                // on concat le mot courant et les suffixes et on ajoute a la liste
                for (StringBuilder suffixe : suffixes) {
                    res.add(new StringBuilder(s).append(suffixe));
                }
            }
        }
        return res;
    }

    private int longueurDeLaBranche() {
        int lvl = 0;
        Patricia pere = this.pere;
        while (pere != null) {
            pere = pere.getPere();
            lvl++;
        }
        return lvl;
    }

    private Patricia getPere() {
        return pere;
    }

    private void setPere(Patricia pere) {
        this.pere = pere;
    }

    private boolean hasFinMot() {
        for (String s : node.keySet()) {
            if (s.equals(finMot))
                return true;
        }
        return false;
    }

    private ArrayList<Patricia> getFeuilles() {
        ArrayList<Patricia> alpt = new ArrayList<>();
        if (node.keySet().contains(finMot))
            alpt.add(this);
        for (Patricia pt : node.values())
            alpt.addAll(pt.getFeuilles());
        return alpt;
    }

    private void ajouterCleValeur(String cle, Patricia valeur) {
        valeur.pere = this;
        this.node.put(cle, valeur);
    }

    private static String prefixCommun(String s, Patricia p) {
        for (String ps : p.node.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, ps);
            if (lp != 0)
                return s.substring(0, lp);
        }
        return null;
    }

    public Hybride toHybride() {
        List<String> node = valeursRacine();
        Hybride res = ConversionTools.patriciaNodeToHybride(node);
        if (res == null)
            return null;
        Hybride sousArbre;
        for (String val : node) {
            sousArbre = ConversionTools.getLastHybrideNode(res, val);
            if (this.node.get(val).hasFinMot()) {
                sousArbre.position = Hybride.cpt++;
                if (this.node.get(val).node.size() == 1)
                    continue;
            }
            sousArbre.eq = this.node.get(val).toHybride();
            sousArbre.setPereDansFils();
        }
        return res;
    }

    private List<String> ajouterPrefixe(String mot, List<String> list) {
        List<String> res = new ArrayList<>();
        for (String suffixe : list) {
            res.add(mot + suffixe);
        }
        return res;
    }

    private static int longueurPlusGrandPrefixeCommun(String a, String b) {
        int len = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); ++i) {
            if (a.charAt(i) != b.charAt(i))
                break;
            ++len;
        }
        return len;
    }

    private Patricia(String mot, Patricia node) {
        this.node = new HashMap<>();
        this.node.put(mot, node);
    }

    private List<String> valeursRacine() {
        List<String> values = new ArrayList<>(this.node.keySet());
        Collections.sort(values);
        return values;
    }

    private Patricia getSousArbre(int i) {
        List<String> values = new ArrayList<>(this.node.keySet());
        Collections.sort(values);
        return this.node.get(values.get(i));
    }

    private static String prefixe(String a, String b) {
        return a.substring(0, longueurPlusGrandPrefixeCommun(a, b));
    }

    private String maCleDansPere() {
        if (pere != null && pere.node != null)
            for (String key : pere.node.keySet())
                if (pere.node.get(key) == this)
                    return key;
        return null;
    }
}
