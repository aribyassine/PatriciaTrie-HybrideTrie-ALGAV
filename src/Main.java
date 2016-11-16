import trie.PatriciaTrie;

public class Main {

    public static void main(String[] args) {
        String a = "Hello World!";
        String b = "Hello biatch!";
        System.out.println(a.substring(0,PatriciaTrie.longueurPlusGrandPrefixeCommun(a,b)));
    }
}
