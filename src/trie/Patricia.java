package trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Patricia {
    private static String finMot = "є";
    private HashMap<String, Patricia> cle;
    private Patricia pere;


    public Patricia() {
        this.cle = new HashMap<String, Patricia>();
    }

    private Patricia(String mot, Patricia cle) {
        this.cle = new HashMap<String, Patricia>();
        this.cle.put(mot, cle);
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

    private static String prefixe(String a, String b) {
        return a.substring(0, longueurPlusGrandPrefixeCommun(a, b));
    }

    public boolean estVide() {
        return this.cle.isEmpty();
    }

    public List<String> valeursRacine() {
        List<String> values = new ArrayList<String>(this.cle.keySet());
        Collections.sort(values);
        return values;
    }

    public Patricia sousArbre(int i) {
        List<String> values = new ArrayList<String>(this.cle.keySet());
        Collections.sort(values);
        return this.cle.get(values.get(i));
    }

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

    public boolean supprimer(String mot) {
        if (mot.isEmpty() && (this.hasFinMot())) {
            cle.remove(finMot);
            String nom = null;
            HashMap<String, Patricia> clePere = this.pere.cle;
            for (String key : clePere.keySet())
                if (clePere.get(key) == this)
                    nom = key;
            //supprimer dans le pere si il existe et si cle vide
            if (cle.isEmpty() && this.getPere() != null) {
                clePere.remove(nom);
            }
            // si reste un frere seul fusion frere avec pere si il existe
            if (clePere.size() > 0) {
                Patricia grandPere = pere.pere;
                //keySet().toArray()[0] existe puisque clePere.size()==1
                String nomPere = (String) clePere.keySet().toArray()[0];
                //fusion avec les fils
                if (grandPere != null) {
                    String nomGrandpPere = null;
                    for (String key : grandPere.cle.keySet())
                        if (grandPere.cle.get(key) == pere)
                            nomGrandpPere = key;
                    pere.cle.get(nomPere).setPere(grandPere);
                    if (!nomPere.equals(finMot)) {
                        grandPere.cle.remove(nomPere);
                        grandPere.cle.remove(nomGrandpPere);
                        grandPere.cle.put(nomGrandpPere + nomPere, clePere.get(nomPere));
                    }
                } else {
                    if (clePere.get(nomPere).cle.keySet().size() == 1) {
                        String frere = (String) clePere.get(nomPere).cle.keySet().toArray()[0];
                        Patricia saveFils = clePere.get(nomPere).cle.get(frere);
                        saveFils.setPere(pere);
                        clePere.get(nomPere).cle.remove(frere);
                        clePere.remove(nomPere);
                        clePere.put(nomPere + frere, saveFils);
                    }
                }
            }
            return true;
        }
        for (String s : this.cle.keySet()) {
            int lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length()) {
                return cle.get(s).supprimer(mot.substring(lp));
            }
        }
        return false;
    }

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

    public List<String> ayantLePrefixe(String mot) {
        List<String> suffixes = listeSuffixe(mot);
        List<String> res = new ArrayList<String>();
        for (String suffixe : suffixes) {
            res.add(mot + suffixe);
        }
        return res;
    }

    public List<String> listeSuffixe(String mot) {
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
        return new ArrayList<String>();
    }

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
        for (Patricia pt : cle.values())
            nbMot += pt.comptageMots();
        return nbMot;
    }

    public double profondeurMoyenne() {
        int sommeProfondeur = 0;
        ArrayList<Patricia> feuilles = getFeuilles();
        for (Patricia pt : feuilles)
            sommeProfondeur += pt.longueurDeLabranche();
        return sommeProfondeur / (double) feuilles.size();
    }

    public boolean contientMotNonStricte(String mot) {
        if (mot.isEmpty())
            return true;
        int lp = 0;
        for (String s : this.cle.keySet()) {
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

    private int longueurDeLabranche() {
        int lvl = 0;
        Patricia pere = this.pere;
        while (pere != null) {
            pere = pere.getPere();
            lvl++;
        }
        return lvl;
    }

    public Patricia getPere() {
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
        ArrayList<Patricia> alpt = new ArrayList<Patricia>();
        if (cle.keySet().contains(finMot))
            alpt.add(this);
        for (Patricia pt : cle.values())
            alpt.addAll(pt.getFeuilles());


        return alpt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.valeursRacine()) {
            for (int i = 0; i < this.longueurDeLabranche(); i++) {
                sb.append("|\t");
            }
            sb.append("|-->" + s + "\n").append(this.cle.get(s).toString());
        }
        return sb.toString();
        //return this.cle.toString();
    }

    private List<String> ajouterPrefixe(String mot, List<String> list) {
        List<String> res = new ArrayList<String>();
        for (String suffixe : list) {
            res.add(mot + suffixe);
        }
        return res;
    }
}
