package trie;

import java.util.List;


import java.util.ArrayList;

public class Hybride {
    private static char motVide = '∅';
    private static int cpt = 0;
    private int position = 0;
    private Hybride inf, sup, eq;
    private char valeur;
    private Hybride pere;

    public Hybride() {
        valeur = motVide;
    }

    public Hybride(Hybride pere) {
        valeur = motVide;
        this.pere = pere;
    }

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
            } else {
                if (eq == null)
                    eq = new Hybride(this);
                if (mot.length() == 1) {
                    cpt++;
                    position = cpt;
                }
                eq.ajouter(mot.substring(1));
            }
        }
    }

    public boolean estVide() {
        return valeur == motVide;
    }

    public boolean rechercher(String mot) {
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
                if (eq == null)
                    return false;
                return eq.rechercher(mot.substring(1));
            }
            if (p < valeur) {
                if (inf == null)
                    return false;
                return inf.rechercher(mot);
            }
            if (p > valeur) {
                if (sup == null)
                    return false;
                return sup.rechercher(mot);
            }

        }
        return false;
    }

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
        List<String> res = new ArrayList<String>();
        for (String suffixe : list) {
            res.add(mot + suffixe);
        }
        return res;
    }

    public List<String> listeMots() {
        List<String> mots = new ArrayList<String>();
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

    private List<Hybride> getFeuilles() {
        List<Hybride> feuilles = new ArrayList<Hybride>();
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

    public Double ProfondeurMoyenne() {
        int prof = 0;
        int nbrFeuille = (this.getFeuilles()).size();
        for (Hybride feuille : this.getFeuilles()) {
            prof += feuille.longueurDeLabranche();
        }
        return (double) prof / (double) nbrFeuille;
    }

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

    public boolean suppression(String mot) {
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
            Hybride[] fils = {inf,sup};
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
                if (eq == null)
                    return false;
                return eq.suppression(mot.substring(1));
            }
            if (p < valeur) {
                if (inf == null)
                    return false;
                return inf.suppression(mot);
            }
            if (p > valeur) {
                if (sup == null)
                    return false;
                return sup.suppression(mot);
            }
        }
        return false;
    }

    public boolean isLeaf() {
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

    public Hybride[] getSousArbres() {
        return new Hybride[]{inf, eq, sup};
    }

    private boolean couperLesBranchesInutiles() {
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
            return true;
        } else if (self.position == 0) {
            //on coupe la branche inutile
            self.supprimerDansPere();
            return true;
        }
        return false;
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
        sb.append("|-->" + valeur + (position != 0 ? "-" + position : "") + "\n");
        Hybride[] fils = getSousArbres();
        for (Hybride h : fils) {
            if (h == null) {
                for (int i = 0; i < lb + 1; i++) {
                    sb.append("|\t");
                }
                sb.append("|-->∅\n");
            } else
                sb.append(h.toString());
        }
        return sb.toString();
    }
}
