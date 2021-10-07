public class Main {
    public static void main(String[] args) {
        var t1 = new Trie();
        t1.insert("cat");
        t1.insert("can");
        t1.insert("car");
        t1.insert("care");
        t1.insert("egg");
//        System.out.println(t1.contains("canada"));//false
//        System.out.println(t1.contains("can"));//true
//        System.out.println(t1.contains("cat"));//true
//        System.out.println(t1.contains(""));//false
//        System.out.println(t1.contains(null));//false
//        //traversePreOrder
////        t1.traverse_Pre_order();
//        //traversePostOrder
//        t1.traverse_Post_order();
//        //remove method
//        t1.remove("car");
//        t1.remove("");
//        t1.remove(null);
//        System.out.println(t1.contains("car"));//as car is removed so it return false
//        System.out.println(t1.contains("care"));//but care isn't removed
//        var words = t1.findWords("ca");
//        System.out.println(words);
//        System.out.println(t1.containsRecursive("cat"));
          t1.printWords();
//        t1.longestCommonPrefix();
    }
}
