SentimentBundle
===============

SentimentBundle for SNSCrawlerFramework

这个情感分析模块是微博数据抽取毕设的一部分

相较于无上下文的情感分析，额外使用了词对wordpair来实现朴素的上下文相关情感度量。

每个word或是wordpair都有若干个多维的情感向量，向量各维度对应情感的权重，其代数和为1

##Training类

###trainWordList方法

输入已有词表、新的句子（已经分词）、以及自行定义该句的情感类别。

通过这个方法，来对已有词表shake，也就是机器学习的迭代过程

###massIncrementEmotion方法

它统计了输入的句子（已经分词）中word和wordpair的总量，以此均分每个词和词对所需增加的权重，然后对其相应的feature进行加权

这里的加权可以使用决策树模型，也可以使用朴素贝叶斯，即“当发现完全相同的wordpair时另行加权”。

（TODO：情感词权重计算需要优化，二义策略可以使用SVM来提高分类精度）
