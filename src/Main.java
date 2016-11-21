import trie.*;

public class Main {

    public static void main(String[] args) {


        String[] tab = {"b", "ab", "bc", "abc", "bcd"};
        String[] tab2 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus"};
        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
        String[] tab3 = exemple.split(" ");
        Hybride pt = new Hybride();
        System.out.println(pt);
        for (String s : tab) {
            pt.ajouter(s);
        }
        System.out.println(pt);
        System.out.println(pt.comptageMots());
        System.out.println(pt.listeMots());
        System.out.println(pt.comptageNil());
        System.out.println(pt.ProfondeurMoyenne());
        System.out.println(pt.prefixe("ro"));

        System.out.println("apres suppression");
        String[] tab4 = {"bcd","bc","b", "ab", "bc", "abc","ab"};
        for (String s : tab4) {
            System.out.println(pt.suppression(s));
            System.out.println(s);
            System.out.println(pt);
        }
        System.out.println(pt.comptageMots());
        System.out.println(pt.listeMots());
        System.out.println(pt.comptageNil());
        System.out.println(pt.ProfondeurMoyenne());
        System.out.println(pt.prefixe("ro"));
//        for (String s : tab) {
//            pt.ajouter(s);
//        }
//        System.out.println(pt.toString());
//        System.out.println(pt.comptageMots());
//        System.out.println(pt.listeMots());
//        System.out.println(pt.comptageNil());
//        System.out.println(pt.ProfondeurMoyenne());
//        System.out.println(pt.prefixe("ab"));


//        String[] tab = {"a","ab","ac","ad","af","abc","abcd","abcde","abcdef"};
//        String[] tab2 = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus", "rubic","a","ab","ac","ad","af","abc","abcd","abcde","abcdef"};
//        String exemple = "A quel genial professeur de dactylographie sommes nous redevables de la superbe phrase ci dessous, un modele du genre, que toute dactylo connait par coeur puisque elle fait appel a chacune des touches du clavier de la machine a ecrire ?";
//        String[] tab3 = exemple.split(" ");
//        Patricia pt = new Patricia();
//        for (String s : tab) {
//            pt.ajouter(s);
//        }
//        System.out.println(pt);
//        for (int i = 0; i < tab.length; i++) {
//            int j = (int)(Math.random()*(double)tab.length);
//            System.out.println(pt.supprimer(tab[j]));
//            System.out.println(tab[j]);
//            System.out.println(pt);
//        }


//        String t = "r";
//        System.out.println(pt.prefixe(t));
//        System.out.println(pt.listeSuffixe(t));
//        System.out.println(pt.ayantLePrefixe(t));
//
//
//        System.out.println(pt.recherche("romulus"));
//        System.out.println(pt.recherche("romu"));
//        System.out.println(pt.recherche("romuluss"));
//
//        System.out.println("_______________________");
//        System.out.println(pt.sousArbre(0));
//        System.out.println(pt.sousArbre(0).sousArbre(0));
//        System.out.println(pt.sousArbre(0).sousArbre(0).sousArbre(0));
//        System.out.println(pt.sousArbre(0).sousArbre(0).sousArbre(0).sousArbre(0));
//        System.out.println("_______________________");
//        System.out.println(pt.sousArbre(0).longueurDeLabranche());
//        System.out.println(pt.sousArbre(0).sousArbre(0).longueurDeLabranche());
//        System.out.println(pt.sousArbre(0).sousArbre(0).sousArbre(0).longueurDeLabranche());
//        System.out.println(pt.sousArbre(0).sousArbre(0).sousArbre(0).sousArbre(0).longueurDeLabranche());*/
//
//
//
//
//        System.out.println("-->"+pt.recherche("a"));
//        System.out.println("-->"+pt.recherche("aa"));
//        System.out.println("-->"+pt.recherche("aaa"));
//        System.out.println("-->"+pt.recherche(""));
//        System.out.println("-->"+pt.recherche("lol"));
//        System.out.println("-->"+pt.recherche("aaaa"));
//        System.out.println("______________________________________________");
//        System.out.println("-->"+pt.contientMotNonStricte("a"));
//        System.out.println("-->"+pt.contientMotNonStricte("aa"));
//        System.out.println("-->"+pt.contientMotNonStricte("aaa"));
//        System.out.println("-->"+pt.contientMotNonStricte(""));
//        System.out.println("-->"+pt.contientMotNonStricte("ya"));
//        System.out.println("-->"+pt.contientMotNonStricte("ay"));
//        System.out.println("-->"+pt.contientMotNonStricte("lol"));
//        System.out.println("-->"+pt.contientMotNonStricte("aaaa"));
    }
}