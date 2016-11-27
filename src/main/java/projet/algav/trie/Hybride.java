package projet.algav.trie;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Hybride implements Trie {
    private static final char motVide = '∅';
    private static int cpt = 0;
    private int position = 0;
    private Hybride inf, sup, eq;
    private char valeur;
    private Hybride pere;

    public Hybride() {
        valeur = motVide;
    }

    private Hybride(Hybride pere) {
        valeur = motVide;
        this.pere = pere;
    }

    @Override
    public void ajouter(String mot) {
        if (mot.length() == 0)
            return;
        if (estVide()) {
            valeur = mot.charAt(0);
            if (mot.length() == 1) {
                cpt++;
                position = cpt;
            } else {
                if (eq == null)
                    eq = new Hybride(this);
                eq.ajouter(mot.substring(1));
            }
        } else {
            char premier = mot.charAt(0);
            if (premier < valeur) {
                if (inf == null)
                    inf = new Hybride(this);
                inf.ajouter(mot);
            } else if (premier > valeur) {
                if (sup == null)
                    sup = new Hybride(this);
                sup.ajouter(mot);
            } else if (premier == valeur) {
                if (eq == null && mot.length() != 1)
                    eq = new Hybride(this);
                if (mot.length() == 1) {
                    cpt++;
                    position = cpt;
                } else
                    eq.ajouter(mot.substring(1));
            }
        }
    }

    @Override
    public boolean estVide() {
        return valeur == motVide;
    }

    @Override
    public boolean recherche(String mot) {
        if (mot.isEmpty() || this.estVide())
            return false;
        if (mot.length() == 1) {
            if (mot.charAt(0) == this.valeur && this.position != 0)
                return true;
            if (inf != null)
                if (mot.charAt(0) == inf.valeur && inf.position != 0)
                    return true;
            if (sup != null)
                if (mot.charAt(0) == sup.valeur && sup.position != 0)
                    return true;
            return false;
        } else {
            char p = mot.charAt(0);
            if (p == valeur) {
                return eq != null && eq.recherche(mot.substring(1));
            }
            if (p < valeur) {
                return inf != null && inf.recherche(mot);
            }
            if (p > valeur) {
                return sup != null && sup.recherche(mot);
            }

        }
        return false;
    }

    @Override
    public int comptageMots() {
        int nbMots = 0;
        if (this.position != 0)
            nbMots++;
        if (eq != null)
            nbMots += eq.comptageMots();
        if (inf != null)
            nbMots += inf.comptageMots();
        if (sup != null)
            nbMots += sup.comptageMots();
        return nbMots;
    }

    @Override
    public JsonElement toNoCollapsibleJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerializer<Hybride> serializer = new JsonSerializer<Hybride>() {
            @Override
            public JsonElement serialize(Hybride hybride, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", valeur + (position != 0 ? "," + position : "  "));
                if (pere != null) {
                    jsonObject.addProperty("parent", pere.valeur + (pere.position != 0 ? "," + pere.position : "  "));
                } else {
                    jsonObject.addProperty("parent", "null");
                }
                JsonArray children = new JsonArray();
                for (Hybride h : hybride.getSousArbres())
                    if (h != null)
                        children.add(h.toNoCollapsibleJSON());
                    else {
                        JsonObject tmp = new JsonObject();
                        tmp.addProperty("name", motVide);
                        children.add(tmp);
                    }
                jsonObject.add("children", children);
                if (pere == null) {
                    JsonArray tmp = new JsonArray();
                    tmp.add(jsonObject);
                    return tmp;
                } else {
                    return jsonObject;
                }
            }
        };
        gsonBuilder.registerTypeAdapter(Hybride.class, serializer);
        Gson customGson = gsonBuilder.create();
        return customGson.toJsonTree(this);
    }

    @Override
    public JsonElement toCollapsibleJSON() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonSerializer<Hybride> serializer = new JsonSerializer<Hybride>() {
            @Override
            public JsonElement serialize(Hybride hybride, Type type, JsonSerializationContext jsonSerializationContext) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", valeur + (position != 0 ? "," + position : "  "));
                JsonArray children = new JsonArray();
                for (Hybride h : hybride.getSousArbres())
                    if (h != null)
                        children.add(h.toCollapsibleJSON());
                    else {
                        JsonObject tmp = new JsonObject();
                        tmp.addProperty("name", motVide);
                        children.add(tmp);
                    }
                jsonObject.add("children", children);
                return jsonObject;
            }
        };
        gsonBuilder.registerTypeAdapter(Hybride.class, serializer);
        Gson customGson = gsonBuilder.create();
        return customGson.toJsonTree(this);
    }

    private int longueurDeLabranche() {
        int lvl = 0;
        Hybride pere = this.pere;
        while (pere != null) {
            pere = pere.pere;
            lvl++;
        }
        return lvl;
    }

    private List<String> ajouterPrefixe(String mot, List<String> list) {
        List<String> res = new ArrayList<>();
        for (String suffixe : list) {
            res.add(mot + suffixe);
        }
        return res;
    }

    @Override
    public List<String> listeMots() {
        List<String> mots = new ArrayList<>();
        String val = String.valueOf(valeur);
        if (position != 0)
            mots.add(val);
        if (inf != null)
            mots.addAll(inf.listeMots());
        if (eq != null)
            mots.addAll(ajouterPrefixe(val, eq.listeMots()));
        if (sup != null)
            mots.addAll(sup.listeMots());
        return mots;
    }

    @Override
    public int comptageNil() {
        int nbNils = 0;
        if (eq != null)
            nbNils += eq.comptageNil();
        else
            nbNils++;
        if (inf != null)
            nbNils += inf.comptageNil();
        else
            nbNils++;
        if (sup != null)
            nbNils += sup.comptageNil();
        else
            nbNils++;
        return nbNils;
    }

    @Override
    public int hauteur() {
        int infH = 0, supH = 0, eqH = 0;
        if (this.estVide())
            return 0;
        if (inf != null)
            infH = inf.hauteur();
        if (eq != null)
            eqH = eq.hauteur();
        if (sup != null)
            supH = sup.hauteur();

        return Integer.max(Integer.max(infH, eqH), supH) + 1;

    }

    @Override
    public int largeur() {
        if (this.isLeaf())
            return 3;
        int cpt = 0;
        if (inf != null)
            cpt += inf.largeur();
        if (eq != null)
            cpt += eq.largeur();
        if (sup != null)
            cpt += sup.largeur();
        return cpt;
    }

    private List<Hybride> getFeuilles() {
        List<Hybride> feuilles = new ArrayList<>();
        if (inf == null && eq == null && sup == null) {
            feuilles.add(this);
            return feuilles;
        } else {
            if (inf != null)
                feuilles.addAll(inf.getFeuilles());
            if (eq != null)
                feuilles.addAll(eq.getFeuilles());
            if (sup != null)
                feuilles.addAll(sup.getFeuilles());
        }

        return feuilles;

    }

    @Override
    public double profondeurMoyenne() {
        int prof = 0;
        int nbrFeuille = (this.getFeuilles()).size();
        for (Hybride feuille : this.getFeuilles()) {
            prof += feuille.longueurDeLabranche();
        }
        return (double) prof / (double) nbrFeuille;
    }

    @Override
    public int prefixe(String mot) {
        int nbPrefixe = 0;
        if (mot.isEmpty() || this.estVide())
            return 0;
        if (mot.length() == 1) {
            if (mot.charAt(0) == this.valeur) {
                if (position != 0)
                    return eq.comptageMots() + 1;
                return eq.comptageMots();
            }
            if (inf != null)
                if (inf.eq != null)
                    if (mot.charAt(0) == inf.valeur)
                        return inf.eq.comptageMots();

            if (sup != null)
                if (sup.eq != null)
                    if (mot.charAt(0) == sup.valeur)
                        return sup.eq.comptageMots();

        } else {
            if (inf != null)
                nbPrefixe += inf.prefixe(mot);
            if (eq != null)
                nbPrefixe += eq.prefixe(mot.substring(1));
            if (sup != null)
                nbPrefixe += sup.prefixe(mot);
        }
        return nbPrefixe;
    }

    @Override
    public boolean supprimer(String mot) {
        if (mot.isEmpty() || this.estVide())
            return false;
        if (mot.length() == 1) {
            if (mot.charAt(0) == this.valeur && this.position != 0) {
                this.position = 0;
                if (isLeaf()) {
                    this.couperLesBranchesInutiles();
                }
                return true;
            }
            Hybride[] fils = {inf, sup};
            for (Hybride h : fils) {
                if (h != null)
                    if (mot.charAt(0) == h.valeur && h.position != 0) {
                        h.position = 0;
                        if (isLeaf()) {
                            this.couperLesBranchesInutiles();
                        }
                        return true;
                    }
            }
            return false;
        } else {
            char p = mot.charAt(0);
            if (p == valeur) {
                return eq != null && eq.supprimer(mot.substring(1));
            }
            if (p < valeur) {
                return inf != null && inf.supprimer(mot);
            }
            if (p > valeur) {
                return sup != null && sup.supprimer(mot);
            }
        }
        return false;
    }

    public void fusion(Hybride hybride) {
        if (hybride != null) {
            if (estVide()) {
                this.initialisation(hybride);
            } else if (this.valeur == hybride.valeur) {
                if (eq == null) {
                    eq = hybride.eq;
                    if (eq != null)
                        eq.pere = this;
                } else eq.fusion(hybride.eq);
                if (inf == null) {
                    inf = hybride.inf;
                    if (inf != null)
                        inf.pere = this;
                } else inf.fusion(hybride.inf);
                if (sup == null) {
                    sup = hybride.sup;
                    if (sup != null)
                        sup.pere = this;
                } else sup.fusion(hybride.sup);
            } else if (this.valeur > hybride.valeur) {
                if (inf == null) {
                    inf = hybride;
                    if (inf != null)
                        inf.pere = this;
                } else inf.fusion(hybride);
            } else if (this.valeur < hybride.valeur) {
                if (sup == null) {
                    sup = hybride;
                    if (sup != null)
                        sup.pere = this;
                } else sup.fusion(hybride);
            }
        }
    }

    public void initialisation(Hybride hybride) {
        this.valeur = hybride.valeur;
        this.eq = hybride.eq;
        this.inf = hybride.inf;
        this.sup = hybride.sup;
        this.valeur = hybride.valeur;
    }

    private boolean isLeaf() {
        boolean videEq = true;
        boolean videInf = true;
        boolean videSup = true;

        if (eq != null)
            videEq = eq.estVide();
        if (inf != null)
            videInf = inf.estVide();
        if (sup != null)
            videSup = sup.estVide();
        return videEq && videInf && videSup;
    }

    private Hybride[] getSousArbres() {
        return new Hybride[]{inf, eq, sup};
    }

    private void couperLesBranchesInutiles() {
        Hybride pere = this.pere;
        Hybride self = this;
        while (pere != null && pere.comptageMots() == 0) {
            self = self.pere;
            pere = pere.pere;
        }
        if (pere == null) {
            //on est a la racine donc on reinitialise l'arbre
            self.position = 0;
            self.valeur = motVide;
            self.inf = null;
            self.eq = null;
            self.inf = null;
            return;
        } else if (self.position == 0) {
            //on coupe la branche inutile
            self.supprimerDansPere();
            return;
        }
    }

    private boolean supprimerDansPere() {
        if (pere == null)
            return false;
        Hybride[] sousArbresPere = pere.getSousArbres();
        for (int i = 0; i < sousArbresPere.length; i++) {
            if (sousArbresPere[i] == this) {
                switch (i) {
                    case 0:
                        pere.inf = null;
                        break;
                    case 1:
                        pere.eq = null;
                        break;
                    case 2:
                        pere.sup = null;
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        int lb = this.longueurDeLabranche();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lb; i++) {
            sb.append("|\t");
        }
        sb.append(valeur).append(position != 0 ? "," + position : "  ").append("\n");
        Hybride[] fils = getSousArbres();
        for (Hybride h : fils) {
            if (h == null) {
                for (int i = 0; i < lb + 1; i++) {
                    sb.append("|\t");
                }
                sb.append("∅\n");
            } else
                sb.append(h.toString());
        }
        return sb.toString();
    }
}
