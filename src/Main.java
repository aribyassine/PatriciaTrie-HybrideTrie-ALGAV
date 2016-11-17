import trie.PatriciaTrie;

public class Main {

    public static void main(String[] args) {
        String[] tab = {"aaa","a","aa","aaaa","a"};
        String[] tab2 = {"romane","romanus","romulus","rubens","ruber","rubicon","rubicundus"};
        PatriciaTrie pt = new PatriciaTrie("a", new PatriciaTrie("a", new PatriciaTrie("a", new PatriciaTrie())));

        System.out.println(pt);
        pt = new PatriciaTrie();
        for (String s : tab2) {
            pt.ajouter(s);
            System.out.println(pt);
        }

        /*
        System.out.println("-->"+pt.contientMot("a"));
        System.out.println("-->"+pt.contientMot("aa"));
        System.out.println("-->"+pt.contientMot("aaa"));
        System.out.println("-->"+pt.contientMot(""));
        System.out.println("-->"+pt.contientMot("lol"));
        System.out.println("-->"+pt.contientMot("aaaa"));
        System.out.println("______________________________________________");
        System.out.println("-->"+pt.contientMotNonStricte("a"));
        System.out.println("-->"+pt.contientMotNonStricte("aa"));
        System.out.println("-->"+pt.contientMotNonStricte("aaa"));
        System.out.println("-->"+pt.contientMotNonStricte(""));
        System.out.println("-->"+pt.contientMotNonStricte("ya"));
        System.out.println("-->"+pt.contientMotNonStricte("ay"));
        System.out.println("-->"+pt.contientMotNonStricte("lol"));
        System.out.println("-->"+pt.contientMotNonStricte("aaaa"));*/
    }
}