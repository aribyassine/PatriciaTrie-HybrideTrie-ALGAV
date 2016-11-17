package trie;

import java.util.*;

public class PatriciaTrie {
    private HashMap<String, PatriciaTrie> cle;

    public PatriciaTrie(String mot) {
        this.cle = new HashMap<String, PatriciaTrie>();
        this.cle.put(mot, new PatriciaTrie());
    }

    public PatriciaTrie() {
        this.cle = new HashMap<String, PatriciaTrie>();

    }

    public PatriciaTrie(String mot, PatriciaTrie cle) {
        this.cle = new HashMap<String, PatriciaTrie>();
        this.cle.put(mot, cle);
    }

    public boolean estVide() {
        return this.cle.isEmpty();
    }

    public Set<String> valeurRacine() {
        return this.cle.keySet();
    }

    public Collection<PatriciaTrie> sousArbres() {
        return this.cle.values();
    }

    public String sousArbre(int i) {
        List<String> values = new ArrayList<String>(this.cle.keySet());
        Collections.sort(values);
        return values.get(i);
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
        if (this.estVide())
            cle.put(mot, new PatriciaTrie());
        else {
            PatriciaTrie tmp;
            int lp = 0;
            for (String s : this.valeurRacine()) {
                lp = longueurPlusGrandPrefixeCommun(s, mot);
                if (lp != 0) {
                    String prefixe = prefixe(s, mot);
                    if (!(lp == s.length())) {
                        tmp = cle.get(s);
                        cle.remove(s);
                        cle.put(prefixe, new PatriciaTrie(s.substring(lp), tmp));
                    }
                    cle.get(prefixe).ajouter(mot.substring(lp));
                    break;
                }
            }
            if (lp == 0) {
                cle.put(mot, new PatriciaTrie());
            }
        }
    }

    private boolean hasFinMot() {
        for (String s : valeurRacine()) {
            if (s.isEmpty())
                return true;
        }
        return false;
    }

    public boolean contientMot(String mot) {
        if (mot.isEmpty() && (this.hasFinMot() || this.estVide()))
            return true;
        int lp = 0;
        for (String s : this.valeurRacine()) {
            lp = longueurPlusGrandPrefixeCommun(s, mot);
            if (lp != 0 && lp == s.length()) {
                return cle.get(s).contientMot(mot.substring(lp));
            }
        }
        return false;
    }

    public boolean contientMotNonStricte(String mot) {
        if (mot.isEmpty())
            return true;
        int lp = 0;
        for (String s : this.valeurRacine()) {
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

    public boolean isLeaf() {
        return this.cle.isEmpty();
    }

    @Override
    public String toString() {
        return cle.toString();
    }
}
