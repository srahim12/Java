public static void preOrder(Node root) {
    if(root!=null){
        visit(root);
        preOrder(root.left);
        preOrder(root.right);
    }   
}