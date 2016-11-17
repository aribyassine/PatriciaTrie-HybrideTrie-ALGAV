import trie.PatriciaTrie;

public class Main {

    public static void main(String[] args) {
        String[] tab = {"romane", "romanus", "romulus", "rubens", "ruber", "rubicon", "rubicundus"};
        String[] tab2 = {"a", "aa", "aaa"};
        PatriciaTrie pt = new PatriciaTrie("a", new PatriciaTrie("a", new PatriciaTrie("a", new PatriciaTrie())));

        System.out.println(pt);
        pt = new PatriciaTrie();
        for (String s : tab) {
            pt.ajouter(s);
            System.out.println(pt);
        }
    }
}