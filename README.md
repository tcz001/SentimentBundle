SentimentBundle
===============

SentimentBundle for SNSCrawlerFramework

这个情感分析模块是微博数据抽取毕设的一部分

这个模块实现了大量已分类语料的机器学习过程，适用于已有大量分类而情感词表匮乏的情况。

相较于无上下文的情感分析，额外使用了词对wordpair来实现朴素的上下文相关情感度量。

每个word或是wordpair都有若干个多维的情感向量，向量各维度对应情感的权重，其代数和为1

##Training类

用于积累情感词表

###trainWordList方法

输入已有词表、新的句子（已经分词）、以及自行定义该句的情感类别。

通过这个方法，来对已有词表shake，也就是机器学习的迭代过程

###massIncrementEmotion方法

它统计了输入的句子（已经分词）中word和wordpair的总量，以此均分每个词和词对所需增加的权重，然后对其相应的feature进行加权

这里的加权可以使用决策树模型，也可以使用朴素贝叶斯，即“当发现完全相同的wordpair时另行加权”。



##Main类

用于评价语料情感

###情感分类

朴素的命中算法，即对句子中每一个词的每一个情感做代数和计算，通过命中率来描述句子与情感特征的匹配度

##TODO

考虑用SVM来优化文本分类，用源数据的词表结果来评估新数据，再用新数据对词表反馈，也就是把Main类与Train合并起来，省去人工特征简约(feature reduce)的过程。
