package projet.algav.trie;

import java.util.ArrayList;
import java.util.List;

public class ConversionTools {

    static Hybride patriciaNodeToHybride(List<String> node) {
        Hybride hybride = new Hybride();
        switch (node.size()) {
            case 1:
                return stringToHybride(hybride, node.get(0));
            case 2:
                if (node.get(0).length() > node.get(1).length()) {
                    if (!node.get(0).isEmpty()) {
                        hybride.valeur = node.get(0).charAt(0);
                        hybride.eq = stringToHybride(new Hybride(hybride), node.get(0).substring(1));
                        hybride.sup = stringToHybride(new Hybride(hybride), node.get(1));
                        return hybride;
                    }
                } else {
                    if (!node.get(1).isEmpty()) {
                        hybride.valeur = node.get(1).charAt(0);
                        hybride.eq = stringToHybride(new Hybride(hybride), node.get(1).substring(1));
                        hybride.inf = stringToHybride(new Hybride(hybride), node.get(1));
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
                    return hybride;
                }
                break;
            default:
                List<String> cp;
                if (node instanceof ArrayList) {
                     cp = (List) ((ArrayList) node).clone();
                }
                else {
                     cp = node;
                }
//                if (!cp.get(node.size() / 2).isEmpty()) {
//                    hybride.valeur = cp.get(node.size() / 2).charAt(0);
//                    cp.set(node.size() / 2, cp.get(node.size() / 2).substring(1));
//                    int cutInfEq;
//                    int cutEqSup;
//                    if (cp.size() % 3 == 0) {
//                        cutInfEq = cp.size() / 3;
//                        cutEqSup = cutInfEq * 2;
//                    } else {
//                        int round = (int) Math.round(((double) cp.size()) / 3);
//                        cutInfEq = round;
//                        cutEqSup = round == cp.size() / 3 ? round * 2 + 1 : round * 2 - 1;
//                    }
//                    //fusion
//                    hybride.inf = patriciaNodeToHybride(cp.subList(0, cutInfEq + 1));
//                    hybride.eq = patriciaNodeToHybride(cp.subList(cutInfEq, cutEqSup + 1));
//                    hybride.sup = patriciaNodeToHybride(cp.subList(cutEqSup, cp.size()));
//                    return hybride;
                    if (!node.get(node.size() / 2).isEmpty()) {
                        hybride.valeur = node.get(node.size() / 2).charAt(0);
                        hybride.inf = patriciaNodeToHybride(node.subList(0, node.size() / 2));
                        hybride.eq = stringToHybride(new Hybride(hybride),node.get(node.size() / 2).substring(1));
                        hybride.sup = patriciaNodeToHybride(node.subList(node.size() / 2+1, node.size()));
                        return hybride;
//                    }
                }
        }
        return null;
    }

    public static int[] diviserIntervalleEnTrois(int borne) {
        double dBorne = (double) borne;
        int x = borne / 3;
        int round = (int) Math.round(dBorne / 3);
        int[] res = new int[2];

        boolean grandM = dBorne / 3 - x > 0.5 ? true : false;
        if (grandM) {
            //13
            res[0] = round;
            res[1] = round * 2 - 1;
        } else {
            //14
            res[0] = round;
            res[1] = round * 2 + 1;
        }
        return res;
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

    static Hybride getLastEqHybride(Hybride h) {
        if (h == null)
            return null;
        Hybride tmp = h;
        while (tmp.eq != null) {
            tmp = tmp.eq;
        }
        return tmp;
    }

    static Hybride getLastInfHybride(Hybride h) {
        if (h == null)
            return null;
        Hybride tmp = h;
        while (tmp.inf != null) {
            tmp = tmp.inf;
        }
        return tmp;
    }

    static Hybride getLastSupHybride(Hybride h) {
        if (h == null)
            return null;
        Hybride tmp = h;
        while (tmp.sup != null) {
            tmp = tmp.sup;
        }
        return tmp;
    }
}
