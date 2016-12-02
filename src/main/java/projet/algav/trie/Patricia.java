package projet.algav.trie;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 * <b>ALGAV Project</b>
 * Patricia Trie
 *
 * @author <a href="mailto:aribyassine@gmail.com">Yassine ARIB</a>
 * @author <a href="mailto:meghari.aghiles@gmail.com">Aghiles MEGHARI</a>
 *
 * @see <a href="https://github.com/aribyassine/projet-ALGAV">Code source</a>
 *
 * @version 1.0
 */

public class Patricia implements Trie {
    /**
     * caractère utilisé pour indiquer la ﬁn d’un mot.
     */
    final static String finMot = "∅";
    /**
     * HashMap utilisé pour la structure de l'arbre
     * Les clés sont des chaînes de caractère qui indexe les sous arbres
     * on a fait le choix d'utiliser un Map car c’est une structure
     * particulièrement adapté pour représenter le noeud d’un Patricia-Trie,
     * cela dit notre implémentation ne respecte pas entièrement.
     * la description vu en TD
     */
    final HashMap<String, Patricia> cle;
    /**
     * référence vers l’arbre parent,
     * l’attribut est notamment utilisé pour l’affichage mais pas que.
     */
    Patricia pere;

    /**
     * Constructeur qui initialise la racine de l’arbre
     */
    public Patricia() {
        this.cle = new HashMap<>();
    }

    /**
     * Fonction qui ajoute un mot dans le Patricia-trie
     *  Principe :
     *  -
     * @param mot Le mot à ajouter dans le Patricia-trie
     */
    @Override
    public void ajouter(String mot) {
        if (!mot.isEmpty()) {
            if (this.estVide()) {
                Patricia pt = new Patricia();
                cle.put(mot, pt);
                pt.cle.put(finMot, new Patricia());
                pt.setPere(this);
            } else {
                Patricia tmp;
                int lp = 0;
                for (String s : this.cle.keySet()) {
                    lp = longueurPlusGrandPrefixeCommun(s, mot);
                    if (lp != 0) {
                        String prefixe = prefixe(s, mot);
                        if (!(lp == s.length())) {
                            tmp = cle.get(s);
                            cle.remove(s);
                            Patricia pt = new Patricia(s.substring(lp), tmp);
                            cle.put(prefixe, pt);
                            pt.setPere(this);
                            tmp.setPere(pt);
                        }
                        if (!(lp == mot.length()))
                            cle.get(prefixe).ajouter(mot.substring(lp));
                        else
                            cle.get(prefixe).cle.put(finMot, new Patricia());
                        break;
                    }
                }
                if (lp == 0) {
                    Patricia pt = new Patricia();
                    cle.put(mot, pt);
                    pt.cle.put(finMot, new Patricia());
                    pt.setPere(this);
                }
            }
        }
    }

    /**
     * Fonction de recherche d’un mot dans le Patricia-trie
     * @param mot Le à rechercher
     * @return Un boolean qui indique si le mot passé en paramètre
     * figure dans le Patricia-trie
     */
    @Override
    public boolean recherche(String mot) {
        if (mot.isEmpty() && (this.hasFinMot()))
            return true;
        for (String s : this.cle.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length()) {
                return cle.get(s).recherche(mot.substring(lp));
            }
        }
        return false;
    }

    /**
     * Fonction qui compte les mots présents dans le Patricia-trie
     * @return Le nombre de mots présents dans le Patricia-trie
     */
    @Override
    public int comptageMots() {
        int nbMot = 0;
        if (cle.keySet().contains(finMot))
            nbMot++;
        for (Patricia pt : cle.values())
            nbMot += pt.comptageMots();
        return nbMot;
    }

    /**
     * Fonction qui liste les mots du Patricia-trie dans l’ordre alphabétique
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
     * Fonction qui compte les références vers null
     * @return Le nombre de références vers null
     */
    @Override
    public int comptageNil() {
        return comptageMots();
    }

    /**
     * Fonction qui calcule la hauteur du Patricia-trie
     * @return La hauteur de l’arbre
     */
    @Override
    public int hauteur() {
        int hMax = 0;
        if (this.cle.isEmpty())
            return 0;
        for (Patricia pt : cle.values()) {
            int hFils = pt.hauteur();
            if (hFils > hMax)
                hMax = hFils;
        }
        return hMax + 1;
    }

    /**
     * Fonction qui calcule la profondeur moyenne des feuilles du Patricia-trie
     * @return La profondeur moyenne des feuilles
     */
    @Override
    public double profondeurMoyenne() {
        int sommeProfondeur = 0;
        ArrayList<Patricia> feuilles = getFeuilles();
        for (Patricia pt : feuilles)
            sommeProfondeur += pt.longueurDeLabranche();
        return sommeProfondeur / (double) feuilles.size();
    }

    /**
     * Fonction qui prend un mot A en argument et qui indique
     * de combien de mots du Patricia-trie le mot A est préﬁxe.
     * @param mot Préﬁxe a chercher
     * @return Le nombre de mots préfixé par le mot passé en paramètre
     */
    @Override
    public int prefixe(String mot) {
        if (mot.isEmpty()) {
            return this.comptageMots();
        }
        for (String s : this.cle.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0) {
                return cle.get(s).prefixe(mot.substring(lp));
            }
        }
        return 0;
    }

    /**
     * Fonction qui prend un mot en argument et qui
     * le supprime du Patricia-trie s’il y ﬁgure
     * @param mot Le mot à supprimer
     * @return Un boolean qui indique si le mot à été trouvé et supprimé
     */
    @Override
    public boolean supprimer(String mot) {
        if (mot.isEmpty() && (this.hasFinMot())) {
            cle.remove(finMot);
            return true;
        }
        for (String s : this.valeursRacine()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length())
                if (cle.get(s).supprimer(mot.substring(lp))) {
                    Patricia sousArbre = cle.get(s);
                    if (sousArbre.cle.size() == 0) {
                        cle.remove(s);
                        return true;
                    }
                    String cleFils = (String) sousArbre.cle.keySet().toArray()[0];
                    if (sousArbre.cle.size() == 1 && !cleFils.equals(finMot)) {
                        cle.remove(s);
                        cle.put(s + cleFils, sousArbre.cle.get(cleFils));
                        sousArbre.cle.get(cleFils).pere = this;
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
     * @return La largeur de du Patricia-trie
     */
    @Override
    public int largeur() {
        return comptageMots();
    }

    /**
     * Fonction qui indique si le Patricia-trie est vide ou non
     * @return Un boolean indique si le Patricia-trie est vide
     */
    @Override
    public boolean estVide() {
        return this.cle.isEmpty();
    }

    /**
     * Fonction qui sérialise un arbre en un objet JSON qui est utilisé
     * par un JavaScript pour un affichage intégrale du Patricia-trie.
     * Affichage non interactif
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
                        children.add(patricia.cle.get(s).toNotCollapsibleJSON());
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
                        children.add(patricia.cle.get(s).toCollapsibleJSON());
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
     * @return une représentation du Patricia-trie
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.valeursRacine()) {
            for (int i = 0; i < this.longueurDeLabranche(); i++) {
                sb.append("|\t");
            }
            sb.append(s).append("\n").append(this.cle.get(s).toString());
        }
        return sb.toString();
    }
    /*****************************************************************************
     * Définitions des fonctions publique propre au Patricia-Trie                *
     *****************************************************************************/

    /**
     * Fonction qui prend un Patricia-trie en argument et qui
     * le fusionne avec this
     * @param pt Le Patricia-trie à fusionner
     * @return le resultat de la fusion
     */
    public Patricia fusion(Patricia pt) {
        if (!pt.estVide()) {
            for (int i = 0; i < this.cle.keySet().size(); i++) {
                String s = this.valeursRacine().get(i);
                String prefixe = prefixCommun(s, pt);
                if (prefixe != null)
                    //prefixe complet dans les deux arbres
                    if (this.cle.containsKey(prefixe) && pt.cle.containsKey(prefixe)) {
                        for (String val : pt.cle.keySet())
                            if (val.equals(prefixe)) {
                                cle.get(prefixe).fusion(pt.cle.get(prefixe));
                            }
                    }
                    // prefixe complet dans this et prefixe partiel dans pt
                    else if (this.cle.containsKey(prefixe)) {
                        for (String val : pt.cle.keySet())
                            if (val.contains(prefixe)) {
                                Patricia tmp = new Patricia();
                                tmp.ajouterCleValeur(val.substring(prefixe.length()), pt.cle.get(val));
                                this.cle.get(prefixe).fusion(tmp);
                            }
                    }
                    // prefixe complet dans pt et prefixe partiel dans this
                    else if (pt.cle.containsKey(prefixe)) {
                        for (String val : pt.cle.keySet())
                            if (val.equals(prefixe)) {
                                Patricia inter = new Patricia();
                                Patricia tmp = this.cle.get(s);
                                this.ajouterCleValeur(prefixe, inter);
                                inter.ajouterCleValeur(s.substring(prefixe.length()), tmp);
                                this.cle.remove(s);
                                inter.fusion(pt.cle.get(prefixe));
                            }
                    }
                    //prefixe partiel dans les deux arbres
                    else {
                        Patricia inter = new Patricia();
                        Patricia tmp = this.cle.get(s);
                        this.ajouterCleValeur(prefixe, inter);
                        inter.ajouterCleValeur(s.substring(prefixe.length()), tmp);
                        this.cle.remove(s);
                        for (String val : pt.cle.keySet())
                            if (val.contains(prefixe))
                                inter.ajouterCleValeur(val.substring(prefixe.length()), pt.cle.get(val));
                    }
            }
            // pas de prefixe commun
            for (String val : pt.cle.keySet())
                if (prefixCommun(val, this) == null) {
                    this.ajouterCleValeur(val, pt.cle.get(val));
                }
        }

        return this;
    }

    public List<String> MotsAyantLePrefixe(String mot) {
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
        for (String s : this.cle.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0) {
                if (mot.length() == lp && s.length() > lp)
                    return ajouterPrefixe(s.substring(lp), cle.get(s).listeMots());
                return cle.get(s).listeSuffixe(mot.substring(lp));
            }
        }
        return new ArrayList<>();
    }

    private ArrayList<StringBuilder> listeMotsRecursive() {
        ArrayList<StringBuilder> res = new ArrayList<>();
        for (String s : cle.keySet()) {
            //si feille on ajoute un mot vide
            if (s.equals(finMot))
                res.add(new StringBuilder());
            else {
                ArrayList<StringBuilder> suffixes = cle.get(s).listeMotsRecursive();
                // on concat le mot courant et les suffixes et on ajoute a la liste
                for (StringBuilder suffixe : suffixes) {
                    res.add(new StringBuilder(s).append(suffixe));
                }
            }
        }
        return res;
    }

    private int longueurDeLabranche() {
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
        for (String s : cle.keySet()) {
            if (s.equals(finMot))
                return true;
        }
        return false;
    }

    private ArrayList<Patricia> getFeuilles() {
        ArrayList<Patricia> alpt = new ArrayList<>();
        if (cle.keySet().contains(finMot))
            alpt.add(this);
        for (Patricia pt : cle.values())
            alpt.addAll(pt.getFeuilles());
        return alpt;
    }

    private void ajouterCleValeur(String cle, Patricia valeur) {
        valeur.pere = this;
        this.cle.put(cle, valeur);
    }

    private static String prefixCommun(String s, Patricia p) {
        for (String ps : p.cle.keySet()) {
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
            if (cle.get(val).hasFinMot()) {
                sousArbre.position = Hybride.cpt++;
                if (cle.get(val).cle.size() == 1)
                    continue;
            }
            sousArbre.eq = cle.get(val).toHybride();
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

    private Patricia(String mot, Patricia cle) {
        this.cle = new HashMap<>();
        this.cle.put(mot, cle);
    }

    private List<String> valeursRacine() {
        List<String> values = new ArrayList<>(this.cle.keySet());
        Collections.sort(values);
        return values;
    }

    private Patricia getSousArbre(int i) {
        List<String> values = new ArrayList<>(this.cle.keySet());
        Collections.sort(values);
        return this.cle.get(values.get(i));
    }

    private static String prefixe(String a, String b) {
        return a.substring(0, longueurPlusGrandPrefixeCommun(a, b));
    }

    private String maCleDansPere() {
        if (pere != null && pere.cle != null)
            for (String key : pere.cle.keySet())
                if (pere.cle.get(key) == this)
                    return key;
        return null;
    }
}
