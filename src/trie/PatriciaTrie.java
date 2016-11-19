package trie;

import java.util.*;

public class PatriciaTrie {
    private HashMap<String, PatriciaTrie> cle;
    private PatriciaTrie pere;
    public static String finMot = "є";


    public PatriciaTrie() {
        this.cle = new HashMap<String, PatriciaTrie>();
    }

    private PatriciaTrie(String mot, PatriciaTrie cle) {
        this.cle = new HashMap<String, PatriciaTrie>();
        this.cle.put(mot, cle);
    }


    public boolean estVide() {
        return this.cle.isEmpty();
    }

    public List<String> valeursRacine() {
        List<String> values = new ArrayList<String>(this.cle.keySet());
        Collections.sort(values);
        return values;
    }

    public PatriciaTrie sousArbre(int i) {
        List<String> values = new ArrayList<String>(this.cle.keySet());
        Collections.sort(values);
        return this.cle.get(values.get(i));
    }

    public static int longueurPlusGrandPrefixeCommun(String a, String b) {
        int len = 0;
        for (int i = 0; i < Math.min(a.length(), b.length()); ++i) {
            if (a.charAt(i) != b.charAt(i))
                break;
            ++len;
        }
        return len;
    }

    private String prefixe(String a, String b) {
        return a.substring(0, longueurPlusGrandPrefixeCommun(a, b));
    }

    public void ajouter(String mot) {
        if (!mot.isEmpty()) {
            if (this.estVide()) {
                PatriciaTrie pt = new PatriciaTrie();
                cle.put(mot, pt);
                pt.cle.put(finMot, new PatriciaTrie());
                pt.setPere(this);
            } else {
                PatriciaTrie tmp;
                int lp = 0;
                for (String s : this.valeursRacine()) {
                    lp = longueurPlusGrandPrefixeCommun(s, mot);
                    if (lp != 0) {
                        String prefixe = prefixe(s, mot);
                        if (!(lp == s.length())) {
                            tmp = cle.get(s);
                            cle.remove(s);
                            PatriciaTrie pt = new PatriciaTrie(s.substring(lp), tmp);
                            cle.put(prefixe, pt);
                            pt.setPere(this);
                            tmp.setPere(pt);
                        }
                        if (!(lp == mot.length()))
                            cle.get(prefixe).ajouter(mot.substring(lp));
                        else
                            cle.get(prefixe).cle.put(finMot, new PatriciaTrie());
                        break;
                    }
                }
                if (lp == 0) {
                    PatriciaTrie pt = new PatriciaTrie();
                    cle.put(mot, pt);
                    pt.cle.put(finMot, new PatriciaTrie());
                    pt.setPere(this);
                }
            }
        }
    }

    private boolean hasFinMot() {
        for (String s : valeursRacine()) {
            if (s.equals(finMot))
                return true;
        }
        return false;
    }

    public boolean recherche(String mot) {
        if (mot.isEmpty() && (this.hasFinMot() || this.estVide()))
            return true;
        int lp = 0;
        for (String s : this.valeursRacine()) {
            lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length()) {
                return cle.get(s).recherche(mot.substring(lp));
            }
        }

        return false;
    }

    public int hauteur() {
        int hMax = 0;
        if (this.cle.isEmpty())
            return 0;
        for (PatriciaTrie pt : cle.values()) {
            int hFils = pt.hauteur();
            if (hFils > hMax)
                hMax = hFils;
        }
        return hMax + 1;
    }

    public List<String> listeMots() {
        ArrayList<String> mots = new ArrayList<String>();
        for (StringBuilder sb : this.listeMotsRecursive()) {
            mots.add(sb.toString());
        }
        Collections.sort(mots);
        return mots;
    }

    private ArrayList<StringBuilder> listeMotsRecursive() {
        ArrayList<StringBuilder> res = new ArrayList<StringBuilder>();
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

    public int comptageMots() {
        int nbMot = 0;
        if (cle.keySet().contains(finMot))
            nbMot++;
        for (PatriciaTrie pt : cle.values())
            nbMot += pt.comptageMots();
        return nbMot;
    }

    public boolean contientMotNonStricte(String mot) {
        if (mot.isEmpty())
            return true;
        int lp = 0;
        for (String s : this.valeursRacine()) {
            lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0) {
/*                System.out.println("mot "+mot);
                System.out.println("smo "+s);
                System.out.println("sub "+mot.substring(lp));*/
                return cle.get(s).contientMotNonStricte(mot.substring(lp));
            }
        }
        return false;
    }

    public int niveau() {
        int lvl = 0;
        PatriciaTrie pere = this.pere;
        while (pere != null) {
            pere = pere.getPere();
            lvl++;
        }
        return lvl;
    }

    public boolean supprimer(String mot) {
        if (this.estVide()) {
            return false;
        }
        int longueurPrefixe = 0;
        for (String a : cle.keySet()) {
            longueurPrefixe = longueurPlusGrandPrefixeCommun(a, mot);
            if (longueurPrefixe != 0) {
            }
        }

        return true;
    }

    public PatriciaTrie getPere() {
        return pere;
    }

    public void setPere(PatriciaTrie pere) {
        this.pere = pere;
    }

    public boolean isLeaf() {
        return this.cle.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.valeursRacine()) {
            for (int i = 0; i < this.niveau(); i++) {
                sb.append("|\t");
            }
            sb.append("|-->" + s + "\n").append(this.cle.get(s).toString());
        }
        return sb.toString();
        //return this.cle.toString();
    }
}
