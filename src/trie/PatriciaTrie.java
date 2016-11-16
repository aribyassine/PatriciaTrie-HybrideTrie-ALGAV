package trie;

import java.util.*;

public class PatriciaTrie {
    private Map<String, PatriciaTrie> cle;

    public PatriciaTrie(String mot) {
        this.cle = new HashMap<String, PatriciaTrie>();
        this.cle.put(mot, new PatriciaTrie());
    }

    public PatriciaTrie() {
        this.cle = new HashMap<String, PatriciaTrie>();
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

    public boolean ajouter(String mot) {
        if (this.estVide()) {
            this.cle.put(mot, new PatriciaTrie());
            return true;
        }

        int longueurPrefixe = 0;
        for (String a : cle.keySet()) {
            longueurPrefixe = longueurPlusGrandPrefixeCommun(a, mot);
            if (longueurPrefixe != 0) {
                if (longueurPrefixe == mot.length())
                    return false;
                this.cle.get(a).ajouter(mot.substring(longueurPrefixe));
                break;
            }
        }
        if (longueurPrefixe == 0)
            this.cle.put(mot, new PatriciaTrie());
        return true;
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
}
