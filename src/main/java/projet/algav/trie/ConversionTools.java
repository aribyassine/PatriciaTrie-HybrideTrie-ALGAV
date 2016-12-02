package projet.algav.trie;

import java.util.List;

public class ConversionTools {

    static Hybride patriciaNodeToHybride(List<String> node) {
        Hybride hybride = new Hybride();
        switch (node.size()) {
            case 0:
                return null;
            case 1:
                return stringToHybride(hybride, node.get(0));
            case 2:
                if (node.get(0).length() > node.get(1).length()) {
                    if (!node.get(0).isEmpty()) {
                        hybride.valeur = node.get(0).charAt(0);
                        hybride.eq = stringToHybride(new Hybride(hybride), node.get(0).substring(1));
                        hybride.sup = stringToHybride(new Hybride(hybride), node.get(1));
                        hybride.setPereDansFils();
                        return hybride;
                    }
                } else {
                    if (!node.get(1).isEmpty()) {
                        hybride.valeur = node.get(1).charAt(0);
                        hybride.eq = stringToHybride(new Hybride(hybride), node.get(1).substring(1));
                        hybride.inf = stringToHybride(new Hybride(hybride), node.get(0));
                        hybride.setPereDansFils();
                        return hybride;
                    }
                }
                break;
            case 3:
                if (!node.get(1).isEmpty()) {
                    hybride.valeur = node.get(1).charAt(0);
                    hybride.inf = stringToHybride(new Hybride(hybride), node.get(0));
                    hybride.eq = stringToHybride(new Hybride(hybride), node.get(1).substring(1));
                    hybride.sup = stringToHybride(new Hybride(hybride), node.get(2));
                    hybride.setPereDansFils();
                    return hybride;
                }
                break;
            default:
                if (!node.get(node.size() / 2).isEmpty()) {
                    hybride.valeur = node.get(node.size() / 2).charAt(0);
                    hybride.inf = patriciaNodeToHybride(node.subList(0, node.size() / 2));
                    hybride.eq = stringToHybride(new Hybride(hybride), node.get(node.size() / 2).substring(1));
                    hybride.sup = patriciaNodeToHybride(node.subList(node.size() / 2 + 1, node.size()));
                    hybride.setPereDansFils();
                    return hybride;
                }
        }
        return null;
    }

    static Hybride stringToHybride(Hybride h, String mot) {
        if (mot.length() == 0 || h == null)
            return null;
        h.valeur = mot.charAt(0);
        if (mot.length() == 1)
            return h;
        h.eq = new Hybride(h);
        stringToHybride(h.eq, mot.substring(1));
        return h;
    }

    static Hybride getLastHybrideNode(Hybride h, String mot) {
        if (mot.isEmpty())
            return h;
        char first = mot.charAt(0);
        if (mot.length() == 1) {
            if (first == h.valeur)
                return h;

            Hybride infNode = null;
            if (h.inf != null)
                if (first == h.inf.valeur)
                    return h.inf;
                else infNode = getLastHybrideNode(h.inf, mot);

            Hybride supNode = null;
            if (h.sup != null)
                if (first == h.sup.valeur)
                    return h.sup;
                else supNode = getLastHybrideNode(h.sup, mot);

            if (infNode != null)
                return infNode;
            if (supNode != null)
                return supNode;
        } else {
            if (first == h.valeur) {
                return getLastHybrideNode(h.eq, mot.substring(1));
            } else if (first < h.valeur) {
                return getLastHybrideNode(h.inf, mot);
            } else if (first > h.valeur) {
                return getLastHybrideNode(h.sup, mot);
            }
        }
        return null;
    }
}
