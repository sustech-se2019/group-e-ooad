# encoding=utf-8
from sklearn.feature_extraction.text import TfidfVectorizer, TfidfTransformer
import csv
import jieba.analyse

from sklearn import feature_extraction
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import CountVectorizer

def get_cn_corpus(corpus):
    cn_corpus=[]
    for i in corpus:
        key_words = tokenizer(i)
        text = " ".join(key_words)
        cn_corpus.append(text)
    return cn_corpus

def tokenizer(new):
    jieba.add_word('\\')
    jieba.add_word('u3000')
    stop_list=[line.strip() for line in open('stop_words.txt',encoding='UTF-8').readlines()]
    result=[]
    #print(new)
    #key_words=jieba.analyse.extract_tags(new,topK=100)
    key_words=jieba.cut(new)
    for i in key_words:
        if i not in stop_list:
            if not str(i).isdigit():
                result.append(i)

    return result







if __name__ == '__main__':
    csv_file = csv.reader(open('116.csv', 'r', encoding='gbk'))
    first_line=1
    corpus = []
    id=[]
    for i in csv_file:
        if first_line:
            first_line=0
        else:
            id.append(i[0])
            corpus.append(i[4])

    cn_corpus=get_cn_corpus(corpus)

    vectorizer = CountVectorizer()
    frequency=(vectorizer.fit_transform(cn_corpus)).toarray()
    feature_words=vectorizer.get_feature_names()
    transformer = TfidfTransformer()
    tfidf = (transformer.fit_transform(frequency)).toarray()
    for i in range(0,len(tfidf)):
        #each new
        print("ID: "+str(id[i]))
        array=tfidf[i]
        dict={}
        for j in range(0,len(array)):
            dict[j]=array[j]

        sorted_dict= sorted(dict.items(),key=lambda item:item[1],reverse=True)
        #print(sorted_dict)
        print("Key words(5): ",end="")
        for j in range(0,len(sorted_dict)):
            if j<5:
                print(feature_words[sorted_dict[j][0]],end=" ")
        print()