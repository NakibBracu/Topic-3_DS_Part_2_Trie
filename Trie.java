import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trie {
    public static int Alphabet_Size=26;

    private class Node{
        private char value;
        private HashMap<Character,Node> children =  new HashMap<>();
        private boolean isEndofWord;

        public Node(char value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "value=" + value;
        }
        //for better abstractions we need to change methods in node class and use these
        //methods in outer classes
        //abstraction is like we want to do minimal changes for extending works
        //just like remote button. Many functionalities have controlled by buttons
        //How it works. It doesn't matter at all for users.

        public boolean hasChild(char ch){
            return children.containsKey(ch);
        }
        public void addChild(char ch){
            children.put(ch,new Node(ch));
        }
        public Node getChild(char ch){
            return children.get(ch);
        }
        public Node[] getChildren(){
            return children.values().toArray(new Node[0]);
            //As children.values().toArray() is returning a collection of nodes
            //but we need array to be returned that's why we create a new node object
            //That will put the result in Node array
        }
        public boolean hasChildren(){
            return !children.isEmpty();//if children exist , then return false
        }
        public void removeChild(char ch){
            children.remove(ch);
        }
    }
    private Node root = new Node(' ');
    static String[] words= new String[10];//assume 10 words can be inserted
    public void insert(String word){
        var current = root;//use of inner class
        for(var ch:word.toCharArray()){
           // var index = ch-'a';
            //If we use hashmap we don't need index cause we use character for key.
            if(!current.hasChild(ch))//its like a story now. Do you have any children?
                current.addChild(ch);//If there is no children, then add children
            current = current.getChild(ch);//also get the children as updated current
        }
        current.isEndofWord=true;//make it true means finish the words.
    }
    public boolean contains(String word){
        if(word==null)//if null input then return false
            return false;
        var current = root;//call root because we need to iterate the trie
        for(var ch:word.toCharArray()){//string to array converted for iteration
            if(!current.hasChild(ch))//if we don't find any particular character we immediately return false
                return false;
            current = current.getChild(ch);//otherwise set current to next character and check if it's exist in trie
        }
        return current.isEndofWord;
    }
    public boolean containsRecursive(String word) {
        if (word == null)
            return false;

        return containsRecursive(root, word, 0);
    }

    private boolean containsRecursive(Node root, String word, int index) {
        // Base condition
        if (index == word.length())
            return root.isEndofWord;

        if (root == null)
            return false;

        var ch = word.charAt(index);
        var child = root.getChild(ch);
        if (child == null)
            return false;

        return containsRecursive(child, word, index + 1);
    }

    public void traverse_Pre_order(){
        traverse_Pre_order(root);
    }
    private void traverse_Pre_order(Node root){
        //In pre-order traversal we have to visit root First
        System.out.println(root.value);
        for(var child:root.getChildren())
            traverse_Pre_order(child);
    }
    public void traverse_Post_order(){
        traverse_Post_order(root);
    }
    private void traverse_Post_order(Node root){
        //In post-order traversal we have to visit the leaf first
        for(var child:root.getChildren())
            traverse_Post_order(child);
        System.out.println(root.value);
    }
    public void remove(String word){
        if(word==null)//in null string , nothing is needed to remove
            return;
        remove(root,word,0); //remove the string
    }
    private void remove(Node root,String word,int index){
        if(index==word.length()) {//base case
            root.isEndofWord=false;
            //as we don't always physically remove the word
            //we can only remove the word if it has no child
            //in example cat & cattle
            //for removing "cat" when we reach at the first 't' of cattle then we set
            //the root.isEndofWord = false cause we are not allow to remove permanently
            //cause we also insert cattle as a word.
            //if we remove c,a,t from the Trie permanently , then if we search for cattle
            //it will show us that no word such cattle exist in Trie.
            return;
        }
        var ch = word.charAt(index);//get the character
        var child = root.getChild(ch);//get the child for that character
        if(child==null)//if no child found , simply return
            return;

        remove(child,word,index+1);

        //if no child found & it is the not the end of another word then only remove the characters
        //permanently from the Trie
        if(!child.hasChildren() && !child.isEndofWord)
            root.removeChild(ch);
    }
    public List<String> findWords(String prefix) {
        List<String> words = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);
        findWords(lastNode, prefix, words);
    //after completing this method execution words which is an arrayList
    // this arrayList contains the words with same prefix
    //this task is called autocompletion
        return words;
    }

    private void findWords(Node root, String prefix, List<String> words) {
        //This method will add the words in the arraylist called "words"
        //if user input (String prefix) is null then just simply return
        //nothing to find
        if (root == null)
            return;
        //if root.isEndofword  true that means the word is valid and we have
        //to add the word to arrayList
        //Now the question is why we just add the prefix in the list?
        //The answer is very simple ,prefix is updated everytime it visits a node
        //and string concatenation happens here until it found that the node.isEndofWord is true
        //we will help to understand this via a example
        //  c
        //  a
        //  r
        // d t
        // findWords(Node root, String prefix, List<String> words) method already
        //have the last node of the prefix which is called outside this method via
        //different method name findLastNodeOf(String prefix)
        //which means if user pass the prefix car
        //the findlastNode method return 'r' which is obvious the last node
        //of the prefix 'car'
        //now for the very first time when the
        // findWords(Node root, String prefix, List<String> words) method is called
        //it will have 'r' in the root parameter
        // now its checking for the first time is 'r' .isEndofWord ==true or not
        //if root.isEndofWord that means r is the end of any word then the prefix
        //car will be added in the arraylist 'words'
        //then the first ever recursion will called by the findWords method itself
        //it will get the child for example now we will find the child of 'r'
        //  c
        //  a
        //  r
        // d t
        //see r has two childs d and t
        //first it will do "car"+d and check d isEndofWord = true or not if yes the 'card'
        //will be added in the word which is an arrayList
        //parallel call happen here it is 'car' for the very first time
        //it works for children d and t parallelly
        //so next recursion generate car+t = cart and check if t .isEndofWord=ture or not
        //if yes then cart will be added in the arraylist 'word'
        if (root.isEndofWord)
            words.add(prefix);

        for (var child : root.getChildren()){
            findWords(child, prefix + child.value, words);
            //System.out.println(child.value);
        }
    }

    private Node findLastNodeOf(String prefix) {
        //if prefix is given null there is no lastnode
        //simply just return it
        if (prefix == null)
            return null;
        //if given prefix is not null , then we have to find the lastnode
        //we have to do iteration through nodes
        //suppose car is given as prefix
        //Now we iterate through c,a,r
        var current = root;
        for (var ch : prefix.toCharArray()) {
            var child = current.getChild(ch);
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
    public int countWords() {
        return countWords(root);
    }

    private int countWords(Node root) {
        var total = 0;//initialize a pointer

        if (root.isEndofWord)
            total++;
        //if any node endofword is true that means it is a complete word

        for (var child : root.getChildren())
            total += countWords(child);//1 call total = total+ countWords(child)

        return total;
    }
    public void printWords() {
        printWords(root, "");
    }

    private void printWords(Node root, String word) {
        int i=0;
        if (root.isEndofWord){
            words[i]=word;
            i++;
            System.out.println(word);
        }

        for (var child : root.getChildren())
            printWords(child, word + child.value);

        longestCommonPrefix(words);
    }

    // We add these words to a trie and walk down
    // the trie. If a node has more than one child,
    // that's where these words deviate. Try this
    // with "can", "canada", "care" and "cab". You'll
    // see that these words deviate after "ca".
    //
    // So, we keep walking down the tree as long as
    // the current node has only one child.
    //
    // One edge cases we need to count is when two
    // words are in the same branch and don't deviate.
    // For example "can" and "canada". In this case,
    // we keep walking down to the end because every
    // node (except the last node) has only one child.
    // But the longest common prefix here should be
    // "can", not "canada". So, we should find the
    // shortest word in the list first. The maximum
    // number of characters we can include in the
    // prefix should be equal to the length of the
    // shortest word.


    public void longestCommonPrefix(){
        //printWords();
        //longestCommonPrefix(words);
    }
    public  String longestCommonPrefix(String[] words) {
        if (words == null)
            return "";

        var trie = new Trie();
        for (var word : words)
            trie.insert(word);

        var prefix = new StringBuffer();
        var maxChars = getShortest(words).length();
        var current = trie.root;
        while (prefix.length() < maxChars) {
            var children = current.getChildren();
            if (children.length != 1)
                break;
            current = children[0];
            prefix.append(current.value);
        }

        return prefix.toString();
    }

    private  String getShortest(String[] words) {
        if (words == null || words.length == 0)
            return "";

        var shortest = words[0];
        for (var i = 1; i < words.length; i++) {
            if (words[i].length() < shortest.length())
                shortest = words[i];
        }

        return shortest;
    }

}
