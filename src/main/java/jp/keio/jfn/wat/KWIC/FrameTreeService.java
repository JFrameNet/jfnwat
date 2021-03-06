package jp.keio.jfn.wat.KWIC;

import jp.keio.jfn.wat.domain.*;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.util.List;

public class FrameTreeService {
    private Frame mainFrame;
    private TreeNode root;

    public TreeNode createTree(Frame frame){
        mainFrame = frame;
        root = new DefaultTreeNode("frame", null);
        // addFEs();
        addLUs();
        return root;
    }

    private void addFEs() {
        for (FrameElement fe : mainFrame.getFrameElements()) {
            TreeNode feTN = new DefaultTreeNode(fe.getName(), root);
        }
    }

    private void addLUs() {
        List<LexUnit> lus = mainFrame.getLexUnits();
        if(!lus.isEmpty()){
            TreeNode lusTN = new DefaultTreeNode("LU's", root);
            for (LexUnit lu : lus) {
                TreeNode luTN = new DefaultTreeNode(lu.getName(), lusTN);
//                addLemma(luTN, lu);
//                addSentences(luTN, lu);
            }
        }
    }

    private void addLemma(TreeNode luTN, LexUnit lu) {
        List<LexemeEntry> lexemes = lu.getLemma().getLexemeEntries();
        String pOS = lu.getLemma().getPartOfSpch().getName();
        TreeNode lemma = new DefaultTreeNode("Lemma("+pOS+")", luTN);
        for (LexemeEntry lexE: lexemes) {
            Lexeme lex = lexE.getLexeme();
            TreeNode lexTN = new DefaultTreeNode("Lexeme: "+lex.getName(), lemma);
            addForms(lexTN, lex);
        }
    }

    private void addForms(TreeNode lexTN, Lexeme lex) {
        for (WordForm wF: lex.getWordForms()) {
            new DefaultTreeNode("Form: "+wF.getForm(), lexTN);
        }
    }


    private void addSentences(TreeNode luTN, LexUnit lu) {

    }
}