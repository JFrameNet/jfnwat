package jp.keio.jfn.wat.KWIC.repository;

import jp.keio.jfn.wat.KWIC.DTOKwicSearch;
import jp.keio.jfn.wat.KWIC.domain.KwicWord;
import jp.keio.jfn.wat.KWIC.domain.Kwics;

import java.util.List;

/**
 * Created by jfn on 6/8/16.
 */
public interface KwicsRepositoryCustom {

    List<Kwics> selectRandomByWord(DTOKwicSearch param, List<KwicWord> words);
    List<Kwics> selectRandomWithScopedCollocate(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates);
    List<Kwics> selectRandomWithEnd(DTOKwicSearch param, List<KwicWord> words);
    List<Kwics> selectRandomWithScopedCollocateAndEnd(DTOKwicSearch param, List<KwicWord> words, List<KwicWord> collocates);

    List<Kwics> sorttest(KwicWord word);

/*    List<Integer> findAllIDByWord(List<String> corpora, List<KwicWord> words);
    List<Integer> findAllIDByCollocate(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates);
    List<Integer> findAllIDByCollocateWithScope(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates,int before,int after);
    List<Integer> findAllIDBySentenceEnd(List<String> corpora,List<KwicWord> words,KwicWord dot,int scope);
    List<Integer> findAllIDByCollocateWithScopeAndEnd(List<String> corpora,List<KwicWord> words,List<KwicWord> collocates,int before,int after,KwicWord dot,int scope);*/
}
